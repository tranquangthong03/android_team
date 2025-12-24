package com.example.android_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.android_project.ui.CartActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    // Khai báo các biến View tương ứng với XML mới
    private TextView tvName, tvEmail;
    private ImageView imgAvatar;

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
        // Đảm bảo tên file layout đúng với file xml bạn đang dùng
        setContentView(R.layout.fragment_profile);

        initViews();
        loadUserProfile();
        setupEvents();
    }

    private void initViews() {
        // Ánh xạ View thông tin cá nhân
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        imgAvatar = findViewById(R.id.imgAvatar);

        // Ánh xạ các nút chức năng (Nhóm 1)
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnAddress = findViewById(R.id.btnAddress);
        btnPaymentMethod = findViewById(R.id.btnPaymentMethod);

        // Ánh xạ các nút chức năng (Nhóm 2)
        btnProfileCart = findViewById(R.id.btnProfileCart);
        btnOrderHistory = findViewById(R.id.btnOrderHistory);
        btnProfileFavorite = findViewById(R.id.btnProfileFavorite);
        btnNotification = findViewById(R.id.btnNotification);

        // Ánh xạ các nút chức năng (Nhóm 3)
        btnSettings = findViewById(R.id.btnSettings);
        btnSupport = findViewById(R.id.btnSupport);
        btnLogout = findViewById(R.id.btnLogout);
    }

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

                        // Nếu có ảnh avatar url từ Firebase, bạn có thể dùng Glide để load vào imgAvatar ở đây
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(ProfileActivity.this, "Lỗi tải thông tin", Toast.LENGTH_SHORT).show());
    }

    private void setupEvents() {
        // --- CÁC CHỨC NĂNG ĐÃ CÓ (Giữ nguyên logic cũ) ---

        // 1. Địa chỉ
        btnAddress.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, AddressActivity.class));
        });

        // 2. Giỏ hàng
        btnProfileCart.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, CartActivity.class));
        });

        // 3. Phương thức thanh toán (Lưu ý tên class PayMentActivity của bạn)
        btnPaymentMethod.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, PayMentActivity.class));
        });

        // 4. Lịch sử đơn hàng
        btnOrderHistory.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, OrderHistoryActivity.class));
        });

        // --- CÁC CHỨC NĂNG MỚI (Tạm thời Toast thông báo) ---

        // 5. Chỉnh sửa thông tin
        btnEditProfile.setOnClickListener(v -> {
            // Bạn có thể tạo EditProfileActivity sau
            Toast.makeText(this, "Chức năng chỉnh sửa hồ sơ đang phát triển", Toast.LENGTH_SHORT).show();
        });

        // 6. Yêu thích
        btnProfileFavorite.setOnClickListener(v -> {
            Toast.makeText(this, "Chức năng Yêu thích đang phát triển", Toast.LENGTH_SHORT).show();
        });

        // 7. Thông báo
        btnNotification.setOnClickListener(v -> {
            Toast.makeText(this, "Bạn không có thông báo mới", Toast.LENGTH_SHORT).show();
        });

        // 8. Cài đặt
        btnSettings.setOnClickListener(v -> {
            Toast.makeText(this, "Mở màn hình Cài đặt", Toast.LENGTH_SHORT).show();
        });

        // 9. Hỗ trợ
        btnSupport.setOnClickListener(v -> {
            Toast.makeText(this, "Liên hệ hỗ trợ: 1900 xxxx", Toast.LENGTH_SHORT).show();
        });

        // --- ĐĂNG XUẤT (Logic cũ) ---
        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(ProfileActivity.this, LogInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
        });
    }
}