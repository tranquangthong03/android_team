package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_project.models.Food;
import com.example.android_project.models.Order;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvoiceActivity extends AppCompatActivity {

    private TextView txtReceiver;
    private TextView txtItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        // 1. Ánh xạ View
        TextView txtOrderId = findViewById(R.id.txtOrderId);
        TextView txtDate = findViewById(R.id.txtDate);
        TextView txtAmount = findViewById(R.id.txtAmount);
        TextView txtMethod = findViewById(R.id.txtMethod);
        txtReceiver = findViewById(R.id.txtReceiver); // View mới
        txtItems = findViewById(R.id.txtItems);       // View mới
        Button btnHome = findViewById(R.id.btnHome);

        // 2. Nhận dữ liệu Order
        Order order = (Order) getIntent().getSerializableExtra("ORDER_DATA");

        if (order != null) {
            // Hiển thị thông tin cơ bản
            txtOrderId.setText("#" + (order.getOrderId().length() > 8 ? order.getOrderId().substring(0, 8).toUpperCase() : order.getOrderId()));
            txtDate.setText(order.getDate());
            txtMethod.setText(order.getPaymentMethod());
            txtAmount.setText("$" + (int)order.getTotalPrice());

            // 3. Xử lý hiển thị danh sách món ăn (Gộp số lượng)
            displayOrderItems(order.getItems());

            // 4. Lấy tên người nhận từ Firebase
            fetchUserName(order.getUserId());
        }

        // 5. Sự kiện nút Home
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(InvoiceActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    // Hàm đếm số lượng và hiển thị món ăn
    private void displayOrderItems(List<Food> items) {
        if (items == null || items.isEmpty()) {
            txtItems.setText("Không có thông tin món ăn");
            return;
        }

        // Sử dụng Map để đếm số lượng từng món (Key: Tên món, Value: Số lượng)
        Map<String, Integer> itemCountMap = new HashMap<>();
        for (Food food : items) {
            String name = food.getName();
            if (itemCountMap.containsKey(name)) {
                itemCountMap.put(name, itemCountMap.get(name) + 1);
            } else {
                itemCountMap.put(name, 1);
            }
        }

        // Xây dựng chuỗi hiển thị
        StringBuilder itemListBuilder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : itemCountMap.entrySet()) {
            itemListBuilder.append("- ")
                    .append(entry.getKey())
                    .append(" (x")
                    .append(entry.getValue())
                    .append(")\n");
        }

        txtItems.setText(itemListBuilder.toString().trim());
    }

    // Hàm lấy tên người dùng từ Firebase
    private void fetchUserName(String userId) {
        if (userId == null || userId.isEmpty()) {
            txtReceiver.setText("Khách");
            return;
        }

        FirebaseFirestore.getInstance().collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String name = documentSnapshot.getString("name");
                        // Nếu không có field "name", thử lấy "email" hoặc để mặc định
                        txtReceiver.setText(name != null ? name : "Khách hàng");
                    } else {
                        txtReceiver.setText("Khách hàng");
                    }
                })
                .addOnFailureListener(e -> txtReceiver.setText("Lỗi tải tên"));
    }
}