package com.example.android_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_project.ui.CartActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    // Khai báo các nút chức năng trong giao diện
    private LinearLayout btnAddresses;
    private LinearLayout btnCart;
    private LinearLayout btnLogout;
    private LinearLayout btnPayment;
    private LinearLayout btnOrders; // Nút Lịch sử đơn hàng

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Đảm bảo tên file layout đúng với dự án của bạn (fragment_profile.xml)
        setContentView(R.layout.fragment_profile);

        // 1. Ánh xạ View
        initViews();

        // 2. Thiết lập sự kiện click
        setupEvents();
    }

    private void initViews() {
        btnAddresses = findViewById(R.id.btn_addresses);
        btnCart = findViewById(R.id.btn_cart);
        btnLogout = findViewById(R.id.btn_logout);
        btnPayment = findViewById(R.id.btn_payment);
        btnOrders = findViewById(R.id.btn_orders);
    }

    private void setupEvents() {
        // --- CHUYỂN TRANG ---

        // 1. Địa chỉ
        btnAddresses.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, AddressActivity.class);
            startActivity(intent);
        });

        // 2. Giỏ hàng
        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, CartActivity.class);
            startActivity(intent);
        });

        // 3. Phương thức thanh toán
        btnPayment.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, PayMentActivity.class);
            startActivity(intent);
        });

        // 4. Lịch sử đơn hàng
        if (btnOrders != null) {
            btnOrders.setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            });
        }

        // --- CHỨC NĂNG ĐĂNG XUẤT (QUAN TRỌNG) ---
        btnLogout.setOnClickListener(v -> {
            // Bước 1: Đăng xuất khỏi Firebase Auth
            FirebaseAuth.getInstance().signOut();

            // Bước 2: Xóa thông tin người dùng lưu tạm trong SharedPreferences
            // (Để khi đăng nhập tài khoản khác, tên người cũ không hiện lên Home)
            SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear(); // Xóa sạch dữ liệu
            editor.apply();

            // Bước 3: Chuyển về màn hình Đăng nhập (LogInActivity)
            Intent intent = new Intent(ProfileActivity.this, LogInActivity.class);

            // Xóa toàn bộ lịch sử Activity (User không thể bấm nút Back để quay lại Profile)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
            finish(); // Đóng Activity hiện tại

            Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
        });
    }
}