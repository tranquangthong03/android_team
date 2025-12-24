package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_project.data.CartManager;
import com.example.android_project.models.Food;
import com.example.android_project.ui.FoodAdapter;
import com.example.android_project.ui.FoodDetailActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText etSearch;
    private ImageView btnBack;
    private RecyclerView rvSearchResult;
    private FoodAdapter adapter;

    // Danh sách chứa kết quả tìm kiếm để hiển thị
    private ArrayList<Food> searchList;

    // Danh sách chứa TOÀN BỘ món ăn lấy từ Firebase (để lọc)
    private ArrayList<Food> allFoodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
        setupRecyclerView();
        loadAllFoods(); // Tải dữ liệu sẵn
        setupSearchLogic(); // Bắt sự kiện gõ phím
    }

    private void initViews() {
        etSearch = findViewById(R.id.etSearch);
        btnBack = findViewById(R.id.btnBackSearch);
        rvSearchResult = findViewById(R.id.rvSearchResult);

        // Tự động focus vào ô nhập và hiện bàn phím (tùy chọn)
        etSearch.requestFocus();

        btnBack.setOnClickListener(v -> finish());
    }

    private void setupRecyclerView() {
        searchList = new ArrayList<>();
        allFoodList = new ArrayList<>();

        // Sử dụng lại FoodAdapter và item_food_home hoặc tạo layout item mới nếu muốn
        adapter = new FoodAdapter(this, searchList, R.layout.item_food_home, new FoodAdapter.FoodListener() {
            @Override
            public void onFoodClick(Food food) {
                Intent intent = new Intent(SearchActivity.this, FoodDetailActivity.class);
                intent.putExtra(FoodDetailActivity.EXTRA_FOOD, food);
                startActivity(intent);
            }

            @Override
            public void onAddToCartClick(Food food) {
                CartManager.addToCart(food, 1);
            }
        });

        rvSearchResult.setLayoutManager(new LinearLayoutManager(this));
        rvSearchResult.setAdapter(adapter);
    }

    private void loadAllFoods() {
        FirebaseFirestore.getInstance().collection("foods")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        allFoodList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Food food = document.toObject(Food.class);
                            food.setId(document.getId());
                            allFoodList.add(food);
                        }
                        // Ban đầu chưa tìm gì thì không hiện gì cả, hoặc hiện gợi ý tùy bạn
                    }
                });
    }

    private void setupSearchLogic() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterFood(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    // Hàm lọc dữ liệu
    private void filterFood(String text) {
        searchList.clear();

        if (text.isEmpty()) {
            // Nếu xóa hết chữ thì xóa danh sách hiển thị
            adapter.notifyDataSetChanged();
            return;
        }

        // Chuyển từ khóa về chữ thường để tìm không phân biệt hoa thường
        String query = text.toLowerCase();

        for (Food food : allFoodList) {
            if (food.getName().toLowerCase().contains(query)) {
                searchList.add(food);
            }
        }
        adapter.notifyDataSetChanged();
    }
}