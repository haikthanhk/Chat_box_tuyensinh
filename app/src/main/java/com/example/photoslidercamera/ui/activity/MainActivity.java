package com.example.photoslidercamera.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.photoslidercamera.R;
import com.example.photoslidercamera.databinding.ActivityMainBinding;
import com.example.photoslidercamera.ui.fragment.GetStartedFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    public static void startMain(Context context){
        Intent intent = new Intent(context, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initialize() {
        getStartedFragment = GetStartedFragment.newInstance(GetStartedFragment.class, null);
        addFragment(getStartedFragment);

    }

    private GetStartedFragment getStartedFragment;

}