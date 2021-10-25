package Cooba.eBoard.text;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class TextService {
    @Autowired
    private TextRepository textRepository;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public ResponseEntity<Text> createNewText(Text text) {
        text.setCreateAt(simpleDateFormat.format(Calendar.getInstance().getTime()));
        text.setEnabled(true);
        System.out.println(text.toString());
        textRepository.insert(text);
        return ResponseEntity.ok().body(text);
    }

    public List<Text> getAllText() {
        return textRepository.findAll();
    }

    public List<Text> getEnabledText() {
        return textRepository.findByEnabled(true);
    }

    public ResponseEntity<Text> updateText(String id, Text text) {
        if (textRepository.existsById(id)) {
            text.setId(textRepository.findById(id).get().getId());
            text.setCreateAt(textRepository.findById(id).get().getCreateAt());
            textRepository.save(text);
            return ResponseEntity.ok().body(text);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity deleteText(String id) {
        if (textRepository.existsById(id)) {
            textRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
