package com.example.android_project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.R;
import com.example.android_project.data.CartManager;
import com.example.android_project.models.Food;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity implements FoodAdapter.FoodListener {

    private RecyclerView rvPopular;
    private FoodAdapter adapter;
    private List<Food> foodList;
    private FirebaseFirestore db; // Khai báo Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buger_food);

        // Khởi tạo Firestore
        db = FirebaseFirestore.getInstance();

        rvPopular = findViewById(R.id.rvPopular);
        ImageButton btnBack = findViewById(R.id.btnBackMain);
        btnBack.setOnClickListener(v -> finish());

        setupRecyclerView();
        fetchFoodData(); // Gọi hàm lấy dữ liệu
    }

    private void setupRecyclerView() {
        foodList = new ArrayList<>();
        // Truyền 'this' (Context) vào Adapter mới
        adapter = new FoodAdapter(this, foodList, this);
        rvPopular.setLayoutManager(new GridLayoutManager(this, 2));
        rvPopular.setAdapter(adapter);
    }

    private void fetchFoodData() {
        // Lấy dữ liệu từ collection "foods" trên Firebase
        db.collection("foods")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        foodList.clear(); // Xóa dữ liệu cũ
                        for (DocumentSnapshot document : task.getResult()) {
                            // Ép kiểu JSON sang Object Food
                            Food food = document.toObject(Food.class);
                            if (food != null) {
                                food.setId(document.getId()); // Lưu ID document vào object
                                foodList.add(food);
                            }
                        }
                        // Cập nhật giao diện
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Lỗi tải dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onFoodClick(Food food) {
        Intent intent = new Intent(FoodActivity.this, FoodDetailActivity.class);
        intent.putExtra(FoodDetailActivity.EXTRA_FOOD, food);
        startActivity(intent);
    }

    @Override
    public void onAddToCartClick(Food food) {
        CartManager.addToCart(food, 1);
        Toast.makeText(this, "Đã thêm " + food.getName() + " vào giỏ", Toast.LENGTH_SHORT).show();
    }
}