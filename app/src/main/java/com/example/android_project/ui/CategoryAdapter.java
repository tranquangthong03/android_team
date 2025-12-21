package com.example.android_project.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android_project.R;
import com.example.android_project.models.CategoryDomain;

import java.util.List; // Dùng List cho linh hoạt

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    List<CategoryDomain> items;
    Context context; // Biến Context để dùng Glide và Intent

    // --- CẬP NHẬT: Hàm khởi tạo nhận 2 tham số ---
    public CategoryAdapter(Context context, List<CategoryDomain> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryDomain category = items.get(position);

        holder.categoryName.setText(category.getName());

        // Dùng context đã truyền vào để load ảnh
        Glide.with(context)
                .load(category.getImagePath())
                .placeholder(R.drawable.sample_burger)
                .into(holder.categoryPic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FoodActivity.class);
            intent.putExtra("CategoryId", category.getId());
            intent.putExtra("CategoryName", category.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
        }
    }
}