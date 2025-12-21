package com.example.android_project.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton; // Hoặc ImageView tùy layout của bạn
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android_project.R;
import com.example.android_project.models.Food;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private Context context;
    private List<Food> mListFood;
    private FoodListener mListener;
    private int layoutId;

    // --- CONSTRUCTOR 1: Dùng cho FoodActivity (Mặc định layout to) ---
    // Nhận 3 tham số: Context, List, Listener
    public FoodAdapter(Context context, List<Food> mListFood, FoodListener mListener) {
        this.context = context;
        this.mListFood = mListFood;
        this.mListener = mListener;
        this.layoutId = R.layout.item_food; // Mặc định dùng layout item_food
    }

    // --- CONSTRUCTOR 2: Dùng cho HomeActivity (Layout nhỏ tùy chỉnh) ---
    // Nhận 4 tham số: Context, List, layoutId, Listener
    public FoodAdapter(Context context, List<Food> mListFood, int layoutId, FoodListener mListener) {
        this.context = context;
        this.mListFood = mListFood;
        this.layoutId = layoutId;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = mListFood.get(position);
        if (food == null) return;

        // 1. Gán dữ liệu text
        holder.txtName.setText(food.getName());
        holder.txtPrice.setText(String.format("$%.2f", food.getPrice())); // Định dạng giá tiền

        // Kiểm tra null để tránh lỗi nếu layout home không có view này
        if (holder.txtRestaurant != null) {
            holder.txtRestaurant.setText(food.getRestaurantName());
        }

        // 2. Load ảnh bằng Glide (Cần Context)
        if (context != null) {
            Glide.with(context)
                    .load(food.getImagePath())
                    .placeholder(R.drawable.ic_launcher_foreground) // Ảnh chờ
                    .error(R.drawable.ic_launcher_background)       // Ảnh lỗi
                    .into(holder.imgFood);
        }

        // 3. Bắt sự kiện click
        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) mListener.onFoodClick(food);
        });

        if (holder.btnAdd != null) {
            holder.btnAdd.setOnClickListener(v -> {
                if (mListener != null) mListener.onAddToCartClick(food);
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListFood != null ? mListFood.size() : 0;
    }

    // --- VIEW HOLDER ---
    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView txtName, txtPrice, txtRestaurant;
        View btnAdd; // Dùng View để có thể là Button hoặc ImageButton/ImageView

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ ID (Đảm bảo các ID này có trong file xml item_food và item_food_home)
            imgFood = itemView.findViewById(R.id.imgFood);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtRestaurant = itemView.findViewById(R.id.txtRestaurant); // Có thể null ở layout nhỏ
            btnAdd = itemView.findViewById(R.id.btnAdd); // Có thể null
        }
    }

    // --- INTERFACE ---
    public interface FoodListener {
        void onFoodClick(Food food);
        void onAddToCartClick(Food food);
    }
}