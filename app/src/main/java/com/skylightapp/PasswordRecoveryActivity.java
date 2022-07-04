package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.florent37.shapeofview.shapes.ArcView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.security.SecureRandom;
import java.util.Objects;

import static com.skylightapp.Classes.Profile.PROFILE_PASSWORD;

import static com.skylightapp.Transactions.OurConfig.TWILIO_ACCOUNT_SID;
import static com.skylightapp.Transactions.OurConfig.TWILIO_AUTH_TOKEN;

public class PasswordRecoveryActivity extends AppCompatActivity {
    AppCompatEditText editText_phoneNo,editText_oldPin,sent_code,surnameEdit,editText_changePin1 , editText_changePin2;
    AppCompatTextView txt_YourPasswordIs,text_password3;

    String strPhoneNumber,password, strCode,strPhoneNumberKnown,textMessage,strNewPin1 , email,strFirstName,strFirstNameOld,strSurname,strSurnameOld,strNewPin2;
    boolean storedNewData;
    DBHelper dbHelper;
    SecureRandom random;
    //Profile profile = new Profile();
    //String uid = UUID.randomUUID().toString();
    LinearLayout linearLayout1, linearLayout2;
    String sender = "Your Skylight Coop." ;
    public static final String ACCOUNT_SID = System.getenv(TWILIO_ACCOUNT_SID);
    public static final String AUTH_TOKEN = System.getenv(TWILIO_AUTH_TOKEN);
    Message message;
    Gson gson;
    private static final String PREF_NAME = "skylight";
    int profileID;
    long codeID;
    Profile userProfile;
    private SharedPreferences preferences;
    LinearLayoutCompat bioLayout,layout_post_bio0,layout_get_passwod,layout_new_password,layout_passwordIs;
    AppCompatButton btn_Send_ACode,btn_after_send_code,btn_get,btn_create,btn_change_password;
    ArcView btn_ls;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_password_recovery);
        preferences= getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        btn_Send_ACode = findViewById(R.id.send_ACode);
        dbHelper=new DBHelper(this);
        //random=new SecureRandom();
        if(random !=null){
            codeID = random.nextInt((int) (Math.random() * 90206) + 50902);

        }

        surnameEdit =findViewById(R.id.surname3);
        editText_phoneNo =findViewById(R.id.phoneNumber3);
        bioLayout =findViewById(R.id.layout_bio0);
        layout_post_bio0 =findViewById(R.id.layout_post_bio0);
        sent_code =findViewById(R.id.pass_code);
        btn_after_send_code =findViewById(R.id.btn_proceed);
        layout_get_passwod =findViewById(R.id.layout_get_passwod);
        btn_get=findViewById(R.id.btn_get);
        btn_create=findViewById(R.id.btn_create);
        layout_passwordIs =findViewById(R.id.layout_passwordIs);
        txt_YourPasswordIs =findViewById(R.id.yourPassword);
        layout_new_password =findViewById(R.id.layout_new_password_);
        editText_changePin1=findViewById(R.id.new_password_3);
        editText_changePin2=findViewById(R.id.new_password_3_1);
        btn_change_password=findViewById(R.id.btn_change_password);
        //userProfile=new Profile();

        //long transactionID = random.nextInt((int) (Math.random() * 920) + 170);
        preferences= this.getSharedPreferences("LastProfileUsed", Context.MODE_PRIVATE);
        gson = new Gson();
        String json = preferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if(userProfile !=null){
            strPhoneNumberKnown = userProfile.getProfilePhoneNumber();
            strFirstNameOld = userProfile.getProfileFirstName();
            strSurnameOld = userProfile.getProfileLastName();
            profileID=userProfile.getPID();
            email = userProfile.getProfileEmail();

        }

        if(email !=null){
            bioLayout.setVisibility(View.GONE);

        }
        if(strPhoneNumber !=null){
            bioLayout.setVisibility(View.GONE);

        }
        textMessage = "Your Skylight Password Modification Code is "+codeID;


        fab = findViewById(R.id.homeward);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "taking you back to Dashboard", Snackbar.LENGTH_LONG)
                        .setAction("Go", null).show();
                homeGo();
            }
        });

        btn_Send_ACode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strPhoneNumber = Objects.requireNonNull(editText_phoneNo.getText()).toString().trim();
                strSurname = Objects.requireNonNull(surnameEdit.getText()).toString().trim();
                if(strPhoneNumber.equals(strPhoneNumberKnown) && strSurname.equals(strSurnameOld) ||strSurname.equals(strFirstNameOld)){
                    sendVerificationCode();
                    saveCode();
                    bioLayout.setVisibility(View.GONE);
                    layout_post_bio0.setVisibility(View.VISIBLE);
                }

            }
        });
        btn_after_send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strCode = Objects.requireNonNull(sent_code.getText()).toString().trim();
                if(strCode.equals(String.valueOf(codeID))){
                    layout_post_bio0.setVisibility(View.GONE);
                    bioLayout.setVisibility(View.GONE);
                    layout_get_passwod.setVisibility(View.VISIBLE);

                }

            }
        });
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_post_bio0.setVisibility(View.GONE);
                bioLayout.setVisibility(View.GONE);
                layout_get_passwod.setVisibility(View.GONE);
                layout_passwordIs.setVisibility(View.VISIBLE);
                btn_create.setVisibility(View.GONE);
                password=dbHelper.getPassword1(profileID);
                txt_YourPasswordIs.setText(String.format("Your password is:%s", password));


            }
        });
        btn_get.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                layout_post_bio0.setVisibility(View.GONE);
                bioLayout.setVisibility(View.GONE);
                layout_get_passwod.setVisibility(View.GONE);
                btn_get.setVisibility(View.VISIBLE);
                btn_create.setVisibility(View.VISIBLE);
                return false;
            }
        });
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_post_bio0.setVisibility(View.GONE);
                bioLayout.setVisibility(View.GONE);
                layout_get_passwod.setVisibility(View.GONE);
                layout_passwordIs.setVisibility(View.GONE);
                btn_get.setVisibility(View.GONE);
                layout_new_password.setVisibility(View.VISIBLE);

            }
        });
        btn_create.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                layout_post_bio0.setVisibility(View.GONE);
                bioLayout.setVisibility(View.GONE);
                layout_get_passwod.setVisibility(View.GONE);
                btn_get.setVisibility(View.VISIBLE);
                layout_new_password.setVisibility(View.GONE);
                return false;
            }
        });

        btn_change_password.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                strPhoneNumber = Objects.requireNonNull(editText_phoneNo.getText()).toString().trim();
                strNewPin1 = Objects.requireNonNull(editText_changePin1.getText()).toString().trim();
                strNewPin2 = Objects.requireNonNull(editText_changePin2.getText()).toString().trim();

                boolean isExist = false;

                if(isExist){
                    if(strNewPin1.equals("")||strNewPin2.equals(""))
                    {
                        Toast.makeText(PasswordRecoveryActivity.this, "Password can not be empty", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(!strNewPin1.equals(strNewPin2))
                    {
                        Toast.makeText(PasswordRecoveryActivity.this, "New Password did not match", Toast.LENGTH_LONG).show();

                    }

                    else {
                        if(strNewPin1.equals(strNewPin2)){
                            dbHelper.updateProfilePassword(strNewPin1,profileID);
                            Toast.makeText(PasswordRecoveryActivity.this, "Password update was successful", Toast.LENGTH_SHORT).show();
                            savePassword();
                            homeGo();

                        }

                    }

                } else {
                    txt_YourPasswordIs.setText(null);
                    Toast.makeText(PasswordRecoveryActivity.this, "an error occurred", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void saveCode() {
        SharedPreferences sharedPreferences= getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putString("VerificationCode", String.valueOf(codeID)).apply();

    }
    public void savePassword() {

        SharedPreferences sharedPreferences= getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putString(PROFILE_PASSWORD, strNewPin1).apply();

    }
    public void homeGo() {
        Intent intent = new Intent(PasswordRecoveryActivity.this, LoginDirAct.class);
        startActivity(intent);

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
    public void sendVerificationCode() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        message = Message.creator(
                new com.twilio.type.PhoneNumber(strPhoneNumber),
                new com.twilio.type.PhoneNumber("234"+strPhoneNumber),
                textMessage)
                .create();

        System.out.println(message.getSid());

    }

}
