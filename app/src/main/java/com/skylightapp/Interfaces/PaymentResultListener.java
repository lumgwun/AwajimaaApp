package com.skylightapp.Interfaces;

import com.skylightapp.Classes.SkyLightPackage;

public interface PaymentResultListener {
    public void onPaymentComplete(boolean success);
    void onPaymentError(int i, String errorMessage);
}
