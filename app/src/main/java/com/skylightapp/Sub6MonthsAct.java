package com.skylightapp;

import static com.skylightapp.Classes.AppConstants.SINGLE_MONTH_NR;
import static com.skylightapp.Classes.AppConstants.SIX_MONTHS_R;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Sub6MonthsAct extends AppCompatActivity {
    private WebView mywebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sub6_months);
        mywebView =  findViewById(R.id.web_6_sub);
        mywebView.setWebViewClient(new WebViewClient());
        WebSettings webViewSettings = mywebView.getSettings();
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setPluginState(WebSettings.PluginState.ON);
        mywebView.loadUrl(SIX_MONTHS_R);
        mywebView.setWebViewClient(new WebViewClient());
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
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

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