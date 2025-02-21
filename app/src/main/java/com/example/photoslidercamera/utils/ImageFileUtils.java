package com.example.photoslidercamera.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageFileUtils {
    public static List<String> getPNGImageFiles(String directoryPath) {
        List<String> imageFiles = new ArrayList<>();

        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".png")) {
                    imageFiles.add(file.getAbsolutePath());
                } else if (file.isDirectory()) {
                    imageFiles.addAll(getPNGImageFiles(file.getAbsolutePath()));
                }
            }
        }

        return imageFiles;
    }
}
