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
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.skylightapp.Adapters.AccountRecylerAdap;
import com.skylightapp.Adapters.DocumentAdapter;
import com.skylightapp.Adapters.MessageAdapter;
import com.skylightapp.Adapters.MySavingsCodeAdapter;
import com.skylightapp.Adapters.SkyLightPackageAdapter;
import com.skylightapp.Adapters.StandingOrderAdapter;
import com.skylightapp.Adapters.SuperSavingsAdapter;
import com.skylightapp.Adapters.TranxAdminA;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Classes.Message;
import com.skylightapp.Classes.PaymentCode;
import com.skylightapp.Classes.PaymentDoc;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.Classes.Transaction;
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
    private TranxAdminA tranxAdminA;
    private AccountRecylerAdap accountAdapter;
    private MySavingsCodeAdapter codeAdapter;
    private  MessageAdapter  messageAdapter;
    private ArrayList<Message> messageArrayList;
    private DocumentAdapter documentAdapter;
    private ArrayList<PaymentDoc> paymentDocs;

    SkyLightPackageAdapter packageAdapter;
    private  SuperSavingsAdapter savingsAdapter;
    DBHelper dbHelper;
    private ArrayList<CustomerDailyReport> savingsArrayList;
    private ArrayList<MarketBizPackage> marketBizPackages;
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
    SQLiteDatabase sqLiteDatabase;
    private static final String PREF_NAME = "awajima";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_admin_cus_action_view);
        setTitle("Admin Customer Actions");
        customerBundle= getIntent().getExtras();
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
        messageDAO= new MessageDAO(this);
        loanDAO= new LoanDAO(this);

        codeDAO= new CodeDAO(this);
        acctDAO= new AcctDAO(this);
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

                    if (dbHelper !=null) {
                        sqLiteDatabase = dbHelper.getReadableDatabase();
                        try {

                            standingOrders = sodao.getAllStandingOrdersForCustomer(customerID);
                        } catch (Exception e) {
                            System.out.println("Oops!");
                        }


                    }



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
                    if (dbHelper !=null) {
                        sqLiteDatabase = dbHelper.getReadableDatabase();
                        try {

                            marketBizPackages = dbHelper.getPackagesFromCustomer(customerID);
                        } catch (Exception e) {
                            System.out.println("Oops!");
                        }


                    }

                    packageAdapter = new SkyLightPackageAdapter(AdminCusActionView.this, marketBizPackages);
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
                    if (dbHelper !=null) {
                        sqLiteDatabase = dbHelper.getReadableDatabase();
                        try {

                            savingsArrayList = dbHelper.getSavingsFromCurrentCustomer(customerID);
                        } catch (Exception e) {
                            System.out.println("Oops!");
                        }


                    }

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

                    if (dbHelper !=null) {
                        sqLiteDatabase = dbHelper.getReadableDatabase();
                        try {

                            paymentCodeArrayList = codeDAO.getCodesFromCurrentCustomer(customerID);
                        } catch (Exception e) {
                            System.out.println("Oops!");
                        }


                    }


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
                    if (dbHelper !=null) {
                        sqLiteDatabase = dbHelper.getReadableDatabase();
                        try {

                            transactions2 = tranXDAO.getAllTransactionCustomer(customerID);
                        } catch (Exception e) {
                            System.out.println("Oops!");
                        }


                    }


                    tranxAdminA = new TranxAdminA(AdminCusActionView.this, transactions2);
                    //rcyclerTransactions.setHasFixedSize(true);
                    rcyclerTransactions.setAdapter(tranxAdminA);
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
                    if (dbHelper !=null) {
                        sqLiteDatabase = dbHelper.getReadableDatabase();
                        try {

                            paymentDocs = paymDocDAO.getDocumentsFromCurrentCustomer(customerID);
                        } catch (Exception e) {
                            System.out.println("Oops!");
                        }


                    }

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

                    if (dbHelper !=null) {
                        sqLiteDatabase = dbHelper.getReadableDatabase();
                        try {

                            cusLoans = loanDAO.getAllLoansCustomer(customerID);
                        } catch (Exception e) {
                            System.out.println("Oops!");
                        }


                    }

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
        dbHelper = new DBHelper(this);
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



            if (dbHelper !=null) {
                sqLiteDatabase = dbHelper.getReadableDatabase();
                try {

                    standingOrders = sodao.getAllStandingOrdersForCustomer(customerID);
                } catch (Exception e) {
                    System.out.println("Oops!");
                }


            }


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

            if (dbHelper !=null) {
                sqLiteDatabase = dbHelper.getReadableDatabase();
                try {

                    messageArrayList = messageDAO.getMessagesForCurrentCustomer(customerID);
                } catch (Exception e) {
                    System.out.println("Oops!");
                }


            }


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