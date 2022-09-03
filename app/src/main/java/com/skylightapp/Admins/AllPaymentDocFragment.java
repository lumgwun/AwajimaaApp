package com.skylightapp.Admins;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.skylightapp.Classes.AllPaymentDocAdapter;
import com.skylightapp.Classes.PaymentDoc;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.AdminBalanceDAO;
import com.skylightapp.Database.CodeDAO;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.LoanDAO;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.Database.PaymDocDAO;
import com.skylightapp.Database.PaymentCodeDAO;
import com.skylightapp.Database.PaymentDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Database.SODAO;
import com.skylightapp.Database.TCashDAO;
import com.skylightapp.Database.TReportDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.LoginDirAct;
import com.skylightapp.R;

import java.util.ArrayList;

public class AllPaymentDocFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private static final String TAG = AllPaymentDocFragment.class.getSimpleName();
    private static final String URL = "https://skylightciacs.com/DocumentListAdmin";

    private RecyclerView recyclerView;

    private ArrayList<PaymentDoc> documentList;
    private AllPaymentDocAdapter mAdapter;
    private FloatingActionButton fab;


    DBHelper dbHelper;
    SharedPreferences userPreferences;
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
    Gson gson;
    String json;
    Profile userProfile;

    public AllPaymentDocFragment() {
    }


    public static AllPaymentDocFragment newInstance(String param1, String param2) {
        AllPaymentDocFragment fragment = new AllPaymentDocFragment();
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

        View view = inflater.inflate(R.layout.frag_all_payment_docs, container, false);

        recyclerView = view.findViewById(R.id.recycler_doc_all);
        fab = view.findViewById(R.id.document_home_all);


        dbHelper = new DBHelper(getContext());

        paymDocDAO= new PaymDocDAO(getContext());

        try {

            documentList = new ArrayList<PaymentDoc>();
            documentList = paymDocDAO.getAllDocuments();
            mAdapter = new AllPaymentDocAdapter(getContext(), documentList);

        } catch (SQLiteException e) {
            System.out.println("Oops!");
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        return view;
    }
    private void cMHome() {
        Intent usersIntent = new Intent(getContext(),
                LoginDirAct.class);
        usersIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Toast.makeText(this.getActivity(), "Taking you to Dashboard", Toast.LENGTH_SHORT).show();

    }
}
