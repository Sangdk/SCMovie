package com.t3h.scmovie.fragment.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

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
    private Dialog mLoadingDialog;
    private OnBackPress mCallback;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie_all;
    }

    @Override
    public String getTitle() {
        return "All Movie";
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallback = (OnBackPress) context;
    }

    public void setData(String title, Context context, int total, Dialog mLoadingDialog) {
        this.mLoadingDialog = mLoadingDialog;
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
        private int currentPages = 1;

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
            addSubMovies(currentPages, mLang, title);
            allMoviesAdapter.setOnBottomReachedListener(() -> {
                if (currentPages < totalPages) {
                    currentPages++;
                    addSubMovies(currentPages, mLang, title);
                } else {
                    Toast.makeText(getContext(),
                            "Load hết " + totalPages * 20 + " phim",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void addSubMovies(int i, String mLang, String title) {
            final String movieUpComing = "Phim sắp chiếu";
            final String movieNowPlaying = "Phim đang chiếu";
            final String moviePopular = "Nhiều người xem";
            final String movieTopRated = "Phim nổi bật";
            switch (title) {
                case movieUpComing:
                    ApiBuilder.getApi().getMoviesUpComing(i, API_KEY).enqueue(new Callback<MovieResponse>() {
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
                    ApiBuilder.getApi().getMoviesNowPlaying(i, API_KEY).enqueue(new Callback<MovieResponse>() {
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
                case moviePopular:
                    ApiBuilder.getApi().getMoviesPopular(mLang, i, API_KEY).enqueue(new Callback<MovieResponse>() {
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
                case movieTopRated:
                    ApiBuilder.getApi().getMoviesTopRated(mLang, i, API_KEY).enqueue(new Callback<MovieResponse>() {
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
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            allMoviesAdapter.setData(moviesAll);
            binding.recyclerMovieAll.setAdapter(allMoviesAdapter);
            binding.textTitle.setText(title);
            allMoviesAdapter.setListener(this);
            mLoadingDialog.dismiss();
        }

        @Override
        public void onMovieClick(Movie movie) {
            Intent intent = new Intent(getContext(), MovieDetailActivity.class);
            intent.putExtra(EXTRA_MOVIE_ID, movie.getId());
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        moviesAll.clear();
        mCallback.onAllMovieFragmentBackPress();
        Log.d("AllMovieFrag", "on destroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public interface OnBackPress {
        void onAllMovieFragmentBackPress();
    }
}
