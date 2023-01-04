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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.skylightapp.Classes.Profile;
import com.skylightapp.R;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.RecyclerViewHolder> implements Filterable {

    private ArrayList<Trip> tripArrayList;
    private List<Trip> customerList;
    private Context mcontext;
    private Trip customer;
    int layout;
    private List<Trip> tripList;
    private BoatTripListener mListener;
    Profile userProfile;
    private String type;

    public TripAdapter(Context context, BoatTripListener listener, ArrayList<Trip> tripArrayList) {
        this.mListener = listener;
        this.tripArrayList = tripArrayList;
        this.mcontext = context;
    }


    public TripAdapter(ArrayList<Trip> tripArrayList, Context mcontext) {
        this.tripArrayList = tripArrayList;
        this.mcontext = mcontext;
    }
    public TripAdapter(Context mcontext, int layout, Profile userProfile1) {
        this.layout = layout;
        this.mcontext = mcontext;
        this.userProfile = userProfile1;
    }
    public TripAdapter(Context mcontext, int layout, ArrayList<Trip> customers1) {
        this.layout = layout;
        this.mcontext = mcontext;
        this.tripArrayList = customers1;
    }

    // Constructor
    public TripAdapter() {
        tripArrayList = new ArrayList<Trip>();
    }

    public TripAdapter(Context customerListActivity, ArrayList<Trip> tripArrayList) {
        this.mcontext = customerListActivity;
        this.tripArrayList = tripArrayList;
    }

    @NonNull
    @Override
    public TripAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.boat_trip_item, parent, false);
        return new TripAdapter.RecyclerViewHolder(itemView);
    }

    //@Override
    public void addCustomer(Trip customer) {

    }

    @Override
    public Filter getFilter() {
        return boatTripFilter;
    }
    private Filter boatTripFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Trip> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(tripList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Trip item : tripList) {
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
        public final ImageView tImg;
        public Trip trip;


        public RecyclerViewHolder(@NonNull View view) {
            super(view);
            txtTo = view.findViewById(R.id.name_dest);
            tImg = view.findViewById(R.id.mImga);
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
            tripArrayList.clear();
            notifyDataSetChanged();
        }


        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(trip);
            }
        }
    }
    public interface BoatTripListener {
        void onItemClick(Trip item);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder viewHolder, final int position) {
        if (tripArrayList.size() <= position) {
            return;
        }
        Trip trip = tripArrayList.get(position);
        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        //viewHolder.parent.setBackgroundColor(currentColor);
        if(trip !=null){
            type=trip.getTripType();
            if(type !=null){
                if(type.equalsIgnoreCase("Boat")){
                   // Picasso.get().load(type).into(viewHolder.tImg);
                    Glide.with(mcontext)
                            .load(R.drawable.boat33)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .error(R.drawable.ic_alert)
                            .skipMemoryCache(true)
                            .fitCenter()
                            .centerCrop()
                            .into(viewHolder.tImg);

                }
                if(type.equalsIgnoreCase("Train")){
                    // Picasso.get().load(type).into(viewHolder.tImg);
                    Glide.with(mcontext)
                            .load(R.drawable.train)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .error(R.drawable.ic_alert)
                            .skipMemoryCache(true)
                            .fitCenter()
                            .centerCrop()
                            .into(viewHolder.tImg);

                }
                /*if(type.equalsIgnoreCase("Train")){
                    Picasso.get().load(R.drawable.train).into(viewHolder.tImg);

                }*/
                if(type.equalsIgnoreCase("Taxi")){
                    // Picasso.get().load(type).into(viewHolder.tImg);
                    Glide.with(mcontext)
                            .load(R.drawable.ic_taxi33)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .error(R.drawable.ic_alert)
                            .skipMemoryCache(true)
                            .fitCenter()
                            .centerCrop()
                            .into(viewHolder.tImg);

                }
            }

            viewHolder.txtStatus.setText(MessageFormat.format("Status:{0}", trip.getBtStatus()));
            viewHolder.txtTo.setText(MessageFormat.format("Dest:{0}", trip.getBtFinalDestination()));
            viewHolder.txtTripAmount.setText(MessageFormat.format("Adult:{0}", trip.getBtAmountForAdult()));
            viewHolder.txtSitsTotal.setText(String.valueOf("Total Sits:"+ trip.getaTripNoOfSits()));
            viewHolder.txtTripDate.setText(MessageFormat.format("Trip Date:{0}", trip.getBtDate()));
            viewHolder.txtSitsRemaining.setText(MessageFormat.format("Rem Sit:{0}", trip.getBtNoOfSitRem()));
            viewHolder.txtState.setText(MessageFormat.format("State:{0}", trip.getBtState()));
            viewHolder.txtEndDate.setText(MessageFormat.format("End Date: {0}", trip.getBtEndTime()));
            viewHolder.txtChildrenAmt.setText(MessageFormat.format("Children: {0}", trip.getBtAmountForChildren()));
            viewHolder.txtTripFrom.setText(MessageFormat.format("Takeoff Point: {0}", trip.getBtLoadingPointLatLngStrng()));

        }




    }

    //@Override
    public void onViewRecycled(RecyclerViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }



    public ArrayList<Trip> getBoatTrips() {
        return tripArrayList;
    }

    public void setData(ArrayList<Trip> customers) {
        this.tripArrayList = customers;

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
        Collections.sort(tripArrayList, new Comparator<Trip>() {
            @Override
            public int compare(Trip b1, Trip b2) {
                return b1.getBtFinalDestination().compareTo(b2.getBtFinalDestination());
            }
        });
    }
    private void sortCustomersByPhoneNumber() {
        Collections.sort(tripArrayList, new Comparator<Trip>() {
            @Override
            public int compare(Trip b1, Trip b2) {
                return b1.getBtFinalDestination().compareTo(b2.getBtFinalDestination());
            }
        });
    }
    private void sortCustomersByEmail() {
        Collections.sort(tripArrayList, new Comparator<Trip>() {
            @Override
            public int compare(Trip b1, Trip b2) {
                return b1.getBtFinalDestination().compareTo(b2.getBtFinalDestination());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != tripArrayList ? tripArrayList.size() : 0);
    }

    public ArrayList<Trip> getData() {
        return tripArrayList;
    }
}
