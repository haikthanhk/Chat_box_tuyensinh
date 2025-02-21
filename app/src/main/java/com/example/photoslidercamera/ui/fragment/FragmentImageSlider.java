package com.example.photoslidercamera.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.photoslidercamera.databinding.FragmentImageSliderBinding;

public class FragmentImageSlider extends BaseFragment<FragmentImageSliderBinding> {

    private static final String IMAGE_PATH = "IMAGE_PATH";
    private static final String POSITION = "POSITION";

    private String pathImage;
    public static FragmentImageSlider instance(String pathImage) {
        FragmentImageSlider fragmentImageSlider = new FragmentImageSlider();
        Bundle bundle = new Bundle();
        bundle.putString(IMAGE_PATH, pathImage);
        fragmentImageSlider.setArguments(bundle);
        return fragmentImageSlider;

    }

    @Override
    void handlerBackpress() {
    }

    @Override
    FragmentImageSliderBinding getBinding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentImageSliderBinding.inflate(inflater, container, false);
    }

    @Override
    void initView() {
        pathImage = requireArguments().getString(IMAGE_PATH);

        Glide.with(requireContext()).load(pathImage).diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.image);
    }
}
