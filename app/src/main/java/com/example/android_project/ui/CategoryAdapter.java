package com.example.android_project.ui;

import android.content.Context;
import android.content.Intent; // 1. Import Intent
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android_project.R;
import com.example.android_project.models.CategoryDomain;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    ArrayList<CategoryDomain> categoryDomains;
    Context context;

    public CategoryAdapter(Context context, ArrayList<CategoryDomain> categoryDomains) {
        this.context = context;
        this.categoryDomains = categoryDomains;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Lấy đối tượng danh mục hiện tại
        CategoryDomain item = categoryDomains.get(position);

        // 1. Gán tên danh mục
        holder.categoryName.setText(item.getName());

        // 2. Tải ảnh từ URL bằng Glide
        Glide.with(context)
                .load(item.getImagePath())
                .placeholder(R.drawable.sample_burger) // Ảnh chờ (nhớ đảm bảo file này có trong drawable)
                .into(holder.categoryPic);

        // 3. XỬ LÝ SỰ KIỆN CLICK (Mới thêm)
        holder.itemView.setOnClickListener(v -> {
            // Tạo Intent để chuyển sang trang FoodActivity
            Intent intent = new Intent(context, FoodActivity.class);

            // Gửi dữ liệu đi ("Key", Giá trị)
            intent.putExtra("CATEGORY_ID", item.getId());     // Gửi ID (ví dụ: "burger")
            intent.putExtra("CATEGORY_NAME", item.getName()); // Gửi Tên (ví dụ: "Burger")

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categoryDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}