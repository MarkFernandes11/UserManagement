package com.bridgelabz.usermanagement.configuration;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.bridgelabz.notes.rabbitMq.MessageListenerImpl;
//import com.bridgelabz.notes.rabbitMq.MessageListner;
//
//@Configuration
//public class RabbitMQConfiguration {
//
//	public static final String ROUTING_KEY = "RabbitRoutingKey";
//
//	public static final String EXCHANGE = "RabbitQueueBL";
//	
//	// Sender part
//	
//	@Bean
//	Queue queue() {
//		return new Queue(ROUTING_KEY, true);
//	}
//
//	@Bean
//	TopicExchange exchange() {
//		return new TopicExchange("my_queue_exchange");
//	}
//
//	@Bean
//	Binding binding(Queue queue, TopicExchange exchange) {
//		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
//	}
//	
//	// Listener Part
//	
//	@Bean
//	ConnectionFactory connectionFactory() {
//		CachingConnectionFactory factory = new CachingConnectionFactory("localhost");
//		factory.setUsername("admin");
//		factory.setPassword("admin");
//		
//		return factory;
//	}
//	
//	@Bean
//	SimpleMessageListenerContainer conatainer(ConnectionFactory connectionFactory,
//			MessageListenerAdapter messageListener) {
//		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//		container.setQueueNames(ROUTING_KEY);
//		container.setConnectionFactory(connectionFactory);
//		container.setMessageListener(messageListener);
//		
//		return container;
//	}
//	
//	@Bean
//	MessageListenerImpl messageListenerImpl() {
//		return new MessageListenerImpl();
//	}
//	
//	
//	@Bean
//	MessageListenerAdapter listenerAdapter(MessageListner listner) {
//		return new MessageListenerAdapter(listner, "getMessage");
//	}
//
//}








