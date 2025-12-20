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
import com.example.android_project.Restaurant; // Import class Restaurant của bạn

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private Context context;
    private List<Restaurant> mList; // Dùng List<Restaurant> thay vì CategoryDomain

    // Constructor nhận Context và List<Restaurant>
    public RestaurantAdapter(Context context, List<Restaurant> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = mList.get(position);
        if (restaurant == null) return;

        // Gán dữ liệu (Code này khớp với ID trong XML bạn vừa sửa)
        holder.txtName.setText(restaurant.getName());
        holder.txtRating.setText(String.valueOf(restaurant.getRating())); // Chuyển double sang string
        holder.txtTime.setText(restaurant.getDeliveryTime());
        holder.txtFee.setText(restaurant.getDeliveryFee());

        // Kiểm tra xem trong XML có txtDesc không, nếu có thì set, không thì bỏ qua
        if (holder.txtDesc != null) {
            holder.txtDesc.setText(restaurant.getDescription());
        }

        // Dùng Glide tải ảnh từ URL
        Glide.with(context)
                .load(restaurant.getImagePath())
                .placeholder(R.drawable.sample_restaurant) // Ảnh chờ
                .into(holder.imgRes);
    }

    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        return 0;
    }

    // Ánh xạ View (ViewHolder)
    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRes;
        TextView txtName, txtRating, txtTime, txtFee, txtDesc;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRes = itemView.findViewById(R.id.imgRes);
            txtName = itemView.findViewById(R.id.txtName);
            txtRating = itemView.findViewById(R.id.txtRating);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtFee = itemView.findViewById(R.id.txtFee);
            txtDesc = itemView.findViewById(R.id.txtDesc);
        }
    }
}