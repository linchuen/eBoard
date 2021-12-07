package Cooba.eBoard.picture;

import Cooba.eBoard.fileservice.FileService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;


@RestController
public class PictureController {
    @Autowired
    private PictureService pictureService;
    @Autowired
    private FileService fileService;

    @ApiOperation(value="上傳圖片", notes="將使用者上傳圖片儲存至伺服器，並建立圖片相關資訊至資料庫")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")
    @ApiResponses({
            @ApiResponse(responseCode="200", description="成功上傳圖片", content = @Content),
            @ApiResponse(responseCode="404", description="上傳失敗")
    })
    @PostMapping(value = "/picture")
    public ResponseEntity<Picture> uploadPicture(@RequestParam("image") MultipartFile multipartFile,
                                        @ApiParam("開始時間") @RequestParam("startAt") Date startAt,
                                        @ApiParam("結束時間") @RequestParam("expiredAt") Date expiredAt) {
        return pictureService.createNewPicture(multipartFile,startAt,expiredAt);
    }

    @ApiOperation(value="取得所有圖片", notes="取得所有上傳圖片資訊")
    @ApiResponses({@ApiResponse(responseCode="200", description="取得所有圖片資訊")})
    @GetMapping(value = "picture/all", produces = "application/json")
    public List<Picture> getAllPicture() {
        return pictureService.getAllPicture();
    }

    @ApiOperation(value="取得啟用圖片", notes="取得有啟用的圖片資訊")
    @ApiResponses({@ApiResponse(responseCode="200", description="取得有啟用的圖片資訊")})
    @GetMapping(value = "picture", produces = "application/json")
    public List<Picture> getEnabledPicture() {
        return pictureService.getEnabledPicture();
    }

    @ApiOperation(value="更新圖片", notes="根據圖片 id 更新相對圖片資訊")
    @ApiResponses({
            @ApiResponse(responseCode="200", description="成功更新圖片資訊"),
            @ApiResponse(responseCode="404", description="要更新的圖片 id 不存在")
    })
    @PutMapping(value = "picture/{id}", produces = "application/json")
    public ResponseEntity updatePicture(@PathVariable String id, @RequestBody Picture picture) {
        return pictureService.updatePicture(id, picture);
    }
    @ApiOperation(value="刪除圖片", notes="根據圖片 id 刪除相對圖片資訊及檔案")
    @ApiResponses({
            @ApiResponse(responseCode="200", description="成功更新圖片資訊"),
            @ApiResponse(responseCode="404", description="要刪除的圖片 id 不存在或刪除失敗")
    })
    @DeleteMapping("picture/{id}")
    public ResponseEntity deletePicture(@PathVariable String id) {
        return pictureService.deletePicture(id);
    }
}
