package com.skylightapp.Markets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBMessageStatusesManager;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.QBSystemMessagesManager;
import com.quickblox.chat.exception.QBChatException;
import com.quickblox.chat.listeners.QBChatDialogMessageListener;
import com.quickblox.chat.listeners.QBChatDialogTypingListener;
import com.quickblox.chat.listeners.QBMessageStatusListener;
import com.quickblox.chat.listeners.QBSystemMessageListener;
import com.quickblox.chat.model.QBAttachment;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.conference.ConferenceClient;
import com.quickblox.conference.ConferenceSession;
import com.quickblox.conference.WsException;
import com.quickblox.conference.callbacks.ConferenceEntityCallback;
import com.quickblox.content.model.QBFile;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;

import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCTypes;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.MarketClasses.AttachmentPreviewAdapCon;
import com.skylightapp.MarketClasses.AttachmentPreviewAdapterView;
import com.skylightapp.MarketClasses.BusinessDeal;
import com.skylightapp.MarketClasses.BusinessDealSub;
import com.skylightapp.MarketClasses.CallServiceConf;
import com.skylightapp.MarketClasses.ChatAdapterConf;
import com.skylightapp.MarketClasses.ChatHelperCon;
import com.skylightapp.MarketClasses.DialogsManager;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.MarketClasses.MediaPickHelper;
import com.skylightapp.MarketClasses.NetworkConnectionChecker;
import com.skylightapp.MarketClasses.OnMediaPickedListener;
import com.skylightapp.MarketClasses.PermissionsCheckerCon;
import com.skylightapp.MarketClasses.QBDialogUtilsCon;
import com.skylightapp.MarketClasses.SystemPermissionsHelper;
import com.skylightapp.MarketClasses.ToastUtilsCon;
import com.skylightapp.MarketClasses.VerboseQbChatConListenerCon;
import com.skylightapp.MarketClasses.WebRtcSessionManagerCon;
import com.skylightapp.MarketInterfaces.ConstsInterface;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.DiscussionHistory;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import com.skylightapp.R;

import net.qiujuer.genius.ui.widget.FloatActionButton;

import static com.skylightapp.BuildConfig.APPLICATION_ID;
import static com.skylightapp.BuildConfig.QUICKBLOX_ACCT_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_APP_ID;
import static com.skylightapp.BuildConfig.QUICKBLOX_AUTH_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_SECRET_KEY;

public class ChatActCon extends BaseActCon implements OnMediaPickedListener, QBMessageStatusListener {
    private static final String TAG = ChatActCon.class.getSimpleName();

    public static final int REQUEST_CODE_SELECT_PEOPLE = 752;
    private static final int REQUEST_CODE_ATTACHMENT = 721;
    private static final int REQUEST_CONFERENCE_PERMISSION_CODE = 175;
    private static final int REQUEST_STREAM_PERMISSION_CODE = 176;
    private static final int PERMISSIONS_FOR_SAVE_FILE_IMAGE_REQUEST = 1010;

    public static final String PROPERTY_FORWARD_USER_NAME = "origin_sender_name";
    public static final String EXTRA_DIALOG_ID = "dialogId";
    public static final String EXTRA_IS_NEW_DIALOG = "isNewDialog";
    public static final String EXTRA_IS_OPEN_FROM_CALL = "isOpenFromCall";
    public static final String IS_IN_BACKGROUND = "is_in_background";

    public static final String ORDER_RULE = "order";
    private static final String CHAT_STATE_KEY = "chat_Key";
    public static final long TYPING_STATUS_DELAY = 2000;
    public static final long TYPING_STATUS_INACTIVITY_DELAY = 10000;
    private static final long SEND_TYPING_STATUS_DELAY = 3000;
    public static final int MAX_ATTACHMENTS_COUNT = 1;
    public static final int MAX_MESSAGE_SYMBOLS_LENGTH = 1000;
    public static final long CHANGE_CONFERENCE_ROOM_DELAY = 10000;
    public static final int MAX_CONFERENCE_OPPONENTS_ALLOWED = 12;


    private ProgressBar progressBar;
    private EditText messageEditText;
    private TextView typingStatus;
    private QBUser currentUser;
    private LinearLayout attachmentPreviewContainerLayout;
    private RecyclerView chatMessagesRecyclerView;
    private ChatAdapterConf chatAdapter;
    private AttachmentPreviewAdapCon attachmentPreviewAdapter;
    private ConnectionListener chatConnectionListener;
    private ImageAttachClickListener imageAttachClickListener;
    private VideoAttachClickListener videoAttachClickListener;
    private FileAttachClickListener fileAttachClickListener;
    private MessageLongClickListenerImpl messageLongClickListener;
    private ConversationNotificationClickListenerImpl conversationNotificationClickListener;
    private QBMessageStatusesManager qbMessageStatusesManager;
    private ChatMessageListener chatMessageListener = new ChatMessageListener();
    private SystemMessagesListener systemMessagesListener = new SystemMessagesListener();
    private TypingStatusListener typingStatusListener;
    private QBSystemMessagesManager systemMessagesManager;
    private List<QBChatMessage> messagesList;
    private QBChatDialog qbChatDialog,lastChatDialog;
    private ArrayList<QBChatMessage> unShownMessages;
    private PermissionsCheckerCon checker;
    private WebRtcSessionManagerCon sessionManager;
    private int skipPagination = 0;
    private Boolean checkAdapterInit = false;
    private boolean isOpenFromCall = false;
    private String streamID,chatState;
    private ServiceConnection callServiceConnection;
    private LinearLayoutCompat fabLayout;
    private ProfDAO profDAO;
    SharedPreferences userPreferences;
    private int qbUserID;
    private Calendar cal;
    private static final String APPLICATION_ID = QUICKBLOX_APP_ID;   //QUICKBLOX_APP_ID
    private static final String AUTH_KEY = QUICKBLOX_AUTH_KEY;
    private static final String AUTH_SECRET = QUICKBLOX_SECRET_KEY;
    private static final String ACCOUNT_KEY = QUICKBLOX_ACCT_KEY;
    private static final String SERVER_URL = "";

    private static final String PREF_NAME = "awajima";
    private DBHelper dbHelper;
    Gson gson, gson1,gson2;
    String json, json1, json2,nIN;
    Profile userProfile,  lastProfileUsed;
    private QBUser qbUser;
    TextInputWatcher textInputWatcher;
    private FloatActionButton fabMilestones,fabAddMembers,fabTranx,fabTimelines,fabControl;
    private long bizID;
    private Bundle bundle;
    private BusinessDeal businessDeal;
    private String bizDealTittle;
    private Profile bizProfile;
    private MarketBusiness marketBusiness;

    public static void startForResultFromCall(Activity activity, int code, String dialogId, boolean isOpenFromCall) {
        Intent intent = new Intent(activity, ChatActCon.class);
        intent.putExtra(ChatActCon.EXTRA_DIALOG_ID, dialogId);
        intent.putExtra(ChatActCon.EXTRA_IS_OPEN_FROM_CALL, isOpenFromCall);
        activity.startActivityForResult(intent, code);
    }

    public static void startForResult(Activity activity, int code, String dialogId, boolean isNewDialog) {
        Intent intent = new Intent(activity, ChatActCon.class);
        intent.putExtra(ChatActCon.EXTRA_DIALOG_ID, dialogId);
        intent.putExtra(ChatActCon.EXTRA_IS_NEW_DIALOG, isNewDialog);
        activity.startActivityForResult(intent, code);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            chatState = savedInstanceState.getString(CHAT_STATE_KEY);
        }

        setContentView(R.layout.act_chat_act_con);
        hasInternetConnection();
        setTitle("Awajima Chats");
        dbHelper= new DBHelper(this);
        profDAO = new ProfDAO(this);
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        gson= new Gson();
        gson1= new Gson();
        gson2= new Gson();
        qbUser= new QBUser();
        userProfile= new Profile();
        lastChatDialog= new QBChatDialog();
        textInputWatcher= new TextInputWatcher();
        cal = Calendar.getInstance();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastQBUserUsed", "");
        qbUser = gson1.fromJson(json1, QBUser.class);

        json2 = userPreferences.getString("LastQBUserUsed", "");
        lastChatDialog = gson2.fromJson(json2, QBChatDialog.class);
        //FirebaseAuthSettings firebaseAuthSettings = auth.getFirebaseAuthSettings();
        fabLayout = findViewById(R.id.Fab_layoutP);
        fabMilestones = findViewById(R.id.fab_create_mileston);
        fabAddMembers = findViewById(R.id.fab_add_members);
        fabTranx = findViewById(R.id.fab_tranx);
        fabTimelines = findViewById(R.id.fab_timelines);
        fabControl = findViewById(R.id.fabControl);
        //getSharedPrefsHelper().delete(IS_IN_BACKGROUND);
        Log.v(TAG, "onCreate ChatActivity on Thread ID = " + Thread.currentThread().getId());
        isOpenFromCall = getIntent().getBooleanExtra(EXTRA_IS_OPEN_FROM_CALL, false);
        String dialogID = getIntent().getStringExtra(EXTRA_DIALOG_ID);
        Log.d(TAG, "Getting Dialog ID from EXTRA => ID : " + dialogID);
        if(qbUser !=null){
            currentUser = qbUser;
        }
        reloginToChat();

        qbChatDialog = lastChatDialog;
        Log.d(TAG, "Deserialized dialog = " + qbChatDialog);
        Log.d(TAG, "dialogID = " + dialogID);

        if (qbChatDialog == null) {
            QBRestChatService.getChatDialogById(dialogID).performAsync(new QBEntityCallback<QBChatDialog>() {
                @Override
                public void onSuccess(QBChatDialog dialog, Bundle bundle) {
                    getQBDialogsHolder().addDialog(dialog);
                    qbChatDialog = dialog;
                }

                @Override
                public void onError(QBResponseException e) {
                    showErrorSnackbar(R.string.select_users_get_dialog_error, e, null);
                }
            });
        }

        try {
            qbChatDialog.initForChat(QBChatService.getInstance());
            qbChatDialog.addMessageListener(chatMessageListener);
            qbChatDialog.addIsTypingListener(typingStatusListener);
            setChatNameToActionBar();
        } catch (IllegalStateException | NullPointerException e) {
            Log.d(TAG, "initForChat error. Error message is : " + e.getMessage());
            Log.e(TAG, "Finishing " + TAG + ". Unable to init chat");
            finish();
        }

        // TODO Typing Status: 1/3 To add Typing Status functionality uncomment this string:
        //qbChatDialog.addIsTypingListener(new TypingStatusListener());

        initViews();
        initMessagesRecyclerView();
        initChatConnectionListener();
        initChat();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if (qbChatDialog != null) {
            outState.putString(EXTRA_DIALOG_ID, qbChatDialog.getDialogId());
        }
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(CHAT_STATE_KEY, chatState);

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (qbChatDialog == null) {
            try {
                qbChatDialog = getQBDialogsHolder().getDialogById(savedInstanceState.getString(EXTRA_DIALOG_ID));
            } catch (Exception e) {
                if (e.getMessage() != null) {
                    Log.d(TAG, e.getMessage());
                }
            }
        }
    }

    @Override
    protected void onResume() {
        checker = new PermissionsCheckerCon(getApplicationContext());
        sessionManager = WebRtcSessionManagerCon.getInstance();
        super.onResume();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onResumeFinished() {
        if (getChatHelper().isLogged()) {
            if (qbChatDialog == null) {
                String dialogID = getIntent().getStringExtra(EXTRA_DIALOG_ID);
                qbChatDialog = getQBDialogsHolder().getDialogById(dialogID);
            }
            updateDialogFromRest();
            returnToChat();
        } else {
            reloginToChat();
        }
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    private void updateDialogFromRest() {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        Log.d(TAG, "Starting Dialog Update");
        QBRestChatService.getChatDialogById(qbChatDialog.getDialogId()).performAsync(new QBEntityCallback<QBChatDialog>() {
            @Override
            public void onSuccess(QBChatDialog updatedChatDialog, Bundle bundle) {
                Log.d(TAG, "Dialog Update Successful: " + updatedChatDialog.getDialogId());
                getQBDialogsHolder().addDialog(updatedChatDialog);
                qbChatDialog = updatedChatDialog;
                setChatNameToActionBar();
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d(TAG, "Dialog Update Error: " + e.getMessage());
            }
        });
    }

    private void reloginToChat() {
        gson= new Gson();
        gson1= new Gson();
        qbUser= new QBUser();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json1 = userPreferences.getString("LastQBUserUsed", "");
        qbUser = gson1.fromJson(json1, QBUser.class);
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        if(qbUser !=null){
            QBChatService.getInstance().login(qbUser, new QBEntityCallback<Void>() {


                @Override
                public void onSuccess(Void unused, Bundle bundle) {
                    returnToChat();
                    hideProgressDialog();

                }

                @Override
                public void onError(QBResponseException exception) {
                    hideProgressDialog();
                    Log.e(TAG, getString(R.string.relogin_chat_failed) + exception.getMessage());
                    showErrorSnackbar(R.string.relogin_chat_failed, exception, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            reloginToChat();
                        }
                    });

                }
            });


        }



    }

    private void returnToChat() {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        Log.e(TAG, "Returning to Chat");
        if (qbChatDialog != null) {
            qbChatDialog.initForChat(QBChatService.getInstance());
            if (!qbChatDialog.isJoined()) {
                try {
                    qbChatDialog.join(new DiscussionHistory());
                } catch (Exception e) {
                    Log.e(TAG, "Join Dialog Exception: " + e.getMessage());
                    showErrorSnackbar(R.string.error_joining_chat, e, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            returnToChat();
                        }
                    });
                }
            }

            returnListeners();

            // Loading unread messages received in background
            if (getSharedPrefsHelper().get(IS_IN_BACKGROUND, false)) {
                progressBar.setVisibility(View.VISIBLE);
                skipPagination = 0;
                checkAdapterInit = false;
                loadChatHistory();
            }
        }
    }

    private void returnListeners() {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        if (qbChatDialog.getMessageListeners().size() > 0) {
            Log.e(TAG, "Message Listeners count - " + qbChatDialog.getMessageListeners().size());
            qbChatDialog.removeMessageListrener(chatMessageListener);
            Log.e(TAG, "Remove current Message Listener");
        }
        Log.e(TAG, "Adding current Message Listener");
        qbChatDialog.addMessageListener(chatMessageListener);
        Log.e(TAG, "Now Message Listeners count - " + qbChatDialog.getMessageListeners().size());

        // TODO Typing Status: 2/3 To add Typing Status functionality uncomment this string:
        //qbChatDialog.addIsTypingListener(new TypingStatusListener());

        try {
            systemMessagesManager = QBChatService.getInstance().getSystemMessagesManager();
            systemMessagesManager.addSystemMessageListener(systemMessagesListener);
            qbMessageStatusesManager = QBChatService.getInstance().getMessageStatusesManager();
            qbMessageStatusesManager.addMessageStatusListener(this);
        } catch (Exception e) {
            if (e.getMessage() != null) {
                Log.d(TAG, e.getMessage());
            }
            Log.e(TAG, "Can not get QBChatService. Finishing Activity");
            showErrorSnackbar(R.string.error_getting_chat_service, e, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnListeners();
                }
            });
        }

        chatAdapter.setAttachImageClickListener(imageAttachClickListener);
        chatAdapter.setAttachVideoClickListener(videoAttachClickListener);
        chatAdapter.setAttachFileClickListener(fileAttachClickListener);
        chatAdapter.setMessageLongClickListener(messageLongClickListener);
        chatAdapter.setConversationNotificationClickListener(conversationNotificationClickListener);
        QBChatService.getInstance().addConnectionListener(chatConnectionListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideProgressDialog();
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        chatAdapter.removeClickListeners();
        qbChatDialog.removeMessageListrener(chatMessageListener);
        qbChatDialog.removeIsTypingListener(typingStatusListener);
        QBChatService.getInstance().removeConnectionListener(chatConnectionListener);
        chatConnectionListener = null;
        if (qbMessageStatusesManager != null) {
            qbMessageStatusesManager.removeMessageStatusListener(this);
        }
        getSharedPrefsHelper().save(IS_IN_BACKGROUND, true);
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        //QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        try {
            if (systemMessagesManager != null) {
                systemMessagesManager.removeSystemMessageListener(systemMessagesListener);
            }
            if (qbChatDialog != null) {
                qbChatDialog.removeMessageListrener(chatMessageListener);
                qbChatDialog.removeIsTypingListener(typingStatusListener);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        //getSharedPrefsHelper().delete(IS_IN_BACKGROUND);
    }

    @Override
    public void onBackPressed() {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        qbChatDialog.removeMessageListrener(chatMessageListener);
        sendDialogId();
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_chat, menu);
        MenuItem menuItemInfo = menu.findItem(R.id.menu_chat_action_info);
        MenuItem menuItemLeave = menu.findItem(R.id.menu_chat_action_leave);
        MenuItem menuItemDelete = menu.findItem(R.id.menu_chat_action_delete);
        MenuItem menuItemConference = menu.findItem(R.id.menu_chat_action_conference);
        MenuItem menuItemStream = menu.findItem(R.id.menu_chat_action_stream);

        switch (qbChatDialog.getType()) {
            case GROUP:
                menuItemDelete.setVisible(false);
                break;
            case PRIVATE:
                menuItemInfo.setVisible(false);
                menuItemLeave.setVisible(false);
                menuItemConference.setVisible(false);
                menuItemStream.setVisible(false);
                break;
            case PUBLIC_GROUP:
                menuItemInfo.setVisible(false);
                menuItemLeave.setVisible(false);
                menuItemDelete.setVisible(false);
                menuItemConference.setVisible(false);
                menuItemStream.setVisible(false);
                break;
        }

        if (isOpenFromCall) {
            menuItemConference.setVisible(false);
            menuItemStream.setVisible(false);
            menuItemLeave.setVisible(false);
        }

        if (qbChatDialog.getOccupants().size() > MAX_CONFERENCE_OPPONENTS_ALLOWED) {
            menuItemConference.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_chat_action_conference:
                if (getChatHelper().isLogged()) {
                    getDialogsManager().sendMessageStartedConference(qbChatDialog);
                    checkConferencePermissions();
                } else {
                    openAlertDialogWaitChat();
                }
                return true;
            case R.id.menu_chat_action_stream:
                if (getChatHelper().isLogged()) {
                    streamID = createNewStreamID();
                    getDialogsManager().sendMessageStartedStream(qbChatDialog, streamID);
                    checkStreamPermissions();
                } else {
                    openAlertDialogWaitChat();
                }
                return true;
            case R.id.menu_chat_action_info:
                startChatInfoWithUpdatedDialog();
                return true;

            case R.id.menu_chat_action_leave:
                openAlertDialogLeaveChat();
                return true;

            case R.id.menu_chat_action_delete:
                deleteChat();
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String createNewStreamID() {
        Integer currentUserID = currentUser.getId();
        Long timeStamp = System.currentTimeMillis();
        String streamID = currentUserID + "_" + timeStamp;
        return streamID;
    }

    private void checkConferencePermissions() {
        boolean needToCheckPermissions = checker.missAllPermissions(ConstsInterface.PERMISSIONS);

        if (needToCheckPermissions) {
            PermissionsActCon.startForResult(this, REQUEST_CONFERENCE_PERMISSION_CODE, ConstsInterface.PERMISSIONS);
        } else {
            startConference(qbChatDialog.getDialogId(), currentUser.getId(), qbChatDialog.getOccupants());
        }
    }

    private void startConference(final String dialogID, Integer userID, final List<Integer> occupants) {
        Log.d(TAG, "Start (Join) Conference : " + dialogID);
        showProgressDialog(R.string.join_conference);
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        ConferenceClient client = ConferenceClient.getInstance(getApplicationContext());
        QBRTCTypes.QBConferenceType conferenceType = QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO;

        client.createSession(userID, conferenceType, new ConferenceEntityCallback<ConferenceSession>() {
            @Override
            public void onSuccess(ConferenceSession session) {
                hideProgressDialog();

                if (session.getActivePublishers().size() >= MAX_CONFERENCE_OPPONENTS_ALLOWED) {
                    session.leave();
                    showInfoSnackbar("Conference Room is full", R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hideSnackbar();
                        }
                    });
                } else {
                    sessionManager.setCurrentSession(session);
                    Log.d(TAG, "Session Created Successfully. \n Session ID = " + session.getSessionID() + "\n Dialog ID = " + session.getDialogID());
                    setCallButtonsDefault();
                    CallActivityCon.start(ChatActCon.this, dialogID, qbChatDialog.getName(), dialogID, occupants, false);
                }
            }

            @Override
            public void onError(WsException e) {
                hideProgressDialog();
                showErrorSnackbar(R.string.join_conference_error, e, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkConferencePermissions();
                    }
                });
            }
        });
    }

    private void checkStreamPermissions() {
        boolean needToAskPermissions = checker.missAllPermissions(ConstsInterface.PERMISSIONS);
        if (needToAskPermissions) {
            PermissionsActCon.startForResult(this, REQUEST_STREAM_PERMISSION_CODE, ConstsInterface.PERMISSIONS);
        } else {
            startStream(streamID);
        }
    }

    private void startStream(String newStreamID) {
        Log.d(TAG, "Starting Stream");
        showProgressDialog(R.string.starting_stream);
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        ConferenceClient client = ConferenceClient.getInstance(this);
        QBRTCTypes.QBConferenceType conferenceType = QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO;

        client.createSession(currentUser.getId(), conferenceType, new ConferenceEntityCallback<ConferenceSession>() {
            @Override
            public void onSuccess(ConferenceSession conferenceSession) {
                hideProgressDialog();
                sessionManager.setCurrentSession(conferenceSession);
                Log.d(TAG, "Session Created Successfully. \n Session ID = " + conferenceSession.getSessionID() + "\n Dialog ID = " + conferenceSession.getDialogID());
                setCallButtonsDefault();
                CallActivityCon.start(ChatActCon.this, newStreamID, currentUser.getFullName(), qbChatDialog.getDialogId(), qbChatDialog.getOccupants(), false);
            }

            @Override
            public void onError(WsException e) {
                hideProgressDialog();
                showErrorSnackbar(R.string.join_stream_error, e, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkStreamPermissions();
                    }
                });
            }
        });
    }

    private void joinStream(String roomId, String streamerName) {
        Log.d(TAG, "Join Stream : " + streamID);
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        showProgressDialog(R.string.join_stream);
        ConferenceClient client = ConferenceClient.getInstance(this);
        QBRTCTypes.QBConferenceType conferenceType = QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO;

        client.createSession(currentUser.getId(), conferenceType, new ConferenceEntityCallback<ConferenceSession>() {
            @Override
            public void onSuccess(ConferenceSession session) {
                hideProgressDialog();
                sessionManager.setCurrentSession(session);
                Log.d(TAG, "Session Created Successfully. \n Session ID = " + session.getSessionID() + "\n Dialog ID = " + session.getDialogID());
                CallActivityCon.start(ChatActCon.this, roomId, streamerName, qbChatDialog.getDialogId(), qbChatDialog.getOccupants(), true);
            }

            @Override
            public void onError(WsException e) {
                hideProgressDialog();
                showErrorSnackbar(R.string.join_stream_error, e, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        joinStream(streamID, streamerName);
                    }
                });
            }
        });
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    private void setCallButtonsDefault() {
        getSharedPrefsHelper().save(ConstsInterface.PREF_CAM_ENABLED, false);
        getSharedPrefsHelper().save(ConstsInterface.PREF_MIC_ENABLED, true);
        getSharedPrefsHelper().save(ConstsInterface.PREF_SCREEN_SHARING_TOGGLE_CHECKED, true);
        getSharedPrefsHelper().save(ConstsInterface.PREF_SWAP_CAM_TOGGLE_CHECKED, true);
    }

    private void showPopupMenu(boolean isIncomingMessageClicked, View view, final QBChatMessage chatMessage) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_message_longclick, popupMenu.getMenu());
        popupMenu.setGravity(Gravity.RIGHT);

        if (isIncomingMessageClicked || qbChatDialog.getType() != QBDialogType.GROUP) {
            popupMenu.getMenu().removeItem(R.id.menu_message_delivered_to);
            popupMenu.getMenu().removeItem(R.id.menu_message_viewed_by);
            popupMenu.setGravity(Gravity.LEFT);
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_message_forward:
                        Log.d(TAG, "Forward Message");
                        startForwardingMessage(chatMessage);
                        break;
                    case R.id.menu_message_delivered_to:
                        Log.d(TAG, "Delivered by");
                        showDeliveredToScreen(chatMessage);
                        break;
                    case R.id.menu_message_viewed_by:
                        Log.d(TAG, "Viewed by");
                        showViewedByScreen(chatMessage);
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void showFilePopup(int itemViewType, final QBAttachment attachment, View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_file_popup, popupMenu.getMenu());

        if (itemViewType == ChatAdapterConf.TYPE_TEXT_RIGHT || itemViewType == ChatAdapterConf.TYPE_ATTACH_RIGHT) {
            popupMenu.setGravity(Gravity.RIGHT);
        } else if (itemViewType == ChatAdapterConf.TYPE_TEXT_LEFT || itemViewType == ChatAdapterConf.TYPE_ATTACH_LEFT) {
            popupMenu.setGravity(Gravity.LEFT);
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_file_save) {
                    saveFileToStorage(attachment);
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void saveFileToStorage(QBAttachment attachment) {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        File file = new File(this.getFilesDir(), attachment.getName());
        String url = QBFile.getPrivateUrlForUID(attachment.getId());
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, file.getName());
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.allowScanningByMediaScanner();
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        if (manager != null) {
            manager.enqueue(request);
        }
    }

    private void startForwardingMessage(QBChatMessage message) {
        ForwardToActCon.start(this, message);
    }

    private void showDeliveredToScreen(QBChatMessage message) {
        MessageInfoActCon.start(this, message, MessageInfoActCon.MESSAGE_INFO_DELIVERED_TO);
    }

    private void showViewedByScreen(QBChatMessage message) {
        MessageInfoActCon.start(this, message, MessageInfoActCon.MESSAGE_INFO_READ_BY);
    }

    private void startChatInfoWithUpdatedDialog() {
        showProgressDialog(R.string.dlg_loading);
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        Log.d(TAG, "Starting Dialog Update");
        QBRestChatService.getChatDialogById(qbChatDialog.getDialogId()).performAsync(new QBEntityCallback<QBChatDialog>() {
            @Override
            public void onSuccess(QBChatDialog updatedChatDialog, Bundle bundle) {
                Log.d(TAG, "Update Dialog Successful: " + updatedChatDialog.getDialogId());
                getQBDialogsHolder().addDialog(updatedChatDialog);
                hideProgressDialog();
                ChatInfoActCon.start(ChatActCon.this, qbChatDialog.getDialogId());
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d(TAG, "Update Dialog Error: " + e.getMessage());
                hideProgressDialog();
                showErrorSnackbar(R.string.select_users_get_dialog_error, e, null);
            }
        });
    }

    private void sendDialogId() {
        Intent intent = new Intent().putExtra(EXTRA_DIALOG_ID, qbChatDialog.getDialogId());
        setResult(Activity.RESULT_OK, intent);
    }

    private void openAlertDialogWaitChat() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChatActCon.this, R.style.AlertDialogStyle);
        alertDialogBuilder.setTitle(getString(R.string.dlg_wait_for_chat_connection_title));
        alertDialogBuilder.setMessage(getString(R.string.dlg_wait_for_chat_connection));
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialogBuilder.create();
        alertDialogBuilder.show();
    }

    private void openAlertDialogLeaveChat() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChatActCon.this, R.style.AlertDialogStyle);
        alertDialogBuilder.setTitle(getString(R.string.dlg_leave_dialog));
        alertDialogBuilder.setMessage(getString(R.string.dlg_leave_question));
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton(getString(R.string.dlg_delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                leaveGroupChat();
            }
        });
        alertDialogBuilder.setNegativeButton(getString(R.string.dlg_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialogBuilder.create();
        alertDialogBuilder.show();
    }

    private void leaveGroupChat() {
        showProgressDialog(R.string.dlg_loading);
        getDialogsManager().sendMessageLeftUser(qbChatDialog);
        getDialogsManager().sendSystemMessageLeftUser(systemMessagesManager, qbChatDialog);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);

        Log.d(TAG, "Leaving Dialog");
        getChatHelper().exitFromDialog(qbChatDialog, new QBEntityCallback<QBChatDialog>() {
            @Override
            public void onSuccess(QBChatDialog qbDialog, Bundle bundle) {
                Log.d(TAG, "Leaving Dialog Successful: " + qbDialog.getDialogId());
                hideProgressDialog();
                getQBDialogsHolder().deleteDialog(qbDialog);
                finish();
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d(TAG, "Leaving Dialog Error: " + e.getMessage());
                hideProgressDialog();
                showErrorSnackbar(R.string.error_leave_chat, e, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        leaveGroupChat();
                    }
                });
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        Log.d(TAG, "onActivityResult with resultCode: " + resultCode + " requestCode: " + requestCode);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_SELECT_PEOPLE && data != null) {
                progressBar.setVisibility(View.VISIBLE);
                final ArrayList<QBUser> selectedUsers = (ArrayList<QBUser>) data.getSerializableExtra(
                        SelectUsersActCon.EXTRA_QB_USERS);
                List<Integer> existingOccupantsIds = qbChatDialog.getOccupants();
                final List<Integer> newUsersIds = new ArrayList<>();

                if (selectedUsers != null) {
                    for (QBUser user : selectedUsers) {
                        if (!existingOccupantsIds.contains(user.getId())) {
                            newUsersIds.add(user.getId());
                        }
                    }
                } else {
                    return;
                }

                QBRestChatService.getChatDialogById(qbChatDialog.getDialogId()).performAsync(new QBEntityCallback<QBChatDialog>() {
                    @Override
                    public void onSuccess(QBChatDialog qbChatDialog, Bundle bundle) {
                        progressBar.setVisibility(View.GONE);
                        getDialogsManager().sendMessageAddedUsers(qbChatDialog, newUsersIds);
                        getDialogsManager().sendSystemMessageAddedUser(systemMessagesManager, qbChatDialog, newUsersIds);
                        if (qbChatDialog != null) {
                            ChatActCon.this.qbChatDialog = qbChatDialog;
                        }
                        updateDialogUsers(selectedUsers);
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        progressBar.setVisibility(View.GONE);
                        showErrorSnackbar(R.string.update_dialog_error, e, null);
                    }
                });
            }
            if (requestCode == REQUEST_CONFERENCE_PERMISSION_CODE) {
                checkConferencePermissions();
            }
            if (requestCode == REQUEST_STREAM_PERMISSION_CODE) {
                checkStreamPermissions();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_FOR_SAVE_FILE_IMAGE_REQUEST && grantResults[0] != -1) {
            openImagePicker();
        }
    }

    @Override
    public void onMediaPicked(int requestCode, File file) {
        switch (requestCode) {
            case REQUEST_CODE_ATTACHMENT:
                SystemPermissionsHelper permissionsHelper = new SystemPermissionsHelper(this);
                if (permissionsHelper.isSaveImagePermissionGranted()) {
                    attachmentPreviewAdapter.add(file);
                } else {
                    permissionsHelper.requestPermissionsForSaveFileImage();
                }
                break;
        }
    }

    @Override
    public void onMediaPickError(int requestCode, Exception e) {
        if (e.getMessage() != null) {
            Log.d(TAG, e.getMessage());
        }
        showErrorSnackbar(0, e, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });
    }

    @Override
    public void onMediaPickClosed(int ignored) {

    }

    public void onSendChatClick(View view) {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        try {
            qbChatDialog.sendStopTypingNotification();

        } catch (XMPPException | SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        int totalAttachmentsCount = attachmentPreviewAdapter.getCount();
        Collection<QBAttachment> uploadedAttachments = attachmentPreviewAdapter.getUploadedAttachments();
        if (!uploadedAttachments.isEmpty()) {
            if (uploadedAttachments.size() == totalAttachmentsCount) {
                for (QBAttachment attachment : uploadedAttachments) {
                    sendChatMessage(null, attachment);
                }
            } else {
                ToastUtilsCon.shortToast(ChatActCon.this, R.string.chat_wait_for_attachments_to_upload);
            }
        }

        String text = messageEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(text)) {
            if (text.length() > MAX_MESSAGE_SYMBOLS_LENGTH) {
                text = text.substring(0, MAX_MESSAGE_SYMBOLS_LENGTH);
            }
            sendChatMessage(text, null);
        }
    }

    private void showMessage(QBChatMessage message) {
        if (isAdapterConnected()) {
            chatAdapter.addMessage(message);
            scrollMessageListDown();
        } else {
            delayShowMessage(message);
        }
    }

    private boolean isAdapterConnected() {
        return checkAdapterInit;
    }

    private void delayShowMessage(QBChatMessage message) {
        if (unShownMessages == null) {
            unShownMessages = new ArrayList<>();
        }
        unShownMessages.add(message);
    }

    private void initViews() {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        textInputWatcher= new TextInputWatcher();
        typingStatus = findViewById(R.id.tv_typing_status);

        messageEditText = findViewById(R.id.et_chat_message);
        messageEditText.addTextChangedListener(textInputWatcher);

        progressBar = findViewById(R.id.progress_chat);
        attachmentPreviewContainerLayout = findViewById(R.id.ll_attachment_preview_container);

        ImageView attachmentBtnChat = findViewById(R.id.iv_chat_attachment);
        attachmentBtnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attachmentPreviewAdapter.getCount() >= MAX_ATTACHMENTS_COUNT) {
                    ToastUtilsCon.shortToast(ChatActCon.this, R.string.error_attachment_count);
                } else {
                    openImagePicker();
                }
            }
        });

        attachmentPreviewAdapter = new AttachmentPreviewAdapCon(this,
                new AttachmentPreviewAdapCon.AttachmentCountChangedListener() {
                    @Override
                    public void onAttachmentCountChanged(int count) {
                        attachmentPreviewContainerLayout.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
                    }
                },
                new AttachmentPreviewAdapCon.AttachmentUploadErrorListener() {
                    @Override
                    public void onAttachmentUploadError(QBResponseException e) {
                        showErrorSnackbar(0, e, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                openImagePicker();
                            }
                        });
                    }
                });

        AttachmentPreviewAdapterView previewAdapterView = findViewById(R.id.adapter_attachment_preview);
        previewAdapterView.setAdapter(attachmentPreviewAdapter);
    }

    private void openImagePicker() {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        SystemPermissionsHelper permissionHelper = new SystemPermissionsHelper(this);
        if (permissionHelper.isSaveImagePermissionGranted()) {
            MediaPickHelper.pickAnImage(this, REQUEST_CODE_ATTACHMENT);
        } else {
            permissionHelper.requestPermissionsForSaveFileImage();
        }
    }

    private void initMessagesRecyclerView() {
        chatMessagesRecyclerView = findViewById(R.id.rv_chat_messages);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        chatMessagesRecyclerView.setLayoutManager(layoutManager);

        messagesList = new ArrayList<>();
        chatAdapter = new ChatAdapterConf(getApplicationContext(), qbChatDialog, messagesList);
        chatAdapter.setPaginationHistoryListener(new PaginationListener());
        chatMessagesRecyclerView.addItemDecoration(
                new StickyRecyclerHeadersDecoration(chatAdapter));

        chatMessagesRecyclerView.setAdapter(chatAdapter);
        imageAttachClickListener = new ImageAttachClickListener();
        videoAttachClickListener = new VideoAttachClickListener();
        fileAttachClickListener = new FileAttachClickListener();
        messageLongClickListener = new MessageLongClickListenerImpl();
        conversationNotificationClickListener = new ConversationNotificationClickListenerImpl();
    }

    private void sendChatMessage(final String text, final QBAttachment attachment) {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        if (getChatHelper().isLogged()) {
            QBChatMessage chatMessage = new QBChatMessage();
            if (attachment != null) {
                chatMessage.addAttachment(attachment);
            } else {
                chatMessage.setBody(text);
            }
            chatMessage.setSaveToHistory(true);
            chatMessage.setDateSent(System.currentTimeMillis() / 1000);
            chatMessage.setMarkable(true);

            if (!QBDialogType.PRIVATE.equals(qbChatDialog.getType()) && !qbChatDialog.isJoined()) {
                try {
                    qbChatDialog.join(new DiscussionHistory());
                } catch (XMPPException | SmackException e) {
                    if (e.getMessage() != null) {
                        Log.d(TAG, e.getMessage());
                    }
                }
                ToastUtilsCon.shortToast(ChatActCon.this, R.string.chat_still_joining);
                return;
            }

            try {
                Log.d(TAG, "Sending Message with ID: " + chatMessage.getId());
                qbChatDialog.sendMessage(chatMessage);

                if (QBDialogType.PRIVATE.equals(qbChatDialog.getType())) {
                    showMessage(chatMessage);
                }

                if (attachment != null) {
                    attachmentPreviewAdapter.remove(attachment);
                } else {
                    messageEditText.setText("");
                }
            } catch (SmackException.NotConnectedException e) {
                if (e.getMessage() != null) {
                    Log.w(TAG, e.getMessage());
                }
                ToastUtilsCon.shortToast(ChatActCon.this, R.string.chat_error_send_message);
            }
        } else {
            showProgressDialog(R.string.dlg_login);
            Log.d(TAG, "Relogin to Chat");
            getChatHelper().loginToChat(currentUser, new QBEntityCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid, Bundle bundle) {
                    Log.d(TAG, "Relogin Successfull");
                    sendChatMessage(text, attachment);
                    hideProgressDialog();
                }

                @Override
                public void onError(QBResponseException e) {
                    Log.d(TAG, "Relogin Error: " + e.getMessage());
                    hideProgressDialog();
                    ToastUtilsCon.shortToast(ChatActCon.this, R.string.chat_send_message_error);
                }
            });
        }
    }

    private void initChat() {
        if (qbChatDialog != null) {
            switch (qbChatDialog.getType()) {
                case GROUP:
                case PUBLIC_GROUP:
                    joinGroupChat(false);
                    break;
                case PRIVATE:
                    loadDialogUsers();
                    break;
                default:
                    ToastUtilsCon.shortToast(ChatActCon.this, String.format("%s %s", getString(R.string.chat_unsupported_type), qbChatDialog.getType().name()));
                    Log.e(TAG, "Finishing " + TAG + ". Unsupported chat type");
                    finish();
                    break;
            }
        }
    }

    private void joinGroupChat(boolean returnToChat) {
        progressBar.setVisibility(View.VISIBLE);
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        getChatHelper().join(qbChatDialog, new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void result, Bundle b) {
                Log.d(TAG, "Joined to Dialog Successful");
                notifyUsersAboutCreatingDialog();
                if (returnToChat) {
                    onResumeFinished();
                }
                hideProgressDialog();
                loadDialogUsers();
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d(TAG, "Joining Dialog Error:" + e.getMessage());
                progressBar.setVisibility(View.GONE);
                showErrorSnackbar(R.string.join_chat_error, e, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        joinGroupChat(true);
                    }
                });
            }
        });
    }

    private void notifyUsersAboutCreatingDialog() {
        if (getIntent().getBooleanExtra(EXTRA_IS_NEW_DIALOG, false)) {
            getDialogsManager().sendMessageCreatedDialog(qbChatDialog);
            getIntent().removeExtra(EXTRA_IS_NEW_DIALOG);
        }
    }

    private void updateDialogUsers(final ArrayList<QBUser> selectedUsers) {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        getChatHelper().updateDialogUsers(currentUser, qbChatDialog, selectedUsers,
                new QBEntityCallback<QBChatDialog>() {
                    @Override
                    public void onSuccess(QBChatDialog dialog, Bundle args) {
                        qbChatDialog = dialog;
                        loadDialogUsers();
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        hideProgressDialog();
                        showErrorSnackbar(R.string.chat_info_add_people_error, e,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        updateDialogUsers(selectedUsers);
                                    }
                                });
                    }
                }
        );
    }

    private void loadDialogUsers() {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        getChatHelper().getUsersFromDialog(qbChatDialog, new QBEntityCallback<ArrayList<QBUser>>() {
            @Override
            public void onSuccess(ArrayList<QBUser> users, Bundle bundle) {
                setChatNameToActionBar();
                loadChatHistory();
            }

            @Override
            public void onError(QBResponseException e) {
                progressBar.setVisibility(View.GONE);
                showErrorSnackbar(R.string.chat_load_users_error, e,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadDialogUsers();
                            }
                        });
            }
        });
    }

    private void setChatNameToActionBar() {
        String chatName = QBDialogUtilsCon.getDialogName(ChatActCon.this, qbChatDialog);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(chatName);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);

            String subtitle;
            if (qbChatDialog.getOccupants().size() != 1) {
                subtitle = getString(R.string.chat_subtitle_plural, String.valueOf(qbChatDialog.getOccupants().size()));
            } else {
                subtitle = getString(R.string.chat_subtitle_singular, "1");
            }
            ab.setSubtitle(subtitle);
        }
    }

    private void loadChatHistory() {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
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
                    addDelayedMessagesToAdapter();
                }
                if (skipPagination == 0) {
                    scrollMessageListDown();
                }
                skipPagination += ChatHelperCon.CHAT_HISTORY_ITEMS_PER_PAGE;
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d(TAG, "Loading Dialog History Error: " + e.getMessage());
                progressBar.setVisibility(View.GONE);
                showErrorSnackbar(R.string.load_chat_history_error, e, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadChatHistory();
                    }
                });
            }
        });
    }

    private void addDelayedMessagesToAdapter() {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        if (unShownMessages != null && !unShownMessages.isEmpty()) {
            List<QBChatMessage> chatList = chatAdapter.getMessages();
            for (QBChatMessage message : unShownMessages) {
                if (!chatList.contains(message)) {
                    chatAdapter.addMessage(message);
                }
            }
        }
    }

    private void scrollMessageListDown() {
        chatMessagesRecyclerView.scrollToPosition(messagesList.size() - 1);
    }

    private void deleteChat() {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        if (qbChatDialog.getType() == QBDialogType.PUBLIC_GROUP) {
            ToastUtilsCon.shortToast(ChatActCon.this, R.string.public_group_chat_cannot_be_deleted);
        } else {
            QBRestChatService.deleteDialog(qbChatDialog.getDialogId(), false).performAsync(new QBEntityCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid, Bundle bundle) {
                    Log.d(TAG, "Chat Deleted");
                    getQBDialogsHolder().deleteDialog(qbChatDialog);
                    setResult(RESULT_OK);
                    finish();
                }

                @Override
                public void onError(QBResponseException e) {
                    showErrorSnackbar(R.string.dialogs_deletion_error, e, v -> deleteChat());
                }
            });
        }
    }

    private void initChatConnectionListener() {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        View rootView = findViewById(R.id.rv_chat_messages);
        chatConnectionListener = new VerboseQbChatConListenerCon(ChatActCon.this, rootView) {
            @Override
            public void reconnectionSuccessful() {
                super.reconnectionSuccessful();
                Log.d(TAG, "Reconnection Successful");
                skipPagination = 0;
                switch (qbChatDialog.getType()) {
                    case GROUP:
                        checkAdapterInit = false;
                        // Join active room if we're in Group Chat
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                joinGroupChat(true);
                            }
                        });
                        break;
                }
            }

            @Override
            public void reconnectionFailed(Exception error) {
                super.reconnectionFailed(error);
                Log.d(TAG, R.string.reconnect_failed + error.getMessage());
                showErrorSnackbar(R.string.reconnect_failed, error, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reloginToChat();
                    }
                });
            }
        };
    }

    private void bindCallServiceAndStartNewConversation() {
        callServiceConnection = new CallServiceConnection();
        Intent intent = new Intent(this, CallServiceConf.class);
        bindService(intent, callServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void startSuitableConversation(QBChatMessage qbChatMessage) {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        String notificationType = (String) qbChatMessage.getProperty(DialogsManager.PROPERTY_NOTIFICATION_TYPE);
        boolean isConference = notificationType.equals(DialogsManager.START_CONFERENCE);
        boolean isStream = notificationType.equals(DialogsManager.START_STREAM);

        if (isConference) {
            checkConferencePermissions();
        } else if (isStream) {
            streamID = (String) qbChatMessage.getProperty(DialogsManager.PROPERTY_CONVERSATION_ID);
            if (currentUser.getId().equals(qbChatMessage.getSenderId())) {
                checkStreamPermissions();
            } else {
                QBUser streamer = getQBUsersHolder().getUserById(qbChatMessage.getSenderId());
                String streamerName = TextUtils.isEmpty(streamer.getFullName()) ? streamer.getLogin() : streamer.getFullName();
                joinStream(streamID, streamerName);
            }
        }
    }

    @Override
    public void processMessageDelivered(String messageID, String dialogID, Integer userID) {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);

        if (qbChatDialog.getDialogId().equals(dialogID) && userID != null) {
            chatAdapter.updateStatusDelivered(messageID, userID);
        }
    }

    @Override
    public void processMessageRead(String messageID, String dialogID, Integer userID) {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        if (qbChatDialog.getDialogId().equals(dialogID) && userID != null) {
            chatAdapter.updateStatusRead(messageID, userID);
        }
    }

    private class ChatMessageListener implements QBChatDialogMessageListener {
        @Override
        public void processMessage(String s, QBChatMessage qbChatMessage, Integer integer) {
            Log.d(TAG, "Processing Received Message: " + qbChatMessage.getBody());
            showMessage(qbChatMessage);
        }

        @Override
        public void processError(String s, QBChatException e, QBChatMessage qbChatMessage, Integer integer) {
            Log.d(TAG, "Processing Received ERROR: " + e.getMessage() + qbChatMessage.getBody());
        }
    }

    private class SystemMessagesListener implements QBSystemMessageListener {
        @Override
        public void processMessage(final QBChatMessage qbChatMessage) {
            Log.d(TAG, "System Message Received: " + qbChatMessage.getId());
            getDialogsManager().onSystemMessageReceived(qbChatMessage);
            updateDialogFromRest();
        }

        @Override
        public void processError(QBChatException e, QBChatMessage qbChatMessage) {
            Log.d(TAG, "System Messages Error: " + e.getMessage() + "With MessageID: " + qbChatMessage.getId());
        }
    }

    public class ImageAttachClickListener implements ChatAdapterConf.AttachClickListener {

        @Override
        public void onAttachmentClicked(int itemViewType, View view, QBAttachment attachment) {
            if (attachment != null) {
                String url = QBFile.getPrivateUrlForUID(attachment.getId());
                AttachmentImageActivity.start(ChatActCon.this, url);
            }
        }
    }

    private class VideoAttachClickListener implements ChatAdapterConf.AttachClickListener {

        @Override
        public void onAttachmentClicked(int itemViewType, View view, QBAttachment attachment) {
            if (attachment != null) {
                String url = QBFile.getPrivateUrlForUID(attachment.getId());
                AttachmentVideoAct.start(ChatActCon.this, attachment.getName(), url);
            }
        }
    }

    private class FileAttachClickListener implements ChatAdapterConf.AttachClickListener {

        @Override
        public void onAttachmentClicked(int itemViewType, View view, QBAttachment attachment) {
            if (attachment != null) {
                showFilePopup(itemViewType, attachment, view);
            }
        }
    }

    private class MessageLongClickListenerImpl implements ChatAdapterConf.MessageLongClickListener {
        @SuppressLint("MissingPermission")
        @Override
        public void onMessageLongClicked(int itemViewType, View view, QBChatMessage qbChatMessage) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                vibrator.vibrate(80);
            }
            if (qbChatMessage != null) {
                if (itemViewType == ChatAdapterConf.TYPE_TEXT_RIGHT || itemViewType == ChatAdapterConf.TYPE_ATTACH_RIGHT) {
                    Log.d(TAG, "Outgoing message LongClicked");
                    showPopupMenu(false, view, qbChatMessage);
                } else if (itemViewType == ChatAdapterConf.TYPE_TEXT_LEFT || itemViewType == ChatAdapterConf.TYPE_ATTACH_LEFT) {
                    Log.d(TAG, "Incoming message LongClicked");
                    showPopupMenu(true, view, qbChatMessage);
                }
            }
        }
    }

    private class ConversationNotificationClickListenerImpl implements ChatAdapterConf.ConversationNotificationClickListener {
        @Override
        public void onConversationNotificationClicked(View view, QBChatMessage qbChatMessage) {
            QBSettings.getInstance().init(ChatActCon.this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
            QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
            if (qbChatMessage != null && qbChatMessage.getProperty(DialogsManager.PROPERTY_NOTIFICATION_TYPE) != null) {
                if (isOpenFromCall) {
                    showProgressDialog(R.string.dlg_connecting_another_conversation);
                    bindCallServiceAndStartNewConversation();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startSuitableConversation(qbChatMessage);
                        }
                    }, CHANGE_CONFERENCE_ROOM_DELAY);
                } else {
                    startSuitableConversation(qbChatMessage);
                }
            }
        }
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


    private class PaginationListener implements ChatAdapterConf.PaginationHistoryListener {
        @Override
        public void onNextPage() {
            Log.w(TAG, "Download More");
            loadChatHistory();
        }
    }

    private class TypingStatusListener implements QBChatDialogTypingListener {
        private ArrayList<String> currentTypingUserNames = new ArrayList<>();
        private HashMap<Integer, Timer> usersTimerMap = new HashMap<>();

        @Override
        public void processUserIsTyping(final String dialogID, final Integer userID) {
            Integer currentUserID = currentUser.getId();
            QBSettings.getInstance().init(ChatActCon.this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
            QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
            if (dialogID != null && dialogID.equals(qbChatDialog.getDialogId()) && userID != null && !userID.equals(currentUserID)) {
                updateTypingInactivityTimer(dialogID, userID);
                QBUser user = getQBUsersHolder().getUserById(userID);
                String userName = user.getFullName();
                if (!TextUtils.isEmpty(userName) && !currentTypingUserNames.contains(userName)) {
                    currentTypingUserNames.add(userName);
                }
                typingStatus.setText(makeStringFromNames());
                typingStatus.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void processUserStopTyping(String dialogID, Integer userID) {
            Integer currentUserID = currentUser.getId();
            if (dialogID != null && dialogID.equals(qbChatDialog.getDialogId()) && userID != null && !userID.equals(currentUserID)) {
                stopInactivityTimer(userID);
                QBUser user = getQBUsersHolder().getUserById(userID);
                String userName = user.getFullName();
                if (!TextUtils.isEmpty(userName)) {
                    currentTypingUserNames.remove(userName);
                }
                typingStatus.setText(makeStringFromNames());
                if (TextUtils.isEmpty(makeStringFromNames())) {
                    typingStatus.setVisibility(View.GONE);
                }
            }
        }

        private void updateTypingInactivityTimer(final String dialogID, final Integer userID) {
            stopInactivityTimer(userID);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Log.d("Typing Status", "User with ID " + userID + " Did not refresh typing status. Processing stop typing");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            processUserStopTyping(dialogID, userID);
                        }
                    });
                }
            }, TYPING_STATUS_INACTIVITY_DELAY);
            usersTimerMap.put(userID, timer);
        }

        private void stopInactivityTimer(Integer userID) {
            if (usersTimerMap.get(userID) != null) {
                try {
                    usersTimerMap.get(userID).cancel();
                } catch (NullPointerException ignored) {

                } finally {
                    usersTimerMap.remove(userID);
                }
            }
        }

        private String makeStringFromNames() {
            String result = "";
            int usersCount = currentTypingUserNames.size();
            if (usersCount == 1) {
                String firstUser = currentTypingUserNames.get(0);

                if (firstUser.length() <= 20) {
                    result = firstUser + " " + getString(R.string.typing_postfix_singular);
                } else {
                    result = firstUser.subSequence(0, 19).toString() +
                            getString(R.string.typing_ellipsis) +
                            " " + getString(R.string.typing_postfix_singular);
                }

            } else if (usersCount == 2) {
                String firstUser = currentTypingUserNames.get(0);
                String secondUser = currentTypingUserNames.get(1);

                if ((firstUser + secondUser).length() > 20) {
                    if (firstUser.length() >= 10) {
                        firstUser = firstUser.subSequence(0, 9).toString() + getString(R.string.typing_ellipsis);
                    }

                    if (secondUser.length() >= 10) {
                        secondUser = secondUser.subSequence(0, 9).toString() + getString(R.string.typing_ellipsis);
                    }
                }
                result = firstUser + " and " + secondUser + " " + getString(R.string.typing_postfix_plural);

            } else if (usersCount > 2) {
                String firstUser = currentTypingUserNames.get(0);
                String secondUser = currentTypingUserNames.get(1);
                String thirdUser = currentTypingUserNames.get(2);

                if ((firstUser + secondUser + thirdUser).length() <= 20) {
                    result = firstUser + ", " + secondUser + " and " + thirdUser + " " + getString(R.string.typing_postfix_plural);
                } else if ((firstUser + secondUser).length() <= 20) {
                    result = firstUser + ", " + secondUser + " and " + (currentTypingUserNames.size() - 2) + " more " + getString(R.string.typing_postfix_plural);
                } else {
                    if (firstUser.length() >= 10) {
                        firstUser = firstUser.subSequence(0, 9).toString() + getString(R.string.typing_ellipsis);
                    }
                    if (secondUser.length() >= 10) {
                        secondUser = secondUser.subSequence(0, 9).toString() + getString(R.string.typing_ellipsis);
                    }
                    result = firstUser + ", " + secondUser +
                            " and " + (currentTypingUserNames.size() - 2) + " more " + getString(R.string.typing_postfix_plural);
                }
            }
            return result;
        }
    }

    private class TextInputWatcher implements TextWatcher {
        private Timer timer = new Timer();
        private long lastSendTime = 0;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (SystemClock.uptimeMillis() - lastSendTime > SEND_TYPING_STATUS_DELAY) {
                lastSendTime = SystemClock.uptimeMillis();
                try {
                    qbChatDialog.sendIsTypingNotification();
                } catch (XMPPException | SmackException.NotConnectedException e) {
                    if (e.getMessage() != null) {
                        Log.d(TAG, e.getMessage());
                    }
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            timer.cancel();
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        qbChatDialog.sendStopTypingNotification();
                    } catch (XMPPException | SmackException.NotConnectedException e) {
                        if (e.getMessage() != null) {
                            Log.d(TAG, e.getMessage());
                        }
                    }
                }
            }, TYPING_STATUS_DELAY);
        }
    }

    private class CallServiceConnection implements ServiceConnection {
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            QBSettings.getInstance().init(ChatActCon.this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
            QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
            CallServiceConf.CallServiceBinder binder = (CallServiceConf.CallServiceBinder) service;
            CallServiceConf callService = binder.getService();
            if (callService.currentSessionExist()) {
                callService.leaveCurrentSession();
                unbindService(callServiceConnection);
                callService.stopSelf();
                callService.stopForeground(true);
            }
        }

    }
}