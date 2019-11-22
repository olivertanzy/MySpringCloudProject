package com.qianlq.eurekacustomer.configuration.config;

import com.qianlq.eurekacustomer.service.OrderService;
import com.qianlq.eurekacustomer.service.TestService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
    @Autowired
    private TestService testService;
    @Autowired
    private OrderService orderService;

    /**
     * FANOUT广播队列监听一.
     *
     * @param message the message
     * @param channel the channel
     * @throws IOException the io exception  这里异常需要处理
     */
    @RabbitListener(queues = {"FANOUT_QUEUE_A"})
    public void on(Message message, Channel channel) throws IOException {
        System.out.println("接受消息");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        System.out.println("FANOUT_QUEUE_A " + new String(message.getBody()));
    }

    /**
     * FANOUT广播队列监听二.
     *
     * @param message the message
     * @param channel the channel
     * @throws IOException the io exception   这里异常需要处理
     */
    @RabbitListener(queues = {"FANOUT_QUEUE_B"})
    public void t(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.error("FANOUT_QUEUE_B " + new String(message.getBody()));
    }

    /**
     * DIRECT模式.
     *
     * @param message the message
     * @param channel the channel
     * @throws IOException the io exception  这里异常需要处理
     */
    @RabbitListener(queues = {"DIRECT_QUEUE"})
    public void message(Message message, Channel channel) throws IOException {
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.error("DIRECT " + new String(message.getBody()));
    }
    @RabbitListener(queues = {"oder"})
    public void saveOrder(Message message, Channel channel) throws IOException {
        System.out.println("请求service-order下单");
        String a = orderService.testCustomer("请求下单");
        System.out.println("-------------->>>"+a);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.error("ServiceCustomer: " + new String(message.getBody()));
    }


}