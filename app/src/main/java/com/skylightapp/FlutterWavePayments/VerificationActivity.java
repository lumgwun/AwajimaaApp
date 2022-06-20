package com.skylightapp.FlutterWavePayments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.skylightapp.Interfaces.VerificationComponent;
import com.skylightapp.R;

public class VerificationActivity extends AppCompatActivity {
    private static final String TAG = VerificationActivity.class.getName();
    public static final String ACTIVITY_MOTIVE = "activityMotive";
    public static final String PUBLIC_KEY_EXTRA = "publicKey";
    public static final String INTENT_SENDER = "sender";
    public static String BASE_URL;
    private Fragment fragment;
    VerificationComponent verificationComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_verification);
        if (findViewById(R.id.frame_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            if (getIntent() != null & getIntent().getStringExtra(ACTIVITY_MOTIVE) != null) {
                switch (getIntent().getStringExtra(ACTIVITY_MOTIVE).toLowerCase()) {
                    case "otp":
                        fragment = new OTPFragment();
                        fragment.setArguments(getIntent().getExtras());
                        break;
                    case "pin":
                        fragment = new PinFragment();
                        fragment.setArguments(getIntent().getExtras());
                        break;
                    default:
                        Log.e(TAG, "No extra value matching motives");
                }
            }

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frame_container, fragment).commit();
            } else {
                finish();
            }
        }
    }
    public VerificationComponent getVerificationComponent() {
        return verificationComponent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}