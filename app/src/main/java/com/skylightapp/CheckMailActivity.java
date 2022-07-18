package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CheckMailActivity extends AppCompatActivity {
    WebView mywebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_check_mail);
        mywebView = (WebView) findViewById(R.id.webView_check_email);
        mywebView.setWebViewClient(new WebViewClient());
        WebSettings webViewSettings = mywebView.getSettings();
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSettings.setJavaScriptEnabled(true);
        mywebView.loadUrl("https://skylightciacs.com/webmail/");
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