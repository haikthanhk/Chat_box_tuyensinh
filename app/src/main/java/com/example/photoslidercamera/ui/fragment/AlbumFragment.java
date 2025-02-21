package com.example.photoslidercamera.ui.fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.photoslidercamera.R;
import com.example.photoslidercamera.databinding.FragmentGalleryBinding;
import com.example.photoslidercamera.model.Image;
import com.example.photoslidercamera.utils.ImageFileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AlbumFragment extends BaseFragment<FragmentGalleryBinding> {
    public static AlbumFragment instance() {
        return newInstance(AlbumFragment.class, null);
    }
    private ArrayList<Image> listImage;

    @Override
    void handlerBackpress() {
        closeFragment(this);
    }

    @Override
    FragmentGalleryBinding getBinding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentGalleryBinding.inflate(inflater, container, false);
    }

    ImageAdapter imageAdapter;

    @Override
    void initView() {
        binding.imgBack.setOnClickListener(v -> closeFragment(this));
        imageAdapter = new ImageAdapter(requireContext(), new OnItemGalleryClicked() {
            @Override
            public void onSizeSelected(int size) {
                if(size > 0){
                    binding.tvCount.setVisibility(View.VISIBLE);
                    binding.tvCount.setText(size + "/10");
                }else{
                    binding.tvCount.setVisibility(View.GONE);
                }
            }
        });
        binding.rcvImage.setAdapter(imageAdapter);
        getListImage(images -> requireActivity().runOnUiThread(() -> {
            if(images.isEmpty()){
                binding.rcvImage.setVisibility(View.GONE);
                binding.llNoImage.setVisibility(View.VISIBLE);
                binding.ctnCreateSlide.setVisibility(View.GONE);
            }else {
                imageAdapter.setItems(images);
                binding.rcvImage.setVisibility(View.VISIBLE);
                binding.llNoImage.setVisibility(View.GONE);
                binding.ctnCreateSlide.setVisibility(View.VISIBLE);
            }
        }));

        binding.ctnCreateSlide.setOnClickListener(v -> {
            ArrayList<Image> imagesSelected = imageAdapter.getSelectedImages();
            if(imagesSelected.isEmpty() || imagesSelected.size() < 2){
                Toast.makeText(requireContext(), getString(R.string.must_select_image), Toast.LENGTH_SHORT).show();
                return;
            }
            if(imagesSelected.size() > 10 ){
                Toast.makeText(requireContext(), getString(R.string.max_is_10), Toast.LENGTH_SHORT).show();
                return;
            }
            addFragment(SliderFragment.instance(imagesSelected));
        });

    }


    private void getListImage(OnFinishQueryImage onFinishQueryImage){
        new Thread(() -> {
            ArrayList<Image> imageArrayList = new ArrayList<>();
            File directPath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/Camera Slider");
            List<String> listPathImage  = ImageFileUtils.getPNGImageFiles(directPath.getAbsolutePath());
            for(String pathImage : listPathImage){
                Image image =  new Image(pathImage);
                imageArrayList.add(image);
                Log.d("TAG", "getListImage: " + pathImage);
            }
            if(onFinishQueryImage != null){
                onFinishQueryImage.onFinish(imageArrayList);
            }
        }).start();
    }



    interface  OnFinishQueryImage{
        void onFinish(ArrayList<Image> images);
    }
}
