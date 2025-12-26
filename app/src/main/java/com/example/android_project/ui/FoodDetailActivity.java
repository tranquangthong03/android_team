package com.example.android_project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android_project.R;
import com.example.android_project.data.CartManager;
import com.example.android_project.models.Food;

public class FoodDetailActivity extends AppCompatActivity {

    public static final String EXTRA_FOOD = "extra_food";

    private ImageView imgFood;
    private TextView txtFoodName, txtRestaurant, txtPrice, txtDesc, txtQuantity, txtTotal;
    private ImageButton btnBack, btnMinus, btnPlus;
    private android.view.View btnAddToCart;

    private int quantity = 1;
    private Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        mapViews();

        food = (Food) getIntent().getSerializableExtra(EXTRA_FOOD);

        if (food != null) {
            bindData();
        } else {
            Toast.makeText(this, "Không tìm thấy dữ liệu món ăn!", Toast.LENGTH_SHORT).show();
            finish();
        }

        setupEvents();
    }

    private void mapViews() {
        imgFood = findViewById(R.id.imgFood);
        txtFoodName = findViewById(R.id.txtFoodName);
        txtRestaurant = findViewById(R.id.txtRestaurant);
        txtPrice = findViewById(R.id.txtPrice);
        txtDesc = findViewById(R.id.txtDesc);
        txtQuantity = findViewById(R.id.txtQuantity);
        txtTotal = findViewById(R.id.txtTotal);

        btnBack = findViewById(R.id.btnBackNHLienKet);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        btnAddToCart = findViewById(R.id.btnAddToCart);
    }

    private void bindData() {
        Glide.with(this)
                .load(food.getImagePath())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(imgFood);

        txtFoodName.setText(food.getName());
        txtRestaurant.setText(food.getRestaurantName());

        txtPrice.setText((int) food.getPrice() + ".000vnđ");

        txtDesc.setText("Delicious food specially made for you.");

        updateTotal();
    }

    private void setupEvents() {
        btnBack.setOnClickListener(v -> finish());

        btnMinus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                updateTotal();
            }
        });

        btnPlus.setOnClickListener(v -> {
            quantity++;
            updateTotal();
        });

        btnAddToCart.setOnClickListener(v -> {
            CartManager.addToCart(food, quantity);
            Toast.makeText(this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(FoodDetailActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }

    private void updateTotal() {
        txtQuantity.setText(String.valueOf(quantity));
        double total = food.getPrice() * quantity;

        txtTotal.setText((int) total + ".000vnđ");
    }
}