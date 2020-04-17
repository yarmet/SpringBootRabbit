package ru.ruslan.SpringBootRabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class RabbitConfiguration {

    Random random = new Random();

    //настраиваем соединение с RabbitMQ
    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    //объявляем очередь с именем queue1
    @Bean
    public Queue myQueue1() {
        return new Queue("queue1");
    }

    //объявляем очередь с именем queue1
    @Bean
    public Queue myQueue2() {
        return new Queue("queue2");
    }

    // создаем exchange
    @Bean
    public FanoutExchange fanoutExchangeA(){
        return new FanoutExchange("exchange-example-3");
    }

    // прикрепляем очередь myQueue1 к  exchange
    @Bean
    public Binding binding1(){
        return BindingBuilder.bind(myQueue1()).to(fanoutExchangeA());
    }

    // прикрепляем очередь myQueue2 к  exchange
    @Bean
    public Binding binding2(){
        return BindingBuilder.bind(myQueue2()).to(fanoutExchangeA());
    }


    // получаем из очереди myQueue1
    @RabbitListener(queues = "queue1")
    public void worker1(String message) throws InterruptedException {
        System.out.println("worker 1 : " + message);
        Thread.sleep(100 * random.nextInt(20));
    }

    // получаем из очереди myQueue2
    @RabbitListener(queues = "queue2")
    public void worker2(String message) throws InterruptedException {
        System.out.println("worker 2 : " + message);
        Thread.sleep(100 * random.nextInt(20));
    }

}
