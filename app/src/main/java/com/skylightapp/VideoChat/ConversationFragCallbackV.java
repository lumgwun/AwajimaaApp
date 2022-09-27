package com.skylightapp.VideoChat;

import com.quickblox.videochat.webrtc.BaseSession;
import com.quickblox.videochat.webrtc.QBRTCSession;
import com.quickblox.videochat.webrtc.QBRTCTypes;
import com.quickblox.videochat.webrtc.callbacks.QBRTCClientVideoTracksCallbacks;
import com.quickblox.videochat.webrtc.callbacks.QBRTCSessionEventsCallback;
import com.quickblox.videochat.webrtc.callbacks.QBRTCSessionStateCallback;
import com.quickblox.videochat.webrtc.view.QBRTCVideoTrack;

import org.jivesoftware.smack.ConnectionListener;
import org.webrtc.CameraVideoCapturer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ConversationFragCallbackV {
    void addConnectionListener(ConnectionListener connectionCallback);

    void removeConnectionListener(ConnectionListener connectionCallback);

    void addSessionStateListener(QBRTCSessionStateCallback clientConnectionCallbacks);

    void removeSessionStateListener(QBRTCSessionStateCallback clientConnectionCallbacks);

    void addVideoTrackListener(QBRTCClientVideoTracksCallbacks<QBRTCSession> callback);

    void removeVideoTrackListener(QBRTCClientVideoTracksCallbacks<QBRTCSession> callback);

    void addSessionEventsListener(QBRTCSessionEventsCallback eventsCallback);

    void removeSessionEventsListener(QBRTCSessionEventsCallback eventsCallback);

    void addCurrentCallStateListener(CallActV.CurrentCallStateCallback currentCallStateCallback);

    void removeCurrentCallStateListener(CallActV.CurrentCallStateCallback currentCallStateCallback);

    void addOnChangeAudioDeviceListener(CallActV.OnChangeAudioDevice onChangeDynamicCallback);

    void removeOnChangeAudioDeviceListener(CallActV.OnChangeAudioDevice onChangeDynamicCallback);

    void onSetAudioEnabled(boolean isAudioEnabled);

    void onSetVideoEnabled(boolean isNeedEnableCam);

    void onSwitchAudio();

    void onHangUpCurrentSession();

    void onStartScreenSharing();

    void onSwitchCamera(CameraVideoCapturer.CameraSwitchHandler cameraSwitchHandler);

    void acceptCall(Map<String, String> userInfo);

    void startCall(Map<String, String> userInfo);

    boolean isCameraFront();

    boolean currentSessionExist();

    List<Integer> getOpponents();

    Integer getCallerId();

    BaseSession.QBRTCSessionState getCurrentSessionState();

    QBRTCTypes.QBRTCConnectionState getPeerChannel(Integer userId);

    boolean isMediaStreamManagerExist();

    boolean isCallState();

    HashMap<Integer, QBRTCVideoTrack> getVideoTrackMap();

    QBRTCVideoTrack getVideoTrack(Integer userId);
}
