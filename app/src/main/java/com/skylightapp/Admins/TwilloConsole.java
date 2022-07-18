package com.skylightapp.Admins;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.skylightapp.R;

public class TwilloConsole extends AppCompatActivity {
    private WebView mywebView;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_twillo_console);
        mywebView = findViewById(R.id.webView_twillo);
        /*getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();*/
        mywebView.setWebViewClient(new WebViewClient());
        WebSettings webViewSettings = mywebView.getSettings();
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setPluginState(WebSettings.PluginState.ON);
        mywebView.loadUrl("https://https://www.twilio.com/login/");
        mywebView.setWebViewClient(new WebViewClient());
    }

    //Code For Back Button
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