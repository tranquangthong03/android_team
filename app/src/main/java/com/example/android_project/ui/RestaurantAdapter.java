package com.example.android_project.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.android_project.R;
import com.example.android_project.models.CategoryDomain; // Tạm dùng model này hoặc tạo RestaurantDomain
import java.util.List;

// Lưu ý: Để nhanh, mình dùng tạm class CategoryDomain (title, pic) để chứa tên và ảnh nhà hàng
// Bạn có thể tạo class RestaurantDomain riêng nếu cần thêm rating, time...
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    List<CategoryDomain> items; 

    public RestaurantAdapter(List<CategoryDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(items.get(position).getTitle());
        
        // Random số liệu giả lập cho đẹp
        holder.tvRating.setText("4." + (5 + position)); 
        holder.tvTime.setText((15 + position * 5) + " min");

        String picUrl = items.get(position).getPic();
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picUrl, "drawable", holder.itemView.getContext().getPackageName());
        
        holder.img.setImageResource(drawableResourceId);
    }

    @Override
    public int getItemCount() { return items.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvRating, tvTime;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvResName);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvTime = itemView.findViewById(R.id.tvTime);
            img = itemView.findViewById(R.id.imgRes);
        }
    }
}