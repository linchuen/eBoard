package Cooba.eBoard.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {
    private final String[] keys = {"send.confirmMail", "hello.test"};
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send_to_CONFIRMMAIL_QUENE(String msg) {
        String key=keys[0];
        System.out.println("Sender : "+ key + msg);
        rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE_NAME, key, msg);
    }

    public void send_to_TEST_QUEUE(String msg) {
        String key=keys[1];
        System.out.println("Sender : "+ key +" Msg : "+ msg);
        rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE_NAME, key, msg);
    }

}