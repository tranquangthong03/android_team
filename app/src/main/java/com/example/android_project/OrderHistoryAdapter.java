package com.example.android_project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.models.Order;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> orderList;

    public OrderHistoryAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_history, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);

        // Cắt chuỗi ID cho ngắn gọn
        String shortId = order.getOrderId().length() > 8
                ? order.getOrderId().substring(0, 8).toUpperCase()
                : order.getOrderId();

        holder.txtOrderId.setText("Đơn hàng #" + shortId);
        holder.txtDate.setText(order.getDate());
        holder.txtTotal.setText("$" + (int)order.getTotalPrice());
        holder.txtStatus.setText(order.getStatus());

        // Click vào dòng đơn hàng -> Xem chi tiết (Dùng lại InvoiceActivity)
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, InvoiceActivity.class);
            intent.putExtra("ORDER_DATA", order); // Truyền object Order sang
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderId, txtDate, txtTotal, txtStatus;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            txtStatus = itemView.findViewById(R.id.txtStatus);
        }
    }
}