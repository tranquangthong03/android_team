package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.data.CartManager;
import com.example.android_project.models.CartItem;
import com.example.android_project.models.Food;
import com.example.android_project.ui.PaymentFoodAdapter;

import java.util.ArrayList;
import java.util.List;

public class PayMentActivity extends AppCompatActivity {

    private ImageView btnBack, btntt_TheNganHang;
    private RecyclerView rcPayment; // Biến cho danh sách món ăn
    private TextView txtTotalPayment; // Biến hiển thị tổng tiền

    private PaymentFoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tatca_phuongthuc_thanhtoan_activity);

        // 1. Ánh xạ View
        btnBack = findViewById(R.id.btnBackPTTToan);
        btntt_TheNganHang = findViewById(R.id.btntt_TheNganHang);

        // Bạn cần thêm RecyclerView có id là rcPayment vào file xml layout nhé
        rcPayment = findViewById(R.id.rcPayment);
        txtTotalPayment = findViewById(R.id.txtTotalPayment); // Thêm TextView tổng tiền vào xml nếu cần

        // 2. Setup RecyclerView và Adapter
        setupPaymentList();

        // 3. Sự kiện Click
        btntt_TheNganHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayMentActivity.this, DanhSachNganHangActivity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Hiển thị tổng tiền
        if (txtTotalPayment != null) {
            double total = CartManager.getTotal();
            txtTotalPayment.setText("Tổng thanh toán: $" + (int) total);
        }
    }

    private void setupPaymentList() {
        // Lấy danh sách từ Giỏ hàng
        List<CartItem> cartItems = CartManager.getCartItems();

        List<Food> foodsToPay = new ArrayList<>();
        for (CartItem item : cartItems) {
            foodsToPay.add(item.getFood());
        }

        // --- KHỞI TẠO ADAPTER (QUAN TRỌNG) ---
        // Truyền 'this' vào tham số đầu tiên để Glide có Context hoạt động
        adapter = new PaymentFoodAdapter(this, foodsToPay);

        rcPayment.setLayoutManager(new LinearLayoutManager(this));
        rcPayment.setAdapter(adapter);
    }
}