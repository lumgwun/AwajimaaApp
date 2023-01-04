package com.skylightapp.MapAndLoc;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.ContentLoadingProgressBar;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MarketClasses.ResourceUtils;
import com.skylightapp.R;
import com.skylightapp.SignUpAct;
import com.skylightapp.SuperAdmin.Awajima;

import net.qiujuer.genius.ui.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ERMediaUAct extends AppCompatActivity {
    private static final String PREF_NAME = "awajima";
    public static final int PICTURE_REQUEST_CODE = 1013;
    ContentLoadingProgressBar progressBar;
    int numMessages;
    public static final String NOTIFICATION_CHANNEL_ID = "871" ;
    Gson gson, gson1,gson3,gson6;
    String json, json1, json3,json6nIN,name;
    Profile userProfile, customerProfile, lastProfileUsed;
    private Awajima awajima;
    private boolean locationPermissionGranted;
    private String picturePath;
    private Uri mImageUri;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final String REQUIRED = "Required";
    private static final int RESULT_CAMERA_CODE = 11560;
    int PERMISSION_ALL = 189;
    private AppCompatButton btnUpload, btnSend;
    private ImageView img;
    String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    int PERMISSION_ALL33 = 2435;
    String[] PERMISSIONS33 = {
            Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.SEND_SMS
    };
    ActivityResultLauncher<Intent> mGetPixContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            if (result.getData() != null) {
                                Intent data = result.getData();
                                mImageUri = data.getData();
                                if (mImageUri != null) {
                                    img.setImageBitmap(getScaledBitmap(mImageUri));
                                } else {
                                    Toast.makeText(ERMediaUAct.this, "Error getting Photo",
                                            Toast.LENGTH_SHORT).show();
                                }


                            }

                            Toast.makeText(ERMediaUAct.this, "Image picking returned successful", Toast.LENGTH_SHORT).show();
                            //doProcessing();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(ERMediaUAct.this, "Activity canceled", Toast.LENGTH_SHORT).show();
                            //finish();
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + result.getResultCode());
                    }
                }
                /*@Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        finish();
                    }
                }*/
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_emerg_media_u);
        gson = new Gson();
        gson1 = new Gson();
        gson3= new Gson();
        userProfile= new Profile();
        btnUpload = findViewById(R.id.upload_mediaF);
        img = findViewById(R.id.img_mediaF);
        btnSend = findViewById(R.id.send_mediaF);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasPermissions(ERMediaUAct.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(ERMediaUAct.this, PERMISSIONS, PERMISSION_ALL);
                }

                final PopupMenu popup = new PopupMenu(ERMediaUAct.this, btnUpload);
                popup.getMenuInflater().inflate(R.menu.profile, popup.getMenu());
                setTitle("Photo selection in Progress...");

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.Camera) {
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, RESULT_CAMERA_CODE);

                        }

                        if (item.getItemId() == R.id.Gallery) {
                            Intent i = new Intent(
                                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                            startActivityForResult(i, RESULT_LOAD_IMAGE);
                        }
                        if (item.getItemId() == R.id.cancel_action) {
                            setTitle("Awajima onBoarding");
                        }

                        return true;
                    }
                });
                popup.show();//showing popup menu


            }

        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnSend.setOnClickListener(this::sendMediaFiles);
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
    private Bitmap getScaledBitmap(Uri uri) {
        Bitmap thumb = null;
        try {
            ContentResolver cr = getContentResolver();
            InputStream in = cr.openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            thumb = BitmapFactory.decodeStream(in, null, options);
        } catch (FileNotFoundException e) {
            Toast.makeText(ERMediaUAct.this, "File not found", Toast.LENGTH_SHORT).show();
        }
        return thumb;
    }

    public void sendMediaFiles(View view) {
    }

    public void uploatMediaF(View view) {
    }
}