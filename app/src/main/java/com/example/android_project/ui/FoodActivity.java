package com.example.android_project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        setContentView(R.layout.buger_food); // Kết nối với layout vừa tạo

        initViews();
        setupData();
        setupRecyclerView();
    }

    private void initViews() {
        rvPopular = findViewById(R.id.rvPopular);
        ImageButton btnBack = findViewById(R.id.btnBackMain);
        
        // Sự kiện nút Back: Quay về Home
        btnBack.setOnClickListener(v -> finish());
    }

    private void setupData() {
        foodList = new ArrayList<>();
        
        // Dữ liệu mẫu (Bạn có thể thay hình khác nếu muốn)
        int imgBurger = R.drawable.sample_burger; 

        foodList.add(new Food("1", "Cheese Burger", "Rose Garden", 40, imgBurger));
        foodList.add(new Food("2", "Smokin' Burger", "Cafenio Resto", 60, imgBurger));
        foodList.add(new Food("3", "Buffalo Burger", "Kaji Kitchen", 75, imgBurger));
        foodList.add(new Food("4", "Big Mac", "McDonald's", 95, imgBurger));
        foodList.add(new Food("5", "Chicken Spicy", "KFC", 50, imgBurger));
        foodList.add(new Food("6", "Double Cheese", "Lotteria", 65, imgBurger));
    }

    private void setupRecyclerView() {
        // Cấu hình Grid Layout 2 cột
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvPopular.setLayoutManager(layoutManager);
        
        // Tối ưu hiệu năng
        rvPopular.setHasFixedSize(true);
        rvPopular.setNestedScrollingEnabled(false); // Để scroll mượt cùng NestedScrollView
        
        adapter = new FoodAdapter(foodList, this);
        rvPopular.setAdapter(adapter);
    }

    // Xử lý khi click vào từng món ăn
    @Override
    public void onFoodClick(Food food) {
        Intent intent = new Intent(FoodActivity.this, FoodDetailActivity.class);
        intent.putExtra(FoodDetailActivity.EXTRA_FOOD, food);
        startActivity(intent);
    }

    // Xử lý khi click nút Add (+)
    @Override
    public void onAddToCartClick(Food food) {
        CartManager.addToCart(food, 1);
        Toast.makeText(this, "Đã thêm " + food.getName() + " vào giỏ", Toast.LENGTH_SHORT).show();
    }
}