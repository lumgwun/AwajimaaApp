package com.skylightapp.Markets;

import static java.lang.String.valueOf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.chat.utils.DialogUtils;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBPagedRequestBuilder;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.BizDealDAO;
import com.skylightapp.Database.BizDealPartnerDAO;
import com.skylightapp.MarketClasses.BizDealPartner;
import com.skylightapp.MarketClasses.BizDPartnerSelectAd;
import com.skylightapp.MarketClasses.BizDealPartnerSpnA;
import com.skylightapp.MarketClasses.BusinessDeal;
import com.skylightapp.MarketClasses.ChatHelper;
import com.skylightapp.MarketClasses.CreateBizDealGrpDialog;
import com.skylightapp.MarketClasses.CustomDialog;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.MarketInterfaces.DealPartnerViewInt;
import com.skylightapp.R;

import org.jivesoftware.smack.ConnectionListener;

import java.util.ArrayList;

public class BizDSelectPartnerAct extends AppCompatActivity implements DealPartnerViewInt, BizDPartnerSelectAd.OnPartnerClickListener,CreateBizDealGrpDialog.GroupNameInterface {
    private RecyclerView recyclerView;
    private AppCompatButton addUsers;
    private ArrayList<BizDealPartner> userSelectedList;
    private ArrayList<BizDealPartner> bizDealPartners ;
    private ArrayList<BizDealPartner> selectedBizDealPartners ;
    ArrayList<String> phoneNumbers;
    private String groupName="";
    private ShimmerFrameLayout shimmerFrameLayout, shimmerBotomSheet;
    private static final String TAG ="BizDSelectPartnerAct";
    private BizDealPartnerSpnA bizDealPartnerSpnA;
    private BizDealPartnerSpnA adapter;
    private Profile marketBizProfile,senderProfile;
    private static final String PREF_NAME = "awajima";
    private SharedPreferences userPreferences;
    private Gson gson,gson1,gson2;
    private String json,json1,json2;
    private Profile bizProfile;
    private BottomSheetBehavior mBottomSheetBehavior;
    private BizDPartnerSelectAd bizDPartnerSelectAd;
    private BizDPartnerSelectAd bizDealPartnerADB;
    private ScrollView scrollView;
    private AppCompatImageButton btnCloseBSheet;
    private AppCompatButton btnAddPartner;
    private MarketBusiness marketBusiness;
    private BizDPartnerSelectAd.OnPartnerClickListener listener;
    private long bizID;
    private Bundle bundle;
    private BusinessDeal businessDeal;
    private String bizDealTittle;
    private BizDealPartnerDAO bizDealPartnerDAO;
    private QBChatDialog createdChatDialog,qbChatDialog;
    private String dialogType;
    private ArrayList<QBChatDialog> bizDealChatDialogs ;
    private String chatPartnerName;
    private BizDealDAO bizDealDAO;
    private QBUser hostQBUser;
    private int partnerQBId,bizDealID, hostQBId;
    private double dealAmount;
    private String dealCurrency,dateCreated;
    private MarketBusiness fromBiz,toBiz;
    private long fromBizID,toBizID;
    private ConnectionListener chatConnectionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_biz_deal_partners_list);
        bundle= new Bundle();
        gson= new Gson();
        gson1= new Gson();
        bizProfile= new Profile();
        fromBiz= new MarketBusiness();
        hostQBUser= new QBUser();
        bizDealDAO= new BizDealDAO(this);
        userSelectedList = new ArrayList<>();
        bizDealPartners = new ArrayList<>();
        selectedBizDealPartners = new ArrayList<>();
        bizDealChatDialogs = new ArrayList<>();
        businessDeal= new BusinessDeal();
        createdChatDialog = new QBChatDialog();
        qbChatDialog = new QBChatDialog();

        bizDealPartnerDAO= new BizDealPartnerDAO(this);

        shimmerFrameLayout = findViewById(R.id.shimmerLayoutUserList);
        shimmerBotomSheet = findViewById(R.id.shimmerPList);

        btnCloseBSheet = findViewById(R.id.btn_closeBD_Sheet);
        btnAddPartner = findViewById(R.id.buttonGetDealsP);

        scrollView = findViewById(R.id.bd_scrollV);
        mBottomSheetBehavior = BottomSheetBehavior.from(scrollView);
        recyclerView = findViewById(R.id.recyDealP);
        //recyclerViewPhoneNo = findViewById(R.id.listUserRecyclerView);
        addUsers = findViewById(R.id.createChat);
        bundle=getIntent().getExtras();

        marketBusiness= new MarketBusiness();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json1 = userPreferences.getString("LastProfileUsed", "");
        bizProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson1.fromJson(json1, MarketBusiness.class);
        json2 = userPreferences.getString("LastQBUserUsed", "");
        hostQBUser = gson2.fromJson(json2, QBUser.class);
        if(marketBusiness !=null){
            bizID =marketBusiness.getBusinessID();
            bizDealPartners= marketBusiness.getBizDealPartners();
        }
        if(bizDealPartnerDAO !=null){
            userSelectedList= bizDealPartnerDAO.getAllBizDealPartners();

        }
        if(bizProfile !=null){
            hostQBUser =bizProfile.getProfQbUser();
        }
        if(hostQBUser !=null){
            hostQBId=hostQBUser.getId();
        }


        btnCloseBSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                } else {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

            }
        });
        if(bundle !=null){
            businessDeal=bundle.getParcelable("BusinessDeal");
        }
        if(businessDeal !=null){
            bizDealTittle=businessDeal.getDealTittle();
            bizDealChatDialogs=businessDeal.getBizDealChatDialogs();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose where to add Biz Partners, from");
        builder.setIcon(R.drawable.user_red);
        builder.setItems(new CharSequence[]
                        {"From your Awajima Partners", "From your Awajima Database"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                scrollView = findViewById(R.id.bd_scrollV);
                                scrollView.setVisibility(View.VISIBLE);
                                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                } else {
                                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                }
                                Toast.makeText(BizDSelectPartnerAct.this, "Getting Your Awajima Partners", Toast.LENGTH_SHORT).show();
                                shimmerBotomSheet.startShimmer();
                                selectedBizDealPartners=bizDealPartners;
                                bizDPartnerSelectAd = new BizDPartnerSelectAd(BizDSelectPartnerAct.this,selectedBizDealPartners,listener);
                                LinearLayoutManager layout = new LinearLayoutManager (BizDSelectPartnerAct.this);
                                recyclerView.setLayoutManager (layout);
                                recyclerView.setAdapter (bizDPartnerSelectAd);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.addItemDecoration(new DividerItemDecoration(BizDSelectPartnerAct.this,
                                        DividerItemDecoration.VERTICAL));
                                SnapHelper snapHelper = new PagerSnapHelper();
                                snapHelper.attachToRecyclerView(recyclerView);
                                recyclerView.setNestedScrollingEnabled(false);
                                recyclerView.invalidate ();
                                shimmerBotomSheet.setVisibility(View.GONE);
                                recyclerView.fling(30,30);


                                break;
                            case 1:
                                Toast.makeText(BizDSelectPartnerAct.this, "Getting Your Partners from the Database", Toast.LENGTH_SHORT).show();
                                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                } else {
                                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                                }
                                shimmerBotomSheet.startShimmer();
                                selectedBizDealPartners=userSelectedList;
                                scrollView = findViewById(R.id.bd_scrollV);
                                scrollView.setVisibility(View.VISIBLE);
                                bizDealPartnerADB = new BizDPartnerSelectAd(BizDSelectPartnerAct.this,selectedBizDealPartners,listener);
                                LinearLayoutManager layoutDB = new LinearLayoutManager (BizDSelectPartnerAct.this);
                                recyclerView.setLayoutManager (layoutDB);
                                recyclerView.setAdapter (bizDealPartnerADB);
                                recyclerView.addItemDecoration(new DividerItemDecoration(BizDSelectPartnerAct.this,
                                        DividerItemDecoration.VERTICAL));
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                SnapHelper snapHelperDB = new PagerSnapHelper();
                                snapHelperDB.attachToRecyclerView(recyclerView);
                                recyclerView.setNestedScrollingEnabled(false);
                                recyclerView.invalidate ();
                                shimmerBotomSheet.setVisibility(View.GONE);
                                recyclerView.fling(90,90);
                                break;

                        }
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        builder.create().show();
        btnAddPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = selectedBizDealPartners.size();
                if(count==1)
                {
                    createOneToOneChat();
                }
                else if(count>1)
                {
                    openCreateGroupDialog();
                }
                else
                {
                    Toast.makeText(BizDSelectPartnerAct.this,"Select atleast one",Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnAddPartner.setOnClickListener(this::getBizDealPartnerN);


        addUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = selectedBizDealPartners.size();
                if(count==1)
                {
                    createOneToOneChat();
                }
                else if(count>1)
                {
                    openCreateGroupDialog();
                }
                else
                {
                    Toast.makeText(BizDSelectPartnerAct.this,"Select atleast one",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void initToolbar(String title) {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();

        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
            actionbar.setTitle(title);
        }

        // set title

    }


    @Override
    public void setViewHidden(boolean enabled, int color) {
        /*LinearLayout courseView = findViewById(R.id.course_view);
        LinearLayout l = findViewById(R.id.layout_course);
        l.setBackgroundColor(getResources().getColor(color));
        if (enabled) {
            courseView.setVisibility(View.GONE);
        }
        else {
            courseView.setVisibility(View.VISIBLE);
            initToolbar(mCourseCode);
        }*/
    }


    private void openCreateGroupDialog() {
        Log.d(TAG,"on create group dialog");
        CreateBizDealGrpDialog groupDialog = new CreateBizDealGrpDialog();
        groupDialog.show(getSupportFragmentManager(),"Dialog");
    }


    @Override
    protected void onResume() {
        super.onResume();
        ChatHelper.getInstance().addConnectionListener(chatConnectionListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ChatHelper.getInstance().removeConnectionListener(chatConnectionListener);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
    private void createGroupChat() {
        createdChatDialog = new QBChatDialog();
        bizDealDAO= new BizDealDAO(this);
        if(bundle !=null){
            businessDeal=bundle.getParcelable("BusinessDeal");
        }
        if(businessDeal !=null){
            bizDealTittle=businessDeal.getDealTittle();
            bizDealChatDialogs=businessDeal.getBizDealChatDialogs();
            bizDealID=businessDeal.getDealID();
            dealAmount=businessDeal.getDealTotalAmount();
            dealCurrency=businessDeal.getDealCurrency();
            fromBiz=businessDeal.getFromBiz();
            toBiz=businessDeal.getToBiz();
        }
        if(toBiz !=null){
            toBizID=toBiz.getBusinessID();
        }
        if(fromBiz !=null){
            fromBizID=fromBiz.getBusinessID();
        }
        if(bizProfile !=null){
            hostQBUser =bizProfile.getProfQbUser();
        }
        if(hostQBUser !=null){
            hostQBId=hostQBUser.getId();
        }
        /*final ProgressDialog dialog = new ProgressDialog(BizDSelectPartnerAct.this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();*/

        groupName = bizDealTittle;
        Log.d(TAG,"groupName : " + groupName);
        //openCreateGroupDialog();
        final CustomDialog dialog = new CustomDialog(BizDSelectPartnerAct.this);
        dialog.showDialog();
        ArrayList<Integer> usersIdList = new ArrayList<>();
        for(BizDealPartner user : selectedBizDealPartners) {
            usersIdList.add(user.getUser().getId());
        }
        QBChatDialog chatDialog = new QBChatDialog();
        //chatDialog.setName("Group: "+userSelectedList.get(0).getUser().getFullName()+","
        //     +userSelectedList.get(1).getUser().getFullName());
        chatDialog.setOccupantsIds(usersIdList);
        chatDialog.setName(groupName);
        chatDialog.setType(QBDialogType.GROUP);

        for (int i = 0; i < bizDealChatDialogs.size(); i++) {
            try {
                if (bizDealChatDialogs.get(i).getName().equalsIgnoreCase(chatPartnerName)) {
                    Toast.makeText(BizDSelectPartnerAct.this, "This Chat is already on the Deal List, here", Toast.LENGTH_LONG).show();
                    return;

                } else {
                    QBRestChatService.createChatDialog(chatDialog).performAsync(new QBEntityCallback<QBChatDialog>() {
                        @Override
                        public void onSuccess(QBChatDialog qbChatDialog, Bundle bundle) {
                            createdChatDialog=qbChatDialog;
                            if(createdChatDialog !=null){
                                dateCreated=createdChatDialog.getFCreatedAt();
                            }
                            businessDeal.addQBChatDialog(createdChatDialog);
                            if(bizDealDAO !=null){
                                bizDealDAO.saveBizDealChat(createdChatDialog,hostQBId,bizDealID,dealAmount,dealCurrency,fromBizID,toBizID);

                            }
                            dialog.hideDialog();
                            Toast.makeText(BizDSelectPartnerAct.this,"Successfully created",Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void onError(QBResponseException e) {

                        }
                    });



                }
            } catch (NullPointerException e) {
                System.out.println("Oops!");
            }

        }

        QBRestChatService.createChatDialog(chatDialog).performAsync(new QBEntityCallback<QBChatDialog>() {
            @Override
            public void onSuccess(QBChatDialog qbChatDialog, Bundle bundle) {
                createdChatDialog=qbChatDialog;
                businessDeal.addQBChatDialog(createdChatDialog);
                dialog.hideDialog();
                Toast.makeText(BizDSelectPartnerAct.this,"Group created successfully",Toast.LENGTH_SHORT).show();
                finish();
            }
            @Override
            public void onError(QBResponseException e) {
                Log.d(TAG, "onError: "+e.getLocalizedMessage());
            }
        });
    }


    private void createOneToOneChat() {
        createdChatDialog = new QBChatDialog();
        bizDealDAO= new BizDealDAO(this);
        if(bundle !=null){
            businessDeal=bundle.getParcelable("BusinessDeal");
        }
        if(businessDeal !=null){
            bizDealTittle=businessDeal.getDealTittle();
            bizDealChatDialogs=businessDeal.getBizDealChatDialogs();
            bizDealID=businessDeal.getDealID();
            dealAmount=businessDeal.getDealTotalAmount();
            dealCurrency=businessDeal.getDealCurrency();
            fromBiz=businessDeal.getFromBiz();
            toBiz=businessDeal.getToBiz();
        }
        if(toBiz !=null){
            toBizID=toBiz.getBusinessID();
        }
        if(fromBiz !=null){
            fromBizID=fromBiz.getBusinessID();
        }
        if(bizProfile !=null){
            hostQBUser =bizProfile.getProfQbUser();
        }
        if(hostQBUser !=null){
            hostQBId=hostQBUser.getId();
        }
        final ProgressDialog dialog = new ProgressDialog(BizDSelectPartnerAct.this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        QBUser user = selectedBizDealPartners.get(0).getUser();
        if(user !=null){
            chatPartnerName=user.getFullName();
            partnerQBId=user.getId();
        }

        Log.d("_________","0 th position name :"+ user.getFullName());
        QBChatDialog userDialog = DialogUtils.buildPrivateDialog(user.getId());
        for (int i = 0; i < bizDealChatDialogs.size(); i++) {
            try {
                if (bizDealChatDialogs.get(i).getName().equalsIgnoreCase(chatPartnerName)) {
                    Toast.makeText(BizDSelectPartnerAct.this, "This Chat is already on the Deal List, here", Toast.LENGTH_LONG).show();
                    return;

                } else {
                    QBRestChatService.createChatDialog(userDialog).performAsync(new QBEntityCallback<QBChatDialog>() {
                        @Override
                        public void onSuccess(QBChatDialog qbChatDialog, Bundle bundle) {
                            createdChatDialog=qbChatDialog;
                            if(createdChatDialog !=null){
                                dateCreated=createdChatDialog.getFCreatedAt();
                            }
                            businessDeal.addQBChatDialog(createdChatDialog);
                            if(bizDealDAO !=null){
                                bizDealDAO.saveBizDealChat(createdChatDialog,hostQBId,bizDealID,dealAmount,dealCurrency,fromBizID,toBizID);

                            }
                            dialog.dismiss();
                            Toast.makeText(BizDSelectPartnerAct.this,"Successfully created",Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void onError(QBResponseException e) {

                        }
                    });



                }
            } catch (NullPointerException e) {
                System.out.println("Oops!");
            }

        }




    }


    @Override
    public void UserSelected(BizDealPartner dealPartner, int position) {
        scrollView = findViewById(R.id.bd_scrollV);
        scrollView.setVisibility(View.VISIBLE);
        if(bundle !=null){
            businessDeal=bundle.getParcelable("BusinessDeal");
        }
        if(selectedBizDealPartners.contains(dealPartner)) {
            selectedBizDealPartners.remove(dealPartner);
        }
        else
            selectedBizDealPartners.add(dealPartner);

        if (selectedBizDealPartners.size()==1||selectedBizDealPartners.size()==0)
            addUsers.setText("Message");
        else
            addUsers.setText("Create Group");

        businessDeal.addBizDealPartner(dealPartner);
        bizDealPartnerADB.notifyItemRemoved(position);

    }

    @Override
    public void UserSelected(BizDealPartner bizDealPartner) {
        scrollView = findViewById(R.id.bd_scrollV);
        scrollView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onCountSelectedItemsChanged(Integer count) {
        scrollView = findViewById(R.id.bd_scrollV);
        scrollView.setVisibility(View.VISIBLE);

    }

    @Override
    public void sendGroupName(String name) {
        groupName = name;
        Log.d(TAG, "sendGroupName: "+name);
        createGroupChat();

    }

    public void getBizDealPartnerN(View view) {
    }

    public void CreateChatDialog(View view) {
        int count = selectedBizDealPartners.size();
        if(count==1)
        {
            createOneToOneChat();
        }
        else if(count>1)
        {
            openCreateGroupDialog();
        }
        else
        {
            Toast.makeText(BizDSelectPartnerAct.this,"Select atleast one",Toast.LENGTH_SHORT).show();
        }
    }
}