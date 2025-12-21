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
import com.example.android_project.models.Restaurant; // ⚠️ Quan trọng: Import Model Restaurant
import com.example.android_project.ui.CartActivity;
import com.example.android_project.ui.CategoryAdapter;
import com.example.android_project.ui.FoodActivity;
import com.example.android_project.ui.FoodAdapter;
import com.example.android_project.ui.FoodDetailActivity;
import com.example.android_project.ProfileActivity; // ⚠️ Quan trọng: Import ProfileActivity
import com.example.android_project.ui.RestaurantAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    // Khai báo các biến giao diện
    private RecyclerView rvCategories, rvPopularHome, rvRestaurants;
    private TextView txtGreeting, txtSeeAll;
    private View btnCart, layoutSearch;
<<<<<<< HEAD
    private ImageView imgProfile; // Biến cho ảnh đại diện
=======
    private ImageView imgProfile;
>>>>>>> huuhung

    // Khai báo Firebase
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Khởi tạo Firestore
        db = FirebaseFirestore.getInstance();

<<<<<<< HEAD
        // 1. Ánh xạ View
=======
>>>>>>> huuhung
        initViews();

        // 2. Hiển thị tên người dùng (nếu có)
        setupUserData();

<<<<<<< HEAD
        // 3. Setup các danh sách
        setupCategories();    // Danh mục (Firebase)
        setupPopularFood();   // Món ngon phổ biến (Firebase)
        setupRestaurants();   // Nhà hàng (Firebase)

        // 4. Bắt sự kiện click
=======
        // Setup các danh sách
        setupCategories();
        setupPopularFood();
        setupRestaurants();

>>>>>>> huuhung
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
<<<<<<< HEAD
        imgProfile = findViewById(R.id.imgProfile); // Ánh xạ Avatar
=======
        imgProfile = findViewById(R.id.imgProfile);
>>>>>>> huuhung
    }

    private void setupUserData() {
        String username = getIntent().getStringExtra("USER_NAME");
<<<<<<< HEAD
        if (username != null && !username.isEmpty()) {
            txtGreeting.setText("Xin chào, " + username + "!");
        } else {
            txtGreeting.setText("Xin chào, bạn!");
        }
    }

    // --- PHẦN 1: CATEGORIES (Tải từ Firebase) ---
    private void setupCategories() {
        ArrayList<CategoryDomain> categoryList = new ArrayList<>();

        // Truyền 'this' vào Adapter để xử lý click chuyển trang & Glide
        CategoryAdapter adapter = new CategoryAdapter(this, categoryList);

        rvCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCategories.setAdapter(adapter);

        // Gọi Firebase lấy dữ liệu
=======
        if (txtGreeting != null) {
            if (username != null && !username.isEmpty()) {
                txtGreeting.setText("Xin chào, " + username + "!");
            } else {
                txtGreeting.setText("Xin chào, bạn!");
            }
        }
    }

    // --- PHẦN 1: CATEGORIES ---
    private void setupCategories() {
        ArrayList<CategoryDomain> categoryList = new ArrayList<>();
        CategoryAdapter adapter = new CategoryAdapter(this, categoryList);

        rvCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCategories.setAdapter(adapter);

>>>>>>> huuhung
        db.collection("categories")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        categoryList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            CategoryDomain category = document.toObject(CategoryDomain.class);
<<<<<<< HEAD
=======
                            category.setId(document.getId()); // Lấy ID document
>>>>>>> huuhung
                            categoryList.add(category);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

<<<<<<< HEAD
    // --- PHẦN 2: MÓN NGON PHỔ BIẾN (Tải từ Firebase) ---
    private void setupPopularFood() {
        ArrayList<Food> foodList = new ArrayList<>();

        // Sử dụng FoodAdapter với Layout Item nhỏ (item_food_home)
=======
    // --- PHẦN 2: POPULAR FOOD ---
    private void setupPopularFood() {
        ArrayList<Food> foodList = new ArrayList<>();

        // Sử dụng FoodAdapter layout nhỏ (item_food_home)
>>>>>>> huuhung
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

<<<<<<< HEAD
        // Lấy 5 món ăn từ Firebase để hiển thị
=======
>>>>>>> huuhung
        db.collection("foods")
                .limit(5)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        foodList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Food food = document.toObject(Food.class);
<<<<<<< HEAD
                            food.setId(document.getId()); // Lưu ID để xử lý logic sau này
=======
                            food.setId(document.getId());
>>>>>>> huuhung
                            foodList.add(food);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

<<<<<<< HEAD
    // --- PHẦN 3: NHÀ HÀNG (Tải từ Firebase) ---
    private void setupRestaurants() {
        ArrayList<Restaurant> resList = new ArrayList<>();

        // Truyền 'this' vào Adapter để dùng Glide
=======
    // --- PHẦN 3: RESTAURANTS ---
    private void setupRestaurants() {
        ArrayList<Restaurant> resList = new ArrayList<>();
>>>>>>> huuhung
        RestaurantAdapter adapter = new RestaurantAdapter(this, resList);

        rvRestaurants.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvRestaurants.setAdapter(adapter);

<<<<<<< HEAD
        // Gọi Firebase lấy dữ liệu nhà hàng
=======
>>>>>>> huuhung
        db.collection("restaurants")
                .get()
                .addOnCompleteListener(task -> {
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
<<<<<<< HEAD
        // Nút Xem thêm -> Mở trang danh sách món ăn đầy đủ
        txtSeeAll.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, FoodActivity.class);
            startActivity(intent);
        });

        // Nút Giỏ hàng -> Mở trang Giỏ hàng
        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });

        // Thanh tìm kiếm (Demo)
        layoutSearch.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Chức năng tìm kiếm đang phát triển", Toast.LENGTH_SHORT).show();
        });

        // Click Avatar -> Mở trang Profile
        if (imgProfile != null) {
            imgProfile.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            });
=======
        if (txtSeeAll != null) {
            txtSeeAll.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, FoodActivity.class)));
        }

        if (btnCart != null) {
            btnCart.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, CartActivity.class)));
        }

        if (layoutSearch != null) {
            layoutSearch.setOnClickListener(v -> Toast.makeText(HomeActivity.this, "Chức năng tìm kiếm đang phát triển", Toast.LENGTH_SHORT).show());
        }

        if (imgProfile != null) {
            imgProfile.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ProfileActivity.class)));
>>>>>>> huuhung
        }
    }
}