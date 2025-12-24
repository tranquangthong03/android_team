package com.example.android_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_project.ui.CartActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    private ImageView btnBack;
    private ImageView btnHome;

    private TextView tvName;
    private TextView tvEmail;

    private LinearLayout btnEditProfile;
    private LinearLayout btnAddress;
    private LinearLayout btnPaymentMethod;

    private LinearLayout btnProfileCart;
    private LinearLayout btnOrderHistory;
    private LinearLayout btnProfileFavorite;
    private LinearLayout btnNotification;

    private LinearLayout btnSettings;
    private LinearLayout btnSupport;
    private LinearLayout btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        initViews();
        loadUserProfile();
        setupEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserProfile();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnHome = findViewById(R.id.btnHome);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);

        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnAddress = findViewById(R.id.btnAddress);
        btnPaymentMethod = findViewById(R.id.btnPaymentMethod);

        btnProfileCart = findViewById(R.id.btnProfileCart);
        btnOrderHistory = findViewById(R.id.btnOrderHistory);
        btnProfileFavorite = findViewById(R.id.btnProfileFavorite);
        btnNotification = findViewById(R.id.btnNotification);

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
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(ProfileActivity.this, "Lỗi kết nối mạng", Toast.LENGTH_SHORT).show());
    }

    private void setupEvents() {
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        if (btnHome != null) {
            btnHome.setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            });
        }

        if (btnAddress != null) {
            btnAddress.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, AddressActivity.class)));
        }

        if (btnProfileCart != null) {
            btnProfileCart.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, CartActivity.class)));
        }

        if (btnPaymentMethod != null) {
            btnPaymentMethod.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, PayMentActivity.class)));
        }

        if (btnOrderHistory != null) {
            btnOrderHistory.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, OrderHistoryActivity.class)));
        }

        if (btnEditProfile != null) {
            btnEditProfile.setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            });
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

        if (btnLogout != null) {
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

                Toast.makeText(ProfileActivity.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
            });
        }
    }
}