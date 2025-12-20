package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.models.CategoryDomain;
import com.example.android_project.models.Food;
import com.example.android_project.ui.CategoryAdapter;
import com.example.android_project.ui.FoodActivity; // Trang "See All"
import com.example.android_project.ui.FoodAdapter;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvPopularHome, rvCategories;
    private TextView txtGreeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        setupUserData();
        
        // 1. Setup Danh mục (Categories)
        setupCategories();
        
        // 2. Setup Món ăn phổ biến (Popular)
        setupPopularData();
        
        // 3. Setup Sự kiện Click
        setupEvents();
    }

    private void initViews() {
        rvPopularHome = findViewById(R.id.rvPopularHome);
        rvCategories = findViewById(R.id.rvCategories);
        txtGreeting = findViewById(R.id.txtGreeting);
    }

    private void setupUserData() {
        String username = getIntent().getStringExtra("USER_NAME");
        txtGreeting.setText(username != null ? "Hi, " + username + "!" : "Hi, User!");
    }

    // --- PHẦN 1: ĐỔ DỮ LIỆU CATEGORY ---
    private void setupCategories() {
        ArrayList<CategoryDomain> categoryList = new ArrayList<>();
        // Tên ảnh phải trùng với tên file trong drawable (vd: pizza_image.png -> "pizza_image")
        categoryList.add(new CategoryDomain("Pizza", "pizza_image"));
        categoryList.add(new CategoryDomain("Burger", "burger_image"));
        categoryList.add(new CategoryDomain("Chicken", "sample_burger")); // Tạm dùng ảnh này
        categoryList.add(new CategoryDomain("Drink", "burger_image"));

        CategoryAdapter adapter = new CategoryAdapter(categoryList);
        rvCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCategories.setAdapter(adapter);
    }

    // --- PHẦN 2: ĐỔ DỮ LIỆU POPULAR ---
    private void setupPopularData() {
        ArrayList<Food> foodList = new ArrayList<>();
        int img = R.drawable.sample_burger;
        
        // Ở trang Home, ta để list ngang cho đẹp
        foodList.add(new Food("1", "Cheese Burger", "Rose Garden", 45, img));
        foodList.add(new Food("2", "Pizza Hut", "Pizza Hut", 80, R.drawable.pizza_image));
        foodList.add(new Food("3", "Fried Chicken", "KFC", 30, img));

        FoodAdapter adapter = new FoodAdapter(foodList, new FoodAdapter.FoodListener() {
            @Override
            public void onFoodClick(Food food) { } // Xử lý mở chi tiết sau
            @Override
            public void onAddToCartClick(Food food) {
                Toast.makeText(HomeActivity.this, "Đã thêm vào giỏ", Toast.LENGTH_SHORT).show();
            }
        });

        rvPopularHome.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvPopularHome.setAdapter(adapter);
    }

    // --- PHẦN 3: SỰ KIỆN SEE ALL ---
    private void setupEvents() {
        TextView txtSeeAll = findViewById(R.id.txtSeeAllCategories);
        txtSeeAll.setOnClickListener(v -> {
            // Chuyển sang FoodActivity (Trang này có Grid món ăn + Nhà hàng bên dưới)
            Intent intent = new Intent(HomeActivity.this, FoodActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnCart).setOnClickListener(v -> {
            // Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            // startActivity(intent);
        });
    }
}