package com.example.android_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private List<AddressModel> addressList;
    // 1. Khai báo Interface để gửi sự kiện ra ngoài
    private OnDeleteClickListener deleteListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    // 2. Cập nhật Constructor để nhận thêm Listener
    public AddressAdapter(List<AddressModel> addressList, OnDeleteClickListener listener) {
        this.addressList = addressList;
        this.deleteListener = listener;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        AddressModel item = addressList.get(position);

        holder.iconType.setImageResource(item.getIcon());
        holder.textType.setText(item.getType());
        holder.textAddress.setText(item.getAddress());

        // 3. Bắt sự kiện click vào nút Xóa (thùng rác)
        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                // Gửi vị trí (position) cần xóa ra ngoài Fragment xử lý
                deleteListener.onDeleteClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder {
        ImageView iconType;
        TextView textType;
        TextView textAddress;
        ImageView btnDelete; // 4. Khai báo nút xóa

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            iconType = itemView.findViewById(R.id.icon_type);
            textType = itemView.findViewById(R.id.text_type);
            textAddress = itemView.findViewById(R.id.text_address);

            // 5. Ánh xạ nút xóa
            // LƯU Ý: Bạn cần kiểm tra trong file item_address.xml xem ID của cái thùng rác là gì.
            // Mình giả sử ID là btn_delete. Nếu khác, hãy sửa lại dòng dưới đây.
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}