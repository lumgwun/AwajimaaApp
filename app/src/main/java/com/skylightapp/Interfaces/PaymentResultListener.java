package com.skylightapp.Interfaces;

public interface PaymentResultListener {
    public void onPaymentComplete(boolean success);
    void onPaymentError(int i, String errorMessage);
}
