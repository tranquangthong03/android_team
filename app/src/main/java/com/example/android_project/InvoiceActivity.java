package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.android_project.models.Order;

public class InvoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        TextView txtOrderId = findViewById(R.id.txtOrderId);
        TextView txtDate = findViewById(R.id.txtDate);
        TextView txtAmount = findViewById(R.id.txtAmount);
        TextView txtMethod = findViewById(R.id.txtMethod);
        Button btnHome = findViewById(R.id.btnHome);

        // Nhận dữ liệu Order từ trang thanh toán
        Order order = (Order) getIntent().getSerializableExtra("ORDER_DATA");

        if (order != null) {
            txtOrderId.setText("Mã đơn: " + order.getOrderId().substring(0, 8).toUpperCase());
            txtDate.setText("Thời gian: " + order.getDate());
            txtMethod.setText("Thanh toán qua: " + order.getPaymentMethod());
            txtAmount.setText("Tổng tiền: $" + (int)order.getTotalPrice());
        }

        btnHome.setOnClickListener(v -> {
            // Quay về trang chủ và xóa hết các activity cũ trong stack
            Intent intent = new Intent(InvoiceActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}