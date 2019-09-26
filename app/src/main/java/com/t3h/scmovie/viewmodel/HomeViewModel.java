package com.t3h.scmovie.viewmodel;

import androidx.lifecycle.ViewModel;

import com.t3h.scmovie.data.model.Movie;
import com.t3h.scmovie.data.model.intf.CategoyName;
import com.t3h.scmovie.service.api.ApiBuilder;
import com.t3h.scmovie.service.response.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private String mLang = "vi";
    private final String apiKey = "f565c6353149e1b97eb7f993217dafac";

    public final List<List<Movie>> categoryMovies = new ArrayList<>();
    public final List<String> categoryTitle = new ArrayList<>();

    private List<Movie> nowPlayingMovies = new ArrayList<>();
    private List<Movie> upComingMovies = new ArrayList<>();
    private List<Movie> popularMovies = new ArrayList<>();
    private List<Movie> topRateMovies = new ArrayList<>();

    public void initHomeView(){
        loadNowPlayingMovies();
        loadPopularMovies();
        loadUpComingMovies();
    }


    private void loadNowPlayingMovies(){
        ApiBuilder.getApi().getMoviesNowPlaying(mLang,1,apiKey).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                nowPlayingMovies.addAll(response.body().getMovies());
                categoryMovies.add(nowPlayingMovies);
                categoryTitle.add(CategoyName.TITLE_NOW_PLAYING);

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    private void loadUpComingMovies(){
        ApiBuilder.getApi().getMoviesUpComing(mLang,1,apiKey).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                upComingMovies.addAll(response.body().getMovies());
                categoryMovies.add(upComingMovies);
                categoryTitle.add(CategoyName.TITLE_UP_COMING);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    private void loadPopularMovies(){
        ApiBuilder.getApi().getMoviesPopular(apiKey,mLang,1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                popularMovies.addAll(response.body().getMovies());
                categoryMovies.add(popularMovies);
                categoryTitle.add(CategoyName.TITLE_POPULAR);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }
}
