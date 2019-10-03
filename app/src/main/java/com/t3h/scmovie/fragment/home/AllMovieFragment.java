package com.t3h.scmovie.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.t3h.scmovie.R;
import com.t3h.scmovie.activity.detail.MovieDetailActivity;
import com.t3h.scmovie.base.BaseAdapter;
import com.t3h.scmovie.base.BaseFragment;
import com.t3h.scmovie.databinding.FragmentMovieAllBinding;
import com.t3h.scmovie.model.Movie;
import com.t3h.scmovie.service.api.ApiBuilder;
import com.t3h.scmovie.service.response.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.t3h.scmovie.Const.API_KEY;
import static com.t3h.scmovie.Const.EXTRA_MOVIE_ID;

public class AllMovieFragment extends BaseFragment<FragmentMovieAllBinding> {
    private List<Movie> moviesAll = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie_all;
    }

    @Override
    public String getTitle() {
        return "All Movie";
    }

    public void setData(String title, Context context, int total) {
        GetAllAsync async = new GetAllAsync();
        async.setContext(context);
        async.setTitle(title);
        async.setTotal(total);
        async.execute();
    }

    private class GetAllAsync extends AsyncTask<Void, Void, Void> implements MovieItemClickListener {
        private String title;
        private int total;
        private BaseAdapter allMoviesAdapter;

        void setTitle(String title) {
            this.title = title;
        }

        void setTotal(int total) {
            this.total = total;
        }

        public void setContext(Context context) {
            allMoviesAdapter = new BaseAdapter(context, R.layout.item_vertical_movie);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            setData(title, total);
            return null;
        }

        private void setData(String title, int totalPages) {
            String mLang = "vi";
            for (int i = 1; i <= totalPages; i++) {
                addSubMovies(i, mLang, title);
            }
        }

        private void addSubMovies(int i, String mLang, String title) {
            final String movieUpComing = "Phim sắp chiếu";
            final String movieNowPlaying = "Phim đang chiếu";
            final String moviePopular = "Nhiều người xem";
            final String movieTopRated = "Phim nổi bật";
            switch (title) {
                case movieUpComing:
                    moviesAll.clear();
                    ApiBuilder.getApi().getMoviesUpComing(mLang, i, API_KEY).enqueue(new Callback<MovieResponse>() {
                        @Override
                        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                            if (response.body() != null) {
                                List<Movie> subMovies = response.body().getMovies();
                                moviesAll.addAll(subMovies);
                                allMoviesAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<MovieResponse> call, Throwable t) {

                        }
                    });
                    break;
                case movieNowPlaying:
                    moviesAll.clear();
                    ApiBuilder.getApi().getMoviesNowPlaying(mLang,i,API_KEY).enqueue(new Callback<MovieResponse>() {
                        @Override
                        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                            if (response.body() != null) {
                                List<Movie> subMovies = response.body().getMovies();
                                moviesAll.addAll(subMovies);
                                allMoviesAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<MovieResponse> call, Throwable t) {

                        }
                    });
                case moviePopular:
                    moviesAll.clear();
                    ApiBuilder.getApi().getMoviesPopular(mLang,i,API_KEY).enqueue(new Callback<MovieResponse>() {
                        @Override
                        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                            if (response.body() != null) {
                                List<Movie> subMovies = response.body().getMovies();
                                moviesAll.addAll(subMovies);
                                allMoviesAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<MovieResponse> call, Throwable t) {

                        }
                    });
                case movieTopRated:
                    moviesAll.clear();
                    ApiBuilder.getApi().getMoviesTopRated(mLang,i,API_KEY).enqueue(new Callback<MovieResponse>() {
                        @Override
                        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                            if (response.body() != null) {
                                List<Movie> subMovies = response.body().getMovies();
                                moviesAll.addAll(subMovies);
                                allMoviesAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<MovieResponse> call, Throwable t) {

                        }
                    });
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            allMoviesAdapter.setData(moviesAll);
            binding.recyclerMovieAll.setAdapter(allMoviesAdapter);
            binding.textTitle.setText(title);
            allMoviesAdapter.setListener(this);
        }

        @Override
        public void onMovieClick(Movie movie) {
            Intent intent = new Intent(getContext(), MovieDetailActivity.class);
            intent.putExtra(EXTRA_MOVIE_ID, movie.getId());
            startActivity(intent);
        }
    }
}
