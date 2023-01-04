package com.skylightapp.Classes;

import android.Manifest;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

public class AppLockUtils {

    public static boolean hasDeviceFingerprintSupport(Context context) {
        FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return fingerprintManager.isHardwareDetected() && fingerprintManager.hasEnrolledFingerprints();
    }
}
