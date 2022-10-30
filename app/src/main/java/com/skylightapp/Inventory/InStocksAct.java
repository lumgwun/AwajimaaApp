package com.skylightapp.Inventory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
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

import com.blongho.country_data.Currency;
import com.blongho.country_data.World;
import com.google.gson.Gson;
import com.klinker.android.send_message.BroadcastUtils;
import com.skylightapp.Adapters.CurrAdapter;
import com.skylightapp.Adapters.OfficeAdapter;
import com.skylightapp.Admins.TellerReportUpdateAct;
import com.skylightapp.CameraActivity;
import com.skylightapp.Classes.AccountTypes;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Birthday;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Classes.Utils;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MarketBizDAO;
import com.skylightapp.Database.OfficeBranchDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Database.StocksDAO;
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.LoginDirAct;
import com.skylightapp.MarketClasses.MarketBizArrayAdapter;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.MarketClasses.MarketStocksAd;
import com.skylightapp.R;
import com.skylightapp.SMSAct;
import com.twilio.Twilio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.skylightapp.Classes.Profile.PROFILE_PHONE;

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
    protected DatePickerDialog datePickerDialog;
    Random ran;
    SecureRandom random;
    String stockDate;

    String uStockType;
    int superID;
    String superSurname;
    String superFirstName, uStockSize;
    String superPhoneNumber;
    String superEmailAddress;
    String superUserName;
    String uStockModel, uStockColor, uStockName,stockerName;
    double uStockPricePerUnit;

    int stockIDNumber,uStockQuantity;
    int customerID1 ;
    DBHelper dbHelper;

    public static final String ACCOUNT_SID = System.getenv("ACb6e4c829a5792a4b744a3e6bd1cf2b4e");
    public static final String AUTH_TOKEN = System.getenv("0d5cbd54456dd0764786db0c37212578");

    Profile stockProfile;
    AppCompatButton btnDoStockProcessing;

    SQLiteDatabase sqLiteDatabase;


    ContentLoadingProgressBar progressBar;
    Gson gson,gson1;
    String json,json1;


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
    int stocksID,selectedCurrencyIndex,selectedStocksIndex,selectedBizIndex,selectedOfficeIndex;
    private AppCompatSpinner spnStockBranch,spnStockPackage,spnStockBiz,spnCurencies;
    String todayDate,name;
    private static final String PREF_NAME = "awajima";
    private ArrayList<MarketBusiness> marketBizS;
    private MarketBusiness marketBusiness;
    private ArrayList<OfficeBranch> bizOffices;
    private OfficeBranch officeB;
    private OfficeAdapter officeBranchAdapter;
    private Currency currency;
    private List<Currency> currenyList;
    private CurrAdapter currencyAdapter;
    private  StocksArrayAdapter stocksAdapter;
    private MarketStocksAd marketStocksAd;
    private MarketBizArrayAdapter mBizAdapter;
    private  Stocks stocks;
    private int branchID,profileID;
    private long bizID;
    private StocksDAO stockDao;
    private MarketBizDAO marketBizDao;
    private OfficeBranchDAO officeBranchDao;
    private String currencyCode, bizPhoneNo,SharedPrefUserMachine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_in_stocks);
        setTitle("New Stocks onBoarding");
        checkInternetConnection();
        btnDoStockProcessing = findViewById(R.id.submitStocks);
        adminUser=new AdminUser();
        marketBizDao= new MarketBizDAO(this);
        gson1 = new Gson();
        gson = new Gson();
        officeB= new OfficeBranch();
        stockProfile= new Profile();
        World.init(this);
        stocks= new Stocks();
        stockDao= new StocksDAO(this);
        officeBranchDao= new OfficeBranchDAO(this);
        progressBar = findViewById(R.id.progressBarStocks);
        userSuperAdmin =new UserSuperAdmin();
        stocksArrayList = new ArrayList<>();
        marketBizS=new ArrayList<>();
        marketBusiness= new MarketBusiness();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        stockProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastUserSuperAdminUsed", "");
        userSuperAdmin = gson1.fromJson(json1, UserSuperAdmin.class);
        SharedPrefUserMachine=userPreferences.getString("machine", "");

        ran = new Random();
        random = new SecureRandom();
        dbHelper = new DBHelper(this);
        currenyList=World.getAllCurrencies();
        stocksPix = findViewById(R.id.image_Stocks);
        picker=(DatePicker)findViewById(R.id.stock_date_);
        edtStockName = findViewById(R.id.StockItemName);
        edtStockModel = findViewById(R.id.StockItemModel);
        edtStockType = findViewById(R.id.StockItemType);
        edtStockColor = findViewById(R.id.StockItemColor);
        edtStockQty = findViewById(R.id.StockItemQty);
        edtStockPricePU = findViewById(R.id.StockItemPrice);
        edtStockSize = findViewById(R.id.stock_Size);

        stockProfile =new Profile();
        profiles=new ArrayList<Profile>();
        btnDoStockProcessing.setOnClickListener(this::submitStocks);
        spnStockBranch = findViewById(R.id.StockBranch);
        spnStockPackage = findViewById(R.id.StockPackageName);
        spnStockBiz = findViewById(R.id.StockBusinessP);
        spnCurencies = findViewById(R.id.StockItenCurre);


        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        stocksID = random.nextInt((int) (Math.random() * 101) + 110);
        if(stockProfile !=null){
            profileID=stockProfile.getPID();
            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                sqLiteDatabase = applicationDb.getReadableDatabase();
                try {
                    marketBizS=marketBizDao.getAllBusinessesForProfile(profileID);
                } catch (Exception e) {
                    System.out.println("Oops!");
                }


            }
            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                sqLiteDatabase = applicationDb.getReadableDatabase();
                try {
                    stocksArrayList=stockDao.getAllStocksForProfile(profileID);
                } catch (Exception e) {
                    System.out.println("Oops!");
                }


            }

        }
        if(SharedPrefUserMachine !=null){
            if(SharedPrefUserMachine.equalsIgnoreCase("AwajimaSuperAdmin")){

                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    sqLiteDatabase = applicationDb.getReadableDatabase();
                    try {
                        stocksArrayList=stockDao.getALLStocksSuper();
                    } catch (Exception e) {
                        System.out.println("Oops!");
                    }


                }
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    sqLiteDatabase = applicationDb.getReadableDatabase();
                    try {
                        bizOffices=officeBranchDao.getAllOfficeBranches();
                    } catch (Exception e) {
                        System.out.println("Oops!");
                    }


                }
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    sqLiteDatabase = applicationDb.getReadableDatabase();
                    try {
                        marketBizS=marketBizDao.getAllBusinesses();
                    } catch (Exception e) {
                        System.out.println("Oops!");
                    }


                }

            }
            if(SharedPrefUserMachine.equalsIgnoreCase("AwajimaAdmin")){

            }

        }

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        todayDate = mdformat.format(calendar.getTime());

        mBizAdapter = new MarketBizArrayAdapter(InStocksAct.this,R.layout.item_market_biz, marketBizS);
        spnStockBiz.setAdapter(mBizAdapter);
        spnStockBiz.setSelection(0);
        selectedBizIndex = spnStockBiz.getSelectedItemPosition();

        try {
            marketBusiness = marketBizS.get(selectedBizIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }
        if(marketBusiness !=null){
            stocksArrayList=marketBusiness.getmBStockList();
            bizOffices=marketBusiness.getOfficeBranches();
            bizID=marketBusiness.getBusinessID();
            bizPhoneNo=marketBusiness.getBizPhoneNo();

        }

        stocksAdapter = new StocksArrayAdapter(InStocksAct.this, R.layout.stock_row2,stocksArrayList);
        spnStockPackage.setAdapter(stocksAdapter);
        spnStockPackage.setSelection(0);
        selectedStocksIndex = spnStockPackage.getSelectedItemPosition();

        try {
            stocks = stocksArrayList.get(selectedStocksIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }
        if(stocks !=null){
            selectedStockPackage=stocks.getStockName();

        }




        currencyAdapter = new CurrAdapter(InStocksAct.this,R.layout.list_currency, currenyList);
        spnCurencies.setAdapter(currencyAdapter);
        spnCurencies.setSelection(0);
        selectedCurrencyIndex = spnCurencies.getSelectedItemPosition();

        try {
            currency = currenyList.get(selectedCurrencyIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }
        if(currency !=null){
            currencyCode=currency.getCode();

        }


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

        officeBranchAdapter = new OfficeAdapter(InStocksAct.this, R.layout.office_row,bizOffices);
        spnStockBranch.setAdapter(officeBranchAdapter);
        spnStockBranch.setSelection(0);
        selectedOfficeIndex = spnStockBranch.getSelectedItemPosition();

        try {
            officeB = bizOffices.get(selectedOfficeIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }
        if(stocks !=null){
            selectedStockPackage=stocks.getStockName();

        }
        if(officeB !=null){
            selectedOfficeBranch=officeB.getOfficeBranchName();
            branchID=officeB.getOfficeBranchID();

        }

        if(stockProfile !=null){
            superSurname = stockProfile.getProfileLastName();
            superFirstName = stockProfile.getProfileFirstName();
            superID = stockProfile.getPID();
            //profileID1=profileID;
            superPhoneNumber = stockProfile.getProfilePhoneNumber();
            superEmailAddress = stockProfile.getProfileEmail();
            superUserName = stockProfile.getProfileUserName();
            userType = stockProfile.getProfileMachine();
            userRole = stockProfile.getProfileRole();

        }

        name=superSurname +","+superFirstName;


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

            startStockingActivity(selectedOfficeBranch, stockProfile,stocksArrayList, stockDate,userSuperAdmin, selectedStockPackage,  stocksID,branchID,currencyCode,bizID,bizPhoneNo);

        });


    }

    private void chooseDate() {
        stockDate = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();


    }
    public void submitStocks(View view) {
        startStockingActivity(selectedOfficeBranch, stockProfile, stocksArrayList, stockDate,userSuperAdmin, selectedStockPackage, stocksID, branchID, currencyCode, bizID, bizPhoneNo);
    }
    public void sendTextMessage(String name) {
        Bundle smsBundle = new Bundle();
        String theMessage="New Stocks have been added"+""+"by"+name;
        smsBundle.putString(PROFILE_PHONE, bizPhoneNo);
        smsBundle.putString("USER_PHONE", bizPhoneNo);
        smsBundle.putString("smsMessage", theMessage);
        smsBundle.putString("to", bizPhoneNo);
        Intent otpIntent = new Intent(InStocksAct.this, SMSAct.class);
        otpIntent.putExtras(smsBundle);
        otpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        setResult(RESULT_OK, otpIntent);
        //startActivity(itemPurchaseIntent);

    }


    public void startStockingActivity(String selectedOfficeBranch, Profile stockManager, ArrayList<Stocks> stocksArrayList, String stockDate, UserSuperAdmin userSuperAdmin, String selectedStockPackage, int stocksID, int branchID, String currencyCode, long bizID, String bizPhoneNo) {
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        ran = new Random();
        random = new SecureRandom();
        dbHelper = new DBHelper(this);
        //stocksArrayList = new ArrayList<>();
        dbHelper = new DBHelper(this);
        if(userSuperAdmin !=null){
            superSurname = userSuperAdmin.getSSurname();
            superFirstName = userSuperAdmin.getSFirstName();
            superID = userSuperAdmin.getSuperID();
            //profileID1=profileID;
            superPhoneNumber = userSuperAdmin.getsPhoneNumber();
            superEmailAddress = userSuperAdmin.getSEmailAddress();
            superUserName = userSuperAdmin.getSuperUserName();
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
                            stockManager.addPTimeLine(myTittle,myDetails);
                            stockManager.addPStocksAll(stocksID,selectedStockPackage,uStockType,uStockModel,uStockColor,uStockSize,uStockQuantity,selectedOfficeBranch,uStockPricePerUnit,stockDate);

                        }
                        if (userSuperAdmin != null) {
                            userSuperAdmin.addStocks(stocksID,selectedStockPackage,uStockType,uStockModel,uStockColor,uStockSize,uStockQuantity,selectedOfficeBranch,uStockPricePerUnit,stockDate);
                        }
                        StocksDAO stocksDAO= new StocksDAO(this);
                        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                            dbHelper.openDataBase();
                            sqLiteDatabase = dbHelper.getWritableDatabase();
                            stocksDAO.insertStock(stocksID,superID,selectedStockPackage,uStockType,uStockModel,uStockColor,uStockSize,uStockQuantity,selectedOfficeBranch,stockDate,stockerName);

                        }
                        TimeLineClassDAO timeLineClassDAO= new TimeLineClassDAO(this);
                        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                            dbHelper.openDataBase();
                            sqLiteDatabase = dbHelper.getWritableDatabase();
                            timeLineClassDAO.insertTimeLine(tittle,details,stockDate,location);
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

        Intent notificationIntent = new Intent(this, LoginDirAct.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(LoginDirAct.class);
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
                ProfDAO profDAO= new ProfDAO(this);

                profDAO.insertProfilePicture(superID,customerID1,selectedImage);
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