package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button signUpButton = findViewById(R.id.btn_sign_up);
        ImageButton backButton = findViewById(R.id.btn_back_signup);

        // Xử lý nút SIGN UP
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignUp();
            }
        });

        // Xử lý nút Back
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Quay lại Activity trước đó (MainActivity hoặc LogInActivity)
            }
        });
    }

    private void handleSignUp() {
        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
        // Sửa thành HomeActivity.class khi đã phát triển
        navigateToMainScreen();
    }

    private void navigateToMainScreen() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}