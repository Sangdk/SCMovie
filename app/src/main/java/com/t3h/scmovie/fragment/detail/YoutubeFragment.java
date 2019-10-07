package com.t3h.scmovie.fragment.detail;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import static com.t3h.scmovie.utils.Const.YOUTUBE_API_KEY;

public class YoutubeFragment extends YouTubePlayerFragment implements YouTubePlayer.OnInitializedListener {
    private YouTubePlayer mYoutubePlayer;
    private String mTrailerId;
    private int mCurrentPosition;
    private final String mCurrentIdKey = "trailerId";
    private final String mCurrentPositionKey = "trailerPosition";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initialize(YOUTUBE_API_KEY, this);
        if (bundle != null) {
            mTrailerId = bundle.getString(mCurrentIdKey);
            int position = bundle.getInt(mCurrentPositionKey);
            playTrailer(position);
            this.mCurrentPosition = position;
        }
        setTrailerId(mTrailerId);
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean restored) {
        mYoutubePlayer = youTubePlayer;
        if (!restored && mTrailerId != null) {
            mYoutubePlayer.setFullscreenControlFlags(
                    YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);
            mYoutubePlayer.cueVideo(mTrailerId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        mYoutubePlayer = null;
    }

    @Override
    public void onDestroy() {
        if (mYoutubePlayer != null) mYoutubePlayer.release();
        super.onDestroy();
    }

    public void setTrailerId(String mTrailerId) {
        this.mTrailerId = mTrailerId;
        if (mTrailerId != null && mYoutubePlayer != null) {
            mYoutubePlayer.cueVideo(mTrailerId);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // Do something here if you need to
            }
        }
    }

    public void playTrailer(int position) {
        if (mYoutubePlayer != null) {
            mYoutubePlayer.seekToMillis(position);
            mYoutubePlayer.play();
        }
    }

    public void setFullScreen(boolean isFullScreen) {
        mYoutubePlayer.setFullscreen(isFullScreen);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (mTrailerId != null && mYoutubePlayer != null) {
            bundle.putString(mCurrentIdKey, mTrailerId);
            bundle.putInt(mCurrentPositionKey, mYoutubePlayer.getCurrentTimeMillis());
        }
    }
}
