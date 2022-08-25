package com.skylightapp.RealEstate;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.gson.Gson;
import com.skylightapp.Classes.OtherBusiness;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MapAndLoc.ProfileLocSourceAct;
import com.skylightapp.R;
import com.skylightapp.SignUpAct;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class PropertyRegAct extends AppCompatActivity {
    AppCompatEditText edtTittle, edtDescription, edtPrice,edtCapacity,orgName,orgAddress,orgEmail,orgPhoneNo,orgRegNo;
    AppCompatSpinner spnLga,spnPriceDuration, spnPropertyType,spnMode,spnOrgType;
    Button mapLocation,photoProp, btnRegister;
    //ImageView imageView;
    ImageSwitcher imageView;
    ArrayList<Uri> mArrayUri;
    Button select, btnPrevious, btnNext;
    int PICK_IMAGE_MULTIPLE = 1;
    int position = 0;
    List<String> imagesEncodedList;
    LinearLayoutCompat layoutImageSwitcher,layoutRegNo,layoutBizType,layoutMode,layoutPriceDuration,layoutBusDetails1,layoutBusDetails2;
    LocationManager locationManager ;
    Location location;
    boolean GpsStatus ;
    Context context;
    String selectedPriceDuration,selectedLGA,selectedOrgType, ComName,comEmail,comAddress,comRegNo,comPhoneNo;
    AppCompatButton btnAsIndividual, btnAsCorporate;
    private SharedPreferences userPreferences;
    private Gson gson;
    private String json,phoneNo, propertyDate,propertyCapacity,propertyTittle,propertyDescription;
    private Profile userProfile;
    private long profileID;
    private double propertPrice;
    DBHelper dbHelper;
    SecureRandom random;
    long propertyID,businessID,bizAcctID;
    OtherBusiness otherBusiness;
    private String selectedPropMode,selectedPropertyType,profileSurname,profileFirstName,profilePhoneNO,profileEmail,profileUserName,profilePassword;

    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK :
                            if (result.getData() != null) {
                                layoutImageSwitcher.setVisibility(View.VISIBLE);
                                Intent data = result.getData();
                                //Bundle bundle = result.getData().getExtras();
                                //Bitmap bitmap = (Bitmap) bundle.get("data");
                                //imageView.setImageBitmap(bitmap);
                                int cout = data.getClipData().getItemCount();
                                for (int i = 0; i < cout; i++) {
                                    Uri imageurl = data.getClipData().getItemAt(i).getUri();
                                    mArrayUri.add(imageurl);
                                    imageView.setImageURI(mArrayUri.get(position));
                                }
                                imageView.setImageURI(mArrayUri.get(0));
                                position = 0;

                            }

                            Toast.makeText(PropertyRegAct.this, "Image picking returned successful", Toast.LENGTH_SHORT).show();
                            //doProcessing();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(PropertyRegAct.this, "Activity canceled", Toast.LENGTH_SHORT).show();
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


    ActivityResultLauncher<Intent> mapLocationStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Intent data = result.getData();
                            Bundle bundle = result.getData().getExtras();
                            Toast.makeText(PropertyRegAct.this, "Location successful registered", Toast.LENGTH_SHORT).show();
                            //doProcessing();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(PropertyRegAct.this, "Location registeration failed", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                    }
                }
            });

    ActivityResultLauncher<Intent> doBizRegStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            //Intent data = result.getData();
                            //Bundle bundle = result.getData().getExtras();
                            userPreferences = PreferenceManager.getDefaultSharedPreferences(PropertyRegAct.this);
                            gson = new Gson();
                            json = userPreferences.getString("LastBusinessUsed", "");
                            otherBusiness = gson.fromJson(json, OtherBusiness.class);
                            if(otherBusiness !=null){
                                businessID= otherBusiness.getBusinessID();
                            }
                            Toast.makeText(PropertyRegAct.this, "Business Signup was successful", Toast.LENGTH_SHORT).show();
                            //doProcessing();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(PropertyRegAct.this, "Business Sign up was cancelled", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                    }
                }
            });

    ActivityResultLauncher<Intent> startSignUpForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Intent data = result.getData();
                            Bundle bundle = result.getData().getExtras();
                            userPreferences = PreferenceManager.getDefaultSharedPreferences(PropertyRegAct.this);
                            gson = new Gson();
                            json = userPreferences.getString("LastProfileUsed", "");
                            userProfile = gson.fromJson(json, Profile.class);
                            if(userProfile !=null){
                                profileID=userProfile.getPID();
                                profileSurname=userProfile.getProfileLastName();
                                profileFirstName=userProfile.getProfileFirstName();
                                profilePhoneNO=userProfile.getProfilePhoneNumber();
                                profileEmail=userProfile.getProfileEmail();
                                profileUserName=userProfile.getProfileUserName();
                                profilePassword=userProfile.getProfilePassword();
                            }
                            Toast.makeText(PropertyRegAct.this, "Signup was successful", Toast.LENGTH_SHORT).show();
                            //doProcessing();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(PropertyRegAct.this, "Sign up was cancelled", Toast.LENGTH_SHORT).show();
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

                            mapLocationStartForResult.launch(new Intent(PropertyRegAct.this, ProfileLocSourceAct.class));
                            Toast.makeText(PropertyRegAct.this, "GPS Is Enabled", Toast.LENGTH_SHORT).show();
                            //doProcessing();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(PropertyRegAct.this, "GPS Is Disabled", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_property_reg);
        edtTittle =  findViewById(R.id.tittle_prop);
        edtDescription = findViewById(R.id.descriptionProp);
        edtPrice = findViewById(R.id.prop_price);
        edtCapacity = findViewById(R.id.capacityProp);
        spnLga = findViewById(R.id.LGA);
        mapLocation = findViewById(R.id.location2);
        photoProp = findViewById(R.id.proprops);
        btnRegister = findViewById(R.id.BtnRegProp);
        imageView = findViewById(R.id.propImage);
        btnPrevious = findViewById(R.id.buttonPrevious);
        btnNext = findViewById(R.id.buttonNext);
        btnAsIndividual = findViewById(R.id.individual);
        btnAsCorporate = findViewById(R.id.corporate);
        layoutPriceDuration = findViewById(R.id.layour_PriceDuration);
        spnPriceDuration = findViewById(R.id.durationPrice);
        spnPropertyType = findViewById(R.id.propertyType);
        spnMode = findViewById(R.id.propMode);
        //layoutBizType = findViewById(R.id.layoutReg_Type);
        //layoutRegNo = findViewById(R.id.layout_reg_No);
        layoutMode = findViewById(R.id.layour_mode);
        Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
        dbHelper = new DBHelper(this);
        userPreferences = PreferenceManager.getDefaultSharedPreferences(PropertyRegAct.this);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        random= new SecureRandom();
        otherBusiness = new OtherBusiness();

        layoutImageSwitcher = findViewById(R.id.propImageLayout);

        mArrayUri = new ArrayList<Uri>();
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

        imageView.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView1 = new ImageView(getApplicationContext());
                return imageView1;
            }
        });
        btnAsIndividual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doDialog();

            }
        });
        btnAsCorporate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doBizReg();



            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < mArrayUri.size() - 1) {

                    position++;
                    imageView.setImageURI(mArrayUri.get(position));
                    imageView.setAnimation(out);
                } else {
                    Toast.makeText(PropertyRegAct.this, "Last Image Already Shown", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position > 0) {
                    position--;
                    imageView.setImageURI(mArrayUri.get(position));
                    imageView.setAnimation(in);
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doPropertyReg();

            }
        });

    }
    private void doPropertyReg(){
        propertyID = random.nextInt((int) (Math.random() * 900000) + 100000);
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
            propertyTittle = edtTittle.getText().toString();
        } catch (Exception e) {
            System.out.println("Oops!");
        }
        try {
            propertyDescription = edtDescription.getText().toString();
        } catch (Exception e) {
            System.out.println("Oops!");
        }
        try {
            propertPrice = Double.parseDouble(Objects.requireNonNull(edtPrice.getText()).toString());
        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }

        try {
            propertyCapacity = Objects.requireNonNull(edtCapacity.getText()).toString();
        } catch (Exception e) {
            System.out.println("Oops!");
        }



        try {
            selectedPropertyType = spnPropertyType.getSelectedItem().toString();

        } catch (Exception e) {
            System.out.println("Oops!");
        }
        try {
            selectedPropMode = spnMode.getSelectedItem().toString();

        } catch (Exception e) {
            System.out.println("Oops!");
        }
        try {
            selectedLGA = spnLga.getSelectedItem().toString();
        } catch (Exception e) {
            System.out.println("Oops!");
        }
        try {
            selectedPriceDuration = spnPriceDuration.getSelectedItem().toString();
        } catch (Exception e) {
            System.out.println("Oops!");
        }
        if(TextUtils.isEmpty(propertyTittle)||TextUtils.isEmpty(propertyDescription)||TextUtils.isEmpty(String.valueOf(propertPrice))||TextUtils.isEmpty(selectedPropertyType)||TextUtils.isEmpty(selectedPropMode)||TextUtils.isEmpty(selectedLGA)||TextUtils.isEmpty(selectedPriceDuration)){
            Toast.makeText(PropertyRegAct.this, "Some record fields are empty,please fill them", Toast.LENGTH_SHORT).show();

        }else  {
            Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");

            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(profilePhoneNO),
                    new com.twilio.type.PhoneNumber("234" + profilePhoneNO),
                    "Our Coop. App: You have registered new Property").create();

            Calendar calendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
            propertyDate = mdformat.format(calendar.getTime());
            try {
                userProfile.addPProperty(propertyID, profileID, propertyTittle,propertyDescription,selectedPropertyType,"",selectedLGA, propertPrice, selectedPriceDuration, propertyCapacity,selectedPropMode,
                        calendar.getTime(),Uri.parse(""), "New");
                dbHelper.saveNewProperty(propertyID, profileID, propertyTittle,propertyDescription,selectedPropertyType,"",selectedLGA, propertPrice, selectedPriceDuration, propertyCapacity,selectedPropMode,
                        calendar.getTime(),Uri.parse(""), "New");

            } catch (SQLiteException e) {
                System.out.println("Oops!");
            }




        }

    }
    private void doBizReg(){
        doBizRegStartForResult.launch(new Intent(PropertyRegAct.this,OrgRegActivity.class));
    }
    private void doDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Individual Profile");
        builder.setItems(new CharSequence[]
                        {"Use Present Profile", "Sign up a new Account"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                Toast.makeText(PropertyRegAct.this, "Use this present profile option, selected ", Toast.LENGTH_SHORT).show();
                                useMyPreference();
                                break;
                            case 1:
                                Toast.makeText(PropertyRegAct.this, "Use a new Profile Account Choice, made", Toast.LENGTH_SHORT).show();
                                Intent signUpIntent = new Intent(PropertyRegAct.this, SignUpAct.class);
                                signUpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

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
    private  void useMyPreference(){
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if(userProfile !=null){
            profileID=userProfile.getPID();
            profileSurname=userProfile.getProfileLastName();
            profileFirstName=userProfile.getProfileFirstName();
            profilePhoneNO=userProfile.getProfilePhoneNumber();
            profileEmail=userProfile.getProfileEmail();
            profileUserName=userProfile.getProfileUserName();
            profilePassword=userProfile.getProfilePassword();
        }
    }

    public void doSelectPropPhoto(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (intent.resolveActivity(getPackageManager()) != null) {
            mGetContent.launch(intent);
        } else {
            Toast.makeText(PropertyRegAct.this, "There is no app that support this action",
                    Toast.LENGTH_SHORT).show();
        }

    }
    public void doMapLocation(View view) {
        try {
            locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            assert locationManager != null;
            GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if(GpsStatus == true) {
                mapLocationStartForResult.launch(new Intent(PropertyRegAct.this, ProfileLocSourceAct.class));
            } else {
                gPSStartForResult.launch(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

            }

        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }




    }
}