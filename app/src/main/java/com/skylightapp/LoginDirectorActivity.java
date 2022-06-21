package com.skylightapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.skylightapp.Accountant.AcctantBackOffice;
import com.skylightapp.Admins.AdminDrawerActivity;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.SuperAdmin.SuperAdminOffice;
import com.skylightapp.Tellers.TellerHomeChoices;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Profile.PICTURE_URI;
import static com.skylightapp.Classes.Profile.PROFILE_EMAIL;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Profile.PROFILE_ADDRESS;
import static com.skylightapp.Classes.Profile.PROFILE_DATE_JOINED;
import static com.skylightapp.Classes.Profile.PROFILE_DOB;
import static com.skylightapp.Classes.Profile.PROFILE_FIRSTNAME;
import static com.skylightapp.Classes.Profile.PROFILE_GENDER;
import static com.skylightapp.Classes.Profile.PROFILE_OFFICE;
import static com.skylightapp.Classes.Profile.PROFILE_PASSWORD;
import static com.skylightapp.Classes.Profile.PROFILE_PHONE;
import static com.skylightapp.Classes.Profile.PROFILE_ROLE;
import static com.skylightapp.Classes.Profile.PROFILE_STATE;
import static com.skylightapp.Classes.Profile.PROFILE_SURNAME;
import static com.skylightapp.Classes.Profile.PROFILE_USERNAME;


public class LoginDirectorActivity extends AppCompatActivity {
    long profileID,customerID;
    AppCompatButton btnCustomer,btnAdmin, btnTeller;
    String machinePref;
    Uri pictureLink;
    SharedPreferences sharedPref;
    Bundle userExtras;
    private static final String PREF_NAME = "skylight";
    private DBHelper dbHelper;
    private boolean checkRole;
    String machineUser,userName, office,state,role,dbRole,joinedDate,password,surname, email,phoneNO, firstName, dob,gender,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login_director);
        setTitle("Login Director");
        dbHelper=new DBHelper(this);
        userExtras=getIntent().getExtras();
        sharedPref= getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        machinePref = sharedPref.getString("Machine", "");
        profileID = sharedPref.getInt("PROFILE_ID", 0);
        userName = sharedPref.getString("PROFILE_USERNAME", "");
        office = sharedPref.getString("PROFILE_OFFICE", "");
        state = sharedPref.getString("PROFILE_STATE", "");
        role = sharedPref.getString("PROFILE_ROLE", "");
        password = sharedPref.getString("PROFILE_PASSWORD", "");
        joinedDate = sharedPref.getString("PROFILE_DATE_JOINED", "");
        surname = sharedPref.getString("PROFILE_SURNAME", "");
        email = sharedPref.getString("PROFILE_EMAIL", "");
        phoneNO = sharedPref.getString("PROFILE_PHONE", "");
        firstName = sharedPref.getString("PROFILE_FIRSTNAME", "");
        dob = sharedPref.getString("PROFILE_DOB", "");
        customerID = sharedPref.getInt("CUSTOMER_ID", 0);
        gender = sharedPref.getString("PROFILE_GENDER", "");
        address = sharedPref.getString("PROFILE_ADDRESS", "");
        pictureLink = Uri.parse(sharedPref.getString("PICTURE_URI", ""));

        machinePref = sharedPref.getString("Machine", "");
        profileID = sharedPref.getInt("PROFILE_ID", 0);
        userName = sharedPref.getString("USER_NAME", "");
        office = sharedPref.getString("USER_OFFICE", "");
        state = sharedPref.getString("USER_STATE", "");
        role = sharedPref.getString("USER_ROLE", "");

        joinedDate = sharedPref.getString("USER_DATE_JOINED", "");
        password = sharedPref.getString("USER_PASSWORD", "");
        surname = sharedPref.getString("USER_SURNAME", "");
        email = sharedPref.getString("EMAIL_ADDRESS", "");
        phoneNO = sharedPref.getString("USER_PHONE", "");
        firstName = sharedPref.getString("USER_FIRSTNAME", "");
        dob = sharedPref.getString("USER_DOB", "");
        customerID = sharedPref.getInt("CUSTOMER_ID", 0);
        gender = sharedPref.getString("USER_GENDER", "");
        address = sharedPref.getString("USER_ADDRESS", "");
        pictureLink = Uri.parse(sharedPref.getString("PICTURE_URI", ""));
        dbRole=dbHelper.getProfileRoleByUserNameAndPassword(userName,password);

        if(userExtras ==null) {
            if(dbRole !=null){
                if (dbRole.equalsIgnoreCase("SuperAdmin")) {

                    Intent tellerIntent = new Intent(this, SuperAdminOffice.class);
                    tellerIntent.putExtra("PROFILE_ID", profileID);
                    tellerIntent.putExtra("PROFILE_USERNAME", userName);
                    tellerIntent.putExtra("PROFILE_PASSWORD", password);
                    tellerIntent.putExtra("PROFILE_OFFICE", office);
                    tellerIntent.putExtra("PROFILE_STATE", state);
                    tellerIntent.putExtra("PROFILE_ROLE", role);
                    tellerIntent.putExtra("PROFILE_DATE_JOINED", joinedDate);
                    tellerIntent.putExtra("PROFILE_EMAIL", email);
                    tellerIntent.putExtra("PROFILE_PHONE", phoneNO);
                    tellerIntent.putExtra("PROFILE_FIRSTNAME", firstName);
                    tellerIntent.putExtra("PROFILE_SURNAME", surname);
                    tellerIntent.putExtra("PROFILE_DOB", dob);
                    tellerIntent.putExtra("PROFILE_DATE_JOINED", joinedDate);
                    tellerIntent.putExtra("CUSTOMER_ID", customerID);
                    tellerIntent.putExtra("PROFILE_GENDER", gender);
                    tellerIntent.putExtra("PROFILE_ADDRESS", address);
                    tellerIntent.putExtra("PROFILE_ADDRESS", address);
                    tellerIntent.putExtra("PICTURE_URI", pictureLink);
                    tellerIntent.putExtra(PROFILE_ID, profileID);
                    tellerIntent.putExtra(PROFILE_USERNAME, userName);
                    tellerIntent.putExtra(PROFILE_PASSWORD, password);
                    tellerIntent.putExtra(PROFILE_OFFICE, office);
                    tellerIntent.putExtra(PROFILE_STATE, state);
                    tellerIntent.putExtra(PROFILE_ROLE, role);
                    tellerIntent.putExtra(PROFILE_DATE_JOINED, joinedDate);
                    tellerIntent.putExtra(PROFILE_PHONE, phoneNO);
                    tellerIntent.putExtra(PROFILE_FIRSTNAME, firstName);
                    tellerIntent.putExtra(PROFILE_SURNAME, surname);
                    tellerIntent.putExtra(PROFILE_DOB, dob);
                    tellerIntent.putExtra(PROFILE_DATE_JOINED, joinedDate);
                    tellerIntent.putExtra(CUSTOMER_ID, customerID);
                    tellerIntent.putExtra(PROFILE_GENDER, gender);
                    tellerIntent.putExtra(PROFILE_ADDRESS, address);
                    tellerIntent.putExtra(PICTURE_URI, pictureLink);

                    tellerIntent.putExtra("USER_NAME", userName);
                    tellerIntent.putExtra("USER_PASSWORD", password);
                    tellerIntent.putExtra("USER_OFFICE", office);
                    tellerIntent.putExtra("USER_STATE", state);
                    tellerIntent.putExtra("USER_ROLE", role);
                    tellerIntent.putExtra("USER_DATE_JOINED", joinedDate);
                    tellerIntent.putExtra("EMAIL_ADDRESS", email);
                    tellerIntent.putExtra("USER_PHONE", phoneNO);
                    tellerIntent.putExtra("USER_FIRSTNAME", firstName);
                    tellerIntent.putExtra("USER_SURNAME", surname);
                    tellerIntent.putExtra("USER_DOB", dob);
                    tellerIntent.putExtra("USER_DATE_JOINED", joinedDate);
                    tellerIntent.putExtra("CUSTOMER_ID", customerID);
                    tellerIntent.putExtra("USER_GENDER", gender);
                    tellerIntent.putExtra("USER_ADDRESS", address);
                    tellerIntent.putExtra("PICTURE_URI", pictureLink);
                    startActivity(tellerIntent);
                }
                if (dbRole.equalsIgnoreCase("BlockedUser")) {

                    Intent tellerIntent = new Intent(this, BlockedUserAct.class);
                    tellerIntent.putExtra("PROFILE_ID", profileID);
                    tellerIntent.putExtra("PROFILE_USERNAME", userName);
                    tellerIntent.putExtra("PROFILE_PASSWORD", password);
                    tellerIntent.putExtra("PROFILE_OFFICE", office);
                    tellerIntent.putExtra("PROFILE_STATE", state);
                    tellerIntent.putExtra("PROFILE_ROLE", role);
                    tellerIntent.putExtra("PROFILE_DATE_JOINED", joinedDate);
                    tellerIntent.putExtra("PROFILE_EMAIL", email);
                    tellerIntent.putExtra("PROFILE_PHONE", phoneNO);
                    tellerIntent.putExtra("PROFILE_FIRSTNAME", firstName);
                    tellerIntent.putExtra("PROFILE_SURNAME", surname);
                    tellerIntent.putExtra("PROFILE_DOB", dob);
                    tellerIntent.putExtra("PROFILE_DATE_JOINED", joinedDate);
                    tellerIntent.putExtra("CUSTOMER_ID", customerID);
                    tellerIntent.putExtra("PROFILE_GENDER", gender);
                    tellerIntent.putExtra("PROFILE_ADDRESS", address);
                    tellerIntent.putExtra("PROFILE_ADDRESS", address);
                    tellerIntent.putExtra("PICTURE_URI", pictureLink);
                    tellerIntent.putExtra(PROFILE_ID, profileID);
                    tellerIntent.putExtra(PROFILE_USERNAME, userName);
                    tellerIntent.putExtra(PROFILE_PASSWORD, password);
                    tellerIntent.putExtra(PROFILE_OFFICE, office);
                    tellerIntent.putExtra(PROFILE_STATE, state);
                    tellerIntent.putExtra(PROFILE_ROLE, role);
                    tellerIntent.putExtra(PROFILE_DATE_JOINED, joinedDate);
                    tellerIntent.putExtra(PROFILE_PHONE, phoneNO);
                    tellerIntent.putExtra(PROFILE_FIRSTNAME, firstName);
                    tellerIntent.putExtra(PROFILE_SURNAME, surname);
                    tellerIntent.putExtra(PROFILE_DOB, dob);
                    tellerIntent.putExtra(PROFILE_DATE_JOINED, joinedDate);
                    tellerIntent.putExtra(CUSTOMER_ID, customerID);
                    tellerIntent.putExtra(PROFILE_GENDER, gender);
                    tellerIntent.putExtra(PROFILE_ADDRESS, address);
                    tellerIntent.putExtra(PICTURE_URI, pictureLink);

                    tellerIntent.putExtra("USER_NAME", userName);
                    tellerIntent.putExtra("USER_PASSWORD", password);
                    tellerIntent.putExtra("USER_OFFICE", office);
                    tellerIntent.putExtra("USER_STATE", state);
                    tellerIntent.putExtra("USER_ROLE", role);
                    tellerIntent.putExtra("USER_DATE_JOINED", joinedDate);
                    tellerIntent.putExtra("EMAIL_ADDRESS", email);
                    tellerIntent.putExtra("USER_PHONE", phoneNO);
                    tellerIntent.putExtra("USER_FIRSTNAME", firstName);
                    tellerIntent.putExtra("USER_SURNAME", surname);
                    tellerIntent.putExtra("USER_DOB", dob);
                    tellerIntent.putExtra("USER_DATE_JOINED", joinedDate);
                    tellerIntent.putExtra("CUSTOMER_ID", customerID);
                    tellerIntent.putExtra("USER_GENDER", gender);
                    tellerIntent.putExtra("USER_ADDRESS", address);
                    tellerIntent.putExtra("PICTURE_URI", pictureLink);
                    startActivity(tellerIntent);
                }
                if (dbRole.equalsIgnoreCase("Accountant")) {

                    Intent tellerIntent = new Intent(this, AcctantBackOffice.class);
                    tellerIntent.putExtra("PROFILE_ID", profileID);
                    tellerIntent.putExtra("PROFILE_USERNAME", userName);
                    tellerIntent.putExtra("PROFILE_PASSWORD", password);
                    tellerIntent.putExtra("PROFILE_OFFICE", office);
                    tellerIntent.putExtra("PROFILE_STATE", state);
                    tellerIntent.putExtra("PROFILE_ROLE", role);
                    tellerIntent.putExtra("PROFILE_DATE_JOINED", joinedDate);
                    tellerIntent.putExtra("PROFILE_EMAIL", email);
                    tellerIntent.putExtra("PROFILE_PHONE", phoneNO);
                    tellerIntent.putExtra("PROFILE_FIRSTNAME", firstName);
                    tellerIntent.putExtra("PROFILE_SURNAME", surname);
                    tellerIntent.putExtra("PROFILE_DOB", dob);
                    tellerIntent.putExtra("PROFILE_DATE_JOINED", joinedDate);
                    tellerIntent.putExtra("CUSTOMER_ID", customerID);
                    tellerIntent.putExtra("PROFILE_GENDER", gender);
                    tellerIntent.putExtra("PROFILE_ADDRESS", address);
                    tellerIntent.putExtra("PROFILE_ADDRESS", address);
                    tellerIntent.putExtra("PICTURE_URI", pictureLink);
                    tellerIntent.putExtra(PROFILE_ID, profileID);
                    tellerIntent.putExtra(PROFILE_USERNAME, userName);
                    tellerIntent.putExtra(PROFILE_PASSWORD, password);
                    tellerIntent.putExtra(PROFILE_OFFICE, office);
                    tellerIntent.putExtra(PROFILE_STATE, state);
                    tellerIntent.putExtra(PROFILE_ROLE, role);
                    tellerIntent.putExtra(PROFILE_DATE_JOINED, joinedDate);
                    tellerIntent.putExtra(PROFILE_PHONE, phoneNO);
                    tellerIntent.putExtra(PROFILE_FIRSTNAME, firstName);
                    tellerIntent.putExtra(PROFILE_SURNAME, surname);
                    tellerIntent.putExtra(PROFILE_DOB, dob);
                    tellerIntent.putExtra(PROFILE_DATE_JOINED, joinedDate);
                    tellerIntent.putExtra(CUSTOMER_ID, customerID);
                    tellerIntent.putExtra(PROFILE_GENDER, gender);
                    tellerIntent.putExtra(PROFILE_ADDRESS, address);
                    tellerIntent.putExtra(PICTURE_URI, pictureLink);

                    tellerIntent.putExtra("USER_NAME", userName);
                    tellerIntent.putExtra("USER_PASSWORD", password);
                    tellerIntent.putExtra("USER_OFFICE", office);
                    tellerIntent.putExtra("USER_STATE", state);
                    tellerIntent.putExtra("USER_ROLE", role);
                    tellerIntent.putExtra("USER_DATE_JOINED", joinedDate);
                    tellerIntent.putExtra("EMAIL_ADDRESS", email);
                    tellerIntent.putExtra("USER_PHONE", phoneNO);
                    tellerIntent.putExtra("USER_FIRSTNAME", firstName);
                    tellerIntent.putExtra("USER_SURNAME", surname);
                    tellerIntent.putExtra("USER_DOB", dob);
                    tellerIntent.putExtra("USER_DATE_JOINED", joinedDate);
                    tellerIntent.putExtra("CUSTOMER_ID", customerID);
                    tellerIntent.putExtra("USER_GENDER", gender);
                    tellerIntent.putExtra("USER_ADDRESS", address);
                    tellerIntent.putExtra("PICTURE_URI", pictureLink);
                    startActivity(tellerIntent);
                }
                if (dbRole.equalsIgnoreCase("Teller")) {

                    Intent tellerIntent = new Intent(this, TellerHomeChoices.class);
                    tellerIntent.putExtra(PROFILE_ID, profileID);
                    tellerIntent.putExtra(PROFILE_USERNAME, userName);
                    tellerIntent.putExtra(PROFILE_PASSWORD, password);
                    tellerIntent.putExtra(PROFILE_OFFICE, office);
                    tellerIntent.putExtra(PROFILE_STATE, state);
                    tellerIntent.putExtra(PROFILE_ROLE, role);
                    tellerIntent.putExtra(PROFILE_DATE_JOINED, joinedDate);
                    tellerIntent.putExtra(PROFILE_PHONE, phoneNO);
                    tellerIntent.putExtra(PROFILE_FIRSTNAME, firstName);
                    tellerIntent.putExtra(PROFILE_SURNAME, surname);
                    tellerIntent.putExtra(PROFILE_DOB, dob);
                    tellerIntent.putExtra(PROFILE_DATE_JOINED, joinedDate);
                    tellerIntent.putExtra(CUSTOMER_ID, customerID);
                    tellerIntent.putExtra(PROFILE_GENDER, gender);
                    tellerIntent.putExtra(PROFILE_ADDRESS, address);
                    tellerIntent.putExtra(PICTURE_URI, pictureLink);
                    tellerIntent.putExtra("PROFILE_ID", profileID);
                    tellerIntent.putExtra("PROFILE_USERNAME", userName);
                    tellerIntent.putExtra("PROFILE_PASSWORD", password);
                    tellerIntent.putExtra("PROFILE_OFFICE", office);
                    tellerIntent.putExtra("PROFILE_STATE", state);
                    tellerIntent.putExtra("PROFILE_ROLE", role);
                    tellerIntent.putExtra("PROFILE_DATE_JOINED", joinedDate);
                    tellerIntent.putExtra("PROFILE_EMAIL", email);
                    tellerIntent.putExtra("PROFILE_PHONE", phoneNO);
                    tellerIntent.putExtra("PROFILE_FIRSTNAME", firstName);
                    tellerIntent.putExtra("PROFILE_SURNAME", surname);
                    tellerIntent.putExtra("PROFILE_DOB", dob);
                    tellerIntent.putExtra("PROFILE_DATE_JOINED", joinedDate);
                    tellerIntent.putExtra("CUSTOMER_ID", customerID);
                    tellerIntent.putExtra("PROFILE_GENDER", gender);
                    tellerIntent.putExtra("PROFILE_ADDRESS", address);
                    tellerIntent.putExtra("PROFILE_ADDRESS", address);
                    tellerIntent.putExtra("PICTURE_URI", pictureLink);

                    tellerIntent.putExtra("USER_NAME", userName);
                    tellerIntent.putExtra("PROFILE_ID", profileID);
                    tellerIntent.putExtra("USER_PASSWORD", password);
                    tellerIntent.putExtra("USER_OFFICE", office);
                    tellerIntent.putExtra("USER_STATE", state);
                    tellerIntent.putExtra("USER_ROLE", role);
                    tellerIntent.putExtra("USER_DATE_JOINED", joinedDate);
                    tellerIntent.putExtra("EMAIL_ADDRESS", email);
                    tellerIntent.putExtra("USER_PHONE", phoneNO);
                    tellerIntent.putExtra("USER_FIRSTNAME", firstName);
                    tellerIntent.putExtra("USER_SURNAME", surname);
                    tellerIntent.putExtra("USER_DOB", dob);
                    tellerIntent.putExtra("USER_DATE_JOINED", joinedDate);
                    tellerIntent.putExtra("CUSTOMER_ID", customerID);
                    tellerIntent.putExtra("USER_GENDER", gender);
                    tellerIntent.putExtra("USER_ADDRESS", address);
                    tellerIntent.putExtra("PICTURE_URI", pictureLink);
                    startActivity(tellerIntent);
                }
                if (dbRole.equalsIgnoreCase("Admin")) {
                    Intent adminIntent = new Intent(this, AdminDrawerActivity.class);

                    adminIntent.putExtra(PROFILE_ID, profileID);
                    adminIntent.putExtra(PROFILE_USERNAME, userName);
                    adminIntent.putExtra(PROFILE_PASSWORD, password);
                    adminIntent.putExtra(PROFILE_OFFICE, office);
                    adminIntent.putExtra(PROFILE_STATE, state);
                    adminIntent.putExtra(PROFILE_ROLE, role);
                    adminIntent.putExtra(PROFILE_DATE_JOINED, joinedDate);
                    adminIntent.putExtra(PROFILE_PHONE, phoneNO);
                    adminIntent.putExtra(PROFILE_FIRSTNAME, firstName);
                    adminIntent.putExtra(PROFILE_SURNAME, surname);
                    adminIntent.putExtra(PROFILE_DOB, dob);
                    adminIntent.putExtra(PROFILE_DATE_JOINED, joinedDate);
                    adminIntent.putExtra(CUSTOMER_ID, customerID);
                    adminIntent.putExtra(PROFILE_GENDER, gender);
                    adminIntent.putExtra(PROFILE_ADDRESS, address);
                    adminIntent.putExtra(PICTURE_URI, pictureLink);
                    adminIntent.putExtra("PROFILE_ID", profileID);
                    adminIntent.putExtra("PROFILE_USERNAME", userName);
                    adminIntent.putExtra("PROFILE_PASSWORD", password);
                    adminIntent.putExtra("PROFILE_OFFICE", office);
                    adminIntent.putExtra("PROFILE_STATE", state);
                    adminIntent.putExtra("PROFILE_ROLE", role);
                    adminIntent.putExtra("PROFILE_DATE_JOINED", joinedDate);
                    adminIntent.putExtra("PROFILE_EMAIL", email);
                    adminIntent.putExtra("PROFILE_PHONE", phoneNO);
                    adminIntent.putExtra("PROFILE_FIRSTNAME", firstName);
                    adminIntent.putExtra("PROFILE_SURNAME", surname);
                    adminIntent.putExtra("PROFILE_DOB", dob);
                    adminIntent.putExtra("PROFILE_DATE_JOINED", joinedDate);
                    adminIntent.putExtra("CUSTOMER_ID", customerID);
                    adminIntent.putExtra("PROFILE_GENDER", gender);
                    adminIntent.putExtra("PROFILE_ADDRESS", address);
                    adminIntent.putExtra("PROFILE_ADDRESS", address);
                    adminIntent.putExtra("PICTURE_URI", pictureLink);

                    adminIntent.putExtra("USER_NAME", userName);
                    adminIntent.putExtra("PROFILE_ID", profileID);
                    adminIntent.putExtra("USER_PASSWORD", password);
                    adminIntent.putExtra("USER_OFFICE", office);
                    adminIntent.putExtra("USER_STATE", state);
                    adminIntent.putExtra("USER_ROLE", role);
                    adminIntent.putExtra("USER_DATE_JOINED", joinedDate);
                    adminIntent.putExtra("EMAIL_ADDRESS", email);
                    adminIntent.putExtra("USER_PHONE", phoneNO);
                    adminIntent.putExtra("USER_FIRSTNAME", firstName);
                    adminIntent.putExtra("USER_SURNAME", surname);
                    adminIntent.putExtra("USER_DOB", dob);
                    adminIntent.putExtra("USER_DATE_JOINED", joinedDate);
                    adminIntent.putExtra("CUSTOMER_ID", customerID);
                    adminIntent.putExtra("USER_GENDER", gender);
                    adminIntent.putExtra("USER_ADDRESS", address);
                    adminIntent.putExtra("PICTURE_URI", pictureLink);
                    startActivity(adminIntent);

                }
                if (dbRole.equalsIgnoreCase("Customer")) {
                    //btnCustomer.setVisibility(View.VISIBLE);
                    Intent customerIntent = new Intent(this, NewCustomerDrawer.class);

                    customerIntent.putExtra(PROFILE_ID, profileID);
                    customerIntent.putExtra(PROFILE_PASSWORD, password);
                    customerIntent.putExtra(PROFILE_USERNAME, userName);
                    customerIntent.putExtra(PROFILE_OFFICE, office);
                    customerIntent.putExtra(PROFILE_STATE, state);
                    customerIntent.putExtra(PROFILE_ROLE, role);
                    customerIntent.putExtra(PROFILE_DATE_JOINED, joinedDate);
                    customerIntent.putExtra(PROFILE_PHONE, phoneNO);
                    customerIntent.putExtra(PROFILE_FIRSTNAME, firstName);
                    customerIntent.putExtra(PROFILE_SURNAME, surname);
                    customerIntent.putExtra(PROFILE_DOB, dob);
                    customerIntent.putExtra(PROFILE_DATE_JOINED, joinedDate);
                    customerIntent.putExtra(CUSTOMER_ID, customerID);
                    customerIntent.putExtra(PROFILE_GENDER, gender);
                    customerIntent.putExtra(PROFILE_ADDRESS, address);
                    customerIntent.putExtra(PICTURE_URI, pictureLink);
                    customerIntent.putExtra("PROFILE_ID", profileID);
                    customerIntent.putExtra("PROFILE_USERNAME", userName);
                    customerIntent.putExtra("PROFILE_PASSWORD", password);
                    customerIntent.putExtra("PROFILE_OFFICE", office);
                    customerIntent.putExtra("PROFILE_STATE", state);
                    customerIntent.putExtra("PROFILE_ROLE", role);
                    customerIntent.putExtra("PROFILE_DATE_JOINED", joinedDate);
                    customerIntent.putExtra("PROFILE_EMAIL", email);
                    customerIntent.putExtra("PROFILE_PHONE", phoneNO);
                    customerIntent.putExtra("PROFILE_FIRSTNAME", firstName);
                    customerIntent.putExtra("PROFILE_SURNAME", surname);
                    customerIntent.putExtra("PROFILE_DOB", dob);
                    customerIntent.putExtra("PROFILE_DATE_JOINED", joinedDate);
                    customerIntent.putExtra("CUSTOMER_ID", customerID);
                    customerIntent.putExtra("PROFILE_GENDER", gender);
                    customerIntent.putExtra("PROFILE_ADDRESS", address);
                    customerIntent.putExtra("PROFILE_ADDRESS", address);
                    customerIntent.putExtra("PICTURE_URI", pictureLink);

                    customerIntent.putExtra("USER_NAME", userName);
                    customerIntent.putExtra("PROFILE_ID", profileID);
                    customerIntent.putExtra("USER_PASSWORD", password);
                    customerIntent.putExtra("USER_OFFICE", office);
                    customerIntent.putExtra("USER_STATE", state);
                    customerIntent.putExtra("USER_ROLE", role);
                    customerIntent.putExtra("USER_DATE_JOINED", joinedDate);
                    customerIntent.putExtra("EMAIL_ADDRESS", email);
                    customerIntent.putExtra("USER_PHONE", phoneNO);
                    customerIntent.putExtra("USER_FIRSTNAME", firstName);
                    customerIntent.putExtra("USER_SURNAME", surname);
                    customerIntent.putExtra("USER_DOB", dob);
                    customerIntent.putExtra("USER_DATE_JOINED", joinedDate);
                    customerIntent.putExtra("CUSTOMER_ID", customerID);
                    customerIntent.putExtra("USER_GENDER", gender);
                    customerIntent.putExtra("USER_ADDRESS", address);
                    customerIntent.putExtra("PICTURE_URI", pictureLink);
                    startActivity(customerIntent);
                }


            }


        }



        if (userExtras != null) {
            if(sharedPref==null){

                machinePref = sharedPref.getString("Machine", "");
                profileID = sharedPref.getInt("PROFILE_ID", 0);
                userName = sharedPref.getString("PROFILE_USERNAME", "");
                office = sharedPref.getString("PROFILE_OFFICE", "");
                state = sharedPref.getString("PROFILE_STATE", "");
                role = sharedPref.getString("PROFILE_ROLE", "");
                password = sharedPref.getString("PROFILE_PASSWORD", "");
                joinedDate = sharedPref.getString("PROFILE_DATE_JOINED", "");
                surname = sharedPref.getString("PROFILE_SURNAME", "");
                email = sharedPref.getString("PROFILE_EMAIL", "");
                phoneNO = sharedPref.getString("PROFILE_PHONE", "");
                firstName = sharedPref.getString("PROFILE_FIRSTNAME", "");
                dob = sharedPref.getString("PROFILE_DOB", "");
                customerID = sharedPref.getInt("CUSTOMER_ID", 0);
                gender = sharedPref.getString("PROFILE_GENDER", "");
                address = sharedPref.getString("PROFILE_ADDRESS", "");
                pictureLink = Uri.parse(sharedPref.getString("PICTURE_URI", ""));


            }


        }
        if (userExtras == null) {
            if(sharedPref !=null){

                try {
                    machinePref = sharedPref.getString("Machine", "");
                    profileID = sharedPref.getInt("PROFILE_ID", 0);
                    userName = sharedPref.getString("PROFILE_USERNAME", "");
                    office = sharedPref.getString("PROFILE_OFFICE", "");
                    state = sharedPref.getString("PROFILE_STATE", "");
                    role = sharedPref.getString("PROFILE_ROLE", "");
                    password = sharedPref.getString("PROFILE_PASSWORD", "");
                    joinedDate = sharedPref.getString("PROFILE_DATE_JOINED", "");
                    surname = sharedPref.getString("PROFILE_SURNAME", "");
                    email = sharedPref.getString("PROFILE_EMAIL", "");
                    phoneNO = sharedPref.getString("PROFILE_PHONE", "");
                    firstName = sharedPref.getString("PROFILE_FIRSTNAME", "");
                    dob = sharedPref.getString("PROFILE_DOB", "");
                    customerID = sharedPref.getInt("CUSTOMER_ID", 0);
                    gender = sharedPref.getString("PROFILE_GENDER", "");
                    address = sharedPref.getString("PROFILE_ADDRESS", "");
                    pictureLink = Uri.parse(sharedPref.getString("PICTURE_URI", ""));


                } catch (NullPointerException e) {
                    e.printStackTrace();
                }



            }

        }
        /*else {
            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    user = firebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        SharedPreferences sharedPreferences=getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("Machine",machinePref);
                        editor.putInt("PROFILE_ID",profileID);
                        editor.putString("USER_NAME",userName);
                        editor.putString("USER_OFFICE",office);
                        editor.putString("USER_ROLE",role);
                        editor.putString("USER_STATE",state);
                        editor.putString("TOKEN",user.getUid());
                        editor.apply();
                        Intent intent=new Intent(LoginDirectorActivity.this,LoginDirectorActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent=new Intent(LoginDirectorActivity.this,SignTabMainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
            };
        }*/
    }
}