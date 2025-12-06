package com.example.android_project.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

<<<<<<< HEAD
import android.content.Intent;
=======
>>>>>>> fdc25bd3ffa2a4dd2f5063cfe4b59cea4dc69414
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
<<<<<<< HEAD

import com.example.android_project.PayMentActivity;
=======
import android.widget.Toast;

>>>>>>> fdc25bd3ffa2a4dd2f5063cfe4b59cea4dc69414
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

        cartItems = CartManager.getCartItems();

        adapter = new CartAdapter(cartItems, this);
        rcCart.setLayoutManager(new LinearLayoutManager(this));
        rcCart.setAdapter(adapter);

        updateTotal();

<<<<<<< HEAD
        // nút back
        btnBackCart.setOnClickListener(v -> onBackPressed());

        // ⭐ CHUYỂN QUA TRANG PAYMENT
        btnPlaceOrder.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, PayMentActivity.class);
            startActivity(intent);
        });
=======
        btnBackCart.setOnClickListener(v -> onBackPressed());

        btnPlaceOrder.setOnClickListener(v ->
                Toast.makeText(this, "Đặt hàng thành công (demo)", Toast.LENGTH_SHORT).show()
        );
>>>>>>> fdc25bd3ffa2a4dd2f5063cfe4b59cea4dc69414
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
