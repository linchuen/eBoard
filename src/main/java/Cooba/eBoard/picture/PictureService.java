package Cooba.eBoard.picture;

import Cooba.eBoard.fileservice.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PictureService {
    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private FileService fileService;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

    public ResponseEntity createNewPicture(MultipartFile multipartFile,Date startAt,Date expiredAt) {
        if(fileService.uploadPicture(multipartFile)){
            String filename=multipartFile.getOriginalFilename();
            Date now=Calendar.getInstance().getTime();
            Picture picture = new Picture(filename,simpleDateFormat.format(now),startAt,expiredAt,true);
            System.out.println(picture.toString());
            pictureRepository.insert(picture);
            return ResponseEntity.ok().body(picture);
        }else{
            return ResponseEntity.badRequest().body("create error");
        }
    }

    public List<Picture> getAllPicture() {
        return pictureRepository.findAll();
    }

    public List<Picture> getEnabledPicture() {
        return pictureRepository.findByEnabled(true);
    }

    public ResponseEntity updatePicture(String id, Picture picture) {
        if (pictureRepository.existsById(id)) {
            Picture videoInDB=pictureRepository.findById(id).get();
            try {
                fileService.renameFile(videoInDB.getFilename(),picture.getFilename());
                picture.setId(videoInDB.getId());
                picture.setCreateAt(videoInDB.getCreateAt());
                pictureRepository.save(picture);
                return ResponseEntity.ok().body(picture);
            }catch (Exception exception){
                return ResponseEntity.badRequest().body(exception);
            }
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity deletePicture(String id) {
        if (pictureRepository.existsById(id)) {
            Picture pictureInDB=pictureRepository.findById(id).get();
            try {
                fileService.deleteFile(pictureInDB.getFilename());
                pictureRepository.deleteById(id);
                return ResponseEntity.ok().build();
            }catch(Exception exception){
                return ResponseEntity.badRequest().body(exception);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
