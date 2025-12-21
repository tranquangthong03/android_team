package com.example.android_project.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.R;
import com.example.android_project.models.Food;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFood;
    private FoodAdapter adapter;
    private ArrayList<Food> foodList;
    private FirebaseFirestore db;
    private String categoryId; // Biến lưu ID danh mục được chọn
    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food); // Đảm bảo file xml này có RecyclerView id là recyclerViewFood

        // 1. Nhận dữ liệu từ màn hình Home gửi sang
        categoryId = getIntent().getStringExtra("CategoryId");
        categoryName = getIntent().getStringExtra("CategoryName");

        // (Tuỳ chọn) Đổi tiêu đề app bar thành tên danh mục
        if (categoryName != null) {
            getSupportActionBar().setTitle(categoryName);
        }

        initView();
        loadFoodsFromFirestore();
    }

    private void initView() {
        recyclerViewFood = findViewById(R.id.recyclerViewFood); // Check lại ID trong xml của bạn
        recyclerViewFood.setLayoutManager(new GridLayoutManager(this, 2)); // Hiển thị 2 cột
        
        foodList = new ArrayList<>();
        // Lưu ý: FoodAdapter của bạn cần constructor phù hợp. 
        // Nếu adapter của bạn khác, hãy sửa dòng dưới cho khớp.
        adapter = new FoodAdapter(this, foodList); 
        recyclerViewFood.setAdapter(adapter);
    }

    private void loadFoodsFromFirestore() {
        db = FirebaseFirestore.getInstance();
        Query query;

        if (categoryId != null && !categoryId.isEmpty()) {
            // LỌC: Chỉ lấy món ăn có categoryId trùng với cái vừa bấm
            query = db.collection("foods").whereEqualTo("categoryId", categoryId);
        } else {
            // Nếu không có categoryId (trường hợp xem tất cả) -> Lấy hết
            query = db.collection("foods");
        }

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                foodList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Food food = document.toObject(Food.class);
                    // Gán ID của document vào object để dùng sau này (nếu cần)
                    food.setId(document.getId());
                    foodList.add(food);
                }
                adapter.notifyDataSetChanged();

                // Kiểm tra nếu danh sách rỗng
                if (foodList.isEmpty()) {
                    Toast.makeText(FoodActivity.this, "Chưa có món nào trong danh mục này", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(FoodActivity.this, "Lỗi khi tải dữ liệu: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}