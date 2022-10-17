package com.skylightapp.MarketClasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Database.ProfDAO;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppInstallRefReceiver extends BroadcastReceiver {
    private static final String TAG = "AppInstallRefReceiver";

    private final Executor backgroundExecutor = Executors.newSingleThreadExecutor();

    private ProfDAO profDAO;
    String referrer = "";
    private PrefManager prefManager;
    private String strName;
    String strCode;
    private int refCount=0;
    private int refNewCount=0;

    @Override
    public void onReceive(Context context, Intent intent) {
        profDAO= new ProfDAO(context.getApplicationContext());
        final PendingResult pendingResult = goAsync();

        Bundle extras = intent.getExtras();
        prefManager= new PrefManager();
        referrer = intent.getStringExtra("referrer");
        backgroundExecutor.execute(() -> {
            try {
                // Do some background work
                StringBuilder sb = new StringBuilder();
                sb.append("Action: ").append(intent.getAction()).append("\n");
                sb.append("URI: ").append(intent.toUri(Intent.URI_INTENT_SCHEME)).append("\n");
                String log = sb.toString();

                if (extras != null) {
                    referrer = extras.getString("referrer");

                    Log.e("Receiver Referral", "===>" + referrer);

                    if(referrer!=null)
                    {
                        String[] referrerParts = referrer.split("(?<=\\D)(?=\\d)");
                        strName = referrerParts[0];
                        strCode = referrerParts[1];

                        Log.e("Receiver Referral Code", "===>" + strCode);
                        Log.e("Receiver Referral Name", "===>" + strName);
                    }
                    refCount =profDAO.getReferrerCount(referrer);
                    refNewCount =refCount+1;
                    //refNewCount +=refCount+1;
                    profDAO.updateRefCount(referrer,refNewCount);
                    prefManager.saveAppReferrer(referrer,strName,strCode);
                }
                Log.d(TAG, log);
            } finally {
                // Must call finish() so the BroadcastReceiver can be recycled
                pendingResult.finish();
            }
        });


        throw new UnsupportedOperationException("Not yet implemented");

    }

}