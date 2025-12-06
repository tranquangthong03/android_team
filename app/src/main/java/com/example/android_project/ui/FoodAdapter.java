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

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    public interface FoodListener {
        void onFoodClick(Food food);
        void onAddToCartClick(Food food);
    }

    private final List<Food> foods;
    private final FoodListener listener;

    public FoodAdapter(List<Food> foods, FoodListener listener) {
        this.foods = foods;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foods.get(position);

        holder.txtName.setText(food.getName());
        holder.txtRestaurant.setText(food.getRestaurant());
        holder.txtPrice.setText("$" + (int) food.getPrice());
        holder.imgFood.setImageResource(food.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onFoodClick(food);
        });

        holder.btnAdd.setOnClickListener(v -> {
            if (listener != null) listener.onAddToCartClick(food);
        });
    }

    @Override
    public int getItemCount() {
        return foods != null ? foods.size() : 0;
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView txtName, txtRestaurant, txtPrice;
        ImageButton btnAdd;

        FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.imgFood);
            txtName = itemView.findViewById(R.id.txtName);
            txtRestaurant = itemView.findViewById(R.id.txtRestaurant);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }
}
