package com.consumer.config;

import com.consumer.service.MessageReceiverAsync;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import javax.jms.ConnectionFactory;

@Configuration
public class MessagingConfiguration1 {

    public static final String DEFAULT_BROKER_URL = "tcp://EPINHYDW0C47:61616";
    public static final String MESSAGE_QUEUE = "message-queue";

    @Autowired
    MessageReceiverAsync messageReceiverAsync;

    @Bean
    public ConnectionFactory connectionFactory()
    {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate()
    {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory());
        jmsTemplate.setDefaultDestinationName(MESSAGE_QUEUE);
        return jmsTemplate;
    }

    @Bean
    public MessageConverter messageConverter()
    {
        return new SimpleMessageConverter();
    }

    @Bean
    public MessageListenerContainer getCotainer()
    {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setDestinationName(MESSAGE_QUEUE);
        container.setMessageListener(messageReceiverAsync);
        return container;
    }
}
