package com.example.photoslidercamera.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.photoslidercamera.R;
import com.example.photoslidercamera.databinding.DialogGotoSettingBinding;

public class GotoSettingDialog extends  BaseDialog<DialogGotoSettingBinding> {
    private Context context;
    private OnPermissionDialogClicked listener;
    public GotoSettingDialog(@NonNull Context context, OnPermissionDialogClicked onPermissionDialogClicked){
        super(context, R.style.Theme_Dialog_2);
        this.context = context;
        this.listener = onPermissionDialogClicked;
    }
    @Override
    protected DialogGotoSettingBinding getViewBinding() {
        return DialogGotoSettingBinding.inflate(LayoutInflater.from(context), null, false);
    }

    @Override
    protected void createView() {
        binding.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAccept();
            }
        });

        binding.deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
