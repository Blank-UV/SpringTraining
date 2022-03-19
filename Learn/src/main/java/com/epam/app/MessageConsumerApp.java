package com.epam.app;

import com.epam.config.AppConfig;
import com.epam.model.Product;
import com.epam.service.MessageReceiver;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class MessageConsumerApp {

    public static void main(String args[]) {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        MessageReceiver messageReceiver = ctx.getBean(MessageReceiver.class);
        Product product = messageReceiver.receiveMessage();
        System.out.println("Message Received : " + product);
        ctx.registerShutdownHook() ;
    }
}
