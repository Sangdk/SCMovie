package com.t3h.scmovie.activity.detail;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentPagerAdapter;

import com.t3h.scmovie.R;
import com.t3h.scmovie.adapter.DetailPagerAdapter;
import com.t3h.scmovie.base.BaseActivity;
import com.t3h.scmovie.databinding.ActivityMovieDetailBinding;
import com.t3h.scmovie.fragment.detail.MovieInfoFragment;
import com.t3h.scmovie.fragment.detail.MovieProductFragment;
import com.t3h.scmovie.fragment.detail.TrailerFragment;
import com.t3h.scmovie.fragment.detail.YoutubeFragment;
import com.t3h.scmovie.model.Movie;
import com.t3h.scmovie.model.People;
import com.t3h.scmovie.model.Video;
import com.t3h.scmovie.service.api.ApiBuilder;
import com.t3h.scmovie.service.response.PeopleResponse;
import com.t3h.scmovie.service.response.VideoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.t3h.scmovie.Const.API_KEY;
import static com.t3h.scmovie.Const.EXTRA_MOVIE_ID;

public class MovieDetailActivity extends BaseActivity<ActivityMovieDetailBinding> {
    private YoutubeFragment mYoutubeFragment;
    private MovieInfoFragment mMovieInfoFragment;
    private TrailerFragment mTrailerFragment;
    private MovieProductFragment mMovieProductFragment;
    private Movie mCurrentMovie;
    private Dialog mLoadingDialog;

    @Override
    protected void initAct() {
        mLoadingDialog = new Dialog(this);
        mLoadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mLoadingDialog.setContentView(R.layout.dialog_loading);

        initFragment();
        initActionBar();
        getData();
        initViewPager();
    }

    private void initFragment() {
        mYoutubeFragment = (YoutubeFragment) getFragmentManager()
                .findFragmentById(R.id.fragment_youtube);
        mMovieInfoFragment = new MovieInfoFragment();
        mTrailerFragment = new TrailerFragment();
        mMovieProductFragment = new MovieProductFragment();
    }

    private void initViewPager() {
        mLoadingDialog.show();
        DetailPagerAdapter mPagerAdapter = new DetailPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mPagerAdapter.addFragment(mMovieInfoFragment);
        mPagerAdapter.addFragment(mTrailerFragment);
        mPagerAdapter.addFragment(mMovieProductFragment);
        binding.viewPager.setAdapter(mPagerAdapter);
        binding.viewPager.setPadding(10, 0, 80, 20);
        binding.viewPager.setClipToPadding(false);
        binding.viewPager.setPageMargin(20);
    }

    private void getData() {
        Intent intent = getIntent();
        int movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0);
        ApiBuilder.getApi().getVideos(movieId, API_KEY).enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                setTrailer(response);
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                Log.d("MovieDetail", "Get videos response data failure");
            }
        });
        ApiBuilder.getApi().getMovieDetail(movieId, API_KEY).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                setMovieInfo(response);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d("MovieDetail", "Get movie detail response data failure");
            }
        });
        ApiBuilder.getApi().getCredits(movieId, API_KEY).enqueue(new Callback<PeopleResponse>() {
            @Override
            public void onResponse(Call<PeopleResponse> call, Response<PeopleResponse> response) {
                setCredits(response);
            }

            @Override
            public void onFailure(Call<PeopleResponse> call, Throwable t) {
                Log.d("MovieDetail", "Get credits response data failure");
            }
        });
    }

    private void setCredits(Response<PeopleResponse> response) {
        if (response.body() != null) {
            List<People> mCasts = response.body().getCasts();
            List<People> mCrews = response.body().getCrews();
            mMovieProductFragment.setListCast(this, mCasts);
            mMovieProductFragment.setListCrew(this, mCrews);
        }
    }

    private void setMovieInfo(Response<Movie> response) {
        mCurrentMovie = response.body();
        mMovieInfoFragment.setMovie(mCurrentMovie,mLoadingDialog);
        mMovieProductFragment.setProduct(mCurrentMovie);
    }

    private void setTrailer(Response<VideoResponse> response) {
        if (response.body() != null) {
            List<Video> mListTrailer = response.body().getVideos();
            mYoutubeFragment.setTrailerId(mListTrailer.get(0).getKey());
            int position = mYoutubeFragment.getCurrentPosition();
            mYoutubeFragment.playTrailer(position);
            mTrailerFragment.setListTrailer(this, mListTrailer, mYoutubeFragment);
        }
    }

    private void initActionBar() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mYoutubeFragment.setFullScreen(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_detail;
    }

}
