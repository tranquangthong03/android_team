package com.example.android_project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.R;
import com.example.android_project.data.CartManager;
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
    private String categoryId;
    private String categoryName;
    private TextView txtTitle;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food); // Đảm bảo bạn đã có layout này

        // 1. Nhận dữ liệu từ HomeActivity
        categoryId = getIntent().getStringExtra("CategoryId");
        categoryName = getIntent().getStringExtra("CategoryName");

        initViews();
        setupEvents();
        loadFoodsFromFirestore();
    }

    private void initViews() {
        // Ánh xạ View (kiểm tra ID trong file xml của bạn)
        recyclerViewFood = findViewById(R.id.recyclerViewFood);
        // txtTitle = findViewById(R.id.txtTitle); // Nếu có tiêu đề
        // btnBack = findViewById(R.id.btnBack);   // Nếu có nút back

        // Setup RecyclerView dạng lưới (2 cột)
        recyclerViewFood.setLayoutManager(new GridLayoutManager(this, 2));

        foodList = new ArrayList<>();
        // Sử dụng FoodAdapter (với layout item to hoặc nhỏ tùy bạn chọn)
        adapter = new FoodAdapter(this, foodList, R.layout.item_food, new FoodAdapter.FoodListener() {
            @Override
            public void onFoodClick(Food food) {
                Intent intent = new Intent(FoodActivity.this, FoodDetailActivity.class);
                intent.putExtra(FoodDetailActivity.EXTRA_FOOD, food);
                startActivity(intent);
            }

            @Override
            public void onAddToCartClick(Food food) {
                CartManager.addToCart(food, 1);
                Toast.makeText(FoodActivity.this, "Đã thêm vào giỏ!", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewFood.setAdapter(adapter);

        // Set tiêu đề nếu có
        if (categoryName != null && txtTitle != null) {
            txtTitle.setText(categoryName);
        }
    }

    private void setupEvents() {
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private void loadFoodsFromFirestore() {
        db = FirebaseFirestore.getInstance();
        Query query;

        // LOGIC LỌC QUAN TRỌNG
        if (categoryId != null && !categoryId.isEmpty()) {
            // Nếu có ID danh mục -> Lọc các món có categoryId trùng khớp
            query = db.collection("foods").whereEqualTo("categoryId", categoryId);
        } else {
            // Nếu không có ID (xem tất cả) -> Lấy hết
            query = db.collection("foods");
        }

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                foodList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Food food = document.toObject(Food.class);
                    food.setId(document.getId()); // Lưu ID document
                    foodList.add(food);
                }
                adapter.notifyDataSetChanged();

                if (foodList.isEmpty()) {
                    Toast.makeText(FoodActivity.this, "Chưa có món nào trong danh mục này", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(FoodActivity.this, "Lỗi tải dữ liệu: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}