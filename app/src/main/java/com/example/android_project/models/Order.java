package com.example.android_project.models;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private String orderId;
    private double totalPrice;
    private String paymentMethod;
    private String date;
    private String status;
    private List<Food> items;
    private String userId;

    public Order() { }

    public Order(String orderId, double totalPrice, String paymentMethod, String date, String status, List<Food> items, String userId) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.date = date;
        this.status = status;
        this.items = items;
        this.userId = userId;
    }

    public String getOrderId() { return orderId; }
    public double getTotalPrice() { return totalPrice; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getDate() { return date; }
    public String getStatus() { return status; }
    public List<Food> getItems() { return items; }
    public String getUserId() { return userId; }
}