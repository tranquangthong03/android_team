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

    // Constructor nhận Context và danh sách món ăn
    public PaymentFoodAdapter(Context context, List<Food> foods) {
        this.context = context;
        this.foods = foods;
    }

    @NonNull
    @Override
    public PaymentFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Sử dụng layout item_food (dùng chung với màn hình danh sách món ăn)
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food, parent, false);
        return new PaymentFoodViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentFoodViewHolder holder, int position) {
        Food food = foods.get(position);
        if (food == null) return;

        // 1. Gán dữ liệu Text
        holder.txtName.setText(food.getName());

        // Sử dụng đúng getter của Model Food
        holder.txtRestaurant.setText(food.getRestaurantName());

        holder.txtPrice.setText("$" + (int) food.getPrice());

        // 2. Load ảnh bằng Glide
        Glide.with(context)
                .load(food.getImagePath())
                .placeholder(R.drawable.ic_launcher_foreground) // Ảnh chờ
                .error(R.drawable.ic_launcher_background)       // Ảnh lỗi
                .into(holder.imgFood);

        // 3. Sự kiện click nút Add (Nếu trang thanh toán không cần nút này, bạn có thể ẩn đi)
        holder.btnAdd.setOnClickListener(v -> {
            // Xử lý logic tại đây nếu cần (ví dụ: Toast thông báo)
        });
    }

    @Override
    public int getItemCount() {
        return foods != null ? foods.size() : 0;
    }

    static class PaymentFoodViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView txtName, txtRestaurant, txtPrice;
        ImageButton btnAdd; // Lưu ý: Kiểm tra file xml xem là ImageButton hay View

        PaymentFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ ID (Đảm bảo ID khớp với layout item_food.xml)
            imgFood = itemView.findViewById(R.id.imgFood);
            txtName = itemView.findViewById(R.id.txtName);
            txtRestaurant = itemView.findViewById(R.id.txtRestaurant);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }
}