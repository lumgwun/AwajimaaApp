package com.skylightapp.Markets;

import static com.skylightapp.BuildConfig.QUICKBLOX_ACCT_KEY;

import static com.skylightapp.BuildConfig.QUICKBLOX_AUTH_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_SECRET_KEY;
import static com.skylightapp.BuildConfig.APPLICATION_ID;


import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.QBSystemMessagesManager;
import com.quickblox.chat.exception.QBChatException;
import com.quickblox.chat.listeners.QBSystemMessageListener;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.model.QBUser;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MarketClasses.BusinessDeal;
import com.skylightapp.MarketClasses.ChatHelper;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.MarketClasses.QbDialogHolderE;
import com.skylightapp.MarketClasses.ToastUtilsCon;
import com.skylightapp.MarketClasses.UsersAdapterCon;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

public class ChatInfoActCon extends BaseActCon {
    private static final String TAG = ChatInfoActCon.class.getSimpleName();
    private static final String EXTRA_DIALOG = "extra_dialog";
    private long bizID;
    private Bundle bundle;
    private BusinessDeal businessDeal;
    private String bizDealTittle;
    private static final String PREF_NAME = "awajima";

    private Profile bizProfile;
    private MarketBusiness marketBusiness;
    private SharedPreferences userPreferences;
    private PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1;
    private  Bundle messageBundle;

    private QBChatDialog qbDialog;
    private UsersAdapterCon userAdapter;
    private final SystemMessagesListener systemMessagesListener = new SystemMessagesListener();
    private final QBSystemMessagesManager systemMessagesManager = QBChatService.getInstance().getSystemMessagesManager();

    public static void start(Context context, String dialogID) {
        Intent intent = new Intent(context, ChatInfoActCon.class);
        intent.putExtra(EXTRA_DIALOG, dialogID);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_chat_info_act_con);
        FirebaseApp.initializeApp(this);
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        bundle= new Bundle();
        gson= new Gson();
        gson1= new Gson();
        marketBusiness= new MarketBusiness();
        bizProfile= new Profile();
        businessDeal= new BusinessDeal();
        bundle=getIntent().getExtras();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json1 = userPreferences.getString("LastProfileUsed", "");
        bizProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson1.fromJson(json1, MarketBusiness.class);
        if(marketBusiness !=null){
            bizID =marketBusiness.getBusinessID();
        }
        
        String dialogID = getIntent().getStringExtra(EXTRA_DIALOG);
        qbDialog = getQBDialogsHolder().getDialogById(dialogID);
        setTitleSubtitle();

        ListView usersListView = findViewById(R.id.list_chat_info_users);
        List<Integer> userIds = qbDialog.getOccupants();
        List<QBUser> users = getQBUsersHolder().getUsersByIds(userIds);
        userAdapter = new UsersAdapterCon(ChatInfoActCon.this, users);
        usersListView = findViewById(R.id.list_chat_info_users);
        usersListView.setAdapter(userAdapter);
        getDialog();
    }

    private void setTitleSubtitle() {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(qbDialog.getName());

            String subtitle;
            if (qbDialog.getOccupants().size() != 1) {
                subtitle = getString(R.string.chat_info_subtitle_plural, String.valueOf(qbDialog.getOccupants().size()));
            } else {
                subtitle = getString(R.string.chat_info_subtitle_singular, "1");
            }

            getSupportActionBar().setSubtitle(subtitle);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        systemMessagesManager.removeSystemMessageListener(systemMessagesListener);
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onResumeFinished() {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        systemMessagesManager.addSystemMessageListener(systemMessagesListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_chat_info_action_add_people) {
            updateDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void getDialog() {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        String dialogID = qbDialog.getDialogId();
        QBRestChatService.getChatDialogById(dialogID).performAsync(new QBEntityCallback<QBChatDialog>() {
            @Override
            public void onSuccess(QBChatDialog qbChatDialog, Bundle bundle) {
                qbDialog = qbChatDialog;
                setTitleSubtitle();
                buildUserList();
            }

            @Override
            public void onError(QBResponseException e) {
                ToastUtilsCon.shortToast(ChatInfoActCon.this, e.getMessage());
                finish();
            }
        });
    }

    private void buildUserList() {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        List<Integer> userIds = qbDialog.getOccupants();
        if (getQBUsersHolder().hasAllUsers(userIds)) {
            List<QBUser> users = getQBUsersHolder().getUsersByIds(userIds);
            userAdapter.clearList();
            userAdapter.addUsers(users);
        } else {
            getChatHelper().getUsersFromDialog(qbDialog, new QBEntityCallback<ArrayList<QBUser>>() {
                @Override
                public void onSuccess(ArrayList<QBUser> qbUsers, Bundle bundle) {
                    if (qbUsers != null) {
                        getQBUsersHolder().putUsers(qbUsers);
                        userAdapter.addUsers(qbUsers);
                    }
                }

                @Override
                public void onError(QBResponseException e) {
                    if (e.getMessage() != null) {
                        Log.d(TAG, e.getMessage());
                    }
                }
            });
        }
    }

    private void updateDialog() {
        showProgressDialog(R.string.dlg_loading);
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        Log.d(TAG, "Starting Dialog Update");
        QBRestChatService.getChatDialogById(qbDialog.getDialogId()).performAsync(new QBEntityCallback<QBChatDialog>() {
            @Override
            public void onSuccess(QBChatDialog updatedChatDialog, Bundle bundle) {
                Log.d(TAG, "Update Dialog Successful: " + updatedChatDialog.getDialogId());
                qbDialog = updatedChatDialog;
                hideProgressDialog();
                SelectUsersActCon.startForResult(ChatInfoActCon.this, ChatActCon.REQUEST_CODE_SELECT_PEOPLE, updatedChatDialog);
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d(TAG, "Dialog Loading Error: " + e.getMessage());
                hideProgressDialog();
                showErrorSnackbar(R.string.select_users_get_dialog_error, e, null);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult with resultCode: $resultCode requestCode: $requestCode");
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ChatActCon.REQUEST_CODE_SELECT_PEOPLE && data != null) {
                showProgressDialog(R.string.chat_info_updating);
                final List<QBUser> selectedUsers = (ArrayList<QBUser>) data.getSerializableExtra(SelectUsersActCon.EXTRA_QB_USERS);
                List<Integer> existingOccupants = qbDialog.getOccupants();
                final List<Integer> newUserIds = new ArrayList<>();

                if (selectedUsers != null) {
                    for (QBUser user : selectedUsers) {
                        if (!existingOccupants.contains(user.getId())) {
                            newUserIds.add(user.getId());
                        }
                    }
                }

                QBRestChatService.getChatDialogById(qbDialog.getDialogId()).performAsync(new QBEntityCallback<QBChatDialog>() {
                    @Override
                    public void onSuccess(QBChatDialog qbChatDialog, Bundle bundle) {
                        qbDialog = qbChatDialog;
                        getDialogsManager().sendMessageAddedUsers(qbChatDialog, newUserIds);
                        getDialogsManager().sendSystemMessageAddedUser(systemMessagesManager, qbChatDialog, newUserIds);
                        updateDialog(selectedUsers);
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        hideProgressDialog();
                        showErrorSnackbar(R.string.update_dialog_error, e, null);
                    }
                });
            }
        }
    }

    private void updateDialog(final List<QBUser> selectedUsers) {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        QBUser currentUser = getChatHelper().getCurrentUser();
        getChatHelper().updateDialogUsers((QBUser) currentUser, qbDialog, selectedUsers, new QBEntityCallback<QBChatDialog>() {
            @Override
            public void onSuccess(QBChatDialog qbChatDialog, Bundle bundle) {
                qbDialog = qbChatDialog;
                hideProgressDialog();
                finish();
            }

            @Override
            public void onError(QBResponseException e) {
                hideProgressDialog();
                showErrorSnackbar(R.string.chat_info_add_people_error, e, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateDialog(selectedUsers);
                    }
                });
            }
        });
    }

    private class SystemMessagesListener implements QBSystemMessageListener {
        @Override
        public void processMessage(QBChatMessage qbChatMessage) {
            Log.d(TAG, "System Message Received: " + qbChatMessage.getId());
            QBSettings.getInstance().init(ChatInfoActCon.this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
            QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
            if (qbChatMessage.getDialogId().equals(qbDialog.getDialogId())) {
                getDialog();
            }
        }

        @Override
        public void processError(QBChatException e, QBChatMessage qbChatMessage) {
            Log.d(TAG, "System Messages Error: " + e.getMessage() + "With MessageID: " + qbChatMessage.getId());
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }



    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if (qbDialog != null) {
            outState.putString(EXTRA_DIALOG, qbDialog.getDialogId());
        }
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (qbDialog == null) {
            qbDialog = QbDialogHolderE.getInstance().getChatDialogById(savedInstanceState.getString(EXTRA_DIALOG));
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);


    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

    }

}