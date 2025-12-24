package com.example.android_project;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
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
    private EditText searchEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danh_sach_ngan_hang_activity);

        ImageView btnBack = findViewById(R.id.btnBackNHLienKet);
        btnBack.setOnClickListener(v -> {
            finish(); // Đóng activity hiện tại để quay về màn hình trước
        });

        recyclerViewNganHang = findViewById(R.id.recycler_view_banks);
        recyclerViewNganHang.setLayoutManager(new LinearLayoutManager(this));

        taiDuLieuNganHang();

        adapterNganHang = new BankAdapter(this, danhSachNganHang);
        recyclerViewNganHang.setAdapter(adapterNganHang);

        searchEditText = findViewById(R.id.search_edit_text);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapterNganHang.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void taiDuLieuNganHang() {
        danhSachNganHang = new ArrayList<>();
        // Thêm dữ liệu ngân hàng mẫu
        danhSachNganHang.add(new Bank(R.drawable.bank_card, "MBBank (MB)", "Ngân hàng TMCP Quân đội"));
        danhSachNganHang.add(new Bank(R.drawable.bank_card, "MBV", "Ngân hàng TNHH MTV Việt Nam Hiện Đại (MBV)"));
        danhSachNganHang.add(new Bank(R.drawable.bank_card, "Agribank (VBA)", "Ngân hàng Nông nghiệp và Phát triển Nông thôn Việt Nam"));
        danhSachNganHang.add(new Bank(R.drawable.bank_card, "Vietcombank (VCB)", "Ngân hàng TMCP Ngoại thương Việt Nam"));
        danhSachNganHang.add(new Bank(R.drawable.bank_card, "Vietinbank (CTG)", "Ngân hàng TMCP Công thương Việt Nam"));
        danhSachNganHang.add(new Bank(R.drawable.bank_card, "BIDV", "Ngân hàng TMCP Đầu tư và phát triển Việt Nam"));
    }

}
