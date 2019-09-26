package com.t3h.scmovie.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.t3h.scmovie.R;
import com.t3h.scmovie.adapter.CategoryAdapter;
import com.t3h.scmovie.base.BaseAdapter;
import com.t3h.scmovie.base.BaseFragment;
import com.t3h.scmovie.data.model.Genre;
import com.t3h.scmovie.data.model.Movie;
import com.t3h.scmovie.data.model.intf.CategoyName;
import com.t3h.scmovie.databinding.FragmentHomeBinding;
import com.t3h.scmovie.service.api.ApiBuilder;
import com.t3h.scmovie.service.response.GenreResponse;
import com.t3h.scmovie.service.response.MovieResponse;
import com.t3h.scmovie.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> implements CategoryAdapter.CategoryListener {
    private String mLang = "vi";
    private final String apiKey = "f565c6353149e1b97eb7f993217dafac";
    private List<Movie> data = new ArrayList<>();
    private BaseAdapter<Movie> adapter;
    private List<Genre> mGenres = new ArrayList<>();
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;

//    private List<List<Movie>> categoryMovies = new ArrayList<>();
//    private List<String> categoryTitle = new ArrayList<>();
//
//    private List<Movie> nowPlayingMovies = new ArrayList<>();
//    private List<Movie> upComingMovies = new ArrayList<>();
//    private List<Movie> popularMovies = new ArrayList<>();
//    private List<Movie> topRateMovies = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding = DataBindingUtil.setContentView(getActivity(),R.layout.fragment_home);
        binding.recyclerViewCategory.setAdapter(new CategoryAdapter(getContext(),this));
        binding.recyclerViewCategory.setNestedScrollingEnabled(false);

        homeViewModel.initHomeView();

//        adapter = new BaseAdapter<>(getContext(), R.layout.item_vertical_movie);
//        ApiBuilder.getApi().getMoviesNowPlaying(mLang,1,apiKey).enqueue(new Callback<MovieResponse>() {
//            @Override
//            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
//                data = response.body().getMovies();
//                adapter.setData(response.body().getMovies());
//                Log.e("AAA", data.size() + "a");
//                int i = data.size();
//            }
//
//            @Override
//            public void onFailure(Call<MovieResponse> call, Throwable t) {
//
//            }
//        });
//        binding.recyclerFamousActor.setAdapter(adapter);

//        categoryAdapter = new CategoryAdapter(getContext());
//        ApiBuilder.getApi().getGenres(apiKey,mLang).enqueue(new Callback<GenreResponse>() {
//            @Override
//            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
//                categoryAdapter.setData(response.body().getGenres());
//                mGenres = response.body().getGenres();
//                int a = mGenres.size();
//                Log.e("aaaa", "onResponse: " + mGenres.size() );
//            }
//
//            @Override
//            public void onFailure(Call<GenreResponse> call, Throwable t) {
//
//            }
//        });
//        binding.recyclerViewCategory.setAdapter(categoryAdapter);



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
    public void onMovieClickListener(Movie movie) {

    }

    @Override
    public void onLoadMoreClickListener(String category) {

    }
}
