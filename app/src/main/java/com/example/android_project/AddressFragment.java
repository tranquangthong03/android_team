package com.example.android_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AddressFragment extends Fragment {

    private RecyclerView recyclerView;
    private AddressAdapter adapter;
    private List<AddressModel> addressList;
    private Button btnAddAddress;
    private ImageView btnBack;

    // --- 1. Launcher nhận địa chỉ từ Bản đồ (MapsOsmActivity) ---
    private final ActivityResultLauncher<Intent> mapLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        String newAddress = data.getStringExtra("new_address");
                        if (newAddress != null && !newAddress.isEmpty()) {
                            // Thêm địa chỉ mới vào danh sách
                            addressList.add(new AddressModel(R.drawable.home, "OTHER", newAddress));

                            // Cập nhật giao diện
                            adapter.notifyDataSetChanged();

                            Toast.makeText(getContext(), "Đã thêm: " + newAddress, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
    );

    public AddressFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);

        initViews(view);
        setupRecyclerView(); // Cấu hình List và chức năng Xóa
        setupEvents();       // Cấu hình nút Thêm và nút Back

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_address);
        btnBack = view.findViewById(R.id.btn_back);
        btnAddAddress = view.findViewById(R.id.btn_add_address); // Khớp ID với XML của bạn
    }

    private void setupRecyclerView() {
        addressList = new ArrayList<>();

        // Dữ liệu mẫu
        addressList.add(new AddressModel(R.drawable.home, "NHÀ RIÊNG", "123 Đường Nguyễn Văn Linh, Đà Nẵng"));
        addressList.add(new AddressModel(R.drawable.work, "CÔNG TY", "Tòa nhà Software Park, Đà Nẵng"));

        // --- KHỞI TẠO ADAPTER VỚI SỰ KIỆN XÓA ---
        // Tham số thứ 2 là Lambda function xử lý sự kiện xóa
        adapter = new AddressAdapter(addressList, position -> {
            deleteAddress(position);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    // --- Hàm xử lý logic Xóa ---
    private void deleteAddress(int position) {
        if (position >= 0 && position < addressList.size()) {
            // Xóa khỏi danh sách dữ liệu
            addressList.remove(position);

            // Cập nhật giao diện: Xóa hiệu ứng dòng đó
            adapter.notifyItemRemoved(position);
            // Cập nhật lại chỉ số (index) các dòng còn lại để tránh lỗi
            adapter.notifyItemRangeChanged(position, addressList.size());

            Toast.makeText(getContext(), "Đã xóa địa chỉ", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupEvents() {
        // Sự kiện nút Back
        btnBack.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        // Sự kiện nút Thêm địa chỉ -> Mở Map
        btnAddAddress.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MapsOsmActivity.class);
            mapLauncher.launch(intent);
        });
    }
}