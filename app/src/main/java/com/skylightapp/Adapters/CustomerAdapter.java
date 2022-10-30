package com.skylightapp.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.InsuranceUsersAct;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.BizSuperAdminAllView;
import com.skylightapp.Tellers.MyCusList;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.RecyclerViewHolder> {

    private ArrayList<Customer> customers;
    private List<Customer> customerList;
    private Context mcontext;
    private Profile userProfile;
    private CustomerListener mListener;
    int layout;


    public CustomerAdapter(ArrayList<Customer> customers, Context mcontext) {
        this.customers = customers;
        this.mcontext = mcontext;
    }
    public CustomerAdapter(Context mcontext, int layout, Profile userProfile1) {
        this.layout = layout;
        this.mcontext = mcontext;
        this.userProfile = userProfile1;
    }
    public CustomerAdapter(Context mcontext, int layout, ArrayList<Customer> customers1) {
        this.layout = layout;
        this.mcontext = mcontext;
        this.customers = customers1;
    }

    // Constructor
    public CustomerAdapter() {
        customers = new ArrayList<Customer>();
    }

    public CustomerAdapter(MyCusList myCusList, ArrayList<Customer> customerArrayList) {
        this.mcontext = myCusList;
        this.customers = customerArrayList;

    }
    public CustomerAdapter(MyCusList myCusList, CustomerListener listener, ArrayList<Customer> customerArrayList) {
        this.mcontext = myCusList;
        this.customers = customerArrayList;
        this.mListener = listener;

    }

    public CustomerAdapter(BizSuperAdminAllView bizSuperAdminAllView, ArrayList<Customer> customersNewToday) {
        this.mcontext = bizSuperAdminAllView;
        this.customers = customersNewToday;
    }

    public CustomerAdapter(InsuranceUsersAct insuranceUsersAct, ArrayList<Customer> customerArrayList) {
        this.customers = customerArrayList;
        this.mcontext = insuranceUsersAct;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list_view, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    //@Override
    public void addCustomer(Customer customer) {

    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView txtCustomerName;
        public final TextView txtPhoneNo;
        public final TextView office4;
        public final TextView txtDateJoined;
        public final TextView packages;
        public final TextView savings;
        public final TextView address;
        public final TextView dob;
        public final TextView gender;
        public final Button delete;
        public final Button addPackage;



        public RecyclerViewHolder(@NonNull View view) {
            super(view);

            mIdView = (TextView) view.findViewById(R.id.id_customer4);
            mContentView = (TextView) view.findViewById(R.id.customer_content);
            //Customer customer = getItem(position);
            txtCustomerName = view.findViewById(R.id.name_customer4);
            txtPhoneNo = view.findViewById(R.id.customer_phone4);
            TextView customerSkylightNo = view.findViewById(R.id.customer_phone4);
            ImageView img_account = view.findViewById(R.id.customer_image4);
            img_account.setImageResource(R.drawable.ic_dashboard_black_24dp);
            office4 = view.findViewById(R.id.customer_office4);
            packages = view.findViewById(R.id.package_count4);
            txtDateJoined = view.findViewById(R.id.customer_date_join4);
            savings = view.findViewById(R.id.savings_count4);
            addPackage = view.findViewById(R.id.add_package4);
             delete = view.findViewById(R.id.update4);
            address = view.findViewById(R.id.customer_address4);
            dob = view.findViewById(R.id.customer_dob4);
            gender = view.findViewById(R.id.customer_gender4);
            //return convertView;

        }
        public void clearCustomers() {
            customers.clear();
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder viewHolder, final int position) {
        if (customers.size() <= position) {
            return;
        }
        final Customer customer = customers.get(position);
        viewHolder.office4.setText(MessageFormat.format("State:{0}", customer.getCusOfficeBranch()));
        viewHolder.txtCustomerName.setText(MessageFormat.format("{0}{1}", customer.getCusFirstName(), customer.getCusSurname()));
        viewHolder.txtPhoneNo.setText(MessageFormat.format("Phone:{0}", customer.getCusPhoneNumber()));
        viewHolder.address.setText(String.valueOf("Address:"+customer.getCusAddress()));
        viewHolder.dob.setText(MessageFormat.format("DOB:{0}", customer.getCusDob()));
        viewHolder.gender.setText(MessageFormat.format("Phone:{0}", customer.getCusGender()));
        viewHolder.packages.setText(MessageFormat.format("Number of Package:{0}", customer.getCusSkyLightPackage().getCount()));
        viewHolder.txtDateJoined.setText(MessageFormat.format("Date Joined: {0}", customer.getCusDateJoined()));
        viewHolder.savings.setText(MessageFormat.format("Savings: {0}", customer.getCusDailyReports().size()));
    }

    //@Override
    public void onViewRecycled(RecyclerViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }



    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setData(ArrayList<Customer> customers) {
        this.customers = customers;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mcontext);

        // Get users sort preference
        if (sharedPref.getString(mcontext.getString(R.string.pref_sort_by_key), "0") == "1") {
            sortCustomersByFirstName();
        } else {
            sortCustomersByPhoneNumber();
        }
        notifyDataSetChanged();
    }



    private void sortCustomersByFirstName() {
        Collections.sort(customers, new Comparator<Customer>() {
            @Override
            public int compare(Customer b1, Customer b2) {
                return b1.getCusFirstName().compareTo(b2.getCusFirstName());
            }
        });
    }
    private void sortCustomersByPhoneNumber() {
        Collections.sort(customers, new Comparator<Customer>() {
            @Override
            public int compare(Customer b1, Customer b2) {
                return b1.getCusPhoneNumber().compareTo(b2.getCusPhoneNumber());
            }
        });
    }
    private void sortCustomersByEmail() {
        Collections.sort(customers, new Comparator<Customer>() {
            @Override
            public int compare(Customer b1, Customer b2) {
                return b1.getCusEmail().compareTo(b2.getCusEmail());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != customers ? customers.size() : 0);
    }

    public ArrayList<Customer> getData() {
        return customers;
    }
    public interface CustomerListener {
        void onItemClick(Customer customer);
    }


}
