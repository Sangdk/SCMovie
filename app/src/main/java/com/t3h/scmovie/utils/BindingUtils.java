package com.t3h.scmovie.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.t3h.scmovie.adapter.MovieAdapter;
import com.t3h.scmovie.data.model.Movie;

import java.util.List;

public class BindingUtils {

    @BindingAdapter("bindImage")
    public static void bindImage(ImageView imageView, String url){
        Glide.with(imageView.getContext())
                .load(StringUtils.getImageUrl(url))
                .centerCrop()
//                .fallback()
//                .error()
                .into(imageView);
    }

    @BindingAdapter("bindCategoryMovies")
    public static void bindCategoryMovies(RecyclerView recyclerView, List<Movie> movies){
        MovieAdapter adapter = (MovieAdapter) recyclerView.getAdapter();
        if (adapter != null){
            adapter.setData(movies);
        }
    }
}
