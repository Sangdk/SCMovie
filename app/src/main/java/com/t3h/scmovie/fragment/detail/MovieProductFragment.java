package com.t3h.scmovie.fragment.detail;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.t3h.scmovie.R;
import com.t3h.scmovie.activity.detail.PeopleDetailActivity;
import com.t3h.scmovie.base.BaseAdapter;
import com.t3h.scmovie.base.BaseFragment;
import com.t3h.scmovie.databinding.FragmentMovieProductBinding;
import com.t3h.scmovie.fragment.home.PeopleItemClickListener;
import com.t3h.scmovie.model.Company;
import com.t3h.scmovie.model.Country;
import com.t3h.scmovie.model.Movie;
import com.t3h.scmovie.model.People;

import java.util.List;

import static com.t3h.scmovie.Const.EXTRA_PERSON_ID;

public class MovieProductFragment extends BaseFragment<FragmentMovieProductBinding> implements
        PeopleItemClickListener {

    public void setProduct(Movie movie) {
        if (movie != null && binding != null) {
            StringBuilder sbCountries = new StringBuilder();
            List<Country> countries = movie.getCountries();
            for (int i = 0; i < countries.size(); i++) {
                sbCountries.append(countries.get(i).getName()).append("\n");
            }
            StringBuilder sbCompanies = new StringBuilder();
            List<Company> companies = movie.getCompanies();
            for (int i = 0; i < companies.size(); i++) {
                sbCompanies.append(companies.get(i).getName()).append("\n");
            }
            binding.textCountry.setText(sbCountries.toString());
            binding.textCompanies.setText(sbCompanies.toString());
        }
    }

    public void setListCast(Context context, List<People> casts) {
        if (context != null && binding != null) {
            BaseAdapter<People> mCastAdapter = new BaseAdapter<>(context, R.layout.item_people_no_text);
            mCastAdapter.setData(casts);
            binding.recyclerCast.setAdapter(mCastAdapter);
            mCastAdapter.setListener(this);
        }
    }

    public void setListCrew(Context context, List<People> crews) {
        if (context != null && binding != null) {
            BaseAdapter<People> mCrewAdapter = new BaseAdapter<>(context, R.layout.item_people_no_text);
            Log.d("MovieProduct ", "" + crews.size());
            mCrewAdapter.setData(crews);
            binding.recyclerCrew.setAdapter(mCrewAdapter);
            mCrewAdapter.setListener(this);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie_product;
    }

    @Override
    public String getTitle() {
        return "Movie product";
    }

    @Override
    public void OnPeopleClick(People people) {
        Intent intent = new Intent(getContext(), PeopleDetailActivity.class);
        intent.putExtra(EXTRA_PERSON_ID, people.getId());
        startActivity(intent);
    }
}
