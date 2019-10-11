package com.t3h.scmovie.fragment.detail;

import android.content.Context;

import com.t3h.scmovie.R;
import com.t3h.scmovie.base.BaseAdapter;
import com.t3h.scmovie.base.BaseFragment;
import com.t3h.scmovie.databinding.FragmentTrailerBinding;
import com.t3h.scmovie.model.Video;

import java.util.List;

public class TrailerFragment extends BaseFragment<FragmentTrailerBinding> implements
        TrailerItemClickListener{
    private BaseAdapter<Video> mVideoAdapter;
    private YoutubeFragment mYoutubeFragment;

    public void setListTrailer(Context context, List<Video> mListTrailer, YoutubeFragment youtubeFragment) {
        if (context != null && binding != null) {
            mVideoAdapter = new BaseAdapter<>(context, R.layout.item_trailer);
            mVideoAdapter.setData(mListTrailer);
            binding.recyclerTrailer.setAdapter(mVideoAdapter);
            mYoutubeFragment = youtubeFragment;
            mVideoAdapter.setListener(this);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trailer;
    }

    @Override
    public String getTitle() {
        return "Trailer Fragment";
    }

    @Override
    public void OnTrailerItemClick(Video video) {
        mYoutubeFragment.setTrailerId(video.getKey());
    }
}
