package com.example.android_project.models;

import java.io.Serializable;

public class Food implements Serializable {
    private String id;
    private String name;
    private String restaurantName; // Đổi từ 'restaurant' sang 'restaurantName' cho khớp Firebase
    private double price;
    private String imagePath;      // Đổi từ 'int' sang 'String' để chứa URL

    // Constructor rỗng (BẮT BUỘC cho Firebase)
    public Food() { }

    // Constructor đầy đủ
    public Food(String id, String name, String restaurantName, double price, String imagePath) {
        this.id = id;
        this.name = name;
        this.restaurantName = restaurantName;
        this.price = price;
        this.imagePath = imagePath;
    }

    // Getter & Setter
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