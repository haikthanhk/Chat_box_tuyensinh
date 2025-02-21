package com.example.photoslidercamera.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.photoslidercamera.R;
import com.example.photoslidercamera.databinding.DialogPermissionBinding;

public class PermissionDialog extends BaseDialog<DialogPermissionBinding> {
    private Context context;

    private OnPermissionDialogClicked listener;

    public PermissionDialog(@NonNull Context context, @NonNull OnPermissionDialogClicked listener){
        super(context, R.style.Theme_Dialog);
        this.context = context;
        this.listener = listener;
    }
    @Override
    protected DialogPermissionBinding getViewBinding() {
        return DialogPermissionBinding.inflate(LayoutInflater.from(context), null, false);
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
