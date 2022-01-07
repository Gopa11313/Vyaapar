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
        Boolean hello=GeneralPref.getIsFirst();
        if(GeneralPref.getIsFirst()==true) {
            Intent intent = new Intent(MainActivity.this, SplashActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent =new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}