package com.allinone.smartocity.model.product;

import java.io.Serializable;

public class FilterColorPojo implements Serializable {
    String id;
    String colorName;
    String colorCode;
    String colorImage;
    int selectedPos = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorImage() {
        return colorImage;
    }

    public int getSelectedPos() {
        return selectedPos;
    }

    public void setSelectedPos(int selectedPos) {
        this.selectedPos = selectedPos;
    }

    public void setColorImage(String colorImage) {
        this.colorImage = colorImage;


    }
}