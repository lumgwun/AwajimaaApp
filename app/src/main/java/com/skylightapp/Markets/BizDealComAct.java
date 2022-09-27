package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.MarketClasses.AttachmentPreviewAdapter;
import com.skylightapp.MarketClasses.BizDealChatAdapter;
import com.skylightapp.MarketClasses.BizDealChatMessage;
import com.skylightapp.MarketClasses.ChatAdapter;
import com.skylightapp.MarketClasses.ChatAdapterConf;
import com.skylightapp.MarketClasses.ChatHelper;
import com.skylightapp.MarketClasses.OpponentsFromCallAdapter;
import com.skylightapp.MarketClasses.QbDialogHolderE;
import com.skylightapp.MarketClasses.StrokedTextView;
import com.skylightapp.R;
import com.skylightapp.VideoChat.OppsFromCallAdap;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.chat.ChatMessageListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import static com.skylightapp.BuildConfig.QUICKBLOX_ACCT_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_APP_ID;
import static com.skylightapp.BuildConfig.QUICKBLOX_AUTH_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_SECRET_KEY;

public class BizDealComAct extends AppCompatActivity  {
    public static final String PROPERTY_FORWARD_USER_NAME = "origin_sender_name";
    private static boolean isNewDialog;
    private static int code=908;

    private static final String TAG = BizDealComAct.class.getSimpleName();
    private static final int REQUEST_CODE_ATTACHMENT = 721;

    private static final String PROPERTY_SAVE_TO_HISTORY = "save_to_history";
    public static final String EXTRA_DIALOG_ID = "dialogId";
    public static final String EXTRA_DELETE_DIALOG = "deleteDialog";
    public static final String EXTRA_DIALOG_DATA = "dialogData";
    public static final int REQUEST_CODE_SELECT_PEOPLE = 752;
    public static final String EXTRA_IS_NEW_DIALOG = "isNewDialog";
    public static final String IS_IN_BACKGROUND = "is_in_background";

    public static final String ORDER_RULE = "order";
    public static final String ORDER_VALUE_UPDATED_AT = "desc string updated_at";

    public static final long TYPING_STATUS_DELAY = 2000;
    public static final long TYPING_STATUS_INACTIVITY_DELAY = 10000;
    private static final long SEND_TYPING_STATUS_DELAY = 3000;
    public static final int MAX_ATTACHMENTS_COUNT = 1;
    public static final int MAX_MESSAGE_SYMBOLS_LENGTH = 1000;
    private static final String APPLICATION_ID = QUICKBLOX_APP_ID;   //QUICKBLOX_APP_ID
    private static final String AUTH_KEY = QUICKBLOX_AUTH_KEY;
    private static final String AUTH_SECRET = QUICKBLOX_SECRET_KEY;
    private static final String ACCOUNT_KEY = QUICKBLOX_ACCT_KEY;
    private static final String SERVER_URL = "";

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


    private AttachmentPreviewAdapter attachmentPreviewAdapter;
    private ConnectionListener chatConnectionListener;

    private QBChatDialog qbChatDialog;
    private ArrayList<QBChatMessage> unShownMessages;
    private ChatMessageListener chatMessageListener;
    List<BizDealChatMessage> chatsList = new ArrayList<>();

    private RecyclerView recyclerViewQBChatMessage;
    private RecyclerView recyclerViewQBVideoMessage;
    private RecyclerView recyclerViewQBConfMessage;
    private RecyclerView recyclerViewBizDeal;
    private RecyclerView recyclerViewBizDealAttachments;
    private RecyclerView recyclerViewTranxChat;
    private RecyclerView recyclerViewOppoFromCall;
    private RecyclerView recyclerViewOppoFromVideo;

    private ChatAdapter chatAdapter;
    private BizDealChatAdapter bizDealChatAdapter;
    private ChatAdapterConf chatAdapterConf;
    private  BDTranxChatAdapter bdTranxChatAdapter;
    private OpponentsFromCallAdapter opponentsFromCallAdapter;
    private OppsFromCallAdap oppsFromVideoCallAdap;

    private View.OnClickListener openProfileActivityOnClickListener;
    private int skipPagination = 0;
    private SharedPreferences userPreferences;
    private PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1;
    private  Bundle messageBundle;
    private  int senderProfileID,adminDepositID,SharedPrefAdminID;

    public static void startForResult(Activity activity, int code, QBChatDialog dialogId) {
        Intent intent = new Intent(activity, BizDealComAct.class);
        intent.putExtra(BizDealComAct.EXTRA_DIALOG_ID, dialogId);
        activity.startActivityForResult(intent, code);
    }
    public static void start(Context context) {
        Intent intent = new Intent(context, BizDealComAct.class);
        context.startActivity(intent);
    }

    public static void start(Context context, boolean b) {
        b=true;
        Intent intent = new Intent(context, BizDealComAct.class);
        intent.putExtra("boolean",b);
        context.startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_biz_deal_com);
        FirebaseApp.initializeApp(this);
        QBSettings.getInstance().init(this, APPLICATION_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);


    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        if (qbChatDialog != null) {
            outState.putString(EXTRA_DIALOG_ID, qbChatDialog.getDialogId());
        }
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (qbChatDialog == null) {
            qbChatDialog = QbDialogHolderE.getInstance().getChatDialogById(savedInstanceState.getString(EXTRA_DIALOG_ID));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.act_com, menu);

        //MenuItem menuItemDelete = menu.findItem(R.id.menu_chat_action_delete);
        //menuItemDelete.setVisible(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ChatHelper.getInstance().addConnectionListener(chatConnectionListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ChatHelper.getInstance().removeConnectionListener(chatConnectionListener);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

}