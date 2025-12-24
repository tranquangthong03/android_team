package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_project.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPhone, etPassword, etRetypePassword;
    private FirebaseAuth auth;
    private FirebaseFirestore db; // Khai báo Firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance(); // Khởi tạo Firestore

        // Ánh xạ view
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email_signup);
        etPhone = findViewById(R.id.et_phone_signup); // Thêm ánh xạ SĐT (nhớ thêm trong XML)
        etPassword = findViewById(R.id.et_password_signup);
        etRetypePassword = findViewById(R.id.et_retype_password_signup);

        Button btnSignUp = findViewById(R.id.btn_sign_up);
        ImageButton btnBack = findViewById(R.id.btn_back_signup);

        btnSignUp.setOnClickListener(v -> handleSignUp());
        btnBack.setOnClickListener(v -> finish());
    }

    private void handleSignUp() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String retypePassword = etRetypePassword.getText().toString().trim();

        // VALIDATION
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải từ 6 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(retypePassword)) {
            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. TẠO TÀI KHOẢN AUTHENTICATION
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        if (firebaseUser != null) {
                            // 2. LƯU THÔNG TIN USER VÀO FIRESTORE
                            saveUserToFirestore(firebaseUser.getUid(), name, email, phone);
                        }
                    } else {
                        Toast.makeText(this, "Đăng ký thất bại: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void saveUserToFirestore(String uid, String name, String email, String phone) {
        // Tạo đối tượng User, mặc định role là "user"
        User newUser = new User(uid, name, email, phone, "user");

        // Lưu vào Collection "users" với Document ID chính là UID của tài khoản
        db.collection("users").document(uid)
                .set(newUser)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                    // Đăng xuất ngay để người dùng đăng nhập lại (hoặc chuyển thẳng vào Home)
                    auth.signOut();

                    navigateToLogin();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Lỗi lưu dữ liệu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        finish();
    }
}