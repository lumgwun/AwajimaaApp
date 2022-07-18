package com.skylightapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Adapters.MessageAdapter;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Message;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;

import java.util.ArrayList;


public class MyMessageFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = MyMessageFragment.class.getSimpleName();
    private static final String URL = "https://skylightciacs.com/MessageListUser";

    private RecyclerView recyclerView,recyclerView2;

    private ArrayList<Message> messageArrayList,messageArrayList2;


    private MessageAdapter mAdapter,mAdapter2;


    DBHelper dbHelper;
    SharedPreferences userPreferences;
    Gson gson;
    String json;
    private ListView lstAccounts;
    private TextView txtTitleMessage;
    private TextView txtDetailMessage;
    Button timeLine;
    private androidx.cardview.widget.CardView CardView;
    private Customer customer;


    private Profile userProfile;
    int profileID;
    int customerID;
    private String mParam1;
    private String mParam2;

    public MyMessageFragment() {
    }


    public static MyMessageFragment newInstance(String param1, String param2) {
        MyMessageFragment fragment = new MyMessageFragment();
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
        //return inflater.inflate(R.layout.frag_my_message, container, false);
        View rootView= inflater.inflate(R.layout.frag_my_message, container, false);
        txtTitleMessage = rootView.findViewById(R.id.message_tittle);
        recyclerView = rootView.findViewById(R.id.recycler_view_message);
        recyclerView2 = rootView.findViewById(R.id.recycler_message23);
        userProfile=new Profile();
        customer=new Customer();
        userPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        gson = new Gson();
        String json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        messageArrayList = new ArrayList<Message>();
        messageArrayList2 = new ArrayList<Message>();
        mAdapter = new MessageAdapter(getContext(), messageArrayList);

        dbHelper = new DBHelper(getContext());
        if(userProfile !=null){
            profileID =userProfile.getPID();
            customer=userProfile.getProfileCus();

        }
        if(customer !=null){
            customerID=customer.getCusUID();
        }
        messageArrayList2=dbHelper.getMessagesForCurrentCustomer(customerID);
        messageArrayList = dbHelper.getMessagesFromCurrentProfile(profileID);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);


        mAdapter2 = new MessageAdapter(getContext(), messageArrayList2);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(mAdapter);
        recyclerView2.setNestedScrollingEnabled(false);

        getActivity().setTitle("Support Messages");
        //((NewCustomerDrawer) getActivity()).showDrawerButton();

        setValues();
        return rootView;
    }
    private void setValues() {

        userPreferences = this.requireActivity().getSharedPreferences("LastProfileUsed", Context.MODE_PRIVATE);
        gson = new Gson();
        String json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);




    }

}