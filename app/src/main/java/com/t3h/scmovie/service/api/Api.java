package com.t3h.scmovie.service.api;

import com.t3h.scmovie.model.Movie;
import com.t3h.scmovie.model.People;
import com.t3h.scmovie.service.response.GenreResponse;
import com.t3h.scmovie.service.response.PeopleResponse;
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
    Call<PeopleResponse> getActorsPopular(
            @Query("language") String lang,
            @Query("page") int page,
            @Query("api_key") String apiKey
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

    @GET("movie/{movie_id}/credits")
    Call<PeopleResponse> getCredits(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey
    );

    @GET("person/{person_id}")
    Call<People> getPeopleDetail(@Path("person_id") int personId,
                                 @Query("api_key") String apiKey);

    @GET("search/multi")
    Call<MovieResponse> searchMovies(@Query("api_key") String apiKey,
                                     @Query("query") String query);
}
