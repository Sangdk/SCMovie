package com.t3h.scmovie.adapter;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class CustomScroller extends Scroller {

    public CustomScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }
}