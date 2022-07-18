package com.skylightapp.Interfaces;


public interface OnPackageChangedListener<SkyLightPackage> {
    public void onObjectChanged(SkyLightPackage obj);

    public void onError(String errorText);

    void onSavingsChanged(SkyLightPackage parsePackageList);
}
