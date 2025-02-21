package com.example.photoslidercamera.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.camera.core.Preview;

import com.example.photoslidercamera.databinding.FragmentPreviewImageBinding;

import java.io.File;

public class PreviewImage extends BaseFragment<FragmentPreviewImageBinding>{

    private final String TAG = PreviewImage.class.getName();
    private static final String PREVIEW_PATH_IMAGE = "PREVIEW_PATH_IMAGE" ;
    public static PreviewImage instance(String pathImage){
        Bundle bundle = new Bundle();
        bundle.putString(PREVIEW_PATH_IMAGE, pathImage);
        return newInstance(PreviewImage.class, bundle);
    }

    private String pathImage = null;

    @Override
    void handlerBackpress() {
        replaceFragment(CameraFragment.instance());
    }

    @Override
    FragmentPreviewImageBinding getBinding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentPreviewImageBinding.inflate(inflater, container, false);
    }

    @Override
    void initView() {
        getImage();
        binding.imgBack.setOnClickListener(v -> closeFragment(this));

        binding.ctnCreateSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(AlbumFragment.instance());
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 500);
            }
        });
    }

    private void getImage(){
        pathImage = requireArguments().getString(PREVIEW_PATH_IMAGE);
        Bitmap bitmap = BitmapFactory.decodeFile(pathImage);
        Log.d(TAG, "getImage: " + pathImage);
        binding.imgPreview.setImageBitmap(bitmap);
    }
}

