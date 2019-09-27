package com.t3h.scmovie.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.t3h.scmovie.R;
import com.t3h.scmovie.base.BaseActivity;
import com.t3h.scmovie.databinding.ActivityMainBinding;
import com.t3h.scmovie.fragment.HomeFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements BottomNavigationView.OnNavigationItemSelectedListener {

    private HomeFragment mFragHome = new HomeFragment();

    @Override
    protected void initAct() {
//         = getSupportActionBar();
        binding.navigation.setOnNavigationItemSelectedListener(this);
//        actionBar.setTitle("Home");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_container, mFragHome);
        transaction.show(mFragHome);
        transaction.commit();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_home:
//                actionBar.setTitle("Home");
                return true;
            case R.id.navigation_favorite:
//                actionBar.setTitle("Favorite");
                return true;
            case R.id.navigation_user:
//                actionBar.setTitle("User");
                return true;
        }
        return false;
    }
}
