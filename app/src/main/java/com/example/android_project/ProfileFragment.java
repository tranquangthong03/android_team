package com.example.android_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android_project.ui.CartActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    // Khai báo các biến View (phải khớp với loại View trong XML)
    private TextView tvName, tvEmail;
    private ImageView imgAvatar;

    // Các nút chức năng
    private LinearLayout btnEditProfile;
    private LinearLayout btnAddress;      // Đã sửa tên biến cho khớp
    private LinearLayout btnPaymentMethod;
    private LinearLayout btnProfileCart;
    private LinearLayout btnOrderHistory;
    private LinearLayout btnProfileFavorite;
    private LinearLayout btnNotification;
    private LinearLayout btnSettings;
    private LinearLayout btnSupport;
    private LinearLayout btnLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Liên kết với file XML giao diện mới
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initViews(view);
        setupEvents();
        loadUserProfile();

        return view;
    }

    private void initViews(View view) {
        // Ánh xạ ID từ XML sang Java (QUAN TRỌNG: ID phải khớp 100% với file XML)

        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        imgAvatar = view.findViewById(R.id.imgAvatar);

        // Nhóm Tài khoản
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnAddress = view.findViewById(R.id.btnAddress); // SỬA LỖI Ở ĐÂY (btn_addresses -> btnAddress)
        btnPaymentMethod = view.findViewById(R.id.btnPaymentMethod);

        // Nhóm Tiện ích
        btnProfileCart = view.findViewById(R.id.btnProfileCart);
        btnOrderHistory = view.findViewById(R.id.btnOrderHistory);
        btnProfileFavorite = view.findViewById(R.id.btnProfileFavorite);
        btnNotification = view.findViewById(R.id.btnNotification);

        // Nhóm Khác
        btnSettings = view.findViewById(R.id.btnSettings);
        btnSupport = view.findViewById(R.id.btnSupport);
        btnLogout = view.findViewById(R.id.btnLogout);
    }

    private void loadUserProfile() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) return;

        String uid = currentUser.getUid();
        FirebaseFirestore.getInstance().collection("users").document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String name = documentSnapshot.getString("name");
                        String email = documentSnapshot.getString("email");

                        if (name != null && tvName != null) tvName.setText(name);
                        if (email != null && tvEmail != null) tvEmail.setText(email);
                    }
                });
    }

    private void setupEvents() {
        // 1. Địa chỉ
        if (btnAddress != null) {
            btnAddress.setOnClickListener(v -> startActivity(new Intent(getActivity(), AddressActivity.class)));
        }

        // 2. Giỏ hàng
        if (btnProfileCart != null) {
            btnProfileCart.setOnClickListener(v -> startActivity(new Intent(getActivity(), CartActivity.class)));
        }

        // 3. Thanh toán
        if (btnPaymentMethod != null) {
            btnPaymentMethod.setOnClickListener(v -> startActivity(new Intent(getActivity(), PayMentActivity.class)));
        }

        // 4. Lịch sử đơn hàng
        if (btnOrderHistory != null) {
            btnOrderHistory.setOnClickListener(v -> startActivity(new Intent(getActivity(), OrderHistoryActivity.class)));
        }

        // --- CÁC NÚT MỚI (Hiện thông báo tạm thời) ---
        if (btnEditProfile != null) {
            btnEditProfile.setOnClickListener(v -> Toast.makeText(getActivity(), "Tính năng chỉnh sửa đang phát triển", Toast.LENGTH_SHORT).show());
        }
        if (btnProfileFavorite != null) {
            btnProfileFavorite.setOnClickListener(v -> Toast.makeText(getActivity(), "Mục yêu thích", Toast.LENGTH_SHORT).show());
        }
        if (btnNotification != null) {
            btnNotification.setOnClickListener(v -> Toast.makeText(getActivity(), "Không có thông báo mới", Toast.LENGTH_SHORT).show());
        }

        // 5. Đăng xuất
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                FirebaseAuth.getInstance().signOut();
                if (getActivity() != null) {
                    SharedPreferences prefs = getActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.clear();
                    editor.apply();

                    Intent intent = new Intent(getActivity(), LogInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    getActivity().finish();
                    Toast.makeText(getActivity(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}