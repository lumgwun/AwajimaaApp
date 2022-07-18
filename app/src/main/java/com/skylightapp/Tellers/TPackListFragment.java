package com.skylightapp.Tellers;

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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.skylightapp.Adapters.PackageRecyclerAdapter;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class TPackListFragment extends Fragment {

    private static final String TAG = TPackListFragment.class.getSimpleName();
    private static final String URL = "https://skylightciacs.com/PackageListTeller";

    private RecyclerView recyclerView;

    private ArrayList<SkyLightPackage> skyLightPackages;
    private PackageRecyclerAdapter mAdapter;


    DBHelper dbHelper;
    SharedPreferences userPreferences;
    Gson gson;
    String json;
    private FloatingActionButton fab;
    Profile userProfile;
    String SharedPrefUserName;
    SharedPreferences sharedPreferences;
    String SharedPrefUserPassword,SharedPrefCusID,SharedPrefUserMachine,SharedPrefState,SharedPrefOffice,
            SharedPrefAddress,SharedPrefJoinedDate,SharedPrefGender,name,
            SharedPrefRole,SharedPrefDOB,SharedPrefPhone,SharedPrefEmail,SharedPrefProfileID,
            SharedPrefSurName,SharedPrefFirstName,SharedPrefAcctNo,customerId,SharedPrefBankNo,SharedPrefAcctBalance,SharedPrefAcctName,SharedPrefType,SharedPrefBank
            ;
    private ListView lstAccounts;
    private TextView txtTitleMessage;
    private TextView txtDetailMessage;
    private Button btnAddMyPackages;
    private Button btnAddAllPackages,btnAcctOverview,btnLoanOverview;
    private androidx.cardview.widget.CardView CardView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String PREF_NAME = "skylight";

    private String mParam1;
    private String mParam2;
    private int selectedAccountIndex;
    FragmentActivity activity;

    public TPackListFragment() {
        // Required empty public constructor
    }

    public static TPackListFragment newInstance(String param1, String param2) {
        TPackListFragment fragment = new TPackListFragment();
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
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.frag_teller_packs, container, false);
        fab = rootView.findViewById(R.id.cm_home_pack);
        txtTitleMessage = rootView.findViewById(R.id.package_tittle);
        recyclerView = rootView.findViewById(R.id.recycler_view_packs);
        sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=sharedPreferences.getString("USER_NAME", "");
        SharedPrefUserPassword=sharedPreferences.getString("USER_PASSWORD", "");
        SharedPrefCusID=sharedPreferences.getString("CUSTOMER_ID", "");
        SharedPrefUserMachine=sharedPreferences.getString("machine", "");
        SharedPrefProfileID=sharedPreferences.getString("PROFILE_ID", "");

        SharedPrefEmail=sharedPreferences.getString("EMAIL_ADDRESS", "");
        SharedPrefRole=sharedPreferences.getString("USER_ROLE", "");
        SharedPrefGender=sharedPreferences.getString("GENDER_KEY", "");
        SharedPrefJoinedDate=sharedPreferences.getString("USER_DATE_JOINED", "");
        SharedPrefOffice=sharedPreferences.getString("CHOSEN_OFFICE", "");
        customerId=sharedPreferences.getString("CUSTOMER_ID", "");
        SharedPrefAcctNo=sharedPreferences.getString("ACCOUNT_NO", "");
        SharedPrefBankNo=sharedPreferences.getString("BANK_ACCT_NO", "");
        SharedPrefAcctBalance=sharedPreferences.getString("ACCOUNT_BALANCE", "");
        SharedPrefAcctName=sharedPreferences.getString("ACCOUNT_NAME", "");
        SharedPrefType=sharedPreferences.getString("ACCOUNT_TYPE", "");
        SharedPrefBank=sharedPreferences.getString("ACCOUNT_BANK", "");
        gson = new Gson();

        userPreferences = getContext().getSharedPreferences("LastProfileUsed", MODE_PRIVATE);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        skyLightPackages = new ArrayList<SkyLightPackage>();
        mAdapter = new PackageRecyclerAdapter(skyLightPackages,getContext());

        dbHelper = new DBHelper(getContext());
        if(userProfile !=null){
            int profileID =userProfile.getPID();

            skyLightPackages = dbHelper.getPackagesFromCurrentProfile(profileID);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            recyclerView.setNestedScrollingEnabled(false);

        }


        getActivity().setTitle("Package Lists");
        //((MarketerDrawerActivity) getActivity()).showDrawerButton();

        setValues();

        return rootView;
    }
    private void setValues() {
        selectedAccountIndex = 0;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cMHome();
            }
        });

    }
    /*private void displayAccountOverview() {
        Intent usersIntent = new Intent(getContext(),
                CMAccountOverview.class);
        usersIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Toast.makeText(this.getActivity(), "Your Account Overview Loading....", Toast.LENGTH_SHORT).show();

    }
    private void displayLoanOverview() {
        Intent usersIntent = new Intent(getContext(),
                CMBorrowingOverviewFragment.class);
        usersIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Toast.makeText(this.getActivity(), "Borrowing Activity Loading....", Toast.LENGTH_SHORT).show();

    }*/
    private void displayFromMyPackages() {
        Intent usersIntent = new Intent(getContext(),
                PackageAllCusAct.class);
        usersIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Toast.makeText(this.getActivity(), "Taking you to retrieved Your packages", Toast.LENGTH_SHORT).show();

    }
    private void displayAddAllPackage() {
        Intent usersIntent = new Intent(getContext(),
                PackageAllCusAct.class);
        usersIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Toast.makeText(this.getActivity(), "Taking you to retrieve from the Database", Toast.LENGTH_SHORT).show();

    }
    private void cMHome() {
        Intent usersIntent = new Intent(getContext(),
                TellerHomeChoices.class);
        usersIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Toast.makeText(this.getActivity(), "Taking your back to your Back Office", Toast.LENGTH_SHORT).show();

    }
}
