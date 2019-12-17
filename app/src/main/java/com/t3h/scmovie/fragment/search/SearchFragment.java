package com.t3h.scmovie.fragment.search;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.t3h.scmovie.R;
import com.t3h.scmovie.activity.detail.MovieDetailActivity;
import com.t3h.scmovie.activity.detail.PeopleDetailActivity;
import com.t3h.scmovie.base.BaseAdapter;
import com.t3h.scmovie.base.BaseFragment;
import com.t3h.scmovie.databinding.FragmentSearchBinding;
import com.t3h.scmovie.fragment.home.MovieItemClickListener;
import com.t3h.scmovie.fragment.home.PeopleItemClickListener;
import com.t3h.scmovie.model.Movie;
import com.t3h.scmovie.model.People;
import com.t3h.scmovie.model.SearchBy;
import com.t3h.scmovie.service.api.ApiBuilder;
import com.t3h.scmovie.service.response.MovieResponse;
import com.t3h.scmovie.service.response.PeopleResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.t3h.scmovie.Const.API_KEY;
import static com.t3h.scmovie.Const.EXTRA_MOVIE_ID;
import static com.t3h.scmovie.Const.EXTRA_PERSON;
import static com.t3h.scmovie.Const.EXTRA_PERSON_ID;

public class SearchFragment extends BaseFragment<FragmentSearchBinding> implements
        SearchView.OnQueryTextListener, MovieItemClickListener, SearchByItemClickListener,
        PeopleItemClickListener {
    private Dialog loadingDialog;
    private int mCurrentPage = 1;
    private List<Movie> movies = new ArrayList<>();
    private List<People> persons = new ArrayList<>();
    private String mCurrentQuery;
    private List<SearchBy> searchByItems = new ArrayList<>();
    private String mSearchBy;
    private String mCurrentSearchBy;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public String getTitle() {
        return "Search Screen";
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.searchView.setOnQueryTextListener(this);
        loadingDialog = new Dialog(getContext());
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setContentView(R.layout.dialog_loading);
        addDataForSearchBy();
    }

    private void addDataForSearchBy() {
        searchByItems.add(new SearchBy(getResources().getString(R.string.famous_person)));
        searchByItems.add(new SearchBy(getResources().getString(R.string.movie_name)));
        searchByItems.add(new SearchBy(getResources().getString(R.string.key_word)));
        searchByItems.add(new SearchBy(getResources().getString(R.string.product_company)));
        searchByItems.add(new SearchBy(getResources().getString(R.string.collection)));

        BaseAdapter<SearchBy> adapter = new BaseAdapter<>(getContext(), R.layout.item_search_by);
        adapter.setData(searchByItems);
        binding.recyclerSearchBy.setAdapter(adapter);
        adapter.setListener(this);
        mSearchBy = "movie";
        mCurrentSearchBy = "movie";
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        String query = s.trim();
        if (mCurrentQuery != null && !mCurrentQuery.equals(query)) {
            movies.clear();
            persons.clear();
        } else if (mCurrentQuery != null && mCurrentQuery.equals(query) && mCurrentSearchBy.equals(mSearchBy)) {
            return false;
        }
        this.mCurrentQuery = query;
        loadingDialog.show();
        if (mSearchBy.equals("movie")) {
            ApiBuilder.getApi().searchMovies(API_KEY, query, 1).enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    setMoviesData(response, query);
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                }
            });
        } else if (mSearchBy.equals("person")) {
            ApiBuilder.getApi().searchPerson(API_KEY, query, 1).enqueue(new Callback<PeopleResponse>() {
                @Override
                public void onResponse(Call<PeopleResponse> call, Response<PeopleResponse> response) {
                    setPersonData(response, query);
                }

                @Override
                public void onFailure(Call<PeopleResponse> call, Throwable t) {

                }
            });
        }
        return false;
    }

    private void setPersonData(Response<PeopleResponse> response, String query) {
        BaseAdapter<People> adapter = new BaseAdapter<>(getContext(), R.layout.item_people);
        if (response.body() != null) {
            persons.addAll(response.body().getPeople());
        }
        adapter.setData(persons);
        binding.recyclerItemsSearch.setAdapter(adapter);
        adapter.setListener(this);
        int totalPages = response.body().getTotalPages();
        adapter.setOnBottomReachedListener(() -> {
            if (mCurrentPage < totalPages) {
                loadingDialog.show();
                mCurrentPage++;
                ApiBuilder.getApi().searchPerson(API_KEY, query, mCurrentPage).enqueue(new Callback<PeopleResponse>() {
                    @Override
                    public void onResponse(Call<PeopleResponse> call, Response<PeopleResponse> response) {
                        persons.addAll(response.body().getPeople());
                        adapter.notifyDataSetChanged();
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<PeopleResponse> call, Throwable t) {
                        loadingDialog.dismiss();
                    }
                });
            }
        });
        mCurrentSearchBy = "person";
        loadingDialog.dismiss();
    }

    private void setMoviesData(Response<MovieResponse> response, String query) {
        BaseAdapter<Movie> adapter = new BaseAdapter<>(getContext(), R.layout.item_vertical_movie);
        if (response.body() != null) {
            movies.addAll(response.body().getMovies());
        }
        adapter.setData(movies);
        binding.recyclerItemsSearch.setAdapter(adapter);
        adapter.setListener(this);
        int totalPages = response.body().getTotalPages();
        adapter.setOnBottomReachedListener(() -> {
            if (mCurrentPage < totalPages) {
                loadingDialog.show();
                mCurrentPage++;
                ApiBuilder.getApi().searchMovies(API_KEY, query, mCurrentPage).enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        movies.addAll(response.body().getMovies());
                        adapter.notifyDataSetChanged();
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        loadingDialog.dismiss();
                    }
                });
            }
        });
        mCurrentSearchBy = "movie";
        loadingDialog.dismiss();
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movie.getId());
        startActivity(intent);
    }

    @Override
    public void onSearchByItemClick(SearchBy item) {
        Toast.makeText(getContext(), item.getName(), Toast.LENGTH_SHORT).show();
        switch (item.getName()) {
            case "tên phim":
                mSearchBy = "movie";
                break;
            case "người nổi tiếng":
                mSearchBy = "person";
                break;
        }
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
    public void onDestroy() {
        super.onDestroy();
        searchByItems.clear();
    }
}
