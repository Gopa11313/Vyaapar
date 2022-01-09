package net.com.gopal.vyapar.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class GeneralPref {
    static Context context;

    public GeneralPref(Context context) {
        this.context = context;

    }

    public static void setIsFirst(Boolean isFirst) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("vypaar", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isfirst", isFirst).apply();
    }

    public static boolean getIsFirst() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("vypaar", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isfirst", false);
    }
}
