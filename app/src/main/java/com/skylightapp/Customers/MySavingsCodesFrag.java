package com.skylightapp.Customers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.skylightapp.Classes.PaymentCode;
import com.skylightapp.Classes.PaymentCodeAdapter;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.util.ArrayList;

public class MySavingsCodesFrag extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private static final String TAG = MySavingsFragment.class.getSimpleName();
    private static final String URL = "https://skylightciacs.com/CustomerSavingsCode";

    private RecyclerView recyclerView;

    private ArrayList<PaymentCode> paymentCodes;
    private PaymentCodeAdapter mAdapter;


    DBHelper dbHelper;
    SharedPreferences userPreferences;
    Gson gson;
    String json;
    private ListView lstAccounts;
    private TextView txtTitleMessage;
    private TextView txtDetailMessage;
    private Button btnAddMyPackages;
    private Button btnSkylightSupport,btnAcctOverview,btnLoanOverview;
    private androidx.cardview.widget.CardView CardView;

    private int selectedAccountIndex;
    FragmentActivity activity;
    private Profile userProfile;
    private OnFragmentInteractionListener listener;


    public MySavingsCodesFrag() {
    }

    public static MySavingsCodesFrag newInstance(String param1, String param2) {
        MySavingsCodesFrag fragment = new MySavingsCodesFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static Fragment newInstance() {
        MySavingsCodesFrag fragment = new MySavingsCodesFrag();
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
        View rootView= inflater.inflate(R.layout.frag_savings_code, container, false);


        ArrayList<PaymentCode> savings = new ArrayList<PaymentCode>();
        mAdapter = new PaymentCodeAdapter(getContext(), this.paymentCodes);
        dbHelper = new DBHelper(getContext());

        recyclerView = rootView.findViewById(R.id.recycler_code_my);
        userPreferences = this.requireActivity().getSharedPreferences("LastProfileUsed", Context.MODE_PRIVATE);
        gson = new Gson();
        String json = userPreferences.getString("LastProfileUsed", "");
        Profile userProfile = gson.fromJson(json, Profile.class);

        this.paymentCodes = new ArrayList<PaymentCode>();
        mAdapter = new PaymentCodeAdapter(getContext(), R.layout.code_list_row, this.paymentCodes);
        if(userProfile !=null){
            int profileID =userProfile.getPID();
            String surName =userProfile.getProfileLastName();
            this.paymentCodes = dbHelper.getSavingsCodesProfile(profileID);

        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);


        requireActivity().setTitle("My Savings Code");
        //((CustomerPackageActivity) requireActivity()).showUpButton();


        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnFragmentInteractionListener {
    }
}
