package Cooba.eBoard.picture;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@Document("picture")
public class Picture {
    @Id
    @ApiModelProperty(value = "圖片id",notes = "自動建立", required = true)
    private String id;
    @ApiModelProperty(value = "圖片名稱", required = true)
    private String filename;
    @ApiModelProperty(value = "圖片建立時間",example = "yyyy/MM/dd HH:mm:ss.SSS",required = false)
    private String createAt;
    @ApiModelProperty(value = "開始時間", required = true)
    private Date startAt;
    @ApiModelProperty(value = "結束時間", required = true)
    private Date expiredAt;
    @ApiModelProperty(value = "是否啟用", required = true)
    private Boolean enabled;

    public Picture(String filename,String createAt, Date startAt, Date expiredAt, Boolean enabled){
        this.filename=filename;
        this.createAt=createAt;
        this.startAt=startAt;
        this.expiredAt=expiredAt;
        this.enabled=enabled;
    }
    public Picture(String filename, Date startAt, Date expiredAt, Boolean enabled){
        this.filename=filename;
        this.startAt=startAt;
        this.expiredAt=expiredAt;
        this.enabled=enabled;
    }
}
