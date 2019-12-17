package com.t3h.scmovie.activity.home;

import android.app.Dialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.t3h.scmovie.R;
import com.t3h.scmovie.base.BaseActivity;
import com.t3h.scmovie.databinding.ActivityMainBinding;
import com.t3h.scmovie.fragment.account.AccountFragment;
import com.t3h.scmovie.fragment.account.LoginFragment;
import com.t3h.scmovie.fragment.home.AllMovieFragment;
import com.t3h.scmovie.fragment.home.HomeFragment;
import com.t3h.scmovie.fragment.home.TVFragment;
import com.t3h.scmovie.fragment.search.SearchFragment;
import com.t3h.scmovie.service.InternetReceiver;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements HomeFragment.LoadAll,
        LoginFragment.LoginSuccess, AccountFragment.OnSignOut,
        AllMovieFragment.OnBackPress, InternetReceiver.OnInternetConnectListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private HomeFragment mFragHome = new HomeFragment();
    private AllMovieFragment mFragAllMovie = new AllMovieFragment();
    private SearchFragment mFragSearch = new SearchFragment();
    private AccountFragment mFragAccount = new AccountFragment();
    private TVFragment mFragTV = new TVFragment();
    private LoginFragment mFragLogin = new LoginFragment();
    private List<Fragment> fragments = new ArrayList<>();
    private static boolean isLogin = false;
    private static boolean isDisconnect = false;
    private InternetReceiver receiver = new InternetReceiver();

    @Override
    protected void initAct() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
        init();
    }

    private void init() {
        fragments.add(mFragLogin);
        fragments.add(mFragSearch);
        fragments.add(mFragAccount);
        fragments.add(mFragTV);
        fragments.add(mFragHome);
        addFragment(mFragAccount);
        addFragment(mFragHome);
        showFragment(mFragHome);
        registerPages();

        binding.bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    private void addFragment(Fragment fmAdd) {
        if (!fmAdd.isAdded()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frame_container, fmAdd);
            if (fmAdd instanceof HomeFragment) {
//                transaction.addToBackStack("Add Fragment");
            }
            transaction.commit();
        }
    }

    private void removeFrag(Fragment fmRemove) {
        if (fmRemove.isAdded()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(fmRemove);
            transaction.commit();
        }
    }

    private void showFragment(Fragment fmShow) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.show(fmShow);
        if (fmShow instanceof HomeFragment) {
//            transaction.addToBackStack("Show fragment");
        }
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i).isAdded()) {
                hideFragment(fragments.get(i));
            }
        }
        transaction.commit();
    }

    private void hideFragment(Fragment fmHide) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fmHide);
        if (fmHide instanceof HomeFragment) {
//            transaction.addToBackStack("Hide Fragment");
        }
        transaction.commit();
    }

    private void registerPages() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void sendTitle(String title, int totalPages) {
        final Dialog mLoadingDialog = new Dialog(this);
        mLoadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mLoadingDialog.setContentView(R.layout.dialog_loading);
        mLoadingDialog.show();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mFragAllMovie.isAdded()) {
            transaction.remove(mFragAllMovie);
        }
        transaction.add(R.id.frame_container, mFragAllMovie);
        transaction.show(mFragAllMovie);
        transaction.addToBackStack(AllMovieFragment.class.getSimpleName());
        transaction.commit();
        mFragAllMovie.setData(title, this, totalPages, mLoadingDialog);
    }

    @Override
    public void showAccountScreen(GoogleSignInAccount account, GoogleSignInClient signInClient) {
        showFragment(mFragAccount);
        mFragAccount.setData(account, signInClient);
        isLogin = true;
    }

    @Override
    public void signOut() {
        showFragment(mFragLogin);
        Toast.makeText(this, R.string.log_out_success, Toast.LENGTH_SHORT).show();
        isLogin = false;
    }

    @Override
    public void onAllMovieFragmentBackPress() {

    }

    @Override
    public void onConnected() {
        if (isDisconnect) {
            init();
        }
    }

    @Override
    public void onDisconnected() {
        removeFrag(mFragHome);
        removeFrag(mFragSearch);
        removeFrag(mFragLogin);
        removeFrag(mFragAccount);
        Toast.makeText(this, R.string.check_your_internet_and_try_again, Toast.LENGTH_SHORT).show();
        isDisconnect = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                addFragment(mFragHome);
                showFragment(mFragHome);
                break;
            case R.id.navigation_tv:
                addFragment(mFragTV);
                showFragment(mFragTV);
                break;
            case R.id.navigation_search:
                addFragment(mFragSearch);
                showFragment(mFragSearch);
                break;
            case R.id.navigation_account:
                if (!isLogin) {
                    addFragment(mFragLogin);
                    showFragment(mFragLogin);
                } else {
                    addFragment(mFragAccount);
                    showFragment(mFragAccount);
                }
                break;
        }
        return true;
    }
}
