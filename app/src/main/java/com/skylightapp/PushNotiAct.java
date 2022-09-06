package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.quickblox.auth.session.QBSessionManager;
import com.quickblox.auth.session.QBSettings;
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
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.KeyboardUtils;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SharedPrefsHelperPush;
import com.skylightapp.Classes.ToastUtils;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Interfaces.Consts;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

public class PushNotiAct extends BasicPushAct implements TextWatcher {
    private final String TAG = getClass().getSimpleName();

    private EditText outgoingMessageEditText;
    private ProgressBar progressBar;
    private ArrayAdapter<String> adapter;

    private List<String> receivedPushes;
    private AppCompatButton btnSendPush;
    private static final String PREF_NAME = "skylight";
    SharedPreferences userPreferences;
    DBHelper dbHelper;
    Profile managerProfile;
    int customerID;
    int profileID1;
    SQLiteDatabase sqLiteDatabase;
    Gson gson, gson1;
    String json, json1, nIN;
    Profile userProfile, customerProfile, lastProfileUsed;
    Customer customer;
    String joinedDate, customerName;
    private QBUser qbUser;

    private BroadcastReceiver pushBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(Consts.EXTRA_FCM_MESSAGE);
            if (TextUtils.isEmpty(message)) {
                message = Consts.EMPTY_FCM_MESSAGE;
            }
            Log.i(TAG, "Receiving event " + Consts.ACTION_NEW_FCM_EVENT + " with data: " + message);
            retrieveMessage(message);
        }
    };

    public static void start(Context context, String message) {
        Intent intent = new Intent(context, PushNotiAct.class);
        intent.putExtra(Consts.EXTRA_FCM_MESSAGE, message);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_push_noti);
        dbHelper = new DBHelper(this);
        customerProfile = new Profile();
        gson1 = new Gson();
        gson = new Gson();
        qbUser= new QBUser();
        customer = new Customer();
        userProfile = new Profile();
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
        menuInflater.inflate(R.menu.push_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_send_message:
                item.setEnabled(false);
                sendPushMessage();
                return true;
            case R.id.menu_enable_notification:
                QBSettings.getInstance().setEnablePushNotification(true);
                setActionbarSubTitle(getResources().getString(R.string.subtitle_enabled));
                return true;
            case R.id.menu_disable_notification:
                QBSettings.getInstance().setEnablePushNotification(false);
                setActionbarSubTitle(getResources().getString(R.string.subtitle_disabled));
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
        progressBar = findViewById(R.id.progress_bar);
        outgoingMessageEditText = findViewById(R.id.edit_message_out);
        btnSendPush = findViewById(R.id.btn_Push_Notification);
        outgoingMessageEditText.addTextChangedListener(this);

        ListView incomingMessagesListView = findViewById(R.id.list_messages);
        adapter = new ArrayAdapter<>(this, R.layout.list_item_message, R.id.item_message, receivedPushes);
        incomingMessagesListView.setAdapter(adapter);
        incomingMessagesListView.setEmptyView(findViewById(R.id.text_empty_messages));
        btnSendPush.setOnClickListener(this::doPushNoti);
        btnSendPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPushMessage();

            }
        });
    }

    private void registerReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(pushBroadcastReceiver,
                new IntentFilter(Consts.ACTION_NEW_FCM_EVENT));
    }

    private void retrieveMessage(String message) {
        receivedPushes.add(0, message);
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void sendPushMessage() {
        String outMessage = outgoingMessageEditText.getText().toString().trim();
        if (!isValidData(outMessage)) {
            ToastUtils.longToast(R.string.error_field_is_empty);
            invalidateOptionsMenu();
            return;
        }

        QBEvent qbEvent = new QBEvent();
        qbEvent.setNotificationType(QBNotificationType.PUSH);
        qbEvent.setEnvironment(QBEnvironment.PRODUCTION);
        qbEvent.setMessage(outMessage);

        StringifyArrayList<Integer> userIds = new StringifyArrayList<>();
        userIds.add(QBSessionManager.getInstance().getSessionParameters().getUserId());
        qbEvent.setUserIds(userIds);

        QBPushNotifications.createEvent(qbEvent).performAsync(new QBEntityCallback<QBEvent>() {
            @Override
            public void onSuccess(QBEvent qbEvent, Bundle bundle) {
                progressBar.setVisibility(View.INVISIBLE);
                KeyboardUtils.hideKeyboard(outgoingMessageEditText);
                outgoingMessageEditText.setText(null);
                invalidateOptionsMenu();
            }

            @Override
            public void onError(QBResponseException e) {
                View rootView = findViewById(R.id.activity_messages);
                showErrorSnackbar(rootView, R.string.sending_error, e, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendPushMessage();
                    }
                });
                progressBar.setVisibility(View.INVISIBLE);
                KeyboardUtils.hideKeyboard(outgoingMessageEditText);
                invalidateOptionsMenu();
            }
        });

        progressBar.setVisibility(View.VISIBLE);
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
            SubscribeService.unSubscribeFromPushes(PushNotiAct.this);
        }
    }

    private void userLogout() {
        Log.d(TAG, "SignOut");
        showProgressDialog(R.string.dlg_logout);

        QBUsers.signOut().performAsync(new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid, Bundle bundle) {
                Log.d(TAG, "SignOut Successful");
                SharedPrefsHelperPush.getInstance().removeQbUser();
                Intent chat = new Intent(PushNotiAct.this, LoginActivity.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                chat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(chat);
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

    public void doPushNoti(View view) {
    }
}