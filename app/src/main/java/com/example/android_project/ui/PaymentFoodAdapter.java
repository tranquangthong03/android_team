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
import com.example.android_project.models.Food;

import java.util.List;

public class PaymentFoodAdapter extends RecyclerView.Adapter<PaymentFoodAdapter.ViewHolder> {

    private Context context;
    private List<Food> foodList;

    public PaymentFoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Lưu ý: Layout này có thể tên là item_payment_food hoặc item_cart_payment tùy code của bạn
        // Nếu báo đỏ R.layout.item_payment_food, hãy kiểm tra lại tên file layout bạn đang dùng
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        // (Thường trang thanh toán dùng chung layout item_cart nhưng ẩn nút tăng giảm, hoặc dùng layout riêng)
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = foodList.get(position);

        holder.txtName.setText(food.getName());

        // --- SỬA Ở ĐÂY: Hiển thị giá dạng .000vnđ ---
        holder.txtPrice.setText((int)food.getPrice() + ".000vnđ");

        // Load ảnh
        Glide.with(context)
                .load(food.getImagePath())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imgFood);

        // Ẩn các nút tăng giảm số lượng nếu dùng chung layout với giỏ hàng
        // (Tùy thuộc vào việc bạn dùng layout nào, đoạn dưới này là tùy chọn để giao diện sạch hơn)
        if (holder.btnMinus != null) holder.btnMinus.setVisibility(View.GONE);
        if (holder.btnPlus != null) holder.btnPlus.setVisibility(View.GONE);
        if (holder.btnRemove != null) holder.btnRemove.setVisibility(View.GONE);
        if (holder.txtQty != null) holder.txtQty.setVisibility(View.VISIBLE); // Hoặc set cứng số lượng nếu cần
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView txtName, txtPrice, txtQty;
        View btnMinus, btnPlus, btnRemove; // Khai báo dạng View để tránh lỗi nếu không có

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ ID theo layout item_cart (hoặc layout bạn đang dùng cho Payment)
            imgFood = itemView.findViewById(R.id.imgFoodCart);
            txtName = itemView.findViewById(R.id.txtNameCart);
            txtPrice = itemView.findViewById(R.id.txtPriceCart);
            txtQty = itemView.findViewById(R.id.txtQtyCart);

            // Ánh xạ các nút thừa để ẩn đi
            btnMinus = itemView.findViewById(R.id.btnMinusCart);
            btnPlus = itemView.findViewById(R.id.btnPlusCart);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}