package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PrivacyPolicy_Web extends AppCompatActivity {

    private WebView mywebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_privacy_policy);
        mywebView =  findViewById(R.id.webview_privacy_policy);
        mywebView.setWebViewClient(new MyWebViewClient());
        WebSettings webViewSettings = mywebView.getSettings();
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setPluginState(WebSettings.PluginState.ON);
        mywebView.loadUrl("https://docs.google.com/document/d/1G8L2qti36yVtxGFJOQR0NV1RA0eQiK2DZFpZQj_Metw/edit?usp=sharing");
        mywebView.setWebViewClient(new MyWebViewClient());
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if ("https://docs.google.com/document/d/1G8L2qti36yVtxGFJOQR0NV1RA0eQiK2DZFpZQj_Metw/edit?usp=sharing".equals(request.getUrl().getHost())) {
                // This is my website, so do not override; let my WebView load the page
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
            startActivity(intent);
            return true;
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
}