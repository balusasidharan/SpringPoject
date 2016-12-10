package com.balu.spring.config;

import java.util.Arrays;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.AmqpAdmin;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

//import com.rabbitmq.client.ConnectionFactory;



 
@Configuration
public class MessagingConfiguration {
 
    private static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";
     
    private static final String ORDER_QUEUE = "order-queue";
    private static final Logger LOG = LoggerFactory.getLogger(MessagingConfiguration.class);
    @Bean
    public ActiveMQConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
        connectionFactory.setTrustedPackages(Arrays.asList("com.balu.spring"));
        return connectionFactory;
    }
     
    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setDefaultDestinationName(ORDER_QUEUE);
        return template;
    }
    
    
//    @Bean
//    public CachingConnectionFactory connectionFactory() {
//        return new CachingConnectionFactory("localhost");
//        
//    }
//
//    @Bean
//    public AmqpAdmin amqpAdmin() {
//        return new RabbitAdmin(connectionFactory());
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate() {
//        return new RabbitTemplate(connectionFactory());
//    }
//
//    @Bean
//    public Queue myQueue() {
//       return new Queue("myrabbitqueue");
//    }
//    
//    
//    @Bean
//    TopicExchange exchange() {
//        return new TopicExchange("spring-boot-exchange");
//    }
//
//    @Bean
//    Binding binding(Queue queue, TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with("myrabbitqueue");
//    }
//
//    @Bean
//    SimpleMessageListenerContainer container(CachingConnectionFactory connectionFactory,
//            MessageListenerAdapter listenerAdapter) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames("myrabbitqueue");
//        container.setMessageListener(listenerAdapter);
//        return container;
//    }
//Camel context
    
    @Bean
    public CamelContext camel() throws Exception{   
      CamelContext camelContext = new DefaultCamelContext(); 
      camelContext.addComponent("jms",JmsComponent.jmsComponentAutoAcknowledge(connectionFactory())) ;
      camelContext.addRoutes(new RouteBuilder(){

		@Override
		public void configure() throws Exception {
			
		from("jms:order-queue").process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				LOG.info("Message received fom JMS Queue1  :"+ exchange.getFromEndpoint().getEndpointUri()+" ->"+exchange.getIn().getBody());
			}
		}).to("jms:order-response-queue");
		
		from("jms:order-response-queue").process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				LOG.info("Message received fom JMS Queue2  :"+ exchange.getFromEndpoint().getEndpointUri()+" ->"+exchange.getIn().getBody());
				
			}
		}).to("jms:order-queue");
		}
		
    	  
      });
      camelContext.start();
      return camelContext;      
    }
    
    
    
    
    
}