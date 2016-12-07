package com.balu.spring.messaging;



import javax.jms.JMSException;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.balu.spring.model.Person;

 
@Component
public class MessageReceiver {
    static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);
 
    private static final String ORDER_RESPONSE_QUEUE = "order-queue";
  
     
     
    @JmsListener(destination = ORDER_RESPONSE_QUEUE)
    public void receiveMessage(final Message<Person> message) throws JMSException {
        LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
        MessageHeaders headers =  message.getHeaders();
        LOG.info("Application : headers received : {}", headers);
         Person person = message.getPayload();
         LOG.info("Application : message recieved :{}",person);
      
        LOG.info("Application : response received : {}", message.getPayload());
         
         
        LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}