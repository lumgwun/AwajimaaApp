package com.skylightapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.skylightapp.Adapters.SuperSkylightPackageAdapter;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Customers.TellerForCusLoanAct;
import com.skylightapp.Customers.RequestPaymentAct;
import com.skylightapp.Customers.SavingsPackAdapt;
import com.skylightapp.Database.DBHelper;

import java.util.ArrayList;
import java.util.List;

import static com.skylightapp.Classes.Account.ACCOUNT_BALANCE;
import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_AMOUNT_COLLECTED_SO_FAR;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_AMOUNT_REMAINING;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_ID;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_VALUE;

public class CustomerPackageListAct extends AppCompatActivity implements  SuperSkylightPackageAdapter.OnItemsClickListener {
    Long accNoStart = 599999999L ;
    Long accNoCount = 0L;

    List<Customer> customersList;
    ArrayList<SkyLightPackage> packageArrayList;
    private Gson gson;
    private String json;

    private Profile userProfile;
    private ListView listView;
    int customerID;
    private SavingsPackAdapt skylightPackageAdapter;
    Customer customer;
    SharedPreferences userPreferences;
    Bundle packBundle,paymentBundle;
    String incomplete,inProgress;
    long packageID;
    double grandTotal;
    Transaction.TRANSACTION_TYPE transactionType;
    double amountContributedSoFar;
    double packageAmount;
    Account account;
    private static final String PREF_NAME = "awajima";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_customer_pack_list);
        DBHelper applicationDb = new DBHelper(this);
        packBundle=new Bundle();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        customer = new Customer();
        userProfile=new Profile();
        paymentBundle = new Bundle();
        packBundle = getIntent().getExtras();
        if(packBundle !=null){
            customer=packBundle.getParcelable("Customer");

        }
        else {
            if(userProfile !=null){
                customer=userProfile.getProfileCus();
            }

            if (customer !=null){
                customerID = customer.getCusUID();

            }

        }
        userProfile = gson.fromJson(json, Profile.class);
        FloatingActionButton dashboard = findViewById(R.id.btnBackP);

        if (customer !=null){
            customerID = customer.getCusUID();

        }

        packageArrayList = new ArrayList<>();
        packageArrayList.addAll(applicationDb.getCustomerIncompletePack(customerID,"inProgress"));
        //array_list.add(new SkyLightPackage(9057l, "Savings", 10000.00, 1000000.00, "23/8/2020", 15, "9/1/12", 20000.00, "Completed"));
        //array_list.add(new SkyLightPackage(9357l, "Savings", 10000.00, 1000000.00, "23/8/2020", 15, "", 20000.00, "in progress"));
        //array_list.add(new SkyLightPackage(9051l, "Savings", 10000.00, 1000000.00, "23/8/2020", 15, "", 20000.00, "in progress"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.p_toolbar);
        setSupportActionBar(toolbar);
        listView = findViewById(R.id.packa_list);
        skylightPackageAdapter = new SavingsPackAdapt(this, (Cursor) packageArrayList);
        listView.setAdapter(skylightPackageAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                int id_To_Search = position + 1;
                SkyLightPackage skyLightPackage = packageArrayList.get(position);
                Bundle packBundle = new Bundle();
                packBundle.putInt("id", id_To_Search);
                if(skyLightPackage !=null){
                    packageID = skyLightPackage.getPackID();
                    amountContributedSoFar = skyLightPackage.getPackageAmount_collected();
                    grandTotal = skyLightPackage.getPackageTotalAmount();
                    packageAmount=skyLightPackage.getPackageDailyAmount();
                    account = skyLightPackage.getPackageAccount();
                    long acctID=account.getAwajimaAcctNo();
                    String status=skyLightPackage.getPackageStatus();
                    customerID=customer.getCusUID();
                    double amtRem = skyLightPackage.getPackageAmtRem();
                    int daysRem = skyLightPackage.getPackageDaysRem();

                    if(amountContributedSoFar==grandTotal && status.equalsIgnoreCase("unpaid")){
                        transactionType = Transaction.TRANSACTION_TYPE.WITHDRAWALTX;
                        paymentBundle.putString("Transaction Type", String.valueOf(transactionType));
                        paymentBundle.putParcelable("Package", skyLightPackage);
                        paymentBundle.putParcelable("SkyLightPackage", skyLightPackage);
                        paymentBundle.putParcelable("Account", account);
                        Toast.makeText(CustomerPackageListAct.this, "packageType Package, selected", Toast.LENGTH_SHORT).show();
                        Intent savingsIntent = new Intent(CustomerPackageListAct.this, RequestPaymentAct.class);
                        savingsIntent.putExtras(paymentBundle);

                    }
                    if(amountContributedSoFar==grandTotal && status.equalsIgnoreCase("paid")){
                       finish();

                    }
                    if(amountContributedSoFar<grandTotal && status.equalsIgnoreCase("inComplete")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerPackageListAct.this);
                        builder.setTitle("Choose Package Action");
                        builder.setIcon(R.drawable.ic_icon2);
                        builder.setItems(new CharSequence[]
                                        {"Borrow Money","Continue Savings"},
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {

                                            case 0:
                                                transactionType= Transaction.TRANSACTION_TYPE.BORROWING;
                                                paymentBundle.putString("Transaction Type", String.valueOf(transactionType));
                                                paymentBundle.putParcelable("Package", skyLightPackage);
                                                paymentBundle.putParcelable("SkyLightPackage", skyLightPackage);
                                                paymentBundle.putParcelable("Account", account);
                                                Intent itemPurchaseIntent = new Intent(CustomerPackageListAct.this, TellerForCusLoanAct.class);
                                                itemPurchaseIntent.putExtras(paymentBundle);
                                                //itemPurchaseIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                break;

                                            case 1:
                                                transactionType= Transaction.TRANSACTION_TYPE.DEPOSIT;
                                                paymentBundle.putString("Transaction Type", String.valueOf(transactionType));
                                                paymentBundle.putParcelable("Package", skyLightPackage);
                                                paymentBundle.putParcelable("SkyLightPackage", skyLightPackage);
                                                paymentBundle.putParcelable("Account", account);
                                                Toast.makeText(CustomerPackageListAct.this, skyLightPackage+" selected for more savings", Toast.LENGTH_SHORT).show();
                                                Intent promoIntent = new Intent(CustomerPackageListAct.this, PayNowActivity.class);
                                                promoIntent.putExtras(paymentBundle);
                                                promoIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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



                    }if(amountContributedSoFar<grandTotal && status.equalsIgnoreCase("inProgress")){
                        finish();

                    }else {

                        if(amountContributedSoFar<grandTotal){
                            double accountBalance = account.getAccountBalance();
                            paymentBundle.putLong(PACKAGE_ID, packageID);
                            paymentBundle.putLong("PACKAGE_ID", packageID);
                            paymentBundle.putLong("Package Id", packageID);
                            paymentBundle.putLong("Account ID", acctID);
                            paymentBundle.putLong(CUSTOMER_ID, customerID);
                            paymentBundle.putLong("CUSTOMER_ID", customerID);
                            paymentBundle.putParcelable("Account", account);
                            paymentBundle.putInt("Number of Days", daysRem);
                            paymentBundle.putInt("Day Remaining", 0);
                            paymentBundle.putString("Surname", "Surname");
                            paymentBundle.putDouble("REPORT_AMOUNT_COLLECTED_SO_FAR", amountContributedSoFar);
                            paymentBundle.putDouble(REPORT_AMOUNT_COLLECTED_SO_FAR, amountContributedSoFar);
                            paymentBundle.putDouble(REPORT_AMOUNT_REMAINING, amtRem);
                            paymentBundle.putDouble("REPORT_AMOUNT_REMAINING", amtRem);
                            paymentBundle.putDouble("Amount", packageAmount);
                            paymentBundle.putDouble("Total", amtRem);
                            paymentBundle.putDouble(PACKAGE_VALUE, grandTotal);
                            paymentBundle.putDouble("PACKAGE_VALUE", grandTotal);
                            paymentBundle.putDouble(ACCOUNT_BALANCE, accountBalance);
                            paymentBundle.putDouble("ACCOUNT_BALANCE", accountBalance);
                            paymentBundle.putDouble("Package Account Balance", accountBalance);
                            Intent intent = new Intent(CustomerPackageListAct.this, PayNowActivity.class);
                            //payNowStartForResult.launch(new Intent(CustomerPackForPayment.this, PayNowActivity.class));
                            //payNowStartForResult.launch(intent.putExtras(paymentBundle));
                            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        }



                    }


                }


            }
        });
        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                packageArrayList.clear();
                packageArrayList.addAll(applicationDb.getPackagesFromCustomer(customerID));
                skylightPackageAdapter.notifyDataSetChanged();
                listView.invalidateViews();
                listView.refreshDrawableState();
            }
        });
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerPackageListAct.this, LoginDirAct.class);

            }
        });


    }

    @Override
    public void onItemClick(SkyLightPackage lightPackage) {
        Bundle dataBundle = new Bundle();
        dataBundle.putParcelable("SkyLightPackage", lightPackage);
        dataBundle.putParcelable("Package", lightPackage);
        Intent intent = new Intent(CustomerPackageListAct.this, CustomerPackageListAct.class);
        intent.putExtras(dataBundle);
        startActivity(intent);

    }
}