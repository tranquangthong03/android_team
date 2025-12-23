package com.example.android_project.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android_project.R;
import com.example.android_project.data.CartManager;
import com.example.android_project.models.Food;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity implements FoodAdapter.FoodListener {

    private RecyclerView recyclerView;
    private FoodAdapter adapter;
    private List<Food> foodList;

    // Các biến giao diện
    private CollapsingToolbarLayout collapsingToolbar;
    private ImageView imgBanner;
    private TextView txtSlogan;
    private Toolbar toolbar;

    // Firebase & Data
    private FirebaseFirestore db;
    private String currentCategoryId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        db = FirebaseFirestore.getInstance();

        initView();
        getIntentExtra(); // Nhận ID từ màn hình trước
        fetchFoodData();  // Gọi Firebase lấy dữ liệu
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerViewFood);
        collapsingToolbar = findViewById(R.id.collapsingToolbar); // Đảm bảo ID này có trong activity_food.xml
        imgBanner = findViewById(R.id.imgCategoryBanner);
        txtSlogan = findViewById(R.id.txtSlogan);
        toolbar = findViewById(R.id.toolbar);

        // Setup Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Setup RecyclerView (Grid 2 cột)
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        
        // Khởi tạo List & Adapter
        foodList = new ArrayList<>();
        // Sử dụng Constructor 3 tham số của FoodAdapter (Context, List, Listener)
        adapter = new FoodAdapter(this, foodList, this);
        recyclerView.setAdapter(adapter);

        // Màu chữ tiêu đề khi cuộn
        if (collapsingToolbar != null) {
            collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.white));
            collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        }
    }

    private void getIntentExtra() {
        // Nhận ID danh mục (Key phải trùng với bên CategoryAdapter)
        currentCategoryId = getIntent().getStringExtra("CATEGORY_ID");
        
        // Nếu null thì thử lấy key khác (phòng hờ)
        if (currentCategoryId == null) {
            currentCategoryId = getIntent().getStringExtra("CategoryId");
        }

        String categoryName = getIntent().getStringExtra("CATEGORY_NAME");
        
        // Setup giao diện dựa trên ID (Banner, Slogan)
        setupBannerInfo(currentCategoryId, categoryName);
    }

   private void setupBannerInfo(String id, String name) {
        String displayTitle = (name != null) ? name : "Thực Đơn";
        String slogan = "Thưởng thức món ngon mỗi ngày";
        
        // Mặc định là ảnh nhà hàng chung chung
        int bannerRes = R.drawable.restaurant_image; 

        if (id != null) {
            // Chuyển ID về chữ thường để so sánh cho dễ
            String lowerId = id.toLowerCase();
            
            // LOGIC CHỌN ẢNH VÀ SLOGAN THEO ID
            if (lowerId.contains("buger") || lowerId.contains("burger")) {
                displayTitle = "Thế Giới Burger";
                slogan = "Bò nướng than hoa, phô mai tan chảy đậm đà 🍔";
                bannerRes = R.drawable.burger_image; // Dùng ảnh burger_image.webp
                
            } else if (lowerId.contains("pizza")) {
                displayTitle = "Pizza Ý Thượng Hạng";
                slogan = "Đế mỏng giòn tan, topping ngập tràn 🍕";
                bannerRes = R.drawable.pizza_image; // Dùng ảnh pizza_image.webp
                
            } else if (lowerId.contains("chicken") || lowerId.contains("ga")) {
                displayTitle = "Gà Rán Giòn Tan";
                slogan = "Vỏ giòn rụm, thịt mềm ngọt khó cưỡng 🍗";
                // Nếu chưa có ảnh gà, bạn có thể tạm dùng ảnh này hoặc tải thêm
                bannerRes = R.drawable.restaurant_image; 
                
            } else if (lowerId.contains("drink") || lowerId.contains("nuoc")) {
                displayTitle = "Đồ Uống Mát Lạnh";
                slogan = "Giải nhiệt cuộc sống, sảng khoái tức thì 🥤";
                // bannerRes = R.drawable.drink_image; 
            }
        }

        // Cập nhật lên giao diện
        if (collapsingToolbar != null) collapsingToolbar.setTitle(displayTitle);
        if (txtSlogan != null) txtSlogan.setText(slogan);
        
        // Load ảnh bằng Glide
        Glide.with(this)
             .load(bannerRes)
             .centerCrop()
             .into(imgBanner);
    }

    private void fetchFoodData() {
        if (currentCategoryId == null || currentCategoryId.isEmpty()) {
            Toast.makeText(this, "Lỗi: Không tìm thấy ID danh mục!", Toast.LENGTH_SHORT).show();
            Log.e("DEBUG_FIREBASE", "CategoryId is NULL");
            return;
        }

        Log.d("DEBUG_FIREBASE", "Đang lấy món ăn với categoryId: " + currentCategoryId);

        // Query Firestore: Tìm trong collection 'foods' có 'categoryId' bằng với ID nhận được
        db.collection("foods")
                .whereEqualTo("categoryId", currentCategoryId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        foodList.clear();
                        for (DocumentSnapshot document : task.getResult()) {
                            try {
                                Food food = document.toObject(Food.class);
                                if (food != null) {
                                    food.setId(document.getId()); // Lưu ID document
                                    foodList.add(food);
                                }
                            } catch (Exception e) {
                                Log.e("DEBUG_FIREBASE", "Lỗi convert data: " + e.getMessage());
                            }
                        }
                        adapter.notifyDataSetChanged();

                        Log.d("DEBUG_FIREBASE", "Tìm thấy " + foodList.size() + " món.");
                        
                        if (foodList.isEmpty()) {
                            Toast.makeText(this, "Chưa có món nào trong danh mục này!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("DEBUG_FIREBASE", "Lỗi query: ", task.getException());
                        Toast.makeText(this, "Lỗi kết nối server!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // --- Xử lý sự kiện từ Adapter (Interface) ---
    @Override
    public void onFoodClick(Food food) {
        Intent intent = new Intent(FoodActivity.this, FoodDetailActivity.class);
        // Key "object" phải khớp với code nhận bên FoodDetailActivity
        intent.putExtra("object", food); 
        // Hoặc nếu bên kia dùng key khác (ví dụ EXTRA_FOOD) thì dùng dòng dưới:
        // intent.putExtra(FoodDetailActivity.EXTRA_FOOD, food);
        startActivity(intent);
    }

    @Override
    public void onAddToCartClick(Food food) {
        CartManager.addToCart(food, 1);
        Toast.makeText(this, "Đã thêm " + food.getName() + " vào giỏ", Toast.LENGTH_SHORT).show();
    }
}