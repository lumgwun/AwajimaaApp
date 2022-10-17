package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.skylightapp.Adapters.AccountAdapter2;
import com.skylightapp.Adapters.OfficeAdapter;
import com.skylightapp.Admins.AdminBankDeposit;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Utils;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.AdminBDepositDAO;
import com.skylightapp.Database.AdminBalanceDAO;
import com.skylightapp.Database.BirthdayDAO;
import com.skylightapp.Database.CodeDAO;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.LoanDAO;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.Database.PaymDocDAO;
import com.skylightapp.Database.PaymentCodeDAO;
import com.skylightapp.Database.PaymentDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Database.SODAO;
import com.skylightapp.Database.TCashDAO;
import com.skylightapp.Database.TReportDAO;
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.LoginDirAct;
import com.skylightapp.MarketClasses.AttachmentPreviewAdapter;
import com.skylightapp.MarketClasses.BizDealAccount;
import com.skylightapp.MarketClasses.BizDealAcctAdapter;
import com.skylightapp.MarketClasses.BizDealAdapter;
import com.skylightapp.MarketClasses.BusinessDeal;
import com.skylightapp.MarketClasses.ChatAdapter;
import com.skylightapp.MarketClasses.MarketAdmin;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.MarketClasses.StrokedTextView;
import com.skylightapp.MarketClasses.UserProfileInfo;
import com.skylightapp.R;

import org.jivesoftware.smack.ConnectionListener;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import static com.skylightapp.BuildConfig.QUICKBLOX_ACCT_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_APP_ID;
import static com.skylightapp.BuildConfig.QUICKBLOX_AUTH_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_SECRET_KEY;

public class BizCusDepositAct extends AppCompatActivity {
    Date currentDate;
    public final static int KEY_EXTRA_REPORT_ID = 1224;

    private DBHelper selector;
    private Profile userProfile;
    private AdminUser adminUser;
    //private AdminUser adminUser;
    private Context context;
    private SharedPreferences userPreferences;
    private PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1;
    private  Bundle messageBundle;
    private  int senderProfileID,adminDepositID,SharedPrefAdminID;
    private  String purpose;
    private Spinner spnBusinessDeal , spnDepositOffice;
    private  String selectedPurpose,sendee;
    private Customer customer;
    DatePicker picker;
    private  Spinner spnToFrom;
    private AppCompatButton btnConfirmation;
    EditText edtAmounts;
    private double amountEntered;
    private ArrayList<AdminBankDeposit> adminBankDeposits;
    private int adminID;
    private int noOfSavings;
    DBHelper applicationDb;
    int officeBranchID;
    int selectedNoIndex,intAdID;
    SecureRandom random;
    AdminBankDeposit adminBankDeposit;
    private static final String PREF_NAME = "skylight";
    private Calendar calendar;
    SQLiteDatabase sqLiteDatabase;
    private SODAO sodao;
    private TranXDAO tranXDAO;
    private MessageDAO messageDAO;
    private LoanDAO loanDAO;
    private AcctDAO acctDAO;
    private CodeDAO codeDAO;
    private PaymDocDAO paymDocDAO;
    private CusDAO cusDAO;
    private PaymentCodeDAO paymentCodeDAO;
    private ProfDAO profileDao;
    private TCashDAO cashDAO;
    private TReportDAO tReportDAO;
    private PaymentDAO paymentDAO;
    private AdminBalanceDAO adminBalanceDAO;
    private BirthdayDAO birthdayDAO;
    private AdminBDepositDAO adminBDepositDAO;
    private TimeLineClassDAO timeLineClassDAO;
    private static final String TAG = ChatActEthernal.class.getSimpleName();
    private static final int REQUEST_CODE_ATTACHMENT = 721;

    private static final String PROPERTY_SAVE_TO_HISTORY = "save_to_history";
    public static final String EXTRA_DIALOG_ID = "dialogId";
    public static final String EXTRA_DELETE_DIALOG = "deleteDialog";
    public static final String EXTRA_DIALOG_DATA = "dialogData";

    private ProgressBar progressBar;
    private StickyListHeadersListView messagesListView;
    private EditText messageEditText;

    private RelativeLayout emptyChatLayout;
    private TextView emptyChatMatchText;
    private TextView emptyChatTimeText;
    private CircleImageView emptyChatCircleImageView;
    private StrokedTextView emptyChatMatchValueText;
    private CircularProgressIndicator emptyChatIndicator;

    private LinearLayout attachmentPreviewContainerLayout;
    private Snackbar snackbar;
    private ChatAdapter chatAdapter;
    private AttachmentPreviewAdapter attachmentPreviewAdapter;
    private ConnectionListener chatConnectionListener;

    private QBChatDialog qbChatDialog;
    private ArrayList<QBChatMessage> unShownMessages;
    private ChatActEthernal.ChatMessageListener chatMessageListener;
    private DBHelper dbHelper;
    private Bundle bizNameBundle;
    private int bizID,marketID,selectedOfficeIndex;

    private View.OnClickListener openProfileActivityOnClickListener;
    private int skipPagination = 0;
    private static final String APPLICATION_ID = QUICKBLOX_APP_ID;   //QUICKBLOX_APP_ID
    private static final String AUTH_KEY = QUICKBLOX_AUTH_KEY;
    private static final String AUTH_SECRET = QUICKBLOX_SECRET_KEY;
    private static final String ACCOUNT_KEY = QUICKBLOX_ACCT_KEY;
    private static final String SERVER_URL = "";
    public static final String USER_DEFAULT_PASSWORD = "quickblox";
    private UserProfileInfo userProfileInfo;

    private MarketBusiness marketBusiness;

    private boolean locationPermissionGranted;
    int profileID, birthdayID, messageID;
    Gson gson2,gson3,gson4;
    String  json2,json3,json4;
    private Profile lastProfileUsed;
    int PERMISSION_ALL = 1;
    private OfficeAdapter officeAdapter;
    private AccountAdapter2 accountAdapter2;
    private Spinner spnSelectBankAcct,spnDepositTranxType;
    private ArrayList<OfficeBranch> officeBranchArrayList;
    private ArrayList<Account> accountArrayList;
    private ArrayList<BusinessDeal> businessDealArrayList;
    private ArrayList<BizDealAccount> bizDealAccountArrayList;
    private  String acctType;
    private String acctPaymentIntent;
    private Bundle customerBundle;
    private Account account;
    String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    int PERMISSION_ALL33 = 2;
    String[] PERMISSIONS33 = {
            Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.SEND_SMS
    };
    String SharedPrefUserPassword;
    String dateSting;
    String selectedBank;
    String stringNoOfSavings;
    OfficeBranch officeBranch;
    String dateOfReport;
    String cmFirstName;
    String cmLastName;
    String adminOfficer;
    String SharedPrefUserMachine;
    String phoneNo;
    String SharedPrefUserName;
    String SharedPrefProfileID;
    int selectedOfficeID;
    String adminName,selectedfficeBranch,selectedTranxType,selectedToFrom;
    int item_biz_acct,acctNo,selectedDealIndex;
    private BusinessDeal businessDeal;
    private BizDealAccount bizDealAcct;
    private MarketAdmin marketAdmin;
    private AdminUser.ADMIN_TYPE admin_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_biz_cus_deposit);
        setTitle("Customer Deposit Arena");
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        bizDealAcct= new BizDealAccount();
        marketAdmin= new MarketAdmin();
        businessDeal= new BusinessDeal();
        bizDealAccountArrayList= new ArrayList<>();
        accountArrayList= new ArrayList<>();
        officeBranchArrayList= new ArrayList<>();
        adminBankDeposits =new ArrayList<>();
        businessDealArrayList =new ArrayList<>();
        customerBundle= new Bundle();
        gson1 = new Gson();
        userProfile=new Profile();
        adminUser =new AdminUser();
        bizNameBundle= new Bundle();
        gson = new Gson();
        gson4 = new Gson();
        gson1 = new Gson();
        gson2 = new Gson();
        gson3 = new Gson();
        marketBusiness= new MarketBusiness();
        userProfileInfo= new UserProfileInfo();
        QBSettings.getInstance().init(this, APPLICATION_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
        dbHelper = new DBHelper(this);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefProfileID=userPreferences.getString("PROFILE_ID", "");
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastAdminUserUsed", "");
        adminUser = gson1.fromJson(json1, AdminUser.class);
        json2 = userPreferences.getString("LastUserProfileInfoUsed", "");
        userProfileInfo = gson2.fromJson(json2, UserProfileInfo.class);
        json3 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson3.fromJson(json3, MarketBusiness.class);

        json4 = userPreferences.getString("LastMarketAdminUsed", "");
        marketAdmin = gson4.fromJson(json4, MarketAdmin.class);
        bizNameBundle=getIntent().getExtras();
        if(bizNameBundle !=null){
            bizID=bizNameBundle.getInt("MARKET_BIZ_ID");
            marketID=bizNameBundle.getInt("MARKET_ID");
        }
        if(customerBundle !=null){
            acctType=customerBundle.getString("AcctType");
            acctPaymentIntent=customerBundle.getString("Intent");
            account=customerBundle.getParcelable("Account");

        }
        adminBankDeposit= new AdminBankDeposit();
        random = new SecureRandom();
        applicationDb = new DBHelper(this);
        cusDAO= new CusDAO(this);
        paymentCodeDAO= new PaymentCodeDAO(this);
        profileDao= new ProfDAO(this);
        cashDAO= new TCashDAO(this);
        paymDocDAO= new PaymDocDAO(this);
        tReportDAO= new TReportDAO(this);
        paymentDAO= new PaymentDAO(this);
        birthdayDAO= new BirthdayDAO(this);
        adminBalanceDAO= new AdminBalanceDAO(this);
        adminBDepositDAO= new AdminBDepositDAO(this);
        timeLineClassDAO= new TimeLineClassDAO(this);

        sodao= new SODAO(this);
        tranXDAO= new TranXDAO(this);
        sodao= new SODAO(this);
        messageDAO= new MessageDAO(this);
        loanDAO= new LoanDAO(this);

        codeDAO= new CodeDAO(this);
        acctDAO= new AcctDAO(this);

        if(adminUser !=null){
            cmFirstName = adminUser.getAdminFirstName();
            cmLastName = adminUser.getAdminSurname();
            adminName =cmLastName+","+cmFirstName;
            adminID = adminUser.getAdminID();
        }
        if(userProfile !=null){
            senderProfileID=userProfile.getPID();
        }
        picker=(DatePicker)findViewById(R.id.biz_deposit_date);
        spnToFrom = findViewById(R.id.spnSelectToFromB);
        spnDepositOffice = findViewById(R.id.spinnerBranchOffice);
        spnBusinessDeal = findViewById(R.id.spinnerSelectBizDeal);
        spnSelectBankAcct = findViewById(R.id.spinnerAcctDeal);
        spnDepositTranxType = findViewById(R.id.spnSelectTypeOfTx);

        if(marketBusiness !=null){
            bizDealAccountArrayList=marketBusiness.getBizDealAccountArrayList();
            officeBranchArrayList=marketBusiness.getOfficeBranches();
            businessDealArrayList=marketBusiness.getmBusBizDeals();

        }

        officeAdapter = new OfficeAdapter(this,  officeBranchArrayList);
        spnDepositOffice.setAdapter(officeAdapter);
        spnDepositOffice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedOfficeIndex = spnDepositOffice.getSelectedItemPosition();
                if(officeBranchArrayList !=null){

                    try {
                        officeBranch = officeBranchArrayList.get(selectedOfficeIndex);

                    } catch (NullPointerException e) {
                        System.out.println("Oops!");
                    }

                }

                //officeBranch = officeBranchArrayList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(officeBranch !=null){
            officeBranchID=officeBranch.getOfficeBranchID();
            selectedfficeBranch=officeBranch.getOfficeBranchName();

        }
        spnDepositTranxType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                //selectedTranxTypeIndex = spnDepositTranxType.getSelectedItemPosition();
                selectedTranxType = spnDepositTranxType.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(officeBranch !=null){
            officeBranchID=officeBranch.getOfficeBranchID();
            selectedfficeBranch=officeBranch.getOfficeBranchName();

        }

        BizDealAdapter bizDealAdapter = new BizDealAdapter(this, item_biz_acct, businessDealArrayList);
        spnBusinessDeal.setAdapter(bizDealAdapter);

        spnBusinessDeal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                selectedDealIndex = spnBusinessDeal.getSelectedItemPosition();
                if(businessDealArrayList !=null){

                    try {
                        businessDeal = businessDealArrayList.get(selectedDealIndex);

                    } catch (NullPointerException e) {
                        System.out.println("Oops!");
                    }

                }





                //businessDeal = businessDealArrayList.get(i);
            }
        });
        if(businessDeal !=null){
            bizDealAccountArrayList=businessDeal.getBizDealAccts();

        }


        BizDealAcctAdapter adapter = new BizDealAcctAdapter(this, item_biz_acct, bizDealAccountArrayList);
        spnSelectBankAcct.setAdapter(adapter);

        spnSelectBankAcct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bizDealAcct = bizDealAccountArrayList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(bizDealAcct !=null){
            acctNo=bizDealAcct.getBdAcctID();
        }


        spnToFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedToFrom = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        edtAmounts = findViewById(R.id.adminDepositAmount);


        btnConfirmation = findViewById(R.id.confirmSubmitAdminDe);
        btnConfirmation.setOnClickListener(this::sendReport);

        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate(dateOfReport);
            }
        });
        if(dateOfReport ==null){
            calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd", Locale.getDefault());
            dateSting = dateFormat.format(calendar.getTime());

            dateOfReport=dateSting;

        }
        dateOfReport = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();


        btnConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    amountEntered = Double.parseDouble(edtAmounts.getText().toString().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }
                if(amountEntered<0.00){
                    edtAmounts.setFocusable(true);
                    return;

                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    if(selectedToFrom.isEmpty()){
                        spnToFrom.setFocusable(true);
                        return;
                    }else {
                        adminDepositID = random.nextInt((int) (Math.random() * 1013) + 3511);
                        intAdID = random.nextInt((int) (Math.random() * 1012) + 1511);
                        processReport(adminDepositID,adminUser,senderProfileID, adminName,bizID,dateOfReport,userProfile, selectedBank,adminBankDeposits,selectedTranxType,amountEntered,officeBranchID);


                    }
                }

            }
        });
    }

    private void chooseDate(String dateOfReport) {
        dateOfReport = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();


    }
    private void processReport(int depositID, AdminUser adminUser, int senderProfileID, String adminName, int bizID, String dateOfReport, Profile userProfile, String selectedBank, ArrayList<AdminBankDeposit> adminDeposits, String selectedTranxType, double amountEntered, int officeBranchID){
        Date reportDate=null;
        adminDeposits = null;
        String date=null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateSting = dateFormat.format(calendar.getTime());
        applicationDb = new DBHelper(this);
        currentDate=calendar.getTime();


        String timelineTittle="Admin Deposit Report Alert";
        String timelineTittle2="Your Deposit Report Submission";
        String timelineDetails= adminName +" just Submitted Report";

        String timelineDetails2= null;
        try {
            timelineDetails2 = " You just Submitted Deposit Report @"+""+ Utils.formatDateTime(dateFormat.parse(dateSting));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Location location=null;
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            adminBDepositDAO= new AdminBDepositDAO(this);
            timeLineClassDAO= new TimeLineClassDAO(this);
            sqLiteDatabase = applicationDb.getWritableDatabase();
            try {
                adminDeposits=adminBDepositDAO.getAllAdminBankDeposit();
            } catch (Exception e) {
                System.out.println("Oops!");
            }

        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            reportDate = mdformat.parse(dateOfReport);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateSting = mdformat.format(calendar.getTime());
        adminDepositID = random.nextInt((int) (Math.random() * 1013) + 3511);
        intAdID = random.nextInt((int) (Math.random() * 1012) + 1511);
        try {
            currentDate=dateFormat.parse(dateSting);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        try {
            for (int i = 0; i < adminDeposits.size(); i++) {
                date = adminDeposits.get(i).getDepositDate();
                if (adminDeposits.get(i).getDepositor().equalsIgnoreCase(adminName)&& date.equalsIgnoreCase(dateOfReport)||adminDeposits.get(i).getDepositBank().equalsIgnoreCase(selectedBank)||adminDeposits.get(i).getDepositOfficeBranch().equalsIgnoreCase(this.selectedfficeBranch)) {
                    Toast.makeText(BizCusDepositAct.this, "There is a similar Deposit report" , Toast.LENGTH_LONG).show();
                    return;

                }else {
                    if (adminUser != null) {

                        adminBankDeposit= new AdminBankDeposit(depositID,senderProfileID,adminName,dateOfReport,bizID, officeBranchID,selectedBank, amountEntered);
                        adminBankDeposit.setDepositBank(selectedBank);
                        adminBankDeposit.setDepositID(depositID);
                        adminBankDeposit.setDepositOfficeID(officeBranchID);
                        adminBankDeposit.setDepositDate(dateOfReport);
                        adminBankDeposit.setDepositor(adminName);
                        adminBankDeposit.setDepositBizID(bizID);
                        adminBankDeposit.setDepositType(selectedTranxType);
                        adminBankDeposit.setDepositAcctNo(acctNo);
                        adminBankDeposit.setDepositProfileID(senderProfileID);
                        adminBankDeposit.setDepositAmount(amountEntered);
                        userProfile.addPTimeLine(timelineTittle2,timelineDetails2);
                        //userProfile.addPTellerReport(bizID, officeBranchID, amountEntered, noOfSavings,dateSting);
                        adminUser.addTimeLine(timelineTittle2,timelineDetails2);
                        adminUser.addDepositReport(adminBankDeposit);
                        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                            adminBDepositDAO= new AdminBDepositDAO(this);
                            timeLineClassDAO= new TimeLineClassDAO(this);
                            sqLiteDatabase = applicationDb.getWritableDatabase();
                            adminBDepositDAO.saveNewAdminDeposit(adminBankDeposit);
                            timeLineClassDAO.insertTimeLine(timelineTittle,timelineDetails,dateOfReport,location);


                        }
                        startNotification();
                        Toast.makeText(BizCusDepositAct.this, "Report submission was successful" , Toast.LENGTH_LONG).show();

                    }

                    }

            }

        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }


    }
    private void startNotification() {
        Date reportDate=null;
        String date=null;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateSting = dateFormat.format(calendar.getTime());
        applicationDb = new DBHelper(this);
        try {
            currentDate=dateFormat.parse(dateSting);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("New Admin Deposit Submission Alert")
                        .setContentText(adminName+ ""+"just submitted a new Deposit Report @"+ Utils.formatDateTime(currentDate));

        Intent notificationIntent = new Intent(this, LoginDirAct.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(LoginDirAct.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    public void sendReport(View view) {
        processReport(adminDepositID,adminUser,senderProfileID, adminName,officeBranchID,dateOfReport,userProfile, selectedBank,adminBankDeposits, selectedTranxType, amountEntered, officeBranchID);

    }
}