package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PayMentActivity extends AppCompatActivity {

    private ImageView btnBack, btntt_TheNganHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tatca_phuongthuc_thanhtoan_activity);

        // Initialize views
        btnBack = findViewById(R.id.btnBackPTTToan);
        btntt_TheNganHang = findViewById(R.id.btntt_TheNganHang);
        btntt_TheNganHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayMentActivity.this, DanhSachNganHangActivity.class);
                startActivity(intent);
            }
        });

        // Back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
