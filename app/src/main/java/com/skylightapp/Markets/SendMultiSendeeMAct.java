package com.skylightapp.Markets;

import static com.skylightapp.Classes.ImageUtil.TAG;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.quickblox.core.helper.StringifyArrayList;
import com.quickblox.users.model.QBUser;
import com.skylightapp.Adapters.OfficeAdapter;
import com.skylightapp.Admins.SendSingleUserMAct;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Database.AdminUserDAO;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.Customer_TellerDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MarketBizDAO;
import com.skylightapp.Database.MarketDAO;
import com.skylightapp.Interfaces.Consts;
import com.skylightapp.MarketClasses.BizAdmin;
import com.skylightapp.MarketClasses.BizDealPartner;
import com.skylightapp.MarketClasses.BusinessDeal;
import com.skylightapp.MarketClasses.BusinessDealSub;
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
import com.skylightapp.MarketClasses.MultiSelBizDealSp;
import com.skylightapp.MarketClasses.MultiSelBizSpinner;
import com.skylightapp.MarketClasses.MultiSelCusSpinner;
import com.skylightapp.MarketClasses.MultiSelDonorSpinner;
import com.skylightapp.MarketClasses.MultiSelMarketAdminSp;
import com.skylightapp.MarketClasses.MultiSelMarketSpinner;
import com.skylightapp.MarketClasses.MultiSelMarketTellerSp;
import com.skylightapp.MarketClasses.MultiSelOfficeSpinner;
import com.skylightapp.MarketClasses.MultiSelPatnersSp;
import com.skylightapp.MarketClasses.MultiSelRegulatorSpn;
import com.skylightapp.R;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class SendMultiSendeeMAct extends AppCompatActivity {
    private ArrayList<BusinessDeal> businessDeals;
    private ArrayList<BusinessDealSub> businessDealSubs;

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
    private AppCompatSpinner spnPSendFromD,spnType;
    private LinearLayoutCompat layoutReg,layoutPartners,layoutBizDeal,layoutAllMarkets,layoutAllBizs,layoutMyDonors,layoutMarketAdmin;

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
    private String fromProfType;
    private BizAdmin bizAdmin;
    private MarketDAO marketDAo;
    private Customer_TellerDAO customer_tellerDAO;

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
    private ArrayAdapter<CustomerManager> customerManagerArrayAdapter;
    private ArrayAdapter<Market> marketArrayAdapter;
    private ArrayAdapter<MarketCustomer> marketCustomerArrayAdapter;
    private ArrayAdapter<MarketBizDonor> marketBizDonorArrayAdapter;
    private ArrayAdapter<MarketBizPartner> marketBizPartnerArrayAdapter;
    private ArrayAdapter<MarketBizRegulator> marketBizRegulatorArrayAdapter;
    private ArrayAdapter<MarketAdmin> adminArrayAdapter;
    private ArrayAdapter<MarketBusiness> marketBusinessArrayAdapter;
    private int selectedMarketIndex;
    private ArrayAdapter<AdminUser> adminUserArrayAdapter;
    private ArrayAdapter<UserSuperAdmin> userSuperAdminArrayAdapter;

    int selectedTellerIndex,marketID,selectedPartnerIndex,selectedMarketBizIndex,selectedDonorIndex,selectedMarketCusIndex;
    private StringifyArrayList<Integer> userIds;
    private MarketBizDAO marketBizDAO;
    private MultiSelCusSpinner multiSelCusSpinner;
    private MultiSelMarketTellerSp spnToTeller;
    private MultiSelMarketSpinner spnMarkets;
    private MultiSelDonorSpinner SpnDonor;
    private MultiSelRegulatorSpn spnRegulators;
    private MultiSelOfficeSpinner spnOfficeBranch;
    private MultiSelPatnersSp spnPartners;
    private MultiSelBizSpinner spnAllBizs;
    private MultiSelMarketAdminSp SpnMarketAdmin;
    private MultiSelBizDealSp spnBizDeal;

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
        Intent intent = new Intent(context, SendMultiSendeeMAct.class);
        intent.putExtra(Consts.EXTRA_FCM_MESSAGE, message);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_send_multi_user_m);
        setTitle("Awajima Multi Messenger");
        loadingProgressBar = findViewById(R.id.progressSDF);
        layoutCus = findViewById(R.id.laCus);
        layoutTeller = findViewById(R.id.laTeller);
        layoutBizDeal = findViewById(R.id.laBDeal);
        layoutAdmin = findViewById(R.id.laAdmin);
        layoutMyDonors = findViewById(R.id.laDonors);
        layoutOffice = findViewById(R.id.laOffice);
        layoutPartners = findViewById(R.id.laPartners);
        layoutReg = findViewById(R.id.laRegulators);
        layoutAllBizs = findViewById(R.id.laBiz);
        layoutAllMarkets = findViewById(R.id.laMarkets);
        layoutMarketAdmin = findViewById(R.id.layoutMAdm);

        spnBizDeal = findViewById(R.id.Spn_dealBiz);
        spnType = findViewById(R.id.deeUserType);
        spnPSendFromD = findViewById(R.id.spnPFr);
        multiSelCusSpinner = findViewById(R.id.deeCus);
        spnToTeller = findViewById(R.id.deeCM);
        spnSendAdmin = findViewById(R.id.deeAdmin);
        SpnDonor = findViewById(R.id.deeDonor);
        spnOfficeBranch = findViewById(R.id.deeOff);
        spnRegulators = findViewById(R.id.deeReg);
        spnAllBizs = findViewById(R.id.deeBiz);
        spnMarkets = findViewById(R.id.deeMarkets);
        SpnMarketAdmin = findViewById(R.id.SdeeAA);
        edt_Purpose = findViewById(R.id.m_Purpose);
        editMessage = findViewById(R.id.message_Edt);
        btnSend = findViewById(R.id.SdeeSend);
        spnPartners = findViewById(R.id.eePartners);
        texName = findViewById(R.id.deeName);
        marketDAo= new MarketDAO(this);
        marketBizDAO= new MarketBizDAO(this);
        customer_tellerDAO= new Customer_TellerDAO(this);
        isInvisible=false;
        CusDAO cusDAO = new CusDAO(this);
        AdminUserDAO adminUserDAO= new AdminUserDAO(this);
        Customer_TellerDAO customer_tellerDAO= new Customer_TellerDAO(this);


        userBundle=new Bundle();


        userBundle.putString("Machine",machine);

        profiles = new ArrayList<String>();

        myMarketBizs=new ArrayList<>();
        myMarkets=new ArrayList<>();
        myMarketCuss=new ArrayList<>();
        marketAdmins=new ArrayList<>();
        marketBizRegulators=new ArrayList<>();
        marketBizPartners=new ArrayList<>();
        marketBizDonors=new ArrayList<>();
        officeArrayList=new ArrayList<>();
        bizAdmin= new BizAdmin();
        qbUser= new QBUser();
        sendeeQBUser= new QBUser();
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



        if(sendeeQBUser !=null){
            sendeeQBUserID=sendeeQBUser.getId();
        }
        if(userProfile !=null){
            profileID=userProfile.getPID();
            userBundle.putLong(PROFILE_ID,profileID);

        }
        if(qbUser !=null){
            qbUserID=qbUser.getId();
        }
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
                if(userProfile !=null){
                    profileID=userProfile.getPID();
                    qbUser=userProfile.getProfQbUser();

                }
                if(qbUser !=null){
                    qbUserID=qbUser.getId();
                }


            }
            if(fromProfType.equalsIgnoreCase("My Business")){

                if(marketBiz !=null){
                    userProfile=marketBiz.getmBusOwner();

                }
                if(userProfile !=null){
                    profileID=userProfile.getPID();
                    qbUser=userProfile.getProfQbUser();

                }
                if(qbUser !=null){
                    qbUserID=qbUser.getId();
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


        marketBizAdapter = new MarketBizArrayAdapter(SendMultiSendeeMAct.this, android.R.layout.simple_spinner_item, myMarketBizs);
        spnAllBizs.setAdapter(marketBizAdapter);
        spnAllBizs.setSelection(0);

        selectedMarketBizIndex = spnAllBizs.getSelectedItemPosition();

        try {
            marketBiz = myMarketBizs.get(selectedMarketBizIndex);
            try {
                if (marketBiz != null) {
                    SendMultiSendeeMAct.this.bundle =null;
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

        marketArrayAdapter = new ArrayAdapter<Market>(SendMultiSendeeMAct.this, android.R.layout.simple_spinner_item, myMarkets);
        marketArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMarkets.setAdapter(marketArrayAdapter);
        spnMarkets.setSelection(0);

        selectedMarketIndex = spnMarkets.getSelectedItemPosition();
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




        customersAdapter = new ArrayAdapter<Customer>(SendMultiSendeeMAct.this, android.R.layout.simple_spinner_item, customersN);
        customersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSendCustomer.setAdapter(customersAdapter);
        spnSendCustomer.setSelection(0);

        selectedCustomerIndex = spnSendCustomer.getSelectedItemPosition();
        try {
            customer = customersN.get(selectedCustomerIndex);
            try {
                if (customer != null) {
                    SendMultiSendeeMAct.this.bundle =null;
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
        //customerManagerArrayList = spnSendTeller.getSelectedItems();

        customerManagerArrayAdapter = new ArrayAdapter<CustomerManager>(SendMultiSendeeMAct.this, android.R.layout.simple_spinner_item, customerManagerArrayList);
        customerManagerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSendTeller.setAdapter(customersAdapter);
        spnSendTeller.setSelection(0);


        selectedTellerIndex = spnSendTeller.getSelectedItemPosition();
        try {
            customerManager = customerManagerArrayList.get(selectedTellerIndex);
            try {
                if (customerManager != null) {
                    SendMultiSendeeMAct.this.bundle =null;
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
        }


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnSend.setOnClickListener(this::sendMultiPush);
    }

    public void sendMultiPush(View view) {
    }
}