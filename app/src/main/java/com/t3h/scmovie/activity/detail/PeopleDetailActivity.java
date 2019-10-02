package com.t3h.scmovie.activity.detail;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.t3h.scmovie.Const;
import com.t3h.scmovie.R;
import com.t3h.scmovie.base.BaseActivity;
import com.t3h.scmovie.base.BaseAdapter;
import com.t3h.scmovie.databinding.ActivityPeopleDetailBinding;
import com.t3h.scmovie.model.Movie;
import com.t3h.scmovie.model.People;
import com.t3h.scmovie.service.api.ApiBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.t3h.scmovie.Const.API_KEY;
import static com.t3h.scmovie.Const.EXTRA_PERSON;
import static com.t3h.scmovie.Const.EXTRA_PERSON_ID;

public class PeopleDetailActivity extends BaseActivity<ActivityPeopleDetailBinding> {

    public void setPeople(People people, Context context, Intent intent) {
        if (people != null && binding != null && context != null) {
            binding.listMovieLabel.setVisibility(View.GONE);
            binding.recyclerKnowFor.setVisibility(View.GONE);
            String jsonMovies = intent.getStringExtra(EXTRA_PERSON);
            if (jsonMovies != null) {
                Gson gson = new Gson();
                List<Movie> movies = gson.fromJson(jsonMovies, new TypeToken<List<Movie>>() {
                }.getType());
                BaseAdapter<Movie> movieAdapter =
                        new BaseAdapter<>(context, R.layout.item_vertical_movie);
                movieAdapter.setData(movies);
                binding.recyclerKnowFor.setAdapter(movieAdapter);
                binding.listMovieLabel.setVisibility(View.VISIBLE);
                binding.recyclerKnowFor.setVisibility(View.VISIBLE);
            }
            binding.textName.setText(people.getName());
            binding.textBirthday.setText(people.getBirthday());
            binding.textPlaceBirth.setText(people.getPlaceOfBirth());
            binding.textDepartment.setText(people.getKnowForDepartment());
            String profilePatch = people.getProfilePatch();
            if (profilePatch != null) {
                Glide.with(binding.imageCast)
                        .load(Const.BASE_IMAGE_URL + profilePatch)
                        .into(binding.imageCast);
            }
        }
    }

    @Override
    protected void initAct() {
        initScreen();
    }

    private void initScreen() {
        Intent intent = getIntent();
        int personId = intent.getIntExtra(EXTRA_PERSON_ID, 0);
        ApiBuilder.getApi().getPeopleDetail(personId, API_KEY).enqueue(new Callback<People>() {
            @Override
            public void onResponse(Call<People> call, Response<People> response) {
                setPeople(response.body(), PeopleDetailActivity.this, intent);
            }

            @Override
            public void onFailure(Call<People> call, Throwable t) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_people_detail;
    }
}
