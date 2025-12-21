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

// Implement Interface ngay tại Activity để code gọn hơn
public class CartActivity extends AppCompatActivity implements CartAdapter.CartListener {

    private RecyclerView rcCart;
    private TextView txtTotalCart;
    private ImageButton btnBackCart;
    private Button btnPlaceOrder;

    private CartAdapter adapter;
    private List<CartItem> cartItems; // Tên biến đúng là cartItems

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initViews();
        setupCart();
        setupEvents();
    }

    private void initViews() {
        // Đảm bảo ID này trùng với file xml activity_cart.xml
        rcCart = findViewById(R.id.rcCart);
        txtTotalCart = findViewById(R.id.txtTotalCart);
        btnBackCart = findViewById(R.id.btnBackCart);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
    }

    private void setupCart() {
        // 1. Lấy dữ liệu
        cartItems = CartManager.getCartItems();

        // 2. Khởi tạo Adapter
        // Tham số 1: Context (this)
        // Tham số 2: List dữ liệu (cartItems)
        // Tham số 3: Listener (this - vì Activity đã implements CartListener)
        adapter = new CartAdapter(this, cartItems, this);

        // 3. Setup RecyclerView
        rcCart.setLayoutManager(new LinearLayoutManager(this));
        rcCart.setAdapter(adapter);

        // 4. Cập nhật tổng tiền ban đầu
        updateTotal();
    }

    private void setupEvents() {
        // Nút back
        if (btnBackCart != null) {
            btnBackCart.setOnClickListener(v -> finish());
        }

        // Chuyển qua trang Payment
        if (btnPlaceOrder != null) {
            btnPlaceOrder.setOnClickListener(v -> {
                Intent intent = new Intent(CartActivity.this, PayMentActivity.class);
                startActivity(intent);
            });
        }
    }

    // Hàm tính tổng tiền (Tên đúng là updateTotal)
    private void updateTotal() {
        double total = CartManager.getTotal();
        if (txtTotalCart != null) {
            txtTotalCart.setText("$" + (int) total);
        }
    }

    // Sự kiện khi tăng/giảm số lượng trong Adapter gọi về
    @Override
    public void onCartChanged() {
        updateTotal(); // Gọi hàm cập nhật lại giá tiền
    }
}