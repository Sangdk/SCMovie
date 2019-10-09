package com.t3h.scmovie.fragment.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.t3h.scmovie.R;
import com.t3h.scmovie.base.BaseFragment;
import com.t3h.scmovie.databinding.FragmentLoginBinding;

import static android.app.Activity.RESULT_OK;
import static com.t3h.scmovie.Const.GOOGLE_SIGN_IN;

public class LoginFragment extends BaseFragment<FragmentLoginBinding> {
    private final String TAG = LoginFragment.class.getSimpleName();
    private GoogleSignInClient googleSignInClient;
    private LoginSuccess mCallback;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public String getTitle() {
        return "Login Screen";
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallback = (LoginSuccess) context;
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount alreadyLoggedAccount = GoogleSignIn.getLastSignedInAccount(getContext());
        if (alreadyLoggedAccount != null) {
            onLoggedIn(alreadyLoggedAccount, googleSignInClient);
        } else {
            Log.d(TAG, "Not logged in");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        binding.frameGoogleSignIn.setOnClickListener(view -> {
            Intent signIn = googleSignInClient.getSignInIntent();
            startActivityForResult(signIn, GOOGLE_SIGN_IN);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GOOGLE_SIGN_IN:
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        onLoggedIn(account, googleSignInClient);
                    } catch (ApiException e) {
                        Log.d(LoginFragment.class.getSimpleName(), "sign in failure: " + e.getStatusCode());
                    }
                    break;
            }
        }
    }

    private void onLoggedIn(GoogleSignInAccount account, GoogleSignInClient signInClient) {
        mCallback.showAccountScreen(account, signInClient);
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }

    public interface LoginSuccess {
        public void showAccountScreen(GoogleSignInAccount account, GoogleSignInClient signInClient);
    }
}
