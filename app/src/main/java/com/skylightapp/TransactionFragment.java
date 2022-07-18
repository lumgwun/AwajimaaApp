package com.skylightapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Customers.TxAdapterCus;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class TransactionFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Account account;
    public enum TransactionTypeFilter {
        ALL_TRANSACTIONS(0),
        PAYMENTS(1),
        TRANSFERS(2),
        DEPOSITS(3);

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

    private TextView txtAccountName;
    private TextView txtAccountBalance;

    private TextView txtTransactionMsg;
    private TextView txtTransfersMsg;
    private TextView txtPaymentsMsg;
    private TextView txtDepositMsg;

    private Spinner spnAccounts;
    private Spinner spnTransactionTypeFilter;
    private Spinner spnDateFilter;

    private TransactionTypeFilter transFilter;
    private DateFilter dateFilter;
    ArrayList<Transaction> transactions;
    ArrayAdapter<Account> accountArrayAdapter;

    Spinner.OnItemSelectedListener spnClickListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            double accountBalance =(userProfile.getProfileAccounts().get(selectedAccountIndex).getAccountBalance());
            if (adapterView.getId() == spnAccounts.getId()) {
                selectedAccountIndex = i;
                txtAccountName.setText(MessageFormat.format("Account: {0}", userProfile.getProfileAccounts().get(selectedAccountIndex)));
                txtAccountBalance.setText(MessageFormat.format("Balance: NGN{0}", String.format(Locale.getDefault(), "%.2f", accountBalance)));
            }
            else if (adapterView.getId() == spnTransactionTypeFilter.getId()) {
                transFilter = transFilter.getTransFilter(i);
            }
            else if (adapterView.getId() == spnDateFilter.getId()) {
                dateFilter = dateFilter.getDateFilter(i);
            }

            setupTransactionAdapter(selectedAccountIndex, transFilter, dateFilter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private ListView lstTransactions;

    private Profile userProfile;

    private int selectedAccountIndex;


    public TransactionFragment() {
        // Required empty public constructor
    }


    public static TransactionFragment newInstance(String param1, String param2) {
        TransactionFragment fragment = new TransactionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        getActivity().setTitle("Transactions");
        selectedAccountIndex = bundle.getInt("SelectedAccount", 0);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_trans2, container, false);

        txtAccountName = rootView.findViewById(R.id.txt_account_name);
        txtAccountBalance = rootView.findViewById(R.id.txt_account_balance4);

        txtTransactionMsg = rootView.findViewById(R.id.txt_no_transactions3);
        txtPaymentsMsg = rootView.findViewById(R.id.txt_no_payments3);
        txtTransfersMsg = rootView.findViewById(R.id.txt_no_transfers4);
        txtDepositMsg = rootView.findViewById(R.id.txt_no_deposits4);

        spnAccounts = rootView.findViewById(R.id.spn_accounts3);
        spnTransactionTypeFilter = rootView.findViewById(R.id.spn_type_filter);
        spnDateFilter = rootView.findViewById(R.id.spn_date_filter);

        lstTransactions = rootView.findViewById(R.id.lst_transactions4);

        //((DrawerActivity) getActivity()).showUpButton();

        setValues();
        return rootView;
    }

    private void setValues() {

        SharedPreferences userPreferences = getActivity().getSharedPreferences("LastProfileUsed", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        userProfile= new Profile();
        account=new Account();
        String json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        transFilter = TransactionTypeFilter.ALL_TRANSACTIONS;
        dateFilter = DateFilter.OLDEST_NEWEST;

        setupTransactionAdapter(selectedAccountIndex, transFilter, dateFilter);

        setupSpinners();
        spnAccounts.setSelection(selectedAccountIndex);
        account=userProfile.getProfileAccount();

        txtAccountName.setText(MessageFormat.format("Account: {0}", userProfile.getProfileAccounts().get(selectedAccountIndex).toString()));
        txtAccountBalance.setText(MessageFormat.format("Balance: NGN{0}", String.format(Locale.getDefault(), "%.2f", userProfile.getProfileAccounts().get(selectedAccountIndex).getAccountBalance())));
    }

    private void setupSpinners() {
        account=userProfile.getProfileAccount();
        ArrayAdapter<Account> accountAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, userProfile.getProfileAccounts());
        accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAccounts.setAdapter(accountAdapter);

        ArrayAdapter<String> transTypeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.transaction_filters));
        transTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTransactionTypeFilter.setAdapter(transTypeAdapter);

        ArrayAdapter<String> dateFilterAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.date_filters));
        dateFilterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDateFilter.setAdapter(dateFilterAdapter);

        spnAccounts.setOnItemSelectedListener(spnClickListener);
        spnTransactionTypeFilter.setOnItemSelectedListener(spnClickListener);
        spnDateFilter.setOnItemSelectedListener(spnClickListener);

    }

    private void setupTransactionAdapter(int selectedAccountIndex, TransactionTypeFilter transFilter, DateFilter dateFilter) {

        transactions=new ArrayList<Transaction>();
        try {
            transactions = userProfile.getProfileAccounts().get(selectedAccountIndex).getTransactions();

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
                TxAdapterCus transactionAdapter = new TxAdapterCus(getActivity(), R.layout.lst_trans, transactions);
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
            TxAdapterCus transactionAdapter = new TxAdapterCus(getActivity(), R.layout.lst_trans, payments);
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
            TxAdapterCus transactionAdapter = new TxAdapterCus(getActivity(), R.layout.lst_trans, transfers);
            lstTransactions.setAdapter(transactionAdapter);
        }
    }

    private void displayDeposits(ArrayList<Transaction> transactions) {
        ArrayList<Transaction> deposits = new ArrayList<>();

        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getTranXType() == Transaction.TRANSACTION_TYPE.DEPOSIT) {
                deposits.add(transactions.get(i));
            }
        }
        if (deposits.size() == 0) {
            txtDepositMsg.setVisibility(VISIBLE);
            lstTransactions.setVisibility(GONE);
        } else {
            lstTransactions.setVisibility(VISIBLE);
            TxAdapterCus transactionAdapter = new TxAdapterCus(getActivity(), R.layout.lst_trans, deposits);
            lstTransactions.setAdapter(transactionAdapter);
        }
    }
}
