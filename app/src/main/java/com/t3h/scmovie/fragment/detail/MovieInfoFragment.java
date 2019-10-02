package com.t3h.scmovie.fragment.detail;

import com.t3h.scmovie.R;
import com.t3h.scmovie.base.BaseFragment;
import com.t3h.scmovie.databinding.FragmentMovieInfoBinding;
import com.t3h.scmovie.model.Movie;

public class MovieInfoFragment extends BaseFragment<FragmentMovieInfoBinding> {

    public void setMovie(Movie movie) {
        if (movie != null && binding != null) {
            binding.movieTitle.setText(movie.getTitle());
            binding.textDuration.setText(movie.getDuration() + " Phút");
            binding.overViewContent.setText(movie.getOverView());
            binding.rateBar.setRating(movie.getVoteAverage());
            binding.releaseDate.setText(movie.getReleaseDate());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie_info;
    }

    @Override
    public String getTitle() {
        return "Movie Info";
    }
}
