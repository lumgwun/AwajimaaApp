package com.skylightapp;

import static com.skylightapp.Classes.EditSavingsDialog.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


import com.google.gson.Gson;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.GrpSavingsProfile;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.GrpProfileDAO;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.Database.SODAO;
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.Database.TranXDAO;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class JoinThisGrpSavAct extends AppCompatActivity {
    private SharedPreferences userPreferences;
    Profile userProfile;
    Gson gson;
    String json,tittle,purpose,firstName,lastName,phoneNo;
    SecureRandom random;
    Random ran ;
    DBHelper dbHelper;
    int profileID;

    AppCompatTextView txtTittle,txtPurpose;
    private GroupAccount groupAccount;
    private AppCompatEditText edtUserID,edtFN,edtSurname;
    AppCompatButton btnAddNewSaver;
    int grpAcctID;
    int grpAccountProfileNo,userID;
    private SODAO sodao;
    private TranXDAO tranXDAO;
    private MessageDAO messageDAO;
    private TimeLineClassDAO timeLineClassDAO;
    private GrpProfileDAO grpProfileDAO;
    private String userIDStrng;
    private long dbID;
    private static final String PREF_NAME = "awajima";
    private static final String GROUP_ACCT_LINK="https://skylightbizapp.page.link/gs";
    private SQLiteDatabase sqLiteDatabase;
    private GrpSavingsProfile grpSavingsProfile;
    private int newGrpSavingsProfID;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_add_new_grps_profile);
        dbHelper = new DBHelper(this);
        bundle= new Bundle();
        txtTittle =  findViewById(R.id.grp_t);
        txtPurpose =  findViewById(R.id.grp_p);
        edtUserID =  findViewById(R.id.grpAcctNewUID);
        edtFN =  findViewById(R.id.grpNewFN);
        edtSurname =  findViewById(R.id.grpALN);
        btnAddNewSaver =  findViewById(R.id.grpAcctDoBtn);
        grpProfileDAO= new GrpProfileDAO(this);
        grpSavingsProfile=new GrpSavingsProfile();
        timeLineClassDAO= new TimeLineClassDAO(this);

        sodao= new SODAO(this);
        tranXDAO= new TranXDAO(this);
        sodao= new SODAO(this);
        messageDAO= new MessageDAO(this);

        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        //userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        userProfile=new Profile();
        random= new SecureRandom();

        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        bundle = getIntent().getExtras() ;
        if(userProfile !=null){
            profileID=userProfile.getPID();

        }

        if(bundle !=null){
            groupAccount = bundle.getParcelable("GroupAccount");
            firstName=bundle.getString("PROFILE_FIRSTNAME");
            userIDStrng=bundle.getString("PROFILE_ID");
            lastName=bundle.getString("PROFILE_SURNAME");
            edtFN.setText(firstName);
            edtSurname.setText(lastName);
            edtUserID.setText(userIDStrng);
            txtTittle.setText(MessageFormat.format("Grp Tittle:{0}", groupAccount.getGrpTittle()));
            txtPurpose.setText(MessageFormat.format("Grp Purpose:{0}", groupAccount.getGrpPurpose()));

        }
        if(groupAccount !=null){
            grpAccountProfileNo=groupAccount.getGrpAcctNo();
            grpAcctID=groupAccount.getGrpAcctNo();
        }

       /* FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent()).addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                        }
                        if (groupAccount == null
                                && deepLink != null
                                && deepLink.getBooleanQueryParameter("gs", false)) {
                            String referrerUid = deepLink.getQueryParameter("gs");
                            createAnonymousAccountWithReferrerInfo(referrerUid);
                        }

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "getDynamicLink:onFailure", e);
                    }
                });*/


        grpProfileDAO= new GrpProfileDAO(this);
        btnAddNewSaver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                newGrpSavingsProfID = random.nextInt((int) (Math.random() * 909) + 1119);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String joinedDate = mdformat.format(calendar.getTime());
                tittle=txtTittle.getText().toString();
                purpose=txtPurpose.getText().toString();
                firstName=edtFN.getText().toString();
                userIDStrng= edtUserID.getText().toString();
                lastName=edtSurname.getText().toString();
                if (TextUtils.isEmpty(userIDStrng)) {
                    edtUserID.setError("Please enter Your User ID");
                }
                userID= Integer.parseInt(userIDStrng);
                if(grpAcctID>0 ||grpAccountProfileNo>0){
                    grpSavingsProfile= new GrpSavingsProfile(newGrpSavingsProfID,userID,firstName,lastName,joinedDate);
                    groupAccount.addGrpSavingsProfile(grpSavingsProfile);
                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                        dbHelper.openDataBase();
                        sqLiteDatabase = dbHelper.getWritableDatabase();
                        dbID=grpProfileDAO.insertGroupProfile(grpAcctID,grpAccountProfileNo,userID,firstName,lastName,joinedDate);

                    }
                    if(dbID>0){
                        Log.w(TAG, "Group Joining successful");
                        Toast.makeText(JoinThisGrpSavAct.this, "Group Joining successful ",Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(JoinThisGrpSavAct.this, LoginDirAct.class);
                        intent.putExtra("Profile", (Parcelable) userProfile);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);

                    }

                }


            }

        });
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            //messageDAO.insertMessage(profileID, customerID, messageID, bizID, otpMessage, "Awajima App", customerName, selectedOffice, joinedDate);

        }
        btnAddNewSaver.setOnClickListener(this::doAddProfToGrp);
    }
    private void createAnonymousAccountWithReferrerInfo(final String referrerUid) {

    }
    public void goHome(View view) {
        gson = new Gson();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if(userProfile !=null){
            profileID=userProfile.getPID();

        }
        //userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, LoginDirAct.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void doAddProfToGrp(View view) {
    }
}