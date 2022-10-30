package com.skylightapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import com.skylightapp.Accountant.AcctantBackOffice;
import com.skylightapp.Admins.AdminDrawerActivity;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.GroupAccountDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Interfaces.ProfileDao;
import com.skylightapp.MapAndLoc.ResponseTeamOffice;
import com.skylightapp.Markets.BizRegulOffice;
import com.skylightapp.Markets.MarketAdminOffice;
import com.skylightapp.Markets.MarketBizDonorOffice;
import com.skylightapp.Markets.MarketBizOffice;
import com.skylightapp.Markets.MarketBizPOffice;
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


public class LoginDirAct extends AppCompatActivity {
    int profileID,customerID;
    String machinePref;
    Uri pictureLink;
    SharedPreferences sharedPref;
    Bundle userExtras;
    private static final String PREF_NAME = "awajima";
    private DBHelper dbHelper;
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
        ProfDAO profileDao= new ProfDAO(LoginDirAct.this);

        email = sharedPref.getString("EMAIL_ADDRESS", "");
        pictureLink = Uri.parse(sharedPref.getString("PICTURE_URI", ""));
        dbRole=profileDao.getProfileRoleByUserNameAndPassword(userName,password);

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

                    tellerIntent.putExtra("PROFILE_ID", profileID);
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
                    adminIntent.putExtra("PROFILE_ID", profileID);
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
                    customerIntent.putExtra("PROFILE_ID", profileID);
                    customerIntent.putExtra("PICTURE_URI", pictureLink);
                    startActivity(customerIntent);
                }


                if (dbRole.equalsIgnoreCase("MarketBusiness")) {
                    Intent adminIntent = new Intent(this, MarketBizOffice.class);
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
                    adminIntent.putExtra("PROFILE_ID", profileID);
                    adminIntent.putExtra("PICTURE_URI", pictureLink);
                    startActivity(adminIntent);

                }

                if (dbRole.equalsIgnoreCase("MarketBizRegulator")) {
                    Intent adminIntent = new Intent(this, BizRegulOffice.class);

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

                    adminIntent.putExtra("PROFILE_ID", profileID);
                    adminIntent.putExtra("PICTURE_URI", pictureLink);
                    startActivity(adminIntent);

                }

                if (dbRole.equalsIgnoreCase("MarketBizPartner")) {
                    Intent adminIntent = new Intent(this, MarketBizPOffice.class);

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
                    adminIntent.putExtra("PROFILE_ID", profileID);
                    adminIntent.putExtra("PICTURE_URI", pictureLink);
                    startActivity(adminIntent);

                }
                if (dbRole.equalsIgnoreCase("MarketAdmin")) {
                    Intent adminIntent = new Intent(this, MarketAdminOffice.class);

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
                    adminIntent.putExtra("PROFILE_ID", profileID);
                    adminIntent.putExtra("PICTURE_URI", pictureLink);
                    startActivity(adminIntent);

                }
                if (dbRole.equalsIgnoreCase("MarketBizDonor")) {
                    Intent adminIntent = new Intent(this, MarketBizDonorOffice.class);

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
                    adminIntent.putExtra("PROFILE_ID", profileID);
                    adminIntent.putExtra("PICTURE_URI", pictureLink);
                    startActivity(adminIntent);

                }
                if (dbRole.equalsIgnoreCase("ResponseTeam")) {
                    Intent adminIntent = new Intent(this, ResponseTeamOffice.class);

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
                    adminIntent.putExtra("PROFILE_ID", profileID);
                    adminIntent.putExtra("PICTURE_URI", pictureLink);
                    startActivity(adminIntent);

                }

                if (dbRole.equalsIgnoreCase("AwajimaSuperAdmin")) {
                    Intent adminIntent = new Intent(this, SuperAdminOffice.class);

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
                    adminIntent.putExtra("PROFILE_ID", profileID);
                    adminIntent.putExtra("PICTURE_URI", pictureLink);
                    startActivity(adminIntent);

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
    }
}