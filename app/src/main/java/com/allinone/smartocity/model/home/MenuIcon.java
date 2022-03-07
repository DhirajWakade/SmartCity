package com.allinone.smartocity.model.home;

public class MenuIcon {

    public int imageId;
    public String txt;

    public MenuIcon(int imageId, String text) {

        this.imageId = imageId;
        this.txt = text;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}