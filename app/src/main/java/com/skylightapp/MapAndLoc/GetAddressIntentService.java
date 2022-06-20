package com.skylightapp.MapAndLoc;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.Locale;

public class GetAddressIntentService extends IntentService {


    private static final String IDENTIFIER = "GetAddressIntentService";
    private ResultReceiver addressResultReceiver;

    public GetAddressIntentService() {
        super(IDENTIFIER);
    }
    private static final String ACTION_FOO = "com.skylightapp.MapAndLoc.action.FOO";
    private static final String ACTION_BAZ = "com.skylightapp.MapAndLoc.action.BAZ";

    private static final String EXTRA_PARAM1 = "com.skylightapp.MapAndLoc.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.skylightapp.MapAndLoc.extra.PARAM2";


    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, GetAddressIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }


    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, GetAddressIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String msg = "";
        if (intent != null) {
            addressResultReceiver = intent.getParcelableExtra("add_receiver");
        }

        if (addressResultReceiver == null) {
            Log.e("GetAddressIntentService",
                    "No receiver, not processing the request further");
            return;
        }

        Location location = null;
        if (intent != null) {
            location = intent.getParcelableExtra("add_location");
        }

        if (location == null) {
            msg = "No location, can't go further without location";
            sendResultsToReceiver(0, msg);
            return;
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    1);
        } catch (Exception ioException) {
            Log.e("", "Error in getting address for the location");
        }

        if (addresses == null || addresses.size()  == 0) {
            msg = "No address found for the location";
            sendResultsToReceiver(1, msg);
        } else {
            Address address = addresses.get(0);
            StringBuffer addressDetails = new StringBuffer();

            addressDetails.append(address.getFeatureName());
            addressDetails.append("\n");

            addressDetails.append(address.getThoroughfare());
            addressDetails.append("\n");

            addressDetails.append("Locality: ");
            addressDetails.append(address.getLocality());
            addressDetails.append("\n");

            addressDetails.append("County: ");
            addressDetails.append(address.getSubAdminArea());
            addressDetails.append("\n");

            addressDetails.append("State: ");
            addressDetails.append(address.getAdminArea());
            addressDetails.append("\n");

            /*addressDetails.append("Country: ");
            addressDetails.append(address.getCountryName());
            addressDetails.append("\n");

            addressDetails.append("Postal Code: ");
            addressDetails.append(address.getPostalCode());
            addressDetails.append("\n");*/

            sendResultsToReceiver(2,addressDetails.toString());
        }
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
    //to send results to receiver in the source activity
    private void sendResultsToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString("address_result", message);
        addressResultReceiver.send(resultCode, bundle);
    }




    private void handleActionFoo(String param1, String param2) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    private void handleActionBaz(String param1, String param2) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}