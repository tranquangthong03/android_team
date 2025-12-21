package com.example.android_project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
<<<<<<< HEAD
import android.widget.ImageButton;
=======
import android.widget.ImageView;
>>>>>>> huuhung
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.R;
import com.example.android_project.data.CartManager;
import com.example.android_project.models.Food;
<<<<<<< HEAD
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
=======
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
>>>>>>> huuhung

import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFood;
    private FoodAdapter adapter;
<<<<<<< HEAD
    private List<Food> foodList;
    private FirebaseFirestore db;
    private TextView txtCategory; // Biến hiển thị tên danh mục
    private ImageButton btnBack;

    private String currentCategoryId = null;
=======
    private ArrayList<Food> foodList;
    private FirebaseFirestore db;
    private String categoryId;
    private String categoryName;
    private TextView txtTitle;
    private ImageView btnBack;
>>>>>>> huuhung

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        // Đảm bảo file layout đúng tên là burger_food.xml (hoặc buger_food tùy bạn đặt)
        setContentView(R.layout.buger_food);

        db = FirebaseFirestore.getInstance();

        // 1. Nhận dữ liệu từ Intent
        currentCategoryId = getIntent().getStringExtra("CATEGORY_ID");
        String categoryName = getIntent().getStringExtra("CATEGORY_NAME");

        // 2. Ánh xạ View
        initViews();

        // 3. Cập nhật tiêu đề trang
        if (categoryName != null) {
            txtCategory.setText(categoryName); // Ví dụ: "Burger", "Pizza"
        } else {
            txtCategory.setText("Tất cả món ăn");
        }

        // 4. Cài đặt RecyclerView và tải dữ liệu
        setupRecyclerView();
        fetchFoodData();
    }

    private void initViews() {
        // Ánh xạ đúng với ID trong file XML bạn cung cấp
        rvPopular = findViewById(R.id.rvPopular);
        btnBack = findViewById(R.id.btnBackMain);
        txtCategory = findViewById(R.id.txtCategory);

        // Sự kiện nút Back
        btnBack.setOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        foodList = new ArrayList<>();
        // Truyền 'this' (Context) vào Adapter
        adapter = new FoodAdapter(this, foodList, this);

        // Grid 2 cột
        rvPopular.setLayoutManager(new GridLayoutManager(this, 2));
        rvPopular.setAdapter(adapter);
    }

    private void fetchFoodData() {
        Query query;

        // --- LOGIC LỌC DỮ LIỆU ---
        if (currentCategoryId != null && !currentCategoryId.isEmpty()) {
            // Nếu có ID danh mục -> Lọc theo field 'categoryId' trên Firebase
            // Querying Firestore
            query = db.collection("foods").whereEqualTo("categoryId", currentCategoryId);
        } else {
            // Nếu không có ID -> Lấy tất cả
            query = db.collection("foods");
        }

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                foodList.clear();
                for (DocumentSnapshot document : task.getResult()) {
                    Food food = document.toObject(Food.class);
                    if (food != null) {
                        food.setId(document.getId());
                        foodList.add(food);
                    }
                }
                adapter.notifyDataSetChanged();

                if (foodList.isEmpty()) {
                    Toast.makeText(this, "Không tìm thấy món ăn nào!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Lỗi tải dữ liệu: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Sự kiện click vào món ăn -> Mở chi tiết
    @Override
    public void onFoodClick(Food food) {
        Intent intent = new Intent(FoodActivity.this, FoodDetailActivity.class);
        intent.putExtra(FoodDetailActivity.EXTRA_FOOD, food);
        startActivity(intent);
    }

    // Sự kiện click nút Add (+)
    @Override
    public void onAddToCartClick(Food food) {
        CartManager.addToCart(food, 1);
        Toast.makeText(this, "Đã thêm " + food.getName() + " vào giỏ", Toast.LENGTH_SHORT).show();
=======
        // Đảm bảo bạn đã tạo file xml này như hướng dẫn trước (activity_food.xml)
        setContentView(R.layout.activity_food);

        // 1. Nhận dữ liệu từ HomeActivity
        categoryId = getIntent().getStringExtra("CategoryId");
        categoryName = getIntent().getStringExtra("CategoryName");

        // 2. Ánh xạ và xử lý giao diện
        initViews();

        // 3. Tải dữ liệu từ Firebase
        loadFoodsFromFirestore();

        // 4. Sự kiện click nút Back
        setupEvents();
    }

    private void initViews() {
        // Ánh xạ View (kiểm tra kỹ ID trong file activity_food.xml của bạn)
        recyclerViewFood = findViewById(R.id.recyclerViewFood);
        txtTitle = findViewById(R.id.txtTitle);
        btnBack = findViewById(R.id.btnBack);

        // Setup RecyclerView dạng lưới (2 cột)
        recyclerViewFood.setLayoutManager(new GridLayoutManager(this, 2));

        foodList = new ArrayList<>();

        // --- SỬA LỖI Ở ĐÂY: Dùng Constructor 3 tham số ---
        // Không truyền R.layout.item_food nữa, Adapter sẽ tự dùng mặc định
        adapter = new FoodAdapter(this, foodList, new FoodAdapter.FoodListener() {
            @Override
            public void onFoodClick(Food food) {
                // Chuyển sang màn hình chi tiết món ăn
                Intent intent = new Intent(FoodActivity.this, FoodDetailActivity.class);
                intent.putExtra(FoodDetailActivity.EXTRA_FOOD, food);
                startActivity(intent);
            }

            @Override
            public void onAddToCartClick(Food food) {
                // Thêm vào giỏ hàng
                CartManager.addToCart(food, 1);
                Toast.makeText(FoodActivity.this, "Đã thêm " + food.getName() + " vào giỏ!", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewFood.setAdapter(adapter);

        // Set tiêu đề cho màn hình (nếu có tên danh mục)
        if (categoryName != null && txtTitle != null) {
            txtTitle.setText(categoryName);
        }
    }

    private void setupEvents() {
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish()); // Đóng màn hình hiện tại để quay lại
        }
    }

    private void loadFoodsFromFirestore() {
        db = FirebaseFirestore.getInstance();
        Query query;

        // LOGIC LỌC DỮ LIỆU
        if (categoryId != null && !categoryId.isEmpty()) {
            // Nếu có ID danh mục -> Lọc các món có categoryId trùng khớp
            // Lưu ý: Trên Firebase bảng 'foods' phải có trường 'categoryId'
            query = db.collection("foods").whereEqualTo("categoryId", categoryId);
        } else {
            // Nếu không có ID (ví dụ bấm nút 'Xem tất cả') -> Lấy hết
            query = db.collection("foods");
        }

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                foodList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Food food = document.toObject(Food.class);
                    food.setId(document.getId()); // Lưu ID document để dùng sau này
                    foodList.add(food);
                }
                adapter.notifyDataSetChanged();

                // Kiểm tra nếu danh sách rỗng
                if (foodList.isEmpty()) {
                    Toast.makeText(FoodActivity.this, "Chưa có món nào trong danh mục này", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(FoodActivity.this, "Lỗi tải dữ liệu: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
>>>>>>> huuhung
    }
}