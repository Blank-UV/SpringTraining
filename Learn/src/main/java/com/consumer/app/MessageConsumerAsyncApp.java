package com.consumer.app;

import com.consumer.config.ConsumerAppConfig;
import com.epam.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class MessageConsumerAsyncApp {

    public static void main(String[] args) {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(ConsumerAppConfig.class);
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            ctx.close();
        }
    }
}
