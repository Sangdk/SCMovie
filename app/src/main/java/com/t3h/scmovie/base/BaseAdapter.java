package com.t3h.scmovie.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.t3h.scmovie.BR;
import com.t3h.scmovie.databinding.ItemPeopleBinding;
import com.t3h.scmovie.databinding.ItemVerticalMovieBinding;

import java.util.List;

public class BaseAdapter<M extends BaseModel> extends RecyclerView.Adapter<BaseAdapter.BaseHolder> {
    private LayoutInflater inflater;
    private int layoutId;
    private List<M> data;
    private BaseItemListener listener;
    private OnBottomReachedListener onBottomReachedListener;

    public BaseAdapter(Context context, @LayoutRes int layoutId) {
        inflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener) {
        this.onBottomReachedListener = onBottomReachedListener;
    }

    public void setData(List<M> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setListener(BaseItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
        return new BaseHolder(binding);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseAdapter.BaseHolder holder, int position) {
        M item = data.get(position);
        holder.binding.setVariable(BR.item, item);
        holder.binding.setVariable(BR.listener, listener);
        holder.binding.executePendingBindings();
        if (position == data.size() - 1 && onBottomReachedListener != null) {
            onBottomReachedListener.onBottomReached();
        }
    }

    public class BaseHolder extends RecyclerView.ViewHolder {
        ViewDataBinding binding;

        @SuppressLint("ResourceAsColor")
        public BaseHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            if (binding instanceof ItemVerticalMovieBinding) {
                ((ItemVerticalMovieBinding) binding).textTitle.setSelected(true);
            } else if (binding instanceof ItemPeopleBinding) {
                ((ItemPeopleBinding) binding).textActor.setSelected(true);
            }
        }
    }

    public interface BaseItemListener {
    }

    public interface OnBottomReachedListener {
        void onBottomReached();
    }
}
