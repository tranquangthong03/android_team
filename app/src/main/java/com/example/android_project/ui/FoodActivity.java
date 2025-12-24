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

    private void setupBannerInfo(String id, String nameFromIntent) {
        // 1. Xác định tên hiển thị
        String displayTitle = (nameFromIntent != null && !nameFromIntent.isEmpty()) 
                              ? nameFromIntent 
                              : "Thực Đơn";
        String slogan = "Thưởng thức món ngon mỗi ngày";
        
        // Ảnh mặc định
        int bannerRes = R.drawable.restaurant_image; 

        // 2. Tạo chuỗi để kiểm tra (Gộp cả ID và Tên vào để tìm từ khóa)
        // Ví dụ: checkString = "7gSt3... Burger" -> Chứa chữ "burger" -> OK
        String checkString = "";
        if (id != null) checkString += id.toLowerCase();
        if (nameFromIntent != null) checkString += " " + nameFromIntent.toLowerCase();

        // 3. Logic chọn ảnh
        if (checkString.contains("buger") || checkString.contains("burger")) {
            displayTitle = "Thế Giới Burger";
            slogan = "Bò nướng than hoa, phô mai tan chảy 🍔";
            bannerRes = R.drawable.burger_image;
            
        } else if (checkString.contains("pizza")) {
            displayTitle = "Pizza Ý Thượng Hạng";
            slogan = "Đế mỏng giòn tan, topping ngập tràn 🍕";
            bannerRes = R.drawable.pizza_image;
            
        } else if (checkString.contains("chicken") || checkString.contains("ga") || checkString.contains("gà")) {
            displayTitle = "Gà Rán Giòn Tan";
            slogan = "Vỏ giòn rụm, thịt mềm ngọt khó cưỡng 🍗";
            // LƯU Ý: Bạn cần có ảnh gà trong drawable, nếu chưa có thì tải về và bỏ comment dòng dưới
            // bannerRes = R.drawable.chicken_image; 
            
        } else if (checkString.contains("drink") || checkString.contains("nuoc") || checkString.contains("nước")) {
            displayTitle = "Đồ Uống Mát Lạnh";
            slogan = "Giải nhiệt cuộc sống, sảng khoái tức thì 🥤";
            // bannerRes = R.drawable.drink_image; 
        }

        // 4. Cập nhật giao diện
        if (collapsingToolbar != null) collapsingToolbar.setTitle(displayTitle);
        if (txtSlogan != null) txtSlogan.setText(slogan);
        
        Glide.with(this)
             .load(bannerRes)
             .placeholder(R.drawable.restaurant_image)
             .centerCrop()
             .into(imgBanner);
    }

    private void fetchFoodData() {
        Query query;

        // --- FIX LỖI Ở ĐÂY ---
        // Không return khi null nữa, mà chia làm 2 trường hợp:
        
        if (currentCategoryId != null && !currentCategoryId.isEmpty()) {
            // Trường hợp 1: Có ID -> Lọc theo danh mục (Ví dụ chỉ lấy Burger)
            Log.d("DEBUG_FIREBASE", "Đang lọc món theo CategoryId: " + currentCategoryId);
            query = db.collection("foods").whereEqualTo("categoryId", currentCategoryId);
        } else {
            // Trường hợp 2: Không có ID (Xem tất cả) -> Lấy TOÀN BỘ món ăn
            Log.d("DEBUG_FIREBASE", "CategoryId rỗng -> Đang lấy TẤT CẢ món ăn");
            query = db.collection("foods");
        }

        // Thực hiện truy vấn
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                foodList.clear();
                for (DocumentSnapshot document : task.getResult()) {
                    try {
                        Food food = document.toObject(Food.class);
                        if (food != null) {
                            food.setId(document.getId()); // Lưu ID document để xử lý click
                            foodList.add(food);
                        }
                    } catch (Exception e) {
                        Log.e("DEBUG_FIREBASE", "Lỗi convert data: " + e.getMessage());
                    }
                }
                adapter.notifyDataSetChanged();

                Log.d("DEBUG_FIREBASE", "Tìm thấy " + foodList.size() + " món.");

                if (foodList.isEmpty()) {
                    Toast.makeText(this, "Chưa có món nào!", Toast.LENGTH_SHORT).show();
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
       intent.putExtra(FoodDetailActivity.EXTRA_FOOD, food);
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