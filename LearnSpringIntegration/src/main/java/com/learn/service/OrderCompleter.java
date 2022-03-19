package com.learn.service;

import com.learn.model.Order;
import com.learn.model.OrderItem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Aggregator;

import java.util.List;

public class OrderCompleter {

    public Order prepareDelivery(List<OrderItem> orderItems) {
        System.out.println("Inside order completer --- aggregator");
        final Order order = new Order();
        order.setOrderItems(orderItems);

      //  System.out.println("*** [OrderCompleter] CompletedOrder Discounted cost: " + order.getTotalDiscountedCost() +" ****");

        return order;
    }
}
