package com.example.android_project.models;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private String name;
    private double rating;
    private String deliveryTime;
    private String deliveryFee;
    private String description;
    private String imagePath;

    // Constructor rỗng (Bắt buộc cho Firebase)
    public Restaurant() { }

    public Restaurant(String name, double rating, String deliveryTime, String deliveryFee, String description, String imagePath) {
        this.name = name;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.deliveryFee = deliveryFee;
        this.description = description;
        this.imagePath = imagePath;
    }

    // Getter & Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getDeliveryTime() { return deliveryTime; }
    public void setDeliveryTime(String deliveryTime) { this.deliveryTime = deliveryTime; }

    public String getDeliveryFee() { return deliveryFee; }
    public void setDeliveryFee(String deliveryFee) { this.deliveryFee = deliveryFee; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}