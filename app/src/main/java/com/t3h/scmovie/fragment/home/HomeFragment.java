package com.t3h.scmovie.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.t3h.scmovie.R;
import com.t3h.scmovie.activity.detail.MovieDetailActivity;
import com.t3h.scmovie.activity.detail.PeopleDetailActivity;
import com.t3h.scmovie.adapter.SlideAdapter;
import com.t3h.scmovie.base.BaseAdapter;
import com.t3h.scmovie.base.BaseFragment;
import com.t3h.scmovie.model.People;
import com.t3h.scmovie.model.Movie;
import com.t3h.scmovie.databinding.FragmentHomeBinding;
import com.t3h.scmovie.service.api.ApiBuilder;
import com.t3h.scmovie.service.response.PeopleResponse;
import com.t3h.scmovie.service.response.MovieResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.t3h.scmovie.utils.Const.API_KEY;
import static com.t3h.scmovie.utils.Const.EXTRA_MOVIE_ID;
import static com.t3h.scmovie.utils.Const.EXTRA_PERSON;
import static com.t3h.scmovie.utils.Const.EXTRA_PERSON_ID;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> implements
        MovieItemClickListener, SlideAdapter.OnClickSlideListener,
        PeopleItemClickListener, View.OnClickListener {
    private List<Movie> data = new ArrayList<>();
    private BaseAdapter<Movie> mAdapterNowPlaying;
    private BaseAdapter<Movie> mAdapterUpComing;
    private BaseAdapter<Movie> mAdapterMoviePopular;
    private BaseAdapter<Movie> mAdapterTopRated;
    private BaseAdapter<People> mAdapterActorPopular;
    private SlideAdapter mSlideAdapter;
    private static final long PERIOD_TIME_SLIDE = 2000;
    private static final long DELAY_TIME_SLIDE = 100;
    private List<Movie> mSlideMovies = new ArrayList<>();
    private int mCurrentSlide = 0;
    private LoadAll mCallback;
    private int totalPagesNowPlaying = 0;
    private int totalPagesUpComing = 0;
    private int totalPagesPopular = 0;
    private int totalPagesTopRated = 0;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapterNowPlaying = new BaseAdapter<>(getContext(), R.layout.item_vertical_movie);
        mAdapterUpComing = new BaseAdapter<>(getContext(), R.layout.item_vertical_movie);
        mAdapterMoviePopular = new BaseAdapter<>(getContext(), R.layout.item_vertical_movie);
        mAdapterTopRated = new BaseAdapter<>(getContext(), R.layout.item_vertical_movie);
        mAdapterActorPopular = new BaseAdapter<>(getContext(), R.layout.item_people);

        initToolBar();
        String mLang = "vi";

        ApiBuilder.getApi().getMoviesNowPlaying(mLang, 1, API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                initDataNowPlaying(response);
                initDataForSlide();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        ApiBuilder.getApi().getMoviesUpComing(mLang, 1, API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                initDataUpComing(response);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
            }
        });

        ApiBuilder.getApi().getMoviesPopular(mLang, 1, API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                initDataPopular(response);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        ApiBuilder.getApi().getMoviesTopRated(mLang, 1, API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                initDataTopRated(response);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        ApiBuilder.getApi().getActorsPopular(mLang, 1, API_KEY).enqueue(new Callback<PeopleResponse>() {
            @Override
            public void onResponse(Call<PeopleResponse> call, Response<PeopleResponse> response) {
                initDataForActor(response);
            }

            @Override
            public void onFailure(Call<PeopleResponse> call, Throwable t) {

            }
        });
        binding.viewPager.setVisibility(View.VISIBLE);
        registerListener();
    }

    private void registerListener() {
        mAdapterUpComing.setListener(this);
        mAdapterNowPlaying.setListener(this);
        mAdapterMoviePopular.setListener(this);
        mAdapterTopRated.setListener(this);
        mAdapterActorPopular.setListener(this);

        binding.textLoadAllUpComing.setOnClickListener(this);
        binding.textLoadAllNowPlaying.setOnClickListener(this);
        binding.textLoadAllPopular.setOnClickListener(this);
        binding.textLoadAllTopRated.setOnClickListener(this);
    }

    private void initToolBar() {
//        binding.appBarLayout.addOnOffsetChangedListener(
//                (appBarLayout, verticalOffset) -> {
//                    if (Math.abs(verticalOffset) > 200) {
//                        binding.collapsingToolbar.setTitleEnabled(true);
//                        binding.collapsingToolbar.setTitle("Home");
//                        binding.viewPager.setVisibility(View.GONE);
//                    } else {
//                        binding.collapsingToolbar.setTitleEnabled(false);
//                        binding.viewPager.setVisibility(View.VISIBLE);
//                    }
//                }
//        );
        binding.viewPager.setPadding(80, 40, 80, 20);
        binding.viewPager.setClipToPadding(false);
        binding.viewPager.setPageMargin(40);
    }

    private void initDataForActor(Response<PeopleResponse> response) {
        mAdapterActorPopular.setData(response.body().getPeople());
        binding.recyclerActorPopular.setAdapter(mAdapterActorPopular);
    }

    private void initDataTopRated(Response<MovieResponse> response) {
        mAdapterTopRated.setData(response.body().getMovies());
        totalPagesTopRated = response.body().getTotalPages();
        binding.recyclerTopRated.setAdapter(mAdapterTopRated);
    }

    private void initDataPopular(Response<MovieResponse> response) {
        mAdapterMoviePopular.setData(response.body().getMovies());
        totalPagesPopular = response.body().getTotalPages();
        binding.recyclerPopular.setAdapter(mAdapterMoviePopular);
    }

    private void initDataUpComing(Response<MovieResponse> response) {
        mAdapterUpComing.setData(response.body().getMovies());
        totalPagesUpComing = response.body().getTotalPages();
        binding.recyclerUpComing.setAdapter(mAdapterUpComing);
    }

    private void initDataNowPlaying(Response<MovieResponse> response) {
        mAdapterNowPlaying.setData(response.body().getMovies());
        data = response.body().getMovies();
        totalPagesNowPlaying = response.body().getTotalPages();
        binding.recyclerNowPlaying.setAdapter(mAdapterNowPlaying);
    }

    private void initDataForSlide() {
        mSlideAdapter = new SlideAdapter(getContext());
        for (int i = 0; i < 5; i++) {
            mSlideMovies.add(data.get(i));
        }
        mSlideAdapter.setMovies(mSlideMovies);
        binding.viewPager.setAdapter(mSlideAdapter);
        initSlideTimer();
        mSlideAdapter.setListener(this);
    }

    private void initSlideTimer() {
        final Handler handler = new Handler();
        final Runnable update = () -> {
            if (mCurrentSlide == binding.viewPager.getAdapter().getCount()) {
                mCurrentSlide = 0;
            }
            binding.viewPager.setCurrentItem(mCurrentSlide++, true);
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

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movie.getId());
        startActivity(intent);
    }

    @Override
    public void onClickSlide() {
        Movie movie = null;
        if (mCurrentSlide > 0) {
            movie = mSlideMovies.get(mCurrentSlide - 1);
        }
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movie.getId());
        startActivity(intent);
    }

    @Override
    public void OnPeopleClick(People people) {
        Gson gson = new Gson();
        String jsonMovies = gson.toJson(people.getKnowFor());
        Intent intent = new Intent(getContext(), PeopleDetailActivity.class);
        intent.putExtra(EXTRA_PERSON_ID, people.getId());
        intent.putExtra(EXTRA_PERSON, jsonMovies);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_load_all_now_playing:
                mCallback.sendTitle(getResources()
                        .getString(R.string.movie_now_playing), totalPagesNowPlaying);
                break;
            case R.id.text_load_all_up_coming:
                mCallback.sendTitle(getResources()
                        .getString(R.string.movie_up_coming), totalPagesUpComing);
                break;
            case R.id.text_load_all_popular:
                mCallback.sendTitle(getResources()
                        .getString(R.string.movie_popular), totalPagesPopular);
                break;
            case R.id.text_load_all_top_rated:
                mCallback.sendTitle(getResources()
                        .getString(R.string.movie_top_rated), totalPagesTopRated);
                break;
        }
    }

    public interface LoadAll {
        void sendTitle(String title, int totalPage);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mCallback = (LoadAll) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement TextClicked");
        }
    }

    @Override
    public void onDetach() {
        mCallback = null; // => avoid leaking
        super.onDetach();
    }
}
