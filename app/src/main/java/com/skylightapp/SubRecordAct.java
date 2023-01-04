package com.skylightapp;

import static com.skylightapp.Database.DBHelper.DATABASE_NAME;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.widget.ContentLoadingProgressBar;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.DatePicker;

import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MarketClasses.MarketBizSub;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.SuperAdmin.Awajima;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class SubRecordAct extends AppCompatActivity implements  View.OnClickListener{
    private Calendar cal;
    private Calendar beginTime;
    private Calendar endTime,calendar;
    private Date dt ;
    private Date dt1;
    private Bundle mBundle;
    private String subTittle,thisMonth,qtSubID;
    private SimpleDateFormat dateFormatterS,dateFormatterE;
    private AppCompatButton btnSubmit;
    private DatePicker datePickerEnd,datePickerStart;
    private static final String PREF_NAME = "awajima";
    int numMessages;
    public static final String NOTIFICATION_CHANNEL_ID = "1121" ;
    ContentLoadingProgressBar progressBar;
    Gson gson, gson1,gson3,gson6;
    String json, json1, json3,json6nIN,todatDate,strStartOfSub,strEndOfSub,strngBizID,strngSubType,strngNoOfMonths,strngBizProfID,strngPaymentCode;
    Profile userProfile, customerProfile, lastProfileUsed;
    private long bizID;
    private int bizProfID;
    private Awajima awajima;
    private SharedPreferences userPreferences;
    private AppCompatEditText edtPaymentCode,edtNoOfMonths,edtBizProfID,edtBizID;
    private AppCompatTextView txtTotal;
    private Date startDate,endDate;
    private int subID;
    private MarketBusiness marketBusiness;
    Random ran;
    SecureRandom random;
    private AppCompatSpinner spnSubType;
    private SQLiteDatabase sqLiteDatabase;
    ArrayList<MarketBizSub> subScriptions;
    private ProgressDialog progressDialog;
    private Uri sound;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sub_record);
        cal = Calendar.getInstance();
        ran= new Random();
        random= new SecureRandom();
        beginTime = Calendar.getInstance();
        endTime = Calendar.getInstance();
        marketBusiness= new MarketBusiness();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson1 = new Gson();
        gson3= new Gson();
        userProfile= new Profile();
        dateFormatterS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatterE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mBundle=new Bundle();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson1.fromJson(json1, MarketBusiness.class);
        btnSubmit = findViewById(R.id.submit_sub);
        datePickerEnd = findViewById(R.id.sub_end_Date);
        datePickerStart = findViewById(R.id.sub_start_Date);
        edtPaymentCode = findViewById(R.id.sub_codeP);
        edtBizID = findViewById(R.id.sub_bizID);
        edtNoOfMonths = findViewById(R.id.sub_months);
        edtBizProfID = findViewById(R.id.sub_biz_prof_ID);
        txtTotal = findViewById(R.id.sub_totalD);
        spnSubType = findViewById(R.id.sub_type);

        calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        todatDate = mdformat.format(calendar.getTime());

        beginTime.set(Calendar.YEAR, datePickerStart.getYear());
        beginTime.set(Calendar.MONTH, datePickerStart.getMonth());
        beginTime.set(Calendar.DAY_OF_MONTH, datePickerStart.getDayOfMonth());
        startDate = beginTime.getTime();
        strStartOfSub = dateFormatterS.format(startDate);
        Animation translater = AnimationUtils.loadAnimation(this, R.anim.bounce);
        Animation translER = AnimationUtils.loadAnimation(this, R.anim.pro_animation);

        endTime.set(Calendar.YEAR, datePickerEnd.getYear());
        endTime.set(Calendar.MONTH, datePickerEnd.getMonth());
        endTime.set(Calendar.DAY_OF_MONTH, datePickerEnd.getDayOfMonth());
        endDate = endTime.getTime();
        strEndOfSub = dateFormatterE.format(endDate);

        subID = random.nextInt((int) (Math.random() * 1101) + 1010);




        if(mBundle !=null){
            subTittle= mBundle.getString("subTittle");
            thisMonth =mBundle.getString("lastMonth");
            qtSubID=mBundle.getString("qtSubID");

        }


        spnSubType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strngSubType = spnSubType.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(strngSubType !=null){
            if(strngSubType.equalsIgnoreCase("Unyeada Package")){

            }
            if(strngSubType.equalsIgnoreCase("Okoroete Package")){

            }
            if(strngSubType.equalsIgnoreCase("Ekede Package")){

            }
            if(strngSubType.equalsIgnoreCase("Ebeke Package")){

            }

        }
        edtPaymentCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtBizID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtNoOfMonths.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtBizProfID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    if(sqLiteDatabase !=null){
                        sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                    }
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }

                view.startAnimation(translater);
                strngBizID = edtBizID.getText().toString().trim();
                strngBizProfID = edtBizProfID.getText().toString().trim();
                strngNoOfMonths = edtNoOfMonths.getText().toString();
                strngPaymentCode = edtPaymentCode.getText().toString().trim();

                try {
                    bizID = Integer.parseInt(strngBizID);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                try {
                    bizProfID = Integer.parseInt(strngBizProfID);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
        });
        btnSubmit.setOnClickListener(this::sendBizSub);




    }
    private void doOtpNotification(String otpMessage) {

        try {
            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(SubRecordAct.this, sound);
            ringtone.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Uri sound = Uri. parse (ContentResolver. SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/raw/quite_impressed.mp3" ) ;
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.logo_awa_round)
                        .setContentTitle("Your Awajima OTP Message")
                        //.setSound(sound)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setAutoCancel(true)
                        .setContentText(otpMessage);

        Intent notificationIntent = new Intent(this, SignUpAct.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(SignUpAct.class);
        stackBuilder.addNextIntent(notificationIntent);
        builder.setNumber(++numMessages);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes. CONTENT_TYPE_SONIFICATION )
                    .setUsage(AudioAttributes. USAGE_ALARM )
                    .build() ;
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new
                    NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            notificationChannel.enableLights( true ) ;
            notificationChannel.setLightColor(Color. RED ) ;
            notificationChannel.enableVibration( true ) ;
            notificationChannel.setVibrationPattern( new long []{ 100 , 200 , 300 , 400 , 500 , 400 , 300 , 200 , 400 }) ;
            notificationChannel.setSound(sound , audioAttributes) ;
            builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis (), builder.build()) ;
        mNotificationManager.notify(20, builder.build());
    }

    private boolean checkInputs() {
        @SuppressLint("UseCompatLoadingForDrawables") Drawable customErrorIcon = getResources().getDrawable(R.drawable.ic_error_black_24dp);
        customErrorIcon.setBounds(0, 0, customErrorIcon.getIntrinsicWidth(), customErrorIcon.getIntrinsicHeight());

        if (TextUtils.isEmpty(edtNoOfMonths.getText().toString())) {
            edtNoOfMonths.setError("Enter the number od months", customErrorIcon);
            btnSubmit.setEnabled(false);
            btnSubmit.setTextColor(Color.argb(50, 0, 0, 0));
            edtNoOfMonths.requestFocus();
        } else if (TextUtils.isEmpty(edtBizProfID.getText().toString())) {
            edtBizProfID.setError("Enter the Biz Profile  ID.", customErrorIcon);
            btnSubmit.setEnabled(false);
            btnSubmit.setTextColor(Color.argb(50, 0, 0, 0));
            edtBizProfID.requestFocus();
        } else if (TextUtils.isEmpty(edtBizID.getText().toString())) {
            edtBizID.setError("Enter the Biz ID.", customErrorIcon);
            btnSubmit.setEnabled(false);
            edtBizID.requestFocus();
        }



        return false;
    }
    public void EmptyEditTextAfterDataInsert() {

        edtBizID.getText().clear();

        edtBizProfID.getText().clear();

        edtNoOfMonths.getText().clear();
        edtPaymentCode.getText().clear();

    }
    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);//you can cancel it by pressing back button
        progressDialog.setMessage("signing up wait ...");
        progressBar.show();//displays the progress bar
    }
    public boolean hasInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        @SuppressLint("MissingPermission") NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public boolean checkInternetConnection() {
        boolean hasInternetConnection = hasInternetConnection();
        if (!hasInternetConnection) {
            showWarningDialog("Internet connection failed");
        }

        return hasInternetConnection;
    }

    public void showWarningDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.button_ok, null);
        builder.show();
    }


    public void sendBizSub(View view) {
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onPause() {
        super.onPause();
        //overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onStop() {
        super.onStop();
        //overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }
    @Override
    protected void onStart() {
        super.onStart();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        overridePendingTransition(R.anim.base_slide_left_out, R.anim.bounce);

    }


    public void onResume(){
        super.onResume();
        overridePendingTransition(R.anim.base_slide_left_out, R.anim.bounce);
    }

    @Override
    public void onClick(View view) {

    }
}