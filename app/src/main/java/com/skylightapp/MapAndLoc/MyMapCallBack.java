package com.skylightapp.MapAndLoc;

public abstract class MyMapCallBack {
    public abstract void onSuccess(String result);
    public void onFailure(Exception e){}
}
