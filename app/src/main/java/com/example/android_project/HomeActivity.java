package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.ui.CartActivity;
import com.example.android_project.ui.FoodActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    // 1. Khai báo các biến giao diện cũ
    private LinearLayout layoutBurger;
    private TextView cartIcon;
    private TextView iconProfile;

    // 2. Khai báo biến cho RecyclerView và Firebase (MỚI)
    private RecyclerView recyclerRestaurants;
    private RestaurantAdapter restaurantAdapter;
    private List<Restaurant> mListRestaurant;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // --- Ánh xạ ID (Tìm các view trong layout) ---
        layoutBurger = findViewById(R.id.layoutBurger);
        cartIcon = findViewById(R.id.cartIcon);
        iconProfile = findViewById(R.id.icon_profile);
        recyclerRestaurants = findViewById(R.id.recyclerRestaurants); // ID mới thêm trong XML

        // --- Cấu hình Firebase & RecyclerView (MỚI) ---
        initFirestoreData();

        // --- Các sự kiện Click (Giữ nguyên code cũ của bạn) ---
        setEventClick();
    }

    private void initFirestoreData() {
        // Khởi tạo Firestore
        db = FirebaseFirestore.getInstance();

        // Cấu hình RecyclerView: Dạng danh sách dọc (Vertical)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerRestaurants.setLayoutManager(linearLayoutManager);

        // Khởi tạo danh sách rỗng
        mListRestaurant = new ArrayList<>();

        // Gắn Adapter vào RecyclerView
        restaurantAdapter = new RestaurantAdapter(this, mListRestaurant);
        recyclerRestaurants.setAdapter(restaurantAdapter);

        // === GỌI API LẤY DỮ LIỆU TỪ FIREBASE ===
        // "restaurants" là tên Collection bạn đã tạo trên Web Firebase
        db.collection("restaurants")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Lấy dữ liệu thành công
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Biến đổi dữ liệu JSON từ Firebase thành Object Java (Restaurant)
                                Restaurant restaurant = document.toObject(Restaurant.class);
                                mListRestaurant.add(restaurant);
                            }
                            // Báo cho Adapter biết dữ liệu đã thay đổi để vẽ lại giao diện
                            restaurantAdapter.notifyDataSetChanged();
                        } else {
                            // Lấy thất bại (ví dụ: mất mạng)
                            Toast.makeText(HomeActivity.this, "Lỗi lấy dữ liệu!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setEventClick() {
        // Sự kiện click món Burger
        layoutBurger.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, FoodActivity.class);
            startActivity(intent);
        });

        // Sự kiện click Giỏ hàng
        cartIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });

        // Sự kiện click Profile
        iconProfile.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }
}