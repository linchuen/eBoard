package Cooba.eBoard.rabbitmq;

import Cooba.eBoard.mailservice.MailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    @Autowired
    MailService mailService;
    @RabbitListener(queues = RabbitConfig.CONFIRMMAIL_QUENE)
    public void process1(String email) {
        System.out.println("Receiver : 成功寄認證信至" + email);
        mailService.sendConfirmMail(email);
    }

    @RabbitListener(queues = RabbitConfig.TEST_QUEUE)
    public void process2(String message) {
        System.out.println("Test Receiver : " + message);
    }
}