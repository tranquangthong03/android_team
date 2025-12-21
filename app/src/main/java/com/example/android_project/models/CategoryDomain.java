package com.example.android_project.models;

<<<<<<< HEAD
import java.io.Serializable; // Thêm Serializable để truyền qua Intent nếu cần

public class CategoryDomain implements Serializable {
    private String name;
    private String imagePath;
    private String id; // THÊM BIẾN NÀY (Key liên kết)

    public CategoryDomain() { }

=======
import java.io.Serializable;
// Nếu muốn dùng @DocumentId để tự lấy ID từ Firestore thì uncomment dòng dưới
// import com.google.firebase.firestore.DocumentId; 

public class CategoryDomain implements Serializable {
    private String name;       // Tên field phải trùng với field trên Firebase (ví dụ: "name")
    private String imagePath;  // Tên field phải trùng với field trên Firebase (ví dụ: "imagePath")
    private String id;         // Dùng để chứa ID danh mục (để lọc món ăn)

    // 1. Constructor rỗng (BẮT BUỘC cho Firebase)
    public CategoryDomain() {
    }

    // 2. Constructor đầy đủ
>>>>>>> huuhung
    public CategoryDomain(String name, String imagePath, String id) {
        this.name = name;
        this.imagePath = imagePath;
        this.id = id;
    }

<<<<<<< HEAD
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    // Getter & Setter cho ID
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
=======
    // 3. Getter và Setter
    // Adapter gọi .getName() thì ở đây phải là getName()
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Adapter gọi .getImagePath() thì ở đây phải là getImagePath()
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
>>>>>>> huuhung
}