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

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryDomain> items;
    private Context context; // Biến Context để dùng Glide và Intent

    // Constructor nhận Context và List dữ liệu
    public CategoryAdapter(Context context, List<CategoryDomain> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_category, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryDomain item = items.get(position);

        // 1. Gán tên danh mục
        holder.categoryName.setText(item.getName());

        // 2. Tải ảnh từ URL bằng Glide
        // Đảm bảo bạn có file ảnh 'sample_burger' hoặc đổi tên thành ảnh khác trong drawable để làm ảnh chờ
        Glide.with(context)
                .load(item.getImagePath())
                .placeholder(R.drawable.sample_burger)
                .error(R.drawable.ic_launcher_background)
                .into(holder.categoryPic);

        // 3. Xử lý sự kiện Click vào danh mục
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FoodActivity.class);

            // QUAN TRỌNG: Key này phải trùng khớp với Key mà FoodActivity đang chờ nhận
            intent.putExtra("CATEGORY_ID", item.getId());
            intent.putExtra("CATEGORY_NAME", item.getName());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ View từ layout viewholder_category.xml
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
        }
    }
}