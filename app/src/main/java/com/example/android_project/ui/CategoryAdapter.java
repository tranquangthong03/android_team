package com.example.android_project.ui;

import android.content.Intent; // Cần import cái này để chuyển màn hình
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
        CategoryDomain category = items.get(position);

        // 1. Gán tên danh mục
        // LƯU Ý: Kiểm tra file CategoryDomain.java, nếu biến là 'name' thì sửa getTitle() thành getName()
        holder.categoryName.setText(category.getName()); 

        // 2. Xử lý hình ảnh
        String picUrl = category.getImagePath(); // LƯU Ý: Kiểm tra lại tên hàm get này bên Model
        
        int drawableResourceId = holder.itemView.getContext().getResources()
                .getIdentifier(picUrl, "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.categoryPic);

        // 3. --- QUAN TRỌNG: BẮT SỰ KIỆN CLICK ĐỂ SANG MÀN HÌNH MÓN ĂN ---
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), FoodActivity.class);
            // Truyền ID danh mục sang để bên kia biết đường mà lọc
            intent.putExtra("CategoryId", category.getId()); 
            intent.putExtra("CategoryName", category.getName());
            holder.itemView.getContext().startActivity(intent);
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
            // Ánh xạ ID theo file layout item_category.xml mới
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
        }
    }
}