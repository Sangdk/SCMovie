package com.t3h.scmovie.activity.detail;

import android.content.Intent;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.t3h.scmovie.Const;
import com.t3h.scmovie.R;
import com.t3h.scmovie.base.BaseActivity;
import com.t3h.scmovie.databinding.ActivityPeopleDetailBinding;
import com.t3h.scmovie.model.People;
import com.t3h.scmovie.service.api.ApiBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.t3h.scmovie.Const.API_KEY;
import static com.t3h.scmovie.Const.EXTRA_PERSON_ID;

public class PeopleDetailActivity extends BaseActivity<ActivityPeopleDetailBinding> {

    public void setPeople(People people) {
        if (people != null && binding != null) {
            binding.textName.setText(people.getName());
            binding.textBirthday.setText(people.getBirthday());
            binding.textPlaceBirth.setText(people.getPlaceOfBirth());
            binding.textDepartment.setText(people.getKnowForDepartment());
            Glide.with(binding.imageCast)
                    .load(Const.BASE_IMAGE_URL + people.getProfilePatch())
                    .into(binding.imageCast);
        }
    }

    @Override
    protected void initAct() {
        initScreen();
    }

    private void initScreen() {
        Intent intent = getIntent();
        int personId = intent.getIntExtra(EXTRA_PERSON_ID, 0);
        Log.d("PeopleDetail", "" + personId);
        ApiBuilder.getApi().getPeopleDetail(personId, API_KEY).enqueue(new Callback<People>() {
            @Override
            public void onResponse(Call<People> call, Response<People> response) {
                setPeople(response.body());
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
