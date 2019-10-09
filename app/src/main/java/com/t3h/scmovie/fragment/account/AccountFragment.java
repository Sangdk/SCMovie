package com.t3h.scmovie.fragment.account;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.t3h.scmovie.R;
import com.t3h.scmovie.base.BaseFragment;
import com.t3h.scmovie.databinding.FragmentAccountBinding;

public class AccountFragment extends BaseFragment<FragmentAccountBinding> {
    private OnSignOut mCallBack;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account;
    }

    @Override
    public String getTitle() {
        return "Account Screen";
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallBack = (OnSignOut) context;
    }

    public void setData(GoogleSignInAccount account, GoogleSignInClient signInClient) {
        String name = account.getDisplayName();
        Uri uri = account.getPhotoUrl();
        binding.textName.setText(name);
        Glide.with(binding.imgAvatar)
                .load(uri)
                .thumbnail(Glide.with(binding.imgAvatar).load(R.drawable.image_prepare))
                .into(binding.imgAvatar);
        binding.textLogOut.setOnClickListener(view -> {
            signInClient.signOut().addOnCompleteListener(task -> {
                mCallBack.signOut();
            });
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBack = null;
    }

    public interface OnSignOut {
        void signOut();
    }
}
