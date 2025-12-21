package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.data.CartManager;
import com.example.android_project.models.CategoryDomain;
import com.example.android_project.models.Food;
import com.example.android_project.models.Restaurant; // Đảm bảo đã có model này
import com.example.android_project.ui.CartActivity;
import com.example.android_project.ui.CategoryAdapter;
import com.example.android_project.ui.FoodActivity;
import com.example.android_project.ui.FoodAdapter;
import com.example.android_project.ui.FoodDetailActivity;
import com.example.android_project.ui.RestaurantAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvCategories, rvPopularHome, rvRestaurants;
    private TextView txtGreeting, txtSeeAll;
    private View btnCart, layoutSearch;
    private FirebaseFirestore db; // Biến Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = FirebaseFirestore.getInstance(); // Khởi tạo Firebase

        initViews();
        setupUserData();
        
        // Setup các danh sách dùng dữ liệu từ Firebase
        setupCategories();
        setupPopularFood();
        setupRestaurants();
        
        setupEvents();
    }

    private void initViews() {
        rvCategories = findViewById(R.id.rvCategories);
        rvPopularHome = findViewById(R.id.rvPopularHome);
        rvRestaurants = findViewById(R.id.rvRestaurants);

        txtGreeting = findViewById(R.id.txtGreeting);
        txtSeeAll = findViewById(R.id.txtSeeAllCategories);
        btnCart = findViewById(R.id.btnCart);
        layoutSearch = findViewById(R.id.layoutSearch);
    }

    private void setupUserData() {
        String username = getIntent().getStringExtra("USER_NAME");
        txtGreeting.setText(username != null ? "Hi, " + username + "!" : "Hi, Hungry User!");
    }

    // --- 1. CATEGORIES (Tải từ Firebase) ---
    private void setupCategories() {
        ArrayList<CategoryDomain> categoryList = new ArrayList<>();
        CategoryAdapter adapter = new CategoryAdapter(categoryList); // Adapter cũ của bạn

        rvCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCategories.setAdapter(adapter);

        db.collection("categories").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                categoryList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    CategoryDomain cat = document.toObject(CategoryDomain.class);
                    // QUAN TRỌNG: Lấy ID từ document để truyền sang màn hình món ăn
                    cat.setId(document.getId()); 
                    categoryList.add(cat);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    // --- 2. POPULAR FOOD (Tải từ Firebase) ---
    private void setupPopularFood() {
        ArrayList<Food> foodList = new ArrayList<>();
        
        // Adapter hỗ trợ click và add to cart
        FoodAdapter adapter = new FoodAdapter(this, foodList, R.layout.item_food_home, new FoodAdapter.FoodListener() {
            @Override
            public void onFoodClick(Food food) {
                Intent intent = new Intent(HomeActivity.this, FoodDetailActivity.class);
                intent.putExtra(FoodDetailActivity.EXTRA_FOOD, food);
                startActivity(intent);
            }

            @Override
            public void onAddToCartClick(Food food) {
                CartManager.addToCart(food, 1);
                Toast.makeText(HomeActivity.this, "Đã thêm " + food.getName() + " vào giỏ!", Toast.LENGTH_SHORT).show();
            }
        });

        rvPopularHome.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvPopularHome.setAdapter(adapter);

        // Lấy 5 món bất kỳ làm "Món phổ biến"
        db.collection("foods").limit(5).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                foodList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Food food = document.toObject(Food.class);
                    food.setId(document.getId()); // Lấy ID món ăn
                    foodList.add(food);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    // --- 3. RESTAURANTS (Tải từ Firebase) ---
    private void setupRestaurants() {
        // Lưu ý: Cần đảm bảo bạn đã có Model Restaurant.java
        // Nếu chưa có, hãy tạo nó giống như Food.java
        ArrayList<Restaurant> resList = new ArrayList<>();
        RestaurantAdapter adapter = new RestaurantAdapter(this, resList);

        rvRestaurants.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvRestaurants.setAdapter(adapter);

        db.collection("restaurants").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                resList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Restaurant res = document.toObject(Restaurant.class);
                    resList.add(res);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setupEvents() {
        txtSeeAll.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, FoodActivity.class)));
        btnCart.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, CartActivity.class)));
        layoutSearch.setOnClickListener(v -> Toast.makeText(this, "Tìm kiếm đang phát triển", Toast.LENGTH_SHORT).show());
    }
}