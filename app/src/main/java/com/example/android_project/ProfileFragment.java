package com.example.android_project;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout; // <-- Quan trọng: Đổi từ TextView sang
// (Nếu có lỗi, hãy đảm bảo bạn import 'android.widget.LinearLayout' nhé)

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // --- ĐÂY LÀ PHẦN ĐÃ SỬA ---

        // 1. Tìm nút "Personal Info" (Giờ nó là LinearLayout)
        LinearLayout btnPersonalInfo = view.findViewById(R.id.btn_personal_info);

        // 2. Tìm nút "Addresses" (Giờ nó là LinearLayout)
        // Dòng này có thể là dòng 25 gây lỗi của bạn
        LinearLayout btnAddresses = view.findViewById(R.id.btn_addresses);

        // 3. Tìm nút "Log Out" (Giờ nó là LinearLayout)
        LinearLayout btnLogout = view.findViewById(R.id.btn_logout);


        // Gán sự kiện click cho nút "Addresses"
        btnAddresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang AddressFragment
                Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_addressFragment);
            }
        });

        // (Bạn có thể gán sự kiện cho các nút khác tương tự)
        // btnPersonalInfo.setOnClickListener(...)
        // btnLogout.setOnClickListener(...)

        return view;
    }
}