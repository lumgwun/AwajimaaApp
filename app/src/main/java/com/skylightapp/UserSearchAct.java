package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Customers.TxAdapterCus;
import com.skylightapp.Database.DBHelper;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class UserSearchAct extends AppCompatActivity {
    private TextView txtAccountName;
    private TextView txtAccountBalance;

    private TextView txtAdminMsg, txtTellersMsg, txtCustomerMsg,txtSOMsg,txtSOAccountBalance;
    private TextView txtTransfersMsg;
    private TextView txtPaymentsMsg;
    private TextView txtDepositMsg;

    private Spinner spnUsers;
    private Spinner spnTransactionTypeFilter;
    private Spinner spnDateFilter;
    private Customer customer;
    SharedPreferences userPreferences;

    private UserTypeFilter transFilter;
    private FirstNameFilter firstNameFilter;
    ArrayList<Profile> profiles;
    private DBHelper dbHelper;
    ArrayAdapter<Customer> customerArrayAdapter;
    ArrayAdapter<CustomerManager> customerManagerArrayAdapter;
    public enum UserTypeFilter {
        ALL_USERS(0),
        ADMINS(1),
        CUSTOMERS(2),
        TELLERS(3);

        private final int userFilterID;
        UserTypeFilter(int userFilterID) {
            this.userFilterID = userFilterID;
        }

        public UserTypeFilter getUserFilter(int index) {
            for (UserTypeFilter filter : UserTypeFilter.values()) {
                if (filter.userFilterID == index) {
                    return filter;
                }
            }
            return null;
        }
    }

    public enum FirstNameFilter {
        OLDEST_NEWEST(0),
        NEWEST_OLDEST(1);

        private final int firstNameFilterID;
        FirstNameFilter(int firstNameFilterID) {
            this.firstNameFilterID = firstNameFilterID;
        }

        public FirstNameFilter getDateFilter(int index) {
            for (FirstNameFilter filter : FirstNameFilter.values()) {
                if (filter.firstNameFilterID == index) {
                    return filter;
                }
            }
            return null;
        }

    }

    class UserComparator implements Comparator<Profile> {
        public int compare(Profile profileOne, Profile profileTwo) {

            String firstNameOne = null;
            String firstNameTwo = null;

            try {
                firstNameOne = profileOne.getProfileFirstName();
                firstNameTwo = profileTwo.getProfileFirstName();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (firstNameOne != null) {
                if (firstNameOne.compareTo(firstNameTwo) > 0) {
                    return (1);
                } else if (firstNameOne.compareTo(firstNameTwo) < 0) {
                    return (-1);
                } else if (firstNameOne.compareTo(firstNameTwo) == 0) {
                    return (1);
                }
            }
            return (1);
        }
    }



    Spinner.OnItemSelectedListener spnClickListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            dbHelper= new DBHelper(UserSearchAct.this);
            userProfile =(dbHelper.getAllProfiles().get(selectedProfileIndex));
            if (adapterView.getId() == spnUsers.getId()) {
                selectedProfileIndex = i;
                txtAccountName.setText(MessageFormat.format("Account: {0}", userProfile.getProfileAccount().getAccountName()));
                txtAccountBalance.setText(MessageFormat.format("Balance: NGN{0}", String.format(Locale.getDefault(), "%.2f", userProfile.getProfileAccount().getAccountBalance())));
                txtSOAccountBalance.setText(MessageFormat.format("SO Balance: NGN{0}", String.format(Locale.getDefault(), "%.2f", userProfile.getProfileSOAcct().getSoAcctBalance())));
            }
            else if (adapterView.getId() == spnTransactionTypeFilter.getId()) {
                transFilter = transFilter.getUserFilter(i);
            }
            else if (adapterView.getId() == spnDateFilter.getId()) {
                firstNameFilter = firstNameFilter.getDateFilter(i);
            }

            setupUsersAdapter(selectedProfileIndex, transFilter, firstNameFilter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private ListView lstTransactions;

    private Profile userProfile;

    private int selectedProfileIndex;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_search);
        dbHelper= new DBHelper(this);
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        userProfile= new Profile();
        customer=new Customer();
        String json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        txtAccountName = findViewById(R.id.txt_account_name4);
        txtAccountBalance = findViewById(R.id.txt_account_balance44);
        txtSOAccountBalance = findViewById(R.id.txt_SOAccount_b);

        txtAdminMsg = findViewById(R.id.txt_no_transactions33);
        txtTellersMsg = findViewById(R.id.txt_no_Savings);
        txtCustomerMsg = findViewById(R.id.txt_no_Loans);
        spnUsers = findViewById(R.id.spn_Users4);
        spnTransactionTypeFilter = findViewById(R.id.spn_type_filter3);
        spnDateFilter = findViewById(R.id.spn_date_filter4);
        lstTransactions = findViewById(R.id.lst_transactions44);
    }

    private void setupUsersAdapter(int selectedUserIndex, UserTypeFilter userTypeFilter, FirstNameFilter firstNameFilter) {

        /*transactions=new ArrayList<Transaction>();
        try {
            transactions = userProfile.getAccounts().get(selectedUserIndex).getTransactions();

        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }
        txtDepositMsg.setVisibility(GONE);
        txtTransfersMsg.setVisibility(GONE);
        txtPaymentsMsg.setVisibility(GONE);

        if (transactions.size() > 0) {

            txtAdminMsg.setVisibility(GONE);
            lstTransactions.setVisibility(VISIBLE);

            if (firstNameFilter == UserTxAct.DateFilter.OLDEST_NEWEST) {
                Collections.sort(transactions, new UserTxAct.TransactionComparator());
            } else if (firstNameFilter == UserTxAct.DateFilter.NEWEST_OLDEST) {
                Collections.sort(transactions, Collections.reverseOrder(new UserTxAct.TransactionComparator()));
            }

            if (transFilter == UserTxAct.TransactionTypeFilter.ALL_TRANSACTIONS) {
                TxAdapterCus transactionAdapter = new TxAdapterCus(this, R.layout.lst_trans, transactions);
                lstTransactions.setAdapter(transactionAdapter);
            }
            else if (transFilter == UserTxAct.TransactionTypeFilter.PAYMENTS) {
                displayPayments(transactions);
            }
            else if (transFilter == UserTxAct.TransactionTypeFilter.TRANSFERS) {
                displayTransfers(transactions);
            }
            else if (transFilter == UserTxAct.TransactionTypeFilter.DEPOSITS) {
                displayDeposits(transactions);
            }
            else if (transFilter == UserTxAct.TransactionTypeFilter.SAVINGS) {
                displaySavings(transactions);
            }
            else if (transFilter == UserTxAct.TransactionTypeFilter.LOANS) {
                displayLoans(transactions);
            }
            else if (transFilter == UserTxAct.TransactionTypeFilter.STANDING_ORDER) {
                displayStandingOrders(transactions);
            }

        } else {
            txtAdminMsg.setVisibility(VISIBLE);
            lstTransactions.setVisibility(GONE);
        }*/

    }

    private void displayPayments(ArrayList<Transaction> transactions) {
        ArrayList<Transaction> payments = new ArrayList<>();

        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getTransactionType() == Transaction.TRANSACTION_TYPE.PAYMENT) {
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
            if (transactions.get(i).getTransactionType() == Transaction.TRANSACTION_TYPE.TRANSFER) {
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
            if (transactions.get(i).getTransactionType() == Transaction.TRANSACTION_TYPE.SAVINGS) {
                savings.add(transactions.get(i));
            }
        }
        if (savings.size() == 0) {
            txtTellersMsg.setVisibility(VISIBLE);
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
            if (transactions.get(i).getTransactionType() == Transaction.TRANSACTION_TYPE.LOAN) {
                loans.add(transactions.get(i));
            }
        }
        if (loans.size() == 0) {
            txtCustomerMsg.setVisibility(VISIBLE);
            lstTransactions.setVisibility(GONE);
        } else {
            lstTransactions.setVisibility(VISIBLE);
            TxAdapterCus transactionAdapter = new TxAdapterCus(this, R.layout.lst_trans, loans);
            lstTransactions.setAdapter(transactionAdapter);
        }
    }
}