package com.example.android_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class OnboardingAdapter extends PagerAdapter {

    private Context context;
    private ViewPager viewPager;

    private int[] images = {
            R.drawable.onboarding1,
            R.drawable.onboarding2,
            R.drawable.onboarding3,
            R.drawable.onboarding4
    };

    private String[] titles = {
            "All your favorites",
            "All your favorites",
            "Order from chosen chef",
            "Free delivery offers"
    };

    private String[] descriptions = {
            "Get all your loved foods in one place...",
            "Get all your loved foods in one place...",
            "Order from chosen chef...",
            "Get exciting delivery offers..."
    };

    // constructor mới
    public OnboardingAdapter(Context context, ViewPager viewPager) {
        this.context = context;
        this.viewPager = viewPager;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_onboarding, container, false);

        ImageView img = view.findViewById(R.id.imageView);
        TextView title = view.findViewById(R.id.title);
        TextView desc = view.findViewById(R.id.description);
        Button nextBtn = view.findViewById(R.id.nextBtn);   // <- lấy nút

        img.setImageResource(images[position]);
        title.setText(titles[position]);
        desc.setText(descriptions[position]);

        // Sự kiện cho nút NEXT
        nextBtn.setOnClickListener(v -> {
            int current = viewPager.getCurrentItem();
            if (current < getCount() - 1) {
                // sang slide kế tiếp
                viewPager.setCurrentItem(current + 1, true);
            } else {
                // đang ở slide cuối cùng
                // sau này bạn có thể chuyển sang LoginActivity ở đây
                // ví dụ:
                // context.startActivity(new Intent(context, LoginActivity.class));
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
