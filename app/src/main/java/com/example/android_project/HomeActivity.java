package com.example.android_project;

import android.os.Bundle;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_project.ui.CartActivity;
import com.example.android_project.ui.FoodActivity;

public class HomeActivity extends AppCompatActivity {
    private LinearLayout layoutBurger;
    private TextView cartIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        layoutBurger = findViewById(R.id.layoutBurger);
        cartIcon = findViewById(R.id.cartIcon);

        layoutBurger.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, FoodActivity.class);
            startActivity(intent);
        });

        cartIcon.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }
}