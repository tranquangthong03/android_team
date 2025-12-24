package com.example.android_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView; // Import cho btnBack, btnHome
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_project.ui.CartActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    // 1. Khai báo biến View (Phải khớp với loại thẻ trong XML)
    
    // Nút điều hướng trên Header
    private ImageView btnBack;
    private ImageView btnHome;

    // Thông tin User
    private TextView tvName;
    private TextView tvEmail;
    
    // Nhóm 1: Tài khoản
    private LinearLayout btnEditProfile;
    private LinearLayout btnAddress;
    private LinearLayout btnPaymentMethod;

    // Nhóm 2: Tiện ích
    private LinearLayout btnProfileCart;
    private LinearLayout btnOrderHistory;
    private LinearLayout btnProfileFavorite;
    private LinearLayout btnNotification;

    // Nhóm 3: Khác
    private LinearLayout btnSettings;
    private LinearLayout btnSupport;
    private LinearLayout btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Đảm bảo tên file layout đúng là fragment_profile.xml
        setContentView(R.layout.fragment_profile); 

        initViews();
        loadUserProfile();
        setupEvents();
    }

    // 2. Ánh xạ ID từ XML sang Java
    private void initViews() {
        // Header Buttons
        btnBack = findViewById(R.id.btnBack);
        btnHome = findViewById(R.id.btnHome);

        // User Info
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);

        // Nhóm Tài khoản
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnAddress = findViewById(R.id.btnAddress);
        btnPaymentMethod = findViewById(R.id.btnPaymentMethod);

        // Nhóm Tiện ích
        btnProfileCart = findViewById(R.id.btnProfileCart);
        btnOrderHistory = findViewById(R.id.btnOrderHistory);
        btnProfileFavorite = findViewById(R.id.btnProfileFavorite);
        btnNotification = findViewById(R.id.btnNotification);

        // Nhóm Khác
        btnSettings = findViewById(R.id.btnSettings);
        btnSupport = findViewById(R.id.btnSupport);
        btnLogout = findViewById(R.id.btnLogout);
    }

    // 3. Tải thông tin người dùng từ Firebase
    private void loadUserProfile() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) return;

        String uid = currentUser.getUid();
        FirebaseFirestore.getInstance().collection("users").document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String name = documentSnapshot.getString("name");
                        String email = documentSnapshot.getString("email");

                        if (name != null) tvName.setText(name);
                        if (email != null) tvEmail.setText(email);
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(ProfileActivity.this, "Lỗi kết nối mạng", Toast.LENGTH_SHORT).show());
    }

    // 4. Xử lý sự kiện Click (Event Code)
    private void setupEvents() {
        
        // --- HEADER NAV ---
        
        // Nút Back: Quay lại màn hình trước
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Nút Home: Về trang chủ, xóa hết các trang cũ để tránh bị loop
        if (btnHome != null) {
            btnHome.setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            });
        }

        // --- NHÓM CHỨC NĂNG ---

        // Địa chỉ
        if (btnAddress != null) {
            btnAddress.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, AddressActivity.class)));
        }

        // Giỏ hàng
        if (btnProfileCart != null) {
            btnProfileCart.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, CartActivity.class)));
        }

        // Phương thức thanh toán (Chú ý tên class PayMentActivity của bạn)
        if (btnPaymentMethod != null) {
            btnPaymentMethod.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, PayMentActivity.class)));
        }

        // Lịch sử đơn hàng
        if (btnOrderHistory != null) {
            btnOrderHistory.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, OrderHistoryActivity.class)));
        }

        // --- CÁC TÍNH NĂNG ĐANG PHÁT TRIỂN (TOAST) ---
        
        if (btnEditProfile != null) {
            btnEditProfile.setOnClickListener(v -> Toast.makeText(ProfileActivity.this, "Tính năng Chỉnh sửa đang phát triển", Toast.LENGTH_SHORT).show());
        }
        
        if (btnProfileFavorite != null) {
            btnProfileFavorite.setOnClickListener(v -> Toast.makeText(ProfileActivity.this, "Danh sách Yêu thích trống", Toast.LENGTH_SHORT).show());
        }
        
        if (btnNotification != null) {
            btnNotification.setOnClickListener(v -> Toast.makeText(ProfileActivity.this, "Không có thông báo mới", Toast.LENGTH_SHORT).show());
        }

        if (btnSettings != null) {
            btnSettings.setOnClickListener(v -> Toast.makeText(ProfileActivity.this, "Cài đặt chung", Toast.LENGTH_SHORT).show());
        }

        if (btnSupport != null) {
            btnSupport.setOnClickListener(v -> Toast.makeText(ProfileActivity.this, "Liên hệ: support@fastbite.com", Toast.LENGTH_SHORT).show());
        }

        // --- ĐĂNG XUẤT ---
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                // 1. Đăng xuất khỏi Firebase
                FirebaseAuth.getInstance().signOut();

                // 2. Xóa dữ liệu lưu tạm (Shared Preferences) nếu có
                SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();

                // 3. Chuyển về màn hình Đăng nhập & Xóa lịch sử Activity
                Intent intent = new Intent(ProfileActivity.this, LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                
                Toast.makeText(ProfileActivity.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
            });
        }
    }
}