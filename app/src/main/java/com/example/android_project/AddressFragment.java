package com.example.android_project;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager; // <-- Import
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList; // <-- Import
import java.util.List;      // <-- Import

public class AddressFragment extends Fragment {

    // Khai báo biến
    RecyclerView recyclerView;
    AddressAdapter adapter;
    List<AddressModel> addressList;

    public AddressFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_address, container, false);

        // --- Xử lý nút Back ---
        ImageView btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        // --- BẮT ĐẦU PHẦN HIỂN THỊ DANH SÁCH ---

        // 1. Tìm RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_address);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Set layout

        // 2. Chuẩn bị dữ liệu (tạo dữ liệu giả)
        addressList = new ArrayList<>();

        // Thêm item "HOME"
        // (Chúng ta dùng R.drawable.home và work vì bạn đã đặt tên file .png như vậy)
        addressList.add(new AddressModel(
                R.drawable.home,
                "HOME",
                "2464 Royal Ln. Mesa, New Jersey 45463"
        ));

        // Thêm item "WORK"
        addressList.add(new AddressModel(
                R.drawable.work,
                "WORK",
                "3891 Ranchview Dr. Richardson, California 62639"
        ));

        // 3. Tạo Adapter
        adapter = new AddressAdapter(addressList);

        // 4. Set Adapter cho RecyclerView
        recyclerView.setAdapter(adapter);

        // --- KẾT THÚC PHẦN HIỂN THỊ DANH SÁCH ---

        return view;
    }
}