package com.t3h.scmovie.fragment.detail;

import com.t3h.scmovie.base.BaseAdapter;
import com.t3h.scmovie.model.Video;

public interface TrailerItemClickListener extends BaseAdapter.BaseItemListener {
    void OnTrailerItemClick(Video video);
}
