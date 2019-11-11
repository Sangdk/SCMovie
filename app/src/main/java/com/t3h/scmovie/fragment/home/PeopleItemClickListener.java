package com.t3h.scmovie.fragment.home;

import com.t3h.scmovie.base.BaseAdapter;
import com.t3h.scmovie.model.People;

public interface PeopleItemClickListener extends BaseAdapter.BaseItemListener {
    void OnPeopleClick(People people);
}
