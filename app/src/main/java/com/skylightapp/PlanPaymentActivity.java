package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class PlanPaymentActivity extends AppCompatActivity {
    private WebView mywebView;
    private TextView txtRate;
    private static final String QUICKTELLER_PAY = "https://business.quickteller.com/link/pay/LumgwunMa0s9";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_plan_payment);
        setTitle("Quick Teller Payment");
        mywebView = (WebView) findViewById(R.id.webView_sub_plan);
        txtRate =  findViewById(R.id.app_rate);
        mywebView.setWebViewClient(new WebViewClient());
        WebSettings webViewSettings = mywebView.getSettings();
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSettings.setJavaScriptEnabled(true);
        mywebView.loadUrl(QUICKTELLER_PAY);
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

    }
}