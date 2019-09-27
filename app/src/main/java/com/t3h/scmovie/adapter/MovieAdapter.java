package com.t3h.scmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.t3h.scmovie.data.model.Movie;
import com.t3h.scmovie.databinding.ItemVerticalMovieBinding;
import com.t3h.scmovie.viewmodel.ItemMovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context mContext;
    private List<Movie> data;
    private LayoutInflater inflater;
    private MovieListener listener;

    public MovieAdapter(Context mContext, MovieListener listener) {
        data = new ArrayList<>();
        inflater = LayoutInflater.from(mContext);
        this.listener = listener;
    }

    public void setData(List<Movie> mMovies){
        data.clear();
        data.addAll(mMovies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVerticalMovieBinding binding = ItemVerticalMovieBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemVerticalMovieBinding binding;
        private MovieListener listener;

        public ViewHolder(@NonNull ItemVerticalMovieBinding binding, MovieListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        public void bindData(Movie movie){
            binding.setViewmodel(new ItemMovieViewModel());
            if (binding.getViewmodel() != null){
                binding.getViewmodel().setMovie(movie);
            }
            binding.cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (binding.getViewmodel() != null){
                listener.onItemClickListener(binding.getViewmodel().getMovie());
            }
        }
    }

    public interface MovieListener{
        void onItemClickListener(Movie movie);
    }
}
