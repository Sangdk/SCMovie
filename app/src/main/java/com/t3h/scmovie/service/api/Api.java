package com.t3h.scmovie.service.api;

import com.t3h.scmovie.model.Actor;
import com.t3h.scmovie.model.Movie;
import com.t3h.scmovie.service.response.ActorResponse;
import com.t3h.scmovie.service.response.GenreResponse;
import com.t3h.scmovie.service.response.MovieResponse;
import com.t3h.scmovie.service.response.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @GET("movie/upcoming")
    Call<MovieResponse> getMoviesUpComing(
            @Query("language") String lang,
            @Query("page") int page,
            @Query("api_key") String apiKey
    );

    @GET("movie/now_playing")
    Call<MovieResponse> getMoviesNowPlaying(
            @Query("language") String lang,
            @Query("page") int page,
            @Query("api_key") String apiKey

    );

    @GET("movie/popular")
    Call<MovieResponse> getMoviesPopular(
            @Query("language") String lang,
            @Query("page") int page,
            @Query("api_key") String apiKey

    );

    @GET("movie/top_rated")
    Call<MovieResponse> getMoviesTopRated(
            @Query("language") String lang,
            @Query("page") int page,
            @Query("api_key") String apiKey

    );

    @GET("person/popular")
    Call<ActorResponse> getActorsPopular(
            @Query("language") String lang,
            @Query("page") int page,
            @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}/videos")
    Call<VideoResponse> getVideos(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey
    );

    @GET("movie/{movie_id}")
    Call<Movie> getMovieDetail(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey
    );
}
