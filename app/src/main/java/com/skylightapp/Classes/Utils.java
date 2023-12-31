package com.skylightapp.Classes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skylightapp.Interfaces.UploadImagePrefix;
import com.skylightapp.MapAndLoc.Coordinate;
import com.skylightapp.MapAndLoc.MapPanoramaStVAct;
import com.skylightapp.R;
import com.skylightapp.Transactions.Pay_load;
import com.skylightapp.Transactions.SubAccounts;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;

import javax.crypto.Cipher;


@SuppressWarnings({"deprecation", "ResultOfMethodCallIgnored"})
public class Utils {
    public static final String ATTRIBUTE_TTF_KEY = "ttf_name";

    public static final String ATTRIBUTE_SCHEMA = "http://schemas.android.com/apk/lib/coop.skylightbizapp.util";

    public final static String SHOPPING_LIST_TAG = "SHoppingListFragment";
    public static final String PRODUCT_OVERVIEW_FRAGMENT_TAG = "ProductOverView";
    public static final String MY_CART_FRAGMENT = "MyCartFragment";
    public static final String MEMBERSHIP_SUB = "https://business.quickteller.com/link/pay/LumgwunErv5T";

    public static final String MY_ORDERS_FRAGMENT = "MYOrdersFragment";
    public static final String HOME_FRAGMENT = "HomeFragment";
    public static final String SEARCH_FRAGMENT_TAG = "SearchFragment";
    public static final String SETTINGS_FRAGMENT_TAG = "SettingsFragment";
    public static final String OTP_LOGIN_TAG = "OTPLogingFragment";
    public static final String CONTACT_US_FRAGMENT = "ContactUs";
    public static final String KEY_LOCATION_UPDATES_RESULT = "LocResult";
    public static final String KEY_LOCATION_UPDATES_REQUESTED = "LocRequested";
    private static final String PACKAGE_NAME = "Our Cooperative App";

    public static final HashMap<String, LatLng> BAY_AREA_LANDMARKS = new HashMap<>();
    public static final String GEOFENCES_ADDED_KEY = PACKAGE_NAME + ".GEOFENCES_ADDED_KEY";
    private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;

    public static final long GEOFENCE_RADIUS_IN_METERS = 1609; // 1 mile, 1.6 km;
    private static final String PREFERENCES_FILE = "materialsample_settings";
    private static String CURRENT_TAG = null;
    public static final int CAMERA_PERMISSION_CODE = 100;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1000;
    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456rtyuofnnWERRTGydryfr346666";
    private static final String ALGORITHM = "DESede";
    private static final String TRANSFORMATION = "DESede/ECB/PKCS5Padding";
    private static final String TARGET = "FLWSECK-";
    private static final String MD5 = "MD5";
    private static final String CHARSET_NAME = "UTF-8";
    private static final String UTF_8 = "utf-8";
    private static final String TAG = "ChangeLogDialog";
    private Context mContext;

    public static String firebaseDBDate = "dd-MM-yyyy";
    public static String firebaseDBDay = "dd-MM-yyyy";
    public static final long NOW_TIME_RANGE = DateUtils.MINUTE_IN_MILLIS * 5; // 5 minutes

    public static String dateTime = "dd-MM-yyyy";
    static Context context;
    //final static String KEY_LOCATION_UPDATES_REQUESTED = "location-updates-requested";
    //final static String KEY_LOCATION_UPDATES_RESULT = "location-update-result";
    final static String CHANNEL_ID = "channel_01";
    public static final String KEY_REQUESTING_LOCATION_UPDATES = "requesting_locaction_updates";
    @SuppressLint("InlinedApi")
    private static final String FINE_LOC = Manifest.permission.ACCESS_FINE_LOCATION,
            BG_LOC = Manifest.permission.ACCESS_BACKGROUND_LOCATION;

    private static final int PERMISSION_REQ_CODE = 115, GPS_REQ = 124;

    public static void setUpToolBar(final Activity context, Toolbar toolbar,
                                    ActionBar actionBar, String title) {
        try {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(title);
            toolbar.getNavigationIcon().setColorFilter(
                    ContextCompat.getColor(context, android.R.color.white),
                    PorterDuff.Mode.SRC_IN);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isNetConnected(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected();
    }


    public void init(AppCompatTextView txtView, Context context, AttributeSet attrs) {
        try {
            Typeface typeface = getCustomFont(context, attrs);
            if (typeface != null)
                txtView.setTypeface(typeface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Typeface getCustomFont(Context context, AttributeSet attrs) {
        Typeface typeface = null;
        try {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.CustomWidget);
            for (int i = 0, count = typedArray.getIndexCount(); i < count; i++) {
                int attribute = typedArray.getIndex(i);
                if (attribute == R.styleable.CustomWidget_font_name) {
                    typeface = Typeface.createFromAsset(context.getResources()
                            .getAssets(), typedArray.getString(attribute));
                }
            }
            typedArray.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return typeface;
    }


    public static void showLocationAlert(final Context context) {
        try {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
            mBuilder.setMessage(context.getString(R.string.location_disabled));
            mBuilder.setPositiveButton(context.getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent myIntent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            context.startActivity(myIntent);
                        }
                    });
            mBuilder.setNegativeButton(context.getString(R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = mBuilder.create();
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showSnack(View view, String msg) {
        Snackbar.make(view, msg, 2000).show();
    }

    public static boolean isAndQ() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }

    public static boolean checkLPermission(Activity context) {
        try {
            if (isPermissionGranted(context))
                return true;
            String[] permissions = isAndQ() ? new String[]{FINE_LOC, BG_LOC}
                    : new String[]{FINE_LOC};
            if (isShouldShow(context, FINE_LOC))
                showPermission(context, permissions);
            else
                showPermission(context, permissions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isPermissionOk(int... results) {
        boolean isAllGranted = true;
        for (int result : results) {
            if (PackageManager.PERMISSION_GRANTED != result) {
                isAllGranted = false;
                break;
            }
        }
        return isAllGranted;
    }

    private static void showPermission(Activity context, String... permissions) {
        ActivityCompat.requestPermissions(context, permissions, PERMISSION_REQ_CODE);
    }


    private static boolean isShouldShow(Activity context, String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(context, permission);
    }

    public static boolean isPermissionGranted(Context context) {
        if (isPermissionOk(context, FINE_LOC))
            return !isAndQ() || isPermissionOk(context, BG_LOC);
        else
            return false;
    }

    private static boolean isPermissionOk(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void enableGPS(final Activity context, final
    OnSuccessListener<Location> listener) {
        try {
            final FusedLocationProviderClient client = LocationServices
                    .getFusedLocationProviderClient(context);
            final LocationRequest locationRequest = getLocationReq();
            LocationSettingsRequest request = new LocationSettingsRequest
                    .Builder().addLocationRequest(locationRequest)
                    .setAlwaysShow(true).build();
            Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(
                    context).checkLocationSettings(request);
            task.addOnSuccessListener(locationSettingsResponse ->
                    getMyLocation(client, locationRequest, listener));
            task.addOnFailureListener(e -> {
                if (e instanceof ResolvableApiException) {
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(context, GPS_REQ);
                    } catch (IntentSender.SendIntentException sendEx) {
                        listener.onSuccess(null);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    private static void getMyLocation(final FusedLocationProviderClient client
            , LocationRequest locationRequest, final OnSuccessListener<Location> listener) {
        try {
            client.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null)
                        Log.d("OnDemandDelivery::","result null");
                    else {
                        Location location = locationResult.getLastLocation();
                        client.removeLocationUpdates(this);
                        listener.onSuccess(location);
                    }
                }
            }, Looper.myLooper());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static LocationRequest getLocationReq() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(2000);
        return locationRequest;
    }

    public static boolean requestingLocationUpdates(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_REQUESTING_LOCATION_UPDATES, false);
    }

    public static void setRequestingLocationUpdates(Context context, boolean requestingLocationUpdates) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(KEY_REQUESTING_LOCATION_UPDATES, requestingLocationUpdates)
                .apply();
    }


    public static String getLocationText(Location location) {
        return location == null ? "Unknown location" :
                "(" + location.getLatitude() + ", " + location.getLongitude() + ")";
    }

    @SuppressLint("StringFormatInvalid")
    public static String getLocationTitle(Context context) {
        return context.getString(R.string.location_updated,
                DateFormat.getDateTimeInstance().format(new Date()));
    }


    public static String awajimaAmountFormat(double count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        DecimalFormat format = new DecimalFormat("0.#");
        String value = format.format(count / Math.pow(1000, exp));
        return String.format("%s%c", value, "kMBTPE".charAt(exp - 1));
    }


    public static Integer toDp(Integer value) {
        Float calculatedValue = value * Resources.getSystem().getDisplayMetrics().density + 0.5f;
        return calculatedValue.intValue();
    }

    public static Coordinate toCoordinate(LatLng latLng) {
        if (latLng != null) {
            return new Coordinate(latLng.latitude, latLng.longitude);
        } else {
            return null;
        }
    }

    public static Long secondToMillisecond(Long value) {
        return value * 1000;
    }

    public static LatLng toLatLng(Coordinate coordinate) {
        return new LatLng(coordinate.lat, coordinate.lng);
    }

    public static Bitmap toBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable)
            return ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static boolean getRequestingLocationUpdates(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_LOCATION_UPDATES_REQUESTED, false);
    }
    public static String concat(String s1, String s2) {
        return s1.concat(s2);
    }
    public static Date dateAdd(Date in, int daysToAdd) {
        if (in == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(in);
        cal.add(Calendar.DAY_OF_MONTH, daysToAdd);
        return cal.getTime();

    }
    public static Date dateAddYear(Date in, int tahun) {
        if (in == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(in);
        cal.add(Calendar.YEAR, tahun);
        return cal.getTime();

    }
    public static OutputStream writeFile(String fileName) {
        OutputStream fileOut = null;
        try {
            File file = new File(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

            if (file.exists()) {
                fileOut = new FileOutputStream(file);
            } else {
                file.createNewFile();
                fileOut = new FileOutputStream(file);
            }

        } catch (IOException e) {
            e.printStackTrace();

        }

        return fileOut;
    }

    public static File getFile(String fileName) {
        File file = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

        return file;
    }
    public static Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static double distance(double lat1, double lng1, double lat2, double lng2, String unit){
        double earthRadius = 6371.0;
        if (unit.equalsIgnoreCase("M")){
            earthRadius = 6371 * 1000;
        }else if(unit.equalsIgnoreCase("N")){
            earthRadius=6371.75;
        }
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat,2)+ Math.pow(sindLng,2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;
        return dist;

    }
    public static double taxiDistance(LatLng userLatLng, LatLng dBLatLng, String unit){
        double earthRadius = 6371.0;
        if (unit.equalsIgnoreCase("M")){
            earthRadius = 6371 * 1000;
        }else if(unit.equalsIgnoreCase("N")){
            earthRadius=6371.75;
        }
        double latitude = userLatLng.latitude;
        double longitude = userLatLng.longitude;

        double latDB = dBLatLng.latitude;
        double longDB = dBLatLng.longitude;


        double dLat = Math.toRadians(latitude-latDB);
        double dLng = Math.toRadians(longitude-longDB);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat,2)+ Math.pow(sindLng,2) * Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(latDB));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;
        return dist;

    }



    public static void sendNotification(Context context, String notificationDetails) {

        Intent notificationIntent = new Intent(context, MapPanoramaStVAct.class);

        notificationIntent.putExtra("from_notification", true);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);


        stackBuilder.addParentStack(MapPanoramaStVAct.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        // Define the notification settings.
        builder.setSmallIcon(R.mipmap.ic_launcher)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.ic_launcher))
                .setColor(Color.RED)
                .setContentTitle("Location update")
                .setContentText(notificationDetails)
                .setContentIntent(notificationPendingIntent);

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.app_name);
            // Create the channel for the notification
            NotificationChannel mChannel =
                    new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);

            // Set the Notification Channel for the Notification Manager.
            mNotificationManager.createNotificationChannel(mChannel);

            // Channel ID
            builder.setChannelId(CHANNEL_ID);
        }

        // Issue the notification
        mNotificationManager.notify(0, builder.build());
    }
    protected void show(final String string) {
        // Get resources
        final String packageName = mContext.getPackageName();
        final Resources resources;
        try {
            resources = mContext.getPackageManager()
                    .getResourcesForApplication(packageName);
        } catch (PackageManager.NameNotFoundException ignored) {
            return;
        }
    }


    @SuppressLint("SimpleDateFormat")
    private String parseDate(final String dateString) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            final Date parsedDate = dateFormat.parse(dateString);
            return android.text.format.DateFormat.getDateFormat(mContext).format(Objects.requireNonNull(parsedDate));
        } catch (ParseException ignored) {

            return dateString;
        }
    }
    public static String getUserCountry(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available
                return simCountry.toLowerCase(Locale.US);
            }
            else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available
                    return networkCountry.toLowerCase(Locale.US);
                }
            }
        }
        catch (Exception e) { }
        return null;
    }



    public static String getLocationResultTitle(Context context, List<Location> locations) {
        String numLocationsReported = context.getResources().getQuantityString(
                R.plurals.num_locations_reported, locations.size(), locations.size());
        return numLocationsReported + ": " + DateFormat.getDateTimeInstance().format(new Date());
    }

    private static String getLocationResultText(Context context, List<Location> locations) {
        if (locations.isEmpty()) {
            return context.getString(R.string.unknown_location);
        }
        StringBuilder sb = new StringBuilder();
        for (Location location : locations) {
            sb.append("(");
            sb.append(location.getLatitude());
            sb.append(", ");
            sb.append(location.getLongitude());
            sb.append(")");
            sb.append("\n");
        }
        return sb.toString();
    }
    public static void setLocationUpdatesResult(Context context, List<Location> locations) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(KEY_LOCATION_UPDATES_RESULT, getLocationResultTitle(context, locations)
                        + "\n" + getLocationResultText(context, locations))
                .apply();
    }

    public static String getLocationUpdatesResult(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(KEY_LOCATION_UPDATES_RESULT, "");
    }


    public static SimpleDateFormat getFirebaseDateFormat() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat cbDateFormat = new SimpleDateFormat(firebaseDBDate);
        cbDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return cbDateFormat;
    }

    public static String formatFirebaseDay(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat cbDateFormat = new SimpleDateFormat(firebaseDBDay);
        cbDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return cbDateFormat.format(date);
    }

    public static String formatDateTime(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat cbDateFormat = new SimpleDateFormat(dateTime);
        cbDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return cbDateFormat.format(date);
    }

    public static CharSequence getRelativeTimeSpanString(Context context, long time) {
        long now = System.currentTimeMillis();
        long range = Math.abs(now - time);

        if (range < NOW_TIME_RANGE) {
            return context.getString(R.string.now);
        }

        return DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
    }

    public static CharSequence getRelativeTimeSpanStringShort(Context context, long time) {
        long now = System.currentTimeMillis();
        long range = Math.abs(now - time);
        return formatDuration(context, range, time);
    }

    public static void loadImage(RequestManager with, Uri savingsDoc, AppCompatImageView docImageView) {
        Glide.with(context)
                .load(savingsDoc)
                .error(R.drawable.ic_alert)
                .override(100, 200)
                .centerCrop() // scale to fill the ImageView and crop any extra
                .into(docImageView);
    }

    public static String[] concat(String[] strings) {
        return new String[0];
    }

    public static class Profile22 {
        public static final int MAX_AVATAR_SIZE = 1280; //px, side of square
        public static final int MIN_AVATAR_SIZE = 100; //px, side of square
        public static final int MAX_NAME_LENGTH = 120;
    }
    public static String generateImageTitle(UploadImagePrefix prefix, String parentId) {
        if (parentId != null) {
            return prefix.toString() + parentId;
        }

        return prefix.toString() + new Date().getTime();
    }

    public static void loadImage( String url, AppCompatImageView imageView) {
        loadImage( url, imageView, DiskCacheStrategy.ALL);
    }

    public static void loadImage(String url, AppCompatImageView imageView, DiskCacheStrategy diskCacheStrategy) {

        Glide.with(context)
                .load(url)
                .diskCacheStrategy(diskCacheStrategy)
                .error(R.drawable.ic_alert)
                .override(100, 200)
                .centerCrop() // scale to fill the ImageView and crop any extra
                .into(imageView);
    }

    public static void loadImage( String url, ImageView imageView,
                                 RequestListener<Drawable> listener) {

        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .error(R.drawable.ic_alert)
                .override(100, 200)
                .listener(listener)
                .centerCrop()
                .into(imageView);

    }

    public static void loadImageCenterCrop( String url, ImageView imageView,
                                           int width, int height) {
        Glide.with(context)
                .load(url)
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_alert)
                .centerCrop()
                .into(imageView);
    }

    public static void loadImageCenterCrop( String url, ImageView imageView,
                                           int width, int height, RequestListener<Drawable> listener) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.ic_alert)
                .override(width, height)
                .listener(listener)
                .centerCrop()
                .into(imageView);

    }

    public static void loadImageCenterCrop( String url, ImageView imageView,
                                           RequestListener<Drawable> listener) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .error(R.drawable.ic_alert)
                .listener(listener)
                .centerCrop()
                .into(imageView);

    }

    @Nullable
    public static Bitmap loadBitmap(RequestManager with, String url, int width, int height) {
        try {


            return Glide.with(context).asBitmap()
                    .load(url)
                    .centerCrop()
                    .submit(width, height)
                    .get();
        } catch (Exception e) {
            LogUtil.logError(TAG, "getBitmapfromUrl", e);
            return null;
        }
    }

    public static void loadImageWithSimpleTarget( String url, SimpleTarget<Bitmap> simpleTarget) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(simpleTarget);
    }

    public static void loadLocalImage( Uri uri, ImageView imageView,
                                      RequestListener<Drawable> listener) {

        Glide.with(context)
                .load(uri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.ic_alert)
                .listener(listener)
                .skipMemoryCache(true)
                .fitCenter()
                .centerCrop()
                .into(imageView);

    }


    public static class SkyLightPackage33 {
        public static final int MAX_TEXT_LENGTH_IN_LIST = 300; //characters
        public static final int MAX_POST_TITLE_LENGTH = 255; //characters
        public static final int POST_AMOUNT_ON_PAGE = 10;
    }

    public static class Database {
        public static final int MAX_UPLOAD_RETRY_MILLIS = 60000; //1 minute
    }

    public static class PushNotification {
        public static final int LARGE_ICONE_SIZE = 256; //px
    }


    public static class General {
        public static final long DOUBLE_CLICK_TO_EXIT_INTERVAL = 3000; // in milliseconds
    }

    private static CharSequence formatDuration(Context context, long range, long time) {
        final Resources res = context.getResources();
        if (range >= DateUtils.WEEK_IN_MILLIS + DateUtils.DAY_IN_MILLIS) {
            return shortFormatEventDay(context, time);
        } else if (range >= DateUtils.WEEK_IN_MILLIS) {
            final int days = (int) ((range + (DateUtils.WEEK_IN_MILLIS / 2)) / DateUtils.WEEK_IN_MILLIS);
            return String.format(res.getString(R.string.duration_week_shortest), days);
        } else if (range >= DateUtils.DAY_IN_MILLIS) {
            final int days = (int) ((range + (DateUtils.DAY_IN_MILLIS / 2)) / DateUtils.DAY_IN_MILLIS);
            return String.format(res.getString(R.string.duration_days_shortest), days);
        } else if (range >= DateUtils.HOUR_IN_MILLIS) {
            final int hours = (int) ((range + (DateUtils.HOUR_IN_MILLIS / 2)) / DateUtils.HOUR_IN_MILLIS);
            return String.format(res.getString(R.string.duration_hours_shortest), hours);
        } else if (range >= NOW_TIME_RANGE) {
            final int minutes = (int) ((range + (DateUtils.MINUTE_IN_MILLIS / 2)) / DateUtils.MINUTE_IN_MILLIS);
            return String.format(res.getString(R.string.duration_minutes_shortest), minutes);
        } else {
            return res.getString(R.string.now);
        }
    }

    private static String shortFormatEventDay(Context context, long time) {
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_MONTH;
        Formatter f = new Formatter(new StringBuilder(50), Locale.getDefault());
        return DateUtils.formatDateRange(context, f, time, time, flags).toString();
    }

    public static String getDeviceId(Context c) {
        //return Settings.Secure.getString(c.getContentResolver(), Settings.Secure.ANDROID_ID);
        return null;
    }
    public static int getDisplayWidth(Context context) {
        return getSize(context).x;
    }

    public static int getDisplayHeight(Context context) {
        return getSize(context).y;
    }

    private static Point getSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static void hide_keyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public static String convertChargeRequestPayloadToJson(Pay_load body) {


        Gson gson = new Gson();
        Type type = new TypeToken<Pay_load>() {
        }.getType();
        return gson.toJson(body, type);
    }

    public static List<Meta> pojofyMetaString(String meta) {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Meta>>() {
            }.getType();
            return gson.fromJson(meta, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static List<SubAccounts> pojofySubaccountString(String subaccount) {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<List<SubAccounts>>() {
            }.getType();
            return gson.fromJson(subaccount, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String stringifyMeta(List<Meta> meta) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Meta>>() {
        }.getType();
        return gson.toJson(meta, type);
    }

    public static String stringifySubaccounts(List<SubAccounts> subAccounts) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<SubAccounts>>() {
        }.getType();
        return gson.toJson(subAccounts, type);
    }

    public static byte[] RSAEncrypt(String plaintext) {
        PublicKey key = getKey("baA/RgjURU3I0uqH3iRos3NbE8fT+lP8SDXKymsnfdPrMQAEoMBuXtoaQiJ1i5tuBG9EgSEOH1LAZEaAsvwClw==");
        byte[] ciphertext = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            ciphertext = cipher.doFinal(plaintext.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ciphertext;
    }

    public static PublicKey getKey(String key) {
        try {
            byte[] byteKey = Base64.decode(key.getBytes(Charset.forName("UTF-16")), Base64.DEFAULT);
            X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");

            return kf.generatePublic(X509publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /*public static String encryptRef(String key, String ref) {
        try {
            return AESCrypt.encrypt(key, ref);
        } catch (GeneralSecurityException e) {
            return null;
        }
    }*/

    /*public static String decryptRef(String key, String encryptedRef) {
        try {

            return AESCrypt.decrypt(key, encryptedRef);
        } catch (GeneralSecurityException e) {
            return null;
        }
    }*/

    private static String getMd5(String md5) throws Exception {

        MessageDigest md = MessageDigest.getInstance(MD5);
        byte[] array = md.digest(md5.getBytes(CHARSET_NAME));
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100), 1, 3);
        }
        return sb.toString();
    }

    public static String obfuscateCardNumber(String first6, String last4) {

        int cardNoLength = first6.length() + last4.length();
        if (cardNoLength < 10) {
            return first6 + last4;
        } else {

            int othersLength = 6;

            String exes = "";
            for (int i = 0; i < othersLength; i++) {
                exes += "X";
            }
            return first6 + exes + last4;
        }
    }

    public static String spacifyCardNumber(String cardNo) {

        cardNo = cardNo.replaceAll("\\s", "");
        String spacified = "";

        int len = cardNo.length();

        int nChunks = len / 4;
        int rem = len % 4;


        for (int i = 0; i < nChunks; i++) {
            spacified += cardNo.substring(i * 4, (i * 4) + 4) + " ";
        }


        spacified += cardNo.substring(nChunks * 4, (nChunks * 4) + rem);

        return spacified;

    }

    public static boolean isValidLuhnNumber(String number) {

        try {// Verify that string contains only numbers
            Long parsed = Long.parseLong(number);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        String reversedNumber = new StringBuffer(number).reverse().toString();
        char[] reversedCharArray = reversedNumber.toCharArray();
        int luhnSum = 0;

        // Double the value of every second digit from the right
        for (int index = 1; index < number.length(); index += 2) {
            int doubleResult = Character.getNumericValue(reversedCharArray[index]) * 2;
            if (doubleResult > 9) {// If result has double digits, sum digits
                doubleResult = 1 + (doubleResult - 10);
            }

            reversedCharArray[index] = Character.forDigit(doubleResult, 10);
        }

        // Sum all digits
        for (int index = 0; index < number.length(); index++) {
            luhnSum += Character.getNumericValue(reversedCharArray[index]);
        }

        // For valiid Luhn number, sum result should be a multiple of 10
        return luhnSum % 10 == 0;
    }

    public String generateUUID(int length) {
        String returnvalue = UUID.randomUUID().toString().replaceAll("-", "");
        return returnvalue;
    }

    public String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; 1 < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    public String generateRef(int length) {
        return generateUUID(length);
    }


    private static Map<String, Typeface> TYPEFACE = new HashMap<String, Typeface>();

    public static int getToolbarHeight(Context context) {
        int height = (int) context.getResources().getDimension(
                R.dimen.abc_action_bar_default_height_material);
        return height;
    }

    public static int getStatusBarHeight(Context context) {
        int height = (int) context.getResources().getDimension(
                R.dimen.statusbar_size);
        return height;
    }
    public static Drawable tintMyDrawable(Drawable drawable, int color) {
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, color);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    public static String getRealPathFromURI(Uri contentUri, Context mContext) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(mContext, contentUri, proj,
                null, null, null);
        Cursor cursor = loader.loadInBackground();
        assert cursor != null;
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
    public static String getSkylightSavings(int savingsNumber) {
        long packageCount = (savingsNumber ) % 31;

        String s = (packageCount < 10) ? "0" + packageCount : "" + packageCount;

        String packageContributions = "";
        if (packageCount > 0) {
            packageContributions = packageCount + "---" + savingsNumber + ":" + s;
        } else {
            packageContributions = packageCount + "---" ;
        }
        return packageContributions;
    }

    public static String getDuration(long milliseconds) {
        long sec = (milliseconds / 1000) % 60;
        long min = (milliseconds / (60 * 1000)) % 60;
        long hour = milliseconds / (60 * 60 * 1000);

        String s = (sec < 10) ? "0" + sec : "" + sec;
        String m = (min < 10) ? "0" + min : "" + min;
        String h = "" + hour;

        String time = "";
        if (hour > 0) {
            time = h + ":" + m + ":" + s;
        } else {
            time = m + ":" + s;
        }
        return time;
    }
    public static String stringToDate(String birthdayString) {
        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            if (birthdayString.startsWith("/")) {
                birthdayString = birthdayString.replaceFirst("/", "1990");
            }
            date = format.parse(birthdayString);
            if (date != null) {
                date.setYear(date.getYear() + 1900);
            }
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(date);
    }
    public static String stringToDayAndMonth(String birthdayString,String[] mDateSplit) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
         int mMonth, mDay;
        mDateSplit=null;

        if (birthdayString.startsWith("/")) {
            //birthdayString = birthdayString.replaceFirst("/", "1990");
            mDateSplit = birthdayString.split("/");
            mDay = Integer.parseInt(mDateSplit[0]);
            mMonth = Integer.parseInt(mDateSplit[1]);
            birthdayString=mDay+"/"+mMonth;
        }
        return birthdayString;
    }
    private static long converTimeStringINToMillis(String time) {

        long milliseconds = 0;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date;
            date = sdf.parse(time);
            milliseconds = date.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            milliseconds = 0;
            e.printStackTrace();
        }

        return milliseconds;
    }

    public static String  setLastSeenTime(String time){


        long milliseconds = Math.abs(System.currentTimeMillis() - converTimeStringINToMillis(time));

        String lastSeen = "";

        int seconds = (int)milliseconds/1000;
        if(seconds < 60)
            lastSeen = String.valueOf(seconds) + "sec ago";
        else if(seconds >60 && seconds < 3600)
            lastSeen = String.valueOf((int)seconds/60) + " min ago";
        else if(seconds > 3600 && seconds < 86400)
            lastSeen = String.valueOf((int)seconds/3600) + " hours ago";
        else if(seconds > 86400 && seconds < 172800)
            lastSeen =" Yesterday";
        else if(seconds > 172800 && seconds < 2592000)
            lastSeen = String.valueOf((int)(seconds/(24*3600))) + " days ago";
        else if(seconds > 2592000)
            lastSeen = String.valueOf((int)(seconds/(30*24*3600))) + " months ago";


        return lastSeen;


    }

    public static boolean hasYearOfBirth(String birthdayString) {
        return ! birthdayString.startsWith("-");
    }

    public static String getDateSuffix(int date) {
        if (date == 11 || date == 12 || date == 13) {
            return "th";
        } else if (date % 10 == 1) {
            return "st";
        } else if (date % 10 == 2) {
            return "nd";
        } else if (date % 10 == 3) {
            return "rd";
        } else {
            return "th";
        }
    }

    public static int getHighlightColor(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        if (prefs.getString(context.getResources().getString(R.string.pref_theme_key_elelenwo), "0").equals("0")) {
            return R.color.colorPrimaryDark1;
        } else if (prefs.getString(context.getResources().getString(R.string.pref_theme_key_unyeada), "0").equals("1")) {
            return R.color.pinky2;
        } else if (prefs.getString(context.getResources().getString(R.string.pref_theme_key_okoroete), "0").equals("2")) {
            return R.color.blue_dark;
        } else {
            return R.color.purple_blue;
        }
    }

    public static boolean isStringEmpty(String s) {
        return s == null || s.equals("");
    }

    // Helper method for getting exact pixel size for device from density independent pixels
    public static int getPixelsFromDP(Context context, int px) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, r.getDisplayMetrics());
    }

    public static int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public static void switchFragmentWithAnimation(int id, Fragment fragment,
                                                   FragmentActivity activity, String TAG, AnimationType transitionStyle) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        if (transitionStyle != null) {
            switch (transitionStyle) {
                case SLIDE_DOWN:

                    // Exit from down
                    fragmentTransaction.setCustomAnimations(R.anim.slide_up,
                            R.anim.slide_down);

                    break;

                case SLIDE_UP:

                    // Enter from Up
                    fragmentTransaction.setCustomAnimations(R.anim.slide_in_up,
                            R.anim.slide_out_up);

                    break;

                case SLIDE_LEFT:

                case SLIDE_IN_SLIDE_OUT:

                    // Enter from left
                    fragmentTransaction.setCustomAnimations(R.anim.slide_left,
                            R.anim.slide_out_left);

                    break;

                // Enter from right
                case SLIDE_RIGHT:
                    fragmentTransaction.setCustomAnimations(R.anim.slide_right,
                            R.anim.slide_out_right);

                    break;

                case FADE_IN:
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in,
                            R.anim.fade_out);

                case FADE_OUT:
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in,
                            R.anim.donot_move);

                    break;

                default:
                    break;
            }
        }

        CURRENT_TAG = TAG;

        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.addToBackStack(TAG);
        fragmentTransaction.commit();
    }

    public static void switchContent(int id, String TAG,
                                     FragmentActivity baseActivity, AnimationType transitionStyle) {

        Fragment fragmentToReplace = null;

        FragmentManager fragmentManager = baseActivity
                .getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // If our current fragment is null, or the new fragment is different, we
        // need to change our current fragment
        if (CURRENT_TAG == null || !TAG.equals(CURRENT_TAG)) {

            if (transitionStyle != null) {
                switch (transitionStyle) {
                    case SLIDE_DOWN:
                        // Exit from down
                        transaction.setCustomAnimations(R.anim.slide_up,
                                R.anim.slide_down);

                        break;
                    case SLIDE_UP:
                        // Enter from Up
                        transaction.setCustomAnimations(R.anim.slide_in_up,
                                R.anim.slide_out_up);
                        break;
                    case SLIDE_LEFT:
                    case SLIDE_IN_SLIDE_OUT:
                        // Enter from left
                        transaction.setCustomAnimations(R.anim.slide_left,
                                R.anim.slide_out_left);
                        break;
                    // Enter from right
                    case SLIDE_RIGHT:
                        transaction.setCustomAnimations(R.anim.slide_right,
                                R.anim.slide_out_right);
                        break;
                    case FADE_IN:
                        transaction.setCustomAnimations(R.anim.fade_in,
                                R.anim.fade_out);
                    case FADE_OUT:
                        transaction.setCustomAnimations(R.anim.fade_in,
                                R.anim.donot_move);
                        break;
                    default:
                        break;
                }
            }

            // Try to find the fragment we are switching to
            Fragment fragment = fragmentManager.findFragmentByTag(TAG);

            // If the new fragment can't be found in the manager, create a new
            // one
            if (fragment == null) {

                if (TAG.equals(HOME_FRAGMENT)) {
                    //fragmentToReplace = new HomeFragment();
                } else if (TAG.equals(SHOPPING_LIST_TAG)) {
                    //fragmentToReplace = new FormFragment();
                } else if (TAG.equals(SETTINGS_FRAGMENT_TAG)) {
                    //fragmentToReplace = new SettingsFragment();
                } else if (TAG.equals(CONTACT_US_FRAGMENT)) {
                    //fragmentToReplace = new ContactSkylightFragment();
                } else if (TAG.equals(PRODUCT_OVERVIEW_FRAGMENT_TAG)) {
                   // fragmentToReplace = new OrderFragment();
                }

            } else

            {
                if (TAG.equals(HOME_FRAGMENT)) {
                   // fragmentToReplace = (HomeFragment) fragment;
                } else if (TAG.equals(SHOPPING_LIST_TAG)) {
                   // fragmentToReplace = (FormFragment) fragment;
                } else if (TAG.equals(PRODUCT_OVERVIEW_FRAGMENT_TAG)) {
                    //fragmentToReplace = (SettingsFragment) fragment;
                } else if (TAG.equals(SETTINGS_FRAGMENT_TAG)) {
                    //fragmentToReplace = (SettingsFragment) fragment;
                } else if (TAG.equals(CONTACT_US_FRAGMENT)) {
                   // fragmentToReplace = (ContactSkylightFragment) fragment;
                }
            }

            CURRENT_TAG = TAG;

            if (fragmentToReplace != null) {
                transaction.replace(id, fragmentToReplace, TAG);
            }
            transaction.commit();

        }


    }

    public static void vibrate(Context context) {
        // Get instance of Vibrator from current Context and Vibrate for 400
        // milliseconds
        ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE))
                .vibrate(100);
    }

    public static String getVersion(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            return String.valueOf(pInfo.versionCode) + " " + pInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            return "1.0.1";
        }
    }

    public static Typeface getFonts(Context context, String fontName) {
        Typeface typeface = TYPEFACE.get(fontName);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), "font/"
                    + fontName);
            TYPEFACE.put(fontName, typeface);
        }
        return typeface;
    }

    public static String getDateSuffix(String dob) {
        return null;
    }


    public enum AnimationType {
        SLIDE_LEFT, SLIDE_RIGHT, SLIDE_UP, SLIDE_DOWN, FADE_IN, SLIDE_IN_SLIDE_OUT, FADE_OUT
    }

    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkSMSPermission(final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.SEND_SMS)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("SMS permission is necessary");
                    alertBuilder.setPositiveButton("yes to Awajima", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkStoragePermission(final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkCameraPermission(final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Camera permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    public Utils() {
    }


    static {
        BAY_AREA_LANDMARKS.put("Unyeada", new LatLng(4.5267, 7.4462));

        BAY_AREA_LANDMARKS.put("Okoroete", new LatLng(4.54077,7.74858));
    }


}
