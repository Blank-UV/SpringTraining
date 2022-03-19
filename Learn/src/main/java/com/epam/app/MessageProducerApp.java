package com.epam.app;

import com.epam.config.AppConfig;
import com.epam.model.Product;
import com.epam.service.MessageSender;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class MessageProducerApp {

    public static void main(String args[]) {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        MessageSender messageSender = ctx.getBean(MessageSender.class);
        Product product = new Product();
        product.setProductId(100);
        product.setName("Yuvaraj");
        product.setQuantity(10);
        messageSender.sendMessage(product);
        System.out.println("Message has been sent");
        ctx.registerShutdownHook() ;
        ctx.close();

    }
}
