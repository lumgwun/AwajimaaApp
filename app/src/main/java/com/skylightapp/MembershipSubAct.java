package com.skylightapp;

import static com.skylightapp.Classes.AppConstants.GOLD_LIFE;
import static com.skylightapp.Classes.AppConstants.PREMIUM_LIFE;
import static com.skylightapp.Classes.AppConstants.SIX_GOLD;
import static com.skylightapp.Classes.AppConstants.SIX_PREMIUM;
import static com.skylightapp.Classes.Utils.MEMBERSHIP_SUB;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.NewCustomerDrawer;

public class MembershipSubAct extends AppCompatActivity {
    private WebView mywebView;
    private String subTypeLink;
    private SharedPreferences userPreferences;
    private static final String PREF_NAME = "awajima";
    private Gson gson;
    private String json, selectedPlan,link;
    private Profile userProfile;
    int profileID;
    AppCompatSpinner spnMembership;
    private FloatingActionButton floatingActionButton;
    private AppCompatButton btnSubmit;
    private int index=0;

    private AdapterView.OnItemSelectedListener membership_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (index ==position) {
                return;

            }else {
                selectedPlan = spnMembership.getSelectedItem().toString();
            }
            if(selectedPlan !=null){
                doMProcessing(selectedPlan);

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    };


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_membership_sub);
        gson = new Gson();
        userProfile= new Profile();
        floatingActionButton = findViewById(R.id.fab_membership);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        spnMembership = findViewById(R.id.m_Spinner);
        btnSubmit = findViewById(R.id.p_Make_Sub);
        spnMembership.setOnItemSelectedListener(membership_listener);
        btnSubmit.setOnClickListener(this::doMPayment);
        floatingActionButton.setOnClickListener(this::doMSubHome);

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void doMProcessing(String selectedPlan) {
        mywebView =  findViewById(R.id.membership_webview);
        if(selectedPlan !=null){
            if(selectedPlan.equalsIgnoreCase("Premium")){
                link=SIX_PREMIUM;
            }
            if(selectedPlan.equalsIgnoreCase("Premium Life")){
                link=PREMIUM_LIFE;
            }
            if(selectedPlan.equalsIgnoreCase("Gold")){
                link=SIX_GOLD;
            }
            if(selectedPlan.equalsIgnoreCase("Gold Life")){
                link=GOLD_LIFE;
            }
            if(selectedPlan.equalsIgnoreCase("General")){
                link=MEMBERSHIP_SUB;
            }

        }



    }


    @Override
    public void onBackPressed() {
        if(mywebView.canGoBack())
        {
            mywebView.goBack();
        }

        else
        {
            super.onBackPressed();
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    public void doMPayment(View view) {
        if(link !=null){
            mywebView.setVisibility(View.VISIBLE);
            mywebView.setWebViewClient(new WebViewClient());
            WebSettings webViewSettings = mywebView.getSettings();
            webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webViewSettings.setJavaScriptEnabled(true);
            webViewSettings.setPluginState(WebSettings.PluginState.ON);
            mywebView.loadUrl(link);
            mywebView.setWebViewClient(new WebViewClient());
        }
    }

    public void doMSubHome(View view) {
        gson = new Gson();
        userProfile= new Profile();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", profileID);
        bundle.putString(machine, machine);
        Intent intent = new Intent(this, NewCustomerDrawer.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}