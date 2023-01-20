package com.skylightapp.Classes;

import android.content.Context;
import android.net.Uri;

import com.klinker.android.logger.Log;
import com.klinker.android.send_message.MmsReceivedReceiver;

public class MmsReceivedReceiverImpl extends MmsReceivedReceiver {

    @Override
    public void onMessageReceived(Context context, Uri messageUri) {
        Log.v("AwajimaSMSReceived", "message received: " + messageUri.toString());
    }

    @Override
    public void onError(Context context, String error) {
        Log.v("AwajimaSMSReceived", "error: " + error);
    }

}