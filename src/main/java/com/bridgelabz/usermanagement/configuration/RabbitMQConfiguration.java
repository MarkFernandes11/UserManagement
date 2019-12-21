package com.bridgelabz.usermanagement.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bridgelabz.usermanagement.rabbitmq.MessageListener;
import com.bridgelabz.usermanagement.rabbitmq.MessageListnerImpl;


@Configuration
@EnableAutoConfiguration
public class RabbitMQConfiguration {

	@Value("${fundoo.rabbitmq.exchange}")
	String exchange;
	
	@Value("${fundoo.rabbitmq.routingkey}")
	String routingKey;

	@Bean
	Queue queue() {
		return new Queue(routingKey, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(exchange);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingKey);
	}
	
	
	// Listening Part
	@Bean
	public ConnectionFactory factory() {
		CachingConnectionFactory factory = new CachingConnectionFactory("localhost");
		factory.setUsername("admin");
		factory.setPassword("admin");
		
		return factory;
	}
	
	
	@Bean
	SimpleMessageListenerContainer container (ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(routingKey);
		container.setMessageListener(listenerAdapter);
		
		return container;		
	}
	
	@Bean
	MessageListener listener() {
		//System.out.println("In listener");
		return new MessageListnerImpl();
	}
	
	@Bean
	MessageListenerAdapter myQueueListenerAdapter(MessageListener listener) {
		System.out.println("In QUEUE LISTENER ");
		return new MessageListenerAdapter(listener, "receiveMessage");
	}

}

