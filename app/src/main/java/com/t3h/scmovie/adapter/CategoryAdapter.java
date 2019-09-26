package com.t3h.scmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.t3h.scmovie.R;
import com.t3h.scmovie.data.model.Genre;
import com.t3h.scmovie.data.model.Movie;
import com.t3h.scmovie.databinding.ItemCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<String> mCategories;
    private List<List<Movie>> mMovies;
    private CategoryListener mListener;

    public CategoryAdapter(Context mContext, CategoryListener listener) {
        inflater = LayoutInflater.from(mContext);
        mCategories = new ArrayList<>();
        mMovies = new ArrayList<>();
        mListener = listener;
    }

    public void setData(List<List<Movie>> data, List<String> mCategories) {
        mMovies = data;
        this.mCategories = mCategories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mMovies.get(position),mCategories.get(position));
    }

    @Override
    public int getItemCount() {
        return mMovies == null ? 0 : mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements MovieAdapter.MovieListener, View.OnClickListener {
        private ItemCategoryBinding binding;
        private CategoryListener mListener;
        public ViewHolder(@NonNull ItemCategoryBinding binding, CategoryListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            mListener = listener;
            binding.recyclerMovie.setAdapter(new MovieAdapter(binding.getRoot().getContext(),this));
            binding.recyclerMovie.setNestedScrollingEnabled(false);
            binding.textMore.setOnClickListener(this);


        }

        public void bindData(List<Movie> movies, String category){
            binding.textCategoryName.setText(category);
            binding.setItemCategoryMovies(movies);
        }

        @Override
        public void onItemClickListener(Movie movie) {
            mListener.onMovieClickListener(movie);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null){
                mListener.onLoadMoreClickListener(binding.textCategoryName.getText().toString());
            }
        }
    }

    public interface CategoryListener{
        void onMovieClickListener(Movie movie);
        void onLoadMoreClickListener(String category);
    }
}
