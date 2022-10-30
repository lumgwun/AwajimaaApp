package com.skylightapp.Admins;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.gson.Gson;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class AdminTransListFrag extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static final String TAG = AdminTransListFrag.class.getSimpleName();

    private RecyclerView recyclerView;
    private List<Transaction> transactionList;
    private TransactionAdapter mAdapter;

    DBHelper dbHelper;
    SharedPreferences userPreferences;
    Gson gson;
    String json;
    Profile userProfile;
    private int resource;
    Context context;
    private TranXDAO tranXDAO;


    public AdminTransListFrag() {
    }

    public static AdminTransListFrag newInstance(String param1, String param2) {
        AdminTransListFrag fragment = new AdminTransListFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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


        View view = inflater.inflate(R.layout.frag_admin_trans_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        transactionList = new ArrayList<>();
        dbHelper = new DBHelper(getContext());
        tranXDAO = new TranXDAO(getContext());

        mAdapter = new TransactionAdapter(getActivity(), transactionList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new TransactionAdapter(context,transactionList));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        transactionList = tranXDAO.getAllTransactionAdmin();
        mAdapter = new TransactionAdapter(getContext(),transactionList);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);


        return view;
    }



    static class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {
        private final Context context;
        private final List<Transaction> transactions;

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView clientId, amount;
            public TextView payee, destinationAccount;
            public TextView payingAccount, transactionId;
            public TextView type,savingsID;

            public TextView name, dateOfTransaction,purposeOfPayment;
            public ImageView thumbnail;
            private final LinearLayout accountLayout;

            public MyViewHolder(View view) {
                super(view);
                clientId = view.findViewById(R.id.customer90);
                name = view.findViewById(R.id.name_of_client);
                amount = view.findViewById(R.id.transaction_amount);
                payee = view.findViewById(R.id.payee);
                savingsID = view.findViewById(R.id.savingsId80);
                destinationAccount = view.findViewById(R.id.destination_account);
                payingAccount = view.findViewById(R.id.payingAccount);
                Button more_btn = view.findViewById(R.id.txMoreBtn);
                type = view.findViewById(R.id.transaction_type);
                dateOfTransaction = view.findViewById(R.id.transaction_date);
                transactionId = view.findViewById(R.id.tx_id);
                purposeOfPayment = view.findViewById(R.id.thumbnail_tx);
                accountLayout=view.findViewById(R.id.t_40);
                more_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        accountLayout.setVisibility(View.VISIBLE);

                    }
                });

            }
        }


        public TransactionAdapter(Context context, List<Transaction> transactions) {
            this.context = context;
            this.transactions = transactions;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.transaction_item_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            final Transaction transaction = transactions.get(position);
            final Customer customer = new Customer();
            holder.transactionId.setText(transaction.getTransactionID());
            holder.amount.setText(valueOf(transaction.getTranxAmount()));
            holder.type.setText(valueOf(transaction.getTranXType()));
            holder.destinationAccount.setText(transaction.getTranxDestAcct());
            holder.name.setText(String.format("%s%s", customer.getCusSurname().substring(1, 0), customer.getCusFirstName()));
            holder.clientId.setText(valueOf(customer.getCusUID()));
            holder.payee.setText(transaction.getTranxPayee());
            holder.savingsID.setText(Math.toIntExact(transaction.getTranxRecordID()));
            holder.payingAccount.setText(transaction.getTranxSendingAcct());
            holder.dateOfTransaction.setText(transaction.getTranxDate());

            holder.purposeOfPayment.setText(transaction.getTranxPayee());

            //Glide.with(context)
            //.load(transaction.getImage())
            // .into(holder.thumbnail);
        }

        @Override
        public int getItemCount() {
            return (null != transactions ? transactions.size() : 0);
        }

    }
}
