package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Accountant.BranchMPayments;
import com.skylightapp.Adapters.OfficeAdapter;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.AdminUserDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.StocksDAO;
import com.skylightapp.Inventory.UpDateStocksCode;
import com.skylightapp.MapAndLoc.EmergencyReport;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.Date;

public class AdminRepostingAct extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Bundle updateBundle;
    private Customer customer;
    private long customerID;
    private Profile userProfile;
    private long userProfileID;
    private CustomerManager teller;
    private long tellerID;
    private AdminUser adminUser;
    private long adminUserID;
    DBHelper dbHelper;
    private AppCompatButton btnRepostAdmin;
    AppCompatEditText edtAdminID;
    int adminID;
    String selectedOffice;
    Spinner spnRepostAdmin;
    private ArrayAdapter<AdminUser> adminUserArrayAdapter;
    Spinner spnAdminUsers;
    int selectedTellerIndex,selectedAdminIndex;
    private ArrayList<AdminUser> adminUsers;
    private MarketBusiness marketBusiness;
    private long bizID;
    private static final String PREF_NAME = "skylight";
    String SharedPrefUserMachine;
    String SharedPrefUserName;
    private int profileID;
    String SharedPrefProfileID,stringLatLng;
    SharedPreferences.Editor editor;
    Gson gson, gson1,gson2;
    private SharedPreferences userPreferences;
    private int selectedOfficeIndex,officeID;
    private OfficeBranch officeB;
    private OfficeAdapter officeBranchAdapter;
    private ArrayList<OfficeBranch> bizOffices;
    String json, json1, json2,userName, userPassword, userMachine, dateOfToday, selectedType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_admin_reposting);
        updateBundle= new Bundle();
        bizOffices= new ArrayList<>();
        dbHelper= new DBHelper(this);
        spnAdminUsers = findViewById(R.id.spn_admin33);
        spnRepostAdmin = findViewById(R.id.spn_repost_office);
        btnRepostAdmin = findViewById(R.id.confirm_Reposting);
        customer= new Customer();
        userProfile= new Profile();
        teller= new CustomerManager();
        adminUser= new AdminUser();
        updateBundle= getIntent().getExtras();
        adminUsers = new ArrayList<AdminUser>();
        gson1 = new Gson();
        gson = new Gson();
        dbHelper = new DBHelper(this);
        userProfile = new Profile();
        customer = new Customer();
        marketBusiness= new MarketBusiness();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefProfileID= String.valueOf(userPreferences.getLong("PROFILE_ID", 0));
        json1 = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson1.fromJson(json1, Profile.class);
        userName=userPreferences.getString("PROFILE_USERNAME", "");
        userPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        userMachine=userPreferences.getString("PROFILE_ROLE", "");
        profileID=userPreferences.getInt("PROFILE_ID", 0);

        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson2.fromJson(json2, MarketBusiness.class);

        spnAdminUsers.setOnItemSelectedListener(this);
        spnRepostAdmin.setOnItemSelectedListener(this);
        if(updateBundle !=null){
            userProfile=updateBundle.getParcelable("Profile");
            customer=updateBundle.getParcelable("Customer");
        }
        if(marketBusiness !=null){
            bizID=marketBusiness.getBusinessID();
            bizOffices=marketBusiness.getOfficeBranches();
        }
        officeBranchAdapter = new OfficeAdapter(AdminRepostingAct.this, bizOffices);
        spnRepostAdmin.setAdapter(officeBranchAdapter);
        spnRepostAdmin.setSelection(0);
        selectedOfficeIndex = spnRepostAdmin.getSelectedItemPosition();

        try {
            officeB = bizOffices.get(selectedOfficeIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }
        if(officeB !=null){
            selectedOffice=officeB.getOfficeBranchName();
            officeID=officeB.getOfficeBranchID();
        }
        if(bizOffices.size()==0){
            officeB=marketBusiness.getMBBranchOffice();
        }

        AdminUserDAO adminUserDAO= new AdminUserDAO(this);
        adminUsers=adminUserDAO.getAllAdminSpinner();

        selectedAdminIndex = spnAdminUsers.getSelectedItemPosition();

        try {
            adminUserArrayAdapter = new ArrayAdapter<AdminUser>(AdminRepostingAct.this, android.R.layout.simple_spinner_item, adminUsers);
            adminUserArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnAdminUsers.setAdapter(adminUserArrayAdapter);
            spnAdminUsers.setSelection(0);

            adminUser = adminUsers.get(selectedAdminIndex);
            try {
                if (adminUser != null) {
                    adminID=adminUser.getAdminID();

                }
            } catch (NullPointerException e) {
                System.out.println("Oops!");
            }
        } catch (Exception e) {
            System.out.println("Oops!");
        }
        if (adminUser != null) {
            adminID=adminUser.getAdminID();

        }
        btnRepostAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adminUser != null) {
                    adminID=adminUser.getAdminID();
                    adminUser.setAdminOffice(selectedOffice);
                    adminUser.addBusinessID(bizID);

                }
                AdminUserDAO adminUserDAO= new AdminUserDAO(AdminRepostingAct.this);
                adminUserDAO.repostAdmin(adminID,selectedOffice);
                sendNotification();

            }
        });
    }
    private void sendNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Admin Reposting Successful")
                        .setContentText("The Admin User has been reposted");

        Intent notificationIntent = new Intent(this, SuperAdminOffice.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(SuperAdminOffice.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    public void superHome(View view) {
        Intent intent8 = new Intent(this, SuperAdminOffice.class);
        startActivity(intent8);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();
        switch (id) {
            case R.id.spn_admin33:
                AdminUserDAO adminUserDAO= new AdminUserDAO(AdminRepostingAct.this);
                adminUsers=adminUserDAO.getAllAdminSpinner();

                selectedAdminIndex = spnAdminUsers.getSelectedItemPosition();

                try {
                    adminUserArrayAdapter = new ArrayAdapter<AdminUser>(AdminRepostingAct.this, android.R.layout.simple_spinner_item, adminUsers);
                    adminUserArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnAdminUsers.setAdapter(adminUserArrayAdapter);
                    spnAdminUsers.setSelection(0);

                    adminUser = adminUsers.get(selectedAdminIndex);
                    try {
                        if (adminUser != null) {
                            adminID=adminUser.getAdminID();

                        }
                    } catch (NullPointerException e) {
                        System.out.println("Oops!");
                    }
                } catch (Exception e) {
                    System.out.println("Oops!");
                }

                break;
            case R.id.spn_repost_office:
                officeBranchAdapter = new OfficeAdapter(AdminRepostingAct.this, bizOffices);
                spnRepostAdmin.setAdapter(officeBranchAdapter);
                spnRepostAdmin.setSelection(0);
                selectedOfficeIndex = spnRepostAdmin.getSelectedItemPosition();

                try {
                    officeB = bizOffices.get(selectedOfficeIndex);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Oops!");
                }
                if(officeB !=null){
                    selectedOffice=officeB.getOfficeBranchName();
                    officeID=officeB.getOfficeBranchID();
                }
                if(bizOffices.size()==0){
                    officeB=marketBusiness.getMBBranchOffice();
                }
                Toast.makeText(AdminRepostingAct.this, "New Office: "+ selectedOffice,Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}