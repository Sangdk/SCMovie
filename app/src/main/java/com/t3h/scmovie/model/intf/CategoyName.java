package com.t3h.scmovie.data.model.intf;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({CategoyName.TITLE_NOW_PLAYING,CategoyName.TITLE_POPULAR,
            CategoyName.TITLE_TOP_RATE, CategoyName.TITLE_UP_COMING})
public @interface CategoyName {
    String TITLE_NOW_PLAYING = "Now Playing";
    String TITLE_UP_COMING = "Up Coming";
    String TITLE_TOP_RATE = "Top Rate";
    String TITLE_POPULAR = "Popular";
}
