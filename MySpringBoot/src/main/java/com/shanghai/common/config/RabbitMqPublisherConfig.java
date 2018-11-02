package com.shanghai.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.shanghai.common.rabbitmq.RabbitMqEnum;

/**
* @author: YeJR 
* @version: 2018年5月24日 下午4:16:32
* RabbitMq生产者配置：
* 	Exchange 交换机配置
* 	Queue 消息队列配置
*	以及交换机和队列的绑定， 交换机和消息队列可以多对多绑定
*/
@Configuration
@AutoConfigureAfter(RabbitMqConfig.class)
public class RabbitMqPublisherConfig {
	
	/**
	 * Direct exchange：直连交换机，点对点式。
	 * 如果和 Binding 中的 binding key 一致， 交换器就将消息发到对应的队列中。
	 * 路由键与队列名完全匹配，如果一个队列绑定到交换机要求路由键为“dog”，则只转发 routing key 标记为“dog”的消息，不会转发“dog.puppy”，也不会转发“dog.guard”等等。它是完全匹配、单播的模式
	 * @param rabbitAdmin
	 * @return
	 */
	@Bean
	public DirectExchange directExchange() {
		DirectExchange contrectDirectExchange = new DirectExchange(RabbitMqEnum.Exchange.CONTRACT_DIRECT.getCode());
		return contrectDirectExchange;
	}
	
	
	/**
	 * Fanout exchange : 扇形交换机，发布订阅式。
	 * 每个发到 fanout 类型交换器的消息都会分到所有绑定的队列上去。
	 * fanout 交换器不处理路由键，只是简单的将队列绑定到交换器上，每个发送到交换器的消息都会被转发到与该交换器绑定的所有队列上。
	 * 很像子网广播，每台子网内的主机都获得了一份复制的消息。fanout 类型转发消息是最快的。
	 * @return
	 */
	@Bean
	public FanoutExchange fanoutExchange() {
		FanoutExchange contractFanoutExchange = new FanoutExchange(RabbitMqEnum.Exchange.CONTRACT_FANOUT.getCode());
		return contractFanoutExchange;
	}
	
	/**
	 * Topic Exchange：主题匹配，发布订阅式
	 * topic 交换器通过模式匹配分配消息的路由键属性，将路由键和某个模式进行匹配，此时队列需要绑定到一个模式上。
	 * 它将路由键和绑定键的字符串切分成单词，这些单词之间用点隔开。
	 * 它同样也会识别两个通配符：符号“#”和符号“*”。#匹配0个或多个单词，*匹配一个单词
	 * @return
	 */
	@Bean
	public TopicExchange topicExchange() {
		TopicExchange contractTopicExchange = new TopicExchange(RabbitMqEnum.Exchange.CONTRACT_TOPIC.getCode());
		return contractTopicExchange;
	}
	
	
	
	
	
	
	
	/**
	 * 一般系统通知消息队列
	 * @return
	 */
	@Bean
	public Queue tipQueue() {
		Queue queue = new Queue(RabbitMqEnum.QueueName.SYSTEMNOTIFY.getCode());
		return queue;
	}
	
	/**
	 * 邮件消息队列
	 * @return
	 */
	@Bean
	public Queue emailQueue() {
		Queue queue = new Queue(RabbitMqEnum.QueueName.EMAILNOTIFY.getCode());
		return queue;
	}
	/**
	 * 短信消息队列
	 * @return
	 */
	@Bean
	public Queue smsQueue() {
		Queue queue = new Queue(RabbitMqEnum.QueueName.SMSNOTIFY.getCode());
		return queue;
	}
	
	
	
	/**
	 * 队列与交换机绑定
	 * @return
	 */
	@Bean
	public Binding bindingTipQueue() {
		Binding binding = BindingBuilder.bind(tipQueue()).to(fanoutExchange());
		return binding;
	}
	
	/**
	 * 队列与交换机绑定
	 * @return
	 */
	@Bean
	public Binding bindingEmailQueue() {
		Binding binding = BindingBuilder.bind(emailQueue()).to(directExchange()).with(RabbitMqEnum.RoutingKeyEnum.EMAILKEY.getCode());
		return binding;
	}
	
	/**
	 * 队列与交换机绑定
	 * @return
	 */
	@Bean
	public Binding bindingSMSQueue() {
		Binding binding = BindingBuilder.bind(smsQueue()).to(topicExchange()).with(RabbitMqEnum.RoutingKeyEnum.SMSKEY.getCode());
		return binding;
	}
	
}
