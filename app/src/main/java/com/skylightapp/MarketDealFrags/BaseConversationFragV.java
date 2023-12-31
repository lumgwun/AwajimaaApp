package com.skylightapp.MarketDealFrags;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;

import com.quickblox.chat.QBChatService;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCTypes;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.MarketClasses.CallServiceConf;
import com.skylightapp.MarketClasses.CollectionsUtils;
import com.skylightapp.MarketClasses.QbUsersDbManager;
import com.skylightapp.MarketClasses.UsersUtils;
import com.skylightapp.MarketClasses.WebRtcSessionManager;
import com.skylightapp.MarketInterfaces.ConstsInterface;
import com.skylightapp.MarketInterfaces.ConversationFragmentCallback;
import com.skylightapp.R;
import com.skylightapp.VideoChat.AudioConversationFragment;
import com.skylightapp.VideoChat.CallActV;
import com.skylightapp.VideoChat.ConversationFragCallbackV;
import com.skylightapp.VideoChat.VideoConversationFragV;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BaseConversationFragV extends BaseToolBarFragment implements CallActV.CurrentCallStateCallback {
    private static final String TAG = BaseConversationFragV.class.getSimpleName();

    public static final String MIC_ENABLED = "is_microphone_enabled";

    protected QbUsersDbManager dbManager;
    protected WebRtcSessionManager sessionManager;

    private boolean isIncomingCall;
    protected TextView timerCallText;

    protected ConversationFragCallbackV conversationFragmentCallback;
    protected QBUser currentUser;
    protected ArrayList<QBUser> opponents;
    protected boolean isStarted;

    private ToggleButton micToggleVideoCall;
    private ImageButton handUpVideoCall;
    protected View outgoingOpponentsRelativeLayout;
    protected TextView allOpponentsTextView;
    protected TextView ringingTextView;

    public static BaseConversationFragV newInstance(BaseConversationFragV baseConversationFragV, boolean isIncomingCall) {
        Log.d(TAG, "isIncomingCall =  " + isIncomingCall);
        Bundle args = new Bundle();
        args.putBoolean(ConstsInterface.EXTRA_IS_INCOMING_CALL, isIncomingCall);
        baseConversationFragV.setArguments(args);
        return baseConversationFragV;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            conversationFragmentCallback = (ConversationFragCallbackV) getContext();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ConversationFragmentCallback");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (conversationFragmentCallback != null) {
            conversationFragmentCallback.addCurrentCallStateListener(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        initFields();
        initViews(view);
        initActionBar();
        initButtonsListener();
        prepareAndShowOutgoingScreen();

        return view;
    }

    private void initActionBar() {
        configureToolbar();
        configureActionBar();
    }

    private void prepareAndShowOutgoingScreen() {
        configureOutgoingScreen();
        allOpponentsTextView.setText(CollectionsUtils.makeStringFromUsersFullNames(opponents));
    }

    protected void configureOutgoingScreen() {

    }

    protected void configureActionBar() {

    }

    protected void configureToolbar() {

    }

    protected void initFields() {
        dbManager = QbUsersDbManager.getInstance(getActivity().getApplicationContext());
        sessionManager = WebRtcSessionManager.getInstance(getActivity());
        currentUser = QBChatService.getInstance().getUser();
        if (currentUser == null) {
            currentUser = PrefManager.getInstance().getQbUser();
        }

        if (getArguments() != null) {
            isIncomingCall = getArguments().getBoolean(ConstsInterface.EXTRA_IS_INCOMING_CALL, false);
        }
        initOpponentsList();
        Log.d(TAG, "Opponents: " + opponents.toString());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (conversationFragmentCallback != null) {
            if (isIncomingCall) {
                conversationFragmentCallback.acceptCall(new HashMap<>());
            } else {
                conversationFragmentCallback.startCall(new HashMap<>());
            }
        }
    }

    @Override
    public void onDestroy() {
        if (conversationFragmentCallback != null) {
            conversationFragmentCallback.removeCurrentCallStateListener(this);
        }
        super.onDestroy();
    }

    protected void initViews(View view) {
        micToggleVideoCall = (ToggleButton) view.findViewById(R.id.toggle_mic);
        micToggleVideoCall.setChecked(PrefManager.getInstance().get(MIC_ENABLED, true));
        handUpVideoCall = (ImageButton) view.findViewById(R.id.button_hangup_call);
        outgoingOpponentsRelativeLayout = view.findViewById(R.id.layout_background_outgoing_screen);
        allOpponentsTextView = (TextView) view.findViewById(R.id.text_outgoing_opponents_names);
        ringingTextView = (TextView) view.findViewById(R.id.text_ringing);

        if (isIncomingCall) {
            hideOutgoingScreen();
        }
    }

    protected void initButtonsListener() {
        micToggleVideoCall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PrefManager.getInstance().save(MIC_ENABLED, isChecked);
                if (conversationFragmentCallback != null) {
                    conversationFragmentCallback.onSetAudioEnabled(isChecked);
                }
            }
        });

        handUpVideoCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionButtonsEnabled(false);
                handUpVideoCall.setEnabled(false);
                handUpVideoCall.setActivated(false);
                CallServiceConf.stop(getContext());

                if (conversationFragmentCallback != null) {
                    conversationFragmentCallback.onHangUpCurrentSession();
                }
                Log.d(TAG, "Call is Stopped");
            }
        });
    }

    private void clearButtonsState() {
        PrefManager.getInstance().delete(MIC_ENABLED);
        PrefManager.getInstance().delete(AudioConversationFragment.SPEAKER_ENABLED);
        PrefManager.getInstance().delete(VideoConversationFragV.CAMERA_ENABLED);
    }

    protected void actionButtonsEnabled(boolean enabled) {
        micToggleVideoCall.setEnabled(enabled);
        micToggleVideoCall.setActivated(enabled);
    }

    private void startTimer() {
        if (!isStarted) {
            timerCallText.setVisibility(View.VISIBLE);
            isStarted = true;
        }
    }

    private void hideOutgoingScreen() {
        outgoingOpponentsRelativeLayout.setVisibility(View.GONE);
    }

    @Override
    public void onCallStarted() {
        hideOutgoingScreen();
        startTimer();
        actionButtonsEnabled(true);
    }

    @Override
    public void onCallStopped() {
        CallServiceConf.stop(getContext());
        isStarted = false;
        clearButtonsState();
        actionButtonsEnabled(false);
    }

    @Override
    public void onOpponentsListUpdated(ArrayList<QBUser> newUsers) {
        initOpponentsList();
    }

    private void initOpponentsList() {
        Log.v(TAG, "initOpponentsList()");
        if (conversationFragmentCallback != null) {
            List<Integer> opponentsIds = conversationFragmentCallback.getOpponents();

            if (opponentsIds != null) {
                ArrayList<QBUser> usersFromDb = dbManager.getUsersByIds(opponentsIds);
                opponents = UsersUtils.getListAllUsersFromIds(usersFromDb, opponentsIds);
            }

            QBUser caller = dbManager.getUserById(conversationFragmentCallback.getCallerId());
            if (caller == null) {
                caller = new QBUser(conversationFragmentCallback.getCallerId());
                caller.setFullName(String.valueOf(conversationFragmentCallback.getCallerId()));
            }

            if (isIncomingCall) {
                opponents.add(caller);
                opponents.remove(QBChatService.getInstance().getUser());
            }
        }
    }

    public QBRTCTypes.QBRTCConnectionState getConnectionState(Integer userID) {
        QBRTCTypes.QBRTCConnectionState result = null;
        if (conversationFragmentCallback != null) {
            result = conversationFragmentCallback.getPeerChannel(userID);
        }
        return result;
    }
}
