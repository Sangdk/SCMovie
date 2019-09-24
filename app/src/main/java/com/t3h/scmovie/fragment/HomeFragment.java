package com.t3h.scmovie.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Slide;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;

import com.t3h.scmovie.R;
import com.t3h.scmovie.activity.MainActivity;
import com.t3h.scmovie.adapter.SlideAdapter;
import com.t3h.scmovie.base.BaseAdapter;
import com.t3h.scmovie.base.BaseFragment;
import com.t3h.scmovie.data.model.Movie;
import com.t3h.scmovie.databinding.FragmentHomeBinding;
import com.t3h.scmovie.service.api.ApiBuilder;
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
    private BaseAdapter<Movie> adapter;
    private SlideAdapter mSlideAdapter;
    private static final long PERIOD_TIME_SLIDE = 2000;
    private static final long DELAY_TIME_SLIDE = 100;
    private List<Movie> mSlideMovies = new ArrayList<>();
    private int mCurrentSlide = 0;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new BaseAdapter<>(getContext(), R.layout.item_vertical_movie);
        ApiBuilder.getApi().getMoviesNowPlaying(mLang, 1, apiKey).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                adapter.setData(response.body().getMovies());
                data = response.body().getMovies();
                initDataForSlide();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
        binding.recyclerFamousActor.setAdapter(adapter);
//        initView();
    }

    private void initView() {
//        MainActivity main = (MainActivity) getActivity();
//        main.setSupportActionBar(binding.toolbar);
//        if (main.getSupportActionBar() != null){
//            main.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),);
//        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(@Nullable Palette palette) {
//                int vibrantColor = palette.getVibrantColor(getResources().getColor(R.color.primary_500));
//                binding.collapsingToolbar.setContentScrimColor(vibrantColor);
//                binding.collapsingToolbar.setStatusBarScrimColor(getResources().getColor(R.color.black_trans80));
//            }
//        });
    }

    private void initDataForSlide() {
        mSlideAdapter = new SlideAdapter(getContext());
        Log.d("Home: data ",""+data.size());
        for (int i = 0; i < 5; i++) {
            mSlideMovies.add(data.get(i));
        }
        mSlideAdapter.setMovies(mSlideMovies);
        binding.viewPager.setAdapter(mSlideAdapter);
        initSlideTimer();
        Log.d("Home: slide ",""+mSlideMovies.size());
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
