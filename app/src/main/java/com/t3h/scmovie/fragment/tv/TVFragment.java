package com.t3h.scmovie.fragment.tv;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.t3h.scmovie.R;
import com.t3h.scmovie.adapter.TVAdapter;
import com.t3h.scmovie.base.BaseFragment;
import com.t3h.scmovie.databinding.FragmentTvBinding;
import com.t3h.scmovie.model.tv.TV;
import com.t3h.scmovie.service.api.ApiBuilder;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.t3h.scmovie.utils.Const.API_KEY;

public class TVFragment extends BaseFragment<FragmentTvBinding> {

    private TVAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tv;
    }

    @Override
    public String getTitle() {
        return "TV";
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new TVAdapter(getContext());
        int i = new Random().nextInt(51);
        ApiBuilder.getApi().getTVShow(i,API_KEY,"vi").enqueue(new Callback<TV>() {
            @Override
            public void onResponse(Call<TV> call, Response<TV> response) {
//                adapter.setmList();
            }

            @Override
            public void onFailure(Call<TV> call, Throwable t) {

            }
        });
    }
}
