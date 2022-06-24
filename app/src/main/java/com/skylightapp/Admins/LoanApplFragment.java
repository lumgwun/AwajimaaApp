package com.skylightapp.Admins;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

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

public class LoanApplFragment extends Fragment {
    private static final String TAG = LoanApplFragment.class.getSimpleName();
    private static final String URL = "https://skylightciacs.com/loanListAdmin";

    private RecyclerView recyclerView;

    private ArrayList<Loan> loanList;
    private LoanAdapter mAdapter;


    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    Gson gson;
    String json;
    Profile userProfile;
    private OnFragmentInteractionListener listener;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoanApplFragment() {
    }

    public static LoanApplFragment newInstance(String param1, String param2) {
        LoanApplFragment fragment = new LoanApplFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance() {
        LoanApplFragment fragment = new LoanApplFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_loan_appln_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_loan_admin);
        loanList = new ArrayList<Loan>();
        mAdapter = new LoanAdapter(getContext(), loanList);

        dbHelper = new DBHelper(getContext());
        sqLiteDatabase = dbHelper.getWritableDatabase();
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {

            dbHelper.openDataBase();
            try {

                loanList = dbHelper.getAllLoansAdmin();


            } catch (SQLiteException e) {
                System.out.println("Oops!");
            }



        }



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setNestedScrollingEnabled(false);


        return view;
    }

    class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.MyViewHolder> {
        private final Context context;
        private final List<Loan> loans;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView name,status, joinedDateText, startDate, amount1_of_text,loan_request_amount,phone_of_customer
                    , loanBalance,bank_of_customer, profileName, endDate, customerName,requestDate_of_customer,customer_balance_text;
            public ImageView thumbnail;

            public MyViewHolder(View view) {
                super(view);



                loan_request_amount = view.findViewById(R.id.loan_request_amount);
                profileName = view.findViewById(R.id.profileName);
                customerName = view.findViewById(R.id.name_of_customerLoan);
                startDate = view.findViewById(R.id.startDate);
                requestDate_of_customer = view.findViewById(R.id.loanDate1);
                loanBalance = view.findViewById(R.id.balanceLoan2);
                status = view.findViewById(R.id.statusLoan);
                endDate = view.findViewById(R.id.loanEndDate);



                thumbnail = view.findViewById(R.id.thumbnail_tx);

                SwitchCompat simpleSwitch = (SwitchCompat)view.findViewById(R.id.switch_loan_action);

                Customer customer = new Customer();
                Profile profile = new Profile();
                Loan loan = new Loan();

                Account account = new Account();
                String loanStatusSwitch = null;
                if (simpleSwitch.isChecked())
                    loanStatusSwitch = simpleSwitch.getTextOn().toString();
                if (loanStatusSwitch != null && loanStatusSwitch.contentEquals("on"))
                    status.setVisibility(View.VISIBLE);
                simpleSwitch.setVisibility(View.INVISIBLE);

                Toast.makeText(getContext(), "Loan approval :" + loanStatusSwitch, Toast.LENGTH_LONG).show();
                dbHelper = new DBHelper(getContext());
                sqLiteDatabase = dbHelper.getWritableDatabase();

                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {

                    dbHelper.openDataBase();
                    try {

                        dbHelper.overwriteLoan1(profile,customer,loan);


                    } catch (SQLiteException e) {
                        System.out.println("Oops!");
                    }



                }


            }
        }


        public LoanAdapter(Context context, List<Loan> loans) {
            this.context = context;
            this.loans = loans;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.loan_item_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            final Loan loan = loans.get(position);
            final Profile profile = loan.getProfile();
            final Customer customer = loan.getCustomer();
            holder.profileName.setText(MessageFormat.format("{0},{1}", profile.getProfileLastName(), profile.getProfileFirstName()));
            holder.customerName.setText("Customer Name:"+customer.getCusSurname()+""+customer.getCusFirstName());
            holder.loan_request_amount.setText("NGN"+loan.getAmount());
            holder.requestDate_of_customer.setText(loan.getDate());
            holder.status.setText(loan.getStatus());
            holder.startDate.setText("Start Date:"+loan.getStartDate());
            holder.endDate.setText(("End Date"+loan.getEndDate()));
            holder.loanBalance.setText("NGN"+loan.getBalanceInBigDecimal());


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
