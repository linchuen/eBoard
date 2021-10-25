package Cooba.eBoard.video;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Document("video")
public class Video {
    @Id
    private String id;
    private String filename;
    private String createAt;
    private Date startAt;
    private Date expiredAt;
    private Boolean enabled;

    public Video(String filename,String createAt, Date startAt, Date expiredAt, Boolean enabled){
        this.filename=filename;
        this.createAt=createAt;
        this.startAt=startAt;
        this.expiredAt=expiredAt;
        this.enabled=enabled;
    }
    public Video(String filename, Date startAt, Date expiredAt, Boolean enabled){
        this.filename=filename;
        this.startAt=startAt;
        this.expiredAt=expiredAt;
        this.enabled=enabled;
    }
}
