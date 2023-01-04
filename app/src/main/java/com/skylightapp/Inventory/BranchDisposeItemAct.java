package com.skylightapp.Inventory;

import static com.skylightapp.Database.DBHelper.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.StocksDAO;
import com.skylightapp.LoginDirAct;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.UpdatePackageAct;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class BranchDisposeItemAct extends AppCompatActivity {
    Bundle itemCollectionBundle;
    private MarketBizPackage awajimaPackage;
    private String packageType, cusOfficeBranch,packageName,customerName,customerPhoneNumber,adminOfficeBranch;
    private long packageCode,customerID,packageID;
    private Customer customer;
    private int itemRemCount;
    private AppCompatSpinner spnItemPacks;
    private AppCompatTextView txtItemCount, txtcusForCollection,txtPackageName;
    AppCompatButton btnApproveCollection;
    DBHelper dbHelper;
    private Gson gson,gson1;
    private String json,json1;
    private  Bundle bundle;
    private static final String PREF_NAME = "awajima";
    private OfficeBranch bizBranchOffice;

    private Profile userProfile;
    SharedPreferences sharedPreferences;
    String SharedPrefUserPassword,SharedPrefCusID,SharedPrefUserMachine,SharedPrefState,SharedPrefOffice,
            SharedPrefAddress,SharedPrefJoinedDate,SharedPrefGender,
            SharedPrefRole,SharedPrefDOB,SharedPrefPhone,SharedPrefEmail,SharedPrefProfileID,
            SharedPrefSurName,SharedPrefFirstName,itemName,SharedPrefUserName,SharedPrefAcctNo,customerId,SharedPrefBankNo,SharedPrefAcctBalance,SharedPrefAcctName,SharedPrefType,SharedPrefBank
            ;
    private SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_branch_item_collectn);
        bundle= new Bundle();
        bizBranchOffice= new OfficeBranch();
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=sharedPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=sharedPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=sharedPreferences.getString("machine", "");
        SharedPrefProfileID=sharedPreferences.getString("PROFILE_ID", "");
        SharedPrefSurName=sharedPreferences.getString("PROFILE_SURNAME", "");
        SharedPrefFirstName=sharedPreferences.getString("PROFILE_FIRSTNAME", "");
        SharedPrefEmail=sharedPreferences.getString("PROFILE_EMAIL", "");
        SharedPrefPhone=sharedPreferences.getString("PROFILE_PHONE", "");
        SharedPrefAddress=sharedPreferences.getString("PROFILE_ADDRESS", "");
        SharedPrefDOB=sharedPreferences.getString("PROFILE_DOB", "");
        SharedPrefRole=sharedPreferences.getString("PROFILE_ROLE", "");
        SharedPrefGender=sharedPreferences.getString("PROFILE_GENDER", "");
        SharedPrefJoinedDate=sharedPreferences.getString("PROFILE_DATE_JOINED", "");
        SharedPrefOffice=sharedPreferences.getString("PROFILE_OFFICE", "");
        SharedPrefState=sharedPreferences.getString("PROFILE_STATE", "");
        gson = new Gson();
        gson1 = new Gson();
        json = sharedPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = sharedPreferences.getString("LastOfficeBranchUsed", "");
        bizBranchOffice = gson1.fromJson(json1, OfficeBranch.class);

        dbHelper= new DBHelper(this);
        StocksDAO stocksDAO= new StocksDAO(this);
        itemCollectionBundle=getIntent().getExtras();
        txtItemCount = findViewById(R.id.stockItemCount);
        txtcusForCollection = findViewById(R.id.cusForCollection);
        txtPackageName = findViewById(R.id.packageName77);
        btnApproveCollection = findViewById(R.id.submitItemApproval);
        if(itemCollectionBundle !=null){
            //itemPackage=itemCollectionBundle.getString("Package");
            awajimaPackage =itemCollectionBundle.getParcelable("MarketBizPackage");
            packageCode=itemCollectionBundle.getLong("PackageCode");
            packageType=itemCollectionBundle.getString("Type");
            customer=itemCollectionBundle.getParcelable("Customer");
            itemName=itemCollectionBundle.getString("ItemName");

        }
        if(bizBranchOffice !=null){
            adminOfficeBranch=bizBranchOffice.getOfficeBranchName();
        }
        if(awajimaPackage !=null){
            packageName= awajimaPackage.getPackageName();
            packageID= awajimaPackage.getPackID();
        }

        if(customer !=null){
            customerID=customer.getCusUID();
            cusOfficeBranch =customer.getCusOfficeBranch();
            customerName=customer.getCusSurname()+""+customer.getCusFirstName();
            customerPhoneNumber=customer.getCusPhoneNumber();

        }
        
        txtPackageName.setText("Package:"+itemName);
        if(dbHelper !=null){
            try {

                if(sqLiteDatabase !=null){
                    sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            if(stocksDAO !=null){
                try {
                    itemRemCount=stocksDAO.getStockItemCountForBranch(packageName, cusOfficeBranch);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }


        }
        
        
        txtcusForCollection.setText("Customer:"+customerName);
        if(itemRemCount>0){
            txtItemCount.setText("Branch"+""+packageName+"Count:"+itemRemCount);
        }else {
            if(itemRemCount==0){
                txtItemCount.setText("This Office Branch has no Package left for collection");

            }
        }
        btnApproveCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemRemCount>0){
                    if(adminOfficeBranch.equalsIgnoreCase(cusOfficeBranch)){
                        sendSMSMessage();
                        bundle.putParcelable("MarketBizPackage", awajimaPackage);
                        bundle.putInt("ItemCount",itemRemCount);
                        bundle.putString("Office", cusOfficeBranch);
                        Intent packageIntent = new Intent(BranchDisposeItemAct.this, UpdatePackageAct.class);
                        packageIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        packageIntent.putExtras(bundle);
                        startActivity(packageIntent);

                    }else {
                        Toast.makeText(BranchDisposeItemAct.this, "This Admin Manager is not Authorised for this Branch" , Toast.LENGTH_LONG).show();
                        sendNotification();
                    }

                }else {
                    if(itemRemCount==0){
                        tellNoItem();
                        Toast.makeText(BranchDisposeItemAct.this, "There is no package left in the Office" , Toast.LENGTH_LONG).show();


                    }
                }
            }
        });
    }
    private void tellNoItem() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("No Item For Collection")
                        .setContentText("There is no Item for the Customer");

        Intent notificationIntent = new Intent(this, LoginDirAct.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(LoginDirAct.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
    private void sendNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Customer from Another Branch")
                        .setContentText("This Admin is not authorised for this Branch");

        Intent notificationIntent = new Intent(this, LoginDirAct.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(LoginDirAct.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
    protected void sendSMSMessage() {
        String welcomeMessage = "Your Package Collection Code is:"+packageCode;
       Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(customerPhoneNumber),
                new com.twilio.type.PhoneNumber("234" + customerPhoneNumber),
                welcomeMessage)
                .create();

    }

    public void doCollection(View view) {

    }
}