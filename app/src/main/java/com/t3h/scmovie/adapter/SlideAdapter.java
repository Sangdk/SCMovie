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
import com.t3h.scmovie.data.model.Movie;
import com.t3h.scmovie.databinding.ItemSlideBinding;

import java.util.ArrayList;
import java.util.List;

public class SlideAdapter extends PagerAdapter {
    private List<Movie> mMovies = new ArrayList<>();
    private LayoutInflater inflater;
    private ItemSlideBinding mBinding;

    public SlideAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setMovies(List<Movie> mMovies) {
        mMovies.clear();
        mMovies.addAll(mMovies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_slide, container, false);
        mBinding.imageSlide.setOnClickListener(view -> {
            if (listener != null) {
                listener.onClickSlide(mMovies.get(position));
            }
        });
        bindData(mMovies.get(position));
        container.addView(mBinding.getRoot());
        return mBinding.getRoot();
    }

    private void bindData(Movie movie) {
        Glide.with(mBinding.imageSlide)
                .load(Const.BASE_IMAGE_URL + movie.getmPosterPath())
                .into(mBinding.imageSlide);
        mBinding.textTitle.setText(movie.getmTitle());
        mBinding.textReleaseDate.setText(movie.getmReleaseDate());
    }

    @Override
    public int getCount() {
        return mMovies == null ? 0 : mMovies.size();
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

<<<<<<< Updated upstream
    @Override
    public void onClick(View view) {

=======
    public void setListener(OnClickSlideListener listener) {
        this.listener = listener;
    }

    public interface OnClickSlideListener {
        void onClickSlide(Movie movie);
>>>>>>> Stashed changes
    }
}
