package com.example.android_project;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class OnboardingActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout layoutDots; // Layout chứa chấm
    OnboardingAdapter adapter;
    ImageView[] dots; // Mảng các chấm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPager = findViewById(R.id.viewPager);
        layoutDots = findViewById(R.id.layoutDots); // Ánh xạ layout chấm

        adapter = new OnboardingAdapter(this, viewPager);
        viewPager.setAdapter(adapter);

        // 1. Tạo các dấu chấm cho trang đầu tiên (vị trí 0)
        addBottomDots(0);

        // 2. Lắng nghe sự kiện thay đổi trang
        viewPager.addOnPageChangeListener(viewListener);
    }

    // Hàm vẽ các dấu chấm
    private void addBottomDots(int currentPage) {
        // Lấy số lượng trang từ Adapter
        int dotsCount = adapter.getCount();
        dots = new ImageView[dotsCount];

        layoutDots.removeAllViews(); // Xóa các chấm cũ để vẽ lại

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);

            // Mặc định dùng hình "chưa chọn" (dot_unselected.xml)
            // Bạn nhớ kiểm tra đúng tên file trong drawable
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.dot_unselected));

            // Chỉnh khoảng cách giữa các chấm
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(12, 0, 12, 0); // Cách nhau 12px
            layoutDots.addView(dots[i], params);
        }

        // Đổi màu chấm tại vị trí trang hiện tại thành "đã chọn" (dot_selected.xml)
        if (dots.length > 0) {
            //
            dots[currentPage].setImageDrawable(getResources().getDrawable(R.drawable.dot_selected));
        }
    }

    // Sự kiện lắng nghe ViewPager chuyển trang
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            // Khi trang thay đổi -> Vẽ lại các chấm
            addBottomDots(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
}