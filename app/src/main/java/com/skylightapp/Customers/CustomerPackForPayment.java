package com.skylightapp.Customers;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.skylightapp.Adapters.PackageRecyclerAdapter;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_ID;

public class CustomerPackForPayment extends AppCompatActivity implements PackageRecyclerAdapter.OnItemsClickListener{
    DBHelper dbHelper;
    Customer customer;
    private List<SkyLightPackage> packages;
    protected ListView lvItems;
    Bundle paymentBundle;
    String incomplete,inProgress;
    int packageID;
    double grandTotal,amountSoFar,amountRem;
    SharedPreferences userPreferences;
    private Gson gson;
    private String json;
    private Profile userProfile;
    Bundle packBundle,bundle;
    int customerID;
    Account account;
    private PackageRecyclerAdapter packageRecyclerAdapter;
    private RecyclerView recyclerView;
    private  SkyLightPackage skyLightPackage;
    private ActivityResultLauncher<Intent> payNowStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),

            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Intent intent = result.getData();
                            if (intent != null) {
                                paymentBundle = intent.getBundleExtra("paymentBundle");
                            }
                            if(paymentBundle !=null){
                                skyLightPackage=paymentBundle.getParcelable("SkyLightPackage");
                                amountSoFar=paymentBundle.getDouble("REPORT_AMOUNT_COLLECTED_SO_FAR");
                                amountRem=paymentBundle.getDouble("REPORT_AMOUNT_REMAINING");
                                grandTotal=skyLightPackage.getGrandTotal();
                                packageID=paymentBundle.getInt(PACKAGE_ID);
                            }
                            if(amountSoFar==grandTotal){
                                dbHelper.updatePackage(customerID,packageID,0,"Completed");

                            }

                            Toast.makeText(CustomerPackForPayment.this, "Activity returned ok", Toast.LENGTH_SHORT).show();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(CustomerPackForPayment.this, "Activity canceled", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cus_pack_for_payment);
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        customer = new Customer();
        userProfile=new Profile();
        packBundle=new Bundle();
        bundle=new Bundle();
        dbHelper= new DBHelper(this);
        packages = new ArrayList<>();
        userProfile = gson.fromJson(json, Profile.class);
        recyclerView = findViewById(R.id.recyclerViewPackList);

        packBundle = getIntent().getExtras();
        if(packBundle !=null){
            customer=packBundle.getParcelable("Customer");

        }
        else {
            if(userProfile !=null){
                customer=userProfile.getTimelineCustomer();
            }

            if (customer !=null){
                customerID = customer.getCusUID();

            }

        }
        packages=dbHelper.getCustomerCompletePack(customerID,"completed");

        FloatingActionButton dashboard = findViewById(R.id.btnBackP);


        LinearLayoutManager layoutManagerC
                = new LinearLayoutManager(CustomerPackForPayment.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManagerC);

        packageRecyclerAdapter = new PackageRecyclerAdapter(CustomerPackForPayment.this, packages);
        recyclerView.setAdapter(packageRecyclerAdapter);
        DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(recyclerView.getContext(),
                layoutManagerC.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration7);
        packageRecyclerAdapter.notifyDataSetChanged();
        /*recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/



    }
    private void sendNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Skylight Message")
                        .setContentText("The package you selected has been cleared");

        Intent notificationIntent = new Intent(this, NewCustomerDrawer.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NewCustomerDrawer.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
    private void requestForPayment(Bundle bundle) {
        Intent intent = new Intent(this, RequestPaymentAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    private void requestForItem(Bundle bundle) {
        Intent intent = new Intent(this, RequestItemAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("What do you want to do?");

        final List<String> lables1 = new ArrayList<>();
        lables1.add("Continue Savings");
        lables1.add("Back to your Dashboard");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, lables1);
        builder.setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CustomerPackForPayment.this,"You have selected " + lables1.get(which), Toast.LENGTH_LONG).show();
                if(lables1.get(which).equalsIgnoreCase("Continue Savings")){
                    continueSaving();


                }
                if(lables1.get(which).equalsIgnoreCase("Back to your Dashboard")){
                    home();


                }
                // requestForPayment();
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void continueSaving() {

        Intent intent = new Intent(getBaseContext(), OldPackCusAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Go ahead and do savings!");

        final List<String> lables = new ArrayList<>();
        //lables.add("Use Our Automatic System");
        lables.add("Continue to savings area");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, lables);
        builder.setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CustomerPackForPayment.this,"You have selected " + lables.get(which), Toast.LENGTH_LONG).show();
                if(lables.get(which).equalsIgnoreCase("Use Our Automatic System")){
                    Intent intent = new Intent(getBaseContext(), SavingsStandingOrder.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }
                if(lables.get(which).equalsIgnoreCase("Do Manual Payment")){
                    Intent intent = new Intent(getBaseContext(), PayNowActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                dialog.dismiss();
            }
        });
        builder.show();*/


    }

    private void home() {
        Intent intent = new Intent(this, NewCustomerDrawer.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Bundle", paymentBundle);
        intent.putExtras(paymentBundle);
        startActivity(intent);


    }


    @Override
    public void onItemClick(SkyLightPackage skyLightPackage) {
        if(skyLightPackage !=null){
            packageID = skyLightPackage.getPackageId();
            double amountContributedSoFar = skyLightPackage.getAmount_collected();
            grandTotal = skyLightPackage.getGrandTotal();
            double packageAmount=skyLightPackage.getDailyAmount();
            account = skyLightPackage.getAccount();
            long acctID=account.getAcctID();
            String status=skyLightPackage.getPackageStatus();
            customer = skyLightPackage.getCustomer();
            long customerID=customer.getCusUID();
            double amtRem = skyLightPackage.getAmountRemaining();
            int daysRem = skyLightPackage.getRemainingDays();
            String type= String.valueOf(skyLightPackage.getPackageType());
            bundle.putParcelable("SkyLightPackage",skyLightPackage);
            bundle.putParcelable("Customer",customer);

            if(amountContributedSoFar==grandTotal && type.equalsIgnoreCase("Savings") && status.equalsIgnoreCase("Completed")){
                requestForPayment(bundle);

            }
            if(amountContributedSoFar==grandTotal && type.equalsIgnoreCase("Item Purchase") && status.equalsIgnoreCase("Completed")){
                requestForItem(bundle);

            }
            if(amountContributedSoFar==grandTotal && type.equalsIgnoreCase("Promo") && status.equalsIgnoreCase("Completed")){
                requestForItem(bundle);

            }
            if(amountContributedSoFar==grandTotal && status.equalsIgnoreCase("Ended")){
                Toast.makeText(CustomerPackForPayment.this,"The package you selected has been cleared" , Toast.LENGTH_LONG).show();
                sendNotification();
                finish();

            }else {

                if(amountContributedSoFar<grandTotal){
                    showToast("You package is not complete, You can  continue saving");
                    double accountBalance = account.getAccountBalance();
                    paymentBundle = new Bundle();
                    paymentBundle.putLong("PACKAGE_ID", packageID);
                    paymentBundle.putLong("Package Id", packageID);
                    paymentBundle.putLong("Account ID", acctID);
                    paymentBundle.putLong("CUSTOMER_ID", customerID);
                    paymentBundle.putParcelable("Account", account);
                    paymentBundle.putInt("Number of Days", daysRem);
                    paymentBundle.putInt("Day Remaining", 0);
                    paymentBundle.putString("Surname", "Surname");
                    paymentBundle.putDouble("REPORT_AMOUNT_COLLECTED_SO_FAR", amountContributedSoFar);
                    paymentBundle.putDouble("REPORT_AMOUNT_REMAINING", amtRem);
                    paymentBundle.putDouble("Amount", packageAmount);
                    paymentBundle.putDouble("Total", amtRem);
                    paymentBundle.putDouble("PACKAGE_VALUE", grandTotal);
                    paymentBundle.putDouble("ACCOUNT_BALANCE", accountBalance);
                    paymentBundle.putDouble("Package Account Balance", accountBalance);
                    paymentBundle.putParcelable("Package", skyLightPackage);
                    paymentBundle.putParcelable("SkyLightPackage", skyLightPackage);
                    paymentBundle.putParcelable("Account", account);
                    paymentBundle.putParcelable("Customer", customer);
                    Intent intent = new Intent(CustomerPackForPayment.this, OldPackCusAct.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtras(paymentBundle);
                    startActivity(intent);

                }



            }


        }


    }
}