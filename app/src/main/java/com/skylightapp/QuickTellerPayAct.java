package com.skylightapp;

import static com.skylightapp.MarketClasses.MarketBizPackage.PACKAGE_TYPE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.interswitchng.iswmobilesdk.IswMobileSdk;
import com.interswitchng.iswmobilesdk.shared.models.core.IswPaymentInfo;
import com.interswitchng.iswmobilesdk.shared.models.core.IswPaymentResult;
import com.skylightapp.Admins.AdminHomeChoices;
import com.skylightapp.Bookings.Trip;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.MarketClasses.Market;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.MarketClasses.MarketBusiness;

import java.security.SecureRandom;
import java.util.UUID;

public class QuickTellerPayAct extends AppCompatActivity implements IswMobileSdk.IswPaymentCallback{
    private String currencyCode,customerName,customerMobile,customerEmail,bundleAmt,amtString,reference;
    private Bundle bundle;
    private LinearLayout resultContainer;
    private long amount,bookingAmt;
    private int accountID;
    private Account account;
    private Customer customer;
    private Transaction transaction;
    MarketBizPackage marketBizPackage;
    double amountSoFar;
    double packageTotal;
    int packageID,AccountID,profileID,customerId;
    double packageAmount;
    double amountRemaining,totalToday;
    String customerPhoneNo,officeBranch;
    CustomerDailyReport customerDailyReport;
    private SharedPreferences userPreferences;
    private static final String PREF_NAME = "awajima";
    private Gson gson;
    private String json;
    private Customer selectedCustomer;
    private Profile userProfile,paymentProfile;
    private Market paymentMarket;
    private MarketBusiness marketBusiness,business;
    private int bookingID,tripID,customerID,bundleProfID,bundleCusID,noOfMinors,marketID;
    private String serviceType,state,office,country,bookingName,currency,takeOffPoint;
    SecureRandom random =new SecureRandom();


    private AppCompatTextView resultTitle,
            responseCode,
            responseDescription,
            paymentAmount,
            channel,
            isSuccessful;
    private int savingsID;
    private String planCode;
    private String accessCode,paymentFor;
    private int numberOfDays,noOfMonths,sitCount,grpSavingsAcctID,grpSavingsID;
    private String date;
    private String packageType,stopPointName,nin;
    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_quick_teller_pay);
        bundle= new Bundle();
        gson = new Gson();
        userProfile= new Profile();
        paymentProfile= new Profile();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        //userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        initiatePayment();
        UUID uuid = UUID.randomUUID();
        trip= new Trip();
        String nonce = uuid.toString().replaceAll("-", "");
        resultContainer = findViewById(R.id.resultContainer);

        resultTitle = findViewById(R.id.resultTitle);
        responseCode = findViewById(R.id.responseCode);
        responseDescription = findViewById(R.id.responseDescription);
        paymentAmount = findViewById(R.id.paymentAmount);
        channel = findViewById(R.id.channel);
        isSuccessful = findViewById(R.id.isSuccessful);

    }
    private void initiatePayment() {
        bundle=getIntent().getExtras();
        account= new Account();
        customer = new Customer();
        if(bundle !=null){
            currencyCode=bundle.getString("currencyCode");
            customerName=bundle.getString("customerName");
            customerId=bundle.getInt("customerId");
            customerMobile=bundle.getString("customerMobile");
            customerEmail=bundle.getString("customerEmail");
            bundleAmt=bundle.getString("bundleAmt");
            accountID = bundle.getInt("Account ID");
            account = bundle.getParcelable("Account");
            customer = bundle.getParcelable("Customer");
            marketBizPackage = bundle.getParcelable("Package");
            marketBizPackage = bundle.getParcelable("MarketBizPackage");
            //package_list_model = bundle.getParcelable("Package1");
            transaction = bundle.getParcelable("Transaction");
            customerDailyReport = bundle.getParcelable("Savings");
            savingsID = bundle.getInt("Report ID");
            planCode = bundle.getString("Plan Code");
            accessCode = bundle.getString("Access Code");
            packageAmount = bundle.getDouble("Amount");
            numberOfDays = bundle.getInt("Number of Days");
            totalToday = bundle.getDouble("Total");
            date = bundle.getString("Date");
            packageType = bundle.getString(PACKAGE_TYPE);

            amount=bundle.getLong("Amount");
            business=bundle.getParcelable("MarketBusiness");
            paymentMarket=bundle.getParcelable("Market");
            bundleProfID=bundle.getInt("PROFILE_ID");
            bundleCusID=bundle.getInt("CUSTOMER_ID");
            noOfMonths=bundle.getInt("NoOfMonths");
            trip =bundle.getParcelable("Trip");
            sitCount=bundle.getInt("SitCount");
            bookingAmt=bundle.getLong("Total");
            paymentFor=bundle.getString("PaymentFor");
            paymentProfile=bundle.getParcelable("Profile");
            stopPointName=bundle.getString("stopPointName");
            nin=bundle.getString("PROFILE_NIN");
            grpSavingsAcctID=bundle.getInt("GrpSavingsAcctID");
            grpSavingsID=bundle.getInt("GrpSavingsID");
            noOfMinors=bundle.getInt("noOfMinors");
            state=bundle.getString("state");
            office=bundle.getString("office");
            country=bundle.getString("country");
            bookingName=bundle.getString("bookingName");
            currency=bundle.getString("currency");
            takeOffPoint=bundle.getString("takeOffPoint");
            marketID =bundle.getInt("marketID");

        }
        if(currencyCode !=null){
            if(currencyCode.equalsIgnoreCase("USD")){
                amount = Integer.parseInt(bundleAmt) * 700;

            }
        }
        if (bundleAmt.isEmpty()) {
            amount = 2500 * 100;
        } else {
            amount = Integer.parseInt(bundleAmt) * 100;
        }

        // amount in kobo e.g. "N500.00" -> 50000

        /*IswPaymentInfo iswPaymentInfo = new IswPaymentInfo(customerId, customerName, customerEmail, customerMobile, currencyCode,
                reference,
                amount);


        IswMobileSdk.getInstance().pay(
                iswPaymentInfo,
                this
        );*/
    }

    @Override
    public void onUserCancel() {
        String title = "You cancelled payment";
        showResult(title, null);

        toast("You cancelled payment, please try again.");

    }

    @Override
    public void onPaymentCompleted(@NonNull IswPaymentResult result) {
        String title = "Payment Result";
        showResult(title, result);


        if (result.isSuccessful()){
            setResult(Activity.RESULT_OK);
            setResult(Activity.RESULT_OK, new Intent());
            toast("your payment was successful, using: " + result.getChannel().name());
        } else {
            toast("unable to complete payment at the moment, try again.");
        }

    }
    private void showResult(String title, IswPaymentResult result) {
        resultContainer.setVisibility(View.VISIBLE);

        // show result
        resultTitle.setText(title);
        boolean hasValue = result != null;
        int primaryColor = ContextCompat.getColor(this, R.color.colorPrimary);
        resultTitle.setTextColor(!hasValue ? Color.RED: primaryColor);

        int visibility = hasValue ? View.VISIBLE : View.INVISIBLE;
        paymentAmount.setVisibility(visibility);
        responseCode.setVisibility(visibility);
        responseDescription.setVisibility(visibility);
        isSuccessful.setVisibility(visibility);
        channel.setVisibility(visibility);

        if (!hasValue) return;

        paymentAmount.setText(new StringBuilder().append("").append(result.getAmount() / 100).toString());
        responseCode.setText(result.getResponseCode());
        responseDescription.setText(result.getResponseDescription());
        isSuccessful.setText("" + result.isSuccessful());
        channel.setText(result.getChannel().name());
    }
    public void goHomeC(View view) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", profileID);
        bundle.putString(machine, machine);
        Intent intent = new Intent(this, NewCustomerDrawer.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


    private void toast(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}