package com.example.android_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailInput;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        auth = FirebaseAuth.getInstance();

        emailInput = findViewById(R.id.et_email_forgot);
        Button btnSend = findViewById(R.id.btn_send_reset);
        ImageButton btnBack = findViewById(R.id.btn_back_forgot);

        btnBack.setOnClickListener(v -> finish());

        btnSend.setOnClickListener(v -> handleResetPassword());
    }

    private void handleResetPassword() {
        String email = emailInput.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(
                                this,
                                "Đã gửi email đổi mật khẩu. Vui lòng kiểm tra hộp thư!",
                                Toast.LENGTH_LONG
                        ).show();
                        finish(); // Quay lại Login
                    } else {
                        Toast.makeText(
                                this,
                                "Lỗi: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}
