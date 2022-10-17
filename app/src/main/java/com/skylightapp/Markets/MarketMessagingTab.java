package com.skylightapp.Markets;

import static com.skylightapp.BuildConfig.QUICKBLOX_ACCT_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_APP_ID;
import static com.skylightapp.BuildConfig.QUICKBLOX_AUTH_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_SECRET_KEY;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBMessageStatusesManager;
import com.quickblox.chat.QBSystemMessagesManager;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.skylightapp.Admins.SendSingleUserMAct;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.CusPacksAct;
import com.skylightapp.Database.AcctDAO;
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
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.LoginDirAct;
import com.skylightapp.MarketClasses.AppConference;
import com.skylightapp.MarketClasses.ChatAdapterConf;
import com.skylightapp.MarketClasses.ChatHelperCon;
import com.skylightapp.MarketClasses.ErrorUtilsCon;
import com.skylightapp.MarketClasses.PermissionsCheckerCon;
import com.skylightapp.MarketClasses.WebRtcSessionManagerCon;
import com.skylightapp.R;
import com.skylightapp.Tellers.MyCashList;
import com.skylightapp.Tellers.MyCustPackTab;
import com.skylightapp.Tellers.TellerDrawerAct;
import com.skylightapp.Tellers.TellerHomeChoices;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import org.jivesoftware.smack.chat.ChatMessageListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class MarketMessagingTab extends TabActivity {
    private static final String TAG = MarketMessagingTab.class.getSimpleName();
    private Account tellerAccount;
    private SODAO sodao;
    private TranXDAO tranXDAO;
    private MessageDAO messageDAO;
    private LoanDAO loanDAO;
    private AcctDAO acctDAO;
    private CodeDAO codeDAO;
    private PaymDocDAO paymDocDAO;

    CusDAO cusDAO;
    PaymentCodeDAO paymentCodeDAO;
    private ProfDAO profileDao;

    private TCashDAO cashDAO;
    private TReportDAO tReportDAO;
    private PaymentDAO paymentDAO;
    private TCashDAO tCashDAO;
    private static final String PREF_NAME = "skylight";
    private SharedPreferences userPreferences;
    private Gson gson,gson1;
    private String json,json1;


    private Profile userProfile;
    FragmentManager fragmentManager;
    private Fragment fragment = null;
    private AppCompatImageView imgTime;
    CircleImageView profilePix;
    int soS,noOfSavings,noOfCustomers,noOfPackages;
    private AppCompatTextView txtWelcomeName, txtTellerBalance,standingOrders, txtTellerID,txtGrpSavings,packages,customers,txtSavings;

    long packageCount;
    int profileId;
    ViewPager viewPager;
    CardView cardViewMyCusPackges,cardViewGrpSavings, cardViewAllCusPacks, cardViewTellerWeb, cardViewOrders, cardViewSupport;

    Bundle bundle;

    public static final int REQUEST_IMAGE = 100;
    public static final String INTENT_IMAGE_PICKER_OPTION = "image_picker_option";

    DBHelper applicationDb;
    Customer customer;
    Account acct;
    double acctBalance,SharedPrefAcctBalance;

    String SharedPrefUserName,SharedPrefUserPassword,SharedPrefBank,names,SharedPrefType,SharedPrefAcctName,SharedPrefBankNo,SharedPrefFirstName,SharedPrefAcctNo,SharedPrefSurName,SharedPrefEmail,SharedPrefPhone,SharedPrefDOB,SharedPrefRole,SharedPrefGender,SharedPrefJoinedDate,SharedPrefAddress,SharedPrefUserMachine,SharedPrefState,SharedPrefOffice;
    SharedPreferences sharedPreferences;

    int SharedPrefCusID;
    int SharedPrefProfileID;

    int customerId;

    private Date dateToday;
    private com.melnykov.fab.FloatingActionButton floatingActionButton;
    private RecyclerView mesRecyler;
    private static final String APPLICATION_ID = QUICKBLOX_APP_ID;   //QUICKBLOX_APP_ID
    private static final String AUTH_KEY = QUICKBLOX_AUTH_KEY;
    private static final String AUTH_SECRET = QUICKBLOX_SECRET_KEY;
    private static final String ACCOUNT_KEY = QUICKBLOX_ACCT_KEY;
    private static final String SERVER_URL = "";
    public static final String USER_DEFAULT_PASSWORD = "quickblox";
    public static final String EXTRA_DIALOG_ID = "dialogId";
    public static final String EXTRA_IS_NEW_DIALOG = "isNewDialog";
    public static final String EXTRA_IS_OPEN_FROM_CALL = "isOpenFromCall";
    private QBMessageStatusesManager qbMessageStatusesManager;
    private QBSystemMessagesManager systemMessagesManager;
    private List<QBChatMessage> messagesList;
    private QBChatDialog qbChatDialog;
    private ArrayList<QBChatMessage> unShownMessages;
    private PermissionsCheckerCon checker;
    private WebRtcSessionManagerCon sessionManager;
    private int skipPagination = 0;
    private Boolean checkAdapterInit = false;
    private boolean isOpenFromCall = false;
    private String streamID;
    private ServiceConnection callServiceConnection;
    private ChatAdapterConf chatAdapter;
    private ProgressDialog progressDialog = null;
    private Snackbar snackbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_market_messaging_tab);
        QBSettings.getInstance().init(this, APPLICATION_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
        TabHost tabhost = findViewById(android.R.id.tabhost);
        TabWidget tabs = findViewById(android.R.id.tabs);
        mesRecyler = findViewById(R.id.messagingRecylerV);
        messagesList = new ArrayList<>();
        if(messagesList.size()>0){
            mesRecyler.setVisibility(View.VISIBLE);

        }
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(MarketMessagingTab.this, LinearLayoutManager.HORIZONTAL, false);
        mesRecyler.setLayoutManager(layoutManager);
        layoutManager.setStackFromEnd(true);
        mesRecyler.setLayoutManager(layoutManager);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mesRecyler);
        mesRecyler.setClickable(true);


        chatAdapter = new ChatAdapterConf(MarketMessagingTab.this, qbChatDialog, messagesList);
        //chatAdapter.setPaginationHistoryListener(new PaginationListener());
        mesRecyler.addItemDecoration(
                new StickyRecyclerHeadersDecoration(chatAdapter));

        mesRecyler.setAdapter(chatAdapter);
        isOpenFromCall = getIntent().getBooleanExtra(EXTRA_IS_OPEN_FROM_CALL, false);
        String dialogID = getIntent().getStringExtra(EXTRA_DIALOG_ID);
        // setting up the tab host
        tabhost.setup(getLocalActivityManager());
        Resources resources = getResources();

        Intent intentSignUp = new Intent().setClass(this, SendSingleUserMAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecSignUp = tabhost
                .newTabSpec("Single")
                .setIndicator("", resources.getDrawable(R.drawable.user_red1))
                .setContent(intentSignUp);
        Intent intentSignIn = new Intent().setClass(this, SendMultiSendeeMAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecLogin = tabhost
                .newTabSpec("Multiple")
                .setIndicator("", resources.getDrawable(R.drawable.users_fg))
                .setContent(intentSignIn);


        tabhost.addTab(tabSpecSignUp);
        tabhost.addTab(tabSpecLogin);

        tabhost.setCurrentTab(0);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                packageTab();
            }
        });
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Toast.makeText(MarketMessagingTab.this, tabId, Toast.LENGTH_SHORT).show();
            }
        });

        dateToday= new Date();
        gson = new Gson();
        gson1 = new Gson();
        userProfile= new Profile();
        tellerAccount= new Account();
        cusDAO= new CusDAO(this);
        paymentCodeDAO= new PaymentCodeDAO(this);
        profileDao= new ProfDAO(this);
        cashDAO= new TCashDAO(this);
        paymDocDAO= new PaymDocDAO(this);
        tReportDAO= new TReportDAO(this);
        sodao= new SODAO(this);
        paymentDAO= new PaymentDAO(this);
        tCashDAO= new TCashDAO(this);

        tranXDAO= new TranXDAO(this);
        sodao= new SODAO(this);
        messageDAO= new MessageDAO(this);
        loanDAO= new LoanDAO(this);

        codeDAO= new CodeDAO(this);
        acctDAO= new AcctDAO(this);
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=sharedPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=sharedPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=sharedPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUserMachine=sharedPreferences.getString("machine", "");
        SharedPrefProfileID=sharedPreferences.getInt("PROFILE_ID", 0);
        SharedPrefSurName=sharedPreferences.getString("PROFILE_SURNAME", "");
        SharedPrefFirstName=sharedPreferences.getString("PROFILE_FIRSTNAME", "");
        SharedPrefEmail=sharedPreferences.getString("PROFILE_EMAIL", "");
        SharedPrefPhone=sharedPreferences.getString("PROFILE_PHONE", "");
        SharedPrefAddress=sharedPreferences.getString("PROFILE_ADDRESS", "");
        SharedPrefDOB=sharedPreferences.getString("PROFILE_DOB", "");
        SharedPrefRole=sharedPreferences.getString("PROFILE_ROLE", "");
        SharedPrefGender=sharedPreferences.getString("PROFILE_GENDER", "");
        SharedPrefJoinedDate=sharedPreferences.getString("PROFILE_DATE_JOINED", "");
        SharedPrefOffice=sharedPreferences.getString("PROFILE_OFFICE", "");
        SharedPrefState=sharedPreferences.getString("PROFILE_STATE", "");
        customerId=sharedPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefAcctNo=sharedPreferences.getString("ACCOUNT_NO", "");
        SharedPrefBankNo=sharedPreferences.getString("BANK_ACCT_NO", "");
        SharedPrefAcctName=sharedPreferences.getString("ACCOUNT_NAME", "");
        SharedPrefType=sharedPreferences.getString("ACCOUNT_TYPE", "");
        SharedPrefBank=sharedPreferences.getString("ACCOUNT_BANK", "");
        json1 = sharedPreferences.getString("LastAccountUsed", "");
        acct = gson1.fromJson(json1, Account.class);
        json = sharedPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        applicationDb = new DBHelper(this);
        if(userProfile!=null){
            profileId=userProfile.getPID();
            names=userProfile.getProfileLastName()+","+userProfile.getProfileFirstName();
            tellerAccount=userProfile.getProfileAccount();
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        //calendar.add(Calendar.DAY_OF_YEAR, 31);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = calendar.getTime();
        String todayDate = sdf.format(newDate);
        try {
            dateToday=sdf.parse(todayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private class PaginationListener implements ChatAdapterConf.PaginationHistoryListener {
        @Override
        public void onNextPage() {
            Log.w(TAG, "Download More");
            loadChatHistory();
        }
    }
    protected ChatHelperCon getChatHelper() {
        return ((AppConference) getApplicationContext()).getChatHelper();
    }

    private void loadChatHistory() {
        getChatHelper().loadChatHistory(qbChatDialog, skipPagination, new QBEntityCallback<ArrayList<QBChatMessage>>() {
            @Override
            public void onSuccess(ArrayList<QBChatMessage> messages, Bundle args) {
                // The newest messages should be in the end of list,
                // so we need to reverse list to show messages in the right order
                Collections.reverse(messages);
                if (checkAdapterInit) {
                    chatAdapter.addMessages(messages);
                } else {
                    checkAdapterInit = true;
                    chatAdapter.setMessages(messages);
                    //addDelayedMessagesToAdapter();
                }
                if (skipPagination == 0) {
                    //scrollMessageListDown();
                }
                skipPagination += ChatHelperCon.CHAT_HISTORY_ITEMS_PER_PAGE;
                //progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d(TAG, "Loading Dialog History Error: " + e.getMessage());
                //progressBar.setVisibility(View.GONE);
                showErrorSnackbar(R.string.load_chat_history_error, e, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadChatHistory();
                    }
                });
            }
        });
    }
    protected void showErrorSnackbar(@StringRes int resId, Exception e, View.OnClickListener clickListener) {
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        if (rootView != null) {
            ErrorUtilsCon.showSnackbar(getApplicationContext(), rootView, resId, e,
                    R.string.dlg_retry, clickListener);
        }
    }

    protected void showInfoSnackbar(String message, @StringRes int actionLabel, View.OnClickListener clickListener) {
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        if (rootView != null) {
            snackbar = ErrorUtilsCon.showInfoSnackbar(getApplicationContext(), rootView, message, actionLabel, clickListener);
        }
    }

    protected void hideSnackbar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }

    protected void showProgressDialog(@StringRes Integer messageId) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

            // Disable the back button
            DialogInterface.OnKeyListener keyListener = (dialog, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK;
            progressDialog.setOnKeyListener(keyListener);
        }
        progressDialog.setMessage(getString(messageId));
        try {
            progressDialog.show();
        } catch (Exception e) {
            if (e.getMessage() != null) {
                Log.d(TAG, e.getMessage());
            }
        }
    }

    protected void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
                progressDialog = null;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    protected boolean isProgressDialogShowing() {
        if (progressDialog != null) {
            return progressDialog.isShowing();
        } else {
            return false;
        }
    }

    public void packageTab() {
        Intent intent = new Intent(this, LoginDirAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("User", (Parcelable) userProfile);
        intent.putExtra("Machine", SharedPrefUserMachine);
        intent.putExtra("ProfileID", SharedPrefProfileID);
        intent.putExtra(PROFILE_ID, SharedPrefProfileID);
        startActivity(intent);




    }
}