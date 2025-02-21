package com.example.photoslidercamera.ui.fragment;

import static androidx.camera.core.AspectRatio.RATIO_16_9;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.core.TorchState;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;

import com.example.photoslidercamera.R;
import com.example.photoslidercamera.databinding.FragmentCameraBinding;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraFragment extends BaseFragment<FragmentCameraBinding> {

    private final String TAG = CameraFragment.class.getName();

    public static CameraFragment instance() {
        return newInstance(CameraFragment.class, null);
    }

    private ProcessCameraProvider cameraProvider;
    private CameraSelector cameraSelector;
    private Camera camera;
    private Preview preview;
    private ImageCapture imageCapture;
    private ImageAnalysis imageAnalysis;
    private ExecutorService cameraExecutor;

    @Override
    void initView() {
        startCamera();
        cameraExecutor = Executors.newSingleThreadExecutor();
        mapping();
    }

    private void mapping() {
        binding.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(BaseFragment.newInstance(SettingFragment.class, null));
            }
        });
        binding.ctnFlash.setOnClickListener(v -> changeStateFlash());
        binding.ctnAlbum.setOnClickListener(v -> {
            addFragment(AlbumFragment.instance());
        });

        binding.takePicture.setOnClickListener(v -> {
            savePicture();
        });
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture = ProcessCameraProvider.getInstance(requireContext());
        cameraProviderListenableFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderListenableFuture.get();
                preview = new Preview.Builder().setTargetAspectRatio(RATIO_16_9).build();
                imageCapture = new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY).setTargetAspectRatio(RATIO_16_9).build();
                imageAnalysis = new ImageAnalysis.Builder()
                        .setTargetAspectRatio(RATIO_16_9)
                        .build();

                imageAnalysis.setAnalyzer(cameraExecutor, image -> {
                });

                cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();

                cameraProvider.unbindAll();
                if (camera != null) {
                    camera.getCameraInfo().getCameraState().removeObservers(getViewLifecycleOwner());
                }

                try {
                    camera = cameraProvider.bindToLifecycle(CameraFragment.this, cameraSelector, preview, imageCapture, imageAnalysis);
                    preview.setSurfaceProvider(binding.previewView.getSurfaceProvider());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                camera = cameraProvider.bindToLifecycle(CameraFragment.this, cameraSelector);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    @Override
    void handlerBackpress() {
        closeFragment(this);
    }

    @Override
    FragmentCameraBinding getBinding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentCameraBinding.inflate(inflater, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cameraExecutor.shutdown();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        if (cameraSelector != null && cameraProvider != null) {
            camera = cameraProvider.bindToLifecycle(CameraFragment.this, cameraSelector, preview, imageCapture, imageAnalysis);
        }
    }

    private void changeStateFlash() {
        LiveData<Integer> torchState = camera.getCameraInfo().getTorchState();

        if (torchState.getValue() != null) {
            camera.getCameraControl().enableTorch(torchState.getValue() == TorchState.OFF);
        }
    }

    private void disableTorch(){
        if(camera == null ) return;
        camera.getCameraControl().enableTorch(false);

    }
    private void savePicture() {
        if (imageCapture == null) return;
        showLoading();
        disableTorch();
        cameraProvider.unbind(preview);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/Camera Slider");

        if (file.exists() || file.mkdir()) {
            File fileSave = new File(file, new SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".png");
            ImageCapture.OutputFileOptions outputFileOptions =  new ImageCapture.OutputFileOptions.Builder(fileSave).build();
            imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(requireContext()), new ImageCapture.OnImageSavedCallback() {
                @Override
                public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                    Log.d(TAG, "onImageSaved: "+ outputFileResults.getSavedUri()+"---> " + fileSave.getAbsolutePath());
                    replaceFragment(PreviewImage.instance(fileSave.getAbsolutePath()));
                    hideLoading();
                }

                @Override
                public void onError(@NonNull ImageCaptureException exception) {
                    Toast.makeText(requireContext(), "Lưu ảnh thất bại, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                    camera = cameraProvider.bindToLifecycle(CameraFragment.this, cameraSelector, preview, imageCapture, imageAnalysis);
                    hideLoading();
                }
            });
        }
    }

    private void showLoading(){
        binding.progess.setVisibility(View.VISIBLE);
    }

    private void hideLoading(){
        binding.progess.setVisibility(View.GONE);
    }

    private final String FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS";

}
