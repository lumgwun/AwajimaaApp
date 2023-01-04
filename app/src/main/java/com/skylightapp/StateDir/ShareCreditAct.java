package com.skylightapp.StateDir;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.skylightapp.R;

public class ShareCreditAct extends AppCompatActivity {
    /*final String TAG = "Share";
    EditText shareEmail,shareAmount;
    TextView shareID,shareNames,inscrire,shareWasteApp;
    ImageView searchEmail;
    String loginEmail = "";
    CardView share;
    private ProgressDialog pd;
    public static TextView tv_credit;
    TextInputLayout inputEmail,inputCredit;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_share_credit);
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
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onStop() {
        super.onStop();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

}