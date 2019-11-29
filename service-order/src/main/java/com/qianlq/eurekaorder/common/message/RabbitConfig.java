package com.qianlq.eurekaorder.common.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class RabbitConfig {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 定制化amqp模版      可根据需要定制多个
     * <p>
     * <p>
     * 此处为模版类定义 Jackson消息转换器
     * ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调   即消息发送到exchange  ack
     * ReturnCallback接口用于实现消息发送到RabbitMQ 交换器，但无相应队列与交换器绑定时的回调  即消息发送不到任何一个队列中  ack
     *
     * @return the amqp template
     */
    // @Primary
    @Bean
    public AmqpTemplate amqpTemplate() {
        Logger log = LoggerFactory.getLogger(RabbitTemplate.class);
        // 使用jackson 消息转换器
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setEncoding("UTF-8");
        // 消息发送失败返回到队列中，yml需要配置 publisher-returns: true
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationIdString();
            log.error("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
        });
        // 消息确认，yml需要配置 publisher-confirms: true
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.error("消息发送到exchange成功,id: {}", correlationData.getId());
            } else {
                log.error("消息发送到exchange失败,原因: {}", cause);
            }
        });
        return rabbitTemplate;
    }



    @Bean("fanoutExchange")
    public FanoutExchange exchangeFanout() {
        return (FanoutExchange) ExchangeBuilder.fanoutExchange("fanoutExchange").durable(true).build();
    }
    @Bean("fanoutExchangeQueue")
    public Queue fanoutExchangeQueue() {
        return QueueBuilder.durable("fanout_exchange").build();
    }
    @Bean
    public Binding fanoutExchangeBinding(@Qualifier("fanoutExchangeQueue") Queue queue,
                                    @Qualifier("fanoutExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }
    @Bean("fanoutQueueA")
    public Queue fanoutQueueA() {
        return QueueBuilder.durable("fanoutQueueA").build();
    }
    @Bean("fanoutQueueB")
    public Queue fanoutQueueB() {
        return QueueBuilder.durable("fanoutQueueB").build();
    }
    @Bean
    public Binding bindingB(@Qualifier("fanoutQueueB") Queue queue,
                            @Qualifier("fanoutExchange") FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }
    @Bean
    public Binding bindingA(@Qualifier("fanoutQueueA") Queue queue,
                            @Qualifier("fanoutExchange") FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }


    @Bean("exchangeDirect")
    public DirectExchange exchangeDirect() {
        return (DirectExchange) ExchangeBuilder.directExchange("exchangeDirect").durable(true).build();
    }
    @Bean("directExchangeQueue")
    public Queue directExchangeQueue() {
        return QueueBuilder.durable("directExchangeQueue").build();
    }
    @Bean
    public Binding directExchangeBinding(@Qualifier("directExchangeQueue") Queue queue,
                                         @Qualifier("exchangeDirect") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("routingKeyDirect");
    }


    @Bean("topicExchange")
    public TopicExchange topicExchange() {
        return (TopicExchange) ExchangeBuilder.topicExchange("topicExchange").durable(true).build();
    }
    @Bean("topicExchangeQueue")
    public Queue topicExchangeQueue() {
        return QueueBuilder.durable("topicExchangeQueue").build();
    }
    @Bean
    public Binding topicBinding(@Qualifier("topicExchangeQueue") Queue queue,
                                @Qualifier("topicExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("user.info.*");
    }
}