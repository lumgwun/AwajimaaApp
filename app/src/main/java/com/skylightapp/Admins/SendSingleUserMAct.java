package com.skylightapp.Admins;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quickblox.auth.session.QBSessionManager;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.messages.QBPushNotifications;
import com.quickblox.messages.model.QBEnvironment;
import com.quickblox.messages.model.QBEvent;
import com.quickblox.messages.model.QBNotificationType;
import com.quickblox.messages.services.QBPushManager;
import com.quickblox.messages.services.SubscribeService;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.skylightapp.Adapters.OfficeAdapter;
import com.skylightapp.Adapters.TellerSpinnerAdapter;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.KeyboardUtils;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Database.AdminUserDAO;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.Customer_TellerDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MarketBizDAO;
import com.skylightapp.Database.MarketDAO;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.Interfaces.Consts;
import com.skylightapp.LoginActivity;
import com.skylightapp.LoginDirAct;
import com.skylightapp.MarketClasses.BizAdmin;
import com.skylightapp.MarketClasses.BizDealPartner;
import com.skylightapp.MarketClasses.MBDonorArrayA;
import com.skylightapp.MarketClasses.MBPartnerArrayA;
import com.skylightapp.MarketClasses.Market;
import com.skylightapp.MarketClasses.MarketAdapter;
import com.skylightapp.MarketClasses.MarketAdmin;
import com.skylightapp.MarketClasses.MarketBizArrayAdapter;
import com.skylightapp.MarketClasses.MarketBizDonor;
import com.skylightapp.MarketClasses.MarketBizPartner;
import com.skylightapp.MarketClasses.MarketBizRegulator;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.MarketClasses.MarketCustomer;
import com.skylightapp.Markets.ToastUtils;
import com.skylightapp.R;
import com.twilio.Twilio;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static com.skylightapp.Classes.ImageUtil.TAG;

public class SendSingleUserMAct extends AppCompatActivity implements TextWatcher {
    Bundle bundle,userBundle;
    int cusID;
    int userProfID;
    String cusPhoneNo,firstName,surName,cusEmail,customerFirstName,cusSurname,cusLoc,profilePhone,email,location;
    AppCompatButton btnSend;
    AppCompatSpinner spnSendAdmin,spnSendTeller,spnSendCustomer;
    AppCompatTextView texName;
    LinearLayoutCompat layoutCus,layoutTeller,layoutAdmin,layoutOffice;
    DBHelper dbHelper;
    ArrayList<Customer>customers;
    private ArrayAdapter<Customer> customersAdapter;
    ArrayList<String>profiles;
    SharedPreferences userPref;
    PreferenceManager preferenceManager;
    boolean isInvisible;
    String messageDetails,name;
    private static final String PREF_NAME = "skylight";
    AppCompatEditText editMessage;
    com.twilio.rest.api.v2010.account.Message message;
    private static boolean isPersistenceEnabled = false;
    int selectedCustomerIndex;

    SharedPreferences sharedpreferences;
    Gson gson0,gson,gson1,gson2,gson3,gson4,gson5,gson6,gson7,gson8,gson9,gson10,gson11,gson12,gson13,gson14,gson15;
    String json0,json,json1,json2,json3,json4,json5,json6,json7,json8,json9,json10,json11,json12,json13,json14,json15;
    private Calendar calendar;
    SecureRandom random;
    int messageID;
    long profileID;
    String machine,userName,officeBranch,sender,sendeeType,sendee,selectedOffice;

    private AppCompatEditText edt_Purpose;


    private MarketBizArrayAdapter marketBizAdapter;
    private MarketAdapter marketAdapter;
    private MBPartnerArrayA marketBizPartnerAdapter;
    private MBDonorArrayA marketBizDonorAdapter;
    private QBUser qbUser,sendeeQBUser;
    Profile userProfile;
    AdminUser adminUser;
    UserSuperAdmin superAdmin;
    private Customer customer;
    CustomerManager customerManager;
    private MarketBusiness marketBiz;
    private Market market;
    private MarketAdmin marketAdmin;
    private MarketCustomer marketBizCus;
    private BizDealPartner bizDealPartner;
    private MarketBizDonor marketBizDonor;
    private MarketBizRegulator marketBizReg;
    private MarketBizPartner marketBizPartner;
    private OfficeAdapter officeAdapter;
    private OfficeBranch officeBranchC;
    private List<OfficeBranch> officeArrayList;
    private String fromProfType,bizName;
    private BizAdmin bizAdmin;
    private AppCompatSpinner SpnDonor,SpnMarketAdmin;
    private MarketDAO marketDAo;
    private  Customer_TellerDAO customer_tellerDAO;

    long bizID;
    private int qbUserID,selectedOfficeIndex,sendeeQBUserID;
    private ContentLoadingProgressBar loadingProgressBar;

    private ArrayList<MarketBusiness> myMarketBizs;
    private ArrayList<Market> myMarkets;
    private ArrayList<MarketCustomer> myMarketCuss;
    private ArrayList<MarketAdmin> marketAdmins;
    private ArrayList<MarketBizRegulator> marketBizRegulators;
    private ArrayList<MarketBizPartner> marketBizPartners;
    private ArrayList<MarketBizDonor> marketBizDonors;

    private ArrayAdapter<Customer> customerArrayAdapterN;
    private ArrayList<Customer> customersN;
    private ArrayList<AdminUser> adminUsers;
    private ArrayList<CustomerManager> customerManagerArrayList;

    private ArrayAdapter<Market> marketArrayAdapter;
    private ArrayAdapter<MarketCustomer> marketCustomerArrayAdapter;
    private ArrayAdapter<MarketBizDonor> marketBizDonorArrayAdapter;
    private ArrayAdapter<MarketBizPartner> marketBizPartnerArrayAdapter;
    private ArrayAdapter<MarketBizRegulator> marketBizRegulatorArrayAdapter;
    private ArrayAdapter<MarketAdmin> adminArrayAdapter;

    private int selectedMarketIndex;
    private ArrayAdapter<AdminUser> adminUserArrayAdapter;
    private ArrayAdapter<UserSuperAdmin> userSuperAdminArrayAdapter;

    AppCompatSpinner spnOfficeBranch;
    int selectedTellerIndex,marketID,selectedPartnerIndex,selectedMarketBizIndex,selectedDonorIndex,selectedMarketCusIndex;
    private  StringifyArrayList<Integer> userIds;
    private MarketBizDAO marketBizDAO;
    private Profile bizProfile;
    private  MarketBizArrayAdapter bizAdapter;
    private int bizProfID;
    private TellerSpinnerAdapter tellerSpinnerAdapter;
    private AppCompatSpinner spnRegulators,spnPartners,SpnMyBizs,SpnMyTellers,spnAllMarkets,spnPSendFromD,spnAllBizs,spnType;
    private LinearLayoutCompat layoutReg,layoutMyTellers,layoutMyBiz,layoutPartners,layoutAllMarkets,layoutAllBizs,layoutMyDonors,layoutMarketAdmin;
    private BroadcastReceiver pushBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(Consts.EXTRA_FCM_MESSAGE);
            if (TextUtils.isEmpty(message)) {
                message = Consts.EMPTY_FCM_MESSAGE;
            }
            Log.i(TAG, "Receiving event " + Consts.ACTION_NEW_FCM_EVENT + " with data: " + message);
            //retrieveMessage(message);
        }
    };

    public static void start(Context context, String message) {
        Intent intent = new Intent(context, SendSingleUserMAct.class);
        intent.putExtra(Consts.EXTRA_FCM_MESSAGE, message);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_send_user_message);
        setTitle("Awajima Single Messenger");
        loadingProgressBar = findViewById(R.id.pBarM);
        layoutCus = findViewById(R.id.layoutMyCus);
        layoutTeller = findViewById(R.id.layoutAllTeller);
        layoutMyDonors = findViewById(R.id.layoutMyDonors);
        layoutMarketAdmin = findViewById(R.id.layoutMarketA);
        layoutAllMarkets = findViewById(R.id.layoutAllMarkets);
        layoutAllBizs = findViewById(R.id.layoutAllBiz);
        layoutReg = findViewById(R.id.layoutRegulators);
        layoutPartners = findViewById(R.id.layoutPartners);
        layoutAdmin = findViewById(R.id.layoutMyAdmin);
        layoutOffice = findViewById(R.id.layoutOffice);
        layoutMyTellers = findViewById(R.id.layoutMyTellers);
        layoutMyBiz = findViewById(R.id.layoutBiz);


        marketDAo= new MarketDAO(this);
        marketBizDAO= new MarketBizDAO(this);
        customer_tellerDAO= new Customer_TellerDAO(this);
        spnOfficeBranch = findViewById(R.id.SendeeOfficeB);
        edt_Purpose = findViewById(R.id.mess_Purpose);
        spnType = findViewById(R.id.SenddeeUserType);
        spnRegulators = findViewById(R.id.SendeeRegulators);
        spnPartners = findViewById(R.id.SendeePartners);
        spnAllBizs = findViewById(R.id.SenddeeAllBiz);
        spnAllMarkets = findViewById(R.id.SenddeeMarkets);
        spnPSendFromD = findViewById(R.id.spnPSendFrom);
        SpnDonor = findViewById(R.id.SenddeeDonor);
        SpnMarketAdmin = findViewById(R.id.SenddeeAA);
        SpnMyTellers = findViewById(R.id.SMyTellers);
        SpnMyBizs = findViewById(R.id.SMyBiz);

        myMarketBizs=new ArrayList<>();
        myMarkets=new ArrayList<>();
        myMarketCuss=new ArrayList<>();
        marketAdmins=new ArrayList<>();
        marketBizRegulators=new ArrayList<>();
        marketBizPartners=new ArrayList<>();
        marketBizDonors=new ArrayList<>();
        officeArrayList=new ArrayList<>();
        customerManagerArrayList= new ArrayList<>();
        bizAdmin= new BizAdmin();
        qbUser= new QBUser();
        sendeeQBUser= new QBUser();
        bizProfile= new Profile();
        officeBranchC= new OfficeBranch();

        marketBiz= new MarketBusiness();
        marketBizCus= new MarketCustomer();
        marketBizDonor= new MarketBizDonor();
        marketAdmin = new MarketAdmin();
        marketBizPartner= new MarketBizPartner();
        market= new Market();
        bizDealPartner= new BizDealPartner();
        marketBizReg= new MarketBizRegulator();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //calendar.add(Calendar.DAY_OF_YEAR, 31);
        Date newDate = calendar.getTime();
        String todayDate = sdf.format(newDate);

        gson0 = new Gson();
        gson1 = new Gson();
        gson2 = new Gson();
        gson3 = new Gson();
        gson4 = new Gson();
        gson5 = new Gson();
        gson6 = new Gson();
        gson7 = new Gson();
        gson8 = new Gson();
        gson9 = new Gson();
        gson10 = new Gson();
        gson11 = new Gson();
        gson12 = new Gson();
        gson13 = new Gson();
        gson14 = new Gson();
        gson15 = new Gson();


        random= new SecureRandom();
        superAdmin= new UserSuperAdmin();
        userProfile= new Profile();
        adminUser=new AdminUser();
        customer= new Customer();
        customersN = new ArrayList<Customer>();
        adminUsers = new ArrayList<AdminUser>();


        userIds= new StringifyArrayList<>();
        bundle=getIntent().getExtras();
        if(bundle !=null){
            userProfID = bundle.getInt("PROFILE_ID",0);
            firstName = bundle.getString("PROFILE_FIRSTNAME","");
            surName = bundle.getString("PROFILE_SURNAME","");
            profilePhone = bundle.getString("PROFILE_PHONE","");
            email = bundle.getString("PROFILE_EMAIL","");
            cusID = bundle.getInt("CUSTOMER_ID",0);
            customerFirstName = bundle.getString("CUSTOMER_FIRST_NAME","");
            cusSurname = bundle.getString("CUSTOMER_SURNAME","");
            cusEmail = bundle.getString("CUSTOMER_EMAIL_ADDRESS","");
            cusPhoneNo = bundle.getString("USER_PHONE","");
            cusLoc = bundle.getString("CUSTOMER_LATLONG","");
            name=surName+","+firstName;
            texName.setText(MessageFormat.format("Name:{0}", name));
            userIds= (StringifyArrayList<Integer>) bundle.getIntegerArrayList("userIds");
            //doBundleDialog();
        }

        customerManagerArrayList = new ArrayList<CustomerManager>();
        customerManager= new CustomerManager();
        spnSendAdmin = findViewById(R.id.SenddeeAllAdmin);
        spnSendTeller = findViewById(R.id.SenddeeAllTeller);
        spnSendCustomer = findViewById(R.id.SenddeeAllCus);
        spnOfficeBranch = findViewById(R.id.SendeeOfficeB);
        sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        machine = sharedpreferences.getString("Machine", "");

        json0 = sharedpreferences.getString("LastUserSuperAdminUsed", "");
        superAdmin = gson0.fromJson(json0, UserSuperAdmin.class);

        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        json1 = sharedpreferences.getString("LastAdminUserProfileUsed", "");
        adminUser = gson1.fromJson(json1, AdminUser.class);

        json2 = sharedpreferences.getString("LastMarketBizRegulatorUsed", "");
        marketBizReg = gson2.fromJson(json2, MarketBizRegulator.class);

        json3 = sharedpreferences.getString("LastBizDealPartnerUsed", "");
        bizDealPartner = gson3.fromJson(json3, BizDealPartner.class);

        json4 = sharedpreferences.getString("LastMarketUsed", "");
        market = gson4.fromJson(json4, Market.class);

        json5 = sharedpreferences.getString("LastMarketBizPartnerUsed", "");
        marketBizPartner = gson5.fromJson(json5, MarketBizPartner.class);

        json6 = sharedpreferences.getString("LastMarketAdminUsed", "");
        marketAdmin = gson6.fromJson(json6, MarketAdmin.class);

        json7 = sharedpreferences.getString("LastQBUserUsed", "");
        qbUser = gson7.fromJson(json7, QBUser.class);

        json8 = sharedpreferences.getString("LastMarketBusinessUsed", "");
        marketBiz = gson8.fromJson(json8, MarketBusiness.class);

        json9 = sharedpreferences.getString("LastMarketBizDonorUsed", "");
        marketBizDonor = gson9.fromJson(json9, MarketBizDonor.class);

        json10 = sharedpreferences.getString("LastMarketCustomerUsed", "");
        marketBizCus = gson10.fromJson(json10, MarketCustomer.class);

        json11 = sharedpreferences.getString("LastCustomerManagerUsed", "");
        customerManager = gson11.fromJson(json11, CustomerManager.class);

        json12 = sharedpreferences.getString("LastMarketBizRegulatorUsed", "");
        marketBizReg = gson12.fromJson(json12, MarketBizRegulator.class);

        json13 = sharedpreferences.getString("LastOfficeBranchUsed", "");
        officeBranchC = gson13.fromJson(json13, OfficeBranch.class);

        json14 = sharedpreferences.getString("LastCustomerUsed", "");
        customer = gson14.fromJson(json14, Customer.class);

        json15 = sharedpreferences.getString("LastBizAdminUsed", "");
        bizAdmin = gson15.fromJson(json15, BizAdmin.class);

        isInvisible=false;
        CusDAO cusDAO = new CusDAO(this);
        AdminUserDAO adminUserDAO= new AdminUserDAO(this);
        Customer_TellerDAO customer_tellerDAO= new Customer_TellerDAO(this);


        texName = findViewById(R.id.SenddeeName);
        editMessage = findViewById(R.id.messageUser);
        btnSend = findViewById(R.id.SenddeeSubmit);

        userBundle=new Bundle();


        userBundle.putString("Machine",machine);

        profiles = new ArrayList<String>();

        if(userProfile !=null){
            profileID=userProfile.getPID();
            qbUser=userProfile.getProfQbUser();

        }
        if(qbUser !=null){
            qbUserID=qbUser.getId();
        }
        /*if(machine.equalsIgnoreCase("SuperAdmin")){
            customersN = cusDAO.getAllCustomerSpinner();
            sender="Awajima App";
            customerManagerArrayList=customer_tellerDAO.getAllCustomersManagers();
            adminUsers=adminUserDAO.getAllAdmin();
            //dialogSuperAdmin(customersN,customerManagerArrayList,adminUsers,sender,bundle, profileID,qbUserID,sendeeQBUserID);

        }*/


        spnPSendFromD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                fromProfType = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        if(fromProfType !=null){
            if(fromProfType.equalsIgnoreCase("Personal")){
                json = sharedpreferences.getString("LastProfileUsed", "");
                userProfile = gson.fromJson(json, Profile.class);
                if(userProfile !=null){
                    profileID=userProfile.getPID();
                    qbUser=userProfile.getProfQbUser();

                }
                if(qbUser !=null){
                    qbUserID=qbUser.getId();
                }


            }
            if(userProfile !=null){
                customerManagerArrayList=userProfile.getProfile_CustomerManagers();
                myMarketBizs=userProfile.getProfile_Businesses();

            }
            tellerSpinnerAdapter = new TellerSpinnerAdapter(SendSingleUserMAct.this, android.R.layout.simple_spinner_item, customerManagerArrayList);
            SpnMyTellers.setAdapter(tellerSpinnerAdapter);
            SpnMyTellers.setSelection(0);

            SpnMyTellers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    customerManager = (CustomerManager) parent.getSelectedItem();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            /*selectedTellerIndex = spnSendTeller.getSelectedItemPosition();
            try {
                customerManager = customerManagerArrayList.get(selectedTellerIndex);
                try {
                    if (customerManager != null) {
                        SendSingleUserMAct.this.bundle =null;
                        cusID =customerManager.getTID();
                        cusPhoneNo=customerManager.getTPhoneNumber();
                        userProfID=customerManager.getTellerProfile().getPID();
                        firstName=customerManager.getTFirstName();
                        surName=customerManager.getTSurname();
                        name=surName+","+firstName;
                        texName.setText("Name:"+name);


                    }
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Oops!");
            }*/


            if(fromProfType.equalsIgnoreCase("My Business")){
                layoutMyBiz.setVisibility(View.VISIBLE);
                if(userProfile !=null){
                    myMarketBizs=userProfile.getProfile_Businesses();

                }
                bizAdapter = new MarketBizArrayAdapter(SendSingleUserMAct.this, android.R.layout.simple_spinner_item, myMarketBizs);
                SpnMyBizs.setAdapter(bizAdapter);
                SpnMyBizs.setSelection(0);
                SpnMyBizs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        marketBiz = (MarketBusiness) parent.getSelectedItem();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


                if(marketBiz !=null){
                    bizName=marketBiz.getBizBrandname();
                    bizID=marketBiz.getBusinessID();
                    myMarkets=marketBiz.getmBusMarkets();

                    if(machine.equalsIgnoreCase("UserSuperAdmin")){
                        userProfile=marketBiz.getmBusOwner();
                        if(userProfile !=null){
                            profileID=userProfile.getPID();
                            qbUser=userProfile.getProfQbUser();

                        }
                        if(qbUser !=null){
                            qbUserID=qbUser.getId();
                        }
                        customersN = cusDAO.getCusForBiz(bizID);
                        sender=bizName;
                        customerManagerArrayList=customer_tellerDAO.getTellerForBiz(bizID);
                        adminUsers=adminUserDAO.getAdminUsersForBiz(bizID);
                        myMarketBizs=marketBizDAO.getAllBusinessesForProfile(userProfID);


                        //dialogSuperAdmin(customersN,customerManagerArrayList,adminUsers, sender,bundle,profileID, qbUserID, sendeeQBUserID);

                    }

                    if(machine.equalsIgnoreCase("Teller")){
                        json = sharedpreferences.getString("LastProfileUsed", "");
                        userProfile = gson.fromJson(json, Profile.class);
                        if(userProfile !=null){
                            profileID=userProfile.getPID();
                            qbUser=userProfile.getProfQbUser();
                            customerManager= userProfile.getProfile_CustomerManager();
                            customersN = userProfile.getProfileCustomers();
                            name=userProfile.getProfileFirstName();

                        }
                        if(qbUser !=null){
                            qbUserID=qbUser.getId();
                        }
                        if(customerManager !=null){
                            officeBranch=customerManager.getTOffice();
                            marketBiz=customerManager.getTellerMarketBiz();

                        }
                        if(marketBiz !=null){
                            customerManagerArrayList=marketBiz.getBizTellers();
                            bizName=marketBiz.businessName;
                        }
                        //customersN=cusDAO.getCustomersFromCurrentProfile(userProfID);


                        sender=bizName+"/"+name;

                        //doTellerDialog(customersN,officeBranch,customerManagerArrayList,sender,bundle,profileID,qbUserID,sendeeQBUserID);

                    }
                    if(machine.equalsIgnoreCase("Admin")){
                        if(adminUser !=null){
                            officeBranch=adminUser.getAdminOffice();

                        }

                        customersN = cusDAO.getAllCustomerBranchSpinner(officeBranch);
                        customerManagerArrayList=customer_tellerDAO.getAllTellerBranchSpinner(officeBranch);
                        sender=officeBranch;
                        //doAdminDialog(officeBranch,customersN,customerManagerArrayList,sender,bundle,profileID, qbUserID, sendeeQBUserID);

                    }
                    if(machine.equalsIgnoreCase("AdminUser")){
                        if(adminUser !=null){
                            officeBranch=adminUser.getAdminOffice();

                        }

                        customersN = cusDAO.getAllCustomerBranchSpinner(officeBranch);
                        customerManagerArrayList=customer_tellerDAO.getAllTellerBranchSpinner(officeBranch);
                        sender=officeBranch;
                        //doAdminDialog(officeBranch, customersN, customerManagerArrayList, sender,bundle, profileID,qbUserID,sendeeQBUserID);

                    }


                }




            }
            if(fromProfType.equalsIgnoreCase("My Partnership Profile")){
                if(marketBizPartner !=null){
                    userProfile=marketBizPartner.getBizPartnerProf();

                }
                if(userProfile !=null){
                    profileID=userProfile.getPID();
                    qbUser=userProfile.getProfQbUser();

                }
                if(qbUser !=null){
                    qbUserID=qbUser.getId();
                }

            }
            if(fromProfType.equalsIgnoreCase("My Customership Profile")){
                if(customer !=null){
                    userProfile=customer.getCusProfile();

                }
                if(userProfile !=null){
                    profileID=userProfile.getPID();
                    qbUser=userProfile.getProfQbUser();

                }
                if(qbUser !=null){
                    qbUserID=qbUser.getId();
                }

            }
            if(fromProfType.equalsIgnoreCase("My Market Admin Profile")){
                if(marketAdmin !=null){
                    userProfile=marketAdmin.getMarketAdminProf();
                }
                if(userProfile !=null){
                    profileID=userProfile.getPID();
                    qbUser=userProfile.getProfQbUser();

                }
                if(qbUser !=null){
                    qbUserID=qbUser.getId();
                }

            }
            if(fromProfType.equalsIgnoreCase("My Regulator Profile")){
                if(marketBizReg !=null){
                    userProfile=marketBizReg.getRegulatorProf();

                }
                if(userProfile !=null){
                    profileID=userProfile.getPID();
                    qbUser=userProfile.getProfQbUser();

                }
                if(qbUser !=null){
                    qbUserID=qbUser.getId();
                }

            }
            if(fromProfType.equalsIgnoreCase("My Market Profile")){
                if(market !=null){
                    userProfile=market.getMarketProf();
                }
                if(userProfile !=null){
                    profileID=userProfile.getPID();
                    qbUser=userProfile.getProfQbUser();

                }
                if(qbUser !=null){
                    qbUserID=qbUser.getId();
                }

            }
            if(fromProfType.equalsIgnoreCase("Business Regulator")){
                if(marketBizReg !=null){
                    userProfile=marketBizReg.getRegulatorProf();
                }
                if(userProfile !=null){
                    profileID=userProfile.getPID();
                    qbUser=userProfile.getProfQbUser();

                }
                if(qbUser !=null){
                    qbUserID=qbUser.getId();
                }

            }
            if(fromProfType.equalsIgnoreCase("My Teller Profile")){
                if(customerManager !=null){
                    userProfile=customerManager.getTellerProfile();

                }
                if(userProfile !=null){
                    profileID=userProfile.getPID();
                    qbUser=userProfile.getProfQbUser();

                }
                if(qbUser !=null){
                    qbUserID=qbUser.getId();
                }

            }
            if(fromProfType.equalsIgnoreCase("My Business Admin Profile")){
                if(bizAdmin !=null){
                    userProfile=bizAdmin.getBizAdminProf();
                }
                if(userProfile !=null){
                    profileID=userProfile.getPID();
                    qbUser=userProfile.getProfQbUser();

                }
                if(qbUser !=null){
                    qbUserID=qbUser.getId();
                }

            }
            if(fromProfType.equalsIgnoreCase("My Admin Profile")){
                if(adminUser !=null){
                    userProfile=adminUser.getAdminProf();
                }
                if(userProfile !=null){
                    profileID=userProfile.getPID();
                    qbUser=userProfile.getProfQbUser();

                }
                if(qbUser !=null){
                    qbUserID=qbUser.getId();
                }

            }
            if(fromProfType.equalsIgnoreCase("My Donor Profile")){
                if(marketBizDonor !=null){
                    userProfile=marketBizDonor.getMarketBizDProf();

                }
                if(userProfile !=null){
                    profileID=userProfile.getPID();
                    qbUser=userProfile.getProfQbUser();

                }
                if(qbUser !=null){
                    qbUserID=qbUser.getId();
                }

            }
        }
        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                sendeeType = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(sendeeType !=null){
            if(sendeeType.equalsIgnoreCase("Market")){
                layoutAllMarkets.setVisibility(View.VISIBLE);
                layoutAllBizs.setVisibility(View.GONE);
                layoutCus.setVisibility(View.GONE);
                layoutTeller.setVisibility(View.GONE);
                layoutAdmin.setVisibility(View.GONE);
                layoutOffice.setVisibility(View.GONE);
                layoutPartners.setVisibility(View.GONE);
                layoutReg.setVisibility(View.GONE);
                layoutMyDonors.setVisibility(View.GONE);
                layoutMarketAdmin.setVisibility(View.GONE);



            }
            if(sendeeType.equalsIgnoreCase("Business Deal Partner")){
                myMarkets=marketDAo.getMarketsFromProfile(userProfID);

                layoutAllBizs.setVisibility(View.VISIBLE);
                layoutAllMarkets.setVisibility(View.GONE);
                layoutCus.setVisibility(View.GONE);
                layoutTeller.setVisibility(View.GONE);
                layoutAdmin.setVisibility(View.GONE);
                layoutOffice.setVisibility(View.GONE);
                layoutPartners.setVisibility(View.GONE);
                layoutReg.setVisibility(View.GONE);
                layoutMyDonors.setVisibility(View.GONE);
                layoutMarketAdmin.setVisibility(View.GONE);

            }
            if(sendeeType.equalsIgnoreCase("Business Teller")){
                myMarkets=marketDAo.getMarketsFromProfile(userProfID);
                layoutAllBizs.setVisibility(View.GONE);
                layoutAllMarkets.setVisibility(View.GONE);
                layoutCus.setVisibility(View.GONE);
                layoutTeller.setVisibility(View.VISIBLE);
                layoutAdmin.setVisibility(View.GONE);
                layoutOffice.setVisibility(View.GONE);
                layoutPartners.setVisibility(View.GONE);
                layoutReg.setVisibility(View.GONE);
                layoutMyDonors.setVisibility(View.GONE);
                layoutMarketAdmin.setVisibility(View.GONE);

            }
            if(sendeeType.equalsIgnoreCase("Business Regulator")){
                myMarkets=marketDAo.getMarketsFromProfile(userProfID);
                layoutAllBizs.setVisibility(View.GONE);
                layoutAllMarkets.setVisibility(View.GONE);
                layoutCus.setVisibility(View.GONE);
                layoutTeller.setVisibility(View.GONE);
                layoutAdmin.setVisibility(View.GONE);
                layoutOffice.setVisibility(View.GONE);
                layoutPartners.setVisibility(View.GONE);
                layoutReg.setVisibility(View.VISIBLE);
                layoutMyDonors.setVisibility(View.GONE);
                layoutMarketAdmin.setVisibility(View.GONE);

            }
            if(sendeeType.equalsIgnoreCase("Business Donor")){
                myMarkets=marketDAo.getMarketsFromProfile(userProfID);
                layoutMyDonors.setVisibility(View.VISIBLE);
                layoutAllBizs.setVisibility(View.GONE);
                layoutAllMarkets.setVisibility(View.GONE);
                layoutCus.setVisibility(View.GONE);
                layoutTeller.setVisibility(View.GONE);
                layoutAdmin.setVisibility(View.GONE);
                layoutOffice.setVisibility(View.GONE);
                layoutPartners.setVisibility(View.GONE);
                layoutReg.setVisibility(View.GONE);
                layoutMarketAdmin.setVisibility(View.GONE);

            }
            if(sendeeType.equalsIgnoreCase("Business Partner")){
                myMarkets=marketDAo.getMarketsFromProfile(userProfID);
                layoutMyDonors.setVisibility(View.GONE);
                layoutAllBizs.setVisibility(View.GONE);
                layoutAllMarkets.setVisibility(View.GONE);
                layoutCus.setVisibility(View.GONE);
                layoutTeller.setVisibility(View.GONE);
                layoutAdmin.setVisibility(View.GONE);
                layoutOffice.setVisibility(View.GONE);
                layoutPartners.setVisibility(View.VISIBLE);
                layoutReg.setVisibility(View.GONE);
                layoutMarketAdmin.setVisibility(View.GONE);

            }
            if(sendeeType.equalsIgnoreCase("Business Customer")){
                myMarkets=marketDAo.getMarketsFromProfile(userProfID);

                layoutMyDonors.setVisibility(View.GONE);
                layoutAllBizs.setVisibility(View.GONE);
                layoutAllMarkets.setVisibility(View.GONE);
                layoutCus.setVisibility(View.VISIBLE);
                layoutTeller.setVisibility(View.GONE);
                layoutAdmin.setVisibility(View.GONE);
                layoutOffice.setVisibility(View.GONE);
                layoutPartners.setVisibility(View.GONE);
                layoutReg.setVisibility(View.GONE);
                layoutMarketAdmin.setVisibility(View.GONE);

            }
            if(sendeeType.equalsIgnoreCase("Business Admin")){
                myMarkets=marketDAo.getMarketsFromProfile(userProfID);
                layoutMyDonors.setVisibility(View.GONE);
                layoutAllBizs.setVisibility(View.GONE);
                layoutAllMarkets.setVisibility(View.GONE);
                layoutCus.setVisibility(View.GONE);
                layoutTeller.setVisibility(View.GONE);
                layoutAdmin.setVisibility(View.VISIBLE);
                layoutOffice.setVisibility(View.GONE);
                layoutPartners.setVisibility(View.GONE);
                layoutReg.setVisibility(View.GONE);
                layoutMarketAdmin.setVisibility(View.GONE);

            }
            if(sendeeType.equalsIgnoreCase("Market Admin")){
                myMarkets=marketDAo.getMarketsFromProfile(userProfID);
                layoutMarketAdmin.setVisibility(View.VISIBLE);
                layoutMyDonors.setVisibility(View.GONE);
                layoutAllBizs.setVisibility(View.GONE);
                layoutAllMarkets.setVisibility(View.GONE);
                layoutCus.setVisibility(View.GONE);
                layoutTeller.setVisibility(View.GONE);
                layoutAdmin.setVisibility(View.GONE);
                layoutOffice.setVisibility(View.GONE);
                layoutPartners.setVisibility(View.GONE);
                layoutReg.setVisibility(View.GONE);

            }
            if(sendeeType.equalsIgnoreCase("Market Business")){
                myMarkets=marketDAo.getMarketsFromProfile(userProfID);


                layoutMarketAdmin.setVisibility(View.GONE);
                layoutMyDonors.setVisibility(View.GONE);
                layoutAllBizs.setVisibility(View.VISIBLE);
                layoutAllMarkets.setVisibility(View.GONE);
                layoutCus.setVisibility(View.GONE);
                layoutTeller.setVisibility(View.GONE);
                layoutAdmin.setVisibility(View.GONE);
                layoutOffice.setVisibility(View.VISIBLE);
                layoutPartners.setVisibility(View.GONE);
                layoutReg.setVisibility(View.GONE);

                officeArrayList=marketBiz.getOfficeBranches();

                officeAdapter = new OfficeAdapter(this, android.R.layout.simple_spinner_item, officeArrayList);
                spnOfficeBranch.setAdapter(officeAdapter);

                spnOfficeBranch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        selectedOfficeIndex = spnOfficeBranch.getSelectedItemPosition();
                        if(officeArrayList !=null){

                            try {
                                officeBranchC = officeArrayList.get(selectedOfficeIndex);

                            } catch (NullPointerException e) {
                                System.out.println("Oops!");
                            }

                        }


                        //businessDeal = businessDealArrayList.get(i);
                    }
                });


            }

        marketBizAdapter = new MarketBizArrayAdapter(SendSingleUserMAct.this, android.R.layout.simple_spinner_item, myMarketBizs);
        spnAllBizs.setAdapter(marketBizAdapter);
        spnAllBizs.setSelection(0);

        selectedMarketBizIndex = spnAllBizs.getSelectedItemPosition();

        try {
            marketBiz = myMarketBizs.get(selectedMarketBizIndex);
            try {
                if (marketBiz != null) {
                    SendSingleUserMAct.this.bundle =null;
                    userProfile=marketBiz.getmBusOwner();

                    if(userProfile !=null){
                        profileID=userProfile.getPID();
                        qbUser=userProfile.getProfQbUser();
                        firstName=userProfile.getProfileFirstName();
                        surName=userProfile.getProfileLastName();

                    }
                    if(qbUser !=null){
                        qbUserID=qbUser.getId();
                    }

                    name=surName+","+firstName;
                    texName.setText("Name:"+name);


                }
            } catch (NullPointerException e) {
                System.out.println("Oops!");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }

        marketArrayAdapter = new ArrayAdapter<Market>(SendSingleUserMAct.this, android.R.layout.simple_spinner_item, myMarkets);
        marketArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAllMarkets.setAdapter(marketArrayAdapter);
        spnAllMarkets.setSelection(0);

        selectedMarketIndex = spnAllMarkets.getSelectedItemPosition();
        if(myMarkets !=null){
            market = myMarkets.get(selectedMarketIndex);


        }
        if(market !=null){
            userProfile=market.getMarketProf();

        }

        if(userProfile !=null){
            profileID=userProfile.getPID();
            qbUser=userProfile.getProfQbUser();

        }
        if(qbUser !=null){
            qbUserID=qbUser.getId();
        }




        customersAdapter = new ArrayAdapter<Customer>(SendSingleUserMAct.this, android.R.layout.simple_spinner_item, customersN);
        customersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSendCustomer.setAdapter(customersAdapter);
        spnSendCustomer.setSelection(0);

        selectedCustomerIndex = spnSendCustomer.getSelectedItemPosition();
        try {
            customer = customersN.get(selectedCustomerIndex);
            try {
                if (customer != null) {
                    SendSingleUserMAct.this.bundle =null;
                    cusID =customer.getCusUID();
                    cusPhoneNo=customer.getCusPhoneNumber();
                    userProfID=customer.getCusProfile().getPID();
                    firstName=customer.getCusFirstName();
                    surName=customer.getCusSurname();
                    name=surName+","+firstName;
                    texName.setText("Name:"+name);

                }
            } catch (NullPointerException e) {
                System.out.println("Oops!");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }



            customersAdapter = new ArrayAdapter<Customer>(SendSingleUserMAct.this, android.R.layout.simple_spinner_item, customersN);
            customersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnSendCustomer.setAdapter(customersAdapter);
            spnSendCustomer.setSelection(0);

            selectedCustomerIndex = spnSendCustomer.getSelectedItemPosition();
            try {
                customer = customersN.get(selectedCustomerIndex);
                try {
                    if (customer != null) {
                        SendSingleUserMAct.this.bundle =null;
                        cusID =customer.getCusUID();
                        cusPhoneNo=customer.getCusPhoneNumber();
                        userProfID=customer.getCusProfile().getPID();
                        firstName=customer.getCusFirstName();
                        surName=customer.getCusSurname();
                        name=surName+","+firstName;
                        texName.setText("Name:"+name);

                    }
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Oops!");
            }





        }
        registerReceiver();





        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //doMessageSend(qbUserID,sendeeQBUserID);
                sendPushMessage(qbUserID,sendeeQBUserID);

            }
        });
        btnSend.setOnClickListener(this::SendSinglePush);


    }
    private void registerReceiver() {
        LocalBroadcastManager.getInstance(this).registerReceiver(pushBroadcastReceiver,
                new IntentFilter(Consts.ACTION_NEW_FCM_EVENT));
    }
    private void sendPushMessage(int qbUserID, int sendeeQBUserID) {
        editMessage = findViewById(R.id.messageUser);
        String outMessage = editMessage.getText().toString().trim();
        if (!isValidData(outMessage)) {
            Toast.makeText(SendSingleUserMAct.this, R.string.error_field_is_empty , Toast.LENGTH_LONG).show();
            invalidateOptionsMenu();
            return;
        }

        QBEvent qbEvent = new QBEvent();
        qbEvent.setNotificationType(QBNotificationType.PUSH);
        qbEvent.setEnvironment(QBEnvironment.PRODUCTION);
        qbEvent.setMessage(outMessage);

        userIds = new StringifyArrayList<>();
        userIds.add(qbUserID);
        userIds.add(sendeeQBUserID);
        userIds.add(QBSessionManager.getInstance().getSessionParameters().getUserId());
        qbEvent.setUserIds(userIds);
        //qbEvent.setUserId();

        QBPushNotifications.createEvent(qbEvent).performAsync(new QBEntityCallback<QBEvent>() {
            @Override
            public void onSuccess(QBEvent qbEvent, Bundle bundle) {
                //progressBar.setVisibility(View.INVISIBLE);
                KeyboardUtils.hideKeyboard(editMessage);
                editMessage.setText(null);
                invalidateOptionsMenu();
            }

            @Override
            public void onError(QBResponseException e) {
                View rootView = findViewById(R.id.activity_messages);
                Toast.makeText(SendSingleUserMAct.this,R.string.sending_error , Toast.LENGTH_LONG).show();
                /*showErrorSnackbar(rootView, R.string.sending_error, e, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendPushMessage();
                    }
                });*/
                //progressBar.setVisibility(View.INVISIBLE);
                KeyboardUtils.hideKeyboard(editMessage);
                invalidateOptionsMenu();
            }
        });

        //progressBar.setVisibility(View.VISIBLE);
    }
    private void unsubscribeFromPushes() {
        if (QBPushManager.getInstance().isSubscribedToPushes()) {
            QBPushManager.getInstance().addListener(new QBPushManager.QBSubscribeListener() {
                @Override
                public void onSubscriptionCreated() {
                    // empty
                }

                @Override
                public void onSubscriptionError(Exception e, int i) {
                    // empty
                }

                @Override
                public void onSubscriptionDeleted(boolean success) {
                    Log.d(TAG, "Subscription Deleted -> Success: " + success);
                    QBPushManager.getInstance().removeListener(this);
                    userLogout();
                }
            });
            SubscribeService.unSubscribeFromPushes(SendSingleUserMAct.this);
        }
    }
    private void userLogout() {
        Log.d(TAG, "SignOut");
        //showProgressDialog(R.string.dlg_logout);

        QBUsers.signOut().performAsync(new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid, Bundle bundle) {
                Log.d(TAG, "SignOut Successful");
                //SharedPrefsHelper.getInstance().removeQbUser();
                Intent myIntent = new Intent(SendSingleUserMAct.this, LoginActivity.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);
                //hideProgressDialog();
                finish();
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d(TAG, "Unable to SignOut: " + e.getMessage());
                //hideProgressDialog();
                View rootView = findViewById(R.id.activity_messages);

            }
        });
        SharedPreferences preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();

    }

    private boolean isValidData(String message) {
        return !TextUtils.isEmpty(message);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // empty
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // empty
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() >= getResources().getInteger(R.integer.push_max_length)) {
            ToastUtils.shortToast(R.string.error_too_long_push);
        }
    }

    protected void doBundleDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose User to Send a Message");
        builder.setIcon(R.drawable.ic_icon2);
        CusDAO cusDAO = new CusDAO(this);
        builder.setItems(new CharSequence[]
                        {"Selected User", "All Customers"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                layoutCus.setVisibility(View.GONE);
                                layoutTeller.setVisibility(View.GONE);
                                layoutAdmin.setVisibility(View.GONE);
                                layoutOffice.setVisibility(View.GONE);

                                break;
                            case 1:
                                customers=cusDAO.getAllCustomers11();
                                customersAdapter = new ArrayAdapter<Customer>(SendSingleUserMAct.this, android.R.layout.simple_spinner_item, customers);
                                customersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spnSendCustomer.setAdapter(customersAdapter);
                                spnSendCustomer.setSelection(0);

                                selectedCustomerIndex = spnSendCustomer.getSelectedItemPosition();
                                try {
                                    customer = customers.get(selectedCustomerIndex);
                                    try {
                                        if (customer != null) {
                                            bundle=null;
                                            cusID =customer.getCusUID();
                                            cusPhoneNo=customer.getCusPhoneNumber();
                                            userProfID=customer.getCusProfile().getPID();
                                            firstName=customer.getCusFirstName();
                                            surName=customer.getCusSurname();
                                            name=surName+","+firstName;
                                            texName.setText("Name:"+name);


                                        }
                                    } catch (NullPointerException e) {
                                        System.out.println("Oops!");
                                    }
                                } catch (IndexOutOfBoundsException e) {
                                    System.out.println("Oops!");
                                }


                                layoutCus.setVisibility(View.VISIBLE);
                                layoutTeller.setVisibility(View.GONE);
                                layoutAdmin.setVisibility(View.GONE);
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
    protected void dialogSuperAdmin(ArrayList<Customer> customersN, ArrayList<CustomerManager> customerManagerArrayList, ArrayList<AdminUser> adminUsers, String sender, Bundle bundle, long profileID, int qbUserID, int sendeeQBUserID){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send to:");
        builder.setIcon(R.drawable.ic_icon2);
        CusDAO cusDAO = new CusDAO(this);
        builder.setItems(new CharSequence[]
                        {"An Admin", "A Teller","A Branch Office","A Customer"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                layoutCus.setVisibility(View.GONE);
                                layoutTeller.setVisibility(View.GONE);
                                layoutOffice.setVisibility(View.GONE);

                                break;
                            case 1:
                                layoutCus.setVisibility(View.GONE);
                                layoutAdmin.setVisibility(View.GONE);
                                layoutOffice.setVisibility(View.GONE);


                                customers=cusDAO.getAllCustomers11();
                                customersAdapter = new ArrayAdapter<Customer>(SendSingleUserMAct.this, android.R.layout.simple_spinner_item, customers);
                                customersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spnSendCustomer.setAdapter(customersAdapter);
                                spnSendCustomer.setSelection(0);

                                selectedCustomerIndex = spnSendCustomer.getSelectedItemPosition();
                                try {
                                    customer = customers.get(selectedCustomerIndex);
                                    try {
                                        if (customer != null) {
                                            SendSingleUserMAct.this.bundle =null;
                                            cusID =customer.getCusUID();
                                            cusPhoneNo=customer.getCusPhoneNumber();
                                            userProfID=customer.getCusProfile().getPID();
                                            firstName=customer.getCusFirstName();
                                            surName=customer.getCusSurname();
                                            name=surName+","+firstName;
                                            texName.setText("Name:"+name);


                                        }
                                    } catch (NullPointerException e) {
                                        System.out.println("Oops!");
                                    }
                                } catch (IndexOutOfBoundsException e) {
                                    System.out.println("Oops!");
                                }

                                break;

                            case 2:
                                layoutCus.setVisibility(View.GONE);
                                layoutTeller.setVisibility(View.GONE);
                                layoutAdmin.setVisibility(View.GONE);

                                break;


                            case 3:
                                layoutAdmin.setVisibility(View.GONE);
                                layoutTeller.setVisibility(View.GONE);
                                layoutOffice.setVisibility(View.GONE);
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
    public void SelectSpinnerValue(View view) {
        spnSendCustomer.setSelection(2);
    }

    public  void doMessageSend(int qbUserID, int sendeeQBUserID){
        calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        sharedpreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        machine = sharedpreferences.getString("Machine", "");
        gson = new Gson();
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        MessageDAO messageDAO = new MessageDAO(this);
        if(userProfile !=null){
            userName=userProfile.getProfileLastName()+","+userProfile.getProfileFirstName();


        }
        if(userProfile !=null){
            bizID=userProfile.getProfileBusinessID();
            marketID=userProfile.getProfileMarketID();
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String meassageDate = mdformat.format(calendar.getTime());
        messageID = random.nextInt((int) (Math.random() * 129) + 11);
        try {
            //messageDAO.insertMessage(profileID,customerID, bizID,marketID, tittle,messageDetails,"Awajima App",sendee,officeBranch,meassageDate);
            //messageDAO.insertMessage(userProfID, iD,messageID, bizID, messageDetails,sender,sendee,officeBranch,meassageDate, bizID);


        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        //Toast.makeText(SendSingleUserMAct.this, "Send a message to the Customer", Toast.LENGTH_SHORT).show();
        /*Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
        messageToCustomer=editMessage.getText().toString();
        message = Message.creator(
                new com.twilio.type.PhoneNumber(cusPhoneNo),
                new com.twilio.type.PhoneNumber("234"+cusPhoneNo),
                messageToCustomer)
                .create();

        messageToCustomer=editMessage.getText().toString();
        message = com.twilio.rest.api.v2010.account.Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:cusPhoneNo"),
                new com.twilio.type.PhoneNumber("whatsapp:cusPhoneNo"),
                messageToCustomer)
                .create();*/

        Intent userIntent = new Intent(SendSingleUserMAct.this, LoginDirAct.class);
        userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        userIntent.putExtras(userBundle);
        startActivity(userIntent);

    }

    public void SendSinglePush(View view) {
    }
}