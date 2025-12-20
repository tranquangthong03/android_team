package com.example.android_project.ui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.R;
import com.example.android_project.models.CategoryDomain;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    List<CategoryDomain> items;

    public CategoryAdapter(List<CategoryDomain> items) {
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
        holder.title.setText(items.get(position).getTitle());
        String picUrl = items.get(position).getPic();
        
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picUrl, "drawable", holder.itemView.getContext().getPackageName());
        holder.pic.setImageResource(drawableResourceId);

        // --- SỰ KIỆN CLICK VÀO DANH MỤC ---
        holder.itemView.setOnClickListener(v -> {
            // Chuyển sang trang FoodActivity (Trang Burger)
            Intent intent = new Intent(holder.itemView.getContext(), FoodActivity.class);
            // Có thể truyền thêm tên danh mục để lọc nếu muốn: intent.putExtra("CAT_NAME", items.get(position).getTitle());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return items.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvCatName);
            pic = itemView.findViewById(R.id.imgCat);
        }
    }
}