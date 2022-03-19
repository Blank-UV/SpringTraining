package com.learn.service;

import com.learn.model.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class OnlineShop {

    public static void main(String[] args) {

        AbstractApplicationContext context =
                new ClassPathXmlApplicationContext("/META-INF/shop.xml", OnlineShop.class);

        Shop shop = (Shop) context.getBean("shop");

        final Order order = createOrder();

        System.out.println("*** [OnlineShop] ****");
        shop.placeOrder(order);

        context.close();
    }

    private static Order createOrder() {
        Book book = new Book("Of Mice & Men", "Bluebird", new BigDecimal("100"), 1988, "John Steinbeck");
        MusicCD cd = new MusicCD("Off the Wall", "publisher", new BigDecimal("100"), 1975, "Michael Jackson");
        Software macos = new Software("Mavericks", "publisher", new BigDecimal("100"), 2014, "10.9.3");

        OrderItem bookItems = new OrderItem(book);

        OrderItem cdItems = new OrderItem(cd);

        OrderItem swItems = new OrderItem(macos);

        final List<OrderItem> orderItems = new ArrayList<OrderItem>();
        orderItems.add(bookItems);
        orderItems.add(cdItems);
        orderItems.add(swItems);

        Order order = new Order();
        order.setOrderItems(orderItems);
        System.out.println("Order: " + order);
		System.out.println("Total : "+ order.getTotalCost());

        return order;
    }

}
