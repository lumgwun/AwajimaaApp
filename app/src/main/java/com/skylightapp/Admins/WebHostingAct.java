package com.skylightapp.Admins;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.skylightapp.R;

public class WebHostingAct extends AppCompatActivity {
    private WebView mywebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_web_hosting);
        /*getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();*/
        mywebView = findViewById(R.id.webView_web_hosting);
        mywebView.setWebViewClient(new WebViewClient());
        WebSettings webViewSettings = mywebView.getSettings();
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setPluginState(WebSettings.PluginState.ON);
        mywebView.loadUrl("https://www.whogohost.com/host/clientarea.php");
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