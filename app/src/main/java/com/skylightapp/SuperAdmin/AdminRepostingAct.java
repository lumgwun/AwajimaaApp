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
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_admin_reposting);
        updateBundle= new Bundle();
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

        spnAdminUsers.setOnItemSelectedListener(this);
        spnRepostAdmin.setOnItemSelectedListener(this);
        if(updateBundle !=null){
            userProfile=updateBundle.getParcelable("Profile");
            customer=updateBundle.getParcelable("Customer");
        }
        spnRepostAdmin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOffice = spnRepostAdmin.getSelectedItem().toString();
                selectedOffice = (String) parent.getSelectedItem();
                Toast.makeText(AdminRepostingAct.this, "New Office: "+ selectedOffice,Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        adminUsers=dbHelper.getAllAdminSpinner();

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

                }
                dbHelper.repostAdmin(adminID,selectedOffice);
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
                adminUsers=dbHelper.getAllAdminSpinner();

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
                selectedOffice = spnRepostAdmin.getSelectedItem().toString();
                selectedOffice = (String) adapterView.getSelectedItem();
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