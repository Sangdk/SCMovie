package com.t3h.scmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.t3h.scmovie.R;
import com.t3h.scmovie.databinding.ItemTvBinding;
import com.t3h.scmovie.model.tv.TV;

import java.util.List;

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context mContext;
    private List<TV> mList;

    public TVAdapter(Context mContext) {
        inflater = LayoutInflater.from(mContext);
    }

    public void setmList(List<TV> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTvBinding binding = DataBindingUtil
                .inflate(inflater,R.layout.item_tv,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TV mTV  = mList.get(position);
        holder.bindData(mTV);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ItemTvBinding binding;
        public ViewHolder(@NonNull ItemTvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindData(TV item){
            binding.tvNameTv.setText(item.getName());
        }

    }
}
