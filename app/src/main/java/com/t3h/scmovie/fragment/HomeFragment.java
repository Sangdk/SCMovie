package com.t3h.scmovie.fragment;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.t3h.scmovie.R;
import com.t3h.scmovie.adapter.CategoryAdapter;
import com.t3h.scmovie.adapter.SlideAdapter;
import com.t3h.scmovie.base.BaseAdapter;
import com.t3h.scmovie.base.BaseFragment;
import com.t3h.scmovie.model.Genre;
import com.t3h.scmovie.model.Movie;
import com.t3h.scmovie.databinding.FragmentHomeBinding;
import com.t3h.scmovie.service.api.ApiBuilder;
import com.t3h.scmovie.service.response.GenreResponse;
import com.t3h.scmovie.service.response.MovieResponse;
import com.t3h.scmovie.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> implements CategoryAdapter.CategoryListener {
    private String mLang = "vi";
    private final String apiKey = "f565c6353149e1b97eb7f993217dafac";
    private List<Movie> data = new ArrayList<>();
    private BaseAdapter<Movie> adapter;
    private List<Genre> mGenres = new ArrayList<>();
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;

//    private List<List<Movie>> categoryMovies = new ArrayList<>();
//    private List<String> categoryTitle = new ArrayList<>();
//
//    private List<Movie> nowPlayingMovies = new ArrayList<>();
//    private List<Movie> upComingMovies = new ArrayList<>();
//    private List<Movie> popularMovies = new ArrayList<>();
//    private List<Movie> topRateMovies = new ArrayList<>();
    private BaseAdapter<Movie> adapter_now_playing;
    private BaseAdapter<Movie> adapter_up_coming;
    private BaseAdapter<Movie> adapter_popular;
    private BaseAdapter<Movie> adapter_top_rated;
    private SlideAdapter mSlideAdapter;
    private static final long PERIOD_TIME_SLIDE = 2000;
    private static final long DELAY_TIME_SLIDE = 100;
    private List<Movie> mSlideMovies = new ArrayList<>();
    private int mCurrentSlide = 0;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = DataBindingUtil.setContentView(getActivity(),R.layout.fragment_home);
        binding.recyclerViewCategory.setAdapter(new CategoryAdapter(getContext(),this));
        binding.recyclerViewCategory.setNestedScrollingEnabled(false);

        homeViewModel.initHomeView();

//        adapter = new BaseAdapter<>(getContext(), R.layout.item_vertical_movie);
//        ApiBuilder.getApi().getMoviesNowPlaying(mLang,1,apiKey).enqueue(new Callback<MovieResponse>() {
//            @Override
//            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
//                data = response.body().getMovies();
//                adapter.setData(response.body().getMovies());
//                Log.e("AAA", data.size() + "a");
//                int i = data.size();
//            }
//
//            @Override
//            public void onFailure(Call<MovieResponse> call, Throwable t) {
//
//            }
//        });
//        binding.recyclerFamousActor.setAdapter(adapter);

//        categoryAdapter = new CategoryAdapter(getContext());
//        ApiBuilder.getApi().getGenres(apiKey,mLang).enqueue(new Callback<GenreResponse>() {
//            @Override
//            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
//                categoryAdapter.setData(response.body().getGenres());
//                mGenres = response.body().getGenres();
//                int a = mGenres.size();
//                Log.e("aaaa", "onResponse: " + mGenres.size() );
//            }
//
//            @Override
//            public void onFailure(Call<GenreResponse> call, Throwable t) {
//
//            }
//        });
//        binding.recyclerViewCategory.setAdapter(categoryAdapter);



        adapter_now_playing = new BaseAdapter<>(getContext(), R.layout.item_vertical_movie);
        adapter_up_coming = new BaseAdapter<>(getContext(), R.layout.item_vertical_movie);
        adapter_popular = new BaseAdapter<>(getContext(), R.layout.item_vertical_movie);
        adapter_top_rated = new BaseAdapter<>(getContext(), R.layout.item_vertical_movie);
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

    }

    private void initDataTopRated(Response<MovieResponse> response) {
        adapter_top_rated.setData(response.body().getMovies());
        binding.recyclerTopRated.setAdapter(adapter_top_rated);
    }

    private void initDataPopular(Response<MovieResponse> response) {
        adapter_popular.setData(response.body().getMovies());
        binding.recyclerPopular.setAdapter(adapter_popular);
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




//    @Override
//    public void onMovieClickListener(Movie movie) {
//
//    }

    @Override
    public void onMovieClickListener(com.t3h.scmovie.data.model.Movie movie) {

    }

    @Override
    public void onLoadMoreClickListener(String category) {

    }
}
