package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.skylightapp.Bookings.BoatTripListAct;
import com.skylightapp.Classes.Profile;
import com.skylightapp.CoachOffice;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.LoginActivity;
import com.skylightapp.MarketClasses.AttachmentPreviewAdapter;
import com.skylightapp.MarketClasses.ChatAdapter;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.MarketClasses.StrokedTextView;
import com.skylightapp.MarketClasses.UserProfileInfo;
import com.skylightapp.R;
import com.skylightapp.SignTabMainActivity;
import com.skylightapp.SignUpAct;

import org.jivesoftware.smack.ConnectionListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

import static com.skylightapp.BuildConfig.QUICKBLOX_ACCT_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_APP_ID;
import static com.skylightapp.BuildConfig.QUICKBLOX_AUTH_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_SECRET_KEY;

public class MarketChatTab extends TabActivity {
    private static final String TAG = MarketChatTab.class.getSimpleName();
    public static final String EXTRA_DIALOG_ID = "dialogId";

    private DBHelper dbHelper;
    private Bundle bizNameBundle;
    private int bizID,marketID;

    private int skipPagination = 0;
    private static final String APPLICATION_ID = QUICKBLOX_APP_ID;   //QUICKBLOX_APP_ID
    private static final String AUTH_KEY = QUICKBLOX_AUTH_KEY;
    private static final String AUTH_SECRET = QUICKBLOX_SECRET_KEY;
    private static final String ACCOUNT_KEY = QUICKBLOX_ACCT_KEY;
    public static final String USER_DEFAULT_PASSWORD = "quickblox";
    private UserProfileInfo userProfileInfo;
    private Profile userProfile;
    private MarketBusiness marketBusiness;
    private static final String PREF_NAME = "awajima";
    SharedPreferences userPreferences;
    Gson gson, gson1,gson2,gson3;
    String json, json1, json2;

    com.melnykov.fab.FloatingActionButton floatingActionButton;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    int PERMISSION_ALL33 = 2;
    String[] PERMISSIONS33 = {
            Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.SEND_SMS
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_market_chat_tab);
        bizNameBundle= new Bundle();
        gson = new Gson();
        gson1 = new Gson();
        gson2 = new Gson();
        gson3 = new Gson();
        userProfile= new Profile();
        marketBusiness= new MarketBusiness();
        userProfileInfo= new UserProfileInfo();
        QBSettings.getInstance().init(this, APPLICATION_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
        setTitle("Chat Arena");
        dbHelper = new DBHelper(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastUserProfileInfoUsed", "");
        userProfileInfo = gson1.fromJson(json1, UserProfileInfo.class);
        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson2.fromJson(json2, MarketBusiness.class);
        bizNameBundle=getIntent().getExtras();
        if(bizNameBundle !=null){
            bizID=bizNameBundle.getInt("MARKET_BIZ_ID");
            marketID=bizNameBundle.getInt("MARKET_ID");
        }
        TabHost tabhost = findViewById(android.R.id.tabhost);
        TabWidget tabs = findViewById(android.R.id.tabs);
        tabhost.setup(getLocalActivityManager());
        Resources resources = getResources();

        floatingActionButton = findViewById(R.id.fab_signinOut5);
        Intent intentSignUp = new Intent().setClass(this, BizDealComAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecSignUp = tabhost
                .newTabSpec("Business Chat")
                .setIndicator("", resources.getDrawable(R.drawable.ic_create_new))
                .setContent(intentSignUp);

        Intent intentSignIn = new Intent().setClass(this, ChatActEthernal.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecLogin = tabhost
                .newTabSpec("Socialize")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentSignIn);

        Intent intentChat = new Intent().setClass(this, CallActivityCon.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpChat = tabhost
                .newTabSpec("Sessions")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentChat);

        tabhost.addTab(tabSpecSignUp);
        tabhost.addTab(tabSpecLogin);
        tabhost.addTab(tabSpChat);

        tabhost.setCurrentTab(0);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loanIntent = new Intent(MarketChatTab.this, CoachOffice.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);

                loanIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loanIntent);

            }
        });
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Toast.makeText(MarketChatTab.this, tabId, Toast.LENGTH_SHORT).show();
            }
        });

    }
}