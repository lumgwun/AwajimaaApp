package com.skylightapp.MarketClasses;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

public class PermissionsCheckerCon {
    private final Context context;

    public PermissionsCheckerCon(Context context) {
        this.context = context;
    }

    public boolean missAllPermissions(String... permissions) {
        for (String permission : permissions) {
            if (missPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    private boolean missPermission(String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED;
    }
}
