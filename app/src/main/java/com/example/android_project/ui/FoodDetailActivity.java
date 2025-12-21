package com.example.android_project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
import com.bumptech.glide.Glide; // Import Glide
=======
import com.bumptech.glide.Glide;
>>>>>>> huuhung
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

<<<<<<< HEAD
        // Nhận object Food
        food = (Food) getIntent().getSerializableExtra(EXTRA_FOOD);

        // Kiểm tra null để tránh crash ứng dụng
        if (food != null) {
            bindData();
        } else {
            Toast.makeText(this, "Không tìm thấy dữ liệu món ăn!", Toast.LENGTH_SHORT).show();
            finish(); // Đóng màn hình nếu lỗi
=======
        // --- SỬA LỖI TẠI ĐÂY ---
        // 1. Nhận dữ liệu từ màn hình trước (KHÔNG dùng new Food(...) tạo dữ liệu ảo nữa)
        food = (Food) getIntent().getSerializableExtra(EXTRA_FOOD);

        // 2. Kiểm tra nếu có dữ liệu thì hiển thị
        if (food != null) {
            bindData();
        } else {
            // Nếu không có dữ liệu (lỗi) thì thông báo và thoát
            Toast.makeText(this, "Không tìm thấy dữ liệu món ăn!", Toast.LENGTH_SHORT).show();
            finish(); 
>>>>>>> huuhung
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
        btnBack = findViewById(R.id.btnBackNHLienKet); // Đảm bảo ID này đúng trong XML
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        btnAddToCart = findViewById(R.id.btnAddToCart);
    }

    private void bindData() {
<<<<<<< HEAD
        // Dùng Glide load ảnh chi tiết
=======
        // Load ảnh bằng Glide
>>>>>>> huuhung
        Glide.with(this)
                .load(food.getImagePath())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imgFood);

        txtFoodName.setText(food.getName());
        txtRestaurant.setText(food.getRestaurantName());
        txtPrice.setText("$" + (int) food.getPrice());
<<<<<<< HEAD
        txtDesc.setText("Delicious food specially made for you."); // Hoặc lấy từ food.getDescription() nếu có
=======
        txtDesc.setText("Delicious food specially made for you."); 
>>>>>>> huuhung
        updateTotal();
    }

    private void setupEvents() {
<<<<<<< HEAD
        btnBack.setOnClickListener(v -> onBackPressed()); // Hoặc finish()
=======
        btnBack.setOnClickListener(v -> finish()); // Đóng màn hình
>>>>>>> huuhung

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
<<<<<<< HEAD

            Intent intent = new Intent(FoodDetailActivity.this, CartActivity.class);
            startActivity(intent);
=======
            // Có thể chuyển sang CartActivity luôn hoặc chỉ thông báo
            // startActivity(new Intent(FoodDetailActivity.this, CartActivity.class));
>>>>>>> huuhung
        });
    }

    private void updateTotal() {
        txtQuantity.setText(String.valueOf(quantity));
        double total = food.getPrice() * quantity;
        txtTotal.setText("Total: $" + (int) total);
    }
}