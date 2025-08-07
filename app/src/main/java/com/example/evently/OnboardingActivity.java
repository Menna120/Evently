package com.example.evently;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class OnboardingActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "OnboardingPrefs";
    private static final String ONBOARDING_COMPLETED_KEY = "onboardingCompleted";

    private ViewPager2 viewPager;
    private OnboardingPagerAdapter pagerAdapter;
    private ImageView previousArrow;
    private ImageView nextArrow;
    private ImageView onboardingState1, onboardingState2, onboardingState3;
    private FrameLayout onboardingStartContainer;
    private View navigationLayout;
    private MaterialButton buttonLetsStartActivity;
    private AppPreferences appPreferences;


    @Override
    protected void attachBaseContext(Context newBase) {
        appPreferences = AppPreferences.getInstance(newBase);
        super.attachBaseContext(LocaleHelper.onAttach(newBase, appPreferences.getLanguage()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize AppPreferences if not already done in attachBaseContext
        if (appPreferences == null) {
            appPreferences = AppPreferences.getInstance(this);
        }

        // Apply theme before setContentView
        if (appPreferences.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_onboarding);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (prefs.getBoolean(ONBOARDING_COMPLETED_KEY, false)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        onboardingStartContainer = findViewById(R.id.onboarding_start_container);
        viewPager = findViewById(R.id.onboarding_view_pager);
        navigationLayout = findViewById(R.id.onboarding_navigation_layout);
        previousArrow = findViewById(R.id.previous_arrow);
        nextArrow = findViewById(R.id.next_arrow);
        onboardingState1 = findViewById(R.id.onboarding_state_1);
        onboardingState2 = findViewById(R.id.onboarding_state_2);
        onboardingState3 = findViewById(R.id.onboarding_state_3);
        buttonLetsStartActivity = findViewById(R.id.button_lets_start_activity);

        ArrayList<Fragment> onboardingFragments = new ArrayList<>();
        onboardingFragments.add(new OnboardingOneFragment());
        onboardingFragments.add(new OnboardingTwoFragment());
        onboardingFragments.add(new OnboardingThreeFragment());

        pagerAdapter = new OnboardingPagerAdapter(this, onboardingFragments);
        viewPager.setAdapter(pagerAdapter);

        // Load OnboardingStartFragment initially
        OnboardingStartFragment onboardingStartFragment;
        if (savedInstanceState == null) {
            onboardingStartFragment = new OnboardingStartFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.onboarding_start_container, onboardingStartFragment);
            fragmentTransaction.commit();
        } else {
            getSupportFragmentManager().findFragmentById(R.id.onboarding_start_container);
        }

        // Initial UI State
        onboardingStartContainer.setVisibility(View.VISIBLE);
        buttonLetsStartActivity.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.GONE);
        navigationLayout.setVisibility(View.GONE);


        buttonLetsStartActivity.setOnClickListener(v -> showSwipableOnboarding());

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateNavigationUI(position);
            }
        });

        previousArrow.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem > 0) {
                viewPager.setCurrentItem(currentItem - 1);
            }
        });

        nextArrow.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem < pagerAdapter.getItemCount() - 1) {
                viewPager.setCurrentItem(currentItem + 1);
            } else {
                // Last page of ViewPager
                markOnboardingCompleted();
                startActivity(new Intent(OnboardingActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    public void showSwipableOnboarding() {
        onboardingStartContainer.setVisibility(View.GONE);
        buttonLetsStartActivity.setVisibility(View.GONE);

        viewPager.setVisibility(View.VISIBLE);
        navigationLayout.setVisibility(View.VISIBLE);
        updateNavigationUI(0); // Set UI for the first page of ViewPager
    }


    private void updateNavigationUI(int position) {
        // ViewPager specific navigation
        if (pagerAdapter.getItemCount() == 0) return;

        previousArrow.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
        nextArrow.setVisibility(position == pagerAdapter.getItemCount() - 1 ? View.INVISIBLE : View.VISIBLE);


        onboardingState1.setImageResource(R.drawable.onboarding_state_inactive);
        onboardingState2.setImageResource(R.drawable.onboarding_state_inactive);
        onboardingState3.setImageResource(R.drawable.onboarding_state_inactive);

        if (pagerAdapter.getItemCount() == 3) { // Assuming 3 fragments in ViewPager
            if (position == 0) { // OnboardingOneFragment
                onboardingState1.setImageResource(R.drawable.onboarding_state_active);
            } else if (position == 1) { // OnboardingTwoFragment
                onboardingState2.setImageResource(R.drawable.onboarding_state_active);
            } else if (position == 2) { // OnboardingThreeFragment
                onboardingState3.setImageResource(R.drawable.onboarding_state_active);
            }
        }
    }


    private void markOnboardingCompleted() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ONBOARDING_COMPLETED_KEY, true);
        editor.apply();
    }

    public void recreateForLanguageChange() {
        recreate();
    }
}
