package com.emon.qwash.ModelClass;



public class AddressItem {
    private int leftIconRes;
    private int rightIcon1Res;
    private int rightIcon2Res;

    public int id;
    public String address;
    public String type;


    public AddressItem( int leftIconRes) {
        this.leftIconRes = leftIconRes;
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
