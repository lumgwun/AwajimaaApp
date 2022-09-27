package com.skylightapp.Customers;

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
import com.skylightapp.Markets.SendCusMessAct;
import com.skylightapp.R;

import java.util.Calendar;
import java.util.Date;

import static com.skylightapp.BuildConfig.QUICKBLOX_ACCT_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_APP_ID;
import static com.skylightapp.BuildConfig.QUICKBLOX_AUTH_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_SECRET_KEY;

public class CusMessageAct extends AppCompatActivity {
    private static final String TAG = CusMessageAct.class.getSimpleName();
    public final static int KEY_EXTRA_MESSAGE_ID = 121;

    private DBHelper selector;
    private Profile marketBizProfile,senderProfile;
    private static final String PREF_NAME = "skylight";
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
    private int marketBizProfileID;
    private int customerID;
    private int SharedPrefAdminID;
    private  String purpose,skylightType;
    private Spinner spnPurpose;
    private  String selectedPurpose, businessName;
    private Customer customer;
    private EditText edtInputId;
    private TextView txtBizName,txtNotification;
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
    private MarketBusiness marketBusiness;
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
        setContentView(R.layout.act_cus_message);
        FirebaseApp.initializeApp(this);
        setTitle("Customer Messenger");
        inputMessage = (EditText) findViewById(R.id.inputMessage2);
        txtCusName = findViewById(R.id.messageCus);
        txtNotification = findViewById(R.id.notification);
        edtInputId = findViewById(R.id.inputId2);
        spnPurpose = findViewById(R.id.spinnerPurpose);
        confirm = (Button) findViewById(R.id.confirmButton3);
        QBSettings.getInstance().init(this, APPLICATION_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson1 = new Gson();
        gson2 = new Gson();
        customer=new Customer();
        marketBizProfile =new Profile();
        senderProfile= new Profile();
        adminUser=new AdminUser();
        messageBundle=getIntent().getExtras();
        if(messageBundle !=null){
            marketBizProfileID =messageBundle.getInt("PROFILE_ID");
            edtInputId.setText("Business ID:"+marketBizProfileID);
            edtInputId.setActivated(false);
            marketBizProfile =messageBundle.getParcelable("Profile");
            marketBusiness=messageBundle.getParcelable("MarketBusiness");

        }
        if(marketBusiness !=null){
            marketBizProfile =marketBusiness.getmBusOwner();
            businessName=marketBusiness.getBizBrandname();
            bizID=marketBusiness.getBusinessID();
            txtBizName.setText("Business:"+""+ businessName);
            edtInputId.setActivated(false);
            edtInputId.setText("Business ID:"+""+bizID);
        }else {
            txtBizName.setVisibility(View.GONE);
            businessName =null;

        }
        if(marketBizProfile !=null){
            marketBizProfileID = marketBizProfile.getPID();
            cusQBUser= marketBizProfile.getProfQbUser();

        }
        if(cusQBUser !=null){
            recipientId=cusQBUser.getId();
        }
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json1 = userPreferences.getString("LastProfileUsed", "");
        senderProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");


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


        confirm.setOnClickListener(this::sendMessage);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String confirmationMessage = "";
                Calendar calendar = Calendar.getInstance();
                Date currentDate = calendar.getTime();
                time= Utils.formatDateTime(currentDate);
                selector=new DBHelper(CusMessageAct.this);

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
}