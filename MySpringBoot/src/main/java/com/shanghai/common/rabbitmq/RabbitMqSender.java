package com.shanghai.common.rabbitmq;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* @author: YeJR 
* @version: 2018年5月25日 上午11:46:46
* rabbitmq 消息发送工具类
*/
@Component
public class RabbitMqSender implements ConfirmCallback{

	private static final Logger logger = LoggerFactory.getLogger(RabbitMqSender.class);
	
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
    public RabbitMqSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this);
    }
	
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		if (ack) {
			logger.info("消息发送确认成功: {}", correlationData.getId());
		} else {
			logger.info("消息发送确认失败: {}", cause);
		}
	}

	/**
	 * 通过direct交换机发送消息到指定（绑定时的routingKey完全一致）的queue
	 * @param routingKey
	 * @param object
	 */
	public void sendRabbitMqDirect(String routingKey, Object object) {
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		logger.info("发送消息到direct交换机: {}", correlationData.getId());
		this.rabbitTemplate.convertAndSend(RabbitMqEnum.Exchange.CONTRACT_DIRECT.getCode(), routingKey, object, correlationData);
	}
	
	/**
	 * 通过fanout交换机发送消息到所有与该交换机绑定的queue上
	 * @param routingKey
	 * @param object
	 */
	public void sendRabbitMqFanout(String routingKey, Object object) {
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		logger.info("发送消息到fanout交换机: {}", correlationData.getId());
		this.rabbitTemplate.convertAndSend(RabbitMqEnum.Exchange.CONTRACT_FANOUT.getCode(), routingKey, object, correlationData);
	}
	
	/**
	 * 通过topic交换机匹配routingkey相同的queue
	 * @param routingKey
	 * @param object
	 */
	public void sendRabbitMqTopic(String routingKey, Object object) {
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
		logger.info("发送消息到topic交换机: {}", correlationData.getId());
		this.rabbitTemplate.convertAndSend(RabbitMqEnum.Exchange.CONTRACT_TOPIC.getCode(), routingKey, object, correlationData);
	}
	
}
