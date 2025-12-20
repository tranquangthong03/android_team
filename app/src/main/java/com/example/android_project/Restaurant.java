package com.example.android_project; // Đảm bảo đúng tên package của bạn

public class Restaurant {
    private String name;
    private Double rating;      // Firebase lưu number, ta dùng Double hoặc float
    private String deliveryTime;
    private String deliveryFee;
    private String description;
    private String imagePath;

    // 1. Constructor rỗng (BẮT BUỘC để Firebase hoạt động)
    public Restaurant() { }

    // 2. Constructor đầy đủ (để sau này ta tự tạo dữ liệu nếu cần)
    public Restaurant(String name, Double rating, String deliveryTime, String deliveryFee, String description, String imagePath) {
        this.name = name;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.deliveryFee = deliveryFee;
        this.description = description;
        this.imagePath = imagePath;
    }

    // 3. Getter (Để lấy dữ liệu ra hiển thị)
    public String getName() { return name; }
    public Double getRating() { return rating; }
    public String getDeliveryTime() { return deliveryTime; }
    public String getDeliveryFee() { return deliveryFee; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
}