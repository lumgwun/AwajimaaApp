package com.skylightapp.Tellers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.skylightapp.Classes.Account.ACCOUNT_TYPE;

public class CustomerLoanOverview extends Fragment {
    private static final String TAG = CustomerLoanOverview.class.getSimpleName();
    private static final String URL = "https://skylightciacs.com";

    private RecyclerView recyclerView;


    private ArrayList<Loan> loanList;
    private LoanAdapter mAdapter;


    DBHelper dbHelper;
    SharedPreferences userPreferences;
    Gson gson;
    String json;
    Profile userProfile;
    String SharedPrefUserName;
    SharedPreferences sharedPreferences;
    String SharedPrefUserPassword;
    long SharedPrefCusID;
    String SharedPrefUserMachine;
    String SharedPrefState;
    String SharedPrefOffice;
    String SharedPrefAddress;
    String SharedPrefJoinedDate;
    String SharedPrefGender;
    String name;
    String SharedPrefRole;
    String SharedPrefDOB;
    String SharedPrefPhone;
    String SharedPrefEmail;
    int SharedPrefProfileID;
    String SharedPrefSurName;
    String SharedPrefFirstName;
    String SharedPrefAcctNo;
    String customerId;
    String SharedPrefBankNo;
    String SharedPrefAcctBalance;
    String SharedPrefAcctName;
    String SharedPrefType;
    String SharedPrefBank;
    private OnFragmentInteractionListener listener;
            ;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 2;

    public CustomerLoanOverview() {
        // Required empty public constructor
    }

    public static CustomerLoanOverview newInstance(String param1, String param2) {
        CustomerLoanOverview fragment = new CustomerLoanOverview();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_teller_loan, container, false);
        recyclerView = view.findViewById(R.id.recycler_loan_teller);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPrefUserName = sharedPreferences.getString("USER_NAME", "");
        SharedPrefUserPassword = sharedPreferences.getString("USER_PASSWORD", "");
        SharedPrefCusID = sharedPreferences.getLong("CUSTOMER_ID", 0);
        SharedPrefUserMachine = sharedPreferences.getString("machine", "");
        SharedPrefProfileID = sharedPreferences.getInt("PROFILE_ID", 0);
        SharedPrefRole = sharedPreferences.getString("USER_ROLE", "");
        SharedPrefJoinedDate = sharedPreferences.getString("USER_DATE_JOINED", "");
        SharedPrefOffice = sharedPreferences.getString("CHOSEN_OFFICE", "");
        customerId = sharedPreferences.getString("CUSTOMER_ID", "");
        SharedPrefAcctNo = sharedPreferences.getString("ACCOUNT_NO", "");
        SharedPrefBankNo = sharedPreferences.getString("BANK_ACCT_NO", "");
        SharedPrefAcctBalance = sharedPreferences.getString("ACCOUNT_BALANCE", "");
        SharedPrefAcctName = sharedPreferences.getString("ACCOUNT_NAME", "");
        SharedPrefType = sharedPreferences.getString("ACCOUNT_TYPE", "");
        SharedPrefBank = sharedPreferences.getString("ACCOUNT_BANK", "");
        gson = new Gson();

        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        loanList = new ArrayList<Loan>();
        mAdapter = new LoanAdapter(getContext(), loanList);
        dbHelper = new DBHelper(getContext());
        loanList = dbHelper.getLoanFromCurrentProfile(SharedPrefProfileID);
        /*try {




        } catch (SQLiteException e) {
            System.out.println("Oops!");
        }*/


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        return view;

    }
    class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.MyViewHolder> implements View.OnClickListener {
        private final Context context;
        private final List<Loan> loans;

        @Override
        public void onClick(View view) {

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView name,status, joinedDateText, startDate, amount1_of_text,loan_request_amount,phone_of_customer
                    , loanBalance,bank_of_customer, profileName, endDate, customerName,requestDate_of_customer,customer_balance_text;
            public ImageView thumbnail;

            public MyViewHolder(View view) {
                super(view);

                loan_request_amount = view.findViewById(R.id.loan_request_amount1);
                profileName = view.findViewById(R.id.profileName1);
                customerName = view.findViewById(R.id.name_of_customerLoan1);
                startDate = view.findViewById(R.id.startDate1);
                requestDate_of_customer = view.findViewById(R.id.loanDate12);
                loanBalance = view.findViewById(R.id.balanceLoan22);
                status = view.findViewById(R.id.statusLoan1);
                endDate = view.findViewById(R.id.loanEndDate1);
                thumbnail = view.findViewById(R.id.thumbnail_tx);

                Customer customer = new Customer();
                Profile profile = new Profile();
                Loan loan = new Loan();



            }
        }


        public LoanAdapter(Context context, List<Loan> loans) {
            this.context = context;
            this.loans = loans;
        }

        @NonNull
        @Override
        public LoanAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.loan_item_row, parent, false);

            return new LoanAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            final Loan loan = loans.get(position);
            final Profile profile = loan.getProfile();
            final Account account = loan.getAccount();
            final Customer customer = loan.getCustomer();
            holder.profileName.setText(MessageFormat.format("{0},{1}", profile.getProfileLastName(), profile.getProfileFirstName()));
            holder.customerName.setText(MessageFormat.format("Customer Name:{0}{1}", customer.getCusSurname(), customer.getCusFirstName()));
            holder.loan_request_amount.setText(MessageFormat.format("NGN{0}", loan.getAmount()));
            holder.requestDate_of_customer.setText(loan.getDate());
            holder.status.setText(loan.getStatus());
            holder.startDate.setText(MessageFormat.format("Start Date:{0}", loan.getStartDate()));
            holder.endDate.setText(("End Date"+loan.getEndDate()));
            holder.loanBalance.setText(MessageFormat.format("NGN{0}", loan.getBalanceInBigDecimal()));


        }

        @Override
        public int getItemCount() {
            return loans.size();
        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            //throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
        void onItemClick(Loan item);
    }

}
