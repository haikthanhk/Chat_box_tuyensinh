package com.example.photoslidercamera.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.example.photoslidercamera.ui.activity.BaseActivity;

public abstract class BaseFragment<T extends ViewBinding> extends Fragment {

    protected T binding;

    private OnBackPressedCallback backPressedCallback;

    abstract void handlerBackpress();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                handlerBackpress();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, backPressedCallback);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        backPressedCallback.remove();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = getBinding(inflater, container);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    abstract T getBinding(LayoutInflater inflater, @Nullable ViewGroup container);

    abstract void initView();

    protected void addFragment(Fragment fragment) {
        ((BaseActivity<?>) requireActivity()).addFragment(fragment, android.R.id.content, true);
    }

    protected void replaceFragment(Fragment fragment){
        ((BaseActivity<?>) requireActivity()).replaceFragment(fragment, android.R.id.content, true);
    }

    protected void closeFragment(Fragment fragment){
        ((BaseActivity<?>) requireActivity()).handlerBackPress();

    }
    public static <F extends Fragment> F newInstance(Class<F> fragment, Bundle args) {
        F f = null;
        try {
            f = fragment.newInstance();
            if (args != null) {
                f.setArguments(args);
            }
        } catch (InstantiationException | IllegalAccessException | java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        return f;
    }
}
