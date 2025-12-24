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
import com.google.firebase.auth.FirebaseUser; // Import thêm để lấy thông tin User

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText emailInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // Khởi tạo Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Ánh xạ View (Đảm bảo ID khớp với layout XML của bạn)
        emailInput = findViewById(R.id.et_email_login);
        passwordInput = findViewById(R.id.et_password_login);
        
        Button logInButton = findViewById(R.id.btn_log_in);
        TextView signUpPrompt = findViewById(R.id.tv_switch_to_signup);
        ImageButton backButton = findViewById(R.id.btn_back_login);
        TextView forgotPassword = findViewById(R.id.tv_forgot_password);

        // 1. Sự kiện nút LOG IN
        logInButton.setOnClickListener(v -> handleLogin());

        // 2. Chuyển sang SIGN UP
        signUpPrompt.setOnClickListener(v -> {
            Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        // 3. Back về Onboarding
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(LogInActivity.this, OnboardingActivity.class);
            // Xóa cờ để tránh chồng chéo Activity không mong muốn
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        // 4. Quên mật khẩu
        forgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LogInActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void handleLogin() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Kiểm tra dữ liệu đầu vào
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ email và mật khẩu!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Thực hiện đăng nhập Firebase
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LogInActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        navigateToMainScreen();
                    } else {
                        // Hiển thị lỗi chi tiết (nếu có)
                        String errorMsg = task.getException() != null ? task.getException().getMessage() : "Lỗi không xác định";
                        Toast.makeText(LogInActivity.this, "Đăng nhập thất bại: " + errorMsg, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void navigateToMainScreen() {
        Intent intent = new Intent(LogInActivity.this, HomeActivity.class);

        // --- LOGIC MỚI: Gửi tên người dùng sang HomeActivity ---
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            // Cách 1: Lấy tên hiển thị (nếu lúc đăng ký có lưu DisplayName)
            String nameToDisplay = user.getDisplayName();

            // Cách 2: Nếu chưa có tên, cắt lấy phần đầu của email (ví dụ: thong@gmail.com -> thong)
            if (nameToDisplay == null || nameToDisplay.isEmpty()) {
                String email = user.getEmail();
                if (email != null && email.contains("@")) {
                    nameToDisplay = email.split("@")[0];
                } else {
                    nameToDisplay = "User"; // Tên mặc định
                }
            }
            
            // Đóng gói tên vào Intent với khóa "USER_NAME" (phải khớp bên HomeActivity)
            intent.putExtra("USER_NAME", nameToDisplay);
        }

        // Xóa LoginActivity khỏi Back Stack để user không bấm Back quay lại màn hình Login được
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}