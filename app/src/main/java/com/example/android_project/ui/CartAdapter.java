package com.example.android_project.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide; // Import Glide
import com.example.android_project.R;
import com.example.android_project.data.CartManager;
import com.example.android_project.models.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    public interface CartListener {
        void onCartChanged();
    }

    private List<CartItem> list;
    private CartListener listener;
    private Context context; // Biến Context để dùng Glide

    // Constructor nhận Context
    public CartAdapter(Context context, List<CartItem> list, CartListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = list.get(position);

        // --- SỬA LỖI Ở ĐÂY ---
        // Dùng Glide tải ảnh từ URL (item.getFood().getImagePath())
        Glide.with(context)
                .load(item.getFood().getImagePath()) 
                .placeholder(R.drawable.ic_launcher_foreground) // Ảnh chờ
                .error(R.drawable.ic_launcher_background)       // Ảnh lỗi
                .into(holder.imgFood);
        
        // Các phần khác giữ nguyên
        holder.txtName.setText(item.getFood().getName());
        holder.txtPrice.setText("$" + (int) item.getFood().getPrice());
        holder.txtQty.setText(String.valueOf(item.getQuantity()));

        holder.btnPlus.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            notifyItemChanged(holder.getAdapterPosition());
            if (listener != null) listener.onCartChanged();
        });

        holder.btnMinus.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                notifyItemChanged(holder.getAdapterPosition());
            } else {
                CartManager.removeItem(item);
                notifyItemRemoved(holder.getAdapterPosition());
            }
            if (listener != null) listener.onCartChanged();
        });

        holder.btnRemove.setOnClickListener(v -> {
            CartManager.removeItem(item);
            notifyItemRemoved(holder.getAdapterPosition());
            if (listener != null) listener.onCartChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView txtName, txtPrice, txtQty;
        ImageButton btnMinus, btnPlus, btnRemove;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            // Đảm bảo các ID này khớp với file res/layout/item_cart.xml
            imgFood = itemView.findViewById(R.id.imgFoodCart);
            txtName = itemView.findViewById(R.id.txtNameCart);
            txtPrice = itemView.findViewById(R.id.txtPriceCart);
            txtQty = itemView.findViewById(R.id.txtQtyCart);
            btnMinus = itemView.findViewById(R.id.btnMinusCart);
            btnPlus = itemView.findViewById(R.id.btnPlusCart);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}