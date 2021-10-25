package Cooba.eBoard.text;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TextController {
    @Autowired
    private TextService textService;

    @PostMapping(value = "/text", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity uploadText(@RequestBody @Validated Text text) {
        return textService.createNewText(text);
    }

    @GetMapping("text/all")
    public List<Text> getAllText() {
        return textService.getAllText();
    }

    @GetMapping("text")
    public List<Text> getEnabledText() {
        return textService.getEnabledText();
    }

    @PutMapping("text/{id}")
    public ResponseEntity updateText(@PathVariable String id, @RequestBody @Validated Text text) {
        return textService.updateText(id, text);
    }

    @DeleteMapping("text/{id}")
    public ResponseEntity deleteText(@PathVariable String id) {
        return textService.deleteText(id);
    }
}
