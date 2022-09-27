package com.skylightapp.Markets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.users.model.QBUser;
import com.skylightapp.LoginActivity;
import com.skylightapp.MarketClasses.BaseAsyncTaskCon;
import com.skylightapp.MarketClasses.DialogsAdapter;
import com.skylightapp.MarketClasses.DialogsManager;
import com.skylightapp.MarketClasses.ToastUtilsCon;
import com.skylightapp.R;

import org.jivesoftware.smack.SmackException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ForwardToActCon extends BaseActCon {
    private static final String TAG = ForwardToActCon.class.getSimpleName();

    private static final String EXTRA_FORWARD_MESSAGE = "extra_forward_message";

    private QBRequestGetBuilder requestBuilder;
    private SwipyRefreshLayout refreshLayout;
    private QBChatMessage originMessage;
    private DialogsAdapter dialogsAdapter;
    private DialogsListener dialogsListener = new DialogsListener(TAG);

    private QBUser currentUser;
    private Menu menu;

    private Boolean isProcessingResultInProgress = false;
    private boolean hasMoreDialogs = true;
    private Set<QBChatDialog> loadedDialogs = new HashSet<>();

    public static void start(Context context, QBChatMessage messageToForward) {
        Intent intent = new Intent(context, ForwardToActCon.class);
        intent.putExtra(EXTRA_FORWARD_MESSAGE, messageToForward);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_forward_to_act_con);
        getDialogsManager().addDialogsCallbacks(dialogsListener);
        if (!getChatHelper().isLogged()) {
            reloginToChat();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.forward_to);
            getSupportActionBar().setSubtitle(getString(R.string.dialogs_actionmode_subtitle, "0"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getChatHelper().getCurrentUser() != null) {
            currentUser = getChatHelper().getCurrentUser();
        } else {
            finish();
        }

        originMessage = (QBChatMessage) getIntent().getSerializableExtra(EXTRA_FORWARD_MESSAGE);
        requestBuilder = new QBRequestGetBuilder();
        requestBuilder.setLimit(DialogsActCon.DIALOGS_PER_PAGE);
        requestBuilder.setSkip(0);
        initUi();
    }

    @Override
    public void onResumeFinished() {
        if (getChatHelper().isLogged()) {
            loadDialogsFromQb();
        } else {
            reloginToChat();
        }
    }

    private void reloginToChat() {
        showProgressDialog(R.string.dlg_loading);
        QBUser appServerUser = getChatHelper().getCurrentUser();
        if (appServerUser != null) {
            getChatHelper().loginToChat(appServerUser, new QBEntityCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid, Bundle bundle) {
                    Log.d(TAG, "Relogin Successful");
                    loadDialogsFromQb();
                }

                @Override
                public void onError(QBResponseException e) {
                    Log.d(TAG, "Relogin Failed " + e.getMessage());
                    LoginActivity.start(ForwardToActCon.this);
                    hideProgressDialog();
                    finish();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDialogsManager().removeDialogsCallbacks(dialogsListener);
    }

    private void initUi() {
        LinearLayout emptyHintLayout = findViewById(R.id.ll_chat_empty);
        ListView dialogsListView = findViewById(R.id.list_dialogs_chats);
        refreshLayout = findViewById(R.id.swipy_refresh_layout);

        List<QBChatDialog> dialogs = new ArrayList<>(getQBDialogsHolder().getDialogs().values());
        dialogsAdapter = new DialogsAdapter(this, dialogs);
        dialogsAdapter.prepareToSelect();

        dialogsListView.setEmptyView(emptyHintLayout);
        dialogsListView.setAdapter(dialogsAdapter);

        dialogsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                QBChatDialog selectedDialog = (QBChatDialog) parent.getItemAtPosition(position);
                dialogsAdapter.toggleSelection(selectedDialog);
                menu.getItem(0).setVisible(dialogsAdapter.getSelectedItems().size() >= 1);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setSubtitle(getString(R.string.dialogs_actionmode_subtitle, String.valueOf(dialogsAdapter.getSelectedItems().size())));
                }
            }
        });

        refreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                loadDialogsFromQb();
            }
        });

        refreshLayout.setColorSchemeResources(R.color.dark_yellow, R.color.random_color_2, R.color.random_color_3, R.color.random_color_7);
        dialogsAdapter.clearSelection();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_activity_forward, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isProcessingResultInProgress) {
            return super.onOptionsItemSelected(item);
        }
        if (item.getItemId() == R.id.menu_send) {
            showProgressDialog(R.string.dlg_sending);
            new ForwardedMessageSenderAsyncTaskCon(this, dialogsAdapter.getSelectedItems()).execute();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void sendForwardedMessage(List<QBChatDialog> dialogs) {
        for (QBChatDialog dialog : dialogs) {
            try {
                QBChatMessage messageToForward = new QBChatMessage();
                messageToForward.setSaveToHistory(true);
                messageToForward.setDateSent(System.currentTimeMillis() / 1000);
                messageToForward.setMarkable(true);

                messageToForward.setAttachments(originMessage.getAttachments());
                if (originMessage.getBody().equals("null")) {
                    messageToForward.setBody(null);
                } else {
                    messageToForward.setBody(originMessage.getBody());
                }

                String senderName = "";
                if (originMessage.getSenderId().equals(currentUser.getId())) {
                    senderName = currentUser.getFullName();
                } else {
                    QBUser sender = getQBUsersHolder().getUserById(originMessage.getSenderId());
                    if (sender != null) {
                        senderName = sender.getFullName();
                    }
                }
                messageToForward.setProperty(ChatActCon.PROPERTY_FORWARD_USER_NAME, senderName);
                dialog.sendMessage(messageToForward);
            } catch (SmackException.NotConnectedException e) {
                Log.d(TAG, "Send Forwarded Message Exception: " + e.getMessage());
                ToastUtilsCon.shortToast(ForwardToActCon.this, R.string.error_forwarding_not_connected);
            }
        }
        disableProgress();
        ToastUtilsCon.shortToast(ForwardToActCon.this, "Forwarding Complete");
        finish();
    }

    private void loadDialogsFromQb() {
        isProcessingResultInProgress = true;
        showProgressDialog(R.string.dlg_loading);

        getChatHelper().getDialogs(requestBuilder, new QBEntityCallback<ArrayList<QBChatDialog>>() {
            @Override
            public void onSuccess(ArrayList<QBChatDialog> dialogs, Bundle bundle) {
                if (dialogs.size() < DialogsActCon.DIALOGS_PER_PAGE) {
                    hasMoreDialogs = false;
                }
                loadedDialogs.addAll(dialogs);
                getQBDialogsHolder().addDialogs(dialogs);
                updateDialogsAdapter();
                requestBuilder.setSkip(loadedDialogs.size());
                if (hasMoreDialogs) {
                    loadDialogsFromQb();
                }
                disableProgress();
            }

            @Override
            public void onError(QBResponseException e) {
                disableProgress();
                dialogsAdapter.clearSelection();
                ToastUtilsCon.shortToast(ForwardToActCon.this, e.getMessage());
            }
        });
    }

    private void disableProgress() {
        isProcessingResultInProgress = false;
        hideProgressDialog();
        refreshLayout.setRefreshing(false);
    }

    private void updateDialogsAdapter() {
        ArrayList<QBChatDialog> listDialogs = new ArrayList<>(getQBDialogsHolder().getDialogs().values());
        dialogsAdapter.updateList(listDialogs);
        dialogsAdapter.prepareToSelect();
    }

    private static class ForwardedMessageSenderAsyncTaskCon extends BaseAsyncTaskCon {
        private WeakReference<ForwardToActCon> activityRef;
        private List<QBChatDialog> dialogs;

        ForwardedMessageSenderAsyncTaskCon(ForwardToActCon forwardToActivity, List<QBChatDialog> dialogs) {
            activityRef = new WeakReference<>(forwardToActivity);
            this.dialogs = dialogs;
        }

        @Override
        public Object performInBackground(Object[] objects) throws Exception {
            activityRef.get().getChatHelper().join(dialogs);
            return null;
        }

        @Override
        public void onResult(Object o) {
            if (activityRef.get() != null) {
                activityRef.get().sendForwardedMessage(dialogs);
            }
        }

        @Override
        public void onException(Exception e) {
            super.onException(e);
            Log.d("Dialog Joiner Task", "Error: $e");
            ToastUtilsCon.shortToast(activityRef.get(), "Error: " + e.getMessage());
        }
    }

    private class DialogsListener implements DialogsManager.DialogsCallbacks {
        private String tag;

        DialogsListener(String tag) {
            this.tag = tag;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 53 * hash + tag.hashCode();
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            boolean equals;
            if (obj instanceof DialogsListener) {
                equals = TAG.equals(((DialogsListener) obj).tag);
            } else {
                equals = super.equals(obj);
            }
            return equals;
        }

        @Override
        public void onDialogCreated(QBChatDialog chatDialog) {
            loadDialogsFromQb();
        }

        @Override
        public void onDialogUpdated(String chatDialog) {
            updateDialogsAdapter();
        }

        @Override
        public void onNewDialogLoaded(QBChatDialog chatDialog) {
            updateDialogsAdapter();
        }

    }
}