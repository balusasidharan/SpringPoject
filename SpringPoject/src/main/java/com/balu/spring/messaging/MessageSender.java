package com.balu.spring.messaging;



import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
 
import com.balu.spring.model.Person;

 
@Component
public class MessageSender {
 
    @Autowired
    JmsTemplate jmsTemplate;
    static final Logger LOG = LoggerFactory.getLogger(MessageSender.class);
    public void sendMessage(final Person person) {
       
        jmsTemplate.send(new MessageCreator(){
                @Override
                public Message createMessage(Session session) throws JMSException{
                    ObjectMessage objectMessage = session.createObjectMessage(person);
                    return objectMessage;
                }
            });
        LOG.info("AMQ:sending message: ",person.toString());
    }
 
}