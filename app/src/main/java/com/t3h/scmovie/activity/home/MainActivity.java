package com.t3h.scmovie.activity.home;

import android.app.Dialog;
import android.view.Window;

import androidx.fragment.app.FragmentTransaction;

import com.t3h.scmovie.R;
import com.t3h.scmovie.base.BaseActivity;
import com.t3h.scmovie.databinding.ActivityMainBinding;
import com.t3h.scmovie.fragment.home.AllMovieFragment;
import com.t3h.scmovie.fragment.home.HomeFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements HomeFragment.LoadAll {

    private HomeFragment mFragHome = new HomeFragment();
    private AllMovieFragment mFragAllMovie = new AllMovieFragment();

    @Override
    protected void initAct() {
//         = getSupportActionBar();
//        binding.navigation.setOnNavigationItemSelectedListener(this);
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
