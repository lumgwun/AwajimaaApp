package com.skylightapp.Tellers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.skylightapp.Classes.BorrowingAdapter;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.util.ArrayList;

public class CMBorrowingOverviewFragment extends Fragment {

    private static final String TAG = CMBorrowingOverviewFragment.class.getSimpleName();
    private static final String URL = "https://skylightciacs.com/loanListTeller";

    private RecyclerView recyclerView;

    private ArrayList<Loan> loanList;
    private BorrowingAdapter mAdapter;


    DBHelper dbHelper;
    SharedPreferences userPreferences;
    Gson gson;
    String json;
    private FloatingActionButton fab;
    private ListView lstAccounts;
    private TextView txtTitleMessage;
    private TextView txtDetailMessage;

    private androidx.cardview.widget.CardView CardView;

    private Profile userProfile;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private int selectedAccountIndex;
    FragmentActivity activity;
    private  BorrowingAdapter borrowingAdapter;

    public CMBorrowingOverviewFragment() {
        // Required empty public constructor
    }

    public static CMBorrowingOverviewFragment newInstance(String param1, String param2) {
        CMBorrowingOverviewFragment fragment = new CMBorrowingOverviewFragment();
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
        View rootView= inflater.inflate(R.layout.frag_borrowing_o, container, false);
        fab = rootView.findViewById(R.id.cm_home);
        txtTitleMessage = rootView.findViewById(R.id.borrowing_tittle);
        recyclerView = rootView.findViewById(R.id.recycler_view_borrow);
        userPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        gson = new Gson();
        String json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        loanList = new ArrayList<Loan>();
        if(userProfile !=null){
            long profileID =userProfile.getPID();

            loanList = userProfile.getProfileLoans();

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            recyclerView.setNestedScrollingEnabled(false);
            mAdapter = new BorrowingAdapter(getContext(), loanList);

            getActivity().setTitle("Borrowings");
            //((TellerHomeChoices) getActivity()).showDrawerButton();

        }


        dbHelper = new DBHelper(getContext());


        setValues();

        return rootView;
    }
    private void setValues() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cMHome();
            }
        });


    }
    private void cMHome() {
        Intent usersIntent = new Intent(getContext(),
                TellerHomeChoices.class);
        usersIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Toast.makeText(this.getActivity(), "Taking you to the Teller Dashboard", Toast.LENGTH_SHORT).show();

    }

}
