package net.com.gopal.vyapar.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.com.gopal.vyapar.DuoMenuAdapter;
import net.com.gopal.vyapar.R;
import net.com.gopal.vyapar.duonavigation.views.DuoDrawerLayout;
import net.com.gopal.vyapar.duonavigation.views.DuoMenuView;
import net.com.gopal.vyapar.duonavigation.widgets.DuoDrawerToggle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class DashBoardActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView toolbar_text;
    private DuoDrawerLayout duodrawerLayout;
    private DuoLock duoLock;
    private ViewHolder mViewHolder;
    private DuoMenuAdapter mMenuAdapter;
    private DashBoardActivity.SetName setName;
    private TextView userName;
    private ArrayList<String> mTitles = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_dash_board);
            setTheme(R.style.AppTheme);
            toolbar = findViewById(R.id.toolbar_main);
            userName = findViewById(R.id.userNameDrawer);
            toolbar.post(new Runnable() {
                @Override
                public void run() {
                    Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_hamburger_filled_blue, null);
                    toolbar.setNavigationIcon(d);
                }
            });
//            mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.ld_activityScreenTitles)));
//            mViewHolder = new ViewHolder();
//            duoNavigationLock();
//            // handleToolbar();
//            handleMenu();
//            handleDrawer();
//            setName = name -> {
//                userName.setText(name);
//
//            };

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void handleDrawer() {
        final DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(DashBoardActivity.this,
                mViewHolder.mDuoDrawerLayout,
                mViewHolder.mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();

      /*  ValueAnimator anim = ValueAnimator.ofFloat(0,1);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float slideOffset = (Float) valueAnimator.getAnimatedValue();
                duoDrawerToggle.onDrawerSlide(mViewHolder.mDuoDrawerLayout, slideOffset);
            }
        });


        anim.setInterpolator(new DecelerateInterpolator());
        // You can change this duration to more closely match that of the default animation.
        anim.setDuration(100);
        anim.start();
*/
    }
    public interface SetName {
        void setName(String name);
    }
//    @Override
//    public void onFooterClicked() {
//
//    }
//
//    @Override
//    public void onHeaderClicked() {
//
//    }
//
//    @Override
//    public void onOptionClicked(int position, Object objectClicked) {
//
//    }
    private void duoNavigationLock() {
        duoLock = new DuoLock() {
            @Override
            public void lockNavigation(int lockmode) {
                duodrawerLayout.setDrawerLockMode(DuoDrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                mViewHolder.mDuoMenuView.setVisibility(View.GONE);
                duodrawerLayout.closeDrawer();
            }
        };
    }
    public interface DuoLock extends Serializable {
        void lockNavigation(int lockmode);
    }
    private class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView mDuoMenuView;
        private Toolbar mToolbar;

        ViewHolder() {
            mDuoDrawerLayout = findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            mToolbar = findViewById(R.id.toolbar_main);
            mDuoMenuView.getHeaderView().setBackgroundColor(Color.WHITE);
        }
    }
    private void handleMenu() {

        mMenuAdapter = new DuoMenuAdapter(mTitles);
        mViewHolder.mDuoMenuView.setOnMenuClickListener((DuoMenuView.OnMenuClickListener) this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);

    }

}