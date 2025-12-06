package com.example.fastbite.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastbite.R;
import com.example.fastbite.data.CartManager;
import com.example.fastbite.models.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    public interface CartListener {
        void onCartChanged();
    }

    private List<CartItem> list;
    private CartListener listener;

    public CartAdapter(List<CartItem> list, CartListener listener) {
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

        holder.imgFood.setImageResource(item.getFood().getImageResId());
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
        return list.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView txtName, txtPrice, txtQty;
        ImageButton btnMinus, btnPlus, btnRemove;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
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
