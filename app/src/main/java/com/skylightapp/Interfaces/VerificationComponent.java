package com.skylightapp.Interfaces;

import com.skylightapp.FlutterWavePayments.OTPFragment;
import com.skylightapp.FlutterWavePayments.PinFragment;

public interface VerificationComponent {


    void inject(OTPFragment otpFragment);

    void inject(PinFragment pinFragment);
}
