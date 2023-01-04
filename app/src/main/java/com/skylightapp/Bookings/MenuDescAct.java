package com.skylightapp.Bookings;

import static com.skylightapp.Bookings.BookingConstant.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.android.volley.VolleyError;
import com.skylightapp.Classes.AppLog;
import com.skylightapp.R;

public class MenuDescAct extends ActionBarBaseAct {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_menu_desc);
        setIcon(R.drawable.ic_arrow_back_black_24dp);
        setIconMenu(R.drawable.ic_taxi);
        setTitle(getIntent().getStringExtra(BookingConstant.Params.TITLE));
        webView = (WebView) findViewById(R.id.wvDesc);
        webView.loadData(getIntent().getStringExtra(BookingConstant.Params.CONTENT),
                "text/html", "utf-8");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnActionNotification:
                onBackPressed();
                break;

            default:
                break;
        }
    }

    @Override
    protected boolean isValidate() {
        return false;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // TODO Auto-generated method stub
        AppLog.Log(TAG, error.getMessage());

    }
}