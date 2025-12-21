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
import com.example.android_project.models.Restaurant; // Đảm bảo import đúng đường dẫn Model

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private Context context;
    private List<Restaurant> mList;

    // --- SỬA LỖI Ở ĐÂY: Thêm Context vào Constructor ---
    // Bây giờ nó nhận 2 tham số: Context và List -> Khớp với HomeActivity
    public RestaurantAdapter(Context context, List<Restaurant> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = mList.get(position);
        if (restaurant == null) return;

        holder.txtName.setText(restaurant.getName());
        holder.txtRating.setText(String.valueOf(restaurant.getRating()));
        holder.txtTime.setText(restaurant.getDeliveryTime());
        // holder.txtFee.setText(restaurant.getDeliveryFee()); // Uncomment nếu có field này

        // Dùng biến context để load ảnh với Glide
        Glide.with(context)
                .load(restaurant.getImagePath())
                .placeholder(R.drawable.sample_restaurant)
                .into(holder.imgRes);
    }

    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRes;
        TextView txtName, txtRating, txtTime, txtFee;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRes = itemView.findViewById(R.id.imgRes);
            txtName = itemView.findViewById(R.id.txtName);
            txtRating = itemView.findViewById(R.id.txtRating);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtFee = itemView.findViewById(R.id.txtFee);
        }
    }
}