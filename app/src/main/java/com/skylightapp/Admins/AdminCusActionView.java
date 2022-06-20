package com.skylightapp.Admins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.skylightapp.Adapters.AccountAdapter2;
import com.skylightapp.Adapters.DocumentAdapter;
import com.skylightapp.Adapters.MessageAdapter;
import com.skylightapp.Adapters.MySavingsCodeAdapter;
import com.skylightapp.Adapters.SkyLightPackageAdapter;
import com.skylightapp.Adapters.StandingOrderAdapter;
import com.skylightapp.Adapters.SuperSavingsAdapter;
import com.skylightapp.Adapters.TransactionAdapter;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Classes.Message;
import com.skylightapp.Classes.PaymentCode;
import com.skylightapp.Classes.PaymentDoc;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.skylightapp.Classes.Customer.CUSTOMER_FIRST_NAME;
import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_SURNAME;
import static com.skylightapp.Classes.Profile.PROFILE_PHONE;

public class AdminCusActionView extends AppCompatActivity {
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson;
    private String json;
    private Profile userProfile;
    private String profileID;
    FloatingActionButton floatingActionButton;
    //CustomerTabAdapter adapter;
    public static final String ACCOUNT_SID = System.getenv("ACb6e4c829a5792a4b744a3e6bd1cf2b4e");
    public static final String AUTH_TOKEN = System.getenv("0d5cbd54456dd0764786db0c37212578");
    private List<CustomerDailyReport> customerDailyReports;
    private StandingOrderAdapter standingOrderAdapter;
    private TransactionAdapter transactionAdapter;
    private AccountAdapter2 accountAdapter;
    private MySavingsCodeAdapter codeAdapter;
    private  MessageAdapter  messageAdapter;
    private ArrayList<Message> messageArrayList;
    private DocumentAdapter documentAdapter;
    private ArrayList<PaymentDoc> paymentDocs;

    SkyLightPackageAdapter packageAdapter;
    private  SuperSavingsAdapter savingsAdapter;
    DBHelper dbHelper;
    private ArrayList<CustomerDailyReport> savingsArrayList;
    private ArrayList<SkyLightPackage> skyLightPackages;
    private ArrayList<Account> accounts4;
    private ArrayList<Transaction> transactions2;
    private ArrayList<Loan> cusLoans;
    private ArrayAdapter<Loan>loanArrayAdapter;

    private ArrayList<StandingOrder> standingOrders;
    private ArrayList<PaymentCode> paymentCodeArrayList;
    //private ArrayList<GroupAccount> paymentCodeArrayList;
    Bundle customerBundle;
    Customer customer;
    int customerID;
    AppCompatTextView txtName,txtID;
    String names,cusPhoneNo,cusFirstName,cusLastName;
    LatLng cusLoc;
    String packages,loans,savings,transactions,savingsCode,doc,messages,so,grpSavings;
    com.melnykov.fab.FloatingActionButton fab;
    AppCompatImageButton btnPacks,btnSavings,btnCode,btnLoans,btnTx,btnMessage,btnSO,btnDoc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_admin_cus_action_view);
        customerBundle= getIntent().getExtras();
        RecyclerView recyclerPackages = findViewById(R.id.recyclerPackages);
        RecyclerView recyclerSavings = findViewById(R.id.recyclerSavings);
        RecyclerView recyclerCodes = findViewById(R.id.recyclerCodes);
        RecyclerView rcyclerTransactions = findViewById(R.id.recyclerTx);
        RecyclerView recyclerMessages = findViewById(R.id.recyclerMessages);
        RecyclerView recyclerStandingOrder = findViewById(R.id.recyclerSO);
        RecyclerView rcyclerDocs = findViewById(R.id.recyclerDocs);
        RecyclerView recyclerLoans = findViewById(R.id.recyclerLoans);

        btnSO = findViewById(R.id.ic_so);
        btnSO.setOnClickListener(this::icso);
        btnSO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(customerBundle !=null) {
                    customer = customerBundle.getParcelable("Customer");
                    cusLoc = customerBundle.getParcelable("PreviousLocation");
                    customerID = customerBundle.getInt(CUSTOMER_ID);
                    cusFirstName = customerBundle.getString(CUSTOMER_FIRST_NAME);
                    cusLastName = customerBundle.getString(CUSTOMER_SURNAME);
                    cusPhoneNo = customerBundle.getString(PROFILE_PHONE);

                    so = customerBundle.getString(so);

                    names = customer.getCusFirstName() + "" + customer.getCusSurname();
                    txtName = findViewById(R.id.INameCus);
                    txtID = findViewById(R.id.IDCus);
                    txtName.setText(MessageFormat.format("Customer''s Name:{0}", names));
                    txtID.setText(MessageFormat.format("Customer''s ID:{0}", customerID));


                    standingOrders = dbHelper.getAllStandingOrdersForCustomer(customerID);
                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(AdminCusActionView.this, LinearLayoutManager.HORIZONTAL, false);
                    recyclerStandingOrder.setLayoutManager(layoutManager);
                    standingOrderAdapter = new StandingOrderAdapter(AdminCusActionView.this, standingOrders);
                    //recyclerStandingOrder.setHasFixedSize(true);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerStandingOrder.getContext(),
                            layoutManager.getOrientation());
                    recyclerStandingOrder.addItemDecoration(dividerItemDecoration);
                    recyclerStandingOrder.setItemAnimator(new DefaultItemAnimator());
                    recyclerStandingOrder.setAdapter(standingOrderAdapter);
                    SnapHelper snapHelper = new PagerSnapHelper();
                    snapHelper.attachToRecyclerView(recyclerStandingOrder);

                }


            }
        });
        btnPacks = findViewById(R.id.ic_pc);
        btnPacks.setOnClickListener(this::icpc);
        dbHelper = new DBHelper(this);
        btnPacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(customerBundle !=null) {
                    customer = customerBundle.getParcelable("Customer");
                    cusLoc = customerBundle.getParcelable("PreviousLocation");
                    customerID = customerBundle.getInt(CUSTOMER_ID);
                    cusFirstName = customerBundle.getString(CUSTOMER_FIRST_NAME);
                    cusLastName = customerBundle.getString(CUSTOMER_SURNAME);
                    cusPhoneNo = customerBundle.getString(PROFILE_PHONE);
                    packages = customerBundle.getString(packages);

                    names = customer.getCusFirstName() + "" + customer.getCusSurname();
                    txtName = findViewById(R.id.INameCus);
                    txtID = findViewById(R.id.IDCus);
                    txtName.setText(MessageFormat.format("Customer''s Name:{0}", names));
                    txtID.setText(MessageFormat.format("Customer''s ID:{0}", customerID));


                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(AdminCusActionView.this, LinearLayoutManager.HORIZONTAL, false);

                    recyclerPackages.setLayoutManager(layoutManager);
                    skyLightPackages = dbHelper.getPackagesFromCustomer(customerID);
                    packageAdapter = new SkyLightPackageAdapter(AdminCusActionView.this, skyLightPackages);
                    //recyclerPackages.setHasFixedSize(true);
                    recyclerPackages.setAdapter(packageAdapter);
                    DividerItemDecoration dividerItemDecoration1 = new DividerItemDecoration(recyclerPackages.getContext(),
                            layoutManager.getOrientation());
                    recyclerPackages.addItemDecoration(dividerItemDecoration1);
                    recyclerPackages.setItemAnimator(new DefaultItemAnimator());
                    SnapHelper snapHelper = new PagerSnapHelper();
                    snapHelper.attachToRecyclerView(recyclerPackages);
                }


            }
        });
        btnSavings = findViewById(R.id.ic_sa);
        btnSavings.setOnClickListener(this::icsa);
        btnSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(customerBundle !=null) {
                    customer = customerBundle.getParcelable("Customer");
                    cusLoc = customerBundle.getParcelable("PreviousLocation");
                    customerID = customerBundle.getInt(CUSTOMER_ID);
                    cusFirstName = customerBundle.getString(CUSTOMER_FIRST_NAME);
                    cusLastName = customerBundle.getString(CUSTOMER_SURNAME);
                    cusPhoneNo = customerBundle.getString(PROFILE_PHONE);
                    savings = customerBundle.getString(savings);

                    names = customer.getCusFirstName() + "" + customer.getCusSurname();
                    txtName = findViewById(R.id.INameCus);
                    txtID = findViewById(R.id.IDCus);
                    txtName.setText(MessageFormat.format("Customer''s Name:{0}", names));
                    txtID.setText(MessageFormat.format("Customer''s ID:{0}", customerID));

                    LinearLayoutManager layoutManager5
                            = new LinearLayoutManager(AdminCusActionView.this, LinearLayoutManager.HORIZONTAL, false);
                    recyclerSavings.setLayoutManager(layoutManager5);

                    recyclerSavings.setItemAnimator(new DefaultItemAnimator());
                    savingsArrayList = dbHelper.getSavingsFromCurrentCustomer(customerID);
                    savingsAdapter = new SuperSavingsAdapter(AdminCusActionView.this, savingsArrayList);
                    recyclerSavings.setAdapter(savingsAdapter);
                    SnapHelper snapHelper = new PagerSnapHelper();
                    snapHelper.attachToRecyclerView(recyclerSavings);
                }

            }
        });
        btnCode = findViewById(R.id.ic_code);
        btnCode.setOnClickListener(this::iccode);
        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(customerBundle !=null) {
                    customer = customerBundle.getParcelable("Customer");
                    cusLoc = customerBundle.getParcelable("PreviousLocation");
                    customerID = customerBundle.getInt(CUSTOMER_ID);
                    cusFirstName = customerBundle.getString(CUSTOMER_FIRST_NAME);
                    cusLastName = customerBundle.getString(CUSTOMER_SURNAME);
                    cusPhoneNo = customerBundle.getString(PROFILE_PHONE);
                    savingsCode = customerBundle.getString(savingsCode);

                    names = customer.getCusFirstName() + "" + customer.getCusSurname();
                    txtName = findViewById(R.id.INameCus);
                    txtID = findViewById(R.id.IDCus);
                    txtName.setText(MessageFormat.format("Customer''s Name:{0}", names));
                    txtID.setText(MessageFormat.format("Customer''s ID:{0}", customerID));


                    dbHelper = new DBHelper(AdminCusActionView.this);


                    LinearLayoutManager layoutManager2
                            = new LinearLayoutManager(AdminCusActionView.this, LinearLayoutManager.HORIZONTAL, false);
                    paymentCodeArrayList = dbHelper.getCodesFromCurrentCustomer(customerID);
                    recyclerCodes.setLayoutManager(layoutManager2);
                    codeAdapter = new MySavingsCodeAdapter(AdminCusActionView.this, paymentCodeArrayList);
                    //recyclerCodes.setHasFixedSize(true);
                    DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(recyclerCodes.getContext(),
                            layoutManager2.getOrientation());
                    recyclerCodes.addItemDecoration(dividerItemDecoration2);
                    recyclerCodes.setItemAnimator(new DefaultItemAnimator());
                    recyclerCodes.setAdapter(codeAdapter);
                    SnapHelper snapHelper = new PagerSnapHelper();
                    snapHelper.attachToRecyclerView(recyclerCodes);



                }

            }
        });
        btnTx = findViewById(R.id.ic_tx);
        btnTx.setOnClickListener(this::ictx);
        btnTx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(customerBundle !=null) {
                    customer = customerBundle.getParcelable("Customer");
                    cusLoc = customerBundle.getParcelable("PreviousLocation");
                    customerID = customerBundle.getInt(CUSTOMER_ID);
                    cusFirstName = customerBundle.getString(CUSTOMER_FIRST_NAME);
                    cusLastName = customerBundle.getString(CUSTOMER_SURNAME);
                    cusPhoneNo = customerBundle.getString(PROFILE_PHONE);
                    transactions = customerBundle.getString(transactions);
                    names = customer.getCusFirstName() + "" + customer.getCusSurname();
                    txtName = findViewById(R.id.INameCus);
                    txtID = findViewById(R.id.IDCus);
                    txtName.setText(MessageFormat.format("Customer''s Name:{0}", names));
                    txtID.setText(MessageFormat.format("Customer''s ID:{0}", customerID));

                    LinearLayoutManager layoutManager3
                            = new LinearLayoutManager(AdminCusActionView.this, LinearLayoutManager.HORIZONTAL, false);

                    rcyclerTransactions.setLayoutManager(layoutManager3);
                    transactions2 = dbHelper.getAllTransactionCustomer(customerID);
                    transactionAdapter = new TransactionAdapter(AdminCusActionView.this, transactions2);
                    //rcyclerTransactions.setHasFixedSize(true);
                    rcyclerTransactions.setAdapter(transactionAdapter);
                    rcyclerTransactions.setItemAnimator(new DefaultItemAnimator());
                    SnapHelper snapHelper = new PagerSnapHelper();
                    snapHelper.attachToRecyclerView(rcyclerTransactions);

                }

            }
        });
        btnMessage = findViewById(R.id.ic_message);
        btnMessage.setOnClickListener(this::icmessage);
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnDoc = findViewById(R.id.ic_doc);
        btnDoc.setOnClickListener(this::icdoc);
        btnDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(customerBundle !=null) {
                    customer = customerBundle.getParcelable("Customer");
                    cusLoc = customerBundle.getParcelable("PreviousLocation");
                    customerID = customerBundle.getInt(CUSTOMER_ID);
                    cusFirstName = customerBundle.getString(CUSTOMER_FIRST_NAME);
                    cusLastName = customerBundle.getString(CUSTOMER_SURNAME);
                    cusPhoneNo = customerBundle.getString(PROFILE_PHONE);

                    doc = customerBundle.getString(doc);

                    names = customer.getCusFirstName() + "" + customer.getCusSurname();
                    txtName = findViewById(R.id.INameCus);
                    txtID = findViewById(R.id.IDCus);
                    txtName.setText(MessageFormat.format("Customer''s Name:{0}", names));
                    txtID.setText(MessageFormat.format("Customer''s ID:{0}", customerID));


                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(AdminCusActionView.this, LinearLayoutManager.HORIZONTAL, false);

                    rcyclerDocs.setLayoutManager(layoutManager);
                    paymentDocs = dbHelper.getDocumentsFromCurrentCustomer(customerID);
                    rcyclerDocs.setItemAnimator(new DefaultItemAnimator());
                    DividerItemDecoration dividerItemDecorationD = new DividerItemDecoration(rcyclerDocs.getContext(),
                            layoutManager.getOrientation());
                    rcyclerDocs.addItemDecoration(dividerItemDecorationD);
                    documentAdapter = new DocumentAdapter(AdminCusActionView.this, paymentDocs);
                    //rcyclerDocs.setHasFixedSize(true);
                    rcyclerDocs.setAdapter(documentAdapter);
                    SnapHelper snapHelper = new PagerSnapHelper();
                    snapHelper.attachToRecyclerView(rcyclerDocs);

                }

            }
        });

        btnLoans = findViewById(R.id.ic_loan);
        btnLoans.setOnClickListener(this::icloan);
        btnLoans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(customerBundle !=null) {
                    customer = customerBundle.getParcelable("Customer");
                    cusLoc = customerBundle.getParcelable("PreviousLocation");
                    customerID = customerBundle.getInt(CUSTOMER_ID);
                    cusFirstName = customerBundle.getString(CUSTOMER_FIRST_NAME);
                    cusLastName = customerBundle.getString(CUSTOMER_SURNAME);
                    cusPhoneNo = customerBundle.getString(PROFILE_PHONE);
                    loans = customerBundle.getString(loans);
                    names = customer.getCusFirstName() + "" + customer.getCusSurname();
                    txtName = findViewById(R.id.INameCus);
                    txtID = findViewById(R.id.IDCus);
                    txtName.setText(MessageFormat.format("Customer''s Name:{0}", names));
                    txtID.setText(MessageFormat.format("Customer''s ID:{0}", customerID));

                    dbHelper = new DBHelper(AdminCusActionView.this);

                    LinearLayoutManager layoutManager4
                            = new LinearLayoutManager(AdminCusActionView.this, LinearLayoutManager.HORIZONTAL, false);

                    recyclerLoans.setLayoutManager(layoutManager4);
                    cusLoans = dbHelper.getAllLoansCustomer(customerID);
                    loanArrayAdapter = new ArrayAdapter<Loan>(AdminCusActionView.this, android.R.layout.simple_spinner_item, cusLoans);
                    //recyclerLoans.setHasFixedSize(true);
                    recyclerLoans.setAdapter(packageAdapter);
                    recyclerLoans.setItemAnimator(new DefaultItemAnimator());
                    SnapHelper snapHelper = new PagerSnapHelper();
                    snapHelper.attachToRecyclerView(recyclerLoans);



                }

            }
        });


        if(customerBundle !=null) {
            customer = customerBundle.getParcelable("Customer");
            cusLoc = customerBundle.getParcelable("PreviousLocation");
            customerID = customerBundle.getInt(CUSTOMER_ID);
            cusFirstName = customerBundle.getString(CUSTOMER_FIRST_NAME);
            cusLastName = customerBundle.getString(CUSTOMER_SURNAME);
            cusPhoneNo = customerBundle.getString(PROFILE_PHONE);
            names = customer.getCusFirstName() + "" + customer.getCusSurname();
            txtName = findViewById(R.id.INameCus);
            txtID = findViewById(R.id.IDCus);
            txtName.setText(MessageFormat.format("Customer''s Name:{0}", names));
            txtID.setText(MessageFormat.format("Customer''s ID:{0}", customerID));

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("PreviousLocation",cusLoc);
                    Intent userIntent = new Intent(AdminCusActionView.this, TrackWorkersAct.class);
                    userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    userIntent.putExtras(bundle);

                }
            });


        }
        btnLoans = findViewById(R.id.ic_loan);
        btnLoans.setOnClickListener(this::icloan);

        if(customerBundle !=null) {
            customer = customerBundle.getParcelable("Customer");
            cusLoc = customerBundle.getParcelable("PreviousLocation");
            customerID = customerBundle.getInt(CUSTOMER_ID);
            cusFirstName = customerBundle.getString(CUSTOMER_FIRST_NAME);
            cusLastName = customerBundle.getString(CUSTOMER_SURNAME);
            cusPhoneNo = customerBundle.getString(PROFILE_PHONE);

            so = customerBundle.getString(so);

            names = customer.getCusFirstName() + "" + customer.getCusSurname();
            txtName = findViewById(R.id.INameCus);
            txtID = findViewById(R.id.IDCus);
            txtName.setText(MessageFormat.format("Customer''s Name:{0}", names));
            txtID.setText(MessageFormat.format("Customer''s ID:{0}", customerID));


            dbHelper = new DBHelper(this);
            standingOrders = dbHelper.getAllStandingOrdersForCustomer(customerID);
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerStandingOrder.setLayoutManager(layoutManager);
            standingOrderAdapter = new StandingOrderAdapter(AdminCusActionView.this, standingOrders);
            //recyclerStandingOrder.setHasFixedSize(true);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerStandingOrder.getContext(),
                    layoutManager.getOrientation());
            recyclerStandingOrder.addItemDecoration(dividerItemDecoration);
            recyclerStandingOrder.setItemAnimator(new DefaultItemAnimator());
            recyclerStandingOrder.setAdapter(standingOrderAdapter);
            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerStandingOrder);

        }

        btnMessage = findViewById(R.id.ic_message);
        btnLoans.setOnClickListener(this::icmessage);

        if(customerBundle !=null) {
            customer = customerBundle.getParcelable("Customer");
            cusLoc = customerBundle.getParcelable("PreviousLocation");
            customerID = customerBundle.getInt(CUSTOMER_ID);
            cusFirstName = customerBundle.getString(CUSTOMER_FIRST_NAME);
            cusLastName = customerBundle.getString(CUSTOMER_SURNAME);
            cusPhoneNo = customerBundle.getString(PROFILE_PHONE);
            messages = customerBundle.getString(messages);

            names = customer.getCusFirstName() + "" + customer.getCusSurname();
            txtName = findViewById(R.id.INameCus);
            txtID = findViewById(R.id.IDCus);
            txtName.setText(MessageFormat.format("Customer''s Name:{0}", names));
            txtID.setText(MessageFormat.format("Customer''s ID:{0}", customerID));


            dbHelper = new DBHelper(this);

            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

            recyclerMessages.setLayoutManager(layoutManager);
            messageArrayList = dbHelper.getMessagesForCurrentCustomer(customerID);
            recyclerMessages.setItemAnimator(new DefaultItemAnimator());
            DividerItemDecoration dividerItemDecorationM = new DividerItemDecoration(recyclerMessages.getContext(),
                    layoutManager.getOrientation());
            recyclerMessages.addItemDecoration(dividerItemDecorationM);
            messageAdapter = new MessageAdapter(AdminCusActionView.this, messageArrayList);
            //recyclerMessages.setHasFixedSize(true);
            recyclerMessages.setAdapter(messageAdapter);
            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerMessages);
        }




    }

    public void icpc(View view) {
    }

    public void icsa(View view) {
    }

    public void iccode(View view) {
    }

    public void ictx(View view) {
    }

    public void icdoc(View view) {
    }

    public void icso(View view) {
    }

    public void icloan(View view) {
    }

    public void icmessage(View view) {
    }
}