package com.skylightapp.Markets;

import static com.skylightapp.Classes.PinEntryView.ACCENT_ALL;
import static com.skylightapp.Classes.PinEntryView.ACCENT_CHARACTER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.quickblox.users.model.QBUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MarketClasses.BizDealMileStone;
import com.skylightapp.MarketClasses.BusinessDeal;
import com.skylightapp.MarketClasses.MarketBizSubScription;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;


import java.io.FileInputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BizDealMileStoneAct extends AppCompatActivity {
    private Bundle bundle;
    java.util.ArrayList<Double> milestonesAmts;
    private BusinessDeal businessDeal;
    private int childcount,noOfMilestones,codeDigit,codeID,bizDealID;
    private static final String PREF_NAME = "awajima";
    private SharedPreferences userPreferences;
    private PrefManager prefManager;
    private Gson gson,gson1,gson2;
    private String json,json1,json2,SharedPrefUserName,SharedPrefUserPassword,SharedPrefUserMachine;
    private Profile bizProfile;
    private QBUser qbUserOfBiz;
    private MarketBizSubScription subScription;
    private MarketBusiness marketBusiness;
    private double productAmt,calAmount,amount;
    private AppCompatButton buttonAdd;
    private boolean isComplete;
    private ArrayList<Integer> codeList;
    private BizDealMileStone bizDealMileStone;
    private ArrayList<BizDealMileStone> mileStones;
    private ArrayList<BizDealMileStone> mileStonesNew;
    private SecureRandom random;
    private Random ran;
    private String dealTittle ,dealCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_biz_deal_mile_stone);
        bundle= new Bundle();
        ran = new Random();
        random = new SecureRandom();
        businessDeal=new BusinessDeal();
        bizDealMileStone= new BizDealMileStone();
        subScription= new MarketBizSubScription();

        codeList = new ArrayList<Integer>();
        milestonesAmts = new ArrayList<Double>();
        mileStones= new ArrayList<>();
        mileStonesNew= new ArrayList<>();

        bundle=getIntent().getExtras();
        gson= new Gson();
        gson1= new Gson();
        bizProfile= new Profile();
        qbUserOfBiz= new QBUser();
        marketBusiness= new MarketBusiness();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        bizProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson1.fromJson(json1, MarketBusiness.class);

        json2 = userPreferences.getString("LastMarketBizSubScriptionUsed", "");
        subScription = gson2.fromJson(json2, MarketBizSubScription.class);

        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");

        if(bundle !=null){
            businessDeal=bundle.getParcelable("BusinessDeal");
        }
        if(businessDeal !=null){
            productAmt=businessDeal.getDealCostOfProduct();
            noOfMilestones=businessDeal.getDealNoOfMileStone();
            bizDealID=businessDeal.getDealID();
            dealTittle=businessDeal.getDealTittle();
            dealCurrency= businessDeal.getDealCurrency();
        }

        ScrollView scrollView = findViewById(R.id.mS_ScrollL);
        codeDigit = ThreadLocalRandom.current().nextInt(122, 1631);
        codeID = random.nextInt((int) (Math.random() * 123) + 1101);
        buttonAdd = findViewById(R.id.add_milestoneV);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childcount = scrollView.getChildCount();

                for (int i = 0; i < childcount; i++) {
                    codeDigit=codeDigit++;
                    codeList.add(codeDigit);
                    LinearLayout innerLayout = (LinearLayout) scrollView.getChildAt(i);
                    EditText edit = innerLayout.findViewById(R.id.mS_editTextL);

                    EditText editText = new EditText(BizDealMileStoneAct.this);
                    editText .setId(i);
                    editText .setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                            1f));
                    editText .setWidth(100);
                    editText .setImeOptions(EditorInfo.IME_ACTION_NEXT);
                    editText .setInputType(InputType.TYPE_CLASS_NUMBER);
                    editText .setKeyListener(DigitsKeyListener.getInstance());
                    editText .setMaxLines(1);
                    editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean b) {
                            int length = editText.getText().length();
                            for (int i = 0; i < noOfMilestones; i++) {

                                /*getChildAt(i).setSelected(hasFocus && (accentType == ACCENT_ALL ||
                                        (accentType == ACCENT_CHARACTER && (i == length ||
                                                (i == noOfMilestones - 1 && length == noOfMilestones)))));*/
                            }


                            editText.setSelection(length);


                        }
                    });
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                            calAmount =Double.parseDouble(edit.getText().toString().trim())+calAmount;
                        }

                        @Override public void afterTextChanged(Editable s) {
                            calAmount =Double.parseDouble(edit.getText().toString().trim())+calAmount;
                            amount= Double.parseDouble(edit.getText().toString().trim());
                            int length = s.length();
                            for (int i = 0; i < noOfMilestones; i++) {
                                if (s.length() > i) {
                                    /*String mask = PinEntryView.this.mask == null || PinEntryView.this.mask.length() == 0 ?
                                            String.valueOf(s.charAt(i)) : PinEntryView.this.mask;
                                    ((TextView) getChildAt(i)).setText(mask);*/
                                } else {
                                   // ((TextView) getChildAt(i)).setText("");
                                }
                                /*if (editText.hasFocus()) {
                                    getChildAt(i).setSelected(accentType == ACCENT_ALL ||
                                            (accentType == ACCENT_CHARACTER && (i == length ||
                                                    (i == noOfMilestones - 1 && length == noOfMilestones))));
                                }*/
                            }

                            if (length == noOfMilestones && productAmt ==calAmount) {
                                isComplete=true;
                            }
                        }
                    });
                    editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                            return false;
                        }
                    });
                    amount= Double.parseDouble(edit.getText().toString().trim());
                    milestonesAmts.add(Double.valueOf(edit.getText().toString().trim()));


                }
                if(noOfMilestones >0){

                    try {
                        for (int i = 0; i < noOfMilestones; i++) {
                            amount=milestonesAmts.get(i).longValue();
                        }
                    } catch (NumberFormatException e) {
                        //
                    }






                }
                bizDealMileStone= new BizDealMileStone(codeID,bizDealID,codeDigit,amount);
                mileStonesNew.add(bizDealMileStone);
                //businessDeal.setBizDealMileStones();


            }
        });
    }

    public void onAddMilestone(View view) {
    }
}