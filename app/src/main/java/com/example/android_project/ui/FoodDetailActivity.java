package com.example.android_project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_project.R;
import com.example.android_project.data.CartManager;
import com.example.android_project.models.Food;

public class FoodDetailActivity extends AppCompatActivity {

    public static final String EXTRA_FOOD = "extra_food";

    private ImageView imgFood;
    private TextView txtFoodName, txtRestaurant, txtPrice, txtDesc, txtQuantity, txtTotal;
    private ImageButton btnBack, btnMinus, btnPlus;
    private Button btnAddToCart;

    private int quantity = 1;
    private Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        mapViews();

        // nhận Food từ Intent
        food = (Food) getIntent().getSerializableExtra(EXTRA_FOOD);
        if (food == null) {
            // demo tạm nếu chưa truyền từ list
            food = new Food("1", "Burger Bistro",
                    "Rose Garden", 40, R.drawable.sample_burger);
        }

        bindData();
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
        imgFood.setImageResource(food.getImageResId());
        txtFoodName.setText(food.getName());
        txtRestaurant.setText(food.getRestaurant());
        txtPrice.setText("$" + (int) food.getPrice());
        txtDesc.setText("Delicious food specially made for you.");
        updateTotal();
    }

    private void setupEvents() {
        btnBack.setOnClickListener(v -> onBackPressed());

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

            // mở CartActivity luôn (tùy bạn)
            Intent intent = new Intent(FoodDetailActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }

    private void updateTotal() {
        txtQuantity.setText(String.valueOf(quantity));
        double total = food.getPrice() * quantity;
        txtTotal.setText("Total: $" + (int) total);
    }
}
