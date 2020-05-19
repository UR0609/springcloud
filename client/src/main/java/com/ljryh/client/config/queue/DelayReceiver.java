package com.ljryh.client.config.queue;

import com.rabbitmq.client.Channel;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 消费者DelayReceiver
 */
@Component
public class DelayReceiver {

    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(DelayReceiver.class);

    @RabbitListener(queues = {DelayRabbitConfig.QUEUE_NAME},concurrency = DelayRabbitConfig.QUEUE_COUNT)
    public void orderDelayQueue(String data, Message message, Channel channel) {
        Logger.info("###########################################");
        Logger.info("【orderDelayQueue 监听的消息】 - 【消费时间】 - [{}]- 【内容】 - [{}]",  new Date(), data);
        Logger.info("###########################################");
    }

}
