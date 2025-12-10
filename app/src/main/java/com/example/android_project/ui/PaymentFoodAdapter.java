package com.example.android_project.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.R;
import com.example.android_project.models.Food;

import java.util.List;

public class PaymentFoodAdapter extends RecyclerView.Adapter<PaymentFoodAdapter.PaymentFoodViewHolder> {

    private final List<Food> foods;

    public PaymentFoodAdapter(List<Food> foods) {
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
        holder.txtRestaurant.setText(food.getRestaurant());
        holder.txtPrice.setText("$" + (int) food.getPrice());
        holder.imgFood.setImageResource(food.getImageResId());

        // Nút thêm món có thể ẩn hoặc xử lý logic thêm vào giỏ hàng
        holder.btnAdd.setOnClickListener(v -> {
            // Có thể thêm logic xử lý ở đây nếu cần
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

