package com.qianlq.eurekaorder.common.message;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 消息监听器
 *
 * @author XiongNeng
 * @version 1.0
 */
@Component
public class Receiver {
    private static final Logger log = LoggerFactory.getLogger(Receiver.class);

    /**
     * FANOUT广播队列监听一.
     *
     * @param message the message
     * @param channel the channel
     * @throws IOException the io exception  这里异常需要处理
     */
/*    @RabbitListener(queues = {"FANOUT_QUEUE_A"})
    public void on(Message message, Channel channel) throws IOException {
        System.out.println("接受消息");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        System.out.println("FANOUT_QUEUE_A " + new String(message.getBody()));
    }

    *//**
     * FANOUT广播队列监听二.
     *
     * @param message the message
     * @param channel the channel
     * @throws IOException the io exception   这里异常需要处理
     *//*
    @RabbitListener(queues = {"FANOUT_QUEUE_B"})
    public void t(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.error("FANOUT_QUEUE_B " + new String(message.getBody()));
    }

    *//**
     * DIRECT模式.
     *
     * @param message the message
     * @param channel the channel
     * @throws IOException the io exception  这里异常需要处理
     *//*
    @RabbitListener(queues = {"DIRECT_QUEUE"})
    public void message(Message message, Channel channel) throws IOException {
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.error("DIRECT " + new String(message.getBody()));
    }
    //@RabbitListener(queues = {"oder"})
    public void saveOrder(Message message, Channel channel) throws IOException {
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.error("DIRECT " + new String(message.getBody()));
    }*/

/*    @RabbitListener(queues = {"fanoutExchangeQueue"})
    public void message(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.error("Finout " + new String(message.getBody()));
    }*/

    @RabbitListener(queues = {"fanoutQueueA"})
    public void message1(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.error("FinoutA " + new String(message.getBody()));
    }
    @RabbitListener(queues = {"fanout_exchange"})
    public void message5(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.error("fanout_exchange " + new String(message.getBody()));
    }


    @RabbitListener(queues = {"fanoutQueueB"})
    public void message2(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.error("FinoutB " + new String(message.getBody()));
    }

    @RabbitListener(queues = {"directExchangeQueue"})
    public void message4(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.error("direct " + new String(message.getBody()));
    }

    @RabbitListener(queues = {"topicExchangeQueue"})
    public void message6(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.error("topicExchangeQueue " + new String(message.getBody()));
    }

}