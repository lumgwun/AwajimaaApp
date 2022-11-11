package com.skylightapp;

import static com.skylightapp.Classes.AppConstants.PAYMENTS_ENVIRONMENT;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.skylightapp.Classes.AppConstants;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.CusPacksAct;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Database.BizSubscriptionDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MarketClasses.CheckoutViewModel;
import com.skylightapp.MarketClasses.Market;
import com.skylightapp.MarketClasses.MarketBizSubScription;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.Markets.MarketBizOffice;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

public class GooglePayAct extends AppCompatActivity {
    private static final int ADD_TO_GOOGLE_WALLET_REQUEST_CODE = 999;

    private View googlePayButton;
    private ImageView addToGoogleWalletButtonContainer;
    private ImageView addToGoogleWalletButton;
    private Bundle gPayBundle;
    long amount;
    SharedPreferences sharedPref;
    Bundle userExtras;
    private static final String PREF_NAME = "awajima";
    Gson gson, gson1,gson2;
    String json, json1, json2;
    SharedPreferences userPreferences;
    private FloatingActionButton fab;
    private CheckoutViewModel model;
    SharedPreferences sharedPreferences;
    String SharedPrefUserPassword;
    private AppCompatButton btnContinue;
    private LinearLayout layoutGoogle;
    private TextView txtPayingFor;
    String SharedPrefUserMachine;
    String SharedPrefUserName;
    String SharedPrefProfileID;
    private Market paymentMarket;
    private MarketBusiness marketBusiness,business;
    SharedPreferences.Editor editor;
    String userName, userPassword, userMachine,subDate,subEndDate;
    Profile userProfile, paymentProf;
    private int profileID;
    private Customer customer;
    private DBHelper dbHelper;
    private PaymentsClient mPaymentsClient;
    private long bizID,subDBID;
    private int noOfMonths;
    private MarketBizSubScription subScription;
    Calendar today;
    private SQLiteDatabase sqLiteDatabase;
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
        btnContinue = findViewById(R.id.con_to_Checkout);
        txtPayingFor = findViewById(R.id.payingForText);
        addToGoogleWalletButton = findViewById(R.id.addG);
        layoutGoogle = findViewById(R.id.layoutGoogle);
        addToGoogleWalletButtonContainer = findViewById(R.id.buy_with_g);
        gPayBundle= new Bundle();
        business= new MarketBusiness();
        paymentProf= new Profile();
        paymentMarket= new Market();
        createPaymentsClient(this);

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
        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson2.fromJson(json2, MarketBusiness.class);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToGoogleWalletButton.setVisibility(View.VISIBLE);
                addToGoogleWalletButtonContainer.setVisibility(View.VISIBLE);
                googlePayButton.setVisibility(View.VISIBLE);
                layoutGoogle.setVisibility(View.VISIBLE);

            }
        });
        btnContinue.setOnClickListener(this::continueToG);
        gPayBundle=getIntent().getExtras();
        if(gPayBundle !=null){
            amount=gPayBundle.getLong("Amount");
            business=gPayBundle.getParcelable("MarketBusiness");
            paymentMarket=gPayBundle.getParcelable("Market");
            profileID=gPayBundle.getInt("PROFILE_ID");
            noOfMonths=gPayBundle.getInt("NoOfMonths");

        }
        initializeUi();
        if(amount >0){
            txtPayingFor.setText("USD"+amount);
        }

        //model = new ViewModelProvider(this).get(CheckoutViewModel.class);
        model.canUseGooglePay.observe(this, this::setGooglePayAvailable);

        // Check out Google Wallet availability
        model.canAddPasses.observe(this, this::setAddToGoogleWalletAvailable);
    }
    private void initializeUi() {

        googlePayButton.setOnClickListener(this::requestPayment);

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
    public void requestPayment(View view) {
        gPayBundle= new Bundle();

        googlePayButton.setClickable(false);

        gPayBundle=getIntent().getExtras();
        if(gPayBundle !=null){
            amount=gPayBundle.getLong("Amount");
            business=gPayBundle.getParcelable("MarketBusiness");
            paymentMarket=gPayBundle.getParcelable("Market");
            profileID=gPayBundle.getInt("PROFILE_ID");
            noOfMonths=gPayBundle.getInt("NoOfMonths");

        }
        long totalPriceCents = amount;
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
        subScription= new MarketBizSubScription();
        today = Calendar.getInstance();


        gPayBundle=getIntent().getExtras();
        if(gPayBundle !=null){
            amount=gPayBundle.getLong("Amount");
            business=gPayBundle.getParcelable("MarketBusiness");
            paymentMarket=gPayBundle.getParcelable("Market");
            profileID=gPayBundle.getInt("PROFILE_ID");
            noOfMonths=gPayBundle.getInt("NoOfMonths");

        }
        if(business !=null){
            bizID=business.getBusinessID();

        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat(TIME_FORMAT_PATTERN, Locale.getDefault());
        subDate = mdformat.format(today.getTime());
        String modeOfPayment="Google Pay";
        today.add(Calendar.MONTH, noOfMonths);
        Date date = new Date(today.getTimeInMillis());
        subEndDate = mdformat.format(date);
        String status="Successful";
        Bundle bundle = new Bundle();
        BizSubscriptionDAO subscriptionDAO= new BizSubscriptionDAO(this);

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            subDBID=subscriptionDAO.insertSubscription(bizID,amount,profileID,noOfMonths,subDate,subEndDate,modeOfPayment,status);

        }
        bundle.putParcelable("MarketBusiness",business);
        bundle.putParcelable("Market",paymentMarket);
        bundle.putParcelable("Profile",userProfile);
        bundle.putInt("PROFILE_ID",profileID);
        subScription= new MarketBizSubScription(bizID,amount,profileID,noOfMonths,subDate,subEndDate,modeOfPayment,status);

        if(subDBID>0){
            business.addSubscription(subScription);

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
        Toast.makeText(
                this,
                "Subscription successful",
                Toast.LENGTH_LONG).show();
        Intent intent=new Intent(GooglePayAct.this, MarketBizOffice.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);
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

    public void continueToG(View view) {
    }
}