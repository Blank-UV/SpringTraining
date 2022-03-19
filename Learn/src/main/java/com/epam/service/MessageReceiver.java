package com.epam.service;



import com.epam.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Component
public class MessageReceiver {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    MessageConverter messageConverter;

    public Product receiveMessage() {
        try {
            Message message = jmsTemplate.receive();
            return (Product) messageConverter.fromMessage(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
