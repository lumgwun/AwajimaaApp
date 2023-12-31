package com.skylightapp.MarketInterfaces;

import android.Manifest;

import com.skylightapp.MarketClasses.ResourceUtils;
import com.skylightapp.R;

public interface ConstsInterface {
    int ERR_LOGIN_ALREADY_TAKEN_HTTP_STATUS = 422;
    String SAMPLE_CONFIG_FILE_NAME = "sample_config.json";

    int PREFERRED_IMAGE_SIZE_PREVIEW = ResourceUtils.getDimen(R.dimen.chat_attachment_preview_size);
    int PREFERRED_IMAGE_SIZE_FULL = ResourceUtils.dpToPx(320);
    String QB_USER_PASSWORD = "qb_user_password";

    int MAX_OPPONENTS_COUNT = 6;
    int MAX_LOGIN_LENGTH = 15;
    int MAX_FULLNAME_LENGTH = 20;
    String EXTRA_FCM_MESSAGE = "message";
    String ACTION_NEW_FCM_EVENT = "new-push-event";
    String EMPTY_FCM_MESSAGE = "empty message";

    String EXTRA_QB_USER = "qb_user";
    String EXTRA_USER_ID = "user_id";
    String EXTRA_USER_LOGIN = "user_login";
    String EXTRA_USER_PASSWORD = "user_password";
    String EXTRA_PENDING_INTENT = "pending_Intent";
    String EXTRA_CONTEXT = "context";
    String EXTRA_OPPONENTS_LIST = "opponents_list";
    String EXTRA_CONFERENCE_TYPE = "conference_type";

    String EXTRA_IS_INCOMING_CALL = "conversation_reason";

    String EXTRA_LOGIN_RESULT = "login_result";
    String EXTRA_LOGIN_ERROR_MESSAGE = "login_error_message";
    int EXTRA_LOGIN_RESULT_CODE = 1002;

    String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};


    String EXTRA_ROOM_ID = "17094";
    String EXTRA_ROOM_TITLE = "Room Tittle";
    String EXTRA_DIALOG_ID = "876";
    String EXTRA_DIALOG_OCCUPANTS = "9";
    String EXTRA_AS_LISTENER = "8";
    String PREF_MIC_ENABLED = "MIC ENABLED";
    String PREF_SWAP_CAM_TOGGLE_CHECKED = "pref_swap_cam_toggle_checked";
    String PREF_SCREEN_SHARING_TOGGLE_CHECKED = "pref_screen_sharing_toggle_checked";
    String PREF_CAM_ENABLED = "pref_cam_enabled";


    String EXTRA_CERTAIN_DIALOG_ID = "certain_dialog_id";
    String EXTRA_SETTINGS_TYPE = "extra_settings_type";

}
