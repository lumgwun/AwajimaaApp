package com.skylightapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.ProfDAO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static androidx.core.content.FileProvider.getUriForFile;
import static com.skylightapp.SignUpAct.PICTURE_REQUEST_CODE;

public class CameraActivity extends AppCompatActivity {
    private static final String TAG = CameraActivity.class.getSimpleName();
    public static final String INTENT_IMAGE_PICKER_OPTION = "image_picker_option";
    public static final String INTENT_ASPECT_RATIO_X = "aspect_ratio_x";
    public static final String INTENT_ASPECT_RATIO_Y = "aspect_ratio_Y";
    public static final String INTENT_LOCK_ASPECT_RATIO = "lock_aspect_ratio";
    public static final String INTENT_IMAGE_COMPRESSION_QUALITY = "compression_quality";
    public static final String INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT = "set_bitmap_max_width_height";
    public static final String INTENT_BITMAP_MAX_WIDTH = "max_width";
    public static final String INTENT_BITMAP_MAX_HEIGHT = "max_height";

    public static final int REQUEST_IMAGE_CAPTURE = 0;

    public static final int REQUEST_GALLERY_IMAGE = 1;
    public static final int REQUEST_IMAGE = 100;

    private boolean lockAspectRatio = false, setBitmapMaxWidthHeight = false;
    private int ASPECT_RATIO_X = 16, ASPECT_RATIO_Y = 9, bitmapMaxWidth = 1000, bitmapMaxHeight = 1000;
    private int IMAGE_COMPRESSION = 80;
    public static String fileName;
    private SharedPreferences sharedpreferences;
    PrefManager prefManager;
    private Gson gson;
    private String json;

    private Profile userProfile;
    private Customer customer;

    public interface PickerOptionListener {
        void onTakeCameraSelected();

        void onChooseGallerySelected();
    }
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private static final int CAMERA_PERMISSION_CODE = 100;
    public static final int STORAGE_PERMISSION_CODE = 101;
    public static final int INTERNET_PERMISSION_CODE = 901;
    byte img[];

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,

    };

    protected ImageView photo;
    private static final String PREF_NAME = "skylight";
    String userChoosenTask;
    View view;
    Context context;
    PickerOptionListener listener;
    int profileID,customerID;
    Bundle mediaFileBundle;
    private static boolean isPersistenceEnabled = false;
    private ProfDAO profileDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (!hasPermissions(CameraActivity.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(CameraActivity.this, PERMISSIONS, PERMISSION_ALL);
        }
        gson = new Gson();
        userProfile=new Profile();
        sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        mediaFileBundle = new Bundle();

        //Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        customer= new Customer();
        Dexter.withContext(this);
        onProfileImageClick();
        Intent intent = getIntent();
        if(intent !=null){
            try {
                profileID=intent.getIntExtra("PROFILE_ID",0);
                customerID=intent.getIntExtra("CUSTOMER_ID", 0);
                customer=intent.getParcelableExtra("Customer");
                userProfile=intent.getParcelableExtra("Profile");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }


        }else {
            if(userProfile !=null){
                profileID=userProfile.getPID();

            }
            if(customer !=null){
                customerID=customer.getCusUID();

            }
            Toast.makeText(this, getString(R.string.toast_image_intent_null), Toast.LENGTH_LONG).show();

        }

    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    void onProfileImageClick() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Picture Source");
        fileName = "SkylightPix"+ System.currentTimeMillis() + ".jpg";
        builder.setItems(new CharSequence[]
                        {"Choose Image from gallery","Take picture"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 1:
                                chooseImageFromGallery();
                                break;

                            case 2:
                                takeCameraImage();
                                break;

                        }
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        builder.create().show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = getIntent();
        if(intent !=null){
            try {
                profileID=intent.getIntExtra("PROFILE_ID",0);
                customerID=intent.getIntExtra("CUSTOMER_ID", 0);
                customer=intent.getParcelableExtra("Customer");
                userProfile=intent.getParcelableExtra("Profile");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }


        }else {
            if(userProfile !=null){
                profileID=userProfile.getPID();

            }
            if(customer !=null){
                customerID=customer.getCusUID();

            }
            Toast.makeText(this, getString(R.string.toast_image_intent_null), Toast.LENGTH_LONG).show();

        }

        if (requestCode == PICTURE_REQUEST_CODE && data != null) {
            Uri uri = null;
            if (resultCode == RESULT_OK) {
                uri = data.getData();
                DBHelper dbHelper = new DBHelper(this);
                profileDao = new ProfDAO(this);

                profileDao.insertProfilePicture(profileID,customerID,uri);
                Toast.makeText(this, "SUCCESS " , Toast.LENGTH_SHORT).show();
                setResultOk(uri);
            }


            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "CANCELLED " , Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CameraActivity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    private void takeCameraImage() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            fileName = "OurSkylightAppPix"+ System.currentTimeMillis() + ".jpg";
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            takePictureIntent.setType("image/*");
                            //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getCacheImagePath(fileName));
                            takePictureIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                            takePictureIntent.putExtra(CameraActivity.INTENT_IMAGE_PICKER_OPTION, CameraActivity.REQUEST_IMAGE_CAPTURE);
                            takePictureIntent.putExtra(CameraActivity.INTENT_LOCK_ASPECT_RATIO, true);
                            takePictureIntent.putExtra(CameraActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
                            takePictureIntent.putExtra(CameraActivity.INTENT_ASPECT_RATIO_Y, 1);
                            takePictureIntent.putExtra(CameraActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
                            takePictureIntent.putExtra(CameraActivity.INTENT_BITMAP_MAX_WIDTH, 100);
                            takePictureIntent.putExtra(CameraActivity.INTENT_BITMAP_MAX_HEIGHT, 100);
                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                            }
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();


    }

    private void chooseImageFromGallery() {
        Dexter.withActivity(this)

                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            //pickPhoto.putExtra(MediaStore.EXTRA_OUTPUT, getCacheImagePath(fileName));
                            pickPhoto.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + "SkylightApp")),"image/*");
                            pickPhoto.putExtra(CameraActivity.INTENT_IMAGE_PICKER_OPTION, CameraActivity.REQUEST_GALLERY_IMAGE);
                            pickPhoto.putExtra(CameraActivity.INTENT_LOCK_ASPECT_RATIO, true);
                            pickPhoto.putExtra(CameraActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
                            pickPhoto.putExtra(CameraActivity.INTENT_ASPECT_RATIO_Y, 1);
                            startActivityForResult(pickPhoto, REQUEST_GALLERY_IMAGE);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();


    }

    private void setResultOk(Uri imagePath) {
        Intent intent = new Intent();
        intent.putExtra("path", imagePath);
        setResult(AppCompatActivity.RESULT_OK, intent);
        finish();
    }

    private void setResultCancelled() {
        Intent intent = new Intent();
        setResult(AppCompatActivity.RESULT_CANCELED, intent);
        finish();
    }
    public static void startPhotoZoom(AppCompatActivity context, int requestCode, Uri fileUri, int width, int height) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(fileUri, "image/*");

        intent.putExtra("crop", "true");

        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("return-data", true);
        Log.i(TAG, "startPhotoZoom"+ fileUri +" uri");
        //toActivity(context, intent, requestCode);
    }


    private Uri getCacheImagePath(String fileName) {
        File path = new File(getExternalCacheDir(), "camera");
        if (!path.exists()) path.mkdirs();
        File image = new File(path, fileName);
        return FileProvider.getUriForFile(CameraActivity.this, BuildConfig.APPLICATION_ID + ".UserContentProvider", image);
    }

    private static String queryName(ContentResolver resolver, Uri uri) {
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }

    public static void clearCache(Context context) {
        File path = new File(context.getExternalCacheDir(), "camera");
        if (path.exists() && path.isDirectory()) {
            for (File child : Objects.requireNonNull(path.listFiles())) {
                child.delete();
            }
        }
    }


    private void onCaptureImageResult(Intent data) throws IOException {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            //destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}