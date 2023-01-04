package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class QuickTellerPlanAct extends AppCompatActivity {
    private WebView mywebView;
    private Bundle bundle;
    private String planlink;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_quick_teller_plan);
        bundle= new Bundle();
        mywebView =  findViewById(R.id.quickT_plan);
        mywebView.setWebViewClient(new MyWebViewClient());
        WebSettings webViewSettings = mywebView.getSettings();
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSettings.setJavaScriptEnabled(true);
        bundle=getIntent().getExtras();
        webViewSettings.setPluginState(WebSettings.PluginState.ON);
        if(bundle !=null){
            planlink=bundle.getString("weblink");

        }
        if(planlink !=null){
            mywebView.loadUrl(planlink);


        }
        mywebView.setWebViewClient(new MyWebViewClient());
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if(planlink !=null){
                mywebView.loadUrl(planlink);

                if (planlink.equals(request.getUrl().getHost())) {
                    // This is my website, so do not override; let my WebView load the page
                    return false;
                }


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