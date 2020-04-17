package ru.ruslan.SpringBootRabbit;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;

@Controller
public class SampleController {

    private int count = 0;

    @PostConstruct
    private void init() {
        System.out.println("controller construsted");
    }

    @Autowired
    private RabbitTemplate template;

    @RequestMapping("/emit")
    @ResponseBody
    public String queue1() {
        // Бросим сообщение в наш Exchange
        count++;
        System.out.println("count " + count);
        System.out.println("Emit to queue1");
        template.setExchange("exchange-example-3");
        template.convertAndSend("Fanout message");
        return "Emit to queue";
    }
}
