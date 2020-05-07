package com.ljryh.client.config.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Component
@PropertySource("classpath:url.properties")
public class QueueSender {

    @Value("${queue.name}")
    private String queueName;

    @Resource
    private AmqpTemplate rabbitTemplate;

    public String sender(String data) {
        for (int i = 0; i < 10; i++) {
            String context = "count:"+i+",data: " + data + " --" + new Date();
            this.rabbitTemplate.convertAndSend(queueName, i + context);
        }
        return null;
    }

}
