package com.example.android_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.R;
import com.example.android_project.models.Bank;

import java.util.List;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.BankViewHolder> {

    private Context context;
    private List<Bank> danhSachNganHang;

    public BankAdapter(Context context, List<Bank> danhSachNganHang) {
        this.context = context;
        this.danhSachNganHang = danhSachNganHang;
    }

    @NonNull
    @Override
    public BankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bank, parent, false);
        return new BankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BankViewHolder holder, int position) {
        Bank nganHang = danhSachNganHang.get(position);
        holder.logoNganHang.setImageResource(nganHang.getLogo());
        holder.tenNganHang.setText(nganHang.getTen());
        holder.tenDayDuNganHang.setText(nganHang.getTenDayDu());
    }

    @Override
    public int getItemCount() {
        return danhSachNganHang.size();
    }

    public static class BankViewHolder extends RecyclerView.ViewHolder {
        ImageView logoNganHang;
        TextView tenNganHang, tenDayDuNganHang;

        public BankViewHolder(@NonNull View itemView) {
            super(itemView);
            logoNganHang = itemView.findViewById(R.id.bank_logo);
            tenNganHang = itemView.findViewById(R.id.bank_name);
            tenDayDuNganHang = itemView.findViewById(R.id.bank_full_name);
        }
    }
}
