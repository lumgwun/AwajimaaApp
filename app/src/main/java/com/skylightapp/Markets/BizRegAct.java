package com.skylightapp.Markets;

import static com.skylightapp.Classes.ImageUtil.TAG;
import static com.skylightapp.Database.DBHelper.DATABASE_NAME;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.widget.ContentLoadingProgressBar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.skylightapp.Classes.MultiSelectSpinner;
import com.skylightapp.Classes.PinEntryView;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.MarketBizDAO;
import com.skylightapp.LoginAct;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;
import com.skylightapp.SignUpAct;
import com.skylightapp.SuperAdmin.Awajima;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import de.hdodenhof.circleimageview.CircleImageView;

public class BizRegAct extends AppCompatActivity {
    TextView  error_msg;
    String database_passcode, database_id, dateOfReg;
    Intent intent;
    ContentLoadingProgressBar progressBar;
    static final int REQUEST_CODE = 123;
    public static final int PICTURE_REQUEST_CODE = 25;
    private static final int RESULT_LOAD_IMAGE = 14;
    private static final int RESULT_CAMERA_CODE = 90;
    private Calendar calendar;
    private ProgressDialog progressDialog;
    ByteArrayOutputStream bytes;
    private AppCompatEditText edtBrandName,edtAddress,edtCACNo;
    String creationDate;
    public static final String NOTIFICATION_CHANNEL_ID = "1301" ;
    SecureRandom random;
    int profileID;
    private static final String PREF_NAME = "awajima";
    Gson gson, gson1,gson3;
    String json, json1,json3, bDTittle, selectedBizType,brandName, address,CACNo,stateOfBiz;
    private Profile adminProfile;
    private long bizNo;
    SharedPreferences userPreferences;
    private AppCompatButton btnCreate,btnLogin;
    private MarketBizDAO bizDAO;
    CircleImageView profilePix;
    SQLiteDatabase sqLiteDatabase;
    private boolean locationPermissionGranted;
    private Calendar cal;
    Uri sound;
    ArrayList<MarketBusiness> bizS;
    private PinEntryView pinEntryView;
    private MarketBusiness marketBusiness,lastMarketBusinessUsed;
    protected DatePickerDialog datePickerDialog;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Uri mImageUri;
    String dateOfCACReg,email,uPhoneNumber,cacRegNo,cacRegName,bizDesc,countryOfBiz;
    AppCompatEditText edtPhone_number,edtRegName;
    AppCompatEditText email_address,edtDesc;
    CountryCodePicker ccp;
    Date date;
    Bitmap thumbnail;
    AppCompatTextView txtDateOfReg,txtState;
    private AppCompatSpinner spnCountry,spnState;
    ArrayList<String> selectedTypes;
    private MultiSelectSpinner multiSelectSpinner;
    int  day, month, year, newMonth,numMessages;
    private CardView cardViewState;
    private Awajima awajima;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    int PERMISSION_ALL = 32;
    String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    ActivityResultLauncher<Intent> mGetPixContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Intent data = new Intent();
                            if (result.getData() != null) {
                                if(result !=null){
                                    data = result.getData();
                                }
                                if(data !=null){
                                    mImageUri = data.getData();

                                }

                                if (mImageUri != null) {
                                    profilePix.setImageBitmap(getScaledBitmap(mImageUri));
                                } else {
                                    Toast.makeText(BizRegAct.this, "Error getting Photo",
                                            Toast.LENGTH_SHORT).show();
                                }


                            }

                            Toast.makeText(BizRegAct.this, "Image picking returned successful", Toast.LENGTH_SHORT).show();
                            //doProcessing();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(BizRegAct.this, "Activity canceled", Toast.LENGTH_SHORT).show();
                            //finish();
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + result.getResultCode());
                    }
                }
                /*@Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        finish();
                    }
                }*/
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_biz_reg);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        checkInternetConnection();
        gson1 = new Gson();
        gson = new Gson();
        marketBusiness= new MarketBusiness();
        selectedTypes=new ArrayList<>();
        bizS= new ArrayList<>();
        lastMarketBusinessUsed= new MarketBusiness();
        bizDAO= new MarketBizDAO(this);
        awajima= new Awajima();
        adminProfile = new Profile();
        profilePix = findViewById(R.id.bizLogo);
        edtRegName = findViewById(R.id.biz_regN);
        edtDesc = findViewById(R.id.biz_descN);

        edtBrandName = findViewById(R.id.biz_brandN);
        edtPhone_number = findViewById(R.id.biz_phone_number);
        email_address = findViewById(R.id.email_biz);
        edtAddress = findViewById(R.id.address_biz);
        btnCreate = findViewById(R.id.biz_reg);
        error_msg = findViewById(R.id.error_m33);
        btnLogin = findViewById(R.id.biz_Login);
        cardViewState = findViewById(R.id.card_state);
        txtState = findViewById(R.id.state_OfOp);

        multiSelectSpinner = findViewById(R.id.type_spinnerB);

        progressBar = findViewById(R.id.progressBar_Biz);
        spnCountry = findViewById(R.id.biz_country);
        spnState = findViewById(R.id.biz_states);
        txtDateOfReg = findViewById(R.id.biz_doR);
        txtDateOfReg.setOnClickListener(this::dateRegPicker);
        edtCACNo = findViewById(R.id.phone_number);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);

        ccp.registerCarrierNumberEditText(edtPhone_number);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        adminProfile = gson.fromJson(json, Profile.class);

        edtPhone_number.addTextChangedListener(new TextWatcher() {
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
        ccp.registerCarrierNumberEditText(edtPhone_number);


        ccp.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
                // your code
            }
        });
        email = email_address.getText().toString().trim();
        email_address.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (email.matches(emailPattern) && s.length() > 0) {
                    Toast.makeText(BizRegAct.this, "valid email address", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(BizRegAct.this, "Invalid email address", Toast.LENGTH_SHORT).show();

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        edtAddress.addTextChangedListener(new TextWatcher() {
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
        edtRegName.addTextChangedListener(new TextWatcher() {
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
        edtCACNo.addTextChangedListener(new TextWatcher() {
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
        edtPhone_number.addTextChangedListener(new TextWatcher() {
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


        bizNo = ThreadLocalRandom.current().nextInt(130, 1010041);
        if (adminProfile != null) {
            profileID = adminProfile.getPID();

        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BizRegAct.this, LoginAct.class);
                startActivity(intent);
                finish();
            }
        });
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        creationDate = mdformat.format(calendar.getTime());

        selectedTypes = multiSelectSpinner.getSelectedItems();

        spnCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countryOfBiz = spnCountry.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(countryOfBiz !=null){
            if(countryOfBiz.equalsIgnoreCase("Nigeria")){
                cardViewState.setVisibility(View.VISIBLE);
                txtState.setVisibility(View.VISIBLE);

            }
        }
        spnState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stateOfBiz = spnState.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        profilePix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasPermissions(BizRegAct.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(BizRegAct.this, PERMISSIONS, PERMISSION_ALL);
                }

                final PopupMenu popup = new PopupMenu(BizRegAct.this, profilePix);
                popup.getMenuInflater().inflate(R.menu.profile, popup.getMenu());
                setTitle("Photo selection in Progress...");

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.Camera) {
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, RESULT_CAMERA_CODE);

                        }

                        if (item.getItemId() == R.id.Gallery) {
                            Intent i = new Intent(
                                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                            startActivityForResult(i, RESULT_LOAD_IMAGE);
                        }
                        if (item.getItemId() == R.id.cancel_action) {
                            setTitle("Awajima onBoarding");
                        }

                        return true;
                    }
                });
                popup.show();//showing popup menu


            }

        });
        cal = Calendar.getInstance();
        txtDateOfReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                newMonth = month + 1;
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(BizRegAct.this, R.style.DatePickerDialogStyle, mDateSetListener, day, month, year);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //make transparent background.
                dialog.show();
                dateOfReg = year + "-" + newMonth + "-" + day;
                dateOfCACReg = day + "-" + newMonth + "-" + year;
                txtDateOfReg.setText("Your date of Birth:" + dateOfReg);

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                Log.d(TAG, "onDateSet: date of Birth: " + day + "-" + month + "-" + year);
                dateOfReg = year + "-" + newMonth + "-" + day;
                dateOfCACReg = day + "-" + newMonth + "-" + year;
                txtDateOfReg.setText("Your date of Birth:" + dateOfReg);


            }


        };
        dateOfCACReg = day + "-" + newMonth + "-" + year;

        try {

            sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        btnCreate.setOnClickListener(this::DoBizReg);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //uPhoneNumber = edtPhone_number.getText().toString();
                brandName = edtBrandName.getText().toString();
                cacRegNo = edtCACNo.getText().toString();
                address = edtAddress.getText().toString();
                cacRegName = edtRegName.getText().toString();
                email = email_address.getText().toString();
                bizDesc = edtDesc.getText().toString();

                uPhoneNumber=ccp.getFullNumberWithPlus();

                if (!uPhoneNumber.isEmpty() && uPhoneNumber.length() > 10 && uPhoneNumber.matches("^[0-9]{10}")) {
                    edtPhone_number.setError(null);
                } else {
                    edtPhone_number.requestFocus();
                    if (uPhoneNumber.isEmpty()) {
                        edtPhone_number.setError("Mobile Number is required");
                    }
                }

                marketBusiness.setBizID(bizNo);
                marketBusiness.setBusinessName(cacRegName);
                marketBusiness.setBizBrandname(brandName);
                marketBusiness.setBizDescription(bizDesc);
                marketBusiness.setBizPicture(mImageUri);
                marketBusiness.setBizID(bizNo);
                marketBusiness.setBizAddress(address);
                marketBusiness.setBizEmail(email);
                marketBusiness.setBizPhoneNo(uPhoneNumber);
                marketBusiness.setBizRegNo(cacRegNo);
                marketBusiness.setBizType(selectedBizType);
                marketBusiness.setDateOfJoin(creationDate);
                marketBusiness.setBizCountry(countryOfBiz);
                marketBusiness.setBizState(stateOfBiz);
                marketBusiness.setmBusProfile(adminProfile);
                marketBusiness.setmBDateOfCACReg(dateOfCACReg);
                if(awajima !=null){
                    awajima.addBusiness(marketBusiness);
                }

                if (userPreferences !=null){
                    userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                    gson3 = new Gson();
                    lastMarketBusinessUsed= marketBusiness;
                    json3 = gson3.toJson(lastMarketBusinessUsed);

                }


                SharedPreferences.Editor editor = null;
                if (userPreferences != null) {
                    editor = userPreferences.edit();
                }
                if (editor != null) {

                    editor.putString("LastMarketBusinessUsed", json3).apply();
                }


                for (int i = 0; i < bizS.size(); i++) {
                    try {
                        if (bizS.get(i).getBizBrandname().equalsIgnoreCase(brandName) ||bizS.get(i).getBusinessName().equalsIgnoreCase(cacRegName)) {
                            Toast.makeText(BizRegAct.this, "This Biz Detail is already in use, here", Toast.LENGTH_LONG).show();
                            return;

                        } else {
                            if(adminProfile !=null){
                                adminProfile.addBusinessID(bizNo);
                                try {
                                    if(adminProfile.getProfile_Businesses().size()<3){
                                        adminProfile.addMarketBusiness(marketBusiness);
                                    }
                                    if(adminProfile.getProfile_Businesses().size()==0){
                                        adminProfile.setProfileBusiness(marketBusiness);
                                    }

                                } catch (IndexOutOfBoundsException e) {
                                    e.printStackTrace();
                                }

                            }
                            if(bizDAO !=null){
                                try {

                                    if(sqLiteDatabase !=null){
                                        sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                                    }
                                } catch (SQLiteException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    bizDAO.saveBiz(bizNo, profileID, cacRegName,cacRegNo,dateOfCACReg, brandName,bizDesc, creationDate, selectedBizType,mImageUri,uPhoneNumber,address, email,countryOfBiz,stateOfBiz);

                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }

                            }

                        }
                    } catch (NullPointerException e) {
                        System.out.println("Oops!");
                    }

                }

            }
        });



    }
    private void doOtpNotification(String otpMessage) {

        try {
            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(BizRegAct.this, sound);
            ringtone.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Uri sound = Uri. parse (ContentResolver. SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/raw/quite_impressed.mp3" ) ;
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.logo_awa_round)
                        .setContentTitle("Your Biz has been Registration")
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

        if (TextUtils.isEmpty(edtPhone_number.getText().toString())) {
            edtPhone_number.setError("Enter Biz. Phone No.", customErrorIcon);
            btnCreate.setEnabled(false);
            btnCreate.setTextColor(Color.argb(50, 0, 0, 0));
            edtPhone_number.requestFocus();
        } else if (TextUtils.isEmpty(edtAddress.getText().toString())) {
            edtAddress.setError("Enter Biz. Address", customErrorIcon);
            btnCreate.setEnabled(false);
            btnCreate.setTextColor(Color.argb(50, 0, 0, 0));
            edtAddress.requestFocus();
        } else if (TextUtils.isEmpty(edtBrandName.getText().toString())) {
            edtBrandName.setError("Enter Brand Name", customErrorIcon);
            btnCreate.setEnabled(false);
            edtBrandName.requestFocus();
        }

        else if (TextUtils.isEmpty(edtCACNo.getText().toString())) {
            edtCACNo.setError("Enter CAC Number", customErrorIcon);
            btnCreate.setEnabled(false);
            btnCreate.setTextColor(Color.argb(50, 0, 0, 0));
            edtCACNo.requestFocus();
        }
        else if (TextUtils.isEmpty(edtRegName.getText().toString())) {
            edtRegName.setError("Enter Official Name", customErrorIcon);
            btnCreate.setEnabled(false);
            btnCreate.setTextColor(Color.argb(50, 0, 0, 0));
            edtRegName.requestFocus();
        }


        return false;
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
        progressBar.show();
    }
    public Bitmap getBitmap(String path) {
        Bitmap myBitmap = null;
        File imgFile = new File(path);
        if (imgFile.exists()) {
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }
        return myBitmap;
    }
    private Bitmap getScaledBitmap(Uri uri) {
        Bitmap thumb = null;
        try {
            ContentResolver cr = getContentResolver();
            InputStream in = cr.openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            thumb = BitmapFactory.decodeStream(in, null, options);
        } catch (FileNotFoundException e) {
            Toast.makeText(BizRegAct.this, "File not found", Toast.LENGTH_SHORT).show();
        }
        return thumb;
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


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    public void DoBizReg(View view) {
    }

    public void dateRegPicker(View view) {
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        newMonth = month + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(BizRegAct.this, R.style.DatePickerDialogStyle, mDateSetListener, day, month, year);
        dialog.show();
        dateOfReg = year + "-" + newMonth + "-" + day;
        dateOfCACReg = day + "-" + newMonth + "-" + year;
        txtDateOfReg.setText("Your date of Birth:" + dateOfReg);
    }
}