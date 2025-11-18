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

        // DÙNG ĐÚNG layout này
        setContentView(R.layout.buger_food);

        rvPopular = findViewById(R.id.rvPopular);
        ImageButton btnBack = findViewById(R.id.btnBackMain);
        btnBack.setOnClickListener(v -> {
            // quay lại màn trước (HomeActivity)
            finish();
            // hoặc: onBackPressed();
        });
        setupData();
        setupRecyclerView();
    }

    private void setupData() {
        foodList = new ArrayList<>();

        // Nếu bạn CHƯA có sample_burger, tạm dùng ic_launcher_foreground
        int img = R.drawable.sample_burger; // hoặc R.drawable.ic_launcher_foreground

        foodList.add(new Food("1", "Burger Bistro", "Rose Garden", 40, img));
        foodList.add(new Food("2", "Smokin' Burger", "Cafenio Restaurant", 60, img));
        foodList.add(new Food("3", "Buffalo Burgers", "Kaji Firm Kitchen", 75, img));
        foodList.add(new Food("4", "Bullseye Burgers", "Kabab Restaurant", 94, img));
    }

    private void setupRecyclerView() {
        rvPopular.setLayoutManager(new GridLayoutManager(this, 2));
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
        Toast.makeText(this,
                "Đã thêm " + food.getName() + " vào giỏ",
                Toast.LENGTH_SHORT).show();
    }
}
