package com.example.fastbite.models;

import java.io.Serializable;

public class Food implements Serializable {

    private String id;
    private String name;
    private String restaurant;
    private double price;
    private int imageResId;

    public Food(String id, String name, String restaurant, double price, int imageResId) {
        this.id = id;
        this.name = name;
        this.restaurant = restaurant;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public double getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
