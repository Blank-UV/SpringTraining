package com.epam.service;

import com.epam.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Session;

@Component
public class MessageSender {

    @Autowired
    JmsTemplate jmsTemplate;

    public void sendMessage(final Product product) {
        jmsTemplate.send((session) ->
                session.createObjectMessage(product));
    }
}
