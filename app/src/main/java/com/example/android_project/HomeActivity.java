package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.models.Food;
import com.example.android_project.ui.FoodActivity; // Trang "bên phải"
import com.example.android_project.ui.FoodAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvPopularHome;
    private FoodAdapter foodAdapter;
    private List<Food> popularFoodList;
    private TextView txtGreeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        setupUserData(); // Hàm nhận tên người dùng
        setupEvents();   // Hàm xử lý See All
        setupPopularData();
    }

    private void initViews() {
        rvPopularHome = findViewById(R.id.rvPopularHome);
        txtGreeting = findViewById(R.id.txtGreeting);
    }

    // 1. NHẬN DỮ LIỆU TỪ LOGIN
    private void setupUserData() {
        String username = getIntent().getStringExtra("USER_NAME");
        if (username != null && !username.isEmpty()) {
            txtGreeting.setText("Hi, " + username + "!");
        } else {
            txtGreeting.setText("Hi, Hungry User!");
        }
    }

    // 2. SỰ KIỆN BẤM "SEE ALL"
    private void setupEvents() {
        TextView txtSeeAll = findViewById(R.id.txtSeeAllCategories);
        txtSeeAll.setOnClickListener(v -> {
            // Chuyển sang màn hình FoodActivity (Hình bên phải)
            Intent intent = new Intent(HomeActivity.this, FoodActivity.class);
            startActivity(intent);
        });
        
        // Sự kiện click vào thanh tìm kiếm (nếu cần)
        findViewById(R.id.layoutSearch).setOnClickListener(v -> {
            Toast.makeText(this, "Chức năng tìm kiếm đang phát triển", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupPopularData() {
        popularFoodList = new ArrayList<>();
        int img = R.drawable.sample_burger; 

        // Thêm 4 món hiển thị ở màn hình chính
        popularFoodList.add(new Food("1", "Cheese Burger", "Rose Garden", 45, img));
        popularFoodList.add(new Food("2", "Pizza Hut", "Pizza Hut", 80, img));
        popularFoodList.add(new Food("3", "Fried Chicken", "KFC", 30, img));
        popularFoodList.add(new Food("4", "Pepsi Zero", "Drink Store", 10, img));

        // Dùng lại FoodAdapter nhưng có thể ẩn nút Add nếu muốn gọn
        foodAdapter = new FoodAdapter(popularFoodList, new FoodAdapter.FoodListener() {
            @Override
            public void onFoodClick(Food food) {
               // Mở chi tiết món ăn
               // Intent intent = new Intent(HomeActivity.this, FoodDetailActivity.class);
               // intent.putExtra("object", food);
               // startActivity(intent);
            }

            @Override
            public void onAddToCartClick(Food food) {
                 Toast.makeText(HomeActivity.this, "Đã thêm " + food.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        rvPopularHome.setLayoutManager(new GridLayoutManager(this, 2));
        rvPopularHome.setNestedScrollingEnabled(false); // Để cuộn mượt cùng màn hình chính
        rvPopularHome.setAdapter(foodAdapter);
    }
}