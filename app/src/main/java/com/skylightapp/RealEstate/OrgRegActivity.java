package com.skylightapp.RealEstate;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AccountTypes;
import com.skylightapp.Classes.OtherBusiness;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MapAndLoc.ProfileLocSourceAct;
import com.skylightapp.R;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class OrgRegActivity extends AppCompatActivity {
    private SharedPreferences userPreferences;
    private Gson gson;
    private String json,phoneNo, joinedDate,propertyCapacity,propertyTittle,propertyDescription;
    private Profile userProfile;
    private long profileID;
    private long businessID;
    private int bizAcctID;
    AppCompatEditText orgBrandName,orgName,orgAddress,orgEmail,orgPhoneNo,orgRegNo;
    AppCompatSpinner spnLga,spnPriceDuration, spnRegType,spnMode,spnOrgType;
    Button mapLocation, btnLogo, btnRegister;
    private double propertPrice;
    DBHelper dbHelper;
    SecureRandom random;
    long propertyID;
    LocationManager locationManager ;
    Location location;
    boolean GpsStatus ;
    Context context;
    AppCompatSpinner  spnState;
    OtherBusiness otherBusiness;
    Account account;
    SharedPreferences.Editor editor ;
    //DatabaseReference fireBaseBizDBRef;
    //FirebaseDatabase fireBaseBizDB;
    Location mCurrentLocation;
    String selectedPriceDuration,brandName,selectedLGA,bizRegType,selectedOrgType, ComName,comEmail,comAddress,comRegNo,comPhoneNo;


    AppCompatImageView logo;
    private String selectedPropMode,selectedPropertyType,profileSurname,profileFirstName,profilePhoneNO,profileEmail,profileUserName,profilePassword;

    ActivityResultLauncher<Intent> mapLocationStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Intent data = result.getData();
                            Bundle bundle = result.getData().getExtras();
                            Toast.makeText(OrgRegActivity.this, "Location successful registered", Toast.LENGTH_SHORT).show();
                            //doProcessing();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(OrgRegActivity.this, "Location registeration failed", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                    }
                }
            });
    ActivityResultLauncher<Intent> gPSStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:

                            mapLocationStartForResult.launch(new Intent(OrgRegActivity.this, ProfileLocSourceAct.class));
                            Toast.makeText(OrgRegActivity.this, "GPS Is Enabled", Toast.LENGTH_SHORT).show();
                            //doProcessing();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(OrgRegActivity.this, "GPS Is Disabled", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                    }
                }
            });
    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK :
                            if (result.getData() != null) {
                                logo = findViewById(R.id.imageLogo);
                                Intent data = result.getData();
                                Uri selectedImage = data.getData();
                                if (selectedImage != null) {
                                    logo.setImageBitmap(getScaledBitmap(selectedImage));
                                } else {
                                    Toast.makeText(OrgRegActivity.this, "Error getting Image",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }

                            Toast.makeText(OrgRegActivity.this, "Image picking returned successful", Toast.LENGTH_SHORT).show();
                            //doProcessing();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(OrgRegActivity.this, "Activity canceled", Toast.LENGTH_SHORT).show();
                            finish();
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
        setContentView(R.layout.act_org_reg);
        userPreferences = PreferenceManager.getDefaultSharedPreferences(OrgRegActivity.this);
        gson = new Gson();
        random= new SecureRandom();
        otherBusiness = new OtherBusiness();
        account= new Account();
        orgAddress =  findViewById(R.id.corpAddress);
        orgEmail =  findViewById(R.id.corporateEmail);
        orgName =  findViewById(R.id.corporateName);
        orgRegNo =  findViewById(R.id.BizRegNo);
        orgPhoneNo =  findViewById(R.id.corpPhone);
        orgBrandName =  findViewById(R.id.BizBrandName);
        spnOrgType = findViewById(R.id.Org2Type);
        spnRegType = findViewById(R.id.regTypeBiz);
        spnState = findViewById(R.id.stateType2);
        spnLga = findViewById(R.id.LGASpn);
        btnLogo = findViewById(R.id.btnLogoSelect);
        mapLocation = findViewById(R.id.bizLoc);
        btnRegister = findViewById(R.id.BtnRegOrg);
        logo = findViewById(R.id.imageLogo);

        Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
        dbHelper = new DBHelper(this);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if(userProfile !=null) {
            profileID = userProfile.getPID();
            profileSurname = userProfile.getProfileLastName();
            profileFirstName = userProfile.getProfileFirstName();
            profilePhoneNO = userProfile.getProfilePhoneNumber();
            profileEmail = userProfile.getProfileEmail();
            profileUserName = userProfile.getProfileUserName();
            profilePassword = userProfile.getProfilePassword();
        }
    }
    private void doBizReg(){
        if(otherBusiness !=null){
            businessID= otherBusiness.getBusinessID();

        }
        propertyID = random.nextInt((int) (Math.random() * 900000) + 100000);
        businessID = random.nextInt((int) (Math.random() * 911) + 119);
        bizAcctID = random.nextInt((int) (Math.random() * 9368) + 8317);
        if(userProfile !=null){
            profileID=userProfile.getPID();
            profileSurname=userProfile.getProfileLastName();
            profileFirstName=userProfile.getProfileFirstName();
            profilePhoneNO=userProfile.getProfilePhoneNumber();
            profileEmail=userProfile.getProfileEmail();
            profileUserName=userProfile.getProfileUserName();
            profilePassword=userProfile.getProfilePassword();
        }
        try {
            comAddress = orgAddress.getText().toString();
        } catch (Exception e) {
            System.out.println("Oops!");
        }
        try {
            comEmail = orgEmail.getText().toString();
        } catch (Exception e) {
            System.out.println("Oops!");
        }
        try {
            brandName = orgBrandName.getText().toString();
        } catch (Exception e) {
            System.out.println("Oops!");
        }

        try {
            ComName = Objects.requireNonNull(orgName.getText().toString());
        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }


        try {
            comPhoneNo = Objects.requireNonNull(orgPhoneNo.getText()).toString();
        } catch (Exception e) {
            System.out.println("Oops!");
        }
        try {
            comRegNo = orgRegNo.getText().toString();
        } catch (Exception e) {
            System.out.println("Oops!");
        }


        try {
            //String selectedState= String.valueOf(state.getSelectedItem());
            selectedLGA = spnLga.getSelectedItem().toString();
        } catch (Exception e) {
            System.out.println("Oops!");
        }
        try {
            selectedOrgType = spnOrgType.getSelectedItem().toString();
        } catch (Exception e) {
            System.out.println("Oops!");
        }

        try {
            bizRegType = spnRegType.getSelectedItem().toString();
        } catch (Exception e) {
            System.out.println("Oops!");
        }


        if(TextUtils.isEmpty(ComName)||TextUtils.isEmpty(comPhoneNo)||TextUtils.isEmpty(String.valueOf(comAddress))||TextUtils.isEmpty(comEmail)||TextUtils.isEmpty(selectedLGA)){
            Toast.makeText(OrgRegActivity.this, "One or more record fields are empty,please fill them", Toast.LENGTH_SHORT).show();

        }else  {
            Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");

            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(comPhoneNo),
                    new com.twilio.type.PhoneNumber("234" + comPhoneNo),
                    "Our Coop. App: You have registered new Property").create();

            Calendar calendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
            joinedDate = mdformat.format(calendar.getTime());
            userProfile.addPBusiness(businessID, profileID, ComName,comEmail,comAddress,comPhoneNo, selectedOrgType, comRegNo,joinedDate,"Pending");
            otherBusiness = new OtherBusiness(businessID, profileID, ComName,brandName,comEmail,comAddress,comPhoneNo, selectedOrgType, comRegNo,joinedDate,"New");
            SharedPreferences.Editor editor = userPreferences.edit();
            gson = new Gson();
            /*try {
                fireBaseBizDBRef = FirebaseDatabase.getInstance().getReference("BusinessInfo").child("profile");

                fireBaseBizDBRef.child("BusinessID").setValue(businessID);
                fireBaseBizDBRef.child("ProfileID").setValue(profileID);
                fireBaseBizDBRef.child("CompanyName").setValue(ComName);
                fireBaseBizDBRef.child("CompanyEmail").setValue(comEmail);
                fireBaseBizDBRef.child("CompanyAddress").setValue(comAddress);
                fireBaseBizDBRef.child("CompanyPhoneNo").setValue(comPhoneNo);
                fireBaseBizDBRef.child("CompanyType").setValue(selectedOrgType);
                fireBaseBizDBRef.child("CompanyRegNo").setValue(comRegNo);
                fireBaseBizDBRef.child("JoinedDate").setValue(joinedDate);
                fireBaseBizDBRef.child("Machine").setValue("Company");

            } catch (DatabaseException e) {
                System.out.println("Oops!");
            }*/
            if(account !=null){
                AccountTypes accountTypes= AccountTypes.EWALLET;
                otherBusiness.addPAccount("Biz Account",brandName,bizAcctID,0.00,accountTypes);

            }
            String json = gson.toJson(otherBusiness);
            editor.putString("LastBusinessUsed", json);
            editor.apply();




            try {

                SimpleDateFormat dateFormatWithZone = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String currentDate = dateFormatWithZone.format(calendar.getTime());
                String managerFullNamesT = profileSurname + "," + profileFirstName;

                String timelineDetailsT = brandName + "was added as a Business" + "by" + managerFullNamesT + "@" + joinedDate;
                String tittleT = "Brand Sign Up Alert!";
                String namesT = brandName;
                String timelineDetailsT1 = "You added" + brandName + "as a Business" + "on" + currentDate;

                String managerFullNames = profileSurname + "," + profileFirstName;
                String timelineDetails = brandName + "was added as a Business" + "by" + managerFullNames + "@" + currentDate;
                String tittle = "Business Sign Up Alert!";

                String names =brandName;

                String name = brandName;

                dbHelper.saveBiz(otherBusiness);
                userProfile.addPTimeLine(tittleT,timelineDetailsT1);
                dbHelper.insertTimeLine(tittle, timelineDetails, currentDate, mCurrentLocation);

            } catch (SQLiteException e) {
                System.out.println("Oops!");
            }
            Intent data = new Intent();
            setResult(RESULT_OK,data);
            finish();




        }

    }
    public Bitmap getBitmap(String path) {
        Bitmap myBitmap = null;
        File imgFile = new File(path);
        if (imgFile.exists()) {
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }
        return myBitmap;
    }


    public String getPath(Uri photoUri) {

        String filePath = "";
        if (photoUri != null) {
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(photoUri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filePath = cursor.getString(columnIndex);
            cursor.close();
        }
        return filePath;
    }

    private File getDir() {
        File sdDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "AppAttachments");
    }
    private Bitmap getScaledBitmap(Uri uri){
        Bitmap thumb = null ;
        try {
            ContentResolver cr = getContentResolver();
            InputStream in = cr.openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize=8;
            thumb = BitmapFactory.decodeStream(in,null,options);
        } catch (FileNotFoundException e) {
            Toast.makeText(OrgRegActivity.this , "File not found" , Toast.LENGTH_SHORT).show();
        }
        return thumb ;
    }
    public void doSelectBizLogo(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (intent.resolveActivity(getPackageManager()) != null) {
            mGetContent.launch(intent);
        } else {
            Toast.makeText(OrgRegActivity.this, "There is no app that support this action",
                    Toast.LENGTH_SHORT).show();
        }

    }
    public void doBizMapLocation(View view) {
        try {
            locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            assert locationManager != null;
            GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if(GpsStatus == true) {
                mapLocationStartForResult.launch(new Intent(OrgRegActivity.this, ProfileLocSourceAct.class));
            } else {
                gPSStartForResult.launch(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

            }

        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }

    }
}