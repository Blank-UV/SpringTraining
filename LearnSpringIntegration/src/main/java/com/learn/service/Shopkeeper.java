package com.learn.service;


import com.learn.model.Book;
import com.learn.model.MusicCD;
import com.learn.model.OrderItem;
import com.learn.model.Software;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

public class Shopkeeper {

    private static final BigDecimal BOOK_DISCOUNT = new BigDecimal(0.05);
    private static final BigDecimal MUSIC_DISCOUNT = new BigDecimal(0.10);
    private static final BigDecimal SOFTWARE_DISCOUNT = new BigDecimal(0.15);

    public OrderItem processBooks(OrderItem bookOrderItem){
        System.out.println("Inside shopkepper -- service activator -- books");
        System.out.println("*** [Shopkeeper] processing book : "+ bookOrderItem.getItem().getTitle() +" ****");

        final BigDecimal finalPrice = calculateDiscountedPrice(bookOrderItem, BOOK_DISCOUNT);

        bookOrderItem.setDiscountedPrice(finalPrice);

        return bookOrderItem;
    }

    public  OrderItem processMusic(OrderItem musicOrderItem){
        System.out.println("Inside shopkepper -- service activator -- music");
        System.out.println("*** [Shopkeeper] processing music : "+ musicOrderItem.getItem().getTitle() +" ****");

        final BigDecimal finalPrice = calculateDiscountedPrice(musicOrderItem, MUSIC_DISCOUNT);

        musicOrderItem.setDiscountedPrice(finalPrice);

        return musicOrderItem;
    }

    public  OrderItem processSoftware(OrderItem softwareOrderItem){

        System.out.println("Inside shopkepper -- service activator -- software");
        System.out.println("*** [Shopkeeper] processing software : "+ softwareOrderItem.getItem().getTitle() +" ****");

        final BigDecimal finalPrice = calculateDiscountedPrice(softwareOrderItem, SOFTWARE_DISCOUNT);

        softwareOrderItem.setDiscountedPrice(finalPrice);

        return softwareOrderItem;
    }

    private BigDecimal calculateDiscountedPrice(final OrderItem orderItem, final BigDecimal discount) {

        final BigDecimal discountedPrice =round(orderItem.getTotalPrice().multiply(discount));
        final BigDecimal finalPrice = round(orderItem.getTotalPrice().subtract(discountedPrice));

//        System.out.println("item (" + getItemType(orderItem) + ") " +
//                "item price: " + orderItem.getItem().getPrice() +
//                " discount: " + discountedPrice +
//                " final price: " + finalPrice);

        return finalPrice;
    }

    private String getItemType(final OrderItem orderItem) {

        String type = "";

        if(orderItem.getItem() instanceof Book) {
            type = "Book: " + orderItem.getItem().getTitle();
        }
        else if(orderItem.getItem() instanceof MusicCD) {
            type = "MusicCD: " + orderItem.getItem().getTitle();
        }
        else if(orderItem.getItem() instanceof Software) {
            type = "Software: " + orderItem.getItem().getTitle();
        }

        return type;
    }

    private BigDecimal round(final BigDecimal value) {
        return value.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
}
