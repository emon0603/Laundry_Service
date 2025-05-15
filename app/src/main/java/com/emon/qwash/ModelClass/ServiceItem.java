package com.emon.qwash.ModelClass;

public class ServiceItem {
    private int imageResId;
    private String price;
    private String name;

    public ServiceItem(int imageResId, String price, String name) {
        this.imageResId = imageResId;
        this.price = price;
        this.name = name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}

