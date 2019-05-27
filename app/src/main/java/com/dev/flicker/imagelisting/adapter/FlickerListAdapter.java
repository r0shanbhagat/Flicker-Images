package com.dev.flicker.imagelisting.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.flicker.R;
import com.dev.flicker.beans.FlickerModel;
import com.dev.flicker.callback.OnItemClickListener;
import com.dev.flicker.databinding.ListItemImageBinding;

import java.util.List;

public class FlickerListAdapter extends RecyclerView.Adapter<FlickerListAdapter.MyViewHolder> {
    private List<FlickerModel> imageList;
    private OnItemClickListener<FlickerModel> itemClickListener;

    public FlickerListAdapter(@NonNull List<FlickerModel> imageList) {
        this.imageList = imageList;
    }

    public void setOnItemClickListener(OnItemClickListener<FlickerModel> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ListItemImageBinding itemBinding = DataBindingUtil
                .inflate(layoutInflater, R.layout.list_item_image, parent, false);
        return new MyViewHolder(itemBinding, itemClickListener);
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        return null != imageList ? imageList.size() : 0;
    }

    public List<FlickerModel> getItemList() {
        return imageList;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final ListItemImageBinding binding;
        private FlickerModel item;

        private MyViewHolder(ListItemImageBinding binding, final OnItemClickListener<FlickerModel> itemClickListener) {
            super(binding.getRoot());
            binding.executePendingBindings();
            this.binding = binding;
            binding.ivThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v, item);
                }
            });
        }

        private void bind(FlickerModel item) {
            this.item = item;
            binding.ivThumbnail.setImageBitmap(item.getThumbnail());
        }
    }
}

