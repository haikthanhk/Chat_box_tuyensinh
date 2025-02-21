package com.example.photoslidercamera.ui.activity;

import android.os.Handler;
import android.os.Looper;

import com.example.photoslidercamera.databinding.SplashActivityBinding;

public class SplashActivity extends BaseActivity<SplashActivityBinding>{
    @Override
    protected SplashActivityBinding getViewBinding() {
        return SplashActivityBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initialize() {
        nextActivity();
    }

    private void nextActivity(){
        new Handler(Looper.getMainLooper()).postDelayed(() -> MainActivity.startMain(SplashActivity.this), 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
