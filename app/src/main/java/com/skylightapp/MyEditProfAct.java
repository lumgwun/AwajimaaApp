package com.skylightapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.UtilsExtra;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.MarketClasses.Market;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class MyEditProfAct extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "MyEditProfAct";
    private AppCompatEditText etProfileFname, etProfileLName, etProfileEmail,
            etProfileNumber, etProfileAddress,
            etCurrentPassword, etNewPassword, etRetypePassword;
    private AppCompatImageView ivProfile, btnProfileEmailInfo;
    private AppCompatButton tvProfileSubmit;
    private DBHelper dbHelper;
    private Uri uri = null;
    private String profileImageData, profileImageFilePath, loginType;
    private Bitmap profilePicBitmap;
    private PrefManager preferenceHelper;
    private static final String PREF_NAME = "awajima";
    public static final int TAKE_PHOTO = 5325;
    public static final int FROM_GALLERY = 925;
    public static final int REQUEST_CROP = 25;
    SharedPreferences userPreferences;
    Gson gson;
    String json,SharedPrefRole,SharedPrefUserName,SharedPrefUser,SharedPrefPassword,password,retypePassword,firstName,lastName,phoneNo,address, email;
    int profileUID,SharedPrefProfileID,SharedPrefCusID,marketProfID;
    private Profile userProfile,marketProf;
    private ProfDAO profDAO;
    private Uri contentURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_edit_prof);
        setTitle("My Profile");
        profDAO= new ProfDAO(this);
        etProfileFname =  findViewById(R.id.etProfileFName);
        etProfileLName = findViewById(R.id.etProfileLName);
        etProfileEmail =  findViewById(R.id.etProfileEmail);
        etCurrentPassword =  findViewById(R.id.etCurrentPassword);
        etNewPassword =  findViewById(R.id.etNewPassword);
        etRetypePassword =  findViewById(R.id.etRetypePassword);
        etProfileNumber = findViewById(R.id.etProfileNumber);
        etProfileAddress =  findViewById(R.id.etProfileAddress);
        tvProfileSubmit =  findViewById(R.id.tvProfileSubmit);
        ivProfile = findViewById(R.id.ivProfileProfile);
        btnProfileEmailInfo =  findViewById(R.id.btnProfileEmailInfo);
        btnProfileEmailInfo.setOnClickListener(this);
        ivProfile.setOnClickListener(this);
        tvProfileSubmit.setOnClickListener(this);

        preferenceHelper = new PrefManager(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvProfileSubmit:
                if (tvProfileSubmit
                        .getText()
                        .toString()
                        .equals(getResources()
                                .getString(R.string.text_edit_profile))) {
                    enableViews();
                    etProfileFname.requestFocus();
                    tvProfileSubmit.setText(getResources().getString(
                            R.string.text_update_profile));
                } else {
                    onUpdateButtonClick();
                }
                break;
            case R.id.ivProfileProfile:
                showPictureDialog();
                break;

            default:
                break;
        }
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle(getResources().getString(
                R.string.text_choosepicture));
        String[] pictureDialogItems = {"from Gallery", "From Camera" };

        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {

                            case 0:
                                choosePhotoFromGallary();
                                break;

                            case 1:
                                takePhotoFromCamera();
                                break;

                        }
                    }
                });
        pictureDialog.show();
    }

    private void choosePhotoFromGallary() {

        // Intent intent = new Intent();
        // intent.setType("image/*");
        // intent.setAction(Intent.ACTION_GET_CONTENT);
        // intent.addCategory(Intent.CATEGORY_OPENABLE);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, FROM_GALLERY);

    }

    private void takePhotoFromCamera() {
        Calendar cal = Calendar.getInstance();
        File file = new File(Environment.getExternalStorageDirectory(),
                (cal.getTimeInMillis() + ".jpg"));

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        uri = Uri.fromFile(file);
        Intent cameraIntent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(cameraIntent, TAKE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FROM_GALLERY) {
            if (data != null) {
                contentURI = data.getData();
                beginCrop(contentURI);

            }

        } else if (requestCode == TAKE_PHOTO) {

            if (uri != null) {
                profileImageFilePath = uri.getPath();
                beginCrop(uri);

            } else {
                Toast.makeText(
                        this,
                        getResources().getString(
                                R.string.toast_unable_to_selct_image),
                        Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == REQUEST_CROP) {

            handleCrop(resultCode, data);
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null,
                null, null);

        if (cursor == null) { // Source is Dropbox or other similar local file
            // path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor
                    .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    private void beginCrop(Uri source) {
        Uri outputUri = Uri.fromFile(new File(Environment
                .getExternalStorageDirectory(), (Calendar.getInstance()
                .getTimeInMillis() + ".jpg")));
        //new Crop(source).output(outputUri).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        /*if (resultCode == RESULT_OK) {
            profileImageData = getRealPathFromURI(Crop.getOutput(result));
            ivProfile.setImageURI(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(),
                    Toast.LENGTH_SHORT).show();
        }*/
    }

    private void disableViews() {
        etProfileFname.setEnabled(false);
        etProfileLName.setEnabled(false);
        etProfileEmail.setEnabled(false);
        etProfileNumber.setEnabled(false);
        // etProfileBio.setEnabled(false);
        // etProfileAddress.setEnabled(false);
        // etProfileZipcode.setEnabled(false);
        etCurrentPassword.setEnabled(false);
        etNewPassword.setEnabled(false);
        etRetypePassword.setEnabled(false);
        ivProfile.setEnabled(false);
    }

    private void enableViews() {
        etProfileFname.setEnabled(true);
        etProfileLName.setEnabled(true);
        etProfileEmail.setEnabled(true);
        etProfileNumber.setEnabled(true);
        etProfileAddress.setEnabled(true);
        etCurrentPassword.setEnabled(true);
        etNewPassword.setEnabled(true);
        etRetypePassword.setEnabled(true);
        ivProfile.setEnabled(true);
    }

    private void setData() {
        dbHelper = new DBHelper(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        userProfile=new Profile();
        marketProf= new Profile();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUser=userPreferences.getString("machine", "");
        SharedPrefRole = userPreferences.getString("PROFILE_ROLE", "");
        if (userProfile != null) {
            etProfileFname.setText(userProfile.getProfileFirstName());
            etProfileLName.setText(userProfile.getProfileLastName());
            etProfileEmail.setText(userProfile.getProfileEmail());
            etProfileNumber.setText(userProfile.getProfilePhoneNumber());
            etProfileAddress.setText(userProfile.getProfileAddress());

        }

    }

    private void onUpdateButtonClick() {
        profDAO= new ProfDAO(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);

        firstName = etProfileFname.getText().toString().trim();
        lastName = etProfileLName.getText().toString().trim();
        phoneNo = etProfileNumber.getText().toString();
        password = etNewPassword.getText().toString();
        address = etProfileAddress.getText().toString().trim();
        SharedPreferences.Editor editor = userPreferences.edit();
        retypePassword = Objects.requireNonNull(etRetypePassword.getText()).toString();
        email = etProfileEmail.getText().toString().trim();

        if (etProfileFname.getText().length() == 0) {
            UtilsExtra.showToast(
                    getResources().getString(R.string.text_enter_name), this);
            return;
        } else if (etProfileLName.getText().length() == 0) {
            UtilsExtra.showToast(
                    getResources().getString(R.string.text_enter_lname), this);
            return;
        } else if (etProfileEmail.getText().length() == 0) {
            UtilsExtra.showToast(
                    getResources().getString(R.string.text_enter_email), this);
            return;
        } else if (!UtilsExtra.eMailValidation(etProfileEmail.getText()
                .toString())) {
            UtilsExtra.showToast(
                    getResources().getString(R.string.text_enter_valid_email),
                    this);
            return;
        } else if (etCurrentPassword.getVisibility() == View.VISIBLE) {
            if (!TextUtils.isEmpty(etNewPassword.getText())) {
                if (etNewPassword.getText().length() < 6) {
                    UtilsExtra.showToast(
                            getResources().getString(
                                    R.string.error_invalid_password), this);
                    return;
                } else if (TextUtils.isEmpty(etCurrentPassword.getText())) {
                    UtilsExtra.showToast(
                            getResources().getString(
                                    R.string.error_field_is_empty), this);
                    return;
                } else if (etCurrentPassword.getText().length() < 6) {
                    UtilsExtra.showToast(
                            getResources().getString(
                                    R.string.error_invalid_password), this);
                    return;
                } else if (TextUtils.isEmpty(etRetypePassword.getText())) {
                    UtilsExtra.showToast(
                            getResources().getString(
                                    R.string.error_field_is_empty), this);
                    return;
                } else if (!etRetypePassword.getText().toString()
                        .equals(etNewPassword.getText().toString())) {
                    UtilsExtra.showToast(
                            getResources().getString(
                                    R.string.error_mismatch_password), this);
                    return;
                }
            } else if (etCurrentPassword.getVisibility() == View.INVISIBLE) {
                etNewPassword.setVisibility(View.INVISIBLE);
                etRetypePassword.setVisibility(View.INVISIBLE);
            }

        }



        if (etProfileNumber.getText().length() == 0) {
            UtilsExtra.showToast(
                    getResources().getString(R.string.text_enter_number), this);
            return;
        }

        else {

            editor.putString("PROFILE_EMAIL", email);
            editor.putString("PROFILE_FIRSTNAME", firstName);
            editor.putString("PROFILE_PHONE", phoneNo);
            editor.putString("PROFILE_SURNAME", lastName);
            editor.putString("PICTURE_URI", String.valueOf(contentURI));
            editor.putString("PROFILE_PASSWORD", password);
            editor.putString("PROFILE_ADDRESS", address).apply();
            profDAO.updateProfile(SharedPrefProfileID,lastName,firstName,phoneNo,email,"",address,"","",password);
        }
    }
}