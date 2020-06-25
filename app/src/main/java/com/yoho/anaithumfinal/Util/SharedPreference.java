package com.yoho.anaithumfinal.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    public static final String PREFS_NAME = "";
    SharedPreferences preference;
    SharedPreferences.Editor editor;

    public SharedPreference() {
        super();
    }

    public void putString(Context context, String text, String text1) {
        preference = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = preference.edit();
        editor.putString(text, text1);
        editor.commit();
    }

    public String getString(Context context, String PREFS_KEY) {
        String text;
        preference = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        text = preference.getString(PREFS_KEY, "");
        return text;
    }

    public void removeString(Context context, String PREFS_KEY) {
        preference = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = preference.edit();
        editor.remove(PREFS_KEY);
        editor.commit();
    }

    public void putInt(Context context, String text, int text1) {
        preference = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = preference.edit();
        editor.putInt(text, text1);
        editor.commit();
    }

    public int getInt(Context context, String PREFS_KEY) {
        int text;
        preference = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        text = preference.getInt(PREFS_KEY, 0);
        return text;
    }
    public void removeInt(Context context, String PREFS_KEY) {
        preference = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = preference.edit();
        editor.remove(PREFS_KEY);
        editor.commit();
    }

    public void putBoolean(Context context, String text, Boolean text1) {
        preference = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = preference.edit();
        editor.putBoolean(text, text1);
        editor.commit();
    }

    public Boolean getBoolean(Context context, String PREFS_KEY) {
        boolean text;
        preference = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        text = preference.getBoolean(PREFS_KEY, false);
        return text;
    }

    public void removeBoolean(Context context, String PREFS_KEY) {
        preference = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = preference.edit();

        editor.remove(PREFS_KEY);
        editor.commit();
    }
    public void clearSharedPreference(Context context) {
        preference = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = preference.edit();
        editor.clear();
        editor.commit();
    }

}
