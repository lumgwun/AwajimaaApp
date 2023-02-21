package com.skylightapp;

import static com.skylightapp.Classes.AppConstants.PAYMENTS_ENVIRONMENT;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.pay.PayClient;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.google.gson.Gson;
import com.skylightapp.Bookings.Trip;
import com.skylightapp.Bookings.TripBooking;
import com.skylightapp.Classes.AppConstants;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.GooglePayMFactory;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.GooglePayDir.ViewModelProvider;
import com.skylightapp.Database.BizSubscriptionDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.TripBookingDAO;
import com.skylightapp.MarketClasses.CheckoutViewModel;
import com.skylightapp.MarketClasses.Market;
import com.skylightapp.MarketClasses.MarketBizSub;
import com.skylightapp.MarketClasses.MarketBusiness;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class GooglePayAct extends AppCompatActivity implements  View.OnClickListener{
    private static final int ADD_TO_GOOGLE_WALLET_REQUEST_CODE = 699;

    private AppCompatImageButton googlePayButton;
    private FrameLayout addToGoogleWalletButtonContainer;
    private AppCompatImageView addToGoogleWalletButton;
    private Bundle gPayBundle;
    long amount;
    SharedPreferences sharedPref;
    Bundle userExtras;
    private static final String PREF_NAME = "awajima";
    Gson gson, gson1,gson2;
    String json, json1, json2,stopPointName;
    SharedPreferences userPreferences;
    private FloatingActionButton fab;
    private CheckoutViewModel model;
    private AppCompatButton btnContinue;
    private LinearLayout layoutGoogle;
    private TextView txtPayingFor;
    String SharedPrefUserMachine;
    String SharedPrefUserName;
    String SharedPrefProfileID;
    private Market paymentMarket;
    private MarketBusiness marketBusiness,business;
    SharedPreferences.Editor editor;
    String userName, userPassword, userMachine,subDate,subEndDate,paymentFor,nin;
    Profile userProfile, paymentProf;
    private int profileID;
    private Customer customer;
    private DBHelper dbHelper;
    private PaymentsClient mPaymentsClient;
    private long bizID,subDBID;
    private int noOfMonths,sitCount;
    private MarketBizSub subScription;
    Calendar today;
    private Trip trip;
    private long bookingAmt,tripBID,grpSavingsAcctID,grpSavingsID;
    private SQLiteDatabase sqLiteDatabase;
    private long totalPriceCents;
    private Profile paymentProfile;
    private Transaction transaction;
    private int bookingID,tripID,customerID,bundleProfID,bundleCusID,noOfMinors,marketID;
    private String paymentType;
    private boolean doIt=false;
    private String serviceType,state,office,country,bookingName,currency,takeOffPoint;
    private static final String TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss.SSSZZZZZ";
    ActivityResultLauncher<IntentSenderRequest> resolvePaymentForResult = registerForActivityResult(
            new ActivityResultContracts.StartIntentSenderForResult(),
            result -> {
                switch (result.getResultCode()) {
                    case Activity.RESULT_OK:
                        Intent resultData = result.getData();
                        if (resultData != null) {
                            PaymentData paymentData = PaymentData.getFromIntent(result.getData());
                            if (paymentData != null) {
                                handlePaymentSuccess(paymentData);
                            }
                        }
                        break;

                    case Activity.RESULT_CANCELED:
                        // The user cancelled the payment attempt
                        break;
                }
            });
    private GooglePayMFactory viewModelFactory;


    public static PaymentsClient createPaymentsClient(Activity activity) {
        Wallet.WalletOptions walletOptions =new Wallet.WalletOptions.Builder()
                .setEnvironment(PAYMENTS_ENVIRONMENT)
                .setTheme(WalletConstants.THEME_DARK)
                .build();
        return Wallet.getPaymentsClient(activity, walletOptions);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_google_pay);

        //AndroidInjection.inject(this);
        btnContinue = findViewById(R.id.con_to_Checkout);
        txtPayingFor = findViewById(R.id.payingForText);
        addToGoogleWalletButton = findViewById(R.id.addG);
        layoutGoogle = findViewById(R.id.layoutGoogle);
        googlePayButton = findViewById(R.id.buy_with_g);

        addToGoogleWalletButtonContainer = findViewById(R.id.addToG);
        gPayBundle= new Bundle();
        business= new MarketBusiness();
        paymentProf= new Profile();
        paymentMarket= new Market();
        createPaymentsClient(this);
        trip = new Trip();
        paymentProfile= new Profile();
        transaction= new Transaction();
        mPaymentsClient = AppConstants.createPaymentsClient(this);
        gson1 = new Gson();
        gson = new Gson();
        gson2 = new Gson();
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
        customerID=userPreferences.getInt("CUSTOMER_ID", 0);
        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson2.fromJson(json2, MarketBusiness.class);
        bookingID = ThreadLocalRandom.current().nextInt(1005, 17460);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doIt=true;
                addToGoogleWalletButton.setVisibility(View.VISIBLE);
                addToGoogleWalletButtonContainer.setVisibility(View.VISIBLE);
                googlePayButton.setVisibility(View.VISIBLE);
                layoutGoogle.setVisibility(View.VISIBLE);

            }
        });

        gPayBundle=getIntent().getExtras();
        if(gPayBundle !=null){
            doIt=true;
            amount=gPayBundle.getLong("Amount");
            business=gPayBundle.getParcelable("MarketBusiness");
            paymentMarket=gPayBundle.getParcelable("Market");
            bundleProfID=gPayBundle.getInt("PROFILE_ID");
            bundleCusID=gPayBundle.getInt("CUSTOMER_ID");
            noOfMonths=gPayBundle.getInt("NoOfMonths");
            trip =gPayBundle.getParcelable("Trip");
            sitCount=gPayBundle.getInt("SitCount");
            bookingAmt=gPayBundle.getLong("Total");
            paymentFor=gPayBundle.getString("PaymentFor");
            paymentProfile=gPayBundle.getParcelable("Profile");
            stopPointName=gPayBundle.getString("stopPointName");
            nin=gPayBundle.getString("PROFILE_NIN");
            grpSavingsAcctID=gPayBundle.getInt("GrpSavingsAcctID");
            grpSavingsID=gPayBundle.getInt("GrpSavingsID");
            noOfMinors=gPayBundle.getInt("noOfMinors");
            state=gPayBundle.getString("state");
            office=gPayBundle.getString("office");
            country=gPayBundle.getString("country");
            bookingName=gPayBundle.getString("bookingName");
            currency=gPayBundle.getString("currency");
            takeOffPoint=gPayBundle.getString("takeOffPoint");
            marketID =gPayBundle.getInt("marketID");

        }
        initializeUi();
        if(amount >0){
            txtPayingFor.setText(currency+amount);
        }

        try {
            model = ViewModelProviders.of(this, viewModelFactory).get(CheckoutViewModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //model = new ViewModelProvider(this).get(CheckoutViewModel.class);

        try {
            model.canUseGooglePay.observe(this, this::setGooglePayAvailable);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        try {
            model.canAddPasses.observe(this, this::setAddToGoogleWalletAvailable);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.con_to_Checkout:
                doIt=true;
                addToGoogleWalletButton.setVisibility(View.VISIBLE);
                addToGoogleWalletButtonContainer.setVisibility(View.VISIBLE);
                googlePayButton.setVisibility(View.VISIBLE);
                layoutGoogle.setVisibility(View.VISIBLE);
                //onBackPressed();
                break;
            case R.id.buy_with_g:
                requestPayment();
                break;
            case R.id.addG:
                model.canAddPasses.observe(this, this::setAddToGoogleWalletAvailable);
                break;
            default:
                break;
        }
    }
    private void initializeUi() {

        addToGoogleWalletButton.setOnClickListener(v -> {
            addToGoogleWalletButton.setClickable(false);
            model.savePasses(model.genericObjectJwt, this, ADD_TO_GOOGLE_WALLET_REQUEST_CODE);
        });
    }
    private void setGooglePayAvailable(boolean available) {
        if (available) {
            googlePayButton.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, R.string.google_pay_status_unavailable, Toast.LENGTH_LONG).show();
        }
    }
    public void requestPayment() {
        gPayBundle= new Bundle();

        googlePayButton.setClickable(false);

        gPayBundle=getIntent().getExtras();
        if(gPayBundle !=null){
            doIt=true;
            amount=gPayBundle.getLong("Amount");
            business=gPayBundle.getParcelable("MarketBusiness");
            paymentMarket=gPayBundle.getParcelable("Market");
            bundleProfID=gPayBundle.getInt("PROFILE_ID");
            bundleCusID=gPayBundle.getInt("CUSTOMER_ID");
            noOfMonths=gPayBundle.getInt("NoOfMonths");
            trip =gPayBundle.getParcelable("Trip");
            sitCount=gPayBundle.getInt("SitCount");
            bookingAmt=gPayBundle.getLong("Total");
            paymentFor=gPayBundle.getString("PaymentFor");
            paymentProfile=gPayBundle.getParcelable("Profile");
            stopPointName=gPayBundle.getString("stopPointName");
            nin=gPayBundle.getString("PROFILE_NIN");
            grpSavingsAcctID=gPayBundle.getInt("GrpSavingsAcctID");
            grpSavingsID=gPayBundle.getInt("GrpSavingsID");
            noOfMinors=gPayBundle.getInt("noOfMinors");
            state=gPayBundle.getString("state");
            office=gPayBundle.getString("office");
            country=gPayBundle.getString("country");
            bookingName=gPayBundle.getString("bookingName");
            currency=gPayBundle.getString("currency");
            takeOffPoint=gPayBundle.getString("takeOffPoint");
            marketID =gPayBundle.getInt("marketID");

        }
        if(paymentFor !=null){
            if(paymentFor.equalsIgnoreCase("Boat Booking")){
                totalPriceCents=bookingAmt;

            }
            if(paymentFor.equalsIgnoreCase("Train Booking")){
                totalPriceCents=bookingAmt;

            }
            if(paymentFor.equalsIgnoreCase("Taxi Booking")){
                totalPriceCents=bookingAmt;

            }
            if(paymentFor.equalsIgnoreCase("Tour Booking")){
                totalPriceCents=bookingAmt;

            }
            if(paymentFor.equalsIgnoreCase("Jet Booking")){
                totalPriceCents=bookingAmt;

            }
            if(paymentFor.equalsIgnoreCase("Coop Subscription")){
                totalPriceCents=bookingAmt;

            }
            if(paymentFor.equalsIgnoreCase("Subscription")){
                totalPriceCents=amount;

            }
            if(paymentFor.equalsIgnoreCase("Donation")){
                totalPriceCents=amount;

            }
            if(paymentFor.equalsIgnoreCase("Group Savings")){
                totalPriceCents=amount;

            }
            if(paymentFor.equalsIgnoreCase("Savings")){
                totalPriceCents=amount;

            }
            if(paymentFor.equalsIgnoreCase("Loan Repayment")){
                totalPriceCents=amount;

            }

        }
        //totalPriceCents = amount;
        final Task<PaymentData> task = model.getLoadPaymentDataTask(totalPriceCents);

        task.addOnCompleteListener(completedTask -> {
            if (completedTask.isSuccessful()) {
                handlePaymentSuccess(completedTask.getResult());
            } else {
                Exception exception = completedTask.getException();
                if (exception instanceof ResolvableApiException) {
                    PendingIntent resolution = ((ResolvableApiException) exception).getResolution();
                    resolvePaymentForResult.launch(new IntentSenderRequest.Builder(resolution).build());

                } else if (exception instanceof ApiException) {
                    ApiException apiException = (ApiException) exception;
                    handleError(apiException.getStatusCode(), apiException.getMessage());
                    doIt=true;
                    Intent dialogIntent = new Intent(GooglePayAct.this, BizSubQTOptionAct.class);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(dialogIntent);

                } else {
                    handleError(CommonStatusCodes.INTERNAL_ERROR, "Unexpected non API" +
                            " exception when trying to deliver the task result to an activity!");
                    Intent dialogIntent = new Intent(GooglePayAct.this, BizSubQTOptionAct.class);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(dialogIntent);
                }
            }

            // Re-enables the Google Pay payment button.
            googlePayButton.setClickable(true);
        });
    }

    private void handlePaymentSuccess(PaymentData paymentData) {
        final String paymentInfo = paymentData.toJson();
        gPayBundle= new Bundle();
        subScription= new MarketBizSub();
        today = Calendar.getInstance();
        transaction= new Transaction();
        BizSubscriptionDAO subscriptionDAO= new BizSubscriptionDAO(this);
        TripBookingDAO tripBookingDAO= new TripBookingDAO(this);


        gPayBundle=getIntent().getExtras();
        if(gPayBundle !=null){
            doIt=true;
            amount=gPayBundle.getLong("Amount");
            business=gPayBundle.getParcelable("MarketBusiness");
            paymentMarket=gPayBundle.getParcelable("Market");
            bundleProfID=gPayBundle.getInt("PROFILE_ID");
            bundleCusID=gPayBundle.getInt("CUSTOMER_ID");
            noOfMonths=gPayBundle.getInt("NoOfMonths");
            trip =gPayBundle.getParcelable("Trip");
            sitCount=gPayBundle.getInt("SitCount");
            bookingAmt=gPayBundle.getLong("Total");
            paymentFor=gPayBundle.getString("PaymentFor");
            paymentProfile=gPayBundle.getParcelable("Profile");
            stopPointName=gPayBundle.getString("stopPointName");
            nin=gPayBundle.getString("PROFILE_NIN");
            grpSavingsAcctID=gPayBundle.getInt("GrpSavingsAcctID");
            grpSavingsID=gPayBundle.getInt("GrpSavingsID");
            noOfMinors=gPayBundle.getInt("noOfMinors");
            state=gPayBundle.getString("state");
            office=gPayBundle.getString("office");
            country=gPayBundle.getString("country");
            bookingName=gPayBundle.getString("bookingName");
            currency=gPayBundle.getString("currency");
            takeOffPoint=gPayBundle.getString("takeOffPoint");
            marketID =gPayBundle.getInt("marketID");

        }

        if(paymentProfile !=null){
            profileID=paymentProfile.getPID();

        }
        if(trip !=null){
            tripID= trip.getaTripID();

        }
        if(business !=null){
            bizID=business.getBusinessID();

        }

        if(paymentFor !=null){
            if(paymentFor.equalsIgnoreCase("Boat Booking")){
                totalPriceCents=bookingAmt;

            }
            if(paymentFor.equalsIgnoreCase("Train Booking")){
                totalPriceCents=bookingAmt;

            }
            if(paymentFor.equalsIgnoreCase("Taxi Booking")){
                totalPriceCents=bookingAmt;

            }
            if(paymentFor.equalsIgnoreCase("Tour Booking")){
                totalPriceCents=bookingAmt;

            }
            if(paymentFor.equalsIgnoreCase("Jet Booking")){
                totalPriceCents=bookingAmt;

            }
            if(paymentFor.equalsIgnoreCase("Coop Subscription")){
                totalPriceCents=bookingAmt;

            }
            if(paymentFor.equalsIgnoreCase("Subscription")){
                totalPriceCents=amount;

            }
            if(paymentFor.equalsIgnoreCase("Donation")){
                totalPriceCents=amount;

            }
            if(paymentFor.equalsIgnoreCase("Group Savings")){
                totalPriceCents=amount;

            }
            if(paymentFor.equalsIgnoreCase("Savings")){
                totalPriceCents=amount;

            }
            if(paymentFor.equalsIgnoreCase("Loan Repayment")){
                totalPriceCents=amount;

            }

        }
        if(currency !=null){
            if(currency.equalsIgnoreCase("NGN")){
                totalPriceCents=totalPriceCents*700;
            }
        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat(TIME_FORMAT_PATTERN, Locale.getDefault());
        subDate = mdformat.format(today.getTime());
        String modeOfPayment="Google Pay";
        today.add(Calendar.MONTH, noOfMonths);
        Date date = new Date(today.getTimeInMillis());
        subEndDate = mdformat.format(date);
        String status="Successful";
        Bundle bundle = new Bundle();

        bookingID = ThreadLocalRandom.current().nextInt(1005, 17460);
        TripBooking tripBooking= new TripBooking(bookingID,tripID,bundleProfID,bundleCusID,paymentFor,sitCount,stopPointName,bookingAmt,modeOfPayment,subDate,"Paid",nin,noOfMinors,state,office,country,bookingName,currency,takeOffPoint);

        if(paymentFor !=null){
            totalPriceCents=bookingAmt;
            if(paymentProfile !=null){
                paymentProfile.addTripBooking(tripBooking);

            }

            if(tripBookingDAO !=null){
                doIt=true;
                try {
                    tripBID=tripBookingDAO.insertTripBooking(bookingID,tripID,bundleProfID,bundleCusID,nin,paymentFor,sitCount,stopPointName,bookingAmt,modeOfPayment,subDate,"Paid",nin,noOfMinors,state,office,country,bookingName,currency,takeOffPoint);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            if(tripBID>0){
                Toast.makeText(
                        this,
                        "Trip Booking successful",
                        Toast.LENGTH_LONG).show();
                Intent intent=new Intent(GooglePayAct.this, LoginDirAct.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(intent);

            }
            if(paymentFor.equalsIgnoreCase("Subscription")){
                totalPriceCents=amount;

                if(subscriptionDAO !=null){
                    doIt=true;
                    try {
                        subDBID=subscriptionDAO.insertSubscription(bookingID,bizID,marketID,amount,bundleProfID,bundleCusID,noOfMonths,subDate,subEndDate,modeOfPayment,paymentFor,status,state,office,country,bookingName,currency);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                bundle.putParcelable("MarketBusiness",business);
                bundle.putParcelable("Market",paymentMarket);
                bundle.putParcelable("Profile",userProfile);
                bundle.putInt("PROFILE_ID",profileID);
                subScription= new MarketBizSub(bookingID,bizID,marketID,amount,bundleProfID,bundleCusID,noOfMonths,subDate,subEndDate,modeOfPayment,paymentFor,status,state,office,country,currency,bookingName);

                if(subDBID>0){
                    business.addSubscription(subScription);

                    Toast.makeText(
                            this,
                            "Subscription successful",
                            Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(GooglePayAct.this, LoginDirAct.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);
                    startActivity(intent);

                }

            }
            if(paymentFor.equalsIgnoreCase("Coop Subscription")){
                totalPriceCents=amount;
                noOfMonths=1;

                if(subscriptionDAO !=null){
                    doIt=true;
                    try {
                        subDBID=subscriptionDAO.insertSubscription(0,amount,bundleProfID,bundleCusID,noOfMonths,subDate,subEndDate,modeOfPayment,paymentFor,status,currency,state,country,bookingName,office);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                bundle.putParcelable("MarketBusiness",business);
                bundle.putParcelable("Market",paymentMarket);
                bundle.putParcelable("Profile",userProfile);
                bundle.putInt("PROFILE_ID",profileID);
                subScription= new MarketBizSub(0,amount,bundleProfID,bundleCusID,noOfMonths,subDate,subEndDate,modeOfPayment,paymentFor,status,currency,state,country,bookingName,office);

                if(subDBID>0){
                    business.addSubscription(subScription);

                    Toast.makeText(
                            this,
                            "Subscription successful",
                            Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(GooglePayAct.this, LoginDirAct.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);
                    startActivity(intent);

                }

            }

        }


        
        try {
            JSONObject paymentMethodData = new JSONObject(paymentInfo).getJSONObject("paymentMethodData");

            final JSONObject info = paymentMethodData.getJSONObject("info");
            final String billingName = info.getJSONObject("billingAddress").getString("name");
            Toast.makeText(
                    this, getString(R.string.payments_show_name, billingName),
                    Toast.LENGTH_LONG).show();

            // Logging token string.
            Log.d("Google Pay token", paymentMethodData
                    .getJSONObject("tokenizationData")
                    .getString("token"));

        } catch (JSONException e) {
            Log.e("handlePaymentSuccess", "Error: " + e);
        }

    }

    private void handleError(int statusCode, @Nullable String message) {
        Log.e("loadPaymentData failed",
                String.format(Locale.getDefault(), "Error code: %d, Message: %s", statusCode, message));
    }


    private void setAddToGoogleWalletAvailable(boolean available) {
        if (available) {
            addToGoogleWalletButtonContainer.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(
                    this,
                    R.string.google_wallet_status_unavailable,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TO_GOOGLE_WALLET_REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_OK: {
                    Toast
                            .makeText(this, getString(R.string.add_google_wallet_success), Toast.LENGTH_LONG)
                            .show();
                    break;
                }

                case RESULT_CANCELED: {
                    // Save canceled
                    break;
                }

                case PayClient.SavePassesResult.SAVE_ERROR: {
                    if (data != null) {
                        String apiErrorMessage = data.getStringExtra(PayClient.EXTRA_API_ERROR_MESSAGE);
                        handleError(resultCode, apiErrorMessage);
                    }
                    break;
                }

                default: handleError(
                        CommonStatusCodes.INTERNAL_ERROR, "Unexpected non API" +
                                " exception when trying to deliver the task result to an activity!"
                );
            }

            addToGoogleWalletButton.setClickable(true);
        }
    }
}