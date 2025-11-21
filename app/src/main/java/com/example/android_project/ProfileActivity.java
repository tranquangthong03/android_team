package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_project.ui.CartActivity;

public class ProfileActivity extends AppCompatActivity {

    private LinearLayout btnAddresses;
    private LinearLayout btnCart;
    private LinearLayout btnLogout;
    private LinearLayout btnPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        // Lấy view trong fragment_profile.xml
        btnAddresses = findViewById(R.id.btn_addresses);
        btnCart = findViewById(R.id.btn_cart);
        btnLogout = findViewById(R.id.btn_logout);
        btnPayment = findViewById(R.id.btn_payment);
        // 👉 Click "Addresses" → mở AddressActivity
        btnAddresses.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, AddressActivity.class);
            startActivity(intent);
        });

        // 👉 Click "Cart" → quay về HomeActivity
        btnCart.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, CartActivity.class);
            startActivity(intent);
        });
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
            startActivity(intent);
        });
        btnPayment.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, PayMentActivity.class);
            startActivity(intent);
        });
    }
}
