package com.example.android_project; // Sửa lại package nếu khác

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private Context context;
    private List<Restaurant> mList;

    // Constructor
    public RestaurantAdapter(Context context, List<Restaurant> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Nạp layout item_restaurant vào
        View view = LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = mList.get(position);
        if (restaurant == null) return;

        // Gán dữ liệu vào các view
        holder.txtName.setText(restaurant.getName());
        holder.txtDesc.setText(restaurant.getDescription());
        holder.txtRating.setText("★ " + restaurant.getRating());
        holder.txtTime.setText(restaurant.getDeliveryTime());
        holder.txtFee.setText(restaurant.getDeliveryFee());

        // Dùng Glide để load ảnh từ URL
        Glide.with(context)
                .load(restaurant.getImagePath())
                .into(holder.imgRes);
    }

    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        return 0;
    }

    // Ánh xạ ID từ file xml sang java
    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRes;
        TextView txtName, txtDesc, txtRating, txtTime, txtFee;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRes = itemView.findViewById(R.id.imgRes);
            txtName = itemView.findViewById(R.id.txtName);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            txtRating = itemView.findViewById(R.id.txtRating);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtFee = itemView.findViewById(R.id.txtFee);
        }
    }
}