package com.example.evently;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LocaleHelper {

    public static Context setLocale(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration config = new Configuration(resources.getConfiguration());
        config.setLocale(locale);

        return context.createConfigurationContext(config);
    }

    public static Context onAttach(Context context, String language) {
//        AppPreferences appPreferences = new AppPreferences(context);
//        String lang = appPreferences.getLanguage();
        return setLocale(context, language);
    }
}