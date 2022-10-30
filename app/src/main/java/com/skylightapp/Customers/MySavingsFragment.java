package com.skylightapp.Customers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.skylightapp.Adapters.SavingsAdapter;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.skylightapp.SkyLightPackageActivity;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class MySavingsFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private static final String TAG = MySavingsFragment.class.getSimpleName();
    private static final String URL = "https://skylightciacs.com/CustomerSavingsList";

    private RecyclerView recyclerView;

    private ArrayList<CustomerDailyReport> savings;
    private SavingsAdapter mAdapter;


    DBHelper dbHelper;
    SharedPreferences userPreferences;
    Gson gson;
    String json;
    private FloatingActionButton fab;
    private ListView lstAccounts;
    private TextView txtTitleMessage;
    private TextView txtDetailMessage;
    private Button btnAddMyPackages;
    private Button btnSkylightSupport,btnAcctOverview,btnLoanOverview;
    private androidx.cardview.widget.CardView CardView;
    private static final String PREF_NAME = "skylight";

    private int selectedAccountIndex;
    FragmentActivity activity;
    private Profile userProfile;
    int profileID;
    String surName;
    private OnFragmentInteractionListener listener;


    public MySavingsFragment() {
    }

    public static MySavingsFragment newInstance(String param1, String param2) {
        MySavingsFragment fragment = new MySavingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static Fragment newInstance() {
        MySavingsFragment fragment = new MySavingsFragment();
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

        View rootView= inflater.inflate(R.layout.frag_my_savings, container, false);


         savings = new ArrayList<CustomerDailyReport>();
        mAdapter = new SavingsAdapter(getContext(), this.savings);
        dbHelper = new DBHelper(getContext());
        recyclerView = rootView.findViewById(R.id.recycler_my_savings2);
        userPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        //userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireContext());
        Gson gson = new Gson();
        userProfile= new Profile();
        String json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        mAdapter = new SavingsAdapter(getContext(), R.layout.savings_list_row, savings);
        dbHelper = new DBHelper(getContext());

        if(userProfile !=null){
            profileID =userProfile.getPID();
             surName =userProfile.getProfileLastName();

        }

        savings = dbHelper.getSavingsFromCurrentProfile(profileID);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        /*this.savings.add(new CustomerDailyReport(19091001,"190","Savings", "NGN3000", "Old","Confirmed"));
        savings.add(new CustomerDailyReport(19091002,"1910","Savings", "NGN1000", "Old","Confirmed"));
        savings.add(new CustomerDailyReport(19091003,"1901","Savings", "NGN30000", "Old","Confirmed"));
        savings.add(new CustomerDailyReport(19091004,"1190","Savings", "NGN1000", "New","Un-Approved"));
        savings.add(new CustomerDailyReport(19091005,"1290","Savings", "NGN7000", "New","Un-Approved"));
        savings.add(new CustomerDailyReport(19091006,"1910","Savings", "NGN9000", "New","Confirmed"));
        savings.add(new CustomerDailyReport(19091007,"2190","Savings", "NGN500", "Old","Confirmed"));
        savings.add(new CustomerDailyReport(19091008,"1990","Savings", "NGN3000", "Old","Confirmed"));
        savings.add(new CustomerDailyReport(19091009,"1900","Savings", "NGN8000", "New","Confirmed"));
        savings.add(new CustomerDailyReport(19091010,"19001","Savings", "NGN3000", "New","Confirmed"));*/


        requireActivity().setTitle("My Savings");
        ((CusPacksAct) requireActivity()).showUpButton();

        setValues();

        return rootView;
    }
    private void setValues() {
        selectedAccountIndex = 0;
        userPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        String json = userPreferences.getString("LastProfileUsed", "");
        Profile userProfile = gson.fromJson(json, Profile.class);


    }
    private void help() {
        Intent usersIntent = new Intent(getContext(),
                CustomerHelpActTab.class);
        usersIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Toast.makeText(this.getContext(), "Taking you to Help Office", Toast.LENGTH_SHORT).show();

    }
    private void displayShopOverview() {
        Intent usersIntent = new Intent(getContext(),
                SkyLightPackageActivity.class);
        usersIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Toast.makeText(this.getContext(), "Taking your the Awajima Shop", Toast.LENGTH_SHORT).show();

    }



    private void myHome() {
        Intent usersIntent = new Intent(getContext(),
                NewCustomerDrawer.class);
        usersIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Toast.makeText(this.getContext(), "Taking your back to your Back Office", Toast.LENGTH_SHORT).show();


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
