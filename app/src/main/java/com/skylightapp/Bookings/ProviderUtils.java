package com.skylightapp.Bookings;

import static java.util.Objects.requireNonNull;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

public class ProviderUtils {
    private static final String TAG = "ProviderUtils";
    private static final String PROVIDER_ID_KEY =
            "com.google.mapsplatform.transportation.sample.provider_id";
    private static final String PROVIDER_URL_KEY =
            "com.google.mapsplatform.transportation.sample.provider_url";

    public static String getProviderId(Context context) {
        Bundle metadata = getAppMetadata(context);
        if (!metadata.containsKey(PROVIDER_ID_KEY)) {
            throw new IllegalStateException("App metadata in manifest does not contain provider Id.");
        }
        return requireNonNull(metadata.getString(PROVIDER_ID_KEY));
    }

    public static String getProviderBaseUrl(Context context) {
        Bundle metadata = getAppMetadata(context);
        if (!metadata.containsKey(PROVIDER_URL_KEY)) {
            throw new IllegalStateException(
                    "App metadata in manifest does not contain provider base URL.");
        }
        return requireNonNull(metadata.getString(PROVIDER_URL_KEY));
    }

    private static Bundle getAppMetadata(Context context) {
        String packageName = context.getPackageName();
        ApplicationInfo applicationInfo;
        try {
            applicationInfo =
                    context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            return applicationInfo.metaData;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Cannot find Manifest metadata in package : " + packageName);
            throw new IllegalStateException(e);
        }
    }

    private ProviderUtils() {}
}
