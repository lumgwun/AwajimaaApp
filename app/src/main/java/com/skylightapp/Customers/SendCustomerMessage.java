package com.skylightapp.Customers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Message;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Utils;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.twilio.Twilio;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class SendCustomerMessage extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = SendCustomerMessage.class.getSimpleName();
    public final static int KEY_EXTRA_MESSAGE_ID = 121;

    private DBHelper selector;
    private  Profile sendeeProfile,senderProfile;
    private static final String PREF_NAME = "skylight";
    private AdminUser adminUser;
    private Context context;
    private SharedPreferences userPreferences;
    private PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1;
    private  Bundle messageBundle;
    private int sendeeProfileID;
    private int customerID;
    private int SharedPrefAdminID;
    private  String purpose,skylightType;
    private Spinner spnPurpose;
    private  String selectedPurpose,sendee;
    private  Customer customer;
    private EditText edtInputId;
    private TextView txtCusName,txtNotification;
    private  EditText inputMessage;
    private Button confirm;
    private String time;
    String SharedPrefUserPassword,SharedPrefUserMachine,senderFullNames,phoneNo,SharedPrefUserName,adminName,sender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_send_cus_mes);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        txtCusName = findViewById(R.id.messageCus);
        txtNotification = findViewById(R.id.notification);
        edtInputId = findViewById(R.id.inputId2);
        sender=null;
        spnPurpose = findViewById(R.id.spinnerPurpose);
        selector= new DBHelper(this);
        gson = new Gson();
        gson1 = new Gson();
        customer=new Customer();
        sendeeProfile =new Profile();
        senderProfile= new Profile();
        adminUser=new AdminUser();
        messageBundle=getIntent().getExtras();
        if(messageBundle !=null){
            sendeeProfileID=messageBundle.getInt("PROFILE_ID");
            customerID=messageBundle.getInt("CUSTOMER_ID");
            edtInputId.setText("Sendee ID:"+customerID);
            edtInputId.setActivated(false);
            sendeeProfile =messageBundle.getParcelable("Profile");
            customer=messageBundle.getParcelable("Customer");

        }
        if(customer !=null){
            sendee=customer.getCusSurname()+""+customer.getCusFirstName();
            phoneNo=customer.getCusPhoneNumber();
            customerID=customer.getCusUID();
            txtCusName.setText("Customer:"+""+sendee);
            edtInputId.setActivated(false);
            edtInputId.setText("Customer ID:"+""+customerID);
        }else {
            txtCusName.setVisibility(View.GONE);
            sendee=null;

        }
        if(sendeeProfile !=null){
            sendeeProfileID= sendeeProfile.getPID();

        }
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json1 = userPreferences.getString("LastProfileUsed", "");
        senderProfile = gson.fromJson(json, Profile.class);

        json = userPreferences.getString("LastAdminProfileUsed", "");
        adminUser = gson.fromJson(json, AdminUser.class);

        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefAdminID = userPreferences.getInt("ADMIN_ID",0);
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        //SharedPrefProfileID=userPreferences.getString(PROFILE_ID, "");
        adminName=userPreferences.getString("AdminName", "");
        skylightType=selector.getProfileRoleByUserNameAndPassword(SharedPrefUserName,SharedPrefUserPassword);
        spnPurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               selectedPurpose = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(senderProfile !=null){
            senderFullNames=senderProfile.getProfileLastName()+","+senderProfile.getProfileFirstName();

        }

        inputMessage = (EditText) findViewById(R.id.inputMessage2);
        confirm = (Button) findViewById(R.id.confirmButton3);
        confirm.setOnClickListener(this::sendMessage);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String confirmationMessage = "";
                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();
                time= Utils.formatDateTime(currentDate);
                selector=new DBHelper(SendCustomerMessage.this);

                if(customer !=null){
                    sendee=customer.getCusSurname()+","+customer.getCusFirstName();
                    phoneNo=customer.getCusPhoneNumber();
                    customerID=customer.getCusUID();
                    txtCusName.setText("Customer:"+""+sendee);
                    edtInputId.setActivated(false);
                    edtInputId.setText("Customer ID:"+""+customerID);
                }else {
                    customerID = Integer.parseInt(edtInputId.getText().toString());
                    txtCusName.setVisibility(View.GONE);
                    sendee=selector.getCustomerNames(customerID);

                }

                String message = inputMessage.getText().toString();
                purpose = inputMessage.getText().toString();
                boolean canLeaveMessage = true;
                String timelineDetailsTD = sendee + "was sent a message" + "by" + senderFullNames + "@" + time;
                String tittleT1 = "Message Alert!";
                Location mCurrentLocation=null;
                String timelineDetails = "You sent" + sendee + "a message" + "on" + time;
                String timelineCus = "Skylight"+""+senderFullNames + "sent you a message" + "on" + time;

                //Message message2= new Message(KEY_EXTRA_MESSAGE_ID,adminName,sendeeProfileID,customerID,selectedPurpose,message,time);
                int id = sendeeProfile.leaveMessage(message, customerID);
                if(customer !=null){
                    customer.addCusMessages(KEY_EXTRA_MESSAGE_ID,selectedPurpose,message,"Skylight"+""+senderFullNames,time);


                }
                selector.insertNewMessage(KEY_EXTRA_MESSAGE_ID,sendeeProfileID,customerID, SharedPrefAdminID, adminName, selectedPurpose, message,"Skylight Admin",sendee, time);
                try {

                    selector.openDataBase();
                    selector.insertTimeLine(tittleT1, timelineDetailsTD, time, mCurrentLocation);


                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                if(senderProfile !=null){
                    senderProfile.addTimeLine(tittleT1, timelineDetails);
                }
                if(customer !=null){
                    customer.addCusTimeLine(tittleT1,timelineCus);
                }


                Toast.makeText(SendCustomerMessage.this, "Message sent sucessfully" , Toast.LENGTH_LONG).show();
                //notification.setText(confirmationMessage);

            }
        });
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void sendMessage(View view) {
        String confirmationMessage = "";
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        time= Utils.formatDateTime(currentDate);
        selector=new DBHelper(SendCustomerMessage.this);

        if(customer !=null){
            sendee=customer.getCusSurname()+","+customer.getCusFirstName();
            phoneNo=customer.getCusPhoneNumber();
            customerID=customer.getCusUID();
            txtCusName.setText("Customer:"+""+sendee);
            edtInputId.setActivated(false);
            edtInputId.setText("Customer ID:"+""+customerID);
        }else {
            customerID = Integer.parseInt(edtInputId.getText().toString());
            txtCusName.setVisibility(View.GONE);
            sendee=selector.getCustomerNames(customerID);

        }

        String message = inputMessage.getText().toString();
        purpose = inputMessage.getText().toString();
        boolean canLeaveMessage = true;
        String timelineDetailsTD = sendee + "was sent a message" + "by" + senderFullNames + "@" + time;
        String tittleT1 = "Message Alert!";
        Location mCurrentLocation=null;
        String timelineDetails = "You sent" + sendee + "a message" + "on" + time;
        String timelineCus = "Skylight"+""+senderFullNames + "sent you a message" + "on" + time;

        Message message2= new Message(KEY_EXTRA_MESSAGE_ID,adminName,sendeeProfileID,customerID,selectedPurpose,message,time);
        int id = sendeeProfile.leaveMessage(message, customerID);
        customer.addCusMessages(KEY_EXTRA_MESSAGE_ID,selectedPurpose,message,"Skylight"+""+senderFullNames,time);
        selector.insertNewMessage(KEY_EXTRA_MESSAGE_ID,sendeeProfileID,customerID, SharedPrefAdminID, adminName, selectedPurpose, message,"Skylight Admin",sendee, time);
        try {

            selector.openDataBase();
            selector.insertTimeLine(tittleT1, timelineDetailsTD, time, mCurrentLocation);


        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if(senderProfile !=null){
            senderProfile.addTimeLine(tittleT1, timelineDetails);
        }
        if(customer !=null){
            customer.addCusTimeLine(tittleT1,timelineCus);
        }


        Toast.makeText(SendCustomerMessage.this, "Message sent sucessfully" , Toast.LENGTH_LONG).show();
        //notification.setText(confirmationMessage);


    }
    protected void sendSMSMessage() {
        final EditText inputMessage = (EditText) findViewById(R.id.inputMessage2);
        String notificationMessage = inputMessage.getText().toString();
        Twilio.init("AC5e05dc0a793a29dc1da2eabdebd6c28d", "39410e8b813c131da386f3d7bb7f94f7");
        com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message.creator(
                new com.twilio.type.PhoneNumber(phoneNo),
                new com.twilio.type.PhoneNumber("234"+phoneNo),
                notificationMessage)
                .create();

    }


}