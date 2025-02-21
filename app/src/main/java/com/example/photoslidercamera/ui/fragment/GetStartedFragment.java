package com.example.photoslidercamera.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.photoslidercamera.databinding.FragmentGetStartedBinding;
import com.example.photoslidercamera.ui.dialog.GotoSettingDialog;
import com.example.photoslidercamera.ui.dialog.OnPermissionDialogClicked;
import com.example.photoslidercamera.ui.dialog.PermissionDialog;

import java.lang.reflect.Array;
import java.util.Map;

public class GetStartedFragment extends BaseFragment<FragmentGetStartedBinding> {
    private boolean isGotoSetting = false;
    private String TAG = GetStartedFragment.class.getName();
    private PermissionDialog permissionDialog;
    private GotoSettingDialog gotoSettingDialog;

    private CameraFragment cameraFragment;

    @Override
    void handlerBackpress() {
        closeFragment(this);
    }

    @Override
    FragmentGetStartedBinding getBinding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentGetStartedBinding.inflate(inflater, container, false);
    }

    @Override
    void initView() {
        cameraFragment = CameraFragment.instance();
        permissionDialog = new PermissionDialog(requireContext(), new OnPermissionDialogClicked() {
            @Override
            public void onAccept() {
                requestPermissionLauncher.launch(PERMISSIONS);
            }
        });

        gotoSettingDialog = new GotoSettingDialog(requireContext(), new OnPermissionDialogClicked() {
            @Override
            public void onAccept() {
                gotoSetting();
            }
        });
        binding.start.setOnClickListener(v -> {
            if(isAllPermissionAccept()){
                addFragment(cameraFragment);
            }else {
                permissionDialog.show();
            }
        });
    }

    private static final String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private final ActivityResultLauncher<String[]> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
        @Override
        public void onActivityResult(Map<String, Boolean> result) {
            boolean isAllPermissionGranted = true;
            for (boolean value : result.values()) {
                if (!value) {
                    isAllPermissionGranted = false;
                    break;
                }
            }

            permissionDialog.dismiss();
            if (isAllPermissionGranted) {
                nextFragment();
                Log.d(TAG, "onActivityResult: allPermissionGranted");
            } else {
                gotoSettingDialog.show();
            }
        }
    });

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requestPermissionLauncher.unregister();
    }

    private void gotoSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", requireContext().getPackageName(), null);
        intent.setData(uri);
        isGotoSetting = true;
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isGotoSetting){
            if(isAllPermissionAccept()){
                isGotoSetting = false;
                permissionDialog.dismiss();
                gotoSettingDialog.dismiss();
                nextFragment();
            }
        }
    }

    private boolean isAllPermissionAccept(){
        boolean isAcceptAll = true;
        for(String permission : PERMISSIONS){
            if(ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED){
                isAcceptAll = false;
                break;
            }
        }
        return isAcceptAll;
    }
    
    private void nextFragment(){
        Log.d(TAG, "nextFragment: ");
    }
}
