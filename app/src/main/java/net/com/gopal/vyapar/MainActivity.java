package net.com.gopal.vyapar;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import net.com.gopal.vyapar.Login.LoginActivity;
import net.com.gopal.vyapar.splash.SplashActivity;
import net.com.gopal.vyapar.utils.GeneralPref;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            GeneralPref generalPref=new GeneralPref(MainActivity.this);
            Boolean hello = generalPref.getIsFirst();
            if (generalPref.getIsFirst()) {
                Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}