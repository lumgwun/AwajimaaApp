package com.skylightapp.Classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.skylightapp.Adapters.CustomerAdapter;
import com.skylightapp.InsuranceUsersAct;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.BizSuperAdminAllView;
import com.skylightapp.Tellers.MyCusList;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CusGenAdapter extends RecyclerView.Adapter<CusGenAdapter.RecyclerViewHolder> {

    private ArrayList<Customer> customers;
    private List<Customer> customerList;
    private Context mcontext;
    private Profile userProfile;
    private CustomerAdapter.CustomerListener mListener;
    int layout;


    public CusGenAdapter(ArrayList<Customer> customers, Context mcontext) {
        this.customers = customers;
        this.mcontext = mcontext;
    }
    public CusGenAdapter(Context mcontext, int layout, Profile userProfile1) {
        this.layout = layout;
        this.mcontext = mcontext;
        this.userProfile = userProfile1;
    }
    public CusGenAdapter(Context mcontext, int layout, ArrayList<Customer> customers1) {
        this.layout = layout;
        this.mcontext = mcontext;
        this.customers = customers1;
    }

    // Constructor
    public CusGenAdapter() {
        customers = new ArrayList<Customer>();
    }

    public CusGenAdapter(MyCusList myCusList, ArrayList<Customer> customerArrayList) {
        this.mcontext = myCusList;
        this.customers = customerArrayList;

    }
    public CusGenAdapter(MyCusList myCusList, CustomerAdapter.CustomerListener listener, ArrayList<Customer> customerArrayList) {
        this.mcontext = myCusList;
        this.customers = customerArrayList;
        this.mListener = listener;

    }

    public CusGenAdapter(BizSuperAdminAllView bizSuperAdminAllView, ArrayList<Customer> customersNewToday) {
        this.mcontext = bizSuperAdminAllView;
        this.customers = customersNewToday;
    }

    public CusGenAdapter(InsuranceUsersAct insuranceUsersAct, ArrayList<Customer> customerArrayList) {
        this.customers = customerArrayList;
        this.mcontext = insuranceUsersAct;
    }

    @NonNull
    @Override
    public CusGenAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ins_list_view, parent, false);
        return new CusGenAdapter.RecyclerViewHolder(itemView);
    }

    //@Override
    public void addCustomer(Customer customer) {

    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public final AppCompatTextView txtCustomerName;
        public final AppCompatTextView txtPhoneNo;
        public final AppCompatTextView office;
        public final AppCompatTextView dob;
        public final AppCompatTextView gender;
        public final AppCompatButton btn_Send_Message;
        public final AppCompatButton addPackage;
        public CircularImageView photo;



        public RecyclerViewHolder(@NonNull View view) {
            super(view);
            txtCustomerName = view.findViewById(R.id.name_insurance);
            photo = view.findViewById(R.id.ins_photo_image);
            txtPhoneNo = view.findViewById(R.id.c_phone_ins);
            dob = view.findViewById(R.id.ins_dob);
            office = view.findViewById(R.id.ins_cus_office);
            btn_Send_Message = view.findViewById(R.id.ins_send_message);
            addPackage = view.findViewById(R.id.ins_moreBtn);
            gender = view.findViewById(R.id.gender_ins);


            //return convertView;

        }
        public void clearCustomers() {
            customers.clear();
            notifyDataSetChanged();
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final CusGenAdapter.RecyclerViewHolder viewHolder, final int position) {
        if (customers.size() <= position) {
            return;
        }
        final Customer customer = customers.get(position);
        if(customer !=null){
            viewHolder.office.setText(MessageFormat.format("", customer.getCusOfficeBranch()));
            viewHolder.txtCustomerName.setText(MessageFormat.format("{0}{1}", customer.getCusFirstName(), customer.getCusSurname()));
            viewHolder.txtPhoneNo.setText(MessageFormat.format("", customer.getCusPhoneNumber()));
            viewHolder.dob.setText(MessageFormat.format("", customer.getCusDob()));
            viewHolder.gender.setText(MessageFormat.format("", customer.getCusGender()));

            Glide.with(mcontext)
                    .load(customer.getCusProfilePicture())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.drawable.ic_alert)
                    .skipMemoryCache(true)
                    .fitCenter()
                    .centerCrop()
                    .into(viewHolder.photo);

        }

    }

    //@Override
    public void onViewRecycled(CusGenAdapter.RecyclerViewHolder holder) {
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
