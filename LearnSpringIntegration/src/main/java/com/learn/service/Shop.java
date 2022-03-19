package com.learn.service;

import com.learn.model.Order;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

public interface Shop {

    @Gateway(requestChannel = "ordersChannel")
    void placeOrder(Order order);
}
