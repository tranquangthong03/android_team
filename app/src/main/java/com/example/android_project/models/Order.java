package com.example.android_project.models;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private String orderId;
    private double totalPrice;
    private String paymentMethod; // "Momo", "Bank", "Cash"
    private String date;
    private String status; // "Success", "Pending"
    private List<Food> items; // Danh sách món ăn

    // Nếu bạn muốn quản lý User, thêm trường userId
    private String userId;

    public Order() { } // Constructor rỗng cho Firebase

    public Order(String orderId, double totalPrice, String paymentMethod, String date, String status, List<Food> items) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.date = date;
        this.status = status;
        this.items = items;
    }

    // Getter & Setter
    public String getOrderId() { return orderId; }
    public double getTotalPrice() { return totalPrice; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getDate() { return date; }
    public String getStatus() { return status; }
    public List<Food> getItems() { return items; }
}