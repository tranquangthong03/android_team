package com.example.android_project;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddPaymentActivity extends AppCompatActivity {

    private EditText edtAccountNumber;
    private Button btnConfirmLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        edtAccountNumber = findViewById(R.id.edtAccountNumber);
        btnConfirmLink = findViewById(R.id.btnConfirmLink);

        String methodType = getIntent().getStringExtra("METHOD_TYPE"); // "MOMO" hoặc "BANK"

        btnConfirmLink.setOnClickListener(v -> {
            String number = edtAccountNumber.getText().toString();

            if (number.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences prefs = getSharedPreferences("PaymentData", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            if ("MOMO".equals(methodType)) {
                editor.putBoolean("IS_MOMO_LINKED", true);
                editor.putString("MOMO_NUMBER", number);
            } else if ("BANK".equals(methodType)) {
                editor.putBoolean("IS_BANK_LINKED", true);
                editor.putString("BANK_NUMBER", number);
            }

            editor.apply();


            Toast.makeText(this, "Liên kết thành công!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}