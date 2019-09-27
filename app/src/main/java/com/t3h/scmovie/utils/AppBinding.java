package com.t3h.scmovie.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import static com.t3h.scmovie.utils.Const.BASE_IMAGE_URL;

public class AppBinding {
    @BindingAdapter("thumb")
    public static void setThumb(ImageView im, String img) {
        Glide.with(im)
                .load(BASE_IMAGE_URL + img)
                .into(im);
    }
}
