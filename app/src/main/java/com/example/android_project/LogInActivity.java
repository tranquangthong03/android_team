package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText emailInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Ánh xạ input email + password
        emailInput = findViewById(R.id.et_email_login);
        passwordInput = findViewById(R.id.et_password_login);

        Button logInButton = findViewById(R.id.btn_log_in);
        TextView signUpPrompt = findViewById(R.id.tv_switch_to_signup);
        ImageButton backButton = findViewById(R.id.btn_back_login);

        // 1️⃣ Xử lý nút LOG IN
        logInButton.setOnClickListener(v -> handleLogin());

        // 2️⃣ Chuyển sang SIGN UP
        signUpPrompt.setOnClickListener(v -> {
            Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        // 3️⃣ Back về Onboarding
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(LogInActivity.this, OnboardingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        // 4️⃣ Quên mật khẩu
        findViewById(R.id.tv_forgot_password).setOnClickListener(v ->
                Toast.makeText(LogInActivity.this, "Tính năng này đang phát triển", Toast.LENGTH_SHORT).show()
        );
    }

    private void handleLogin() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Kiểm tra dữ liệu
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ email và mật khẩu!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Đăng nhập Firebase
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LogInActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        navigateToMainScreen();
                    } else {
                        Toast.makeText(LogInActivity.this,
                                "Đăng nhập thất bại: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void navigateToMainScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
