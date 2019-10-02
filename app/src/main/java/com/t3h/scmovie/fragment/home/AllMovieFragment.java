package com.t3h.scmovie.fragment.home;

import android.content.Context;
import android.os.AsyncTask;

import com.t3h.scmovie.R;
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

    public void setData(String title, Context context,int total) {
        GETALLASYNC aaa = new GETALLASYNC();
        aaa.setContext(context);
        aaa.setTitle(title);
        aaa.setTotal(total);
        aaa.execute();
    }

    private class GETALLASYNC extends AsyncTask<Void, Void, Void>{
        String title;
        int total;
        Context context;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            setData(title,total,context);
            return null;
        }
        public void setData(String title, int totalPages, Context context) {
            String mLang = "vi";
            int x = totalPages;
            for (int i = 1; i <= totalPages; i++) {
                addSubMovies(i, mLang);
            }
        }

        private void addSubMovies(int i, String mLang) {
            ApiBuilder.getApi().getMoviesUpComing(mLang, i, API_KEY).enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    List<Movie> subMovies = response.body().getMovies();
                    moviesAll.addAll(subMovies);
                    int i = moviesAll.size();
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                }
            });
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            BaseAdapter allMoviesAdapter = new BaseAdapter(context, R.layout.item_vertical_movie);
            allMoviesAdapter.setData(moviesAll);
            binding.recyclerMovieAll.setAdapter(allMoviesAdapter);
            binding.textTitle.setText(title);
        }
    }
}
