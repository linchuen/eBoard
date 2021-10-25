package Cooba.eBoard.fileservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;

@Service
public class FileService {
    @Value("${web.upload-path}")
    String UPLOAD_DIRECTORY;

    public Boolean uploadPicture(MultipartFile multipartFile){
        String pictureType=multipartFile.getContentType();
        if (!multipartFile.isEmpty()) {
            if(Objects.equals(pictureType, MediaType.IMAGE_PNG_VALUE)||
                    Objects.equals(pictureType, MediaType.IMAGE_JPEG_VALUE)||
                    Objects.equals(pictureType, MediaType.IMAGE_GIF_VALUE)){
                try {
                    File directory = new File(UPLOAD_DIRECTORY);
                    if (!directory.exists()){
                        directory.mkdirs();
                    }
                    String filepath=UPLOAD_DIRECTORY.concat(multipartFile.getOriginalFilename());
                    multipartFile.transferTo(new File(filepath));
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public Boolean uploadVideo(MultipartFile multipartFile){
        String videoType=multipartFile.getContentType();
        if (!multipartFile.isEmpty()) {
            if(Objects.equals(videoType, "video/mp4")||
                    Objects.equals(videoType, "video/ogg")||
                    Objects.equals(videoType, "video/webm")) {
                try {
                    File directory = new File(UPLOAD_DIRECTORY);
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }
                    String filepath = UPLOAD_DIRECTORY.concat(multipartFile.getOriginalFilename());
                    multipartFile.transferTo(new File(filepath));
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    public boolean renameFile(String originFilename,String newFilename){
        File file=new File(UPLOAD_DIRECTORY.concat(originFilename));
        if (file.exists()){
            file.renameTo(new File(UPLOAD_DIRECTORY.concat(newFilename)));
            return true;
        }return false;
    }

    public boolean deleteFile(String filename){
        File file=new File(UPLOAD_DIRECTORY.concat(filename));
        return file.delete();
    }
}
