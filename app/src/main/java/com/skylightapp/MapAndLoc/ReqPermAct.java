package com.skylightapp.MapAndLoc;

import static android.Manifest.permission.ACCESS_BACKGROUND_LOCATION;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.skylightapp.Classes.AppController;
import com.skylightapp.LoginDirAct;
import com.skylightapp.R;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

public class ReqPermAct extends AppCompatActivity {
    private static final String CLASSTAG =
            " " + ReqPermAct.class.getSimpleName () + " ";
    private static final int REQUEST_CODE_PERMISSIONS = 101;

    public static final String CHANNEL_ID = "FENCE_NOTIFICATION";

    private AppCompatButton btnPermissions;
    private AlertDialog inadequatePermissionsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_req_perm);
        createNotificationChannel ();

        final List<String> permissions = permissionsDesired ();

        if (permissions.size () > 0) {

            btnPermissions = findViewById (R.id.btnPer);
            btnPermissions.setClickable (true);
            btnPermissions.setVisibility (View.VISIBLE);
            btnPermissions.setOnClickListener (new View.OnClickListener ()
            {
                @Override
                public void onClick (View view)
                {
                    String [] strPermissions = new String [permissions.size ()];
                    permissions.toArray (strPermissions);
                    requestLocationPermissions (strPermissions);
                }
            });

        } else
        {
            // No permissions required.
            startActivity (new Intent (this, LoginDirAct.class));
            finish ();
        }
    }

    @Override
    public void onPause ()
    {
        super.onPause ();
        //Log.v (Constants.LOGTAG, CLASSTAG + "onPause called");
    }

    @Override
    public void onRequestPermissionsResult ( int requestCode,
                                             @NonNull String [] permissions,
                                             @NonNull int [] grantResults )
    {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);
        //Log.v (Constants.LOGTAG, CLASSTAG + "onRequestPermissionsResult called");

        // Ignore any calls which are not related to REQUEST_CODE_PERMISSIONS
        if (requestCode == REQUEST_CODE_PERMISSIONS)
        {
            AppController app = (AppController) getApplication ();
            boolean coarsePermission = app.isHasCoarseLocationPermission ();
            boolean finePermission = app.isHasFineLocationPermission ();
            boolean backgroundPermission = app.isHasBackgroundLocationPermission ();

            // Check all permissions and update foreground, background appropriately.
            for (int i = 0; i < permissions.length; i++)
            {
                String permission = permissions [i];
                boolean grant = grantResults [i] == PERMISSION_GRANTED;
                if (permission.equalsIgnoreCase (ACCESS_COARSE_LOCATION))
                {
                    coarsePermission = grant;
                } else if (permission.equalsIgnoreCase (ACCESS_FINE_LOCATION))
                {
                    finePermission = grant;
                } else if (permission.equalsIgnoreCase (ACCESS_BACKGROUND_LOCATION))
                {
                    backgroundPermission = grant;
                } else
                {
                    //Log.w ( Constants.LOGTAG, CLASSTAG + "Unexpected permission request: " + permission);
                }
            }

            if (coarsePermission || finePermission)
            {
                // Background permission is true for API's before Android 10 / Q
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
                { backgroundPermission = true; }

                if (backgroundPermission)
                {
                    handleAllLocationUpdates (coarsePermission, finePermission);
                } else
                {
                    handleForegroundLocationUpdatesOnly (coarsePermission, finePermission);
                }
                startActivity (new Intent(this, LoginDirAct.class));
                finish ();
            } else
            {
                createInadequatePermissionsDialog ();
                inadequatePermissionsDialog.show ();
            }
        }
    }
    @Override
    public void onResume ()
    {
        super.onResume ();
        //Log.v (Constants.LOGTAG, CLASSTAG + "onResume called");
    }


    private void createInadequatePermissionsDialog ()
    {
        inadequatePermissionsDialog =  new AlertDialog.Builder (this)
                .setTitle (R.string.inadequate_permissions_title)
                .setMessage (R.string.inadequate_permissions_message)
                .setPositiveButton (R.string.ok, new DialogInterface.OnClickListener ()
                {
                    @Override
                    public void onClick (DialogInterface dialog, int which)
                    {
                        handleCloseInadequatePermissionsDialog ();
                    }
                })
                .create ();
    }

    private void createNotificationChannel ()
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = getString (R.string.channel_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel =
                    new NotificationChannel (CHANNEL_ID, name, importance);
            channel.setDescription (getString (R.string.channel_description));
            NotificationManager notificationManager =
                    getSystemService (NotificationManager.class);
            notificationManager.createNotificationChannel (channel);
        }
    }

    private void handleAllLocationUpdates (boolean coarsePermission, boolean finePermission)
    {
        // foreground and background enabled.
        AppController app = (AppController) getApplication ();
        app.setHasBackgroundLocationPermission (true);
        app.setHasCoarseLocationPermission (coarsePermission);
        app.setHasFineLocationPermission (finePermission);
    }

    private void handleCloseInadequatePermissionsDialog ()
    {
        inadequatePermissionsDialog = null;
        finish ();
    }

    private void handleForegroundLocationUpdatesOnly ( boolean coarsePermission,
                                                       boolean finePermission )
    {
        // only foreground enabled.
        AppController app = (AppController) getApplication ();
        app.setHasCoarseLocationPermission (coarsePermission);
        app.setHasFineLocationPermission (finePermission);
    }

    @Contract(pure = true)
    @NonNull
    private List<String> permissionsDesired ()
    {
        AppController app = (AppController) getApplication ();

        app.setHasCoarseLocationPermission (
                ActivityCompat.checkSelfPermission (this, ACCESS_COARSE_LOCATION) ==
                        PERMISSION_GRANTED );

        app.setHasFineLocationPermission (
                ActivityCompat.checkSelfPermission (this, ACCESS_FINE_LOCATION) ==
                        PERMISSION_GRANTED );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {

            app.setHasBackgroundLocationPermission (
                    ActivityCompat.checkSelfPermission (this,
                            ACCESS_BACKGROUND_LOCATION) == PERMISSION_GRANTED );
        }

        // Now work out what to ask for
        List<String> result = new ArrayList<>();

        if (! app.isHasCoarseLocationPermission ())
        { result.add (ACCESS_COARSE_LOCATION); }

        if (! app.isHasFineLocationPermission ())
        { result.add (ACCESS_FINE_LOCATION); }


        if (! app.isHasBackgroundLocationPermission ())
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            { result.add (ACCESS_BACKGROUND_LOCATION); }
        }

        if (result.isEmpty () && (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q))
        { app.setHasBackgroundLocationPermission (true); }

        //Log.v ( Constants.LOGTAG,CLASSTAG + "permissionsDesired returns: " + result.toString () );

        return result;
    }

    private void requestLocationPermissions (@NonNull String [] permissions)
    {
        ActivityCompat.requestPermissions (this, permissions,
                REQUEST_CODE_PERMISSIONS );
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }



    @Override
    public void onStop() {
        super.onStop();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }
    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

}
