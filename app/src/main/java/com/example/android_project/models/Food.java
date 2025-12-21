package com.example.android_project.models;

import java.io.Serializable;

public class Food implements Serializable {
    private String id;
    private String name;
    private String restaurantName;
    private double price;
    private String imagePath;
    
    // --- THÊM BIẾN NÀY ĐỂ LỌC ---
    private String categoryId; 

    public Food() { }

    // Constructor cập nhật thêm categoryId
    public Food(String id, String name, String restaurantName, double price, String imagePath, String categoryId) {
        this.id = id;
        this.name = name;
        this.restaurantName = restaurantName;
        this.price = price;
        this.imagePath = imagePath;
        this.categoryId = categoryId;
    }

    // Getter & Setter cho categoryId
    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }

    // ... Các getter/setter cũ giữ nguyên
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}