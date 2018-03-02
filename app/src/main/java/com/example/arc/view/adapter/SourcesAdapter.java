package com.example.arc.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arc.BR;
import com.example.arc.R;
import com.example.arc.model.data.Source;

import java.util.ArrayList;

/**
 * @author ihsan on 12/18/17.
 */

public class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.ViewHolder> {

    private ArrayList<Source> data;
    private ItemSelectedListener listener;

    public SourcesAdapter() {
        this.data = new ArrayList<>();
    }

    public void setData(ArrayList<Source> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public SourcesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_source_view, parent, false);
        return new ViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(SourcesAdapter.ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ItemSelectedListener {
        void onItemSelected(Source item);
    }

    public void setItemSelectedListener(ItemSelectedListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemSelectedListener listener;
        private final ViewDataBinding binding;

        ViewHolder(ViewDataBinding binding, ItemSelectedListener listener) {
            super(binding.getRoot());
            binding.getRoot().findViewById(R.id.button).setOnClickListener(this);
            this.binding = binding;
            this.listener = listener;
        }

        void bind(Source source) {
            binding.setVariable(BR.source, source);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                Source item = data.get(getAdapterPosition());
                item.setSelected(!item.isSelected());
                notifyItemChanged(getAdapterPosition());
                listener.onItemSelected(item);
            }
        }
    }
}
