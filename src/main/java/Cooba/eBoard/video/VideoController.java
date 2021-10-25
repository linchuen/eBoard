package Cooba.eBoard.video;

import Cooba.eBoard.fileservice.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private FileService fileService;

    @PostMapping(value = "/video")
    public ResponseEntity uploadVideo(@RequestParam("video") MultipartFile multipartFile,@RequestParam("startAt") Date startAt
            ,@RequestParam("expiredAt") Date expiredAt) {
        return videoService.createNewVideo(multipartFile,startAt,expiredAt);
    }

    @GetMapping("video/all")
    public List<Video> getAllVideo() {
        return videoService.getAllVideo();
    }

    @GetMapping("video")
    public List<Video> getEnabledVideo() {
        return videoService.getEnabledPicture();
    }

    @PutMapping("video/{id}")
    public ResponseEntity updateVideo(@PathVariable String id, @RequestBody Video video) {
        return videoService.updateVideo(id, video);
    }

    @DeleteMapping("video/{id}")
    public ResponseEntity deleteVideo(@PathVariable String id) {
        return videoService.deleteVideo(id);
    }
}
