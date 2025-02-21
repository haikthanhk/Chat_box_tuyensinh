package com.example.photoslidercamera.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.photoslidercamera.databinding.FragmentSliderBinding;
import com.example.photoslidercamera.model.Image;
import com.example.photoslidercamera.ui.view.CustomViewPagerAdapter;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SliderFragment extends BaseFragment<FragmentSliderBinding> {

    private static String KEY_IMAGE_LIST = "KEY_IMAGE_LIST";
    private Timer timer = null;
    private Handler handlerNextPage = new Handler(Looper.getMainLooper());

    public static SliderFragment instance(ArrayList<Image> imageArrayList) {
        SliderFragment sliderFragment = new SliderFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_IMAGE_LIST, imageArrayList);
        sliderFragment.setArguments(bundle);
        return sliderFragment;
    }

    private final ArrayList<BaseFragment<?>> fragmentList = new ArrayList<>();

    @Override
    void handlerBackpress() {
        closeFragment(this);
    }

    @Override
    FragmentSliderBinding getBinding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentSliderBinding.inflate(inflater, container, false);
    }

    @Override
    void initView() {

        binding.imgBack.setOnClickListener(v -> closeFragment(this));
        ArrayList<Image> images;
        Bundle bundle = getArguments();
        if (bundle != null) {
            images = bundle.getParcelableArrayList(KEY_IMAGE_LIST);
            if (images != null) {
                for (Image image : images) {
                    Log.d("TAG", "initView: " + image.getPathImage());
                    fragmentList.add(FragmentImageSlider.instance(image.getPathImage()));
                }
                addControlPageContent();
                binding.viewPager.setOffscreenPageLimit(images.size());

            }
        }
    }

    private void addControlPageContent() {
        CustomViewPagerAdapter customViewPagerAdapter = new CustomViewPagerAdapter(getChildFragmentManager(), fragmentList, requireContext());

        binding.viewPager.setAdapter(customViewPagerAdapter);
        binding.dotIndicator.attachTo(binding.viewPager);
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handlerNextPage.post(new Runnable() {
                        @Override
                        public void run() {

                            int currentPage = binding.viewPager.getCurrentItem();
                            int count = fragmentList.size() - 1;
                            if (!binding.viewPager.isTouch()) {
                                if (currentPage < count) {
                                    currentPage += 1;
                                    binding.viewPager.setCurrentItem(currentPage, true);
                                } else {
                                    binding.viewPager.setCurrentItem(0, true);
                                }
                            }else {
                                binding.viewPager.setCurrentItem(currentPage);
                            }
                        }
                    });
                }
            }, 2000L, 2000L);
        }
        binding.viewPager.setPagingEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        timer.cancel();
        timer = null;
        handlerNextPage.removeCallbacksAndMessages(null);
    }
}
