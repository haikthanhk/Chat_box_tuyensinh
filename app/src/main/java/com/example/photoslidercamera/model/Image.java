package com.example.photoslidercamera.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {
    private String pathImage;

    public Image() {

    }

    public Image(String pathImage) {
        this.pathImage = pathImage;
    }

    protected Image(Parcel in) {
        pathImage = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pathImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

