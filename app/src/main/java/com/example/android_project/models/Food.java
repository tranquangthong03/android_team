package com.example.android_project.models;

import java.io.Serializable;

public class Food implements Serializable {
    private String id;              // ID của document Firestore
    private String name;            // Tên món
    private String restaurantName;  // Tên nhà hàng
    private double price;           // Giá tiền
    private String imagePath;       // URL ảnh
    private String categoryId;      // ID danh mục (Dùng để lọc)
    private double rating;          // Điểm đánh giá (Ví dụ: 4.5)
    private String description;     // Mô tả món ăn (Thêm vào để hiển thị chi tiết nếu cần)

    // --- 1. Constructor Rỗng (BẮT BUỘC để Firestore map dữ liệu) ---
    public Food() {
    }

    // --- 2. Constructor Đầy đủ ---
    public Food(String id, String name, String restaurantName, double price, String imagePath, String categoryId, double rating, String description) {
        this.id = id;
        this.name = name;
        this.restaurantName = restaurantName;
        this.price = price;
        this.imagePath = imagePath;
        this.categoryId = categoryId;
        this.rating = rating;
        this.description = description;
    }

    // --- 3. Getter & Setter ---
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

    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}