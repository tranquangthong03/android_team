package com.example.android_project.models;

import java.io.Serializable;

public class CategoryDomain implements Serializable {
    private String name;
    private String imagePath;
    private String id; // Biến quan trọng để lọc món ăn

    // 1. Constructor rỗng (BẮT BUỘC để Firebase map dữ liệu)
    public CategoryDomain() {
    }

    // 2. Constructor đầy đủ (Dùng khi cần tạo đối tượng thủ công)
    public CategoryDomain(String name, String imagePath, String id) {
        this.name = name;
        this.imagePath = imagePath;
        this.id = id;
    }

    // 3. Getter và Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // Getter & Setter cho ID (Khắc phục lỗi 'Cannot resolve method setId')
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}