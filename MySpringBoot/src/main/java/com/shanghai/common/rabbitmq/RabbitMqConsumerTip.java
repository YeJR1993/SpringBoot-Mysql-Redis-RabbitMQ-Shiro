package com.shanghai.common.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

/**
* @author: YeJR 
* @version: 2018年5月24日 下午4:16:32
* RabbitMQ 消费者配置， 监听定义好的queue
* springboot注解的方式监听队列，无法手动指定回调，
* 所以采用了实现ChannelAwareMessageListener接口，重写onMessage来进行手动回调
* SYSTEMNOTIFY消息队列监听处理
*/
@Component
public class RabbitMqConsumerTip {
	
	private static final Logger logger = LoggerFactory.getLogger(RabbitMqConsumerTip.class);
	
	/**
	 * 管理消息监听
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(RabbitMqEnum.QueueName.SYSTEMNOTIFY.getCode());
		//设置监听信道
		container.setMessageListener(tipChannelAwareMessageListener());
		//设置确认模式
		//NONE:可以称之为自动回调，即使无响应或者发生异常均会通知队列消费成功，会丢失数据。
		//AUTO:自动检测异常或者超时事件，如果发生则返回noack，消息自动回到队尾，但是这种方式可能出现消息体本身有问题，返回队尾其他队列也不能消费，造成队列阻塞
		//MANUAL:手动回调，在程序中我们可以对消息异常记性捕获，如果出现消息体格式错误问题，手动回复ack，接着再次调用发送接口把消息推到队尾
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		return container;
	}
	
	/**
	 * 监听消息队列中的消息，回调处理
	 * @return
	 */
	@Bean
	public ChannelAwareMessageListener tipChannelAwareMessageListener () {
		return new ChannelAwareMessageListener() {
			
			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				try {
					byte[] body = message.getBody();
					String string = new String(body);
					logger.info("获取到"+RabbitMqEnum.QueueName.SYSTEMNOTIFY.getCode()+"队列中的消息：{}", string);
				} catch (Exception e) {
					//发声错误再把消息发送到队列
					e.printStackTrace();
				} finally {
					//确认消息成功消费
					channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
				}
				
			}
			
		};
	}
	
}
