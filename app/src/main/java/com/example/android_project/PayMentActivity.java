package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.data.CartManager;
import com.example.android_project.models.CartItem;
import com.example.android_project.models.Food;
import com.example.android_project.ui.PaymentFoodAdapter;

import java.util.ArrayList;
import java.util.List;

public class PayMentActivity extends AppCompatActivity {

    private ImageView btnBack;
    private Button btnPayConfirm;
    private RecyclerView rvFoods;
    private PaymentFoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_activity);

        // Initialize views
        btnBack = findViewById(R.id.btnBack);
        btnPayConfirm = findViewById(R.id.btnPayConfirm);
        rvFoods = findViewById(R.id.rvFoods);

        // Setup RecyclerView
        setupRecyclerView();

        // Back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Pay & Confirm button
        btnPayConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayMentActivity.this, PayCongratActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupRecyclerView() {
        // Lấy dữ liệu từ CartManager hoặc tạo dữ liệu mẫu
        List<Food> foodList = getFoodData();

        // Tạo adapter
        adapter = new PaymentFoodAdapter(foodList);

        // Setup RecyclerView với LinearLayoutManager
        rvFoods.setLayoutManager(new LinearLayoutManager(this));
        rvFoods.setAdapter(adapter);
    }

    private List<Food> getFoodData() {
        List<Food> foodList = new ArrayList<>();

        // Lấy dữ liệu từ CartManager nếu có
        List<CartItem> cartItems = CartManager.getCartItems();
        if (cartItems != null && !cartItems.isEmpty()) {
            // Chuyển đổi CartItem thành Food để hiển thị
            for (CartItem item : cartItems) {
                foodList.add(item.getFood());
            }
        } else {
            // Nếu giỏ hàng trống, tạo dữ liệu mẫu
            int img = R.drawable.ic_launcher_foreground; // Thay bằng drawable thực tế
            foodList.add(new Food("1", "Burger Bistro", "Rose Garden", 40, img));
            foodList.add(new Food("2", "Smokin' Burger", "Cafenio Restaurant", 60, img));
            foodList.add(new Food("3", "Buffalo Burgers", "Kaji Firm Kitchen", 75, img));
            foodList.add(new Food("4", "Bullseye Burgers", "Kabab Restaurant", 94, img));
        }

        return foodList;
    }
}
