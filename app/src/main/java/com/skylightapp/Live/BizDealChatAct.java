package com.skylightapp.Live;

import static com.skylightapp.BuildConfig.QUICKBLOX_ACCT_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_APP_ID;
import static com.skylightapp.BuildConfig.QUICKBLOX_AUTH_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_SECRET_KEY;
import static com.skylightapp.MarketClasses.UserSwipeProfileAdapter.EXTRA_SWIPE_VIEW_SOURCE;
import static com.skylightapp.MarketClasses.UserSwipeProfileAdapter.EXTRA_USER_PROFILE;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.model.QBAttachment;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.model.QBUser;
import com.skylightapp.Classes.App;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SpringInterpolator;
import com.skylightapp.MarketClasses.AttachmentPreviewAdapter;
import com.skylightapp.MarketClasses.AttachmentPreviewAdapterView;
import com.skylightapp.MarketClasses.ChatAdapter;
import com.skylightapp.MarketClasses.ChatHelper;
import com.skylightapp.MarketClasses.ImagePickHelper;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.MarketClasses.PushUtils;
import com.skylightapp.MarketClasses.QbChatDialogMessageListenerImp;
import com.skylightapp.MarketClasses.QbDialogHolderE;
import com.skylightapp.MarketClasses.QbDialogUtils;
import com.skylightapp.MarketClasses.StrokedTextView;
import com.skylightapp.MarketClasses.TimeUtilsEthernal;
import com.skylightapp.MarketClasses.Toaster;
import com.skylightapp.MarketClasses.UserProfileInfo;
import com.skylightapp.MarketClasses.UserProfileInfoHolder;
import com.skylightapp.MarketClasses.VerboseQbChatConnectionListener;
import com.skylightapp.MarketInterfaces.OnImagePickedListener;
import com.skylightapp.MarketInterfaces.PaginationHistoryListener;
import com.skylightapp.Markets.BaseActivity;
import com.skylightapp.Markets.ProfileActivity;
import com.skylightapp.R;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class BizDealChatAct extends BaseActivity implements OnImagePickedListener {
    public static final int START_T_ACTION = 466;
    private static final String TAG = BizDealChatAct.class.getSimpleName();
    private static final int REQUEST_CODE_ATTACHMENT = 721;

    private static final String PROPERTY_SAVE_TO_HISTORY = "save_to_history";
    public static final String EXTRA_DIALOG_ID = "dialogId";
    public static final String EXTRA_DELETE_DIALOG = "deleteDialog";
    public static final String EXTRA_DIALOG_DATA = "dialogData";
    private ProgressBar progressBar;
    private StickyListHeadersListView messagesListView;
    private AppCompatEditText messageEditText;

    private AppCompatTextView emptyChatLayout;
    private AppCompatTextView emptyChatMatchText;
    private AppCompatTextView emptyChatTimeText;
    private CircleImageView emptyChatCircleImageView;
    private StrokedTextView emptyChatDealDetails;
    private CircularProgressIndicator emptyChatIndicator;

    private LinearLayout attachmentPreviewContainerLayout;
    private Snackbar snackbar;

    private ChatAdapter chatAdapter;
    private AttachmentPreviewAdapter attachmentPreviewAdapter;
    private ConnectionListener chatConnectionListener;

    private QBChatDialog qbChatDialog ,qbChatDialog77;
    private ArrayList<QBChatMessage> unShownMessages;
    private ChatMessageListener chatMessageListener;

    private View.OnClickListener openProfileActivityOnClickListener;
    private int skipPagination = 0;
    private Bundle bundle, newBundle;
    private static final String PREF_NAME = "awajima";
    private static final String APPLICATION_ID = QUICKBLOX_APP_ID;   //QUICKBLOX_APP_ID
    private static final String AUTH_KEY = QUICKBLOX_AUTH_KEY;
    private static final String AUTH_SECRET = QUICKBLOX_SECRET_KEY;
    private static final String ACCOUNT_KEY = QUICKBLOX_ACCT_KEY;
    private SharedPreferences userPreferences;
    private PrefManager prefManager;
    private Gson gson,gson1,gson2;
    private String json,json1,json2;
    private Profile bizProfile;
    private QBUser qbUserOfBiz;
    private Customer customer;
    private MarketBusiness marketBusiness;
    private String SharedPrefUserName,SharedPrefUserPassword,SharedPrefUserMachine;


    public static void startForResult(Activity activity, int code, QBChatDialog dialogId) {
        Intent intent = new Intent(activity, BizDealChatAct.class);
        intent.putExtra(BizDealChatAct.EXTRA_DIALOG_ID, dialogId);
        activity.startActivityForResult(intent, code);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_host);
        bundle= new Bundle();
        Log.v(TAG, "onCreate ChatActivity on Thread ID = " + Thread.currentThread().getId());
        qbChatDialog = (QBChatDialog) getIntent().getSerializableExtra(EXTRA_DIALOG_ID);
        Log.v(TAG, "deserialized dialog = " + qbChatDialog);
        qbChatDialog.initForChat(QBChatService.getInstance());
        gson= new Gson();
        gson1= new Gson();
        bizProfile= new Profile();
        qbUserOfBiz= new QBUser();
        customer= new Customer();
        marketBusiness= new MarketBusiness();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        bizProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson1.fromJson(json1, MarketBusiness.class);

        json2 = userPreferences.getString("LastQBUserUsed", "");
        qbUserOfBiz = gson2.fromJson(json2, QBUser.class);

        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");

        chatMessageListener = new ChatMessageListener();
        qbChatDialog.addMessageListener(chatMessageListener);
        initChatConnectionListener();
        initViews();
        initChat();
        bundle=getIntent().getExtras();

        if(bundle !=null){
            qbChatDialog77=bundle.getParcelable("QBChatDialog");
        }

    }
    private void sendDialogId() {
        Intent result = new Intent();
        result.putExtra(EXTRA_DIALOG_ID, qbChatDialog.getDialogId());
        setResult(RESULT_OK, result);
    }

    @Override
    public void onImagePicked(int requestCode, File file) {
        switch (requestCode) {
            case REQUEST_CODE_ATTACHMENT:
                attachmentPreviewAdapter.add(file);
                break;
        }
    }

    @Override
    public void onImagePickError(int requestCode, Exception e) {
        showErrorSnackbar(0, e, null);
    }

    @Override
    public void onImagePickClosed(int requestCode) {
    }

    @Override
    protected View getSnackbarAnchorView() {
        return findViewById(R.id.list_chat_messages);
    }

    public void onSendChatClick(View view) {
        int totalAttachmentsCount = attachmentPreviewAdapter.getCount();
        Collection<QBAttachment> uploadedAttachments = attachmentPreviewAdapter.getUploadedAttachments();
        if (!uploadedAttachments.isEmpty()) {
            if (uploadedAttachments.size() == totalAttachmentsCount) {
                for (QBAttachment attachment : uploadedAttachments) {
                    sendChatMessage(null, attachment);
                }
            } else {
                Toaster.shortToast(R.string.chat_wait_for_attachments_to_upload);
            }
        }

        String text = messageEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(text)) {
            sendChatMessage(text, null);
        }
    }

    public void onAttachmentsClick(View view) {
        new ImagePickHelper().pickAnImage(this, REQUEST_CODE_ATTACHMENT);
    }

    public void showMessage(QBChatMessage message) {
        if (emptyChatLayout.getVisibility() == View.VISIBLE)
            emptyChatLayout.setVisibility(View.GONE);
        if (chatAdapter != null) {
            chatAdapter.add(message);
            scrollMessageListDown();
        } else {
            if (unShownMessages == null) {
                unShownMessages = new ArrayList<>();
            }
            unShownMessages.add(message);
        }
    }

    private void initViews() {
        actionBar.setDisplayHomeAsUpEnabled(true);
        newBundle= new Bundle();

        messagesListView = _findViewById(R.id.deal_chat_messages);
        messageEditText = _findViewById(R.id.edit_chat_deal);
        progressBar = _findViewById(R.id.progress_deal_chat);

        emptyChatLayout = _findViewById(R.id.empty_deal_chat_);
        emptyChatMatchText = _findViewById(R.id.empty_chat_text);
        emptyChatTimeText = _findViewById(R.id.empty_chat_date);
        emptyChatCircleImageView = _findViewById(R.id.empty_chat_circle_image);
        emptyChatDealDetails = _findViewById(R.id.empty_chat_layout_deal_value);
        emptyChatIndicator = _findViewById(R.id.empty_chat_ind);

        attachmentPreviewContainerLayout = _findViewById(R.id.deal_attachment_preview_container);

        attachmentPreviewAdapter = new AttachmentPreviewAdapter(this,
                new AttachmentPreviewAdapter.OnAttachmentCountChangedListener() {
                    @Override
                    public void onAttachmentCountChanged(int count) {
                        attachmentPreviewContainerLayout.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
                    }
                },
                new AttachmentPreviewAdapter.OnAttachmentUploadErrorListener() {
                    @Override
                    public void onAttachmentUploadError(QBResponseException e) {
                        showErrorSnackbar(0, e, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onAttachmentsClick(v);
                            }
                        });
                    }
                });
        AttachmentPreviewAdapterView previewAdapterView = _findViewById(R.id.adapter_preview);
        previewAdapterView.setAdapter(attachmentPreviewAdapter);

        openProfileActivityOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                UserProfileInfo userProfileInfo = UserProfileInfoHolder.getInstance().getProfileInfo(qbChatDialog.getRecipientId());
                //userProfileInfo.setMatchValue(qbChatDialog.getCustomData().getInteger("matchValue"));
                newBundle.putParcelable("UserProfileInfo",userProfileInfo);
                intent.putExtra(EXTRA_USER_PROFILE, userProfileInfo);
                intent.putExtra(EXTRA_SWIPE_VIEW_SOURCE, false);
                intent.putExtras(newBundle);
                startActivity(intent);
            }
        };
    }

    private void sendChatMessage(String text, QBAttachment attachment) {
        QBChatMessage chatMessage = new QBChatMessage();
        if (attachment != null) {
            chatMessage.addAttachment(attachment);
        } else {
            chatMessage.setBody(text);
        }
        chatMessage.setProperty(PROPERTY_SAVE_TO_HISTORY, "1");
        chatMessage.setDateSent(System.currentTimeMillis() / 1000);
        chatMessage.setMarkable(true);

        try {
            qbChatDialog.sendMessage(chatMessage);
            PushUtils.sendPushAboutMessage(qbChatDialog, text);
            showMessage(chatMessage);

            if (attachment != null) {
                attachmentPreviewAdapter.remove(attachment);
            } else {
                messageEditText.setText("");
            }
        } catch (SmackException.NotConnectedException e) {
            Log.w(TAG, e);
            Toaster.shortToast("Can't send a message, You are not connected to chat");
        }
    }

    private void initChat() {
        switch (qbChatDialog.getType()) {
            case PRIVATE:
                loadDialogUsers();
                break;

            default:
                Toaster.shortToast(String.format("%s %s", getString(R.string.chat_unsupported_type), qbChatDialog.getType().name()));
                finish();
                break;
        }
    }


    private void releaseChat() {
        qbChatDialog.removeMessageListrener(chatMessageListener);
    }

    private void updateDialog(final ArrayList<QBUser> selectedUsers) {
        ChatHelper.getInstance().updateDialogUsers(qbChatDialog, selectedUsers,
                new QBEntityCallback<QBChatDialog>() {
                    @Override
                    public void onSuccess(QBChatDialog dialog, Bundle args) {
                        qbChatDialog = dialog;
                        loadDialogUsers();
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        showErrorSnackbar(R.string.chat_info_add_people_error, e,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        updateDialog(selectedUsers);
                                    }
                                });
                    }
                }
        );
    }

    private void loadDialogUsers() {
        ChatHelper.getInstance().getUsersFromDialog(qbChatDialog, new QBEntityCallback<ArrayList<QBUser>>() {
            @Override
            public void onSuccess(ArrayList<QBUser> users, Bundle bundle) {
                setChatNameToActionBar();
                loadChatHistory();
            }

            @Override
            public void onError(QBResponseException e) {
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
        final ActionBar abar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_chat, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        viewActionBar.findViewById(R.id.actionbar_layout).setOnClickListener(openProfileActivityOnClickListener);
        TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText(QbDialogUtils.getDialogName(qbChatDialog));
        CircleImageView actionBarImageView = viewActionBar.findViewById(R.id.actionbar_imageview);

        String photoLink = null;
        UserProfileInfo userProfileInfo = UserProfileInfoHolder.getInstance().getProfileInfo(qbChatDialog.getRecipientId());
        if (userProfileInfo != null)
            photoLink = UserProfileInfoHolder.getInstance().getProfileInfo(qbChatDialog.getRecipientId()).getPhotoLinks().get(0);
        else {
            photoLink = QbDialogUtils.getQBUserPhotos(qbChatDialog).get(0);
        }
        App.getImageLoader().downloadImage(photoLink, actionBarImageView);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setIcon(R.color.transparent);
        abar.setHomeButtonEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.primary_purple), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    @SuppressLint("SetTextI18n")
    private void showEmptyChatView() {
        String photoLink = UserProfileInfoHolder.getInstance().getProfileInfo(qbChatDialog.getRecipientId()).getPhotoLinks().get(0);
        emptyChatMatchText.setText(String.format("%s %s", getString(R.string.chat_matched_with_message), QbDialogUtils.getDialogName(qbChatDialog)));

        emptyChatTimeText.setText(TimeUtilsEthernal.getTimeSpan(qbChatDialog.getCreatedAt()));
        App.getImageLoader().downloadImage(photoLink, emptyChatCircleImageView);
        emptyChatCircleImageView.setOnClickListener(openProfileActivityOnClickListener);
        emptyChatIndicator.setProgress(qbChatDialog.getCustomData().getInteger("matchValue"), true);
        emptyChatDealDetails.setText(qbChatDialog.getCustomData().getInteger("matchValue").toString() + "%");

        Animation emptyChatAnim = AnimationUtils.loadAnimation(App.getAppContext(), R.anim.empty_chat_elements);
        emptyChatAnim.setInterpolator(new SpringInterpolator(0.4f));
        emptyChatLayout.setVisibility(View.VISIBLE);
        emptyChatLayout.startAnimation(emptyChatAnim);
    }

    private void loadChatHistory() {
        ChatHelper.getInstance().loadChatHistory(qbChatDialog, skipPagination, new QBEntityCallback<ArrayList<QBChatMessage>>() {
            @Override
            public void onSuccess(ArrayList<QBChatMessage> messages, Bundle args) {
                Collections.reverse(messages);
                if (chatAdapter == null) {
                    chatAdapter = new ChatAdapter(BizDealChatAct.this, qbChatDialog, messages);
                    chatAdapter.setPaginationHistoryListener(new PaginationHistoryListener() {
                        @Override
                        public void downloadMore() {
                            loadChatHistory();
                        }
                    });
                    chatAdapter.setOnItemInfoExpandedListener(new ChatAdapter.OnItemInfoExpandedListener() {
                        @Override
                        public void onItemInfoExpanded(final int position) {
                            if (isLastItem(position)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        messagesListView.setSelection(position);
                                    }
                                });
                            } else {
                                messagesListView.smoothScrollToPosition(position);
                            }
                        }

                        private boolean isLastItem(int position) {
                            return position == chatAdapter.getCount() - 1;
                        }
                    });
                    if (unShownMessages != null && !unShownMessages.isEmpty()) {
                        List<QBChatMessage> chatList = chatAdapter.getList();
                        for (QBChatMessage message : unShownMessages) {
                            if (!chatList.contains(message)) {
                                chatAdapter.add(message);
                            }
                        }
                    }
                    messagesListView.setAdapter(chatAdapter);
                    messagesListView.setAreHeadersSticky(false);
                    messagesListView.setDivider(null);
                } else {
                    chatAdapter.addList(messages);
                    messagesListView.setSelection(messages.size());
                }
                progressBar.setVisibility(View.GONE);
                if (messagesListView.getCount() == 0) {
                    showEmptyChatView();
                }
            }

            @Override
            public void onError(QBResponseException e) {
                progressBar.setVisibility(View.GONE);
                skipPagination -= ChatHelper.CHAT_HISTORY_ITEMS_PER_PAGE;
                snackbar = showErrorSnackbar(R.string.connection_error, e, null);
            }
        });
        skipPagination += ChatHelper.CHAT_HISTORY_ITEMS_PER_PAGE;
    }

    private void scrollMessageListDown() {
        messagesListView.setSelection(messagesListView.getCount() - 1);
    }

    private void deleteChat() {
        ChatHelper.getInstance().deleteDialog(qbChatDialog, new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid, Bundle bundle) {
                Intent intentDeleteDialog = new Intent();
                intentDeleteDialog.putExtra(EXTRA_DELETE_DIALOG, true);
                intentDeleteDialog.putExtra(EXTRA_DIALOG_DATA, qbChatDialog);
                setResult(RESULT_OK, intentDeleteDialog);
                finish();
            }

            @Override
            public void onError(QBResponseException e) {
                showErrorSnackbar(R.string.dialogs_deletion_error, e,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteChat();
                            }
                        });
            }
        });
    }

    private void initChatConnectionListener() {
        chatConnectionListener = new VerboseQbChatConnectionListener(getSnackbarAnchorView()) {
            @Override
            public void reconnectionSuccessful() {
                super.reconnectionSuccessful();
                skipPagination = 0;
                switch (qbChatDialog.getType()) {
                    case GROUP:
                        chatAdapter = null;
                        // Join active room if we're in Group Chat
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //  joinGroupChat();
                            }
                        });
                        break;
                }
            }
        };
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
        getMenuInflater().inflate(R.menu.activity_chat, menu);

        MenuItem menuItemDelete = menu.findItem(R.id.menu_chat_action_delete);
        menuItemDelete.setVisible(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

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
        releaseChat();
        sendDialogId();

        super.onBackPressed();
    }

    public class ChatMessageListener extends QbChatDialogMessageListenerImp {
        @Override
        public void processMessage(String s, QBChatMessage qbChatMessage, Integer integer) {
            showMessage(qbChatMessage);
        }
    }

    public void redeemDo(View view) {
    }

    public void getDiamondHistory(View view) {
    }

    public void goToRedeemReq(View view) {
    }

    public void transferDToWallet(View view) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("action", START_T_ACTION);
        setResult(RESULT_OK,returnIntent);
        finish();
    }
}