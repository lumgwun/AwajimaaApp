package com.skylightapp.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;



public class AdminCusAdapter extends RecyclerView.Adapter<AdminCusAdapter.RecyclerViewHolder> implements Filterable {

    private ArrayList<Customer> customers;
    private List<Customer> customerList;
    private Context mcontext;
    private Customer customer;
    int layout;
    private List<Customer> customersListFull;
    private CustomerListener mListener;
    Profile userProfile;

    public AdminCusAdapter(Context context, CustomerListener listener, ArrayList<Customer> customers) {
        this.mListener = listener;
        this.customers = customers;
        this.mcontext = context;
    }


    public AdminCusAdapter(ArrayList<Customer> customers, Context mcontext) {
        this.customers = customers;
        this.mcontext = mcontext;
    }
    public AdminCusAdapter(Context mcontext, int layout, Profile userProfile1) {
        this.layout = layout;
        this.mcontext = mcontext;
        this.userProfile = userProfile1;
    }
    public AdminCusAdapter(Context mcontext, int layout, ArrayList<Customer> customers1) {
        this.layout = layout;
        this.mcontext = mcontext;
        this.customers = customers1;
    }

    // Constructor
    public AdminCusAdapter() {
        customers = new ArrayList<Customer>();
    }

    public AdminCusAdapter(Context customerListActivity, ArrayList<Customer> customers) {
        this.mcontext = customerListActivity;
        this.customers = customers;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_row, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    //@Override
    public void addCustomer(Customer customer) {

    }

    @Override
    public Filter getFilter() {
        return customerFilter;
    }
    private Filter customerFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Customer> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(customersListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Customer item : customersListFull) {
                    if (item.getCusFirstName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            customerList.clear();
            customerList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mIdView;
        public final ImageView profileImg;
        public final TextView txtCustomerName;
        public final TextView txtPhoneNo;
        public final TextView office4;
        public final TextView txtDateJoined;
        public final TextView packages;
        public final TextView savings;
        public final TextView address;
        public final TextView dob;
        public final TextView ninName;
        public final TextView gender;
        public  Customer customer;



        public RecyclerViewHolder(@NonNull View view) {
            super(view);


            mIdView = (TextView) view.findViewById(R.id.custID3);
            //mContentView = (TextView) view.findViewById(R.id.customer_content);
            //Customer customer = getItem(position);
            txtCustomerName = view.findViewById(R.id.name_customer2);
            profileImg = view.findViewById(R.id.customer_image4);
            ninName = view.findViewById(R.id.ninName4);
            txtPhoneNo = view.findViewById(R.id.customer_phone4);
            address = view.findViewById(R.id.customer_address4);
            dob = view.findViewById(R.id.customer_dob4);
            gender = view.findViewById(R.id.customer_gender4);
            office4 = view.findViewById(R.id.customer_office4);
            txtDateJoined = view.findViewById(R.id.customer_date_join4);
            packages = view.findViewById(R.id.package_count4);
            savings = view.findViewById(R.id.savings_count4);



            //return convertView;

        }
        public void clearCustomers() {
            customers.clear();
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + txtCustomerName.getText() + "'";
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(customer);
            }
        }
    }
    public interface CustomerListener {
        void onItemClick(Customer item);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder viewHolder, final int position) {
        if (customers.size() <= position) {
            return;
        }
        final Customer customer = customers.get(position);
        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        //viewHolder.parent.setBackgroundColor(currentColor);
        viewHolder.office4.setText(MessageFormat.format("Office:{0}", customer.getCusOfficeBranch()));
        viewHolder.txtCustomerName.setText(MessageFormat.format("{0}{1}", customer.getCusFirstName(), customer.getCusSurname()));
        viewHolder.txtPhoneNo.setText(MessageFormat.format("Phone:{0}", customer.getCusPhoneNumber()));
        viewHolder.address.setText(String.valueOf("Address:"+customer.getCusAddress()));
        viewHolder.dob.setText(MessageFormat.format("DOB:{0}", customer.getCusDob()));
        viewHolder.gender.setText(MessageFormat.format("Gender:{0}", customer.getCusGender()));
        viewHolder.packages.setText(MessageFormat.format("No of Packs:{0}", customer.getCusSkyLightPackage().getCount()));
        viewHolder.txtDateJoined.setText(MessageFormat.format("DateJoined: {0}", customer.getCusDateJoined()));
        viewHolder.savings.setText(MessageFormat.format("Savings: {0}", customer.getCusDailyReports().size()));
        viewHolder.ninName.setText(MessageFormat.format("NinName: {0}", customer.getCustomerNin()));
        viewHolder.profileImg.setImageResource(Integer.parseInt(customer.getCusProfilePicture()));


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
}
