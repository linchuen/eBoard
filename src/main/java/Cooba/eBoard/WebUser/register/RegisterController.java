package Cooba.eBoard.WebUser.register;

import Cooba.eBoard.WebUser.WebUser;
import Cooba.eBoard.WebUser.WebUserRole;
import Cooba.eBoard.WebUser.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class RegisterController {
    @Autowired
    private WebUserService webUserService;
    
    @PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity registerNewUser(@Valid @RequestBody RegisRequest request) {
        return webUserService.createNewUser(new WebUser(request.getUsername(), request.getEmail(), request.getPassword(), WebUserRole.USER));
    }

    @GetMapping(path = "/register/{id}")
    public String enableRegisterUser(@PathVariable String id) {
        webUserService.enableUser(id);
        return "/ready/ready.html";
    }
}
