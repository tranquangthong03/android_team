package com.example.android_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView; // Nhớ import ImageView
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_project.ui.CartActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    private LinearLayout btnAddresses;
    private LinearLayout btnCart;
    private LinearLayout btnLogout;
    private LinearLayout btnPayment;
    private LinearLayout btnOrders;

    // Nút Home mới
    private ImageView btnBackToHome;

    private TextView txtProfileName;
    private TextView txtProfileEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        initViews();
        setupEvents();
        loadUserProfile();
        
    }

    private void initViews() {
        btnAddresses = findViewById(R.id.btn_addresses);
        btnCart = findViewById(R.id.btn_cart);
        btnLogout = findViewById(R.id.btn_logout);
        btnPayment = findViewById(R.id.btn_payment);
        btnOrders = findViewById(R.id.btn_orders);

        // Ánh xạ nút Home
        btnBackToHome = findViewById(R.id.btnBackToHome);

        txtProfileName = findViewById(R.id.txt_profile_name);
        txtProfileEmail = findViewById(R.id.txt_profile_email);
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

                        if (name != null) txtProfileName.setText(name);
                        if (email != null) txtProfileEmail.setText(email);
                    }
                });
    }

    private void setupEvents() {
        // --- XỬ LÝ NÚT HOME ---
        btnBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
            // Xóa các activity cũ để về Home sạch sẽ
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // ... Các sự kiện khác giữ nguyên ...
        btnAddresses.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, AddressActivity.class)));
        btnCart.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, CartActivity.class)));
        btnPayment.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, PayMentActivity.class)));
        if (btnOrders != null) btnOrders.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, OrderHistoryActivity.class)));

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