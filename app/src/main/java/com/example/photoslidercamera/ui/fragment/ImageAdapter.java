package com.example.photoslidercamera.ui.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.photoslidercamera.adapter.BaseAdapter;
import com.example.photoslidercamera.databinding.ItemGalleryBinding;
import com.example.photoslidercamera.model.Image;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImageAdapter extends BaseAdapter<Image, ItemGalleryBinding> {
    
    private Context context;
    private Set<Integer> selectedPositions;
    private OnItemGalleryClicked onItemGalleryClicked;

    public ImageAdapter(){
        
    }

    public ArrayList<Image> getSelectedImages() {
        ArrayList<Image> selectedImages = new ArrayList<>();
        for (int position : selectedPositions) {
            if (position >= 0 && position < listItem.size()) {
                selectedImages.add(listItem.get(position));
            }
        }
        return selectedImages;
    }

    public ImageAdapter(Context context, OnItemGalleryClicked onItemGalleryClicked){
        this.context = context;
        this.selectedPositions = new HashSet<>();
        this.onItemGalleryClicked = onItemGalleryClicked;

    }

    @Override
    public ItemGalleryBinding createBinding(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return ItemGalleryBinding.inflate(inflater, parent, false);
    }

    private void toggleItemSelection(int position) {
        if (selectedPositions.contains(position)) {
            selectedPositions.remove(position);
        } else {
            selectedPositions.add(position);
        }
        notifyItemChanged(position);
    }

    public Set<Integer> getSelectedPositions() {
        return selectedPositions;
    }
    @Override
    public void bind(ItemGalleryBinding binding, Image item, int position) {
        Glide.with(context).load(item.getPathImage()).into(binding.image);
        if (selectedPositions.contains(position)) {
            binding.imgSelect.setVisibility(View.VISIBLE);
        } else {
            binding.imgSelect.setVisibility(View.GONE);
        }
        binding.image.setOnClickListener(v -> {
            toggleItemSelection(position);
            onItemGalleryClicked.onSizeSelected(selectedPositions.size());
        }
        );
    }
}

interface OnItemGalleryClicked{
    void onSizeSelected(int size);
}
