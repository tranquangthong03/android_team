package com.example.android_project;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.models.Order;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView rcOrders;
    private OrderHistoryAdapter adapter;
    private List<Order> orderList;
    private ImageView btnBack;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        rcOrders = findViewById(R.id.rcOrders);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        // Setup RecyclerView
        orderList = new ArrayList<>();
        adapter = new OrderHistoryAdapter(this, orderList);
        rcOrders.setLayoutManager(new LinearLayoutManager(this));
        rcOrders.setAdapter(adapter);

        // Load Data
        loadOrders();
    }

    private void loadOrders() {
        db = FirebaseFirestore.getInstance();

        // Lấy dữ liệu từ bảng 'orders', sắp xếp ngày mới nhất lên đầu
        // Lưu ý: Nếu muốn lọc theo User, cần thêm .whereEqualTo("userId", currentUserId)
        db.collection("orders")
                .orderBy("date", Query.Direction.DESCENDING) // Cần tạo Index trong Firebase Console nếu báo lỗi
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        orderList.clear();
                        for (DocumentSnapshot doc : task.getResult()) {
                            Order order = doc.toObject(Order.class);
                            if (order != null) {
                                orderList.add(order);
                            }
                        }
                        adapter.notifyDataSetChanged();

                        if(orderList.isEmpty()){
                            Toast.makeText(this, "Bạn chưa có đơn hàng nào", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Lỗi tải đơn hàng: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}