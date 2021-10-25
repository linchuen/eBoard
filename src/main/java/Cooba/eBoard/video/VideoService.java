package Cooba.eBoard.video;

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
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private FileService fileService;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

    public ResponseEntity createNewVideo(MultipartFile multipartFile,Date startAt,Date expiredAt) {
        if(fileService.uploadVideo(multipartFile)){
            String filename=multipartFile.getOriginalFilename();
            Date now=Calendar.getInstance().getTime();
            Video video = new Video(filename,simpleDateFormat.format(now),startAt,expiredAt,true);
            System.out.println(video.toString());
            videoRepository.insert(video);
            return ResponseEntity.ok().body(video);
        }else{
            return ResponseEntity.badRequest().body("create error");
        }
    }

    public List<Video> getAllVideo() {
        return videoRepository.findAll();
    }

    public List<Video> getEnabledPicture() {
        return videoRepository.findByEnabled(true);
    }

    public ResponseEntity updateVideo(String id, Video video) {
        if (videoRepository.existsById(id)) {
            Video videoInDB=videoRepository.findById(id).get();
            try {
                fileService.renameFile(videoInDB.getFilename(),video.getFilename());
                video.setId(videoInDB.getId());
                video.setCreateAt(videoInDB.getCreateAt());
                videoRepository.save(video);
                return ResponseEntity.ok().body(video);
            }catch (Exception exception){
                return ResponseEntity.badRequest().body(exception);
            }
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity deleteVideo(String id) {
        if (videoRepository.existsById(id)) {
            Video videoInDB=videoRepository.findById(id).get();
            try {
                fileService.deleteFile(videoInDB.getFilename());
                videoRepository.deleteById(id);
                return ResponseEntity.ok().build();
            }catch(Exception exception){
                return ResponseEntity.badRequest().body(exception);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
