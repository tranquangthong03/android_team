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
import com.example.android_project.R; // ⚠️ QUAN TRỌNG: Phải là dòng này
import com.example.android_project.models.Restaurant;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private Context context;
    private List<Restaurant> mList;

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

        // Convert các số sang String để tránh lỗi
        holder.txtRating.setText(String.valueOf(restaurant.getRating()));

        holder.txtTime.setText(restaurant.getDeliveryTime());
        holder.txtFee.setText(restaurant.getDeliveryFee());

        if (holder.txtDesc != null) {
            holder.txtDesc.setText(restaurant.getDescription());
        }

        Glide.with(context)
                .load(restaurant.getImagePath())
                .placeholder(R.drawable.sample_restaurant)
                .into(holder.imgRes);
    }

    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        return 0;
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRes;
        TextView txtName, txtRating, txtTime, txtFee, txtDesc;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ ID (Phải khớp với file item_restaurant.xml)
            imgRes = itemView.findViewById(R.id.imgRes);
            txtName = itemView.findViewById(R.id.txtName);

            // 3 biến bạn đang bị lỗi đây:
            txtRating = itemView.findViewById(R.id.txtRating);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtFee = itemView.findViewById(R.id.txtFee);

            txtDesc = itemView.findViewById(R.id.txtDesc);
        }
    }
}