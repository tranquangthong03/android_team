package com.example.android_project;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// 1. Adapter cần kế thừa RecyclerView.Adapter
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    // 2. Danh sách dữ liệu
    private List<AddressModel> addressList;

    public AddressAdapter(List<AddressModel> addressList) {
        this.addressList = addressList;
    }

    // 3. ViewHolder: Chứa các View trong file item_address.xml
    public static class AddressViewHolder extends RecyclerView.ViewHolder {
        ImageView iconType;
        TextView textType;
        TextView textAddress;
        // (Bạn có thể thêm iconEdit, iconDelete ở đây nếu cần xử lý click)

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ (tìm) các View bằng ID
            iconType = itemView.findViewById(R.id.icon_type);
            textType = itemView.findViewById(R.id.text_type);
            textAddress = itemView.findViewById(R.id.text_address);
        }
    }

    // 4. onCreateViewHolder: Tạo (inflate) layout cho mỗi hàng
    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_address, parent, false);
        return new AddressViewHolder(view);
    }

    // 5. onBindViewHolder: Gán dữ liệu từ List vào View
    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        AddressModel item = addressList.get(position);

        holder.iconType.setImageResource(item.getIcon());
        holder.textType.setText(item.getType());
        holder.textAddress.setText(item.getAddress());
    }

    // 6. getItemCount: Trả về số lượng item trong List
    @Override
    public int getItemCount() {
        return addressList.size();
    }
}