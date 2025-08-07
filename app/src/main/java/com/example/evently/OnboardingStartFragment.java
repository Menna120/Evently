package com.example.evently;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.Locale;

public class OnboardingStartFragment extends Fragment {

    private ImageView imageViewLangUs;
    private ImageView imageViewLangEg;
    private AppPreferences appPreferences;

    public OnboardingStartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPreferences = AppPreferences.getInstance(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialSwitch themeSwitch = view.findViewById(R.id.theme_switch);
        imageViewLangUs = view.findViewById(R.id.imageView_lang_us);
        imageViewLangEg = view.findViewById(R.id.imageView_lang_eg);

        // Set initial states
        boolean isDarkMode = appPreferences.isDarkMode();
        themeSwitch.setChecked(isDarkMode);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        updateLanguageSelectionUI();

        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            appPreferences.setDarkMode(isChecked);
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        imageViewLangUs.setOnClickListener(v -> {
            if (!appPreferences.getLanguage().equals("en")) {
                appPreferences.setLanguage("en");
                updateLanguageSelectionUI();
                setLocaleAndRestart("en");
            }
        });

        imageViewLangEg.setOnClickListener(v -> {
            if (!appPreferences.getLanguage().equals("ar")) {
                appPreferences.setLanguage("ar");
                updateLanguageSelectionUI();
                setLocaleAndRestart("ar");
            }
        });
    }

    private void updateLanguageSelectionUI() {
        String currentLanguage = appPreferences.getLanguage();
        if ("ar".equals(currentLanguage)) {
            imageViewLangEg.setBackgroundResource(R.drawable.flag_border_selected);
            imageViewLangUs.setBackgroundResource(android.R.color.transparent);
        } else {
            imageViewLangUs.setBackgroundResource(R.drawable.flag_border_selected);
            imageViewLangEg.setBackgroundResource(android.R.color.transparent);
        }
    }

    private void setLocaleAndRestart(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = requireActivity().getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        config.setLayoutDirection(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // Recreate the activity to apply language changes and restart the process
        Intent intent = requireActivity().getIntent();
        requireActivity().finish();
        startActivity(intent);
        requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
