package com.t3h.scmovie.fragment.search;

import com.t3h.scmovie.base.BaseAdapter;
import com.t3h.scmovie.model.SearchBy;

public interface SearchByItemClickListener extends BaseAdapter.BaseItemListener {
    void onSearchByItemClick(SearchBy item);
}
