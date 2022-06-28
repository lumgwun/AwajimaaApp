package com.skylightapp.Inventory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.klinker.android.send_message.BroadcastUtils;
import com.skylightapp.CameraActivity;
import com.skylightapp.Classes.AccountTypes;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Birthday;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Classes.Utils;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.LoginDirectorActivity;
import com.skylightapp.R;
import com.skylightapp.SMSAct;
import com.skylightapp.SignUpAct;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.skylightapp.Classes.Profile.PROFILE_PHONE;
import static com.skylightapp.Transactions.OurConfig.TWILIO_ACCOUNT_SID;
import static com.skylightapp.Transactions.OurConfig.TWILIO_AUTH_TOKEN;

public class InStocksAct extends AppCompatActivity {
    private static final String TAG = InStocksAct.class.getSimpleName();
    public static final int PICTURE_REQUEST_CODE = 505;
    SharedPreferences userPreferences;
    AppCompatEditText edtStockType;
    AppCompatEditText edtStockColor;
    AppCompatEditText edtStockModel;
    AppCompatEditText edtStockName;
    AppCompatEditText edtStockPricePU;
    AppCompatEditText edtStockSize;
    AppCompatEditText edtStockQty;
    String formattedDaysRem;
    protected DatePickerDialog datePickerDialog;
    Random ran;
    SecureRandom random;
    Context context;
    String stockDate;
    PreferenceManager preferenceManager;
    private Bundle bundle;
    String uStockType;
    private ProgressDialog progressDialog;
    int superID;
    long tellerID;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    private ProgressBar loadingPB;
    String superSurname;
    String superFirstName, uStockSize;
    String superPhoneNumber;
    String superEmailAddress;
    String managerAddress;
    String superUserName;
    String mLastUpdateTime, selectedGender, selectedOffice, selectedState;
    Birthday birthday;
    AppCompatButton dob1;
    String uStockModel, uStockColor, uStockName,stockerName;
    double uStockPricePerUnit;

    int stockIDNumber,uStockQuantity;
    int customerID1 ;
    DBHelper dbHelper;

    public static final String ACCOUNT_SID = System.getenv("ACb6e4c829a5792a4b744a3e6bd1cf2b4e");
    public static final String AUTH_TOKEN = System.getenv("0d5cbd54456dd0764786db0c37212578");

    Profile stockManager;
    AppCompatButton btnDoStockProcessing;
    Location mCurrentLocation = null;
    String daysRemaining;
    int daysBTWN;

    String acct;
    SQLiteDatabase sqLiteDatabase;

    AccountTypes accountTypeStr;
    ContentLoadingProgressBar progressBar;
    Gson gson,gson1;
    String json,json1,nIN;
    Profile userProfile;
    String pix;

    Long profileID2,comAcctID,profileFId,birthdayFID,acctFID;
    SharedPreferences.Editor editor ;
    private static boolean isPersistenceEnabled = false;
    DBHelper applicationDb;
    DatePicker picker;
    String userProfileType;
    Customer customer;
    Uri selectedImage;
    int selectedId;
    String userType,userRole, selectedStockPackage, selectedOfficeBranch;
    int daysInBetween;
    ArrayList<Profile> profiles;
    ArrayList<Stocks> stocksArrayList;
    CircleImageView stocksPix;
    private UserSuperAdmin userSuperAdmin;
    private AdminUser adminUser;
    int stocksID;
    private  Spinner spnStockBranch,spnStockPackage;
    String todayDate,name;
    private static final String PREF_NAME = "skylight";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_in_stocks);
        setTitle("New Stocks onBoarding");
        checkInternetConnection();
        btnDoStockProcessing = findViewById(R.id.submitStocks);
        adminUser=new AdminUser();
        gson1 = new Gson();
        gson = new Gson();
        progressBar = findViewById(R.id.progressBarStocks);
        userSuperAdmin =new UserSuperAdmin();
        stocksArrayList = new ArrayList<>();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        stockManager = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastSuperProfileUsed", "");
        userSuperAdmin = gson1.fromJson(json, UserSuperAdmin.class);
        ran = new Random();
        random = new SecureRandom();
        dbHelper = new DBHelper(this);
        stocksPix = findViewById(R.id.image_Stocks);
        picker=(DatePicker)findViewById(R.id.stock_date_);
        edtStockName = findViewById(R.id.StockItemName);
        edtStockModel = findViewById(R.id.StockItemModel);
        edtStockType = findViewById(R.id.StockItemType);
        edtStockColor = findViewById(R.id.StockItemColor);
        edtStockQty = findViewById(R.id.StockItemQty);
        edtStockPricePU = findViewById(R.id.StockItemPrice);
        edtStockSize = findViewById(R.id.stock_Size);

        stockManager =new Profile();
        profiles=new ArrayList<Profile>();
        btnDoStockProcessing.setOnClickListener(this::submitStocks);
        spnStockBranch = findViewById(R.id.StockBranch);
        spnStockPackage = findViewById(R.id.StockPackageName);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        stocksID = random.nextInt((int) (Math.random() * 101) + 110);

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        todayDate = mdformat.format(calendar.getTime());

        spnStockPackage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStockPackage = spnStockPackage.getSelectedItem().toString();
                selectedStockPackage = (String) parent.getSelectedItem();
                Toast.makeText(InStocksAct.this, "Package: "+ selectedStockPackage,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(selectedStockPackage !=null){
            edtStockModel.setVisibility(View.GONE);
            edtStockPricePU.setVisibility(View.GONE);
            edtStockColor.setVisibility(View.GONE);
            edtStockName.setVisibility(View.GONE);
            edtStockSize.setVisibility(View.GONE);

        }
        if (selectedStockPackage != null && selectedStockPackage.equalsIgnoreCase("Others")) {
            edtStockModel.setVisibility(View.VISIBLE);
            edtStockPricePU.setVisibility(View.VISIBLE);
            edtStockColor.setVisibility(View.VISIBLE);
            edtStockName.setVisibility(View.VISIBLE);
            edtStockSize.setVisibility(View.VISIBLE);

        }
        spnStockBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOfficeBranch = spnStockBranch.getSelectedItem().toString();
                selectedOfficeBranch = (String) parent.getSelectedItem();
                Toast.makeText(InStocksAct.this, "Stock Office: "+ selectedOfficeBranch,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(userSuperAdmin !=null){
            superSurname = userSuperAdmin.getUSurname();
            superFirstName = userSuperAdmin.getUFirstName();
            superID = userSuperAdmin.getUserID();
            //profileID1=profileID;
            superPhoneNumber = userSuperAdmin.getsPhoneNumber();
            superEmailAddress = userSuperAdmin.getEmail();
            superUserName = userSuperAdmin.getuUserName();
            userType = userSuperAdmin.getSAdminRole();
            userRole = userSuperAdmin.getSAdminRole();

        }else {
            if(stockManager !=null){
                superSurname = stockManager.getProfileLastName();
                superFirstName = stockManager.getProfileFirstName();
                superID = stockManager.getPID();
                //profileID1=profileID;
                superPhoneNumber = stockManager.getProfilePhoneNumber();
                superEmailAddress = stockManager.getProfileEmail();
                superUserName = stockManager.getProfileUserName();
                userType = stockManager.getProfileMachine();
                userRole = stockManager.getProfileRole();

            }

        }
        name=superSurname +""+superFirstName;

        if(stockManager !=null){
            superSurname = stockManager.getProfileLastName();
            superFirstName = stockManager.getProfileFirstName();
            superID = stockManager.getPID();
            //profileID1=profileID;
            superPhoneNumber = stockManager.getProfilePhoneNumber();
            superEmailAddress = stockManager.getProfileEmail();
            superUserName = stockManager.getProfileUserName();
            userType = stockManager.getProfileMachine();
            userRole = stockManager.getProfileRole();

        }

        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
        stockDate = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();

        stockIDNumber = random.nextInt((int) (Math.random() * 1573) + 1753);

        Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");


        if(stockDate.isEmpty()){
            stockDate=todayDate;
        }

        btnDoStockProcessing.setOnClickListener(view -> {
            startStockingActivity(selectedOfficeBranch, stockManager,stocksArrayList, stockDate,userSuperAdmin, selectedStockPackage, (int) stocksID);

        });


    }

    private void chooseDate() {
        stockDate = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();


    }
    public void submitStocks(View view) {
        startStockingActivity(selectedOfficeBranch, stockManager, stocksArrayList, stockDate,userSuperAdmin, selectedStockPackage, (int) stocksID);
    }
    public void sendTextMessage(String name) {
        Bundle smsBundle = new Bundle();
        String theMessage="New Stocks have been added"+""+"by"+name;
        smsBundle.putString(PROFILE_PHONE, "234806952459");
        smsBundle.putString("USER_PHONE", "234806952459");
        smsBundle.putString("smsMessage", theMessage);
        smsBundle.putString("to", "234806952459");
        Intent otpIntent = new Intent(InStocksAct.this, SMSAct.class);
        otpIntent.putExtras(smsBundle);
        otpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        setResult(RESULT_OK, otpIntent);
        //startActivity(itemPurchaseIntent);

    }


    public void startStockingActivity(String selectedOfficeBranch, Profile stockManager, ArrayList<Stocks> stocksArrayList, String stockDate, UserSuperAdmin userSuperAdmin, String selectedStockPackage, int stocksID) {
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        ran = new Random();
        random = new SecureRandom();
        dbHelper = new DBHelper(this);
        //stocksArrayList = new ArrayList<>();
        dbHelper = new DBHelper(this);
        if(userSuperAdmin !=null){
            superSurname = userSuperAdmin.getUSurname();
            superFirstName = userSuperAdmin.getUFirstName();
            superID = userSuperAdmin.getUserID();
            //profileID1=profileID;
            superPhoneNumber = userSuperAdmin.getsPhoneNumber();
            superEmailAddress = userSuperAdmin.getEmail();
            superUserName = userSuperAdmin.getuUserName();
            userType = userSuperAdmin.getSAdminRole();
            userRole = userSuperAdmin.getSAdminRole();

        }else {
            if(stockManager !=null){
                superSurname = stockManager.getProfileLastName();
                superFirstName = stockManager.getProfileFirstName();
                superID = stockManager.getPID();
                //profileID1=profileID;
                superPhoneNumber = stockManager.getProfilePhoneNumber();
                superEmailAddress = stockManager.getProfileEmail();
                superUserName = stockManager.getProfileUserName();
                userType = stockManager.getProfileMachine();
                userRole = stockManager.getProfileRole();

            }

        }

        if(stockManager !=null){
            superSurname = stockManager.getProfileLastName();
            superFirstName = stockManager.getProfileFirstName();
            superID = stockManager.getPID();
            //profileID1=profileID;
            superPhoneNumber = stockManager.getProfilePhoneNumber();
            superEmailAddress = stockManager.getProfileEmail();
            superUserName = stockManager.getProfileUserName();
            userType = stockManager.getProfileMachine();
            userRole = stockManager.getProfileRole();

            stockerName=superSurname+","+superFirstName;

        }
        boolean usernameTaken = false;

        if (selectedStockPackage.equalsIgnoreCase("Others")) {
            selectedStockPackage=uStockName;

            try {
                uStockName = edtStockName.getText().toString().trim();
            } catch (Exception e) {
                System.out.println("Oops!");
            }


            try {
                uStockModel = edtStockModel.getText().toString();
            } catch (Exception e) {
                System.out.println("Oops!");
            }
            try {
                uStockColor = edtStockColor.getText().toString();
            } catch (Exception e) {
                System.out.println("Oops!");
            }
            try {
                uStockType = Objects.requireNonNull(edtStockType.getText()).toString();
            } catch (Exception e) {
                System.out.println("Oops!");
                edtStockType.requestFocus();
            }
            try {
                uStockQuantity = Integer.parseInt(Objects.requireNonNull(edtStockQty.getText()).toString());
            } catch (Exception e) {
                System.out.println("Oops!");
                edtStockQty.requestFocus();
            }
            /*try {
                uStockPricePerUnit = Double.parseDouble(edtStockPricePU.getText().toString());
            } catch (Exception e) {
                System.out.println("Oops!");
                edtStockPricePU.requestFocus();
            }*/
            try {
                uStockSize = Objects.requireNonNull(edtStockSize.getText()).toString();
            } catch (Exception e) {
                System.out.println("Oops!");
                edtStockSize.requestFocus();
            }
        }else {
            uStockName=null;
            uStockModel=null;
            uStockColor=null;
            uStockType=null;
            uStockPricePerUnit=0.00;
            uStockSize=null;

        }
        Location location=null;
        String myTittle="InStock Operation";
        String tittle="InStock Operation";
        String details=uStockQuantity +""+"of"+""+selectedStockPackage+""+"to"+""+ selectedOfficeBranch;
        String myDetails="I added"+""+uStockQuantity +""+"of"+""+selectedStockPackage+""+"to"+""+ selectedOfficeBranch+"@"+ ""+ Utils.setLastSeenTime(stockDate);


        try {
            for (int i = 0; i < stocksArrayList.size(); i++) {
                try {
                    if (stocksArrayList.get(i).getStockType().equalsIgnoreCase(uStockType)&& stocksArrayList.get(i).getStockName().equalsIgnoreCase(uStockName) && stocksArrayList.get(i).getStockDate().equalsIgnoreCase(stockDate)){
                        if(stocksArrayList.get(i).getStockItemQty()==uStockQuantity){
                            Toast.makeText(InStocksAct.this, "A similar stock, has been entered for this date" , Toast.LENGTH_LONG).show();
                            return;

                        }

                    }else {
                        if (stockManager != null) {
                            stockManager.addTimeLine(myTittle,myDetails);
                            stockManager.addStocksAll(stocksID,selectedStockPackage,uStockType,uStockModel,uStockColor,uStockSize,uStockQuantity,selectedOfficeBranch,uStockPricePerUnit,stockDate);

                        }
                        if (userSuperAdmin != null) {
                            userSuperAdmin.addStocks(stocksID,selectedStockPackage,uStockType,uStockModel,uStockColor,uStockSize,uStockQuantity,selectedOfficeBranch,uStockPricePerUnit,stockDate);
                        }
                        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                            dbHelper.openDataBase();
                            sqLiteDatabase = dbHelper.getWritableDatabase();
                            dbHelper.insertStock(stocksID,superID,selectedStockPackage,uStockType,uStockModel,uStockColor,uStockSize,uStockQuantity,selectedOfficeBranch,stockDate,stockerName);

                        }

                        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                            dbHelper.openDataBase();
                            sqLiteDatabase = dbHelper.getWritableDatabase();
                            dbHelper.insertTimeLine(tittle,details,stockDate,location);
                        }


                        startStocksNotification();
                        sendTextMessage(stockerName);
                    }
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }

            }
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }


        BroadcastUtils.sendExplicitBroadcast(this, new Intent(), "skylight Stocks InStock operation");


        }


    public Bitmap getBitmap(String path) {
        Bitmap myBitmap = null;
        File imgFile = new File(path);
        if (imgFile.exists()) {
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }
        return myBitmap;
    }

    private void startStocksNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("New Stock Alert")
                        .setContentText("A New Stock has just been added");

        Intent notificationIntent = new Intent(this, LoginDirectorActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(LoginDirectorActivity.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICTURE_REQUEST_CODE && data != null) {
            selectedImage = null;
            if (resultCode == RESULT_OK) {
                selectedImage = data.getData();
                DBHelper dbHelper = new DBHelper(this);
                dbHelper.insertProfilePicture(superID,customerID1,selectedImage);
                if (selectedImage != null) {
                    stocksPix.setImageBitmap(getScaledBitmap(selectedImage));

                }

                Toast.makeText(this, "SUCCESS " , Toast.LENGTH_SHORT).show();
                setResultOk(selectedImage);
            }


            else if (resultCode == RESULT_CANCELED) {
                setResultCancelled();
                Toast.makeText(this, "CANCELLED " , Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
    private void setResultOk(Uri selectedImage) {
        Intent intent = new Intent();
        stocksPix.setImageBitmap(getScaledBitmap(selectedImage));
        setResult(AppCompatActivity.RESULT_OK, intent);
        finish();
    }

    private void setResultCancelled() {
        Intent intent = new Intent();
        setResult(AppCompatActivity.RESULT_CANCELED, intent);
        finish();
    }


    public String getPath(Uri photoUri) {

        String filePath = "";
        if (photoUri != null) {
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(photoUri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filePath = cursor.getString(columnIndex);
            cursor.close();
        }
        return filePath;
    }

    private File getDir() {
        File sdDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "LS Attachments");
    }
    private Bitmap getScaledBitmap(Uri uri){
        Bitmap thumb = null ;
        try {
            ContentResolver cr = getContentResolver();
            InputStream in = cr.openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize=8;
            thumb = BitmapFactory.decodeStream(in,null,options);
        } catch (FileNotFoundException e) {
            Toast.makeText(InStocksAct.this , "File not found" , Toast.LENGTH_SHORT).show();
        }
        return thumb ;
    }
    public void doSelectPix(View view) {

        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent, PICTURE_REQUEST_CODE);
        Toast.makeText(InStocksAct.this, "Picture selection in Progress",
                Toast.LENGTH_SHORT).show();

        /*if (intent.resolveActivity(getPackageManager()) != null) {
            mGetUserPixContent.launch(intent);
        } else {
            Toast.makeText(SuperUserCreator.this, "There is no app that support this action",
                    Toast.LENGTH_SHORT).show();
        }*/


    }

    public boolean hasInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(menuItem));



    }


}