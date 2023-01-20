package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.quickblox.auth.session.QBSessionManager;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.messages.QBPushNotifications;
import com.quickblox.messages.model.QBEnvironment;
import com.quickblox.messages.model.QBEvent;
import com.quickblox.messages.model.QBNotificationType;
import com.quickblox.messages.services.QBPushManager;
import com.quickblox.messages.services.SubscribeService;
import com.quickblox.users.QBUsers;
import com.skylightapp.Adapters.AccountRecylerAdap;
import com.skylightapp.Adapters.OfficeAdapter;
import com.skylightapp.Admins.AdminBankDeposit;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.KeyboardUtils;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.UserSuperAdmin;
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
import com.skylightapp.Interfaces.Consts;
import com.skylightapp.LoginAct;
import com.skylightapp.LoginDirAct;
import com.skylightapp.MarketClasses.AttachmentPreviewAdapter;
import com.skylightapp.MarketClasses.BizDealAccount;
import com.skylightapp.MarketClasses.BusinessDeal;
import com.skylightapp.MarketClasses.ChatAdapter;
import com.skylightapp.MarketClasses.MarketAdmin;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.MarketClasses.StrokedTextView;
import com.skylightapp.MarketClasses.UserProfileInfo;
import com.skylightapp.R;

import org.jivesoftware.smack.ConnectionListener;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import static com.skylightapp.BuildConfig.QUICKBLOX_ACCT_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_APP_ID;
import static com.skylightapp.BuildConfig.QUICKBLOX_AUTH_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_SECRET_KEY;

public class SingleCusMessagingAct extends AppCompatActivity implements TextWatcher {
    Bundle bundle,userBundle;
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
    int cusID;
    int userProfID;
    String cusPhoneNo,firstName,surName,cusEmail,customerFirstName,cusSurname,cusLoc,profilePhone,email,location;
    Button btnSend;
    AppCompatSpinner spnSendAdmin,spnSendTeller,spnSendCustomer;
    AppCompatTextView texName,txtCusID, txtProfileID,txtPhoneNo;
    LinearLayoutCompat layoutCus,layoutTeller,layoutAdmin,layoutOffice;
    ArrayList<Customer> customers;
    private ArrayAdapter<Customer> customersAdapter;
    ArrayList<String>profiles;
    SharedPreferences userPref;
    PreferenceManager preferenceManager;
    boolean isInvisible;
    String messageDetails,name;
    AppCompatEditText editMessage,editTittle;
    com.twilio.rest.api.v2010.account.Message message;
    private static boolean isPersistenceEnabled = false;
    int selectedCustomerIndex;
    SharedPreferences sharedpreferences;
    Gson gson0,gson2;
    String json0,tittle;
    int messageID;
    int profileID;
    String machine,userName,officeBranch,sender,selectedOffice;

    UserSuperAdmin superAdmin;
    CustomerManager customerManager;
    private ArrayAdapter<Customer> customerArrayAdapterN;
    private ArrayList<Customer> customersN;
    private ArrayList<AdminUser> adminUsers;
    private ArrayList<CustomerManager> customerManagerArrayList;
    private ArrayAdapter<CustomerManager> customerManagerArrayAdapter;
    private ArrayAdapter<AdminUser> adminUserArrayAdapter;
    private ArrayAdapter<UserSuperAdmin> userSuperAdminArrayAdapter;
    AppCompatSpinner spnOfficeBranch;
    private StringifyArrayList<Integer> userIds;
    private static final String APPLICATION_ID = QUICKBLOX_APP_ID;   //QUICKBLOX_APP_ID
    private static final String AUTH_KEY = QUICKBLOX_AUTH_KEY;
    private static final String AUTH_SECRET = QUICKBLOX_SECRET_KEY;
    private static final String ACCOUNT_KEY = QUICKBLOX_ACCT_KEY;
    private static final String SERVER_URL = "";
    public static final String USER_DEFAULT_PASSWORD = "quickblox";
    Gson gson3,gson4;
    String  json2,json3,json4;
    private Profile lastProfileUsed;
    int PERMISSION_ALL = 1;
    private OfficeAdapter officeAdapter;
    private AccountRecylerAdap accountRecylerAdap;
    private Spinner spnSelectBankAcct,spnDepositTranxType;
    private ArrayList<OfficeBranch> officeBranchArrayList;
    private ArrayList<Account> accountArrayList;
    private ArrayList<BusinessDeal> businessDealArrayList;
    private ArrayList<BizDealAccount> bizDealAccountArrayList;
    private  String acctType;
    private String acctPaymentIntent;
    private Bundle customerBundle;
    private Account account;
    String SharedPrefUserPassword;
    String dateSting;
    String selectedBank;
    String stringNoOfSavings;
    String dateOfReport;
    String cmFirstName;
    String cmLastName;
    String adminOfficer;
    String SharedPrefUserMachine;
    String phoneNo;
    String SharedPrefUserName;
    String SharedPrefProfileID,outMessage;
    int selectedOfficeID;
    String adminName,selectedfficeBranch,selectedTranxType,selectedToFrom;
    int customerID,acctNo,selectedDealIndex,qbUserID;
    private BusinessDeal businessDeal;
    private BizDealAccount bizDealAcct;
    private MarketAdmin marketAdmin;
    private AdminUser.ADMIN_TYPE admin_type;
    private MarketBusiness marketBusiness;
    private UserProfileInfo userProfileInfo;
    private BroadcastReceiver pushBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(Consts.EXTRA_FCM_MESSAGE);
            if (TextUtils.isEmpty(message)) {
                message = Consts.EMPTY_FCM_MESSAGE;
            }
            Log.i(TAG, "Receiving event " + Consts.ACTION_NEW_FCM_EVENT + " with data: " + message);
            //retrieveMessage(message);
        }
    };

    public static void start(Context context, String message) {
        Intent intent = new Intent(context, SingleCusMessagingAct.class);
        intent.putExtra(Consts.EXTRA_FCM_MESSAGE, message);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_single_cust_messaging);
        setTitle("Customer Messaging");
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
        userProfileInfo = gson1.fromJson(json2, UserProfileInfo.class);
        json3 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson3.fromJson(json3, MarketBusiness.class);

        json4 = userPreferences.getString("LastMarketAdminUsed", "");
        marketAdmin = gson1.fromJson(json4, MarketAdmin.class);
        bizNameBundle=getIntent().getExtras();
        if(bizNameBundle !=null){
            bizID=bizNameBundle.getInt("MARKET_BIZ_ID");
            marketID=bizNameBundle.getInt("MARKET_ID");
        }
        if(customerBundle !=null){
            customerID=customerBundle.getInt("CustomerID");
            qbUserID=customerBundle.getInt("CusQBUserID");

        }
        texName = findViewById(R.id.push_Cus);
        txtCusID = findViewById(R.id.push_ID_Cus);
        editMessage = findViewById(R.id.push_message);
        editTittle = findViewById(R.id.pust_tittle);
        btnSend = findViewById(R.id.sendPushMesssage);
        txtCusID.setText("Customer ID:"+customerID);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tittle=editTittle.getText().toString();
                outMessage = editMessage.getText().toString().trim();
                doMessageSend(tittle,outMessage,customerID,qbUserID);
                sendPushMessage(tittle,outMessage,customerID,qbUserID);

            }
        });

    }
    public  void doMessageSend(String tittle,String outMessage,int customerID,int qbUserID){
        customerBundle=getIntent().getExtras();
        calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        sharedpreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        machine = sharedpreferences.getString("Machine", "");
        gson = new Gson();
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        MessageDAO messageDAO = new MessageDAO(this);
        if(userProfile !=null){
            userName=userProfile.getProfileLastName()+","+userProfile.getProfileFirstName();


        }
        if(userProfile !=null){
            bizID=userProfile.getProfileBusinessID();
            marketID=userProfile.getProfileMarketID();
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String meassageDate = mdformat.format(calendar.getTime());
        messageID = random.nextInt((int) (Math.random() * 129) + 11);
        try {
            messageDAO.insertMessage(profileID,customerID, bizID,marketID, tittle,messageDetails,"Awajima App",sendee,officeBranch,meassageDate);
            //messageDAO.insertMessage(userProfID, iD,messageID, bizID, messageDetails,sender,sendee,officeBranch,meassageDate, bizID);


        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        Intent userIntent = new Intent(SingleCusMessagingAct.this, LoginDirAct.class);
        userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        userIntent.putExtras(userBundle);
        startActivity(userIntent);

    }
    private void registerReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(pushBroadcastReceiver,
                new IntentFilter(Consts.ACTION_NEW_FCM_EVENT));
    }
    private void sendPushMessage(String tittle,String outMessage,int customerID,int qbUserID) {
        editMessage = findViewById(R.id.messageUser);

        if (!isValidData(outMessage)) {
            Toast.makeText(SingleCusMessagingAct.this, R.string.error_field_is_empty , Toast.LENGTH_LONG).show();
            invalidateOptionsMenu();
            return;
        }

        QBEvent qbEvent = new QBEvent();
        qbEvent.setNotificationType(QBNotificationType.PUSH);
        qbEvent.setEnvironment(QBEnvironment.PRODUCTION);
        qbEvent.setMessage(outMessage);
        qbEvent.setName(tittle);

        //userIds = new StringifyArrayList<>();
        userIds.add(QBSessionManager.getInstance().getSessionParameters().getUserId());
        //qbEvent.setUserIds(userIds);
        qbEvent.setUserId(qbUserID);

        QBPushNotifications.createEvent(qbEvent).performAsync(new QBEntityCallback<QBEvent>() {
            @Override
            public void onSuccess(QBEvent qbEvent, Bundle bundle) {
                //progressBar.setVisibility(View.INVISIBLE);
                KeyboardUtils.hideKeyboard(editMessage);
                editMessage.setText(null);
                invalidateOptionsMenu();
            }

            @Override
            public void onError(QBResponseException e) {
                View rootView = findViewById(R.id.activity_messages);
                Toast.makeText(SingleCusMessagingAct.this,R.string.sending_error , Toast.LENGTH_LONG).show();
                /*showErrorSnackbar(rootView, R.string.sending_error, e, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendPushMessage();
                    }
                });*/
                //progressBar.setVisibility(View.INVISIBLE);
                KeyboardUtils.hideKeyboard(editMessage);
                invalidateOptionsMenu();
            }
        });

        //progressBar.setVisibility(View.VISIBLE);
    }
    private void unsubscribeFromPushes() {
        if (QBPushManager.getInstance().isSubscribedToPushes()) {
            QBPushManager.getInstance().addListener(new QBPushManager.QBSubscribeListener() {
                @Override
                public void onSubscriptionCreated() {
                    // empty
                }

                @Override
                public void onSubscriptionError(Exception e, int i) {
                    // empty
                }

                @Override
                public void onSubscriptionDeleted(boolean success) {
                    Log.d(TAG, "Subscription Deleted -> Success: " + success);
                    QBPushManager.getInstance().removeListener(this);
                    userLogout();
                }
            });
            SubscribeService.unSubscribeFromPushes(SingleCusMessagingAct.this);
        }
    }
    private void userLogout() {
        Log.d(TAG, "SignOut");
        //showProgressDialog(R.string.dlg_logout);

        QBUsers.signOut().performAsync(new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid, Bundle bundle) {
                Log.d(TAG, "SignOut Successful");
                //SharedPrefsHelper.getInstance().removeQbUser();
                Intent myIntent = new Intent(SingleCusMessagingAct.this, LoginAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);
                //hideProgressDialog();
                finish();
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d(TAG, "Unable to SignOut: " + e.getMessage());
                //hideProgressDialog();
                View rootView = findViewById(R.id.activity_messages);

            }
        });
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();

    }

    private boolean isValidData(String message) {
        return !TextUtils.isEmpty(message);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // empty
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // empty
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() >= getResources().getInteger(R.integer.push_max_length)) {
            ToastUtils.shortToast(R.string.error_too_long_push);
        }
    }
}