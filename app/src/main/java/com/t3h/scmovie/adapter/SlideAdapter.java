package com.t3h.scmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.t3h.scmovie.Const;
import com.t3h.scmovie.R;
import com.t3h.scmovie.databinding.ItemSlideTvBinding;
import com.t3h.scmovie.model.Movie;
import com.t3h.scmovie.databinding.ItemSlideBinding;
import com.t3h.scmovie.model.TV;

import java.util.ArrayList;
import java.util.List;

public class SlideAdapter extends PagerAdapter {
    private List<Movie> mMovies = new ArrayList<>();
    private List<TV> mTVs = new ArrayList<>();
    private LayoutInflater inflater;
    private ItemSlideBinding mBinding;
    private ItemSlideTvBinding mBindingTv;
    private OnClickSlideListener listener;
    private String type;

    public SlideAdapter(Context context, String type) {
        inflater = LayoutInflater.from(context);
        this.type = type;
    }

    public void setMovies(List<Movie> mMovies) {
        this.mMovies = mMovies;
        notifyDataSetChanged();
    }

    public void setTVs(List<TV> mTVs) {
        this.mTVs = mTVs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (type.equals("movie")) {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.item_slide, container, false);
            mBinding.imageSlide.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onClickSlide(mMovies.get(position));
                }
            });
            container.addView(mBinding.getRoot());
            bindData(mMovies.get(position));
            return mBinding.getRoot();
        }else {
            mBindingTv = DataBindingUtil.inflate(inflater, R.layout.item_slide_tv, container, false);
            container.addView(mBindingTv.getRoot());
            bindDataTV(mTVs.get(position));
            return mBindingTv.getRoot();
        }
    }

    private void bindDataTV(TV tv) {
        Glide.with(mBindingTv.imageSlide)
                .load(Const.BASE_IMAGE_URL + tv.getPosterPatch())
                .into(mBindingTv.imageSlide);
        mBindingTv.textTitle.setText(tv.getName());
    }

    private void bindData(Movie movie) {
        Glide.with(mBinding.imageSlide)
                .load(Const.BASE_IMAGE_URL + movie.getBackdropPath())
                .into(mBinding.imageSlide);
        mBinding.textTitle.setText(movie.getTitle());
        mBinding.textReleaseDate.setText(movie.getReleaseDate());
    }

    @Override
    public int getCount() {
        if (type.equals("movie")) {
            return mMovies == null ? 0 : mMovies.size();
        } else {
            return mTVs == null ? 0 : mTVs.size();
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View v = (View) object;
        container.removeView(v);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public void setListener(OnClickSlideListener listener) {
        this.listener = listener;
    }

    public interface OnClickSlideListener {
        void onClickSlide(Movie movie);
    }
}
