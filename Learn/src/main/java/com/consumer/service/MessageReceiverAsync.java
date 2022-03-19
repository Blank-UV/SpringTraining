package com.consumer.service;

import com.epam.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@Component
public class MessageReceiverAsync implements MessageListener {

    @Autowired
    MessageConverter messageConverter;

    @Override
    public void onMessage(Message message) {
        try{
            Product product = (Product) messageConverter.fromMessage(message);
            System.out.println("--Inside Message Listener ---");
            System.out.println(product);
        }
        catch(JMSException e){
            e.printStackTrace();
        }
    }
}
