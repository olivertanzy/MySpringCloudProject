package com.qianlq.eurekaorder.common.message;

import com.qianlq.eurekaorder.Etity.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import java.util.UUID;

/**
 * 消息发送服务
 */
@Service
public class SenderService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 测试广播模式.
     *
     * @param p the p
     * @return the response entity
     */
/*    public void broadcast(String p) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        System.out.println("发送消息");
        rabbitTemplate.convertAndSend("FANOUT_EXCHANGE", "", p, correlationData);
    }

    *//**
     * 测试Direct模式.
     *
     * @param p the p
     * @return the response entity
     *//*
    public void direct(String p) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("DIRECT_EXCHANGE", "DIRECT_ROUTING_KEY", p, correlationData);
    }

    public void testOder(Test p) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("testOrder", "oder", p, correlationData);
    }*/

    /**
     * 测试exchange的Fanout模式
     * @param p
     */
    public void tesFanout(Test p) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("fanoutExchange", "", p,correlationData);
        CorrelationData correlationDatas = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("fanoutExchange", "routingKeyFanout", p,correlationDatas);
    }
    /**
     * 测试exchange的Direct模式
     * @param p
     */
    public void tesDirect(Test p) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("exchangeDirect", "routingKeyDirect", p,correlationData);
    }

    /**
     * 测试exchange的topic模式
     * @param p
     */
    public void tesTopic(Test p) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("topicExchange", "user.info.name", p,correlationData);
        CorrelationData correlationDatas = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("topicExchange", "user.info.age", p,correlationDatas);
    }


}