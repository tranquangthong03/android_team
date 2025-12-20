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
    private ImageButton btnBackCart;
    private Button btnPlaceOrder;

    private CartAdapter adapter;
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rcCart = findViewById(R.id.rcCart);
        txtTotalCart = findViewById(R.id.txtTotalCart);
        btnBackCart = findViewById(R.id.btnBackCart);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        // Lấy dữ liệu giỏ hàng
        cartItems = CartManager.getCartItems();

        // --- SỬA LỖI TẠI ĐÂY ---
        // Thêm từ khóa 'this' vào đầu để truyền Context cho Adapter
        adapter = new CartAdapter(this, cartItems, this);

        rcCart.setLayoutManager(new LinearLayoutManager(this));
        rcCart.setAdapter(adapter);

        updateTotal();

        // Nút back
        btnBackCart.setOnClickListener(v -> onBackPressed());

        // Chuyển qua trang Payment
        btnPlaceOrder.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, PayMentActivity.class);
            startActivity(intent);
        });
    }

    private void updateTotal() {
        double total = CartManager.getTotal();
        txtTotalCart.setText("$" + (int) total);
    }

    @Override
    public void onCartChanged() {
        updateTotal();
    }
}