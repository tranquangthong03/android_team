package com.example.android_project;

public class Restaurant {
    private String name;
    private String imagePath; // Firebase lưu String (URL)
    private Double rating;
    private String deliveryTime;
    private String deliveryFee;
    private String description;

    // 1. Constructor rỗng (BẮT BUỘC cho Firebase)
    public Restaurant() { }

    // 2. Constructor đầy đủ
    public Restaurant(String name, String imagePath, Double rating, String deliveryTime, String deliveryFee, String description) {
        this.name = name;
        this.imagePath = imagePath;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.deliveryFee = deliveryFee;
        this.description = description;
    }

    // 3. Getters
    public String getName() { return name; }
    public String getImagePath() { return imagePath; }
    public Double getRating() { return rating; }
    public String getDeliveryTime() { return deliveryTime; }
    public String getDeliveryFee() { return deliveryFee; }
    public String getDescription() { return description; }
}