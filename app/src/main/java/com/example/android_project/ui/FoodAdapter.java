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

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<Food> mListFood;
    private FoodListener mListener;
    private Context context;
    private int layoutId; 

    // Constructor mặc định (Dùng cho trang FoodActivity - Grid to)
    public FoodAdapter(Context context, List<Food> mListFood, FoodListener mListener) {
        this.context = context;
        this.mListFood = mListFood;
        this.mListener = mListener;
        this.layoutId = R.layout.item_food; // Mặc định dùng layout to
    }

    // Constructor mở rộng (Dùng cho trang Home - List nhỏ)
    public FoodAdapter(Context context, List<Food> mListFood, int layoutId, FoodListener mListener) {
        this.context = context;
        this.mListFood = mListFood;
        this.mListener = mListener;
        this.layoutId = layoutId; // Dùng layout nhỏ (item_food_home)
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

        holder.txtName.setText(food.getName());
        
        if (holder.txtRestaurant != null) {
            holder.txtRestaurant.setText(food.getRestaurantName()); 
        }

        holder.txtPrice.setText("$" + (int) food.getPrice());

        // Dùng Glide để load ảnh từ URL thay vì setImageResource
        Glide.with(context)
                .load(food.getImagePath())
                .placeholder(R.drawable.ic_launcher_foreground) // Ảnh hiển thị khi đang tải
                .into(holder.imgFood);

        // Sự kiện click vào ảnh -> Xem chi tiết
        holder.itemView.setOnClickListener(v -> mListener.onFoodClick(food));

        // Sự kiện click dấu cộng -> Thêm vào giỏ
        holder.btnAdd.setOnClickListener(v -> mListener.onAddToCartClick(food));
    }

    @Override
    public int getItemCount() {
        return mListFood != null ? mListFood.size() : 0;
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView txtName, txtPrice, txtRestaurant;
        ImageButton btnAdd;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgFood);
            txtName = itemView.findViewById(R.id.txtName);
            txtRestaurant = itemView.findViewById(R.id.txtRestaurant);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }

    public interface FoodListener {
        void onFoodClick(Food food);
        void onAddToCartClick(Food food);
    }
}