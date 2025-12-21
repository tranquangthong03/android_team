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

import com.bumptech.glide.Glide;
import com.example.android_project.R;
import com.example.android_project.models.Food;

import java.util.List;

public class PaymentFoodAdapter extends RecyclerView.Adapter<PaymentFoodAdapter.PaymentFoodViewHolder> {

    private final List<Food> foods;
    private final Context context;

    public PaymentFoodAdapter(Context context, List<Food> foods) {
        this.context = context;
        this.foods = foods;
    }

    @NonNull
    @Override
    public PaymentFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food, parent, false);
        return new PaymentFoodViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentFoodViewHolder holder, int position) {
        Food food = foods.get(position);

        holder.txtName.setText(food.getName());

        // --- SỬA LỖI TẠI ĐÂY ---
        // Phải gọi đúng tên hàm trong Food.java là getRestaurantName()
        holder.txtRestaurant.setText(food.getRestaurantName());

        holder.txtPrice.setText("$" + (int) food.getPrice());

        // Load ảnh bằng Glide
        Glide.with(context)
                .load(food.getImagePath())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imgFood);

        // Sự kiện click nút Add (nếu cần)
        holder.btnAdd.setOnClickListener(v -> {
            // Xử lý logic thêm món hoặc hiển thị thông báo
        });
    }

    @Override
    public int getItemCount() {
        return foods != null ? foods.size() : 0;
    }

    static class PaymentFoodViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView txtName, txtRestaurant, txtPrice;
        ImageButton btnAdd;

        PaymentFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgFood);
            txtName = itemView.findViewById(R.id.txtName);
            txtRestaurant = itemView.findViewById(R.id.txtRestaurant);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }
}