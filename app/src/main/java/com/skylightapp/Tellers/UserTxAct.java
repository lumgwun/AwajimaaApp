package com.skylightapp.Tellers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Adapters.NewCusTranxAdapter;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Customers.TxAdapterCus;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class UserTxAct extends AppCompatActivity{
    private TextView txtCustomerName;
    private TextView txtAccountBalance;

    private TextView txtTransactionMsg,txtSavingsMsg,txtLoansMsg,txtSOMsg;
    private TextView txtTransfersMsg;
    private TextView txtPaymentsMsg;
    private TextView txtDepositMsg;

    private Spinner spnCustomers;
    private Spinner spnTransactionTypeFilter;
    private Account account;
    SharedPreferences userPreferences;

    private TransactionTypeFilter transFilter;
    private DateFilter dateFilter;
    ArrayList<Transaction> transactions;
    String customerName,selectedTranxType;
    double acctBalance;
    AppCompatButton btnSearch;
    DBHelper dbHelper;
    ArrayAdapter<Account> accountArrayAdapter;


    public enum TransactionTypeFilter {
        ALL_TRANSACTIONS(0),
        PAYMENTS(1),
        TRANSFERS(2),
        DEPOSITS(3),
        SAVINGS(4),
        LOANS(5),
        STANDING_ORDER(6);

        private final int transFilterID;
        TransactionTypeFilter(int transFilterID) {
            this.transFilterID = transFilterID;
        }

        public TransactionTypeFilter getTransFilter(int index) {
            for (TransactionTypeFilter filter : TransactionTypeFilter.values()) {
                if (filter.transFilterID == index) {
                    return filter;
                }
            }
            return null;
        }
    }

    public enum DateFilter {
        OLDEST_NEWEST(0),
        NEWEST_OLDEST(1);

        private final int dateFilterID;
        DateFilter(int dateFilterID) {
            this.dateFilterID = dateFilterID;
        }

        public DateFilter getDateFilter(int index) {
            for (DateFilter filter : DateFilter.values()) {
                if (filter.dateFilterID == index) {
                    return filter;
                }
            }
            return null;
        }

    }

    class TransactionComparator implements Comparator<Transaction> {
        public int compare(Transaction transOne, Transaction transTwo) {

            Date dateOne = null;
            Date dateTwo = null;

            try {
                dateOne = Transaction.DATE_FORMAT.parse(transOne.getTranxDate());
                dateTwo = Transaction.DATE_FORMAT.parse(transTwo.getTranxDate());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (dateOne.compareTo(dateTwo) > 0) {
                return (1);
            } else if (dateOne.compareTo(dateTwo) < 0) {
                return (-1);
            } else if (dateOne.compareTo(dateTwo) == 0) {
                return (1);
            }
            return (1);
        }
    }



    Spinner.OnItemSelectedListener spnClickListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Customer customer =(userProfile.getProfileCustomers().get(selectedCustomerIndex));
            if(customer !=null){
                customerName=customer.getCusSurname()+","+customer.getCusFirstName();
                account=customer.getCusAccount();
            }
            if(account !=null){
                acctBalance=account.getAccountBalance();
                if(acctBalance==0){
                    txtAccountBalance.setText(MessageFormat.format("Wallet Balance: NGN{0}", String.format(Locale.getDefault(), "%.2f", 0.00)));


                }
            }
            if (adapterView.getId() == spnCustomers.getId()) {
                selectedCustomerIndex = i;
                txtCustomerName.setText(MessageFormat.format("Customer: {0}", customerName));
                txtAccountBalance.setText(MessageFormat.format("Wallet Balance: NGN{0}", String.format(Locale.getDefault(), "%.2f", acctBalance)));
            }
            else if (adapterView.getId() == spnTransactionTypeFilter.getId()) {
                //transFilter = transFilter.getTransFilter(i);
                spnTransactionTypeFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedTranxType = spnTransactionTypeFilter.getSelectedItem().toString();
                        selectedTranxType = (String) parent.getSelectedItem();
                        Toast.makeText(UserTxAct.this, "Gender: "+ selectedTranxType,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }

            //setupTransactionAdapter(selectedCustomerIndex, transFilter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private ListView lstTransactions;

    private Profile userProfile;

    private int selectedCustomerIndex;
    int customerID;

    private ArrayAdapter<Customer> customerArrayAdapter;
    private Customer customer;
    private ArrayList<Customer> customers;
    private NewCusTranxAdapter transactionAdapter;
    RecyclerView recyclerView;
    DatePicker picker;
    protected DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormat;
    Calendar calendar;
    String dateOfTransaction;
    int customerTranxCount;
    double transactionTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_tx);
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        userProfile= new Profile();
        account=new Account();
        customer= new Customer();
        customers=new ArrayList<Customer>();
        transactions=new ArrayList<Transaction>();
        dbHelper= new DBHelper(this);
        String json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        txtCustomerName = findViewById(R.id.txt_account_name4);
        txtAccountBalance = findViewById(R.id.txt_account_balance44);
        picker = findViewById(R.id.TX_date_TX);
        spnCustomers = findViewById(R.id.spn_TellerCustomers44);
        spnTransactionTypeFilter = findViewById(R.id.spn_type_filter3);

        txtTransactionMsg = findViewById(R.id.txt_no_transactions33);
        txtPaymentsMsg = findViewById(R.id.txt_no_payments33);
        txtSOMsg = findViewById(R.id.txt_no_SO);
        txtSavingsMsg = findViewById(R.id.txt_no_Savings);
        txtLoansMsg = findViewById(R.id.txt_no_Loans);
        recyclerView = findViewById(R.id.recyclerViewTXUser);
        txtTransfersMsg = findViewById(R.id.txt_no_transfers44);
        txtDepositMsg = findViewById(R.id.txt_no_deposits44);
        btnSearch = findViewById(R.id.buttonCusDB);
        btnSearch.setOnClickListener(this::getDBSearch);
        spnCustomers.setOnItemSelectedListener(spnClickListener);
        spnTransactionTypeFilter.setOnItemSelectedListener(spnClickListener);


        lstTransactions = findViewById(R.id.lst_transactions44);
        transFilter = TransactionTypeFilter.ALL_TRANSACTIONS;
        dateFilter = DateFilter.OLDEST_NEWEST;
        selectedCustomerIndex = spnCustomers.getSelectedItemPosition();
        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
        dateOfTransaction = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();
        SnapHelper snapHelper = new PagerSnapHelper();

        if (userProfile != null) {
            customers = userProfile.getProfileCustomers();
            customer =(userProfile.getProfileCustomers().get(selectedCustomerIndex));
            customerArrayAdapter = new ArrayAdapter<>(UserTxAct.this, android.R.layout.simple_spinner_item, customers);
            customerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnCustomers.setAdapter(customerArrayAdapter);
            spnCustomers.setSelection(customerArrayAdapter.getPosition(customer));

            spnCustomers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    customer = (Customer) parent.getSelectedItem();
                    Toast.makeText(UserTxAct.this, "Customer's ID: "+customer.getCusUID()+",  Customer's Name : "+customer.getCusFirstName(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


        }
        spnCustomers.setSelection(selectedCustomerIndex);
        //setupTransactionAdapter(selectedCustomerIndex, transFilter);
        spnTransactionTypeFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTranxType = spnTransactionTypeFilter.getSelectedItem().toString();
                selectedTranxType = (String) parent.getSelectedItem();
                Toast.makeText(UserTxAct.this, "Type: "+ selectedTranxType,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if(customer !=null){
            customerName=customer.getCusSurname()+","+customer.getCusFirstName();
            account=customer.getCusAccount();
            customerID=customer.getCusUID();
        }
        if(account !=null){
            acctBalance=account.getAccountBalance();
            if(acctBalance==0){
                txtAccountBalance.setText(MessageFormat.format("Wallet Balance: NGN{0}", String.format(Locale.getDefault(), "%.2f", 0.00)));


            }
        }
        txtCustomerName.setText(MessageFormat.format("Customer: {0}", customerName));
        txtAccountBalance.setText(MessageFormat.format("Wallet Balance: NGN{0}", String.format(Locale.getDefault(), "%.2f", acctBalance)));
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTranxType !=null){
                    transactions=dbHelper.getAllTranxWithTypeForCustomer(customerID,selectedTranxType);

                }else {
                    transactions=dbHelper.getAllTransactionCustomer(customerID);

                }

            }
        });
        LinearLayoutManager layoutManagerC
                = new LinearLayoutManager(UserTxAct.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManagerC);
        //recyclerView.setHasFixedSize(true);
        try {
            transactions = dbHelper.getAllTransactionCustomer(customerID);
            customerTranxCount =dbHelper.getTransactionCountForCustomer(customerID,dateOfTransaction);
            transactionTotal=dbHelper.getTotalTransactionForCustomer(customerID,dateOfTransaction);

        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        transactionAdapter = new NewCusTranxAdapter(UserTxAct.this,R.layout.lst_trans,transactions);
        recyclerView.setAdapter(transactionAdapter);
        DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(recyclerView.getContext(),
                layoutManagerC.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration7);


        if(transactionTotal >0){
            txtTransactionMsg.setVisibility(VISIBLE);
            txtTransactionMsg.setText(MessageFormat.format("TranX Total:N{0}", transactionTotal));

        }else if(transactionTotal ==0){
            txtTransactionMsg.setVisibility(VISIBLE);
            txtTransactionMsg.setText("TranX Total:N0");

        }

    }
    private void chooseDate() {
        dateOfTransaction = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();

    }

    public void getDBSearch(View view) {
        transactions=dbHelper.getAllTransactionCustomer(customerID);
    }

    private void setupTransactionAdapter(int selectedCustomerIndex, TransactionTypeFilter transFilter) {

        transactions=new ArrayList<Transaction>();
        try {
            transactions = userProfile.getProfileAccounts().get(selectedCustomerIndex).getTransactions();

        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }
        txtDepositMsg.setVisibility(GONE);
        txtTransfersMsg.setVisibility(GONE);
        txtPaymentsMsg.setVisibility(GONE);

        if (transactions.size() > 0) {

            txtTransactionMsg.setVisibility(GONE);
            lstTransactions.setVisibility(VISIBLE);

            if (dateFilter == DateFilter.OLDEST_NEWEST) {
                Collections.sort(transactions, new TransactionComparator());
            } else if (dateFilter == DateFilter.NEWEST_OLDEST) {
                Collections.sort(transactions, Collections.reverseOrder(new TransactionComparator()));
            }

            if (transFilter == TransactionTypeFilter.ALL_TRANSACTIONS) {
                TxAdapterCus transactionAdapter = new TxAdapterCus(this, R.layout.lst_trans, transactions);
                lstTransactions.setAdapter(transactionAdapter);
            }
            else if (transFilter == TransactionTypeFilter.PAYMENTS) {
                displayPayments(transactions);
            }
            else if (transFilter == TransactionTypeFilter.TRANSFERS) {
                displayTransfers(transactions);
            }
            else if (transFilter == TransactionTypeFilter.DEPOSITS) {
                displayDeposits(transactions);
            }
            else if (transFilter == TransactionTypeFilter.SAVINGS) {
                displaySavings(transactions);
            }
            else if (transFilter == TransactionTypeFilter.LOANS) {
                displayLoans(transactions);
            }
            else if (transFilter == TransactionTypeFilter.STANDING_ORDER) {
                displayStandingOrders(transactions);
            }

        } else {
            txtTransactionMsg.setVisibility(VISIBLE);
            lstTransactions.setVisibility(GONE);
        }

    }

    private void displayPayments(ArrayList<Transaction> transactions) {
        ArrayList<Transaction> payments = new ArrayList<>();

        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getTranXType() == Transaction.TRANSACTION_TYPE.PAYMENT) {
                payments.add(transactions.get(i));
            }
        }
        if (payments.size() == 0) {
            txtPaymentsMsg.setVisibility(VISIBLE);
            lstTransactions.setVisibility(GONE);
        } else {
            lstTransactions.setVisibility(VISIBLE);
            TxAdapterCus transactionAdapter = new TxAdapterCus(this, R.layout.lst_trans, payments);
            lstTransactions.setAdapter(transactionAdapter);
        }
    }

    private void displayTransfers(ArrayList<Transaction> transactions) {
        ArrayList<Transaction> transfers = new ArrayList<>();

        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getTranXType() == Transaction.TRANSACTION_TYPE.TRANSFER) {
                transfers.add(transactions.get(i));
            }
        }
        if (transfers.size() == 0) {
            txtTransfersMsg.setVisibility(VISIBLE);
            lstTransactions.setVisibility(GONE);
        } else {
            lstTransactions.setVisibility(VISIBLE);
            TxAdapterCus transactionAdapter = new TxAdapterCus(this, R.layout.lst_trans, transfers);
            lstTransactions.setAdapter(transactionAdapter);
        }
    }

    private void displaySavings(ArrayList<Transaction> transactions) {
        ArrayList<Transaction> savings = new ArrayList<>();

        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getTranXType() == Transaction.TRANSACTION_TYPE.SAVINGS) {
                savings.add(transactions.get(i));
            }
        }
        if (savings.size() == 0) {
            txtSavingsMsg.setVisibility(VISIBLE);
            lstTransactions.setVisibility(GONE);
        } else {
            lstTransactions.setVisibility(VISIBLE);
            TxAdapterCus transactionAdapter = new TxAdapterCus(this, R.layout.lst_trans, savings);
            lstTransactions.setAdapter(transactionAdapter);
        }
    }
    private void displayLoans(ArrayList<Transaction> transactions) {
        ArrayList<Transaction> loans = new ArrayList<>();

        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getTranXType() == Transaction.TRANSACTION_TYPE.LOAN) {
                loans.add(transactions.get(i));
            }
        }
        if (loans.size() == 0) {
            txtLoansMsg.setVisibility(VISIBLE);
            lstTransactions.setVisibility(GONE);
        } else {
            lstTransactions.setVisibility(VISIBLE);
            TxAdapterCus transactionAdapter = new TxAdapterCus(this, R.layout.lst_trans, loans);
            lstTransactions.setAdapter(transactionAdapter);
        }
    }
    private void displayDeposits(ArrayList<Transaction> transactions) {
        ArrayList<Transaction> deposit = new ArrayList<>();

        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getTranXType() == Transaction.TRANSACTION_TYPE.DEPOSIT) {
                deposit.add(transactions.get(i));
            }
        }
        if (deposit.size() == 0) {
            txtDepositMsg.setVisibility(VISIBLE);
            lstTransactions.setVisibility(GONE);
        } else {
            lstTransactions.setVisibility(VISIBLE);
            TxAdapterCus transactionAdapter = new TxAdapterCus(this, R.layout.lst_trans, deposit);
            lstTransactions.setAdapter(transactionAdapter);
        }
    }



    private void displayStandingOrders(ArrayList<Transaction> transactions) {
        ArrayList<Transaction> standingOrders = new ArrayList<>();

        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getTranXType() == Transaction.TRANSACTION_TYPE.STANDING_ORDER) {
                standingOrders.add(transactions.get(i));
            }
        }
        if (standingOrders.size() == 0) {
            txtSOMsg.setVisibility(VISIBLE);
            lstTransactions.setVisibility(GONE);
        } else {
            lstTransactions.setVisibility(VISIBLE);
            TxAdapterCus transactionAdapter = new TxAdapterCus(this, R.layout.lst_trans, standingOrders);
            lstTransactions.setAdapter(transactionAdapter);
        }
    }
}