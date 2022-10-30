package com.skylightapp.Tellers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.BuildConfig;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.PaymentCode;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Settings;
import com.skylightapp.Database.CodeDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.R;
import com.skylightapp.SMSAct;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.util.Random;


import static com.skylightapp.BuildConfig.TWILLO_NO;
import static com.skylightapp.BuildConfig.T_ACCT_SID;
import static com.skylightapp.BuildConfig.T_AUTH_TOKEN;
import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_DATE;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_ID;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_TOTAL;
import static com.skylightapp.Classes.PaymentCode.CODE_DATE;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_ID;

public class BusinessSMSAct extends AppCompatActivity {
    int customerID,packageID, savingsID;
    private String TWILLO_ACCOUNT_SID= T_ACCT_SID;
    private String TWILLO_AUTH_TOKEN= T_AUTH_TOKEN;
    private String TWILLO_PHONE_NO= TWILLO_NO;
    String from = TWILLO_NO;
    String password= BuildConfig.SKYLIGHT_EMAIL_PASSWORD;
    double totalAmount;
    String date,smsCustomer,smsToC,subject;
    String phoneNo;
    String message;
    int randomNumber;

    DBHelper dbHelper;
    AppCompatButton confirmButton,resendButton;
    AppCompatEditText reportID,skylightCode;
    String correct;
    int packageId;
    int reportId;
    int customerId;
    String customerPhoneN,cusEmail,emailMessage;
    SmsManager smsManager;

    SharedPreferences userPreferences;
    Gson gson;
    String json,purpose,businessName,messageToSend;
    Profile userProfile;
    private Settings settings;
    private RecyclerView log;
    //private LogAdapter logAdapter;
    AppCompatSpinner customerSpinner;
    int profileID;
    Customer customer;
    long savingsCode;
    Bundle bundle,extras;
    CustomerDailyReport dailyReport;
    private SQLiteDatabase sqLiteDatabase;
    private int marketID, businessID;
    MessageDAO messageDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_teller_smsact);
        bundle= new Bundle();
        extras= new Bundle();
        extras = getIntent().getExtras();
        final Random random = new Random();
        dbHelper= new DBHelper(this);
        messageDAO= new MessageDAO(this);
        if(extras != null) {
            customerID= extras.getInt(CUSTOMER_ID);
            packageID= extras.getInt(PACKAGE_ID);
            savingsID= extras.getInt(REPORT_ID);
            totalAmount= extras.getDouble(REPORT_TOTAL);
            date= extras.getString(REPORT_DATE);
            purpose= extras.getString("Purpose");
            businessName= extras.getString("BusinessName");
            dailyReport=extras.getParcelable("Savings");
            customer=extras.getParcelable("Customer");
            if(dailyReport ==null){
                dailyReport=extras.getParcelable("CustomerDailyReport");

            }
            if(customer !=null){
                cusEmail=customer.getCusEmailAddress();
                customerPhoneN=customer.getCusPhoneNumber();

            }
            CodeDAO codeDAO = new CodeDAO(this);
            Twilio.init(TWILLO_ACCOUNT_SID, TWILLO_AUTH_TOKEN);
            smsCustomer="Awajima"+ random.nextInt((int) (Math.random() * 2) + 1039);
            savingsCode=random.nextInt((int) (Math.random() * 2) + 1039);
            smsToC = "This"+""+savingsCode + ", is your Awajima  code for Savings:"+ ""+ savingsID;
            PaymentCode paymentCode= new PaymentCode(customerID,savingsID,savingsCode,CODE_DATE);
            subject="Awajima Code Alert";
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Send Awajima Code To:");
            emailMessage=smsToC;
            builder.setIcon(R.drawable.ic_icon2);
            bundle.putLong("PaymentCode",savingsCode);
            bundle.putString("Message",messageToSend);
            bundle.putString("emailAddress",cusEmail);
            bundle.putString("EmailMessage",emailMessage);
            bundle.putString("from","Awajima");
            bundle.putString("to",cusEmail);
            bundle.putString("Purpose",purpose);
            bundle.putString("Business",businessName);
            bundle.putString("subject",subject);
            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                dbHelper.openDataBase();
                codeDAO.saveNewSavingsCode(paymentCode);


            }

            dailyReport.setRecordSavingsCode(savingsCode);
            builder.setItems(new CharSequence[]
                            {"Phone sms", "Email","both"},
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    Toast.makeText(BusinessSMSAct.this, "Sending savings code through SMS", Toast.LENGTH_SHORT).show();
                                    Intent savingsIntent = new Intent(BusinessSMSAct.this, SMSAct.class);
                                    savingsIntent.putExtras(bundle);
                                    savingsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(savingsIntent);


                                    /*Twilio.init(TWILLO_ACCOUNT_SID, TWILLO_AUTH_TOKEN);
                                    Message message = Message.creator(
                                            new com.twilio.type.PhoneNumber(customerPhoneN),
                                            new com.twilio.type.PhoneNumber("+234"+customerPhoneN),
                                            smsToC)
                                            .create();

                                    System.out.println(message.getSid());*/

                                    break;
                                case 1:
                                    Toast.makeText(BusinessSMSAct.this, "Sending savings code through Email", Toast.LENGTH_SHORT).show();
                                    Intent emailIntent = new Intent(BusinessSMSAct.this, TellerSendEmailAct.class);
                                    emailIntent.putExtras(bundle);
                                    emailIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(emailIntent);
                                    break;

                                case 2:
                                    Toast.makeText(BusinessSMSAct.this, "Sending message  through Email", Toast.LENGTH_SHORT).show();
                                    Intent bothIntent = new Intent(BusinessSMSAct.this, TellerSendEmailAct.class);
                                    bothIntent.putExtras(bundle);
                                    bothIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(bothIntent);

                                    Toast.makeText(BusinessSMSAct.this, "Sending message  through SMS", Toast.LENGTH_SHORT).show();
                                    Intent sendIntent = new Intent(BusinessSMSAct.this, SMSAct.class);
                                    sendIntent.putExtras(bundle);
                                    sendIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(sendIntent);
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
                        }
                    });

            builder.create().show();
        }

    }
}