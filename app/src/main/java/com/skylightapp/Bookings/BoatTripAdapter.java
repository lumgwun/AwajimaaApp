package com.skylightapp.Bookings;

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

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Classes.Profile;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class BoatTripAdapter extends RecyclerView.Adapter<BoatTripAdapter.RecyclerViewHolder> implements Filterable {

    private ArrayList<BoatTrip> boatTripArrayList;
    private List<BoatTrip> customerList;
    private Context mcontext;
    private BoatTrip customer;
    int layout;
    private List<BoatTrip> boatTripList;
    private BoatTripListener mListener;
    Profile userProfile;

    public BoatTripAdapter(Context context, BoatTripListener listener, ArrayList<BoatTrip> boatTripArrayList) {
        this.mListener = listener;
        this.boatTripArrayList = boatTripArrayList;
        this.mcontext = context;
    }


    public BoatTripAdapter(ArrayList<BoatTrip> boatTripArrayList, Context mcontext) {
        this.boatTripArrayList = boatTripArrayList;
        this.mcontext = mcontext;
    }
    public BoatTripAdapter(Context mcontext, int layout, Profile userProfile1) {
        this.layout = layout;
        this.mcontext = mcontext;
        this.userProfile = userProfile1;
    }
    public BoatTripAdapter(Context mcontext, int layout, ArrayList<BoatTrip> customers1) {
        this.layout = layout;
        this.mcontext = mcontext;
        this.boatTripArrayList = customers1;
    }

    // Constructor
    public BoatTripAdapter() {
        boatTripArrayList = new ArrayList<BoatTrip>();
    }

    public BoatTripAdapter(Context customerListActivity, ArrayList<BoatTrip> boatTripArrayList) {
        this.mcontext = customerListActivity;
        this.boatTripArrayList = boatTripArrayList;
    }

    @NonNull
    @Override
    public BoatTripAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.boat_trip_item, parent, false);
        return new BoatTripAdapter.RecyclerViewHolder(itemView);
    }

    //@Override
    public void addCustomer(BoatTrip customer) {

    }

    @Override
    public Filter getFilter() {
        return boatTripFilter;
    }
    private Filter boatTripFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<BoatTrip> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(boatTripList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (BoatTrip item : boatTripList) {
                    if (item.getBtFinalDestination().toLowerCase().contains(filterPattern)) {
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
        public final AppCompatTextView txtTripID, txtStatus, txtSitsRemaining, txtTripFrom, txtTripDate, txtState, txtSitsTotal, txtChildrenAmt, txtEndDate, txtTripAmount, txtTo;
        public final ImageView profileImg;
        public BoatTrip boatTrip;


        public RecyclerViewHolder(@NonNull View view) {
            super(view);
            txtTo = view.findViewById(R.id.name_dest);
            profileImg = view.findViewById(R.id.mImga);
            txtTripFrom = view.findViewById(R.id.trip_fromASD);
            txtTripID =  view.findViewById(R.id.td_trip);
            txtTripAmount = view.findViewById(R.id.amt_triper);
            txtSitsTotal = view.findViewById(R.id.trip_total_Sit);
            txtTripDate = view.findViewById(R.id.trip_dated);
            txtSitsRemaining = view.findViewById(R.id.sits_remaining);
            txtState = view.findViewById(R.id.trip_state);
            txtEndDate = view.findViewById(R.id.trip_end_date);
            txtChildrenAmt = view.findViewById(R.id.trip_amt_child);
            txtStatus = view.findViewById(R.id.trip_status);

            //mContentView = (TextView) view.findViewById(R.id.customer_content);
            //Customer customer = getItem(position);














            //return convertView;

        }
        public void clearCustomers() {
            boatTripArrayList.clear();
            notifyDataSetChanged();
        }


        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(boatTrip);
            }
        }
    }
    public interface BoatTripListener {
        void onItemClick(BoatTrip item);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder viewHolder, final int position) {
        if (boatTripArrayList.size() <= position) {
            return;
        }
        BoatTrip boatTrip = boatTripArrayList.get(position);
        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        //viewHolder.parent.setBackgroundColor(currentColor);
        if(boatTrip !=null){
            viewHolder.txtStatus.setText(MessageFormat.format("Status:{0}", boatTrip.getBtStatus()));
            viewHolder.txtTo.setText(MessageFormat.format("Dest:{0}", boatTrip.getBtFinalDestination()));
            viewHolder.txtTripAmount.setText(MessageFormat.format("Adult:{0}", boatTrip.getBtAmountForAdult()));
            viewHolder.txtSitsTotal.setText(String.valueOf("Total Sits:"+boatTrip.getBoatTripNoOfSits()));
            viewHolder.txtTripDate.setText(MessageFormat.format("Trip Date:{0}", boatTrip.getBtDate()));
            viewHolder.txtSitsRemaining.setText(MessageFormat.format("Rem Sit:{0}", boatTrip.getBtNoOfSitRem()));
            viewHolder.txtState.setText(MessageFormat.format("State:{0}", boatTrip.getBtState()));
            viewHolder.txtEndDate.setText(MessageFormat.format("End Date: {0}", boatTrip.getBtEndTime()));
            viewHolder.txtChildrenAmt.setText(MessageFormat.format("Children: {0}", boatTrip.getBtAmountForChildren()));
            viewHolder.txtTripFrom.setText(MessageFormat.format("Takeoff Point: {0}", boatTrip.getBtLoadingPoint()));

        }




    }

    //@Override
    public void onViewRecycled(RecyclerViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }



    public ArrayList<BoatTrip> getBoatTrips() {
        return boatTripArrayList;
    }

    public void setData(ArrayList<BoatTrip> customers) {
        this.boatTripArrayList = customers;

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
        Collections.sort(boatTripArrayList, new Comparator<BoatTrip>() {
            @Override
            public int compare(BoatTrip b1, BoatTrip b2) {
                return b1.getBtFinalDestination().compareTo(b2.getBtFinalDestination());
            }
        });
    }
    private void sortCustomersByPhoneNumber() {
        Collections.sort(boatTripArrayList, new Comparator<BoatTrip>() {
            @Override
            public int compare(BoatTrip b1, BoatTrip b2) {
                return b1.getBtFinalDestination().compareTo(b2.getBtFinalDestination());
            }
        });
    }
    private void sortCustomersByEmail() {
        Collections.sort(boatTripArrayList, new Comparator<BoatTrip>() {
            @Override
            public int compare(BoatTrip b1, BoatTrip b2) {
                return b1.getBtFinalDestination().compareTo(b2.getBtFinalDestination());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != boatTripArrayList ? boatTripArrayList.size() : 0);
    }

    public ArrayList<BoatTrip> getData() {
        return boatTripArrayList;
    }
}
