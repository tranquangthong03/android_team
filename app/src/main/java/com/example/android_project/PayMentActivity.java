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

    // Các layout từng phương thức
    private RelativeLayout layoutMomo, layoutBank, layoutCash;

    // Các hộp chứa (Container)
    private LinearLayout containerLinked, containerOther;

    // Text chi tiết
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
        // Mỗi khi quay lại màn hình này, kiểm tra và sắp xếp lại vị trí
        refreshPaymentLayouts();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBackPTTToan);
        rcPayment = findViewById(R.id.rcPayment);
        txtTotalPayment = findViewById(R.id.txtTotalPayment);

        // Layout phương thức
        layoutMomo = findViewById(R.id.layout_momo);
        layoutBank = findViewById(R.id.layout_bank);
        layoutCash = findViewById(R.id.layout_cash);

        // Container
        containerLinked = findViewById(R.id.container_linked);
        containerOther = findViewById(R.id.container_other);

        // Text chi tiết
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

    // --- HÀM SẮP XẾP GIAO DIỆN ---
    private void refreshPaymentLayouts() {
        SharedPreferences prefs = getSharedPreferences("PaymentData", MODE_PRIVATE);
        boolean isMomoLinked = prefs.getBoolean("IS_MOMO_LINKED", false);
        boolean isBankLinked = prefs.getBoolean("IS_BANK_LINKED", false);

        // 1. Gỡ các view ra khỏi vị trí cũ để tránh lỗi
        removeViewFromParent(layoutMomo);
        removeViewFromParent(layoutBank);
        removeViewFromParent(layoutCash);

        // 2. Xử lý MOMO
        if (isMomoLinked) {
            // Đã liên kết -> Đưa lên trên (containerLinked)
            containerLinked.addView(layoutMomo);
            String number = prefs.getString("MOMO_NUMBER", "••8888");
            txtMomoDetail.setText("Đã liên kết: " + number);
            // SỬA LỖI MÀU Ở ĐÂY: Dùng android.R.color.holo_green_dark
            txtMomoDetail.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            // Chưa liên kết -> Đưa xuống dưới (containerOther)
            containerOther.addView(layoutMomo);
            txtMomoDetail.setText("Nhấn để liên kết");
            // SỬA LỖI MÀU Ở ĐÂY: Dùng android.R.color.darker_gray thay vì R.color.gray
            txtMomoDetail.setTextColor(getResources().getColor(android.R.color.darker_gray));
        }

        // 3. Xử lý BANK
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

        // 4. Xử lý TIỀN MẶT (Luôn nằm ở dưới)
        containerOther.addView(layoutCash);
    }

    private void removeViewFromParent(View view) {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    private void setupEvents() {
        btnBack.setOnClickListener(v -> finish());

        // CLICK MOMO
        layoutMomo.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("PaymentData", MODE_PRIVATE);
            if (prefs.getBoolean("IS_MOMO_LINKED", false)) {
                // Đã liên kết -> Chọn thanh toán
                selectedMethod = "MoMo";
                updateSelectedUI();
            } else {
                // Chưa liên kết -> Mở trang nhập thông tin
                openLinkActivity("MOMO");
            }
        });

        // CLICK BANK
        layoutBank.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("PaymentData", MODE_PRIVATE);
            if (prefs.getBoolean("IS_BANK_LINKED", false)) {
                selectedMethod = "Bank";
                updateSelectedUI();
            } else {
                openLinkActivity("BANK");
            }
        });

        // CLICK TIỀN MẶT
        layoutCash.setOnClickListener(v -> {
            selectedMethod = "Cash";
            updateSelectedUI();
        });

        // NÚT THANH TOÁN
        btnPayNow.setOnClickListener(v -> {
            processPayment();
        });
    }

    private void updateSelectedUI() {
        txtSelectedMethod.setText(selectedMethod);

        // Hiện icon check nếu chọn tiền mặt (bạn có thể mở rộng logic này cho các phương thức khác)
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

        String orderId = UUID.randomUUID().toString();
        double total = CartManager.getTotal();
        String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());

        List<Food> items = new ArrayList<>();
        for (CartItem item : CartManager.getCartItems()) {
            items.add(item.getFood());
        }

        Order newOrder = new Order(orderId, total, selectedMethod, currentDate, "Success", items);

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