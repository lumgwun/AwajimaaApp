package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MarketClasses.BusinessDeal;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;

public class BizDealDetailHostAct extends AppCompatActivity {
    private AppCompatTextView txtTimelineCount,txtInsuranceAmt,txtLogisticsAmt,txtTotalDealAmt,txtMilestoneCount,txtDealAcctBalance,txtTittle,txtSupport;
    private AppCompatButton btnMoreTimelines,btnMoreInsurance,btnMoreLogistics,btnMilestone,btnApproveReq;
    private LinearLayout layoutLive,layoutPP;
    private SwitchCompat switchAllowCall;
    private Bundle bundle;
    private static final String PREF_NAME = "awajima";
    private SharedPreferences userPreferences;
    private PrefManager prefManager;
    private Gson gson,gson1,gson2;
    private BusinessDeal businessDeal;
    private String json,json1,json2,SharedPrefUserName,SharedPrefUserPassword,SharedPrefUserMachine;
    private Profile userProfile;
    private MarketBusiness marketBusiness;
    private FloatingActionButton fabHome;
    private String tittle,tmCount;
    private  long dealAcctNo;
    private int bizDealID, milestones;
    private double totalAmt, logAmt, insAmt, goodsAmt,balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_biz_deal_host_chat);
        bundle=new Bundle();
        userProfile= new Profile();
        businessDeal= new BusinessDeal();
        marketBusiness= new MarketBusiness();
        fabHome = findViewById(R.id.fab_deal_home);
        txtTimelineCount = findViewById(R.id.txt_timelines);
        txtInsuranceAmt = findViewById(R.id.txt_insurance);
        txtLogisticsAmt = findViewById(R.id.txt_logistics);
        btnMoreTimelines = findViewById(R.id.timeline_hDeal);
        btnMoreInsurance = findViewById(R.id.hDeal_insurance);
        btnMoreLogistics = findViewById(R.id.hDeal_logistics);
        txtTotalDealAmt = findViewById(R.id.txt_total_deal);
        btnMilestone = findViewById(R.id.btn_milestomes);
        txtMilestoneCount = findViewById(R.id.tv_milestoneC);
        txtDealAcctBalance = findViewById(R.id.dealAcct_WalletD);
        layoutLive = findViewById(R.id.btn_goLiveDeal);
        layoutPP = findViewById(R.id.btn_pp);
        switchAllowCall = findViewById(R.id.avail_for_Deal_call);
        btnApproveReq = findViewById(R.id.btn_ApprovalReq);
        txtTittle = findViewById(R.id.tittle_bizD);
        txtSupport = findViewById(R.id.btn_deal_support);
        bundle= getIntent().getExtras();
        gson= new Gson();
        gson1= new Gson();

        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json1 = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson1.fromJson(json1, MarketBusiness.class);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        json2 = userPreferences.getString("LastBusinessDealUsed", "");
        businessDeal = gson2.fromJson(json2, BusinessDeal.class);

        if(businessDeal !=null){
            bizDealID=businessDeal.getDealID();
            tittle=businessDeal.getDealTittle();
            totalAmt=businessDeal.getDealTotalAmount();
            logAmt= businessDeal.getDealShippingCost();
            goodsAmt= businessDeal.getDealCostOfProduct();
            insAmt=businessDeal.getDealCostOfInsurance();
            balance= businessDeal.getDealBalance();
            milestones=businessDeal.getDealNoOfMileStone();
            dealAcctNo=businessDeal.getDealAcctNumber();
        }
        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnApproveReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnMoreTimelines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnMoreInsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnMoreLogistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnMilestone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        txtSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void LogisticsDetails(View view) {
    }

    public void InsuranceDetails(View view) {
    }

    public void TimelimesHistory(View view) {
    }

    public void MilestoneHistory(View view) {
    }
    public void getAppSupport(View view) {
    }

    public void OurPrivacyS(View view) {
    }
}