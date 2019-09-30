package com.t3h.scmovie;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import static com.t3h.scmovie.Const.BASE_IMAGE_URL;
import static com.t3h.scmovie.Const.BASE_THUMBNAIL_PATH;

public class AppBinding {
    @BindingAdapter("thumb")
    public static void setThumb(ImageView im, String img) {
        Glide.with(im)
                .load(BASE_IMAGE_URL + img)
                .into(im);
    }

    @BindingAdapter("thumbTrailer")
    public static void setThubTrailer(ImageView im, String key) {
        Glide.with(im)
                .load(String.format(BASE_THUMBNAIL_PATH, key))
                .into(im);
    }
}
