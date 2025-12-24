package com.example.android_project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android_project.PayMentActivity;
import com.example.android_project.R;
import com.example.android_project.data.CartManager;
import com.example.android_project.models.CartItem;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.CartListener {

    private RecyclerView rcCart;
    private TextView txtTotalCart;
    private TextView txtDone; // 1. Khai báo biến cho nút Mua thêm
    private ImageButton btnBackCart;
    private Button btnPlaceOrder;

    private CartAdapter adapter;
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initViews();
        setupCart();
        setupEvents();
    }

    private void initViews() {
        rcCart = findViewById(R.id.rcCart);
        txtTotalCart = findViewById(R.id.txtTotalCart);
        txtDone = findViewById(R.id.txtDone); // 2. Ánh xạ ID từ XML
        btnBackCart = findViewById(R.id.btnBackCart);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
    }

    private void setupCart() {
        cartItems = CartManager.getCartItems();
        adapter = new CartAdapter(this, cartItems, this);
        rcCart.setLayoutManager(new LinearLayoutManager(this));
        rcCart.setAdapter(adapter);
        updateTotal();
    }

    private void setupEvents() {
        // Nút Back (Quay lại màn hình trước đó)
        if (btnBackCart != null) {
            btnBackCart.setOnClickListener(v -> finish());
        }

        // --- XỬ LÝ NÚT MUA THÊM ---
        if (txtDone != null) {
            txtDone.setOnClickListener(v -> {
                // Chuyển sang FoodActivity
                Intent intent = new Intent(CartActivity.this, FoodActivity.class);

                startActivity(intent);
                finish(); // Đóng trang giỏ hàng hiện tại
            });
        }

        // Chuyển qua trang Thanh toán
        if (btnPlaceOrder != null) {
            btnPlaceOrder.setOnClickListener(v -> {
                Intent intent = new Intent(CartActivity.this, PayMentActivity.class);
                startActivity(intent);
            });
        }
    }

    private void updateTotal() {
        double total = CartManager.getTotal();
        if (txtTotalCart != null) {
            txtTotalCart.setText("$" + (int) total);
        }
    }

    @Override
    public void onCartChanged() {
        updateTotal();
    }
}