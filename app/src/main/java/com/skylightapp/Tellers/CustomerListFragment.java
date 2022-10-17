package com.skylightapp.Tellers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.gson.Gson;
import com.mig35.carousellayoutmanager.CarouselLayoutManager;
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.mig35.carousellayoutmanager.CenterScrollListener;
import com.skylightapp.Adapters.CusRowItemAdapt;
import com.skylightapp.Classes.CubeOutTransformer;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.skylightapp.Classes.Account.ACCOUNT_TYPE;

public class CustomerListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = CustomerListFragment.class.getSimpleName();
    private static final String URL = "https://skylightciacs.com";

    private RecyclerView recyclerView;


    private ArrayList<Customer> customerList;
    private CusRowItemAdapt mAdapter;


    DBHelper dbHelper;
    SharedPreferences userPreferences;
    Gson gson;
    String json;
    Profile userProfile;
    String SharedPrefUserName;
    SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "skylight";
    String SharedPrefUserPassword,SharedPrefCusID,SharedPrefUserMachine,SharedPrefState,SharedPrefOffice,
            SharedPrefAddress,SharedPrefJoinedDate,SharedPrefGender,name,
            SharedPrefRole,SharedPrefDOB,SharedPrefPhone,SharedPrefEmail,SharedPrefProfileID,
            SharedPrefSurName,SharedPrefFirstName,SharedPrefAcctNo,customerId,SharedPrefBankNo,SharedPrefAcctBalance,SharedPrefAcctName,SharedPrefType,SharedPrefBank
            ;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 2;


    public CustomerListFragment() {
        // Required empty public constructor
    }

    public static CustomerListFragment newInstance(String param1, String param2) {
        CustomerListFragment fragment = new CustomerListFragment();
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

        View view = inflater.inflate(R.layout.frag_customer2, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_customers);
        customerList = new ArrayList<Customer>();
        userProfile= new Profile();
        dbHelper = new DBHelper(getContext());
        mAdapter = new CusRowItemAdapt(getActivity(), customerList);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferences = getContext().getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        json = sharedPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        dbHelper = new DBHelper(getContext());
        if(userProfile !=null){
            customerList = userProfile.getProfileCustomers();

            mAdapter = new CusRowItemAdapt(getContext(), customerList);
            recyclerView.setAdapter(new CusRowItemAdapt(getContext(), customerList));

        }
        SharedPrefUserName=sharedPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=sharedPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=sharedPreferences.getString("CUSTOMER_ID", "");
        SharedPrefUserMachine=sharedPreferences.getString("machine", "");
        SharedPrefProfileID=sharedPreferences.getString("PROFILE_ID", "");
        SharedPrefSurName=sharedPreferences.getString("PROFILE_SURNAME", "");
        SharedPrefFirstName=sharedPreferences.getString("PROFILE_FIRSTNAME", "");
        SharedPrefEmail=sharedPreferences.getString("PROFILE_EMAIL", "");
        SharedPrefPhone=sharedPreferences.getString("PHONE_NUMBER_KEY", "");
        SharedPrefAddress=sharedPreferences.getString("PROFILE_ADDRESS", "");
        SharedPrefDOB=sharedPreferences.getString("PROFILE_DOB", "");
        SharedPrefRole=sharedPreferences.getString("PROFILE_ROLE", "");
        SharedPrefGender=sharedPreferences.getString("PROFILE_GENDER", "");
        SharedPrefJoinedDate=sharedPreferences.getString("PROFILE_DATE_JOINED", "");
        SharedPrefOffice=sharedPreferences.getString("PROFILE_OFFICE", "");
        SharedPrefState=sharedPreferences.getString("PROFILE_STATE", "");

        customerId=sharedPreferences.getString("CUSTOMER_ID", "");
        SharedPrefAcctNo=sharedPreferences.getString("ACCOUNT_NO", "");
        SharedPrefBankNo=sharedPreferences.getString("BANK_ACCT_NO", "");
        SharedPrefAcctBalance=sharedPreferences.getString("ACCOUNT_BALANCE", "");
        SharedPrefAcctName=sharedPreferences.getString("ACCOUNT_NAME", "");
        SharedPrefType=sharedPreferences.getString("ACCOUNT_TYPE", "");
        SharedPrefBank=sharedPreferences.getString("ACCOUNT_BANK", "");
        gson = new Gson();


        mAdapter = new CusRowItemAdapt(getActivity(), customerList);
        CarouselLayoutManager linearLayoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        linearLayoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new CubeOutTransformer());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addOnScrollListener(new CenterScrollListener());
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);


        return view;
    }



}
