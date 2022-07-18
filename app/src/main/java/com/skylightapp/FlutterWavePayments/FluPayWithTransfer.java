package com.skylightapp.FlutterWavePayments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.skylightapp.Interfaces.PaymentResultListener;
import com.skylightapp.R;

public class FluPayWithTransfer extends AppCompatActivity implements PaymentResultListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flu_pay_with_transfer);
    }

    @Override
    public void onPaymentComplete(boolean success) {

    }

    @Override
    public void onPaymentError(int i, String errorMessage) {

    }
}