package com.example.android_project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.R;
import com.example.android_project.data.CartManager;
import com.example.android_project.models.Food;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

// Implement Interface FoodListener ngay tại đây để code gọn hơn
public class FoodActivity extends AppCompatActivity implements FoodAdapter.FoodListener {

    private RecyclerView rvPopular;
    private FoodAdapter adapter;
    private List<Food> foodList;
    private FirebaseFirestore db;
    private TextView txtCategory;
    private ImageButton btnBack;

    private String currentCategoryId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sử dụng layout buger_food.xml (Nếu tên file xml của bạn khác, hãy sửa ở đây)
        setContentView(R.layout.buger_food);

        db = FirebaseFirestore.getInstance();

        // 1. Nhận dữ liệu từ Intent (Gửi từ HomeActivity)
        // QUAN TRỌNG: Key này phải khớp với bên CategoryAdapter
        currentCategoryId = getIntent().getStringExtra("CATEGORY_ID");
        String categoryName = getIntent().getStringExtra("CATEGORY_NAME");

        // 2. Ánh xạ View
        initViews();

        // 3. Cập nhật tiêu đề trang
        if (categoryName != null) {
            txtCategory.setText(categoryName);
        } else {
            txtCategory.setText("Tất cả món ăn");
        }

        // 4. Cài đặt RecyclerView và tải dữ liệu
        setupRecyclerView();
        fetchFoodData();
    }

    private void initViews() {
        // Ánh xạ đúng với ID trong file XML buger_food.xml của bạn
        rvPopular = findViewById(R.id.rvPopular);
        btnBack = findViewById(R.id.btnBackMain);
        txtCategory = findViewById(R.id.txtCategory); // Hoặc txtCategoryTitle tùy XML của bạn

        // Sự kiện nút Back
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
    }

    private void setupRecyclerView() {
        foodList = new ArrayList<>();

        // Khởi tạo Adapter
        // Tham số 1: Context (this)
        // Tham số 2: List dữ liệu
        // Tham số 3: Listener (this - vì Activity đã implements FoodListener)
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

    // --- XỬ LÝ SỰ KIỆN TỪ ADAPTER ---

    // 1. Khi click vào ảnh món ăn -> Mở trang chi tiết
    @Override
    public void onFoodClick(Food food) {
        Intent intent = new Intent(FoodActivity.this, FoodDetailActivity.class);
        intent.putExtra(FoodDetailActivity.EXTRA_FOOD, food);
        startActivity(intent);
    }

    // 2. Khi click nút Add (+) -> Thêm vào giỏ hàng
    @Override
    public void onAddToCartClick(Food food) {
        CartManager.addToCart(food, 1);
        Toast.makeText(this, "Đã thêm " + food.getName() + " vào giỏ", Toast.LENGTH_SHORT).show();
    }
}