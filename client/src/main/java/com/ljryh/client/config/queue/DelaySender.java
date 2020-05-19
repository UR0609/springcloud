package com.ljryh.client.config.queue;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 生产者DelaySender
 */
@Component
public class DelaySender {

    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(DelaySender.class);

    @Resource
    private AmqpTemplate amqpTemplate;

    public String sendDelay(String data) {
        Logger.info("生成时间" + new Date().toString() +"【10秒后检查是否被消费】" + data );
        this.amqpTemplate.convertAndSend(DelayRabbitConfig.DELAY_EXCHANGE, DelayRabbitConfig.DELAY_ROUTING_KEY, data, message -> {
            // 如果配置了 params.put("x-message-ttl", 5 * 1000); 那么这一句也可以省略,具体根据业务需要是声明 Queue 的时候就指定好延迟时间还是在发送自己控制时间
            message.getMessageProperties().setExpiration(1 * 1000 * 10 + "");
            return message;
        });
        return ""+new Date();
    }

}
