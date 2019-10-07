package com.t3h.scmovie.activity.home;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.t3h.scmovie.R;
import com.t3h.scmovie.base.BaseActivity;
import com.t3h.scmovie.databinding.ActivityMainBinding;
import com.t3h.scmovie.fragment.home.HomeFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding>  {

    private HomeFragment mFragHome = new HomeFragment();

    private NavigationView mNavigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void initAct() {
//         = getSupportActionBar();
//        binding.navigation.setOnNavigationItemSelectedListener(this);
//        actionBar.setTitle("Home");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_container, mFragHome);
        transaction.show(mFragHome);
        transaction.commit();


//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle(null);

//        drawerLayout = findViewById(R.id.drawer_layout);
//        mNavigationView = findViewById(R.id.nav_view);
//        toggle = new ActionBarDrawerToggle(
//                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.color_white));
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (toggle.onOptionsItemSelected(item)){
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    //    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.navigation_home:
////                actionBar.setTitle("Home");
//                return true;
//            case R.id.navigation_favorite:
////                actionBar.setTitle("Favorite");
//                return true;
//            case R.id.navigation_user:
////                actionBar.setTitle("User");
//                return true;
//        }
//        return false;
//    }
}
