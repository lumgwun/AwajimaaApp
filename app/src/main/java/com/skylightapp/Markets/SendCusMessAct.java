package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBIncomingMessagesManager;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.QBSystemMessagesManager;
import com.quickblox.chat.listeners.QBChatDialogMessageListener;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBDialogCustomData;
import com.quickblox.chat.utils.DialogUtils;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.model.QBUser;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Utils;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.AdminBalanceDAO;
import com.skylightapp.Database.AwardDAO;
import com.skylightapp.Database.BirthdayDAO;
import com.skylightapp.Database.CodeDAO;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.GrpProfileDAO;
import com.skylightapp.Database.LoanDAO;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.Database.OfficeBranchDAO;
import com.skylightapp.Database.PaymDocDAO;
import com.skylightapp.Database.PaymentCodeDAO;
import com.skylightapp.Database.PaymentDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Database.SODAO;
import com.skylightapp.Database.StockTransferDAO;
import com.skylightapp.Database.StocksDAO;
import com.skylightapp.Database.TCashDAO;
import com.skylightapp.Database.TReportDAO;
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.Database.TransactionGrantingDAO;
import com.skylightapp.Database.WorkersDAO;
import com.skylightapp.MarketClasses.DialogsManager;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;

import java.util.Calendar;
import java.util.Date;

import static com.skylightapp.BuildConfig.QUICKBLOX_ACCT_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_APP_ID;
import static com.skylightapp.BuildConfig.QUICKBLOX_AUTH_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_SECRET_KEY;

public class SendCusMessAct extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = SendCusMessAct.class.getSimpleName();
    public final static int KEY_EXTRA_MESSAGE_ID = 121;

    private DBHelper selector;
    private  Profile sendeeProfile,senderProfile;
    private static final String PREF_NAME = "awajima";
    private static final String APPLICATION_ID = QUICKBLOX_APP_ID;   //QUICKBLOX_APP_ID
    private static final String AUTH_KEY = QUICKBLOX_AUTH_KEY;
    private static final String AUTH_SECRET = QUICKBLOX_SECRET_KEY;
    private static final String ACCOUNT_KEY = QUICKBLOX_ACCT_KEY;
    private static final String SERVER_URL = "";
    private AdminUser adminUser;
    private Context context;
    private SharedPreferences userPreferences;
    private PrefManager prefManager;
    private Gson gson,gson1,gson2;
    private String json,json1,json2;
    private  Bundle messageBundle;
    private int sendeeProfileID;
    private int customerID;
    private int SharedPrefAdminID;
    private  String purpose, roleType;
    private Spinner spnPurpose;
    private  String selectedPurpose,sendee;
    private  Customer customer;
    private EditText edtInputId;
    private TextView txtCusName,txtNotification;
    private  EditText inputMessage;
    private Button confirm;
    private SODAO sodao;
    private TranXDAO tranXDAO;
    private MessageDAO messageDAO;
    private LoanDAO loanDAO;
    private AcctDAO acctDAO;
    private CodeDAO codeDAO;
    private PaymDocDAO paymDocDAO;
    private CusDAO cusDAO;
    private PaymentCodeDAO paymentCodeDAO;
    private ProfDAO profileDao;
    private TCashDAO cashDAO;
    private TReportDAO tReportDAO;
    private PaymentDAO paymentDAO;
    private AdminBalanceDAO adminBalanceDAO;
    private TimeLineClassDAO timeLineClassDAO;
    private GrpProfileDAO grpProfileDAO;
    private StocksDAO stocksDAO;
    private WorkersDAO workersDAO;
    private StockTransferDAO stockTransferDAO;
    private OfficeBranchDAO officeBranchDAO;
    private BirthdayDAO birthdayDAO;
    private TransactionGrantingDAO grantingDAO;
    private AwardDAO awardDAO;
    private  MarketBusiness marketBusiness;
    private long bizID;
    private int marKetID, bizIDInt, adminProfID;
    private DialogsManager dialogsManager;

    private int skipRecords = 0;
    private boolean openDialogFromSwiping;
    private QBChatDialogMessageListener allDialogsMessagesListener;
    private QBSystemMessagesManager systemMessagesManager;
    private QBIncomingMessagesManager incomingMessagesManager;

    private String time,bizName, officeBranch;
    private int recipientId;
    private QBUser cusQBUser;
    private  Profile cusProf;
    String SharedPrefUserPassword,SharedPrefUserMachine,senderFullNames,phoneNo,SharedPrefUserName,adminName,sender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_send_cus_mes);
        setTitle("Business Messenger");
        FirebaseApp.initializeApp(this);
        QBSettings.getInstance().init(this, APPLICATION_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        txtCusName = findViewById(R.id.messageCus);
        txtNotification = findViewById(R.id.notification);
        edtInputId = findViewById(R.id.inputId2);
        sender=null;
        workersDAO= new WorkersDAO(this);
        awardDAO= new AwardDAO(this);
        grantingDAO= new TransactionGrantingDAO(this);
        stocksDAO= new StocksDAO(this);
        cusDAO= new CusDAO(this);
        birthdayDAO= new BirthdayDAO(this);
        officeBranchDAO= new OfficeBranchDAO(this);
        stockTransferDAO= new StockTransferDAO(this);

        paymentCodeDAO= new PaymentCodeDAO(this);
        profileDao= new ProfDAO(this);
        cashDAO= new TCashDAO(this);
        paymDocDAO= new PaymDocDAO(this);
        tReportDAO= new TReportDAO(this);
        paymentDAO= new PaymentDAO(this);
        adminBalanceDAO= new AdminBalanceDAO(this);
        timeLineClassDAO= new TimeLineClassDAO(this);
        grpProfileDAO= new GrpProfileDAO(this);

        sodao= new SODAO(this);
        tranXDAO= new TranXDAO(this);
        sodao= new SODAO(this);
        messageDAO= new MessageDAO(this);
        loanDAO= new LoanDAO(this);

        codeDAO= new CodeDAO(this);
        acctDAO= new AcctDAO(this);
        spnPurpose = findViewById(R.id.spinnerPurpose);
        selector= new DBHelper(this);
        gson = new Gson();
        gson1 = new Gson();
        gson2 = new Gson();
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
            sendeeProfile=customer.getCusProfile();
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
            cusQBUser=sendeeProfile.getProfQbUser();

        }
        if(cusQBUser !=null){
            recipientId=cusQBUser.getId();
        }
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        senderProfile = gson.fromJson(json, Profile.class);

        json1 = userPreferences.getString("LastAdminProfileUsed", "");
        adminUser = gson1.fromJson(json1, AdminUser.class);

        json2 = userPreferences.getString("LastAdminProfileUsed", "");
        marketBusiness = gson2.fromJson(json2, MarketBusiness.class);

        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefAdminID = userPreferences.getInt("ADMIN_ID",0);
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        if(marketBusiness !=null){
            bizName=marketBusiness.businessBrandName;
            bizID=marketBusiness.getBusinessID();
            marKetID=marketBusiness.getBizMarketID();
            officeBranch=marketBusiness.getBizState();
        }else {
            bizName="Awajima App";
        }
        //SharedPrefProfileID=userPreferences.getString(PROFILE_ID, "");
        adminName=userPreferences.getString("AdminName", "");
        roleType =profileDao.getProfileRoleByUserNameAndPassword(SharedPrefUserName,SharedPrefUserPassword);
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
            adminProfID=senderProfile.getPID();
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
                selector=new DBHelper(SendCusMessAct.this);

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
                    cusDAO= new CusDAO(SendCusMessAct.this);
                    sendee=cusDAO.getCustomerNames(customerID);

                }

                String message = inputMessage.getText().toString();
                purpose = inputMessage.getText().toString();
                boolean canLeaveMessage = true;
                String timelineDetailsTD = sendee + "was sent a message" + "by" + senderFullNames + "@" + time;
                String tittleT1 = "Message Alert!";
                Location mCurrentLocation=null;
                String timelineDetails = "You sent" + sendee + "a message" + "on" + time;
                String timelineCus = bizName+""+senderFullNames + "sent you a message" + "on" + time;

                //Message message2= new Message(KEY_EXTRA_MESSAGE_ID,adminName,sendeeProfileID,customerID,selectedPurpose,message,time);
                int id = sendeeProfile.leaveMessage(message, customerID);
                if(customer !=null){
                    customer.addCusMessages(KEY_EXTRA_MESSAGE_ID,selectedPurpose,message,bizName+""+senderFullNames,time);


                }
                bizIDInt = Math.toIntExact(bizID);
                messageDAO= new MessageDAO(SendCusMessAct.this);
                String senderFrom=senderFullNames+""+"From"+bizName;
                try {

                    selector.openDataBase();
                    messageDAO.insertNewMessage(adminProfID,customerID, bizIDInt,marKetID,selectedPurpose,message,senderFrom,sendee, officeBranch, time);

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                try {

                    selector.openDataBase();
                    timeLineClassDAO= new TimeLineClassDAO(SendCusMessAct.this);
                    timeLineClassDAO.insertTimeLine(tittleT1, timelineDetailsTD, time, mCurrentLocation);


                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                if(senderProfile !=null){
                    senderProfile.addPTimeLine(tittleT1, timelineDetails);
                }
                if(customer !=null){
                    customer.addCusTimeLine(tittleT1,timelineCus);
                }


                Toast.makeText(SendCusMessAct.this, "Message sent sucessfully" , Toast.LENGTH_LONG).show();
                //notification.setText(confirmationMessage);

            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    public void createNewDialog(int recipientId, int matchValue) {
        if(cusQBUser !=null){
            recipientId=cusQBUser.getId();
        }
        QBChatDialog dialog = DialogUtils.buildPrivateDialog(recipientId);
        QBDialogCustomData dialogCustomData = new QBDialogCustomData("DialogMatchValue");
        dialogCustomData.putInteger("matchValue", matchValue);
        dialog.setCustomData(dialogCustomData);
        QBRestChatService.createChatDialog(dialog).performAsync(new QBEntityCallback<QBChatDialog>() {
            @Override
            public void onSuccess(QBChatDialog result, Bundle params) {
                Log.d("Chat created", result.toString());
                dialogsManager.sendSystemMessageAboutCreatingDialog(systemMessagesManager, result);
                //getUsersFromDialog(result);

                if (openDialogFromSwiping){
                    openDialogFromSwiping = false;
                    //ChatActCon.startForResult(this, REQUEST_DIALOG_ID_FOR_UPDATE, result);
                }
            }

            @Override
            public void onError(QBResponseException responseException) {
            }
        });
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void sendMessage(View view) {
        /*String confirmationMessage = "";
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
            cusDAO= new CusDAO(this);
            sendee=cusDAO.getCustomerNames(customerID);

        }

        String message = inputMessage.getText().toString();
        purpose = inputMessage.getText().toString();
        boolean canLeaveMessage = true;
        String timelineDetailsTD = sendee + "was sent a message" + "by" + senderFullNames + "@" + time;
        String tittleT1 = "Message Alert!";
        Location mCurrentLocation=null;
        String timelineDetails = "You sent" + sendee + "a message" + "on" + time;
        String timelineCus = "Awajima"+""+senderFullNames + "sent you a message" + "on" + time;

        Message message2= new Message(KEY_EXTRA_MESSAGE_ID,adminName,sendeeProfileID,customerID,selectedPurpose,message,time);
        int id = sendeeProfile.leaveMessage(message, customerID);
        messageDAO= new MessageDAO(this);
        customer.addCusMessages(KEY_EXTRA_MESSAGE_ID,selectedPurpose,message,"Awajima"+""+senderFullNames,time);
        messageDAO.insertNewMessage(KEY_EXTRA_MESSAGE_ID,sendeeProfileID,customerID, SharedPrefAdminID, adminName, selectedPurpose, message,"Awajima Admin",sendee, time);
        try {
            timeLineClassDAO= new TimeLineClassDAO(this);

            selector.openDataBase();
            timeLineClassDAO.insertTimeLine(tittleT1, timelineDetailsTD, time, mCurrentLocation);


        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if(senderProfile !=null){
            senderProfile.addPTimeLine(tittleT1, timelineDetails);
        }
        if(customer !=null){
            customer.addCusTimeLine(tittleT1,timelineCus);
        }


        Toast.makeText(SendCustomerMessage.this, "Message sent sucessfully" , Toast.LENGTH_LONG).show();*/
        //notification.setText(confirmationMessage);


    }
    protected void sendSMSMessage() {
        final EditText inputMessage = (EditText) findViewById(R.id.inputMessage2);
        String notificationMessage = inputMessage.getText().toString();
        /*Twilio.init("AC5e05dc0a793a29dc1da2eabdebd6c28d", "39410e8b813c131da386f3d7bb7f94f7");
        com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message.creator(
                new com.twilio.type.PhoneNumber(phoneNo),
                new com.twilio.type.PhoneNumber("234"+phoneNo),
                notificationMessage)
                .create();*/

    }


}