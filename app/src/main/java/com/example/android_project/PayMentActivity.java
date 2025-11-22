package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PayMentActivity extends AppCompatActivity {

    private ImageView btnBack;
    private Button btnAddNew, btnPayConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_activity);

        // Initialize views
        btnBack = findViewById(R.id.btnBack);
        btnPayConfirm = findViewById(R.id.btnPayConfirm);


        // Back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Add new card button
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở màn hình thêm thẻ mới
                Toast.makeText(PayMentActivity.this, "Add new card", Toast.LENGTH_SHORT).show();
            }
        });

        // Pay & Confirm button
        btnPayConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PayMentActivity.this, PayCongratActivity.class);
                startActivity(intent);
            }
        });
    }
}
