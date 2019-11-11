package com.t3h.scmovie.fragment.home;

import com.t3h.scmovie.base.BaseAdapter;
import com.t3h.scmovie.model.Movie;

public interface MovieItemClickListener extends BaseAdapter.BaseItemListener {
    void onMovieClick(Movie movie);
}
