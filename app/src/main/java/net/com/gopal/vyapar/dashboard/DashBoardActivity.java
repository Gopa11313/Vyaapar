package net.com.gopal.vyapar.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.com.gopal.vyapar.DuoMenuAdapter;
import net.com.gopal.vyapar.R;
import net.com.gopal.vyapar.duonavigation.views.DuoDrawerLayout;
import net.com.gopal.vyapar.duonavigation.views.DuoMenuView;
import net.com.gopal.vyapar.duonavigation.widgets.DuoDrawerToggle;
import net.com.gopal.vyapar.folder.FolderActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class DashBoardActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private TextView toolbar_text;
    private DuoDrawerLayout duodrawerLayout;
    private TextView userName;
    private FrameLayout container;
    private ArrayList<String> mTitles = new ArrayList<>();
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_dash_board);
            setTheme(R.style.AppTheme);
            toolbar = findViewById(R.id.toolbar_main);
            userName = findViewById(R.id.userNameDrawer);
            container = findViewById(R.id.container);
            toolbar.post(new Runnable() {
                @Override
                public void run() {
                    Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_hamburger_filled_blue, null);
                    toolbar.setNavigationIcon(d);
                    toolbar.setNavigationOnClickListener(v -> {
                        Intent intent=new Intent(DashBoardActivity.this, FolderActivity.class);
                        startActivity(intent);
                    });
                }
            });
            DashboardFragment fragment = new DashboardFragment();
            fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.container, fragment).commit();
            toolbar.setOnClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.toolbar:
//
//                finish();
//                break;
//        }
    }
}