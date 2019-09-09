package base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BaseAdapter<M extends BaseModel> extends RecyclerView.Adapter<BaseAdapter.BaseHolder> {
    private LayoutInflater inflater;
    private int layoutId;
    private List<M> data;
    private BaseitemListener listener;
    public BaseAdapter(Context context, @LayoutRes int layoutId) {
        inflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
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
//        holder.binding.setVariable(BR.item,item);
//        holder.binding.setVariable(BR.listener, listener);
        holder.binding.executePendingBindings();
    }

    public class BaseHolder extends RecyclerView.ViewHolder {
        ViewDataBinding binding;

        public BaseHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface BaseitemListener { }
}
