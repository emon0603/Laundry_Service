package com.emon.qwash.ModelClass;



public class AddressItem {
    private String title;
    private int leftIconRes;
    private int rightIcon1Res;
    private int rightIcon2Res;

    public AddressItem(String title, int leftIconRes) {
        this.title = title;
        this.leftIconRes = leftIconRes;
    }

    public String getTitle() {
        return title;
    }

    public int getLeftIconRes() {
        return leftIconRes;
    }

    public int getRightIcon1Res() {
        return rightIcon1Res;
    }

    public int getRightIcon2Res() {
        return rightIcon2Res;
    }
}
