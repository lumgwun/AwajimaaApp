package com.skylightapp.RealEstate;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class PropertyUpdateAct extends AppCompatActivity {
    private static final String TAG = PropertyUpdateAct.class.getSimpleName();
    public final static String PROPERTY_IMAGE_PATH = "ImageLink";
    public final static String PROPERTY_IMAGE_TITTLE = "ImageTittle";
    public final static String PROPERTY_IMAGE_ID = "ImageID";
    DBHelper dbHelper;
    Properties properties;
    long propertyID, ownerID;
    private Profile userProfile;
    SQLiteDatabase sqLiteDatabase;
    private Gson gson;
    private String json;
    private SharedPreferences userPreferences;
    private static boolean isPersistenceEnabled = false;
    Bundle bundle;
    long profileID;

    Long propertyIDReceived;
    int propertyOwnerID;
    AppCompatTextView txtPropertyID,txtPropertyAdminID;
    AppCompatTextView propertyTittle;
    Location mCurrentLocation=null;
    AppCompatEditText edtPropertyTittle;
    String uFirstName, uEmail, uSurname, uPhoneNo,uAddress, uUserName;
    String tittle,propertyDescription,status;
    double propertPrice;
    AppCompatEditText edtTittle,edtDescript,edtPrice,edtStatus;
    AppCompatButton btnUpdate;
    ActivityResultLauncher<Intent> mPropertyUpdating = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Intent data = result.getData();
                            /*if (data != null) {
                                pictureUri = data.getData();
                                Picasso.get().load(pictureUri).into(photoPrOP);*/
                            //}
                            Toast.makeText(PropertyUpdateAct.this, "Property updating successful ", Toast.LENGTH_SHORT).show();
                            //doProcessing();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(PropertyUpdateAct.this, "Property updating called", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_prop_update);
        dbHelper = new DBHelper(this);
        gson = new Gson();
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if(userProfile !=null){
            profileID=userProfile.getPID();
            uSurname=userProfile.getProfileLastName();
            uFirstName=userProfile.getProfileFirstName();
        }
        txtPropertyID = findViewById(R.id.idProp3);
        txtPropertyAdminID = findViewById(R.id.idOwner3);
        edtTittle = findViewById(R.id.propertyTittle2);
        edtDescript = findViewById(R.id.propertyDes);
        edtPrice = findViewById(R.id.propertyPrice3);
        edtStatus = findViewById(R.id.propertyStatus3);
        btnUpdate = findViewById(R.id.updateProp);
        bundle = getIntent().getExtras();
        try {
            if(bundle !=null){
                properties=bundle.getParcelable("Properties");

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if(properties !=null){
            propertyIDReceived=properties.getPropertyID();
            userProfile=properties.getProfile();


        }
        if(userProfile !=null){
            propertyOwnerID=userProfile.getPID();
            uPhoneNo=userProfile.getProfilePhoneNumber();

        }
        txtPropertyAdminID.setText(MessageFormat.format("Admin Profile ID:{0}", propertyOwnerID));
        txtPropertyID.setText(MessageFormat.format("Property ID:{0}", propertyIDReceived));
    }

    public void updateInDB(View view) {
        try {
            tittle = edtTittle.getText().toString();
        } catch (Exception e) {
            System.out.println("Oops!");
        }
        try {
            propertyDescription = edtDescript.getText().toString();
        } catch (Exception e) {
            System.out.println("Oops!");
        }
        try {
            propertPrice = Double.parseDouble(Objects.requireNonNull(edtPrice.getText()).toString());
        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }
        try {
            status = edtStatus.getText().toString();
        } catch (Exception e) {
            System.out.println("Oops!");
        }
        if(TextUtils.isEmpty(tittle)) {
            Toast.makeText(PropertyUpdateAct.this, "Please check, Tittle field is empty",
                    Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(propertyDescription)) {
            Toast.makeText(PropertyUpdateAct.this, "Please check, description field is empty",
                    Toast.LENGTH_SHORT).show();
        }
        if(propertPrice==0) {
            Toast.makeText(PropertyUpdateAct.this, "Please check, Price field is empty",
                    Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(status)) {
            Toast.makeText(PropertyUpdateAct.this, "Please check, Status field is empty",
                    Toast.LENGTH_SHORT).show();
        }else {
            try {
                Calendar calendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
                String timeLineTime = mdformat.format(calendar.getTime());
                String managerFullNames = uSurname + "," + uFirstName;
                String timelineDetailsT = propertyIDReceived + "was edited by " + managerFullNames+ "" + "on"+""+ timeLineTime;
                String tittleT = "Property Updating Alert!";
                String timelineDetailsT1 = "You updated" + "Property" + propertyIDReceived+"," +"on" + timeLineTime;

                String timelineDetails = uSurname + "," + uFirstName + "updated "  + propertyIDReceived + "on" + timeLineTime;

                dbHelper.insertTimeLine(tittleT, timelineDetailsT, timeLineTime, mCurrentLocation);
                dbHelper.updateProperty(profileID,propertyIDReceived,tittle,propertyDescription,propertPrice,status);
                userProfile.addPTimeLine(tittleT,timelineDetailsT1);


            } catch (SQLiteException e) {
                System.out.println("Oops!");
            }

            Toast.makeText(PropertyUpdateAct.this, "The property update was successful", Toast.LENGTH_LONG).show();

        }

    }
    protected void sendSMSMessage() {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        String timeLineTime = mdformat.format(calendar.getTime());
        String managerFullNames = uSurname + "," + uFirstName;
        String welcomeMessage = "The property:" +propertyIDReceived +"" +"was updated by "+managerFullNames+""+"on"+timeLineTime;
        Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(uPhoneNo),
                new com.twilio.type.PhoneNumber("234" + uPhoneNo),
                welcomeMessage)
                .create();


    }
}