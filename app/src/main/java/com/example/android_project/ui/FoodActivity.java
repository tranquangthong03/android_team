package com.example.android_project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.R;
import com.example.android_project.data.CartManager;
import com.example.android_project.models.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity implements FoodAdapter.FoodListener {

    private RecyclerView rvPopular;
    private FoodAdapter adapter;
    private List<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buger_food); // Layout mới đã cập nhật

        initView();
        setupData();
        setupRecyclerView();
    }

    private void initView() {
        rvPopular = findViewById(R.id.rvPopular);
        ImageButton btnBack = findViewById(R.id.btnBackMain);
        
        btnBack.setOnClickListener(v -> finish());
    }

    private void setupData() {
        foodList = new ArrayList<>();
        
        // Dùng ảnh có sẵn trong drawable của bạn (sample_burger)
        int imgBurger = R.drawable.sample_burger; 

        // Thêm dữ liệu giả lập (Hardcode)
        foodList.add(new Food("1", "Cheese Burger", "Rose Garden", 40, imgBurger));
        foodList.add(new Food("2", "Smokin' Burger", "Cafenio Resto", 60, imgBurger));
        foodList.add(new Food("3", "Buffalo Burger", "Kaji Kitchen", 75, imgBurger));
        foodList.add(new Food("4", "Big Mac", "McDonald's", 95, imgBurger));
        foodList.add(new Food("5", "Spicy Chicken", "KFC", 55, imgBurger)); // Thêm món để thấy danh sách cuộn
        foodList.add(new Food("6", "Fish Burger", "Lotteria", 45, imgBurger));
    }

    private void setupRecyclerView() {
        // Sử dụng Grid 2 cột
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvPopular.setLayoutManager(layoutManager);
        
        // Quan trọng: Set setHasFixedSize(true) để tối ưu hiệu năng
        rvPopular.setHasFixedSize(true);
        
        adapter = new FoodAdapter(foodList, this);
        rvPopular.setAdapter(adapter);
    }

    @Override
    public void onFoodClick(Food food) {
        Intent intent = new Intent(FoodActivity.this, FoodDetailActivity.class);
        intent.putExtra(FoodDetailActivity.EXTRA_FOOD, food);
        startActivity(intent);
    }

    @Override
    public void onAddToCartClick(Food food) {
        CartManager.addToCart(food, 1);
        Toast.makeText(this, "Đã thêm " + food.getName() + " vào giỏ", Toast.LENGTH_SHORT).show();
    }
}