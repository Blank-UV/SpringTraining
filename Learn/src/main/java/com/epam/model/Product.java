package com.epam.model;

import java.io.Serializable;

public class Product implements Serializable {

    int productId;
    String name;
    int quantity;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", name='" + name + '\'' + ", quantity=" + quantity + '}';
    }
}
