package com.skylightapp.Customers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.skylightapp.Adapters.PackageRecyclerAdapter;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.util.ArrayList;

public class CustomerPackageOverview extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private static final String TAG = CustomerPackageOverview.class.getSimpleName();
    private static final String URL = "https://skylightciacs.com/CustomerPackageList";

    private RecyclerView recyclerView;

    private ArrayList<SkyLightPackage> skyLightPackages;
    private PackageRecyclerAdapter mAdapter;


    DBHelper dbHelper;
    SharedPreferences userPreferences;
    Gson gson;
    String json;
    private ListView lstAccounts;
    private TextView txtTitleMessage;
    private TextView txtDetailMessage;
    private Button btnAddMyPackages;
    private Button btnSkylightSupport, btnHistory,btnLoanOverview;
    private androidx.cardview.widget.CardView CardView;

    private int selectedAccountIndex;
    FragmentActivity activity;
    private Customer customer;
    long profileID;

    public CustomerPackageOverview() {
    }

    public static CustomerPackageOverview newInstance(String param1, String param2) {
        CustomerPackageOverview fragment = new CustomerPackageOverview();
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

        View rootView= inflater.inflate(R.layout.frag_cus_packs_, container, false);

        //btnAddMyPackages = rootView.findViewById(R.id.add_to_my_package_1);
        //btnSkylightSupport = rootView.findViewById(R.id.support_skylight);
        //btnHistory = rootView.findViewById(R.id.acct_overview_1);
        //btnLoanOverview = rootView.findViewById(R.id.loan_overview_1);
        //addSavings = rootView.findViewById(R.id.cm_add_pack);
        customer= new Customer();
        txtTitleMessage = rootView.findViewById(R.id.package_tittle2);
        recyclerView = rootView.findViewById(R.id.recycler_view_cus);

        userPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        customer = gson.fromJson(json, Customer.class);

        skyLightPackages = new ArrayList<SkyLightPackage>();
        mAdapter = new PackageRecyclerAdapter(getContext(), R.layout.package_list_row,skyLightPackages);
        dbHelper = new DBHelper(getContext());
        if(customer !=null){
            int profileID = customer.getCusUID();
            skyLightPackages = dbHelper.getPackagesFromCustomer(profileID);

        }



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        getActivity().setTitle("My Packages");
        //((NewCustomerDrawer) getActivity()).showUpButton();

        setValues();

        return rootView;
    }
    private void setValues() {
        selectedAccountIndex = 0;
        userPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        gson = new Gson();
        String json = userPreferences.getString("LastProfileUsed", "");
        Profile profile = gson.fromJson(json, Profile.class);



    }



}
