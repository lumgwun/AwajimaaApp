package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import static com.skylightapp.Classes.AppConstants.ACCOUNT_SID;
import static com.skylightapp.Classes.AppConstants.AUTH_TOKEN;
import static com.skylightapp.Classes.Profile.PROFILE_ID;




public class MyNewGrpSavings extends AppCompatActivity {
    Profile userProfile;
    long profileUID;
    long id,grpAcctNo;
    private SharedPreferences userPreferences;
    AppCompatEditText edtTittle,edtPurpose,edtFirstName,edtSurName,edtAmount,edtEmail,edtPhoneNo;
    String ManagerSurname,managerFirstName,managerPhoneNumber1,managerEmail, managerNIN,managerUserName;

    private Calendar calendar;
    protected DBHelper dbHelper;
    Gson gson;
    String json,tittle,purpose,firstName,surname,phoneNo,emailAddress,machine,bundleID,bundleMachine;
    SecureRandom random;
    DatePicker picker;
    Random ran ;
    private  double amount;
    AppCompatSpinner spnFrequency;
    AppCompatButton btnCreateGroup;
    Bundle userBundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_new_grp_savings);
        dbHelper=new DBHelper(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        userProfile=new Profile();
        random= new SecureRandom();
        json = userPreferences.getString("LastProfileUsed", "");
        edtTittle =  findViewById(R.id.grpTittle);
        edtPurpose =  findViewById(R.id.grpPurpose);
        edtFirstName =  findViewById(R.id.grpFirstName);
        edtSurName =  findViewById(R.id.grpSurname);
        edtAmount =  findViewById(R.id.grpAmount);
        edtEmail =  findViewById(R.id.grpEmail);
        edtPhoneNo =  findViewById(R.id.grpPhoneNo);
        //spnFrequency =  findViewById(R.id.grpFreq);
        btnCreateGroup =  findViewById(R.id.grpCreateGrp);
        userProfile = gson.fromJson(json, Profile.class);
        userBundle=new Bundle();

        if(userProfile !=null){
            ManagerSurname = userProfile.getProfileLastName();
            managerFirstName = userProfile.getProfileFirstName();
            managerPhoneNumber1 = userProfile.getProfilePhoneNumber();
            managerEmail = userProfile.getProfileEmail();
            managerNIN = userProfile.getProfileIdentity();
            managerUserName = userProfile.getProfileUserName();
            machine = userProfile.getProfileMachine();
            profileUID = userProfile.getPID();
            userBundle.putString(PROFILE_ID, String.valueOf(profileUID));
            userBundle.putString("Machine",machine);

        }
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userProfile !=null){
                    ManagerSurname = userProfile.getProfileLastName();
                    managerFirstName = userProfile.getProfileFirstName();
                    managerPhoneNumber1 = userProfile.getProfilePhoneNumber();
                    managerEmail = userProfile.getProfileEmail();
                    managerNIN = userProfile.getProfileIdentity();
                    managerUserName = userProfile.getProfileUserName();
                    String userRole = userProfile.getProfileMachine();
                    profileUID = userProfile.getPID();
                    userBundle.putString(PROFILE_ID, String.valueOf(profileUID));
                    userBundle.putString("Machine",userRole);



                }
                calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
                String grpDateDate = mdformat.format(calendar.getTime());
                Date currentDate = calendar.getTime();


                try {
                    tittle = edtTittle.getText().toString();
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
                try {
                    purpose = edtPurpose.getText().toString();
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }


                try {
                    firstName = edtFirstName.getText().toString();
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
                try {
                    amount = Double.parseDouble(edtAmount.getText().toString());
                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }
                try {
                    surname = edtSurName.getText().toString();
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
                try {
                    emailAddress = edtEmail.getText().toString();
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
                try {
                    phoneNo = edtPhoneNo.getText().toString();
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
                try {
                    grpAcctNo = random.nextInt((int) (Math.random() * 902304) + 136197);

                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }

                try {
                    if (String.valueOf(purpose).isEmpty()) {
                        Toast.makeText(MyNewGrpSavings.this, "Purpose can not be left empty", Toast.LENGTH_LONG).show();


                    }
                    if (String.valueOf(firstName).isEmpty()) {
                        Toast.makeText(MyNewGrpSavings.this, "First Name can not be left empty", Toast.LENGTH_LONG).show();
                    }
                    if (String.valueOf(amount).isEmpty()) {
                        Toast.makeText(MyNewGrpSavings.this, "Amount can not be left empty", Toast.LENGTH_LONG).show();
                    }
                    if (String.valueOf(surname).isEmpty()) {
                        Toast.makeText(MyNewGrpSavings.this, "Surname can not be left empty", Toast.LENGTH_LONG).show();


                    }else {
                        String status3 = "Subscription in progress";
                        String status1 = "Savings Unconfirmed";
                        grpDateDate = mdformat.format(calendar.getTime());
                        String tittle = "New Group Savings alert";
                        String timelineDetails1 = "A new Group savings of NGN" + amount + "was started @"+""+currentDate;
                        String timelineDetails = "A new Group savings of NGN" + amount + "was started";
                        int grpNo=0;


                        try {

                            dbHelper.insertTimeLine(tittle, timelineDetails, grpDateDate, null);
                            dbHelper.insertGroupAccount(grpAcctNo, profileUID, tittle, purpose,firstName,surname,phoneNo,emailAddress,currentDate,0.00,null,"new");
                            doNotification();

                        } catch (SQLiteException e) {
                            System.out.println("Oops!");
                        }

                        if(userProfile !=null){
                            userProfile.addPTimeLine(tittle,timelineDetails1);
                            userProfile.addPSavingsGrp(grpNo, tittle,surname+","+ firstName,purpose,amount, currentDate, null,"New");


                        }


                        String paymentMessage = "Congratulations" + surname+","+ firstName + " a new Group Savings on the Skylight App,is for You";

                        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                        Message message = Message.creator(
                                new com.twilio.type.PhoneNumber(phoneNo),
                                new com.twilio.type.PhoneNumber("234"+phoneNo),
                                paymentMessage)
                                .create();
                        //message.getStatus();

                        Intent amTIntent = new Intent(MyNewGrpSavings.this, LoginDirAct.class);
                        amTIntent.putExtras(userBundle);
                        amTIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    }

                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }

            }
        });
    }
    private void doNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Group Creation")
                        .setContentText("A new Group was successfully created");

        Intent notificationIntent = new Intent(this, LoginDirAct.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(LoginDirAct.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}