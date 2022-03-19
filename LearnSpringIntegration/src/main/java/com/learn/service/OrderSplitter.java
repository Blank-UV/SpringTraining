package com.learn.service;

import com.learn.model.Order;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

public class OrderSplitter extends AbstractMessageSplitter{

    @Override
    protected Object splitMessage(Message<?> message) {
       // System.out.println("*** [OrderSplitter] splitting Order into it's constituent OrderItems : number of OrderItems: "+ ((Order)message.getPayload()).getOrderItems().size() + " ****");
        System.out.println("Inside ordersplitter");
        System.out.println(((Order)message.getPayload()).getOrderItems());
        return ((Order)message.getPayload()).getOrderItems();
    }

}
