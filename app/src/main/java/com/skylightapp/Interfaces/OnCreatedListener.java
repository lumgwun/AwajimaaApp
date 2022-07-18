package com.skylightapp.Interfaces;

public interface OnCreatedListener {
    void onCountChanged(long count);
    public void onCreated(boolean success);
    public void onNotCreated(boolean failure);
}
