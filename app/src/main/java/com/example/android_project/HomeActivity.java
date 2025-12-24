package com.example.android_project;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.android_project.models.Restaurant;
import com.example.android_project.ui.CartActivity;
import com.example.android_project.ui.CategoryAdapter;
import com.example.android_project.ui.FoodActivity;
import com.example.android_project.ui.FoodAdapter;
import com.example.android_project.ui.FoodDetailActivity;
import com.example.android_project.ui.RestaurantAdapter;
import com.google.firebase.auth.FirebaseAuth; // Import Auth
import com.google.firebase.auth.FirebaseUser; // Import User
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    // Khai báo các biến giao diện
    private RecyclerView rvCategories, rvPopularHome, rvRestaurants;
    private TextView txtGreeting, txtSeeAll;
    private View btnCart, layoutSearch;
    private ImageView imgProfile;

    // Khai báo Firebase
    private FirebaseFirestore db;
    private FirebaseAuth auth; // Thêm biến Auth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Khởi tạo Firebase
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance(); // Khởi tạo Auth

        // 1. Ánh xạ View
        initViews();

        // 2. Lấy tên thật từ Firebase
        setupUserData();

        // 3. Setup các danh sách
        setupCategories();
        setupPopularFood();
        setupRestaurants();

        // 4. Bắt sự kiện click
        setupEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật lại tên mỗi khi quay lại trang này (đề phòng vừa đổi tên xong)
        setupUserData();
    }

    private void initViews() {
        rvCategories = findViewById(R.id.rvCategories);
        rvPopularHome = findViewById(R.id.rvPopularHome);
        rvRestaurants = findViewById(R.id.rvRestaurants);

        txtGreeting = findViewById(R.id.txtGreeting);
        txtSeeAll = findViewById(R.id.txtSeeAllCategories);
        btnCart = findViewById(R.id.btnCart);
        layoutSearch = findViewById(R.id.layoutSearch);
        imgProfile = findViewById(R.id.imgProfile);
    }

    // --- HÀM QUAN TRỌNG: LẤY TÊN TỪ FIREBASE ---
    private void setupUserData() {
        FirebaseUser currentUser = auth.getCurrentUser();

        // Nếu chưa đăng nhập
        if (currentUser == null) {
            txtGreeting.setText("Xin chào, bạn!");
            return;
        }

        // Bước 1: Hiển thị tên tạm từ bộ nhớ máy (để đỡ bị trống trong lúc chờ mạng)
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String cachedName = prefs.getString("FULL_NAME", "bạn");
        txtGreeting.setText("Xin chào, " + cachedName + "!");

        // Bước 2: Gọi lên Firestore để lấy tên chính xác nhất
        String uid = currentUser.getUid();

        db.collection("users").document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Lấy trường "name" từ Firestore (đã lưu lúc đăng ký)
                        String realName = documentSnapshot.getString("name");

                        if (realName != null && !realName.isEmpty()) {
                            // Cập nhật giao diện
                            txtGreeting.setText("Xin chào, " + realName + "!");

                            // Lưu lại vào bộ nhớ máy để lần sau mở app lên là có ngay
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("FULL_NAME", realName);
                            editor.apply();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    // Nếu lỗi mạng thì giữ nguyên tên tạm
                });
    }

    // --- PHẦN 1: CATEGORIES ---
    private void setupCategories() {
        ArrayList<CategoryDomain> categoryList = new ArrayList<>();
        CategoryAdapter adapter = new CategoryAdapter(this, categoryList);
        rvCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCategories.setAdapter(adapter);

        db.collection("categories").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                categoryList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    CategoryDomain category = document.toObject(CategoryDomain.class);
                    category.setId(document.getId());
                    categoryList.add(category);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    // --- PHẦN 2: POPULAR FOOD ---
    private void setupPopularFood() {
        ArrayList<Food> foodList = new ArrayList<>();
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

        db.collection("foods").limit(5).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                foodList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Food food = document.toObject(Food.class);
                    food.setId(document.getId());
                    foodList.add(food);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    // --- PHẦN 3: RESTAURANTS ---
    private void setupRestaurants() {
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

    // --- PHẦN 4: EVENTS ---
    private void setupEvents() {
        if (txtSeeAll != null) {
            txtSeeAll.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, FoodActivity.class)));
        }
        if (btnCart != null) {
            btnCart.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, CartActivity.class)));
        }
        if (layoutSearch != null) {
            layoutSearch.setOnClickListener(v -> {
                // Chuyển sang màn hình tìm kiếm
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            });
        }

        if (imgProfile != null) {
            imgProfile.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ProfileActivity.class)));
        }
    }
}