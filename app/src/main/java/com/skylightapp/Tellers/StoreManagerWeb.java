package com.skylightapp.Tellers;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.skylightapp.R;

public class StoreManagerWeb extends AppCompatActivity {
    private WebView mywebView;

    private static final boolean AUTO_HIDE = true;

    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_store_manager_web);
        /*getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();*/
        mywebView = (WebView) findViewById(R.id.webView_store1);
        WebSettings webSettings= mywebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mywebView.loadUrl("https://skylightciacs.com/store-manager/");
// Line of Code for opening links in app
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