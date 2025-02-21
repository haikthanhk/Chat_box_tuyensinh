package com.example.photoslidercamera.ui.view;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import com.example.photoslidercamera.ui.fragment.BaseFragment;

import java.util.List;

public class CustomViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment<?>> fragmentList;
    private Context context;

    public CustomViewPagerAdapter(FragmentManager fragmentManager, List<BaseFragment<?>> fragmentList, Context context) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragmentList = fragmentList;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
//        switch (position) {
//            case 1:
//                return fragmentList.get(1);
//            case 2:
//                return fragmentList.get(2);
//            default:
//                return fragmentList.get(0);
//        }
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }
}

