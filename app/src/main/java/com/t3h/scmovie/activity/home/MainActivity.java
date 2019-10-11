package com.t3h.scmovie.activity.home;

import android.app.Dialog;

import android.content.IntentFilter;
import android.net.ConnectivityManager;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.t3h.scmovie.R;
import com.t3h.scmovie.base.BaseActivity;
import com.t3h.scmovie.databinding.ActivityMainBinding;
import com.t3h.scmovie.fragment.account.AccountFragment;
import com.t3h.scmovie.fragment.account.LoginFragment;
import com.t3h.scmovie.fragment.home.AllMovieFragment;
import com.t3h.scmovie.fragment.home.HomeFragment;
import com.t3h.scmovie.fragment.search.SearchFragment;
import com.t3h.scmovie.service.InternetReceiver;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements HomeFragment.LoadAll,
        View.OnClickListener, LoginFragment.LoginSuccess, AccountFragment.OnSignOut,
        AllMovieFragment.OnBackPress, InternetReceiver.OnInternetConnectListener {

    private HomeFragment mFragHome = new HomeFragment();
    private AllMovieFragment mFragAllMovie = new AllMovieFragment();
    private SearchFragment mFragSearch = new SearchFragment();
    private AccountFragment mFragAccount = new AccountFragment();
    private LoginFragment mFragLogin = new LoginFragment();
    private List<Button> buttons = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private boolean isLogin = false;
    private boolean isDisconnect = false;
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
        fragments.add(mFragHome);
        fragments.add(mFragSearch);
        fragments.add(mFragAccount);
        addFragment(mFragAccount);
        addFragment(mFragHome);
        showFragment(mFragHome);
        binding.btnHome.setTextColor(getResources().getColor(R.color.color_orange_mango_tango));
        registerPages();
    }

    private void addFragment(Fragment fmAdd) {
        if (!fmAdd.isAdded()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frame_container, fmAdd);
            transaction.commit();
            if (fmAdd instanceof HomeFragment) {
                transaction.addToBackStack("Add Fragment");
            }
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
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i).isAdded()) {
                transaction.hide(fragments.get(i));
            }
        }
        transaction.show(fmShow);
        transaction.commit();
        transaction.addToBackStack("Show fragment");
    }

    private void registerPages() {
        buttons.add(binding.btnHome);
        buttons.add(binding.btnSearch);
        buttons.add(binding.btnAccount);
        buttons.add(binding.btnTv);
        binding.btnHome.setOnClickListener(this);
        binding.btnTv.setOnClickListener(this);
        binding.btnSearch.setOnClickListener(this);
        binding.btnAccount.setOnClickListener(this);
    }

    private void setFocus(Button btnFocus) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setTextColor(getResources().getColor(R.color.color_white));
        }
        btnFocus.setTextColor(getResources().getColor(R.color.color_orange_mango_tango));
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
        binding.linearPages.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                addFragment(mFragHome);
                showFragment(mFragHome);
                setFocus(binding.btnHome);
                break;
            case R.id.btn_tv:
                setFocus(binding.btnTv);
                break;
            case R.id.btn_search:
                addFragment(mFragSearch);
                showFragment(mFragSearch);
                setFocus(binding.btnSearch);
                break;
            case R.id.btn_account:
                if (!isLogin) {
                    addFragment(mFragLogin);
                    showFragment(mFragLogin);
                } else {
                    addFragment(mFragAccount);
                    showFragment(mFragAccount);
                }
                setFocus(binding.btnAccount);
                break;
        }
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
        binding.linearPages.setVisibility(View.VISIBLE);
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
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
}
