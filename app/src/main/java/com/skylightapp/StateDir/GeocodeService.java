package com.skylightapp.StateDir;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GeocodeService extends IntentService {


    private static final String ACTION_FOO = "com.skylightapp.StateDir.action.FOO";
    private static final String ACTION_BAZ = "com.skylightapp.StateDir.action.BAZ";

    private static final String EXTRA_PARAM1 = "com.skylightapp.StateDir.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.skylightapp.StateDir.extra.PARAM2";
    protected ResultReceiver resultReceiver;


    public GeocodeService() {
        super("GeocodeService");
    }

    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, GeocodeService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, GeocodeService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
        Log.v(GeocodeConstants.TAG_GEOCODE,"onHandle");

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        ArrayList<List<Address>> addresses = new ArrayList<>();

        String errorMessage = "";

        ArrayList<String> addressName = null;
        if (intent != null) {
            addressName = intent.getStringArrayListExtra(GeocodeConstants.LOCATION_NAME);
        }

        if (addressName != null) {
            Log.v(GeocodeConstants.TAG_GEOCODE,"Address test : " + addressName.get(0));
        }

        if (addressName != null) {
            for(int i = 0; i < addressName.size(); i++)
            {
                try{
                    List<Address> test = geocoder.getFromLocationName(addressName.get(i),1);
                    Log.v(GeocodeConstants.TAG_GEOCODE,"test 2 : " + test.get(0));
                    addresses.add(test);
                }catch (Exception e){
                    errorMessage = "Service not available";
                    Log.e(GeocodeConstants.TAG_GEOCODE,errorMessage,e);
                }
            }
        }

        if (intent != null) {
            resultReceiver = intent.getParcelableExtra(GeocodeConstants.ADDRESS_RECEIVER);
        }

        if(addresses == null || addresses.size() == 0){
            errorMessage = "Latitude/Longitude not found";
            Log.e(GeocodeConstants.TAG_GEOCODE,errorMessage);
            deliverResultToMainActivity(GeocodeConstants.FAILURE,errorMessage,null);
        }
        else{

            List<Address> address = new ArrayList<Address>();
            for (int i = 0; i < addresses.size(); i++){
                address.add(addresses.get(i).get(0));
            }

            Log.v(GeocodeConstants.TAG_GEOCODE,"Address : " + String.valueOf(addresses.get(0)));

            deliverResultToMainActivity(GeocodeConstants.SUCCESS,
                    "Lat/Log found",address);
        }
    }
    private void deliverResultToMainActivity(int resultCode, String message, List<Address> address){

        // Create a bundle
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(GeocodeConstants.RES_ADDRESS, (ArrayList<? extends Parcelable>) address);
        bundle.putString(GeocodeConstants.MESSAGE,message);
        resultReceiver.send(resultCode,bundle);

    }


    private void handleActionFoo(String param1, String param2) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void handleActionBaz(String param1, String param2) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}