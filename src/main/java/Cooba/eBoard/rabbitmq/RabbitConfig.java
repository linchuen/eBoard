package Cooba.eBoard.rabbitmq;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {
    public static final String TOPIC_EXCHANGE_NAME = "email-exchange";

    public static final String CONFIRMMAIL_QUENE = "ConfirmMailQuene";
    public static final String TEST_QUEUE = "test-queue";
    @Bean
    public Queue queue1() {
        return new Queue(CONFIRMMAIL_QUENE);
    }

    @Bean
    public Queue queue2() {
        return new Queue(TEST_QUEUE);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    Binding binding1(Queue queue1, TopicExchange exchange) {
        return BindingBuilder.bind(queue1).to(exchange).with("send.confirmMail");
    }

    @Bean
    Binding binding2(Queue queue2, TopicExchange exchange) {
        return BindingBuilder.bind(queue2).to(exchange).with("#.test");
    }

}
