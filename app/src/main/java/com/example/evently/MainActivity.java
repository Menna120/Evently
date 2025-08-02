package com.example.evently;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 onboardingViewPager;
    private List<Fragment> onboardingFragments;
    private ImageButton nextArrow, previousArrow;
    private ImageView onboardingState1, onboardingState2, onboardingState3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onboardingFragments = Arrays.asList(
                new OnboardingOneFragment(),
                new OnboardingTwoFragment(),
                new OnboardingThreeFragment()
        );

        onboardingViewPager = findViewById(R.id.onboarding_view_pager);
        onboardingViewPager.setAdapter(new OnboardingViewPagerAdapter(this, new ArrayList<>(onboardingFragments)));

        nextArrow = findViewById(R.id.next_arrow_image_button);
        previousArrow = findViewById(R.id.previous_arrow_image_button);
        onboardingState1 = findViewById(R.id.onboarding_state_1);
        onboardingState2 = findViewById(R.id.onboarding_state_2);
        onboardingState3 = findViewById(R.id.onboarding_state_3);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateNavigationUI(position);
            }
        });

        nextArrow.setOnClickListener(v -> onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1));
        previousArrow.setOnClickListener(v -> onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() - 1));

        updateNavigationUI(0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        onboardingViewPager.unregisterOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateNavigationUI(position);
            }
        });
    }

    private void updateNavigationUI(int position) {
        previousArrow.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
        nextArrow.setVisibility(position == onboardingFragments.size() - 1 ? View.INVISIBLE : View.VISIBLE);

        onboardingState1.setImageResource(position == 0 ? R.drawable.oval_onboarding_state : R.drawable.circle_onboarding_state);
        onboardingState2.setImageResource(position == 1 ? R.drawable.oval_onboarding_state : R.drawable.circle_onboarding_state);
        onboardingState3.setImageResource(position == 2 ? R.drawable.oval_onboarding_state : R.drawable.circle_onboarding_state);
    }
}