package com.learn.service;

import com.learn.model.Book;
import com.learn.model.MusicCD;
import com.learn.model.OrderItem;
import com.learn.model.Software;

public class OrderItemRouter {

    public String routeOrder(OrderItem orderItem) {
        System.out.println("Inside routeOrder");
        String channel = "";
        if (isBook(orderItem)) {
            System.out.println("book");
            channel = "bookItemsChannel";
        } else if (isMusic(orderItem)) {
            System.out.println("music");
            channel = "softwareItemsChannel";
        } else if (isSoftware(orderItem)) {
            System.out.print("software");
            channel = "softwareItemsChannel";
        }
        return channel;
    }

    private Boolean isBook(OrderItem orderItem) {
        return orderItem.getItem() instanceof Book;
    }

    private Boolean isMusic(OrderItem orderItem) {
        return orderItem.getItem() instanceof MusicCD;
    }

    private Boolean isSoftware(OrderItem orderItem) {
        return orderItem.getItem() instanceof Software;
    }
}
