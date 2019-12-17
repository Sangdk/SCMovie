package com.t3h.scmovie.fragment.home;

import android.os.Bundle;
import android.os.Handler;
import android.transition.Slide;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.t3h.scmovie.R;
import com.t3h.scmovie.adapter.SlideAdapter;
import com.t3h.scmovie.base.BaseFragment;
import com.t3h.scmovie.databinding.FragmentTvBinding;
import com.t3h.scmovie.model.TV;
import com.t3h.scmovie.service.api.ApiBuilder;
import com.t3h.scmovie.service.response.TVResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.t3h.scmovie.Const.API_KEY;


public class TVFragment extends BaseFragment<FragmentTvBinding> {
    private static final long PERIOD_TIME_SLIDE = 4000;
    private static final long DELAY_TIME_SLIDE = 2000;
    private int mCurrentSlide = 0;
    private final Timer t = new Timer();
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tv;
    }

    @Override
    public String getTitle() {
        return "Fragment TV";
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ApiBuilder.getApi().getTVAiringToday(API_KEY, "vi", 1).enqueue(new Callback<TVResponse>() {
            @Override
            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                initSlide(response.body().getListTV());
            }

            @Override
            public void onFailure(Call<TVResponse> call, Throwable t) {
                Log.d(TVFragment.class.getSimpleName(), "response on failure");
            }
        });
    }

    private void initSlide(List<TV> listTV) {
        SlideAdapter mSlideAdapter = new SlideAdapter(getContext(),"tv");
        List<TV> dataSlide = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            dataSlide.add(listTV.get(i));
        }
        mSlideAdapter.setTVs(dataSlide);
        binding.viewPager.setAdapter(mSlideAdapter);
        initSlideTimer();
        binding.viewPager.setPadding(100, 40, 100, 20);
        binding.viewPager.setClipToPadding(false);
        binding.viewPager.setPageMargin(60);
        initToolbar();
    }

    private void initToolbar() {
        binding.appBarLayout.addOnOffsetChangedListener(
                (appBarLayout, verticalOffset) -> {
                    if (Math.abs(verticalOffset) > 800) {
                        binding.collapsingToolbar.setTitleEnabled(true);
                        binding.collapsingToolbar.setTitle("TV");
                        binding.viewPager.setVisibility(View.GONE);
                    } else {
                        binding.collapsingToolbar.setTitleEnabled(false);
                        binding.viewPager.setVisibility(View.VISIBLE);
                    }
                }
        );
    }

    private void initSlideTimer() {
        final Handler handler = new Handler();
        final Runnable update = () -> {
            if (mCurrentSlide == 9) {
                mCurrentSlide = 0;
            }
            binding.viewPager.setCurrentItem(mCurrentSlide++, true);
        };
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if (binding.viewPager.getAdapter() != null) {
                    handler.post(update);
                }
            }
        }, DELAY_TIME_SLIDE, PERIOD_TIME_SLIDE);
    }


}
