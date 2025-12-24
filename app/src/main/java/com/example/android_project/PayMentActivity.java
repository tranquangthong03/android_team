package com.example.android_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.data.CartManager;
import com.example.android_project.models.CartItem;
import com.example.android_project.models.Food;
import com.example.android_project.models.Order;
import com.example.android_project.ui.PaymentFoodAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class PayMentActivity extends AppCompatActivity {

    private ImageView btnBack;
    private RecyclerView rcPayment;
    private TextView txtTotalPayment;
    private PaymentFoodAdapter adapter;

    private RelativeLayout layoutMomo, layoutBank, layoutCash;
    private LinearLayout containerLinked, containerOther;
    private TextView txtMomoDetail, txtBankDetail, txtSelectedMethod;
    private ImageView imgCashCheck;

    private Button btnPayNow;
    private String selectedMethod = "";

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tatca_phuongthuc_thanhtoan_activity);

        db = FirebaseFirestore.getInstance();

        initViews();
        setupPaymentList();
        setupEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshPaymentLayouts();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBackPTTToan);
        rcPayment = findViewById(R.id.rcPayment);
        txtTotalPayment = findViewById(R.id.txtTotalPayment);

        layoutMomo = findViewById(R.id.layout_momo);
        layoutBank = findViewById(R.id.layout_bank);
        layoutCash = findViewById(R.id.layout_cash);

        containerLinked = findViewById(R.id.container_linked);
        containerOther = findViewById(R.id.container_other);

        txtMomoDetail = findViewById(R.id.txt_momo_detail);
        txtBankDetail = findViewById(R.id.txt_bank_detail);
        txtSelectedMethod = findViewById(R.id.txt_selected_method);
        imgCashCheck = findViewById(R.id.img_cash_check);

        btnPayNow = findViewById(R.id.btnPayNow);
    }

    private void setupPaymentList() {
        List<CartItem> cartItems = CartManager.getCartItems();
        List<Food> foodsToPay = new ArrayList<>();
        for (CartItem item : cartItems) {
            foodsToPay.add(item.getFood());
        }
        adapter = new PaymentFoodAdapter(this, foodsToPay);
        rcPayment.setLayoutManager(new LinearLayoutManager(this));
        rcPayment.setAdapter(adapter);

        if (txtTotalPayment != null) {
            txtTotalPayment.setText("$" + (int) CartManager.getTotal());
        }
    }

    private void refreshPaymentLayouts() {
        SharedPreferences prefs = getSharedPreferences("PaymentData", MODE_PRIVATE);
        boolean isMomoLinked = prefs.getBoolean("IS_MOMO_LINKED", false);
        boolean isBankLinked = prefs.getBoolean("IS_BANK_LINKED", false);

        removeViewFromParent(layoutMomo);
        removeViewFromParent(layoutBank);
        removeViewFromParent(layoutCash);

        if (isMomoLinked) {
            containerLinked.addView(layoutMomo);
            String number = prefs.getString("MOMO_NUMBER", "••8888");
            txtMomoDetail.setText("Đã liên kết: " + number);
            txtMomoDetail.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            containerOther.addView(layoutMomo);
            txtMomoDetail.setText("Nhấn để liên kết");
            txtMomoDetail.setTextColor(getResources().getColor(android.R.color.darker_gray));
        }

        if (isBankLinked) {
            containerLinked.addView(layoutBank);
            String number = prefs.getString("BANK_NUMBER", "••VISA");
            txtBankDetail.setText("Đã liên kết thẻ: " + number);
            txtBankDetail.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            containerOther.addView(layoutBank);
            txtBankDetail.setText("Nhấn để liên kết");
            txtBankDetail.setTextColor(getResources().getColor(android.R.color.darker_gray));
        }

        containerOther.addView(layoutCash);
    }

    private void removeViewFromParent(View view) {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    private void setupEvents() {
        btnBack.setOnClickListener(v -> finish());

        layoutMomo.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("PaymentData", MODE_PRIVATE);
            if (prefs.getBoolean("IS_MOMO_LINKED", false)) {
                selectedMethod = "MoMo";
                updateSelectedUI();
            } else {
                openLinkActivity("MOMO");
            }
        });

        layoutBank.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("PaymentData", MODE_PRIVATE);
            if (prefs.getBoolean("IS_BANK_LINKED", false)) {
                selectedMethod = "Bank";
                updateSelectedUI();
            } else {
                openLinkActivity("BANK");
            }
        });

        layoutCash.setOnClickListener(v -> {
            selectedMethod = "Cash";
            updateSelectedUI();
        });

        btnPayNow.setOnClickListener(v -> processPayment());
    }

    private void updateSelectedUI() {
        txtSelectedMethod.setText(selectedMethod);
        if (imgCashCheck != null) {
            imgCashCheck.setVisibility(selectedMethod.equals("Cash") ? View.VISIBLE : View.GONE);
        }
        Toast.makeText(this, "Đã chọn: " + selectedMethod, Toast.LENGTH_SHORT).show();
    }

    private void openLinkActivity(String type) {
        Intent intent = new Intent(PayMentActivity.this, AddPaymentActivity.class);
        intent.putExtra("METHOD_TYPE", type);
        startActivity(intent);
    }

    private void processPayment() {
        if (selectedMethod.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn phương thức thanh toán!", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "Vui lòng đăng nhập để thanh toán", Toast.LENGTH_SHORT).show();
            return;
        }

        String orderId = UUID.randomUUID().toString();
        double total = CartManager.getTotal();
        String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());

        List<Food> items = new ArrayList<>();
        for (CartItem item : CartManager.getCartItems()) {
            items.add(item.getFood());
        }

        Order newOrder = new Order(orderId, total, selectedMethod, currentDate, "Success", items, user.getUid());

        db.collection("orders").document(orderId)
                .set(newOrder)
                .addOnSuccessListener(aVoid -> {
                    CartManager.clearCart();
                    Intent intent = new Intent(PayMentActivity.this, InvoiceActivity.class);
                    intent.putExtra("ORDER_DATA", newOrder);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Lỗi thanh toán: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}