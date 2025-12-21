package com.example.android_project.ui;

<<<<<<< HEAD
import android.content.Context; // 1. Import Context
=======
import android.content.Context;
>>>>>>> huuhung
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

<<<<<<< HEAD
import com.bumptech.glide.Glide; // 2. Import Glide
=======
import com.bumptech.glide.Glide;
>>>>>>> huuhung
import com.example.android_project.R;
import com.example.android_project.models.Food;

import java.util.List;

public class PaymentFoodAdapter extends RecyclerView.Adapter<PaymentFoodAdapter.PaymentFoodViewHolder> {

    private final List<Food> foods;
<<<<<<< HEAD
    private final Context context; // 3. Khai báo Context

    // 4. Cập nhật Constructor để nhận Context
=======
    private final Context context;

>>>>>>> huuhung
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

<<<<<<< HEAD
        // --- SỬA LỖI TÊN HÀM ---
        holder.txtRestaurant.setText(food.getRestaurantName()); // Dùng getRestaurantName()
        holder.txtPrice.setText("$" + (int) food.getPrice());

        // --- SỬA LỖI ẢNH (Dùng Glide) ---
=======
        // --- SỬA LỖI TẠI ĐÂY ---
        // Phải gọi đúng tên hàm trong Food.java là getRestaurantName()
        holder.txtRestaurant.setText(food.getRestaurantName());

        holder.txtPrice.setText("$" + (int) food.getPrice());

        // Load ảnh bằng Glide
>>>>>>> huuhung
        Glide.with(context)
                .load(food.getImagePath())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imgFood);

<<<<<<< HEAD
        // Logic click nút add (nếu cần)
        holder.btnAdd.setOnClickListener(v -> {
            // Xử lý logic
=======
        // Sự kiện click nút Add (nếu cần)
        holder.btnAdd.setOnClickListener(v -> {
            // Xử lý logic thêm món hoặc hiển thị thông báo
>>>>>>> huuhung
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
            txtRestaurant = itemView.findViewById(R.id.txtRestaurant); // Đảm bảo ID này đúng trong item_food.xml
            txtPrice = itemView.findViewById(R.id.txtPrice);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }
}