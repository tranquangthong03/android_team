package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Button logInButton = findViewById(R.id.btn_log_in);
        TextView signUpPrompt = findViewById(R.id.tv_switch_to_signup);
        ImageButton backButton = findViewById(R.id.btn_back_login);

        // 1. Xử lý nút LOG IN
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });

        // 2. Xử lý chuyển sang màn hình SIGN UP
        signUpPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        // 3. Xử lý nút Back
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, OnboardingActivity.class);
                // Nếu Onboarding đã có trong back stack thì xóa các màn phía trên
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                // Đóng màn hiện tại để không quay ngược lại nữa
                finish();
            }
        });

        // 4. Xử lý nút Quên Mật khẩu (Forgot Password)
        findViewById(R.id.tv_forgot_password).setOnClickListener(v -> {
            Toast.makeText(LogInActivity.this, "Chuyển đến màn hình Quên Mật khẩu", Toast.LENGTH_SHORT).show();
        });
    }

    private void handleLogin() {
        Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
        // Sửa thành HomeActivity.class khi đã phát triển
        navigateToMainScreen();
    }

    private void navigateToMainScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}