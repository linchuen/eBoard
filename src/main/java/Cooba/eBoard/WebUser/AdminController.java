package Cooba.eBoard.WebUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {
    @Autowired
    WebUserService webUserService;

    @GetMapping(path = "/admin")
    public List<WebUser> showAllUser(){
        return webUserService.ShowAllUser();
    }

    @DeleteMapping(path = "/admin/{email}")
    public ResponseEntity deleteUser(@PathVariable String email){
        return webUserService.DeleteUser(email);
    }
}
