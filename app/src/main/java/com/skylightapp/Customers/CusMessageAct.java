package com.skylightapp.Customers;

import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.quickblox.auth.session.QBSessionManager;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBIncomingMessagesManager;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.QBSystemMessagesManager;
import com.quickblox.chat.listeners.QBChatDialogMessageListener;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBDialogCustomData;
import com.quickblox.chat.utils.DialogUtils;
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
import com.quickblox.users.model.QBUser;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.ErrorUtils;
import com.skylightapp.Classes.KeyboardUtils;
import com.skylightapp.Classes.Message;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Utils;
import com.skylightapp.Database.AwardDAO;
import com.skylightapp.Database.BirthdayDAO;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.Database.OfficeBranchDAO;
import com.skylightapp.Database.SODAO;
import com.skylightapp.Database.StockTransferDAO;
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.Database.TransactionGrantingDAO;
import com.skylightapp.Interfaces.Consts;
import com.skylightapp.LoginAct;
import com.skylightapp.MarketClasses.DialogsManager;
import com.skylightapp.MarketClasses.MarketBizArrayAdapter;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.MarketInterfaces.ConstsInterface;
import com.skylightapp.Markets.ToastUtils;
import com.skylightapp.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.skylightapp.BuildConfig.QUICKBLOX_ACCT_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_APP_ID;
import static com.skylightapp.BuildConfig.QUICKBLOX_AUTH_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_SECRET_KEY;

public class CusMessageAct extends AppCompatActivity implements TextWatcher, View.OnClickListener{
    private static final String TAG = CusMessageAct.class.getSimpleName();
    public final static int KEY_EXTRA_MESSAGE_ID = 121;

    private DBHelper selector;
    private Profile marketBizProfile,senderProfile;
    private static final String PREF_NAME = "awajima";
    private static final String APPLICATION_ID = QUICKBLOX_APP_ID;   //QUICKBLOX_APP_ID
    private static final String AUTH_KEY = QUICKBLOX_AUTH_KEY;
    private static final String AUTH_SECRET = QUICKBLOX_SECRET_KEY;
    private static final String ACCOUNT_KEY = QUICKBLOX_ACCT_KEY;
    private static final String SERVER_URL = "";
    private AdminUser adminUser;
    private Context context;
    private SharedPreferences userPreferences;
    private PrefManager prefManager;
    private Gson gson,gson1,gson2;
    private String json,json1,json2;
    private  Bundle messageBundle;
    private int marketBizProfileID;
    private int customerID,sendeeProfileID;
    private int SharedPrefAdminID;
    private  String purpose,skylightType;
    private Spinner spnPurpose;
    private  String selectedPurpose, businessName;
    private Customer customer;
    private AppCompatEditText edtInputId;
    private TextView txtBizName,txtNotification;
    private AppCompatEditText inputMessage;
    private Button confirm;
    private SODAO sodao;
    private TranXDAO tranXDAO;
    private MessageDAO messageDAO;
    private CusDAO cusDAO;
    private TimeLineClassDAO timeLineClassDAO;

    private StockTransferDAO stockTransferDAO;
    private OfficeBranchDAO officeBranchDAO;
    private BirthdayDAO birthdayDAO;
    private TransactionGrantingDAO grantingDAO;
    private AwardDAO awardDAO;
    private MarketBusiness marketBusiness;
    private long bizID;
    private int marKetID, bizIDInt, senderProfID;
    private DialogsManager dialogsManager;

    private int skipRecords = 0;
    private boolean openDialogFromSwiping;
    private QBChatDialogMessageListener allDialogsMessagesListener;
    private QBSystemMessagesManager systemMessagesManager;
    private QBIncomingMessagesManager incomingMessagesManager;

    private String time,bizName, officeBranch,outMessage;
    private int recipientId,cusID;
    private QBUser cusQBUser;
    private  Profile cusProf;
    private AppCompatTextView txtName;
    private AppCompatButton btnSend,btnHome;
    private AppCompatSpinner spnP,spnSelectBiz;
    private ProgressDialog progressDialog;
    private ContentLoadingProgressBar progressBar;
    private SharedPreferences.Editor editor;
    private LinearLayoutCompat layoutCompatNew;
    private ArrayList<MarketBusiness> marketBusinessArrayList;
    private Bundle bundle;
    private MarketBizArrayAdapter bizArrayAdapter;
    private int marketBizID, qbUserID;
    private Profile bizProfile,sendeeProfile;
    private QBUser qbUserOfBiz;

    protected ActionBar actionBar;

    private ArrayAdapter<String> adapter;
    private List<String> receivedPushes;
    private AppCompatTextView txtNoMessage;
    String SharedPrefUserPassword,SharedPrefUserMachine,senderFullNames,phoneNo,SharedPrefUserName,adminName,sender;
    private BroadcastReceiver pushBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(ConstsInterface.EXTRA_FCM_MESSAGE);
            if (TextUtils.isEmpty(message)) {
                message = ConstsInterface.EMPTY_FCM_MESSAGE;
            }
            Log.i(TAG, "Receiving event " + ConstsInterface.ACTION_NEW_FCM_EVENT + " with data: " + message);
            retrieveMessage(message);
        }
    };

    public static void start(Context context, String message) {
        Intent intent = new Intent(context, CusMessageAct.class);
        intent.putExtra(ConstsInterface.EXTRA_FCM_MESSAGE, message);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cus_message);
        FirebaseApp.initializeApp(this);
        setTitle("Awajima Messenger");
        actionBar = getSupportActionBar();
        try {

            ViewConfiguration config = ViewConfiguration.get(this);
            @SuppressLint("SoonBlockedPrivateApi") Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ignored) {

        }
        bundle= new Bundle();
        gson= new Gson();
        gson1= new Gson();
        bizProfile= new Profile();
        qbUserOfBiz= new QBUser();
        customer= new Customer();
        sendeeProfile= new Profile();
        marketBusiness= new MarketBusiness();
        marketBusinessArrayList= new ArrayList<>();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        senderProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");

        boolean enable = QBSettings.getInstance().isEnablePushNotification();
        String subtitle = getSubtitleStatus(enable);
        setActionbarSubTitle(subtitle);

        receivedPushes = new ArrayList<>();
        initUI();

        String message = getIntent().getStringExtra(Consts.EXTRA_FCM_MESSAGE);

        if (message != null) {
            retrieveMessage(message);
        }

        registerReceiver();

        inputMessage = findViewById(R.id.edt_Cus_message);
        progressBar = findViewById(R.id.pb_cusTY);
        txtName = findViewById(R.id.txt_name_cu);
        btnSend =  findViewById(R.id.btn_send_cus_message);
        btnHome = findViewById(R.id.btn_go_bk);
        spnP = findViewById(R.id.spn_select_p);
        layoutCompatNew = findViewById(R.id.p_message_select);

        spnSelectBiz = findViewById(R.id.spn_biz);


        bundle=getIntent().getExtras();
        if(bundle !=null){
            senderProfile=bundle.getParcelable("Profile");
            customer=bundle.getParcelable("Customer");

        }
        if(senderProfile !=null){
            marketBusinessArrayList=senderProfile.getProfile_Businesses();
            senderProfID =senderProfile.getPID();
            senderFullNames=senderProfile.getProfileLastName()+","+senderProfile.getProfileFirstName();

        }
        if(customer !=null){
            if(marketBusinessArrayList ==null){
                marketBusinessArrayList=customer.getCusMarketBusinesses();

            }

            cusID =customer.getCusUID();
            senderFullNames=customer.getCusSurname()+","+customer.getCusFirstName();
            sendeeProfile=customer.getCusProfile();

        }
        if(sendeeProfile !=null){
            sendeeProfileID= sendeeProfile.getPID();
        }

        bizArrayAdapter = new MarketBizArrayAdapter(CusMessageAct.this, android.R.layout.simple_spinner_item, marketBusinessArrayList);
        bizArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSelectBiz.setAdapter(bizArrayAdapter);

        spnSelectBiz.setSelection(bizArrayAdapter.getPosition(marketBusiness));

        spnSelectBiz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                marketBusiness = (MarketBusiness) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(marketBusiness !=null){
            marketBizID=marketBusiness.getBizMarketID();
            bizProfile=marketBusiness.getmBusOwner();
        }
        if(bizProfile !=null){
            qbUserOfBiz=bizProfile.getProfQbUser();
        }
        if(qbUserOfBiz !=null){
            try {
                qbUserID =qbUserOfBiz.getId();

            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }

        txtName.setText("Sender"+senderFullNames);

        btnHome.setOnClickListener(this);

        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson1 = new Gson();
        gson2 = new Gson();
        customer=new Customer();
        marketBizProfile =new Profile();
        senderProfile= new Profile();
        adminUser=new AdminUser();
        messageBundle=getIntent().getExtras();
        spnP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //selectedPurpose = (String) parent.getSelectedItem();
                selectedPurpose = spnP.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnSend.setOnClickListener(this);
        timeLineClassDAO= new TimeLineClassDAO(CusMessageAct.this);
        messageDAO= new MessageDAO(CusMessageAct.this);


    }
    @Override
    public void onClick(View view) {
        gson= new Gson();
        gson1= new Gson();
        bizProfile= new Profile();
        qbUserOfBiz= new QBUser();
        customer= new Customer();
        sendeeProfile= new Profile();
        timeLineClassDAO= new TimeLineClassDAO(CusMessageAct.this);
        messageDAO= new MessageDAO(CusMessageAct.this);
        marketBusiness= new MarketBusiness();
        marketBusinessArrayList= new ArrayList<>();
        Bundle loanBundle = new Bundle();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        senderProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        btnSend =  findViewById(R.id.btn_send_cus_message);
        btnHome = findViewById(R.id.btn_go_bk);
        switch (view.getId()) {
            case R.id.btn_send_cus_message:
                String confirmationMessage = "";
                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();
                time= Utils.formatDateTime(currentDate);
                selector=new DBHelper(CusMessageAct.this);

                String message = inputMessage.getText().toString();
                boolean canLeaveMessage = true;
                String timelineDetailsTD =  "A message was sent by" + "" + senderFullNames + ""+"@" + time;
                String tittleT1 = "Message Alert!";
                Location mCurrentLocation=null;
                String timelineDetails = "You sent a message" +""+ "on" + ""+ time;
                String timelineCus = bizName+""+senderFullNames + "sent you a message" + "on" + time;
                sendPushMessage(qbUserID,message);

                Message message2= new Message(KEY_EXTRA_MESSAGE_ID,adminName,sendeeProfileID,customerID,selectedPurpose,message,time);
                int id = sendeeProfile.leaveMessage(message, customerID);
                if(customer !=null){
                    customer.addCusMessages(KEY_EXTRA_MESSAGE_ID,selectedPurpose,message,bizName+""+senderFullNames,time);


                }
                bizIDInt = Math.toIntExact(bizID);
                String senderFrom=senderFullNames+""+"From"+bizName;
                if(messageDAO !=null){
                    try {
                        messageDAO.insertNewMessage(senderProfID,customerID, bizIDInt,marKetID,selectedPurpose,message,senderFrom,senderFullNames, officeBranch, time);

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }


                }


                if(timeLineClassDAO !=null){
                    try {
                        timeLineClassDAO.insertTimeLine(tittleT1, timelineDetailsTD, time, mCurrentLocation);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }


                }
                try {
                    if(senderProfile !=null){
                        senderProfile.addPTimeLine(tittleT1, timelineDetails);
                    }
                    if(customer !=null){
                        customer.addCusTimeLine(tittleT1,timelineCus);
                    }


                }
                catch (Exception e) {
                    e.printStackTrace();
                }



                Toast.makeText(CusMessageAct.this, "Message sent sucessfully" , Toast.LENGTH_LONG).show();
                //notification.setText(confirmationMessage);
                break;

            case R.id.btn_go_bk:
                loanBundle.putParcelable("Profile",senderProfile);
                Intent hIntent = new Intent(CusMessageAct.this, NewCustomerDrawer.class);
                Toast.makeText(CusMessageAct.this, "Old Membership Sub", Toast.LENGTH_SHORT).show();
                hIntent.putExtras(loanBundle);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                hIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                hIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(hIntent);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(pushBroadcastReceiver);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.getItem(0).setEnabled(true);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_messages, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_send_message:
                item.setEnabled(false);
                //sendPushMessage();
                return true;
            case R.id.menu_enable_notification:
                QBSettings.getInstance().setEnablePushNotification(true);
                setActionbarSubTitle(getResources().getString(R.string.subtitle_enabled));
                return true;
            case R.id.menu_disable_notification:
                QBSettings.getInstance().setEnablePushNotification(false);
                setActionbarSubTitle(getResources().getString(R.string.subtitle_disabled));
                return true;
            case R.id.menu_appinfo:
                //AppInfoActivity.start(this);
                return true;
            case R.id.menu_logout:
                unsubscribeFromPushes();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String getSubtitleStatus(boolean enable) {
        return enable ? getResources().getString(R.string.subtitle_enabled)
                : getResources().getString(R.string.subtitle_disabled);
    }

    private void setActionbarSubTitle(String subTitle) {
        if (actionBar != null)
            actionBar.setSubtitle(subTitle);
    }

    private void initUI() {
        progressBar = findViewById(R.id.pb_cusTY);
        inputMessage = findViewById(R.id.edt_Cus_message);
        txtName = findViewById(R.id.txt_name_cu);
        txtNoMessage = findViewById(R.id.empty_mes);

        btnSend =  findViewById(R.id.btn_send_cus_message);
        receivedPushes = new ArrayList<>();

        inputMessage = findViewById(R.id.edt_Cus_message);
        inputMessage.addTextChangedListener(this);

        ListView incomingMessagesListView = findViewById(R.id.list_mes_push);
        adapter = new ArrayAdapter<>(this, R.layout.list_item_message, R.id.item_message, receivedPushes);
        incomingMessagesListView.setAdapter(adapter);
        incomingMessagesListView.setEmptyView(findViewById(R.id.empty_mes));
    }

    private void registerReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(pushBroadcastReceiver,
                new IntentFilter(ConstsInterface.ACTION_NEW_FCM_EVENT));
    }

    private void retrieveMessage(String message) {
        receivedPushes.add(0, message);
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.INVISIBLE);
    }
    private void sendPushMessage(int qbID,String message) {
        QBSettings.getInstance().init(this, APPLICATION_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
        outMessage = message;
        if (!isValidData(outMessage)) {
            try {

                ToastUtils.longToast(R.string.error_field_is_empty);

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            invalidateOptionsMenu();
            return;
        }

        QBEvent qbEvent = new QBEvent();
        qbEvent.setNotificationType(QBNotificationType.PUSH);
        qbEvent.setEnvironment(QBEnvironment.PRODUCTION);
        qbEvent.setMessage(outMessage);

        StringifyArrayList<Integer> userIds = new StringifyArrayList<>();
        userIds.add(qbID);
        userIds.add(QBSessionManager.getInstance().getSessionParameters().getUserId());
        qbEvent.setUserIds(userIds);

        QBPushNotifications.createEvent(qbEvent).performAsync(new QBEntityCallback<QBEvent>() {
            @Override
            public void onSuccess(QBEvent qbEvent, Bundle bundle) {
                progressBar.setVisibility(View.INVISIBLE);
                KeyboardUtils.hideKeyboard(inputMessage);
                inputMessage.setText(null);
                invalidateOptionsMenu();
            }

            @Override
            public void onError(QBResponseException e) {
                View rootView = findViewById(R.id.activity_messages);
                showErrorSnackbar(rootView, R.string.sending_error, e, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendPushMessage(qbID,outMessage);
                    }
                });
                progressBar.setVisibility(View.INVISIBLE);
                KeyboardUtils.hideKeyboard(inputMessage);
                invalidateOptionsMenu();
            }
        });

        progressBar.setVisibility(View.VISIBLE);
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
            try {

                ToastUtils.shortToast(R.string.error_too_long_push);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }
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
            SubscribeService.unSubscribeFromPushes(CusMessageAct.this);
        }
    }


    private boolean isValidData(String message) {
        return !TextUtils.isEmpty(message);
    }
    protected void showProgressDialog(@StringRes Integer messageId) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

            // Disable the back button
            DialogInterface.OnKeyListener keyListener = new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    return keyCode == KeyEvent.KEYCODE_BACK;
                }
            };
            progressDialog.setOnKeyListener(keyListener);
        }
        progressDialog.setMessage(getString(messageId));
        try {
            progressDialog.show();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }



    protected void showErrorSnackbar(View rootLayout, @StringRes int resId, QBResponseException e, View.OnClickListener clickListener) {
        ErrorUtils.showSnackbar(rootLayout, resId, e, R.string.dlg_retry, clickListener);
    }


    private void userLogout() {
        QBSettings.getInstance().init(this, APPLICATION_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
        Log.d(TAG, "SignOut");
        showProgressDialog(R.string.dlg_logout);
        PrefManager prefManager= new PrefManager(this);
        prefManager.logoutUser();
        QBUsers.signOut().performAsync(new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid, Bundle bundle) {
                Log.d(TAG, "SignOut Successful");
                Intent myIntent = new Intent(CusMessageAct.this, LoginAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);
                hideProgressDialog();
                finish();
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d(TAG, "Unable to SignOut: " + e.getMessage());
                hideProgressDialog();
                View rootView = findViewById(R.id.activity_messages);
                showErrorSnackbar(rootView, R.string.error_logout, e, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userLogout();
                    }
                });
            }
        });

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
    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    private void showProgressDialog() {
        progressBar = findViewById(R.id.pb_cusTY);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);//you can cancel it by pressing back button
        progressDialog.setMessage("please wait ...");
        progressBar.show();//displays the progress bar
    }


    /*public void addDialog(QBChatDialog chatDialog) {
        String dialogString = gson.toJson(chatDialog);
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        dialogList.add(chatDialog.getDialogId());
        editor.putString(DIALOG_ID_LIST_NAME, gson.toJson(dialogList));
        editor.putString(chatDialog.getDialogId(), dialogString);
        editor.apply();
    }

    public void deleteDialog(String dialogId) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        dialogList.remove(dialogId);
        editor.putString(DIALOG_ID_LIST_NAME, gson.toJson(dialogList));

        editor.remove(dialogId);
        editor.apply();
    }*/
    public void createNewDialog(int recipientId, int matchValue) {
        QBSettings.getInstance().init(this, APPLICATION_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
        if(cusQBUser !=null){
            recipientId=cusQBUser.getId();
        }
        QBChatDialog dialog = DialogUtils.buildPrivateDialog(recipientId);
        QBDialogCustomData dialogCustomData = new QBDialogCustomData("DialogMatchValue");
        dialogCustomData.putInteger("matchValue", matchValue);
        dialog.setCustomData(dialogCustomData);
        QBRestChatService.createChatDialog(dialog).performAsync(new QBEntityCallback<QBChatDialog>() {
            @Override
            public void onSuccess(QBChatDialog result, Bundle params) {
                Log.d("Chat created", result.toString());
                dialogsManager.sendSystemMessageAboutCreatingDialog(systemMessagesManager, result);
                //getUsersFromDialog(result);

                if (openDialogFromSwiping){
                    openDialogFromSwiping = false;
                    //ChatActCon.startForResult(this, REQUEST_DIALOG_ID_FOR_UPDATE, result);
                }
            }

            @Override
            public void onError(QBResponseException responseException) {
            }
        });
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
        overridePendingTransition(R.anim.base_slide_left_out, R.anim.bounce);

    }
    public void onResume(){
        super.onResume();
        overridePendingTransition(R.anim.base_slide_left_out, R.anim.bounce);
    }


}