package com.example.evently;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 onboardingViewPager;
    private ArrayList<Fragment> onboardingFragments;
    private ImageButton nextArrow, previousArrow;
    private ImageView onboardingState1, onboardingState2, onboardingState3;
    private ViewPager2.OnPageChangeCallback onPageChangeCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onboardingFragments = new ArrayList<>();
        onboardingFragments.add(new OnboardingOneFragment());
        onboardingFragments.add(new OnboardingTwoFragment());
        onboardingFragments.add(new OnboardingThreeFragment());

        OnboardingViewPagerAdapter onboardingViewPagerAdapter = new OnboardingViewPagerAdapter(this, onboardingFragments);

        onboardingViewPager = findViewById(R.id.onboarding_view_pager);
        onboardingViewPager.setAdapter(onboardingViewPagerAdapter);
        nextArrow = findViewById(R.id.next_arrow_image_button);
        previousArrow = findViewById(R.id.previous_arrow_image_button);
        onboardingState1 = findViewById(R.id.onboarding_state_1);
        onboardingState2 = findViewById(R.id.onboarding_state_2);
        onboardingState3 = findViewById(R.id.onboarding_state_3);

        onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateNavigationArrows(position);
                updateOnboardingCircles(position);
            }
        };
        onboardingViewPager.registerOnPageChangeCallback(onPageChangeCallback);

        nextArrow.setOnClickListener(v -> {
            int currentItem = onboardingViewPager.getCurrentItem();
            if (currentItem < onboardingFragments.size() - 1) {
                onboardingViewPager.setCurrentItem(currentItem + 1);
            }
        });

        previousArrow.setOnClickListener(v -> {
            int currentItem = onboardingViewPager.getCurrentItem();
            if (currentItem > 0) {
                onboardingViewPager.setCurrentItem(currentItem - 1);
            }
        });
        updateNavigationArrows(0);
        updateOnboardingCircles(0);
    }

    private void updateNavigationArrows(int position) {
        if (position == 0) {
            previousArrow.setVisibility(View.INVISIBLE);
        } else {
            previousArrow.setVisibility(View.VISIBLE);
        }

        if (position == onboardingFragments.size() - 1) {
            nextArrow.setVisibility(View.INVISIBLE);
        } else {
            nextArrow.setVisibility(View.VISIBLE);
        }
    }

    private void updateOnboardingCircles(int position) {
        onboardingState1.setImageResource(R.drawable.circle_onboarding_state);
        onboardingState2.setImageResource(R.drawable.circle_onboarding_state);
        onboardingState3.setImageResource(R.drawable.circle_onboarding_state);

        switch (position) {
            case 0:
                onboardingState1.setImageResource(R.drawable.oval_onboarding_state);
                break;
            case 1:
                onboardingState2.setImageResource(R.drawable.oval_onboarding_state);
                break;
            case 2:
                onboardingState3.setImageResource(R.drawable.oval_onboarding_state);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (onPageChangeCallback != null) {
            onboardingViewPager.unregisterOnPageChangeCallback(onPageChangeCallback);
        }
    }
}

