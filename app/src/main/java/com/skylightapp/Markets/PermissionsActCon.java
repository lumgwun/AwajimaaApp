package com.skylightapp.Markets;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;

import com.skylightapp.MarketClasses.PermissionsCheckerCon;
import com.skylightapp.MarketClasses.ToastUtilsCon;
import com.skylightapp.R;

public class PermissionsActCon extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 777;
    private static final String EXTRA_PERMISSIONS = "extraPermissions";

    private enum permissionFeatures {
        CAMERA,
        MICROPHONE
    }


    private PermissionsCheckerCon checker;
    private boolean requiresCheck;

    public static void startForResult(Activity activity, int code, String... permissions) {
        Intent intent = new Intent(activity, PermissionsActCon.class);
        intent.putExtra(EXTRA_PERMISSIONS, permissions);
        ActivityCompat.startActivityForResult(activity, intent, code, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || !getIntent().hasExtra(EXTRA_PERMISSIONS)) {
            throw new RuntimeException("This Activity needs to be launched using the static startActivityForResult() method.");
        }
        setContentView(R.layout.act_permissions_act_con);

        checker = new PermissionsCheckerCon(this);
        requiresCheck = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (requiresCheck) {
            checkPermissions();
        } else {
            requiresCheck = true;
        }
    }

    private void checkPermissions() {
        String[] permissions = getPermissions();
        checkPermissionAudioVideo(permissions);
    }

    private void checkPermissionAudioVideo(String[] permissions) {
        if (checker.missAllPermissions(permissions)) {
            requestPermissions(permissions);
        } else {
            allPermissionsGranted();
        }
    }

    private String[] getPermissions() {
        return getIntent().getStringArrayExtra(EXTRA_PERMISSIONS);
    }

    private void requestPermissions(String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    private void allPermissionsGranted() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
            requiresCheck = true;
            allPermissionsGranted();
        } else {
            requiresCheck = false;
            showDeniedResponse(grantResults);
            finish();
        }
    }

    private void showDeniedResponse(int[] grantResults) {
        if (grantResults.length > 1) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != 0) {
                    ToastUtilsCon.longToast(PermissionsActCon.this, getString(R.string.permission_unavailable, permissionFeatures.values()[i]));
                }
            }
        } else {
            ToastUtilsCon.longToast(PermissionsActCon.this, getString(R.string.permission_unavailable, permissionFeatures.MICROPHONE));
        }
    }

    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;

    }
}