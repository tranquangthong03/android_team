package com.example.android_project.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    // --- CONSTRUCTOR 1: Mặc định ---
    // Dùng cho trang FoodActivity (dùng layout item_food mặc định)
    public FoodAdapter(Context context, List<Food> mListFood, FoodListener mListener) {
        this.context = context;
        this.mListFood = mListFood;
        this.mListener = mListener;
        this.layoutId = R.layout.item_food; // Mặc định layout to
    }

    // --- CONSTRUCTOR 2: Tùy chỉnh Layout ---
    // Dùng cho trang Home (truyền R.layout.item_food_home vào đây)
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

        // 1. Gán dữ liệu Text
        holder.txtName.setText(food.getName());

        // Format giá tiền (ép kiểu int cho gọn hoặc dùng %.2f nếu muốn hiển thị số lẻ)
        holder.txtPrice.setText("$" + (int) food.getPrice());

        // Kiểm tra null vì layout nhỏ ở Home có thể không có TextView này
        if (holder.txtRestaurant != null) {
            holder.txtRestaurant.setText(food.getRestaurantName());
        }

        // 2. Load ảnh bằng Glide
        Glide.with(context)
                .load(food.getImagePath())
                .placeholder(R.drawable.ic_launcher_foreground) // Ảnh chờ
                .error(R.drawable.ic_launcher_background)       // Ảnh lỗi
                .into(holder.imgFood);

        // 3. Sự kiện Click vào cả dòng -> Xem chi tiết
        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) mListener.onFoodClick(food);
        });

        // 4. Sự kiện Click nút Add (+) -> Thêm vào giỏ
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
        View btnAdd; // Dùng View để linh hoạt (có thể là ImageView hoặc Button)

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ ID (Đảm bảo các ID này tồn tại trong các file xml item_food và item_food_home)
            imgFood = itemView.findViewById(R.id.imgFood);
            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtRestaurant = itemView.findViewById(R.id.txtRestaurant); // Có thể null ở layout nhỏ
            btnAdd = itemView.findViewById(R.id.btnAdd); // Nút cộng (+)
        }
    }

    // --- INTERFACE ---
    public interface FoodListener {
        void onFoodClick(Food food);
        void onAddToCartClick(Food food);
    }
}