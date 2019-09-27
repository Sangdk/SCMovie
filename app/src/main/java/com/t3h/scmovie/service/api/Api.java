package com.t3h.scmovie.service.api;

import com.t3h.scmovie.data.model.Actor;
import com.t3h.scmovie.data.model.Movie;
import com.t3h.scmovie.service.response.GenreResponse;
import com.t3h.scmovie.service.response.MovieResponse;

import java.util.ArrayList;
import java.util.List;

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
            @Query("api_key") String apiKey,
            @Query("language") String lang,
            @Query("page") int page

    );

    @GET("genre/movie/list")
    Call<GenreResponse> getGenres(
            @Query("api_key") String apuKey,
            @Query("language") String lang
    );

    @GET("trending/movie/day")
    Call<MovieResponse> getMoviesTrending();

    @GET("movie/{type}")
    Call<MovieResponse> getMoviesByCategory(@Query("language") String lang,
                                            @Query("api_key") String apiKey,
                                            @Query("type") String type,
                                            @Query("page") int page);

    @GET("discover/movie")
    Call<MovieResponse> getMoviesByGenre(@Query("with_genres") String idGenre,
                                         @Query("language") String lang,
                                         @Query("api_key") String apiKey,
                                         @Query("page") int page);

    @GET("discover/movie")
    Call<MovieResponse> getMoviesByCast(@Query("with_cast") String idCast,
                                        @Query("language") String lang,
                                        @Query("api_key") String apiKey,
                                        @Query("page") int page);

    @GET("discover/movie")
    Call<MovieResponse> getMoviesByCompany(@Query("with_companies") int idCompany,
                                           @Query("language") String lang,
                                           @Query("api_key") String apiKey,
                                           @Query("page") int page);

    @GET("search/movie")
    Call<MovieResponse> searchMovieByName(@Query("query") String key,
                                          @Query("language") String lang,
                                          @Query("api_key") String apiKey,
                                          @Query("page") int page);

    @GET("movie/{movie_id}?append_to_response=credits,videos")
    Call<Movie> getMovieDetail(@Path("movie_id") int id,
                               @Query("api_key") String apiKey);

    @GET("person/{actor_id}")
    Call<Actor> getProfile(@Path("actor_id") String actorId,
                           @Query("api_key") String apiKey);
}
