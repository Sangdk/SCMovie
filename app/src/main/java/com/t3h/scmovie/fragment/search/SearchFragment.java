package com.t3h.scmovie.fragment.search;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.SearchView;

import androidx.annotation.Nullable;

import com.t3h.scmovie.R;
import com.t3h.scmovie.base.BaseAdapter;
import com.t3h.scmovie.base.BaseFragment;
import com.t3h.scmovie.databinding.FragmentSearchBinding;
import com.t3h.scmovie.model.Movie;
import com.t3h.scmovie.service.api.ApiBuilder;
import com.t3h.scmovie.service.response.MovieResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.t3h.scmovie.Const.API_KEY;

public class SearchFragment extends BaseFragment<FragmentSearchBinding> implements SearchView.OnQueryTextListener {
    private Dialog loadingDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public String getTitle() {
        return "Search Fragment";
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.searchView.setOnQueryTextListener(this);
        loadingDialog = new Dialog(getContext());
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setContentView(R.layout.dialog_loading);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        loadingDialog.show();
        ApiBuilder.getApi().searchMovies(API_KEY, s).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                setData(response);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
        return false;
    }

    private void setData(Response<MovieResponse> response) {
        BaseAdapter<Movie> adapter = new BaseAdapter<>(getContext(), R.layout.item_vertical_movie);
        adapter.setData(response.body().getMovies());
        binding.recyclerMoviesSearch.setAdapter(adapter);
        loadingDialog.dismiss();
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
