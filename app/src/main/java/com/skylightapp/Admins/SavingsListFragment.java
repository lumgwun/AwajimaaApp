package com.skylightapp.Admins;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.PaymentDoc;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.String.valueOf;

public class SavingsListFragment extends Fragment {
    private static final String TAG = SavingsListFragment.class.getSimpleName();
    private static final String URL = "https://skylightciacs.com/savingsListFragment";

    private RecyclerView recyclerView;

    private List<CustomerDailyReport> customerDailyReports;
    private SavingsAdapter mAdapter;

    DBHelper dbHelper;
    FloatingActionButton homFab;
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson;
    private String json;

    private Profile adminProfile;
    private String profileID;
    private PaymentDoc paymentDoc;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    long userId;
    long savingsId;

    public SavingsListFragment() {
        // Required empty public constructor
    }


    public static SavingsListFragment  newInstance(String param1, String param2) {
        SavingsListFragment fragment = new SavingsListFragment();
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
        //return inflater.inflate(R.layout.fragment_savings_list2, container, false);
        View view = inflater.inflate(R.layout.frag_savings, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_savings2);
        ArrayList<CustomerDailyReport> customerDailyReports = new ArrayList<CustomerDailyReport>();
        mAdapter = new SavingsAdapter(getActivity(), this.customerDailyReports);
        dbHelper = new DBHelper(getContext());
        userPreferences = requireContext().getSharedPreferences("LastProfileUsed", MODE_PRIVATE);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        adminProfile = gson.fromJson(json, Profile.class);
        userId=adminProfile.getPID();

        homFab=view.findViewById(R.id.admin_home4);
        homFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome();
            }
        });

        this.customerDailyReports = dbHelper.getAllReportsAdmin();
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        //recyclerView.setLayoutManager(mLayoutManager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);


        return view;
    }


    static class SavingsAdapter extends RecyclerView.Adapter<SavingsAdapter.MyViewHolder> {
        private final Context context;
        private final List<CustomerDailyReport> dailyReports;
        private PaymentDoc paymentDoc;
        private MarketBizPackage marketBizPackage;
        private int packageCount;
        int packageID;

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView savings_customer,packageCount,paymentMethod,paymentCode, status11,amount_remaining,package_id,savings_date,record_amount3,savings_id12,numberOfSavings,daysRemaining3,total;
            public ImageView image_report;
            public AppCompatButton savings_more1;
            LinearLayout layoutGoneRem,layoutPaymentMethod;

            public MyViewHolder(View view) {
                super(view);
                packageCount = view.findViewById(R.id.package_count);
                paymentCode = view.findViewById(R.id.paymentCode);
                paymentMethod = view.findViewById(R.id.paymentMethod);
                layoutGoneRem = view.findViewById(R.id.goneRem);
                savings_more1 = view.findViewById(R.id.savings_more11);
                package_id = view.findViewById(R.id.package_id);
                layoutPaymentMethod = view.findViewById(R.id.gonePaymentMethod);
                record_amount3 = view.findViewById(R.id.defaultAmount3);
                savings_customer = view.findViewById(R.id.savings_customer);
                total = view.findViewById(R.id.totalsavings);
                savings_date = view.findViewById(R.id.savings_date);
                numberOfSavings = view.findViewById(R.id.number_day12);
                amount_remaining = view.findViewById(R.id.amount_rem2);
                savings_id12 = view.findViewById(R.id.savings_id12);
                status11 = view.findViewById(R.id.status11);

                daysRemaining3 = view.findViewById(R.id.daysRem);

                savings_more1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layoutGoneRem.setVisibility(View.VISIBLE);
                        layoutPaymentMethod.setVisibility(View.VISIBLE);
                        //cardView3.setVisibility(View.VISIBLE);
                        //cardView2.setVisibility(View.VISIBLE);
                        //cardView1.setVisibility(View.VISIBLE);

                    }
                });
                savings_more1.setLongClickable(true);
                savings_more1.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        layoutGoneRem.setVisibility(View.GONE);
                        layoutPaymentMethod.setVisibility(View.GONE);
                        return false;
                    }
                });


            }
        }


        public SavingsAdapter(Context context, List<CustomerDailyReport> packagesSubscribed) {
            this.context = context;
            this.dailyReports = packagesSubscribed;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.frag_savings_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            final CustomerDailyReport customerDailyReport = dailyReports.get(position);
            if(customerDailyReport !=null){
                paymentDoc = customerDailyReport.getRecordPaymentDoc();
                marketBizPackage =customerDailyReport.getRecordPackage();

            }
            if(marketBizPackage !=null){
                packageCount= marketBizPackage.getCount();
                packageID= marketBizPackage.getPackID();

            }

            holder.package_id.setText(MessageFormat.format("Pack ID: {0}", customerDailyReport.getRecordPackageId()));
            holder.savings_customer.setText(MessageFormat.format("Customer: {0}", customerDailyReport.getRecordCustomerName()));
            holder.record_amount3.setText(MessageFormat.format("Amount:{0}", valueOf(customerDailyReport.getRecordAmount())));
            holder.total.setText(valueOf("Total:"+ customerDailyReport.getRecordAmount()));
            holder.numberOfSavings.setText(valueOf("Days:"+customerDailyReport.getRecordNumberOfDays()));
            holder.savings_date.setText(MessageFormat.format("Date:{0}", customerDailyReport.getRecordDate()));
            holder.amount_remaining.setText(String.valueOf("Amount Rem:"+customerDailyReport.getRecordAmountRemaining()));
            holder.paymentCode.setText(MessageFormat.format("Code:{0}", customerDailyReport.getRecordSavingsCode()));
            if(paymentDoc !=null){
                holder.packageCount.setText(MessageFormat.format("Count:{0}", packageCount));
                holder.paymentMethod.setText(MessageFormat.format("Payment method:{0}", paymentDoc.getPaymentMethod()));


            }

            //Bitmap savingsDoc=dbHelper.getDocPicture(savingsId);
            holder.savings_id12.setText(MessageFormat.format("Savings ID:{0}", customerDailyReport.getRecordID()));
            holder.status11.setText(customerDailyReport.getRecordStatus());


            Glide.with(context)
                    .load(customerDailyReport.getRecordDocLink())
                    .into(holder.image_report);


        }

        @Override
        public int getItemCount() {
            return dailyReports.size();
        }


    }
    public void goHome() {
        Intent intent = new Intent(getActivity(), NewCustomerDrawer.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("UserID", userId);
        startActivity(intent);

    }
}
