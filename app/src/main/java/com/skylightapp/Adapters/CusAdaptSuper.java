package com.skylightapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Admins.AdminDrawerActivity;
import com.skylightapp.BlockedUserAct;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Customers.SendCustomerMessage;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.CusByPackAct;
import com.skylightapp.SuperAdmin.SuperAdminCountAct;
import com.skylightapp.Tellers.MyCusList;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CusAdaptSuper extends RecyclerView.Adapter<CusAdaptSuper.RecyclerViewHolder> implements View.OnClickListener, Filterable {

    private ArrayList<Customer> customers;
    private List<Customer> customerList;
    private List<Customer> getCustomerList;
    private Context mContext;
    private Customer customer;
    private CustomerListener mListener;
    int layout;


    public CusAdaptSuper(ArrayList<Customer> customers, Context mContext) {
        this.customers = customers;
        this.mContext = mContext;
    }

    public CusAdaptSuper(Context mContext, int layout, ArrayList<Customer> customers1) {
        this.layout = layout;
        this.mContext = mContext;
        this.customers = customers1;
    }


    public CusAdaptSuper() {
        customers = new ArrayList<Customer>();
    }

    public CusAdaptSuper(MyCusList myCusList, ArrayList<Customer> customerArrayList) {
        this.mContext = myCusList;
        this.customers = customerArrayList;

    }
    public CusAdaptSuper(MyCusList myCusList, CustomerListener listener, ArrayList<Customer> customerArrayList) {
        this.mContext = myCusList;
        this.customers = customerArrayList;
        this.mListener = listener;

    }

    public CusAdaptSuper(SuperAdminCountAct superAdminCountAct, ArrayList<Customer> customersNewToday) {
        this.mContext = superAdminCountAct;
        this.customers = customersNewToday;
    }

    public CusAdaptSuper(CusByPackAct cusByPackAct, ArrayList<Customer> customers) {
        this.mContext = cusByPackAct;
        this.customers = customers;
    }

    @NonNull
    @Override
    public CusAdaptSuper.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cus_row, parent, false);
        return new CusAdaptSuper.RecyclerViewHolder(itemView);
    }

    //@Override
    public void addCustomer(Customer customer) {

    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            mListener.onItemClick(customer);
        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    customerList = getCustomerList;
                } else {
                    List<Customer> filteredList = new ArrayList<>();
                    for (Customer customer : getCustomerList) {
                        if (customer.getCusFirstName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(customer);
                        }
                    }
                    customerList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = customerList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                customerList = (ArrayList<Customer>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtCustomerName;
        public final TextView txtPhoneNo;
        public final TextView cusEmail;
        public final TextView officeBranch;
        public final TextView address;
        public final Button btnUpdateUserType;
        public final Button btnSendMessage;
        private ImageView img_account;



        public RecyclerViewHolder(@NonNull View view) {
            super(view);
            txtCustomerName = view.findViewById(R.id.super_cusTomer);
            txtPhoneNo = view.findViewById(R.id.customer_number);
            cusEmail = view.findViewById(R.id.customer_email);
            address = view.findViewById(R.id.cus_address);
            img_account = view.findViewById(R.id.cus_image);
            officeBranch = view.findViewById(R.id.cus_officeB);
            btnSendMessage = view.findViewById(R.id.sendCusMessage);
            btnUpdateUserType = view.findViewById(R.id.changeUserType);



        }
        public void clearCustomers() {
            customers.clear();
            notifyDataSetChanged();
        }


    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder viewHolder, final int position) {
        if (customers.size() <= position) {
            return;
        }
        final Customer customer = customers.get(position);
        viewHolder.cusEmail.setText(customer.getCusEmailAddress());
        viewHolder.txtCustomerName.setText(MessageFormat.format("{0},{1}", customer.getCusFirstName(), customer.getCusSurname()));
        viewHolder.txtPhoneNo.setText(customer.getCusPhoneNumber());
        viewHolder.address.setText(customer.getCusAddress());
        viewHolder.officeBranch.setText(customer.getCusOfficeBranch());
        viewHolder.btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle messageBundle= new Bundle();
                messageBundle.putParcelable("Customer", customer);
                Intent intent = new Intent(mContext.getApplicationContext(), SendCustomerMessage.class);
                intent.putExtras(messageBundle);
                mContext.startActivity(intent);

            }
        });
        viewHolder.btnUpdateUserType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle updateBundle= new Bundle();
                updateBundle.putParcelable("Customer", customer);
                Intent intent = new Intent(mContext.getApplicationContext(), BlockedUserAct.class);
                intent.putExtras(updateBundle);
                mContext.startActivity(intent);

            }
        });
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

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);

        // Get users sort preference
        if (sharedPref.getString(mContext.getString(R.string.pref_sort_by_key), "0") == "1") {
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
