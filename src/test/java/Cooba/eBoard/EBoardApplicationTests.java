package Cooba.eBoard;

import Cooba.eBoard.WebUser.WebUser;
import Cooba.eBoard.WebUser.WebUserRepository;
import Cooba.eBoard.WebUser.WebUserService;
import Cooba.eBoard.mailservice.MailService;
import Cooba.eBoard.rabbitmq.Sender;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EBoardApplicationTests {
    private String testUsername="Harry Potter";
    private String testEmail="abc@gmail.com";
    private String testPassword="root123";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    JavaMailSender mailSender;
    @MockBean
    MailService mailService;
    @Autowired
    WebUserRepository webUserRepository;
    @Autowired
    WebUserService webUserService;
    @Autowired
    Sender sender;

/*
    @Test
    void sendToGmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("youraccount@gmail.com");
        message.setSubject("測試透過 Gmail 去發信");
        message.setText("org.springframework.mail.SimpleMailMessage 透過 Gmail 發信。");

        mailSender.send(message);
    }
*/

    @Test
    public void registerTest() throws Exception {
        webUserRepository.deleteAll();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        Mockito.doNothing().when(mailService).sendConfirmMail(anyString());
        JSONObject request = new JSONObject()
                .put("username", testUsername)
                .put("email", testEmail)
                .put("password",testPassword);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString());

        mockMvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void enableUserTest() throws Exception {
        WebUser testuser=webUserRepository.findByEmail(testEmail).get();
        Assertions.assertEquals("Hello " + testuser.getUsername() + ", your account is ready to use!!",webUserService.enableUser(testuser.getId()));
    }

    @Test
    public void authTest() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        JSONObject request = new JSONObject()
                .put("username", testEmail)
                .put("password",testPassword);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.toString());

        mockMvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void sendTestMsg() throws Exception {
        sender.send_to_TEST_QUEUE("Hello test msg");
    }
}
