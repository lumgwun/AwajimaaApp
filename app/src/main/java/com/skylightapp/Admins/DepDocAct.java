package com.skylightapp.Admins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.google.gson.Gson;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.squareup.picasso.Picasso;

public class DepDocAct extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private AppCompatButton chooseImageBtn;
    private AppCompatButton uploadBtn;
    //private AppCompatEditText nameEditText;
    private AppCompatEditText edtDocID;
    private ImageView chosenImageView;
    private ProgressBar uploadProgressBar;
    SQLiteDatabase sqLiteDatabase;

    private Uri mImageUri;

    private Bundle docBundle;
    int adminDepositID;
    DBHelper dbHelper;
    private AdminBankDeposit adminBankDeposit;
    Gson gson,gson1;
    String json,json1;
    private Profile managerProfile;
    SharedPreferences userPreferences;
    private  AdminUser adminUser;
    private static final String PREF_NAME = "skylight";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dep_doc);
        setTitle("Admin Deposit Doc.");
        adminUser=new AdminUser();
        gson1 = new Gson();
        gson = new Gson();
        managerProfile= new Profile();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        managerProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastAdminProfileUsed", "");
        adminUser = gson1.fromJson(json, AdminUser.class);
        chooseImageBtn = findViewById(R.id.button_choose_image);
        uploadBtn = findViewById(R.id.uploadBtn);
        mImageUri=null;
        sqLiteDatabase = dbHelper.getWritableDatabase();
        adminBankDeposit= new AdminBankDeposit();
        docBundle=getIntent().getExtras();
        dbHelper = new DBHelper(this);
        //nameEditText = findViewById(R.id.nameEditText);
        edtDocID = findViewById ( R.id.edtADepositDoc );
        chosenImageView = findViewById(R.id.chosenImageView);
        uploadProgressBar = findViewById(R.id.progress_bar);
        if(docBundle !=null){
            adminBankDeposit=docBundle.getParcelable("AdminBankDeposit");
        }
        if(adminBankDeposit !=null){
            adminDepositID=adminBankDeposit.getDepositID();
        }


        chooseImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mImageUri !=null){
                    sqLiteDatabase = dbHelper.getWritableDatabase();
                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                        dbHelper.openDataBase();
                        dbHelper.updateAdminDeposit(adminDepositID,mImageUri);

                    }
                }else{
                    Toast.makeText(DepDocAct.this, "Deposit Document can not be Empty", Toast.LENGTH_LONG).show();

                }

            }
        });
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Glide.with(this).load(mImageUri)
                    .into(chosenImageView);
            Glide.with(this).load(R.drawable.ic_admin_user)
                    .into(chosenImageView);

        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(AdminBankDeposit adminBankDeposit, long adminDepositID) {
        if (mImageUri != null) {
            /*StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            uploadProgressBar.setVisibility(View.VISIBLE);
            uploadProgressBar.setIndeterminate(true);

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    uploadProgressBar.setVisibility(View.VISIBLE);
                                    uploadProgressBar.setIndeterminate(false);
                                    uploadProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(DepDocAct.this, "Admin Deposit Doc.  Upload successful", Toast.LENGTH_LONG).show();

                            String uploadId = mDatabaseRef.push().getKey();
                            if (uploadId != null) {
                                mDatabaseRef.child(uploadId).setValue(adminBankDeposit);
                            }

                            uploadProgressBar.setVisibility(View.INVISIBLE);
                            openImagesActivity ();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            uploadProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(DepDocAct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            uploadProgressBar.setProgress((int) progress);
                        }
                    });*/
        } else {
            Toast.makeText(this, "You haven't Selected Any file selected", Toast.LENGTH_SHORT).show();
        }
    }
    private void openImagesActivity(){
        Intent intent = new Intent(this, AdminDrawerActivity.class);
        startActivity(intent);

    }
}