package com.example.evently;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    private static final String PREFS_NAME = "EventlyPrefs"; // Changed from PREF_NAME
    private static final String KEY_THEME_DARK_MODE = "theme_dark_mode";
    private static final String KEY_LANGUAGE = "language";

    private static AppPreferences instance;
    private final SharedPreferences sharedPreferences;

    AppPreferences(Context context) { // Made constructor private
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized AppPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new AppPreferences(context.getApplicationContext());
        }
        return instance;
    }

    public boolean isDarkMode() {
        return sharedPreferences.getBoolean(KEY_THEME_DARK_MODE, false); // Default to light mode
    }

    public void setDarkMode(boolean isDarkMode) {
        sharedPreferences.edit().putBoolean(KEY_THEME_DARK_MODE, isDarkMode).apply();
    }

    public String getLanguage() {
        return sharedPreferences.getString(KEY_LANGUAGE, "en"); // Default to English
    }

    public void setLanguage(String languageCode) {
        sharedPreferences.edit().putString(KEY_LANGUAGE, languageCode).apply();
    }
}
