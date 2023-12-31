package com.skylightapp.VideoChat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quickblox.chat.QBChatService;
import com.quickblox.core.QBEntityCallbackImpl;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.AppRTCAudioManager;
import com.quickblox.videochat.webrtc.BaseSession;
import com.quickblox.videochat.webrtc.QBRTCScreenCapturer;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.QBRTCTypes;
import com.quickblox.videochat.webrtc.callbacks.QBRTCClientSessionCallbacks;
import com.quickblox.videochat.webrtc.callbacks.QBRTCClientVideoTracksCallbacks;
import com.quickblox.videochat.webrtc.callbacks.QBRTCSessionEventsCallback;
import com.quickblox.videochat.webrtc.callbacks.QBRTCSessionStateCallback;
import com.quickblox.videochat.webrtc.view.QBRTCVideoTrack;
import com.skylightapp.Classes.ErrorUtils;
import com.skylightapp.MarketClasses.QbUsersDbManager;
import com.skylightapp.MarketClasses.SettingsUtil;
import com.skylightapp.MarketClasses.UsersUtils;
import com.skylightapp.MarketClasses.WebRtcSessionManager;
import com.skylightapp.MarketInterfaces.ConstsInterface;
import com.skylightapp.Markets.ToastUtils;
import com.skylightapp.R;

import org.jivesoftware.smack.AbstractConnectionListener;
import org.jivesoftware.smack.ConnectionListener;
import org.webrtc.CameraVideoCapturer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.quickblox.videochat.webrtc.BaseSession.QBRTCSessionState.QB_RTC_SESSION_PENDING;
import static com.skylightapp.VideoChat.CallServiceV.ONE_OPPONENT;

public class CallActV extends BaseActivity implements IncomeCallFragmentCallbackListener,
        QBRTCSessionStateCallback<QBRTCSession>, QBRTCClientSessionCallbacks,
        ConversationFragmentCallback, ScreenShareFragment.OnSharingEvents {
    private static final String TAG = CallActV.class.getSimpleName();

    public static final String INCOME_CALL_FRAGMENT = "income_call_fragment";
    public static final int REQUEST_PERMISSION_SETTING = 545;

    private ArrayList<CurrentCallStateCallback> currentCallStateCallbackList = new ArrayList<>();
    private QbUsersDbManager dbManager = QbUsersDbManager.getInstance(this);
    private Handler showIncomingCallWindowTaskHandler;
    private ConnectionListenerImpl connectionListener;
    private ServiceConnection callServiceConnection;
    private Runnable showIncomingCallWindowTask;
    private boolean isInComingCall = false;
    private List<Integer> opponentsIdsList;
    private SharedPreferences sharedPref;
    private boolean isVideoCall = false;
    private PermissionsChecker checker;
    private CallServiceV callService;

    public static void start(Context context, boolean isIncomingCall) {
        Intent intent = new Intent(context, CallActV.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ConstsV.EXTRA_IS_INCOMING_CALL, isIncomingCall);
        SharedPrefsHelperV.getInstance().save(ConstsV.EXTRA_IS_INCOMING_CALL, isIncomingCall);
        context.startActivity(intent);
        CallServiceV.start(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_act_v);
        checker = new PermissionsChecker(this);
    }
    private void initScreen() {
        callService.setCallTimerCallback(new CallTimerCallback());
        isVideoCall = callService.isVideoCall();

        opponentsIdsList = callService.getOpponents();
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        initSettingsStrategy();
        addListeners();

        if (getIntent() != null && getIntent().getExtras() != null) {
            isInComingCall = getIntent().getExtras().getBoolean(ConstsV.EXTRA_IS_INCOMING_CALL, false);
        } else {
            isInComingCall = sharedPrefsHelper.get(ConstsV.EXTRA_IS_INCOMING_CALL, false);
        }

        if (callService.isCallMode()) {
            checkPermission();
            if (callService.isSharingScreenState()) {
                startScreenSharing(null);
                return;
            }
            addConversationFragment(isInComingCall);
        } else {
            if (!isInComingCall) {
                callService.playRingtone();
            }
            startSuitableFragment(isInComingCall);
        }
    }

    private void addListeners() {
        addSessionEventsListener(this);
        addSessionStateListener(this);

        connectionListener = new ConnectionListenerImpl();
        addConnectionListener(connectionListener);
    }

    private void removeListeners() {
        removeSessionEventsListener(this);
        removeSessionStateListener(this);
        removeConnectionListener(connectionListener);
        callService.removeCallTimerCallback();
    }

    private void bindCallService() {
        callServiceConnection = new CallServiceConnection();
        Intent intent = new Intent(this, CallServiceV.class);
        bindService(intent, callServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult requestCode=" + requestCode + ", resultCode= " + resultCode);
        if (resultCode == ConstsV.EXTRA_LOGIN_RESULT_CODE) {
            if (data != null) {
                boolean isLoginSuccess = data.getBooleanExtra(ConstsV.EXTRA_LOGIN_RESULT, false);
                if (isLoginSuccess) {
                    initScreen();
                } else {
                    CallServiceV.stop(this);
                    finish();
                }
            }
        }
        if (requestCode == QBRTCScreenCapturer.REQUEST_MEDIA_PROJECTION
                && resultCode == Activity.RESULT_OK && data != null) {
            startScreenSharing(data);
            Log.i(TAG, "Starting Screen Capture");
        }
    }

    private void startScreenSharing(final Intent data) {
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(ScreenShareFragment.class.getSimpleName());
        if (!(fragmentByTag instanceof ScreenShareFragment)) {
            ScreenShareFragment screenShareFragment = ScreenShareFragment.newInstance();
            FragmentExecuotr.addFragment(getSupportFragmentManager(), R.id.fragment_containerV, screenShareFragment, ScreenShareFragment.class.getSimpleName());

            if (data != null) {
                callService.startScreenSharing(data);
            }
        }
    }

    private void startSuitableFragment(boolean isInComingCall) {
        QBRTCSession session = WebRtcSessionManager.getInstance(this).getCurrentSession();
        if (session != null) {
            if (isInComingCall) {
                initIncomingCallTask();
                startLoadAbsentUsers();
                addIncomeCallFragment();
                checkPermission();
            } else {
                addConversationFragment(isInComingCall);
                getIntent().removeExtra(ConstsV.EXTRA_IS_INCOMING_CALL);
                sharedPrefsHelper.save(ConstsV.EXTRA_IS_INCOMING_CALL, false);
            }
        } else {
            finish();
        }
    }

    private void checkPermission() {
        boolean cam = SharedPrefsHelperV.getInstance().get(ConstsV.PERMISSIONS[0], true);
        boolean mic = SharedPrefsHelperV.getInstance().get(ConstsV.PERMISSIONS[1], true);

        if (isVideoCall && checker.lacksPermissions(ConstsV.PERMISSIONS)) {
            if (cam) {
                PermissionsActivity.startActivity(this, false, ConstsV.PERMISSIONS);
            } else {
                View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
                ErrorUtils.showSnackbar(rootView, R.string.error_permission_video, R.string.dlg_allow, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startPermissionSystemSettings();
                    }
                });
            }
        } else if (checker.lacksPermissions(ConstsV.PERMISSIONS[1])) {
            if (mic) {
                PermissionsActivity.startActivity(this, true, ConstsV.PERMISSIONS);
            } else {
                View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
                ErrorUtils.showSnackbar(rootView, R.string.error_permission_audio, R.string.dlg_allow, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startPermissionSystemSettings();
                    }
                });
            }
        }
    }

    private void startPermissionSystemSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
    }

    private void startLoadAbsentUsers() {
        ArrayList<QBUser> usersFromDb = dbManager.getAllUsers();
        ArrayList<Integer> allParticipantsOfCall = new ArrayList<>();

        if (opponentsIdsList != null) {
            allParticipantsOfCall.addAll(opponentsIdsList);
        }

        if (isInComingCall) {
            Integer callerID = callService.getCallerId();
            if (callerID != null) {
                allParticipantsOfCall.add(callerID);
            }
        }

        ArrayList<Integer> idsUsersNeedLoad = UsersUtils.getIdsNotLoadedUsers(usersFromDb, allParticipantsOfCall);
        if (!idsUsersNeedLoad.isEmpty()) {
            requestExecutor.loadUsersByIds(idsUsersNeedLoad, new QBEntityCallbackImpl<ArrayList<QBUser>>() {
                @Override
                public void onSuccess(ArrayList<QBUser> users, Bundle params) {
                    dbManager.saveAllUsers(users, false);
                    notifyCallStateListenersNeedUpdateOpponentsList(users);
                }
            });
        }
    }

    private void initSettingsStrategy() {
        if (opponentsIdsList != null) {
            SettingsUtil.setSettingsStrategy(opponentsIdsList, sharedPref, this);
        }
    }

    private void initIncomingCallTask() {
        showIncomingCallWindowTaskHandler = new Handler(Looper.myLooper());
        showIncomingCallWindowTask = new Runnable() {
            @Override
            public void run() {
                ToastUtils.longToast("Call was stopped by UserNoActions timer");
                callService.clearCallState();
                callService.clearButtonsState();
                WebRtcSessionManager.getInstance(getApplicationContext()).setCurrentSession(null);
                CallServiceV.stop(CallActV.this);
                finish();
            }
        };
    }

    public void hangUpCurrentSession() {
        callService.stopRingtone();
        if (!callService.hangUpCurrentSession(new HashMap<>())) {
            CallServiceV.stop(this);
            finish();
        }
    }

    private void startIncomeCallTimer(long time) {
        Log.d(TAG, "startIncomeCallTimer");
        showIncomingCallWindowTaskHandler.postAtTime(showIncomingCallWindowTask, SystemClock.uptimeMillis() + time);
    }

    private void stopIncomeCallTimer() {
        Log.d(TAG, "stopIncomeCallTimer");
        showIncomingCallWindowTaskHandler.removeCallbacks(showIncomingCallWindowTask);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindCallService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(callServiceConnection);
        if (callService != null) {
            removeListeners();
        }
    }

    @Override
    public void finish() {
        CallServiceV.stop(this);
        OpponentsActivity.start(this);
        super.finish();
    }

    @Override
    public void onBackPressed() {
        // To prevent returning from Call Fragment
    }

    private void addIncomeCallFragment() {
        Log.d(TAG, "Adding IncomeCallFragment");
        if (callService.currentSessionExist()) {
            IncomeCallFragmentV fragment = new IncomeCallFragmentV();
            FragmentExecuotr.addFragment(getSupportFragmentManager(), R.id.fragment_containerV, fragment, INCOME_CALL_FRAGMENT);
        } else {
            Log.d(TAG, "SKIP Adding IncomeCallFragment");
        }
    }

    private void addConversationFragment(boolean isIncomingCall) {
        BaseConversationFragmentV conversationFragment = BaseConversationFragmentV.newInstance(
                isVideoCall
                        ? new VideoConversationFragV()
                        : new AudioConversationFragment(),
                isIncomingCall);
        FragmentExecuotr.addFragment(getSupportFragmentManager(), R.id.fragment_containerV, conversationFragment, conversationFragment.getClass().getSimpleName());
    }

    private void showNotificationPopUp(final int text, final boolean show) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayout connectionView = (LinearLayout) View.inflate(CallActV.this, R.layout.connection_popup, null);
                if (show) {
                    ((TextView) connectionView.findViewById(R.id.notification)).setText(text);
                    if (connectionView.getParent() == null) {
                        ((ViewGroup) CallActV.this.findViewById(R.id.fragment_containerV)).addView(connectionView);
                    }
                } else {
                    ((ViewGroup) CallActV.this.findViewById(R.id.fragment_containerV)).removeView(connectionView);
                }
            }
        });
    }

    private class ConnectionListenerImpl extends AbstractConnectionListener {
        @Override
        public void connectionClosedOnError(Exception e) {
            showNotificationPopUp(R.string.connection_was_lost, true);
        }

        @Override
        public void reconnectionSuccessful() {
            showNotificationPopUp(R.string.connection_was_lost, false);
        }
    }

    @Override
    public void onDisconnectedFromUser(QBRTCSession session, Integer userID) {
        Log.d(TAG, "Disconnected from user: " + userID);
    }

    @Override
    public void onConnectedToUser(QBRTCSession session, final Integer userID) {
        notifyCallStateListenersCallStarted();
        if (isInComingCall) {
            stopIncomeCallTimer();
        }
        Log.d(TAG, "onConnectedToUser() is started");
    }

    @Override
    public void onConnectionClosedForUser(QBRTCSession session, Integer userID) {
        Log.d(TAG, "Connection closed for user: " + userID);
    }

    @Override
    public void onStateChanged(QBRTCSession qbrtcSession, BaseSession.QBRTCSessionState qbrtcSessionState) {

    }

    @Override
    public void onUserNotAnswer(QBRTCSession session, Integer userID) {
        if (callService.isCurrentSession(session)) {
            callService.stopRingtone();
        }
    }

    @Override
    public void onSessionStartClose(final QBRTCSession session) {
        if (callService.isCurrentSession(session)) {
            callService.removeSessionStateListener(this);
            notifyCallStateListenersCallStopped();
        }
    }

    @Override
    public void onReceiveHangUpFromUser(final QBRTCSession session, final Integer userID, Map<String, String> map) {
        if (callService.isCurrentSession(session)) {
            int numberOpponents = session.getOpponents().size();
            if (numberOpponents == ONE_OPPONENT) {
                hangUpCurrentSession();
                Log.d(TAG, "Initiator hung up the call");
            } else {
                if (session.getState() == QB_RTC_SESSION_PENDING && userID.equals(session.getCallerID())) {
                    hangUpCurrentSession();
                }
            }

            QBUser participant = dbManager.getUserById(userID);
            final String participantName = participant != null ? participant.getFullName() : String.valueOf(userID);
            ToastUtils.shortToast("User " + participantName + " " + getString(R.string.text_status_hang_up) + " conversation");
        }
    }

    @Override
    public void onCallAcceptByUser(QBRTCSession session, Integer userId, Map<String, String> userInfo) {
        if (callService.isCurrentSession(session)) {
            callService.stopRingtone();
        }
    }

    @Override
    public void onReceiveNewSession(final QBRTCSession session) {
        Log.d(TAG, "Session " + session.getSessionID() + " Received");
    }


    @Override
    public void onUserNoActions(QBRTCSession qbrtcSession, Integer integer) {
        startIncomeCallTimer(0);
    }

    @Override
    public void onSessionClosed(final QBRTCSession session) {
        if (callService.isCurrentSession(session)) {
            Log.d(TAG, "Stopping session");
            callService.stopForeground(true);
            finish();
        }
    }

    @Override
    public void onCallRejectByUser(QBRTCSession session, Integer userID, Map<String, String> userInfo) {
        if (callService.isCurrentSession(session)) {
            callService.stopRingtone();
        }
    }

    @Override
    public void onAcceptCurrentSession() {
        if (callService.currentSessionExist()) {
            addConversationFragment(true);
        } else {
            Log.d(TAG, "SKIP addConversationFragment method");
        }
    }

    @Override
    public void onRejectCurrentSession() {
        callService.rejectCurrentSession(new HashMap<>());
    }

    @Override
    public void addConnectionListener(ConnectionListener connectionCallback) {
        callService.addConnectionListener(connectionCallback);
    }

    @Override
    public void removeConnectionListener(ConnectionListener connectionCallback) {
        callService.removeConnectionListener(connectionCallback);
    }

    @Override
    public void addSessionStateListener(QBRTCSessionStateCallback clientConnectionCallbacks) {
        callService.addSessionStateListener(clientConnectionCallbacks);
    }

    @Override
    public void addSessionEventsListener(QBRTCSessionEventsCallback eventsCallback) {
        callService.addSessionEventsListener(eventsCallback);
    }

    @Override
    public void onSetAudioEnabled(boolean isAudioEnabled) {
        callService.setAudioEnabled(isAudioEnabled);
    }

    @Override
    public void onHangUpCurrentSession() {
        hangUpCurrentSession();
    }

    @Override
    public void onStartScreenSharing() {
        QBRTCScreenCapturer.requestPermissions(this);
    }

    @Override
    public void onSwitchCamera(CameraVideoCapturer.CameraSwitchHandler cameraSwitchHandler) {
        callService.switchCamera(cameraSwitchHandler);
    }

    @Override
    public void onSetVideoEnabled(boolean isNeedEnableCam) {
        callService.setVideoEnabled(isNeedEnableCam);
    }

    @Override
    public void onSwitchAudio() {
        callService.switchAudio();
    }

    @Override
    public void removeSessionStateListener(QBRTCSessionStateCallback clientConnectionCallbacks) {
        callService.removeSessionStateListener(clientConnectionCallbacks);
    }

    @Override
    public void removeSessionEventsListener(QBRTCSessionEventsCallback eventsCallback) {
        callService.removeSessionEventsListener(eventsCallback);
    }

    @Override
    public void addCurrentCallStateListener(CurrentCallStateCallback currentCallStateCallback) {
        if (currentCallStateCallback != null) {
            currentCallStateCallbackList.add(currentCallStateCallback);
        }
    }

    @Override
    public void removeCurrentCallStateListener(CurrentCallStateCallback currentCallStateCallback) {
        currentCallStateCallbackList.remove(currentCallStateCallback);
    }

    @Override
    public void addOnChangeAudioDeviceListener(OnChangeAudioDevice onChangeDynamicCallback) {

    }

    @Override
    public void removeOnChangeAudioDeviceListener(OnChangeAudioDevice onChangeDynamicCallback) {

    }

    @Override
    public void acceptCall(Map<String, String> userInfo) {
        callService.acceptCall(userInfo);
    }

    @Override
    public void startCall(Map<String, String> userInfo) {
        callService.startCall(userInfo);
    }

    @Override
    public boolean isCameraFront() {
        return callService.isCameraFront();
    }

    @Override
    public boolean currentSessionExist() {
        return callService.currentSessionExist();
    }

    @Override
    public List<Integer> getOpponents() {
        return callService.getOpponents();
    }

    @Override
    public Integer getCallerId() {
        return callService.getCallerId();
    }

    @Override
    public void addVideoTrackListener(QBRTCClientVideoTracksCallbacks<QBRTCSession> callback) {
        callService.addVideoTrackListener(callback);
    }

    @Override
    public void removeVideoTrackListener(QBRTCClientVideoTracksCallbacks<QBRTCSession> callback) {
        callService.removeVideoTrackListener(callback);
    }

    @Override
    public BaseSession.QBRTCSessionState getCurrentSessionState() {
        return callService.getCurrentSessionState();
    }

    @Override
    public QBRTCTypes.QBRTCConnectionState getPeerChannel(Integer userId) {
        return callService.getPeerChannel(userId);
    }

    @Override
    public boolean isMediaStreamManagerExist() {
        return callService.isMediaStreamManagerExist();
    }

    @Override
    public boolean isCallState() {
        return callService.isCallMode();
    }

    @Override
    public HashMap<Integer, QBRTCVideoTrack> getVideoTrackMap() {
        return callService.getVideoTrackMap();
    }

    @Override
    public QBRTCVideoTrack getVideoTrack(Integer userId) {
        return callService.getVideoTrack(userId);
    }

    @Override
    public void onStopPreview() {
        callService.stopScreenSharing();
        addConversationFragment(isInComingCall);
    }

    private void notifyCallStateListenersCallStarted() {
        for (CurrentCallStateCallback callback : currentCallStateCallbackList) {
            callback.onCallStarted();
        }
    }

    private void notifyCallStateListenersCallStopped() {
        for (CurrentCallStateCallback callback : currentCallStateCallbackList) {
            callback.onCallStopped();
        }
    }

    private void notifyCallStateListenersNeedUpdateOpponentsList(final ArrayList<QBUser> newUsers) {
        for (CurrentCallStateCallback callback : currentCallStateCallbackList) {
            callback.onOpponentsListUpdated(newUsers);
        }
    }

    private void notifyCallStateListenersCallTime(String callTime) {
        for (CurrentCallStateCallback callback : currentCallStateCallbackList) {
            callback.onCallTimeUpdate(callTime);
        }
    }

    private class CallServiceConnection implements ServiceConnection {
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            CallServiceV.CallServiceBinder binder = (CallServiceV.CallServiceBinder) service;
            callService = binder.getService();
            if (callService.currentSessionExist()) {
                if (QBChatService.getInstance().isLoggedIn()) {
                    initScreen();
                } else {
                    login();
                }
            } else {
                finish();
            }
        }

        private void login() {
            QBUser qbUser = SharedPrefsHelperV.getInstance().getQbUser();
            Intent tempIntent = new Intent(CallActV.this, LoginService.class);
            PendingIntent pendingIntent = createPendingResult(ConstsInterface.EXTRA_LOGIN_RESULT_CODE, tempIntent, 0);
            LoginService.start(CallActV.this, qbUser, pendingIntent);
        }
    }

    private class CallTimerCallback implements CallServiceV.CallTimerListener {
        @Override
        public void onCallTimeUpdate(String time) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notifyCallStateListenersCallTime(time);
                }
            });
        }
    }

    public interface OnChangeAudioDevice {
        void audioDeviceChanged(AppRTCAudioManager.AudioDevice newAudioDevice);
    }


    public interface CurrentCallStateCallback {
        void onCallStarted();

        void onCallStopped();

        void onOpponentsListUpdated(ArrayList<QBUser> newUsers);

        void onCallTimeUpdate(String time);
    }
}