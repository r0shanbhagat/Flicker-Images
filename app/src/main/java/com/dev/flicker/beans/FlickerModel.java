package com.dev.flicker.beans;


import android.graphics.Bitmap;

public class FlickerModel {
    private String name;
    private Bitmap thumbnail, largeBitmap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Bitmap getLargeBitmap() {
        return largeBitmap;
    }

    public void setLargeBitmap(Bitmap largeBitmap) {
        this.largeBitmap = largeBitmap;
    }
}
