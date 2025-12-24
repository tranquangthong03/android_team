package com.example.android_project;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.adapter.BankAdapter;
import com.example.android_project.models.Bank;

import java.util.ArrayList;
import java.util.List;

public class DanhSachNganHangActivity extends AppCompatActivity {

    private RecyclerView recyclerViewNganHang;
    private BankAdapter adapterNganHang;
    private List<Bank> danhSachNganHang;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danh_sach_ngan_hang_activity);

        ImageView btnBack = findViewById(R.id.btnBackNHLienKet);
        btnBack.setOnClickListener(v -> {
            finish(); // Đóng activity hiện tại để quay về màn hình trước
        });

        danhSachNganHang = new ArrayList<>();
        // Thêm dữ liệu ngân hàng mẫu
        danhSachNganHang.add(new Bank(R.drawable.facebook, "MBBank (MB)", "Ngân hàng TMCP Quân đội"));
        danhSachNganHang.add(new Bank(R.drawable.facebook, "MBV", "Ngân hàng TNHH MTV Việt Nam Hiện Đại (MBV)"));
        danhSachNganHang.add(new Bank(R.drawable.facebook, "Agribank (VBA)", "Ngân hàng Nông nghiệp và Phát triển Nông thôn Việt Nam"));
        danhSachNganHang.add(new Bank(R.drawable.facebook, "Vietcombank (VCB)", "Ngân hàng TMCP Ngoại thương Việt Nam"));
        danhSachNganHang.add(new Bank(R.drawable.facebook, "Vietinbank (CTG)", "Ngân hàng TMCP Công thương Việt Nam"));
        danhSachNganHang.add(new Bank(R.drawable.facebook, "BIDV", "Ngân hàng TMCP Đầu tư và phát triển Việt Nam"));

        recyclerViewNganHang = findViewById(R.id.recycler_view_banks);
        recyclerViewNganHang.setLayoutManager(new LinearLayoutManager(this));

        adapterNganHang = new BankAdapter(this, danhSachNganHang);
        recyclerViewNganHang.setAdapter(adapterNganHang);
    }

}
