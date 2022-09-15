package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import com.quickblox.conference.ConferenceSession;
import com.quickblox.conference.WsException;
import com.quickblox.conference.WsHangUpException;
import com.quickblox.conference.WsNoResponseException;
import com.quickblox.conference.callbacks.ConferenceSessionCallbacks;
import com.quickblox.videochat.webrtc.BaseSession;
import com.quickblox.videochat.webrtc.QBRTCScreenCapturer;
import com.quickblox.videochat.webrtc.callbacks.QBRTCSessionStateCallback;
import com.quickblox.videochat.webrtc.view.QBRTCVideoTrack;
import com.skylightapp.Conference.DialogsActivity;
import com.skylightapp.MarketClasses.CallServiceConf;
import com.skylightapp.MarketClasses.WebRtcSessionManager;
import com.skylightapp.MarketInterfaces.ConstsInterface;
import com.skylightapp.MarketInterfaces.ReconnectionCallback;
import com.skylightapp.R;

import org.webrtc.CameraVideoCapturer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.os.Bundle;

public class CallActivityCon extends BaseActivity implements QBRTCSessionStateCallback<ConferenceSession>, ConferenceSessionCallbacks,
        ConversationFragmentCallback, ScreenShareFragment.OnSharingEvents {
    private static final String TAG = CallActivityCon.class.getSimpleName();
    private static final String ICE_FAILED_REASON = "ICE failed";
    private static final int REQUEST_CODE_OPEN_CONVERSATION_CHAT = 183;
    private static final int REQUEST_CODE_MANAGE_GROUP = 132;

    private ConferenceSession currentSession;
    private SharedPreferences settingsSharedPref;
    private String currentDialogID;
    private String currentRoomID;
    private String currentRoomTitle;
    private ServiceConnection callServiceConnection;

    private ArrayList<CallServiceConf.CurrentCallStateCallback> currentCallStateCallbackList = new ArrayList<>();
    private volatile boolean connectedToJanus;
    private CallServiceConf callService;
    private final Set<ReconnectionCallback> reconnectionCallbacks = new HashSet<>();
    private final ReconnectionListenerImpl reconnectionListener = new ReconnectionListenerImpl(TAG);
    private LinearLayout reconnectingLayout;

    public static void start(Context context, String roomID, String roomTitle, String dialogID,
                             List<Integer> occupants, boolean listenerRole) {
        Intent intent = new Intent(context, CallActivityCon.class);
        intent.putExtra(ConstsInterface.EXTRA_ROOM_ID, roomID);
        intent.putExtra(ConstsInterface.EXTRA_ROOM_TITLE, roomTitle);
        intent.putExtra(ConstsInterface.EXTRA_DIALOG_ID, dialogID);
        intent.putExtra(ConstsInterface.EXTRA_DIALOG_OCCUPANTS, (Serializable) occupants);
        intent.putExtra(ConstsInterface.EXTRA_AS_LISTENER, listenerRole);

        context.startActivity(intent);
        CallServiceConf.start(context, roomID, roomTitle, dialogID, occupants, listenerRole);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, CallActivityCon.class);
        context.startActivity(intent);
    }

    @Override
    public void addReconnectionCallback(ReconnectionCallback reconnectionCallback) {
        reconnectionCallbacks.add(reconnectionCallback);
    }

    @Override
    public void removeReconnectionCallback(ReconnectionCallback reconnectionCallback) {
        reconnectionCallbacks.remove(reconnectionCallback);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_call_con);
        currentRoomID = getIntent().getStringExtra(ConstsInterface.EXTRA_ROOM_ID);
        currentDialogID = getIntent().getStringExtra(ConstsInterface.EXTRA_DIALOG_ID);
        currentRoomTitle = getIntent().getStringExtra(ConstsInterface.EXTRA_ROOM_TITLE);
        PreferenceManager.setDefaultValues(this, R.xml.preferences_video, false);
        PreferenceManager.setDefaultValues(this, R.xml.preferences_audio, false);

        reconnectingLayout = findViewById(R.id.llReconnecting);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow();
            w.setStatusBarColor(ContextCompat.getColor(this, R.color.color_new_blue));

            // TODO: To set fullscreen style in a call:
            //w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    private void initScreen() {
        SettingsUtils.setSettingsStrategy(settingsSharedPref, CallActivityCon.this);

        WebRtcSessionManager sessionManager = WebRtcSessionManager.getInstance();
        if (sessionManager.getCurrentSession() == null) {
            //we have already currentSession == null, so it's no reason to do further initialization
            finish();
            return;
        }
        currentSession = sessionManager.getCurrentSession();
        initListeners(currentSession);

        if (callService != null && callService.isSharingScreenState()) {
            if (callService.getReconnectionState() == CallServiceConf.ReconnectionState.COMPLETED) {
                QBRTCScreenCapturer.requestPermissions(this);
            } else {
                startScreenSharing(null);
            }
        } else {
            startConversationFragment();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        settingsSharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        bindCallService();
        if (callService != null) {
            switch (callService.getReconnectionState()) {
                case COMPLETED:
                    reconnectingLayout.setVisibility(View.GONE);
                    for (ReconnectionCallback reconnectionCallback : reconnectionCallbacks) {
                        reconnectionCallback.completed();
                    }
                    break;
                case IN_PROGRESS:
                    reconnectingLayout.setVisibility(View.VISIBLE);
                    for (ReconnectionCallback reconnectionCallback : reconnectionCallbacks) {
                        reconnectionCallback.inProgress();
                    }
                    break;
                case FAILED:
                    ToastUtils.shortToast(getApplicationContext(), R.string.reconnection_failed);
                    callService.leaveCurrentSession();
                    finish();
                    break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (callServiceConnection != null) {
            unbindService(callServiceConnection);
            callServiceConnection = null;
        }

        callService.unsubscribeReconnectionListener(reconnectionListener);
        removeListeners();
    }

    @Override
    public void finish() {
        String dialogID = currentDialogID;
        if (callService != null) {
            dialogID = callService.getDialogID();
        }
        DialogsActivity.start(this, dialogID);
        Log.d(TAG, "Starting Dialogs Activity to open dialog with ID : " + dialogID);

        Log.d(TAG, "finish CallActivity");
        super.finish();
    }

    private void bindCallService() {
        callServiceConnection = new CallServiceConnection();
        Intent intent = new Intent(this, CallServiceConf.class);
        bindService(intent, callServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void leaveCurrentSession() {
        callService.leaveCurrentSession();
        finish();
    }

    private void initListeners(ConferenceSession session) {
        if (session != null) {
            Log.d(TAG, "Init new ConferenceSession");
            this.currentSession.addSessionCallbacksListener(CallActivityCon.this);
            this.currentSession.addConferenceSessionListener(CallActivityCon.this);
        }
    }

    private void releaseCurrentSession() {
        Log.d(TAG, "Release current session");
        if (currentSession != null) {
            leaveCurrentSession();
            removeListeners();
            this.currentSession = null;
        }
    }

    private void removeListeners() {
        if (currentSession != null) {
            this.currentSession.removeSessionCallbacksListener(CallActivityCon.this);
            this.currentSession.removeConferenceSessionListener(CallActivityCon.this);
        }
    }

    // ---------------Chat callback methods implementation  ----------------------//

    @Override
    public void onConnectionClosedForUser(ConferenceSession session, Integer userID) {
        Log.d(TAG, "QBRTCSessionStateCallbackImpl onConnectionClosedForUser userID=" + userID);
    }

    @Override
    public void onConnectedToUser(ConferenceSession session, final Integer userID) {
        Log.d(TAG, "onConnectedToUser userID= " + userID + " sessionID= " + session.getSessionID());
    }

    @Override
    public void onStateChanged(ConferenceSession session, BaseSession.QBRTCSessionState state) {
        if (BaseSession.QBRTCSessionState.QB_RTC_SESSION_CONNECTED.equals(state)) {
            connectedToJanus = true;
            Log.d(TAG, "onStateChanged and begin subscribeToPublishersIfNeed");
        }
    }

    @Override
    public void onDisconnectedFromUser(ConferenceSession session, Integer userID) {
        Log.d(TAG, "QBRTCSessionStateCallbackImpl onDisconnectedFromUser userID=" + userID);
    }

    private void startConversationFragment() {
        ArrayList<Integer> opponentsIDsList = callService.getOpponentsIDsList();
        boolean asListenerRole = callService.isListenerRole();
        boolean sharingScreenState = callService.isSharingScreenState();
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(ConstsInterface.EXTRA_DIALOG_OCCUPANTS, opponentsIDsList);
        bundle.putBoolean(ConstsInterface.EXTRA_AS_LISTENER, asListenerRole);
        bundle.putBoolean("from_screen_sharing", sharingScreenState);
        bundle.putString(ConstsInterface.EXTRA_ROOM_TITLE, currentRoomTitle);
        bundle.putString(ConstsInterface.EXTRA_ROOM_ID, currentRoomID);
        ConversationFragment conversationFragment = new ConversationFragment();
        conversationFragment.setArguments(bundle);
        callService.setOnlineParticipantsChangeListener(conversationFragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                conversationFragment, conversationFragment.getClass().getSimpleName())
                .commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {

    }

    ////////////////////////////// Conversation Fragment Callbacks ////////////////////////////

    @Override
    public void addClientConnectionCallback(QBRTCSessionStateCallback clientConnectionCallbacks) {
        if (currentSession != null) {
            currentSession.addSessionCallbacksListener(clientConnectionCallbacks);
        }
    }

    @Override
    public void onSetAudioEnabled(boolean isAudioEnabled) {
        callService.setAudioEnabled(isAudioEnabled);
    }

    @Override
    public void onLeaveCurrentSession() {
        leaveCurrentSession();
    }

    @Override
    public void onSwitchCamera(CameraVideoCapturer.CameraSwitchHandler cameraSwitchHandler) {
        callService.switchCamera(cameraSwitchHandler);
    }

    @Override
    public void onStartJoinConference() {
        callService.joinConference();
    }

    @Override
    public void onStartScreenSharing() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        QBRTCScreenCapturer.requestPermissions(this);
    }

    @Override
    public boolean isScreenSharingState() {
        return callService.isSharingScreenState();
    }

    @Override
    public HashMap<Integer, QBRTCVideoTrack> getVideoTrackMap() {
        return callService.getVideoTrackMap();
    }

    @Override
    public void onReturnToChat() {
        ChatActivity.startForResultFromCall(CallActivityCon.this, REQUEST_CODE_OPEN_CONVERSATION_CHAT,
                callService.getDialogID(), true);
    }

    @Override
    public void onManageGroup() {
        ArrayList<Integer> publishers = callService.getActivePublishers();
        publishers.add(0, getChatHelper().getCurrentUser().getId());

        ManageGroupActivity.startForResult(CallActivityCon.this, REQUEST_CODE_MANAGE_GROUP,
                currentRoomTitle, publishers);
    }

    @Override
    public String getDialogID() {
        return callService.getDialogID();
    }

    @Override
    public String getRoomID() {
        return callService.getRoomID();
    }

    @Override
    public String getRoomTitle() {
        return callService.getRoomTitle();
    }

    @Override
    public boolean isListenerRole() {
        return callService.isListenerRole();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult requestCode=" + requestCode + ", resultCode= " + resultCode);
        if (requestCode == QBRTCScreenCapturer.REQUEST_MEDIA_PROJECTION
                && resultCode == Activity.RESULT_OK && data != null) {
            startScreenSharing(data);
            Log.i(TAG, "Starting Screen Capture");
        } else if (requestCode == QBRTCScreenCapturer.REQUEST_MEDIA_PROJECTION && resultCode == Activity.RESULT_CANCELED) {
            callService.stopScreenSharing();
            startConversationFragment();
        }
        if (requestCode == REQUEST_CODE_OPEN_CONVERSATION_CHAT) {
            Log.d(TAG, "Returning back from ChatActivity");
        }
    }

    private void startScreenSharing(final Intent data) {
        ScreenShareFragment screenShareFragment = ScreenShareFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                screenShareFragment, ScreenShareFragment.class.getSimpleName())
                .commitAllowingStateLoss();

        callService.setVideoEnabled(true);
        callService.startScreenSharing(data);
    }

    @Override
    public void onStopPreview() {
        callService.stopScreenSharing();
        startConversationFragment();
    }

    @Override
    public void onSetVideoEnabled(boolean isNeedEnableCam) {
        callService.setVideoEnabled(isNeedEnableCam);
    }

    @Override
    public void removeClientConnectionCallback(QBRTCSessionStateCallback clientConnectionCallbacks) {
        if (currentSession != null) {
            currentSession.removeSessionCallbacksListener(clientConnectionCallbacks);
        }
    }

    @Override
    public void addCurrentCallStateCallback(CallServiceConf.CurrentCallStateCallback currentCallStateCallback) {
        currentCallStateCallbackList.add(currentCallStateCallback);
    }

    @Override
    public void removeCurrentCallStateCallback(CallServiceConf.CurrentCallStateCallback currentCallStateCallback) {
        currentCallStateCallbackList.remove(currentCallStateCallback);
    }

    ////////////////////////////// ConferenceSessionCallbacks ////////////////////////////

    @Override
    public void onPublishersReceived(ArrayList<Integer> publishersList) {
        Log.d(TAG, "OnPublishersReceived connectedToJanus " + connectedToJanus);
    }

    @Override
    public void onPublisherLeft(Integer userID) {
        Log.d(TAG, "OnPublisherLeft userID" + userID);
    }

    @Override
    public void onMediaReceived(String type, boolean success) {
        Log.d(TAG, "OnMediaReceived type " + type + ", success" + success);
    }

    @Override
    public void onSlowLinkReceived(boolean uplink, int nacks) {
        Log.d(TAG, "OnSlowLinkReceived uplink " + uplink + ", nacks" + nacks);
    }

    @Override
    public void onError(WsException exception) {
        if (WsHangUpException.class.isInstance(exception) && exception.getMessage() != null && exception.getMessage().equals(ICE_FAILED_REASON)) {
            ToastUtils.shortToast(getApplicationContext(), exception.getMessage());
            Log.d(TAG, "OnError exception= " + exception.getMessage());
            releaseCurrentSession();
            finish();
        } else {
            ToastUtils.shortToast(getApplicationContext(), (WsNoResponseException.class.isInstance(exception)) ? getString(R.string.packet_failed) : exception.getMessage());
        }
    }

    @Override
    public void onSessionClosed(final ConferenceSession session) {
        Log.d(TAG, "Session " + session.getSessionID() + " start stop session");
    }

    private class CallServiceConnection implements ServiceConnection {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            CallServiceConf.stop(CallActivityCon.this);
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            CallServiceConf.CallServiceBinder binder = (CallServiceConf.CallServiceBinder) service;
            callService = binder.getService();
            if (callService.currentSessionExist()) {
                currentDialogID = callService.getDialogID();
                if (callService.getReconnectionState() != CallServiceConf.ReconnectionState.IN_PROGRESS){
                    initScreen();
                }
            } else {
                //we have already currentSession == null, so it's no reason to do further initialization
                CallServiceConf.stop(CallActivityCon.this);
                finish();
            }
            callService.subscribeReconnectionListener(reconnectionListener);
        }
    }

    private class ReconnectionListenerImpl implements CallServiceConf.ReconnectionListener {
        private final String tag;

        ReconnectionListenerImpl(String tag) {
            this.tag = tag;
        }

        @Override
        public void onChangedState(CallServiceConf.ReconnectionState reconnectionState) {
            switch (reconnectionState) {
                case COMPLETED:
                    reconnectingLayout.setVisibility(View.GONE);
                    for (ReconnectionCallback reconnectionCallback : reconnectionCallbacks) {
                        reconnectionCallback.completed();
                    }
                    initScreen();
                    callService.setReconnectionState(CallServiceConf.ReconnectionState.DEFAULT);
                    break;
                case IN_PROGRESS:
                    reconnectingLayout.setVisibility(View.VISIBLE);
                    for (ReconnectionCallback reconnectionCallback : reconnectionCallbacks) {
                        reconnectionCallback.inProgress();
                    }
                    break;
                case FAILED:
                    ToastUtils.shortToast(getApplicationContext(), R.string.reconnection_failed);
                    callService.leaveCurrentSession();
                    finish();
                    break;
            }
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
            if (obj instanceof ReconnectionListenerImpl) {
                equals = TAG.equals(((ReconnectionListenerImpl) obj).tag);
            } else {
                equals = super.equals(obj);
            }
            return equals;
        }

    }
}