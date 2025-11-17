package com.example.android_project;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.example.android_project.OnboardingAdapter;

public class OnboardingActivity extends AppCompatActivity {
    ViewPager viewPager;
    OnboardingAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        viewPager = findViewById(R.id.viewPager);
        adapter = new OnboardingAdapter(this, viewPager);
        viewPager.setAdapter(adapter);
    }
}