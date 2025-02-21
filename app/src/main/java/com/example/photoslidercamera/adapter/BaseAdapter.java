package com.example.photoslidercamera.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, VB extends ViewBinding> extends RecyclerView.Adapter<BaseAdapter<T, VB>.ViewHolder> {
    protected final ArrayList<T> listItem = new ArrayList<>();
    private VB binding;
    public int itemSelectedPosition = RecyclerView.NO_POSITION;

    public abstract VB createBinding(LayoutInflater inflater, ViewGroup parent, int viewType);

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = createBinding(inflater, parent, viewType);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        T item = listItem.get(position);
        bind(holder.binding, item, position);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public abstract void bind(VB binding, T item, int position);

    public void setItems(List<T> items) {
        this.listItem.clear();
        this.listItem.addAll(items);
        notifyDataSetChanged();
    }

    public void addItems(List<T> items) {
        this.listItem.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(T item, int index) {
        this.listItem.add(index, item);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final VB binding;

        public ViewHolder(VB binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
