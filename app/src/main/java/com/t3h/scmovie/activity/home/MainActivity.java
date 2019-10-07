package com.t3h.scmovie.activity.home;

import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.t3h.scmovie.R;
import com.t3h.scmovie.base.BaseActivity;
import com.t3h.scmovie.databinding.ActivityMainBinding;
import com.t3h.scmovie.fragment.home.AllMovieFragment;
import com.t3h.scmovie.fragment.home.HomeFragment;
import com.t3h.scmovie.fragment.search.SearchFragment;


public class MainActivity extends BaseActivity<ActivityMainBinding> implements HomeFragment.LoadAll, View.OnClickListener {


    private HomeFragment mFragHome = new HomeFragment();
    private AllMovieFragment mFragAllMovie = new AllMovieFragment();
    private SearchFragment mFragSearch = new SearchFragment();

    private NavigationView mNavigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void initAct() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_container,mFragSearch);
        transaction.add(R.id.frame_container, mFragHome);
        transaction.show(mFragHome);
        binding.btnHome.setTextColor(getResources().getColor(R.color.color_orange_mango_tango));
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

        registerPages();
    }

    private void showFragment(Fragment fmShow){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mFragHome);
        transaction.hide(mFragSearch);
        transaction.hide(mFragAllMovie);
        transaction.show(fmShow);
        transaction.commit();
    }

    private void registerPages() {
        binding.btnHome.setOnClickListener(this);
        binding.btnTv.setOnClickListener(this);
        binding.btnSearch.setOnClickListener(this);

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

    @Override
    public void sendTitle(String title, int totalPages) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_container, mFragAllMovie);
        transaction.show(mFragAllMovie);
        transaction.addToBackStack(AllMovieFragment.class.getSimpleName());
        transaction.commit();
        final Dialog mLoadingDialog = new Dialog(this);
        mLoadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mLoadingDialog.setContentView(R.layout.dialog_loading);
        mLoadingDialog.show();
        mFragAllMovie.setData(title, this, totalPages, mLoadingDialog);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                showFragment(mFragHome);
                binding.btnHome.setTextColor(getResources().getColor(R.color.color_orange_mango_tango));
                binding.btnSearch.setTextColor(getResources().getColor(R.color.color_white));
                break;
            case R.id.btn_tv:
                break;
            case R.id.btn_search:
                showFragment(mFragSearch);
                binding.btnHome.setTextColor(getResources().getColor(R.color.color_white));
                binding.btnSearch.setTextColor(getResources().getColor(R.color.color_orange_mango_tango));
                break;
        }
    }
}
