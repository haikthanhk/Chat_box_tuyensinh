package com.example.photoslidercamera.ui.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.photoslidercamera.databinding.FragmentSettingBinding;

public class SettingFragment extends  BaseFragment<FragmentSettingBinding> {
    @Override
    void handlerBackpress() {
        closeFragment(this);
    }

    @Override
    FragmentSettingBinding getBinding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentSettingBinding.inflate(inflater, container, false);
    }

    @Override
    void initView() {

        binding.imgBack.setOnClickListener(v -> {
            handlerBackpress();
        });
    }
}
