package com.example.android_project.models;

import java.io.Serializable; // Thêm Serializable để truyền qua Intent nếu cần

public class CategoryDomain implements Serializable {
    private String name;
    private String imagePath;
    private String id; // THÊM BIẾN NÀY (Key liên kết)

    public CategoryDomain() { }

    public CategoryDomain(String name, String imagePath, String id) {
        this.name = name;
        this.imagePath = imagePath;
        this.id = id;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    // Getter & Setter cho ID
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}