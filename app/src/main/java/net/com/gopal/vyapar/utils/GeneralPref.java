package net.com.gopal.vyapar.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class GeneralPref {

    public static void setIsFirst(Boolean isFirst) {
        SharedPreferences sharedPreferences = CustomerApplication.getContext().getSharedPreferences("Mybalance", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isfirst", isFirst).apply();
    }

    public static boolean getIsFirst() {
        SharedPreferences sharedPreferences = CustomerApplication.getContext().getSharedPreferences("Mybalance", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isfirst",true);
    }
}
