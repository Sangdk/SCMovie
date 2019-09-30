package com.t3h.scmovie.activity.detail;

import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.FragmentPagerAdapter;

import com.t3h.scmovie.R;
import com.t3h.scmovie.adapter.DetailPagerAdapter;
import com.t3h.scmovie.base.BaseActivity;
import com.t3h.scmovie.databinding.ActivityMovieDetailBinding;
import com.t3h.scmovie.fragment.detail.MovieInfoFragment;
import com.t3h.scmovie.fragment.detail.TrailerFragment;
import com.t3h.scmovie.fragment.detail.YoutubeFragment;
import com.t3h.scmovie.model.Movie;
import com.t3h.scmovie.model.Video;
import com.t3h.scmovie.service.api.ApiBuilder;
import com.t3h.scmovie.service.response.VideoResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.t3h.scmovie.Const.API_KEY;
import static com.t3h.scmovie.Const.EXTRA_MOVIE_ID;

public class MovieDetailActivity extends BaseActivity<ActivityMovieDetailBinding> {
    private YoutubeFragment mYoutubeFragment;
    private List<Video> mListTrailer = new ArrayList<>();
    private MovieInfoFragment mMovieInfoFragment = new MovieInfoFragment();
    private TrailerFragment mTrailerFragment = new TrailerFragment();
    private Movie mCurrentMovie;

    @Override
    protected void initAct() {
        initActionBar();
        initYoutubeFragment();
        initViewPager();
    }

    private void initViewPager() {
        DetailPagerAdapter mPagerAdapter = new DetailPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        initFragmentInfo(mPagerAdapter);
    }

    private void initFragmentInfo(DetailPagerAdapter mPagerAdapter) {
        mPagerAdapter.addFragment(mMovieInfoFragment);
        mPagerAdapter.addFragment(mTrailerFragment);
        binding.viewPager.setAdapter(mPagerAdapter);
        binding.viewPager.setPadding(0,0, 80, 20);
        binding.viewPager.setClipToPadding(false);
        binding.viewPager.setPageMargin(20);
    }

    private void initYoutubeFragment() {
        mYoutubeFragment = (YoutubeFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_youtube);
        Intent intent = getIntent();
        int movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0);
        ApiBuilder.getApi().getVideos(movieId, API_KEY).enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                setTrailer(response);
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                Log.d("MovieDetail", "Api response data Failure");
            }
        });
        ApiBuilder.getApi().getMovieDetail(movieId, API_KEY).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                mCurrentMovie = response.body();
                mMovieInfoFragment.setMovie(mCurrentMovie);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }

    private void setTrailer(Response<VideoResponse> response) {
        mListTrailer = response.body().getVideos();
        mYoutubeFragment.setTrailerId(mListTrailer.get(0).getKey());
        mYoutubeFragment.playTrailer();
        mTrailerFragment.setListTrailer(this,mListTrailer,mYoutubeFragment);
    }

    private void initActionBar() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_detail;
    }

}
