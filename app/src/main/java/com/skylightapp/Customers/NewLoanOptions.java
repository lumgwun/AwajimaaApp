package com.skylightapp.Customers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.MembershipSubAct;
import com.skylightapp.R;
import com.skylightapp.SubHistoryAct;
import com.skylightapp.SuperAdmin.Awajima;

public class NewLoanOptions extends AppCompatActivity implements  View.OnClickListener{
    private Bundle loanBundle;
    private static final String PREF_NAME = "awajima";
    Gson gson, gson1,gson3,gson6;
    String json, json1, json3;
    Profile userProfile, customerProfile, lastProfileUsed;
    private Awajima awajima;
    private SharedPreferences userPreferences;
    private AppCompatButton btnSubmit,btnHistory,btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_new_loan_options);
        loanBundle= new Bundle();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson1 = new Gson();
        gson3= new Gson();
        userProfile= new Profile();
        Animation translater = AnimationUtils.loadAnimation(this, R.anim.bounce);
        Animation translER = AnimationUtils.loadAnimation(this, R.anim.pro_animation);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        btnSubmit = findViewById(R.id.btnSub_New);
        btnHistory = findViewById(R.id.btn_HistorySub);
        btnHome = findViewById(R.id.home_button);

        btnSubmit.setOnClickListener(this);

        btnHistory.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        //doSubOptions();


    }
    @Override
    public void onClick(View view) {
        loanBundle= new Bundle();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson1 = new Gson();
        gson3= new Gson();
        userProfile= new Profile();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        switch (view.getId()) {
            case R.id.btnSub_New:
                loanBundle.putParcelable("Profile",userProfile);
                Intent newIntent = new Intent(NewLoanOptions.this, MembershipSubAct.class);
                Toast.makeText(NewLoanOptions.this, "New Membership Sub.", Toast.LENGTH_SHORT).show();
                newIntent.putExtras(loanBundle);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent);

                break;
            case R.id.btn_HistorySub:
                loanBundle.putParcelable("Profile",userProfile);
                Intent historyIntent = new Intent(NewLoanOptions.this, SubHistoryAct.class);
                Toast.makeText(NewLoanOptions.this, "Subscription History", Toast.LENGTH_SHORT).show();
                historyIntent.putExtras(loanBundle);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                historyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                historyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(historyIntent);

                break;

            case R.id.home_button:
                loanBundle.putParcelable("Profile",userProfile);
                Intent hIntent = new Intent(NewLoanOptions.this, NewCustomerDrawer.class);
                Toast.makeText(NewLoanOptions.this, "Customer Dashboard", Toast.LENGTH_SHORT).show();
                hIntent.putExtras(loanBundle);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                hIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                hIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(hIntent);
        }

    }

    private void doSubOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Membership Subscription");
        builder.setIcon(R.drawable.transaction);
        builder.setItems(new CharSequence[]
                        {"New Membership", "My Subscriptions"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:

                                break;
                            case 1:


                        }
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();

                    }
                });

        builder.create().show();


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onPause() {
        super.onPause();
        //overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onStop() {
        super.onStop();
        //overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }
    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.base_slide_left_out, R.anim.bounce);

    }
    public void onResume(){
        super.onResume();
        overridePendingTransition(R.anim.base_slide_left_out, R.anim.bounce);
    }


}