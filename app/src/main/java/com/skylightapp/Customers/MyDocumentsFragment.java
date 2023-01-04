package com.skylightapp.Customers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.skylightapp.Adapters.DocumentAdapter;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PaymentDoc;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.PaymDocDAO;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.Objects;


import static android.content.Context.MODE_PRIVATE;

public class MyDocumentsFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private static final String URL = "https://awajima.com/MyDocumentList";

    private RecyclerView recyclerView;
    private ArrayList<PaymentDoc> paymentDocs;
    private DocumentAdapter mAdapter;


    DBHelper dbHelper;
    SharedPreferences userPreferences;
    Gson gson;
    String json,json1,profileFirstName,profileSurname;
    int customerID;
    private ListView lstAccounts;
    private AppCompatTextView txtTitleMessage;
    private AppCompatTextView txtDetailMessage;
    private AppCompatButton btnTimeLine;
    private AppCompatButton btnAddSavings;
    private androidx.cardview.widget.CardView CardView;
    FragmentManager fragmentManager;
    private Customer customer;
    Profile userProfile;

    private int selectedAccountIndex;
    FragmentActivity activity;
    PaymentDoc document;
    private static final String PREF_NAME = "awajima";

    public MyDocumentsFragment() {
    }

    public static MyDocumentsFragment newInstance(String param1, String param2) {
        MyDocumentsFragment fragment = new MyDocumentsFragment();
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

        View rootView= inflater.inflate(R.layout.frag_my_docs, container, false);
        txtTitleMessage = rootView.findViewById(R.id.document_tittle);
        recyclerView = rootView.findViewById(R.id.recycler_view_document);
        userPreferences= requireContext().getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        customer= new Customer();
        userProfile= new Profile();
        json = userPreferences.getString("LastCustomerUsed", "");
        customer = gson.fromJson(json, Customer.class);
        gson = new Gson();
        json1 = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        dbHelper = new DBHelper(getContext());
        PaymDocDAO paymDocDAO= new PaymDocDAO(getContext());

        paymentDocs = new ArrayList<PaymentDoc>();
        mAdapter = new DocumentAdapter(getContext(), paymentDocs);


        try {
            if(customer !=null){
                customerID = customer.getCusUID();

            }
            if(paymDocDAO !=null){
                try {
                    paymentDocs = paymDocDAO.getDocumentsFromCurrentCustomer(customerID);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


            }



        } catch (SQLiteException e) {
            System.out.println("Oops!");
        }





        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        //DocumentDataPrepare();
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        requireActivity().setTitle("My Documents");
        //((CustomerPackageActivity) requireActivity()).showUpButton();

        setValues();

        return rootView;
    }
    /*private void DocumentDataPrepare() {
        PaymentDocument data = new PaymentDocument();
        paymentDocuments.add(data);
        data = new PaymentDocument();
        paymentDocuments.add(data);
        data = new PaymentDocument();
        paymentDocuments.add(data);
        data = new PaymentDocument();
        paymentDocuments.add(data);
        data = new PaymentDocument();
        paymentDocuments.add(data);
        paymentDocuments.add(data);
        Collections.sort(paymentDocuments, new Comparator() {
            @Override
            public int compare(PaymentDocument o1, PaymentDocument o2) {
                return o1.document.compareTo(o2.document);
            }
        });
    }*/
    private void setValues() {



    }

    private void cMHome() {
        Intent usersIntent = new Intent(getContext(),
                NewCustomerDrawer.class);
        usersIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Toast.makeText(this.getActivity(), "Taking you to the Customer Dashboard", Toast.LENGTH_SHORT).show();

    }
}
