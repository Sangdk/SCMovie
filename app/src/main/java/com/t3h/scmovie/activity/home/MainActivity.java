package com.t3h.scmovie.activity.home;

import android.app.Dialog;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements HomeFragment.LoadAll,
        View.OnClickListener, LoginFragment.LoginSuccess, AccountFragment.OnSignOut {

    private HomeFragment mFragHome = new HomeFragment();
    private AllMovieFragment mFragAllMovie = new AllMovieFragment();
    private SearchFragment mFragSearch = new SearchFragment();
    private AccountFragment mFragAccount = new AccountFragment();
    private LoginFragment mFragLogin = new LoginFragment();
    private List<Button> buttons = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private boolean isLogin = false;

    @Override
    protected void initAct() {
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
        Toast.makeText(this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
        isLogin = false;
    }
}
