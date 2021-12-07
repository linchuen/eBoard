package Cooba.eBoard.WebUser;


import Cooba.eBoard.mailservice.MailService;
import Cooba.eBoard.rabbitmq.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WebUserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private WebUserRepository webUserRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    Sender sender;
    @Override
    public WebUser loadUserByUsername(String email) throws UsernameNotFoundException {
        return webUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public ResponseEntity createNewUser(WebUser webUser) {
        boolean isExist = webUserRepository.findByEmail(webUser.getEmail()).isPresent();
        if (isExist) {
            return ResponseEntity.badRequest().body("使用者已經存在");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(webUser.getPassword());
        webUser.setPassword(encodedPassword);
        webUserRepository.save(webUser);
        try {
            sender.send_to_CONFIRMMAIL_QUENE(webUser.getEmail());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("發送認證信譽到錯誤");
        }

        return ResponseEntity.ok().body("帳號建立成功，請確認您的電子信箱是否收到認證信");
    }

    public String enableUser(String id) {
        WebUser user = webUserRepository.findById(id).get();
        user.setEnabled(true);
        webUserRepository.save(user);
        return "Hello " + user.getUsername() + ", your account is ready to use!!";
    }

    public List<WebUser> ShowAllUser() {
        return webUserRepository.findAll();
    }

    public ResponseEntity DeleteUser(String email){
        Optional<WebUser> webUser=webUserRepository.findByEmail(email);
        boolean isExist = webUser.isPresent();
        if (!isExist) {
            return ResponseEntity.badRequest().body("要刪除的使用者不存在");
        }

        webUserRepository.delete(webUser.get());
        return ResponseEntity.ok().body(webUser.get().getEmail()+"刪除成功");
    }
}
