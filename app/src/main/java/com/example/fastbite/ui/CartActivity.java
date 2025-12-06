package com.example.fastbite.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastbite.R;
import com.example.fastbite.data.CartManager;
import com.example.fastbite.models.CartItem;

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

        btnBackCart.setOnClickListener(v -> onBackPressed());

        btnPlaceOrder.setOnClickListener(v ->
                Toast.makeText(this, "Đặt hàng thành công (demo)", Toast.LENGTH_SHORT).show()
        );
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
