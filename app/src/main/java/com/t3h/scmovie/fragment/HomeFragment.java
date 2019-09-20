package com.t3h.scmovie.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.t3h.scmovie.R;
import com.t3h.scmovie.base.BaseAdapter;
import com.t3h.scmovie.base.BaseFragment;
import com.t3h.scmovie.data.model.Movie;
import com.t3h.scmovie.databinding.FragmentHomeBinding;
import com.t3h.scmovie.service.api.ApiBuilder;
import com.t3h.scmovie.service.response.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {
    private String mLang = "vi";
    private final String apiKey = "f565c6353149e1b97eb7f993217dafac";
    private List<Movie> data = new ArrayList<>();
    private BaseAdapter<Movie> adapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new BaseAdapter<>(getContext(), R.layout.item_vertical_movie);
        ApiBuilder.getApi().getMoviesByCategory(mLang, apiKey, 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                adapter.setData(response.body().getMovies());
                data = response.body().getMovies();
                Log.d("AAA", data.size() + "");
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        binding.recyclerFamousActor.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public String getTitle() {
        return "Home";
    }
}
