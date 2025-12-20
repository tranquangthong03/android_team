package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.models.CategoryDomain;
import com.example.android_project.models.Food;
import com.example.android_project.ui.CartActivity;
import com.example.android_project.ui.CategoryAdapter;
import com.example.android_project.ui.FoodActivity;
import com.example.android_project.ui.FoodAdapter;
import com.example.android_project.ui.FoodDetailActivity;
import com.example.android_project.ui.RestaurantAdapter;
import com.example.android_project.data.CartManager;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvCategories, rvPopularHome, rvRestaurants;
    private TextView txtGreeting, txtSeeAll;
    private View btnCart, layoutSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();
        setupUserData();
        
        // 1. Setup Danh mục (Trượt ngang)
        setupCategories();
        
        // 2. Setup Món ăn phổ biến (Trượt ngang)
        setupPopularFood();
        
        // 3. Setup Nhà hàng (Danh sách dọc - Mới thêm)
        setupRestaurants();
        
        // 4. Xử lý sự kiện click
        setupEvents();
    }

    private void initViews() {
        rvCategories = findViewById(R.id.rvCategories);
        rvPopularHome = findViewById(R.id.rvPopularHome);
        rvRestaurants = findViewById(R.id.rvRestaurants); // RecyclerView cho nhà hàng
        
        txtGreeting = findViewById(R.id.txtGreeting);
        txtSeeAll = findViewById(R.id.txtSeeAllCategories);
        btnCart = findViewById(R.id.btnCart);
        layoutSearch = findViewById(R.id.layoutSearch);
    }

    // --- LOGIC NHẬN TÊN TỪ LOGIN ---
    private void setupUserData() {
        String username = getIntent().getStringExtra("USER_NAME");
        if (username != null && !username.isEmpty()) {
            txtGreeting.setText("Hi, " + username + "!");
        } else {
            txtGreeting.setText("Hi, Hungry User!");
        }
    }

    // --- PHẦN 1: CATEGORIES (NGANG) ---
    private void setupCategories() {
        ArrayList<CategoryDomain> categoryList = new ArrayList<>();
        // Lưu ý: Chuỗi "pizza_image" phải trùng tên file ảnh trong res/drawable
        categoryList.add(new CategoryDomain("Burger", "burger_image"));
        categoryList.add(new CategoryDomain("Pizza", "pizza_image"));
        categoryList.add(new CategoryDomain("Chicken", "sample_burger"));
        categoryList.add(new CategoryDomain("Drink", "burger_image"));
        categoryList.add(new CategoryDomain("Donut", "pizza_image"));

        CategoryAdapter adapter = new CategoryAdapter(categoryList);
        rvCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCategories.setAdapter(adapter);
    }

    // --- PHẦN 2: POPULAR FOOD (NGANG) ---
    private void setupPopularFood() {
        ArrayList<Food> foodList = new ArrayList<>();
        int imgDefault = R.drawable.sample_burger;

        foodList.add(new Food("1", "Cheese Burger", "Rose Garden", 45, imgDefault));
        foodList.add(new Food("2", "Pepperoni Pizza", "Pizza Hut", 80, R.drawable.pizza_image));
        foodList.add(new Food("3", "Spicy Chicken", "KFC", 35, imgDefault));
        foodList.add(new Food("4", "Iced Coffee", "Highlands", 15, imgDefault));

        // Dùng Constructor mới: Truyền layout R.layout.item_food_home (Bản nhỏ)
        // AND Context (this) which we added to FoodAdapter
        FoodAdapter adapter = new FoodAdapter(this, foodList, R.layout.item_food_home, new FoodAdapter.FoodListener() {
            @Override
            public void onFoodClick(Food food) {
                Intent intent = new Intent(HomeActivity.this, FoodDetailActivity.class);
                intent.putExtra(FoodDetailActivity.EXTRA_FOOD, food);
                startActivity(intent);
            }

            @Override
            public void onAddToCartClick(Food food) {
                // --- LOGIC THÊM VÀO GIỎ HÀNG ---
                CartManager.addToCart(food, 1);
                Toast.makeText(HomeActivity.this, "Đã thêm " + food.getName() + " vào giỏ!", Toast.LENGTH_SHORT).show();
            }
        });

        rvPopularHome.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvPopularHome.setAdapter(adapter);
    }

    // --- PHẦN 3: OPEN RESTAURANTS (DỌC) ---
    private void setupRestaurants() {
        ArrayList<CategoryDomain> resList = new ArrayList<>();
        // Dùng tạm CategoryDomain (title, pic) để hiển thị nhà hàng
        resList.add(new CategoryDomain("McDonald's - Fast Food", "sample_restaurant"));
        resList.add(new CategoryDomain("Starbucks Coffee", "sample_restaurant"));
        resList.add(new CategoryDomain("Pizza Hut Vietnam", "pizza_image"));
        resList.add(new CategoryDomain("KFC Fried Chicken", "sample_restaurant"));
        resList.add(new CategoryDomain("Highlands Coffee", "sample_restaurant"));

        RestaurantAdapter adapter = new RestaurantAdapter(resList);
        // Quan trọng: Vertical để list chạy dọc xuống dưới
        rvRestaurants.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvRestaurants.setAdapter(adapter);
    }

    // --- PHẦN 4: SỰ KIỆN CLICK ---
    private void setupEvents() {
        // 1. Nút See All -> Mở FoodActivity (Trang danh sách tổng hợp)
        txtSeeAll.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, FoodActivity.class);
            startActivity(intent);
        });

        // 2. Nút Giỏ hàng -> Mở CartActivity
        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });

        // 3. Thanh tìm kiếm -> Thông báo
        layoutSearch.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Tính năng tìm kiếm đang phát triển", Toast.LENGTH_SHORT).show();
        });
    }
}