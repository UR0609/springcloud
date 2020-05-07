package com.ljryh.client.config.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@PropertySource("classpath:url.properties")
public class QueueReceiver {

    @Value("${queue.name}")
    private String queueName;

    @Bean
    public Queue testQueue() {
        return new Queue(queueName, true);
    }

    @RabbitListener(queues = "${queue.name}", concurrency = "${queue.nub}")
    public void receiver(String data) {
        log.info("<------- QueueReceiver:{} ------->",data);
    }

}
