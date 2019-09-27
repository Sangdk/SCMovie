package com.t3h.scmovie.fragment;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.t3h.scmovie.R;
import com.t3h.scmovie.adapter.SlideAdapter;
import com.t3h.scmovie.base.BaseAdapter;
import com.t3h.scmovie.base.BaseFragment;
import com.t3h.scmovie.model.Actor;
import com.t3h.scmovie.model.Movie;
import com.t3h.scmovie.databinding.FragmentHomeBinding;
import com.t3h.scmovie.service.api.ApiBuilder;
import com.t3h.scmovie.service.response.ActorResponse;
import com.t3h.scmovie.service.response.MovieResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {
    private String mLang = "vi";
    private final String apiKey = "f565c6353149e1b97eb7f993217dafac";
    private List<Movie> data = new ArrayList<>();
    private BaseAdapter<Movie> adapter_now_playing;
    private BaseAdapter<Movie> adapter_up_coming;
    private BaseAdapter<Movie> adapter_movie_popular;
    private BaseAdapter<Movie> adapter_top_rated;
    private BaseAdapter<Actor> adapter_actor_popular;
    private SlideAdapter mSlideAdapter;
    private static final long PERIOD_TIME_SLIDE = 2000;
    private static final long DELAY_TIME_SLIDE = 100;
    private List<Movie> mSlideMovies = new ArrayList<>();
    private int mCurrentSlide = 0;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter_now_playing = new BaseAdapter<>(getContext(), R.layout.item_vertical_movie);
        adapter_up_coming = new BaseAdapter<>(getContext(), R.layout.item_vertical_movie);
        adapter_movie_popular = new BaseAdapter<>(getContext(), R.layout.item_vertical_movie);
        adapter_top_rated = new BaseAdapter<>(getContext(), R.layout.item_vertical_movie);
        adapter_actor_popular = new BaseAdapter<>(getContext(), R.layout.item_actor);
        ApiBuilder.getApi().getMoviesNowPlaying(mLang, 1, apiKey).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                initDataNowPlaying(response);
                initDataForSlide();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        ApiBuilder.getApi().getMoviesUpComing(mLang, 1, apiKey).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                initDataUpComing(response);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        ApiBuilder.getApi().getMoviesPopular(mLang, 1, apiKey).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                initDataPopular(response);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        ApiBuilder.getApi().getMoviesTopRated(mLang, 1, apiKey).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                initDataTopRated(response);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        ApiBuilder.getApi().getActorsPopular(mLang, 1, apiKey).enqueue(new Callback<ActorResponse>() {
            @Override
            public void onResponse(Call<ActorResponse> call, Response<ActorResponse> response) {
                initDataForActor(response);
            }

            @Override
            public void onFailure(Call<ActorResponse> call, Throwable t) {

            }
        });

    }

    private void initDataForActor(Response<ActorResponse> response) {
        adapter_actor_popular.setData(response.body().getActors());
        binding.recyclerActorPopular.setAdapter(adapter_actor_popular);
    }

    private void initDataTopRated(Response<MovieResponse> response) {
        adapter_top_rated.setData(response.body().getMovies());
        binding.recyclerTopRated.setAdapter(adapter_top_rated);
    }

    private void initDataPopular(Response<MovieResponse> response) {
        adapter_movie_popular.setData(response.body().getMovies());
        binding.recyclerPopular.setAdapter(adapter_movie_popular);
    }

    private void initDataUpComing(Response<MovieResponse> response) {
        adapter_up_coming.setData(response.body().getMovies());
        binding.recyclerUpComing.setAdapter(adapter_up_coming);
    }

    private void initDataNowPlaying(Response<MovieResponse> response) {
        adapter_now_playing.setData(response.body().getMovies());
        data = response.body().getMovies();
        binding.recyclerNowPlaying.setAdapter(adapter_now_playing);
    }

    private void initDataForSlide() {
        mSlideAdapter = new SlideAdapter(getContext());
        for (int i = 0; i < 5; i++) {
            mSlideMovies.add(data.get(i));
        }
        mSlideAdapter.setMovies(mSlideMovies);
        binding.viewPager.setAdapter(mSlideAdapter);
        initSlideTimer();
    }

    private void initSlideTimer() {
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (mCurrentSlide == binding.viewPager.getAdapter().getCount()) {
                    mCurrentSlide = 0;
                }
                binding.viewPager.setCurrentItem(mCurrentSlide++, true);
            }
        };
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_TIME_SLIDE, PERIOD_TIME_SLIDE);
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
