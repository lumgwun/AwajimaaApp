package com.skylightapp.Bookings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.blongho.country_data.Currency;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.R;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.ArrayList;

public class TripBookingAdapter extends RecyclerView.Adapter<TripBookingAdapter.RecyclerViewHolder> {

    private ArrayList<TripBooking> bookings;
    private Context mcontext;
    int resources;
    FragmentActivity activity;
    private OnTBClickListener listener;
    private int mNumberItems = 0;
    private ArrayList<TripBooking> mAccountList;
    private ArrayList<String> ninList;
    private TripBooking recyclerData;
    private Currency currency;
    private String symbol, tripBookingType;

    public TripBookingAdapter(ArrayList<TripBooking> recyclerDataArrayList, Context mcontext) {
        this.bookings = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    public TripBookingAdapter(Context context, int resources, ArrayList<TripBooking> bookings) {
        this.bookings = bookings;
        this.mcontext = context;
        this.resources = resources;

    }
    public void setWhenClickListener(OnTBClickListener listener){
        this.listener = listener;
    }
    public TripBookingAdapter(Context context, ArrayList<TripBooking> bookings) {
        this.bookings = bookings;
        this.mcontext = context;

    }
    public TripBookingAdapter(OnTBClickListener itemClickListener) {
        listener = itemClickListener;
    }
    public void setTripBookingList(ArrayList<TripBooking> accountList) {
        mAccountList = accountList;
        mNumberItems = mAccountList.size();
        notifyDataSetChanged();
    }

    public TripBooking getAccount(int i) {
        return mAccountList.get(i);
    }

    public TripBookingAdapter(FragmentActivity activity, ArrayList<TripBooking> bookings) {
        this.bookings = bookings;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        boolean shouldAttachToParentImmediately = false;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_booking_row, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        recyclerData = bookings.get(position);
        ninList= new ArrayList<>();
        if(recyclerData !=null){
            currency= recyclerData.getTripBCurrency();
            if(currency !=null){
                symbol=currency.getCode();
            }
            ninList=recyclerData.getNinList();
            tripBookingType =recyclerData.getTripBTypeOfService();

            holder.txtTripBID.setText(MessageFormat.format("ID:{0}/{1}", recyclerData.getTripBID(), recyclerData.getTripBTripID()));
            holder.txtTripBAmount.setText(MessageFormat.format("Amount:{0}{1}", recyclerData.getTripBCurStrng(), recyclerData.getTripBAmount()));
            holder.txtTripBStatus.setText(MessageFormat.format("Status:{0}", recyclerData.getTripBStatus()));
            holder.txtTripBTye.setText(MessageFormat.format("Type{0}", recyclerData.getTripBTypeOfService()));
            holder.txtTripBFrom.setText(MessageFormat.format("From:{0}", String.valueOf(recyclerData.getTripBFrom())));
            holder.txtTripBOffice.setText(MessageFormat.format("Office:{0}", String.valueOf(recyclerData.getTripBOffice())));
            holder.txtTripBDate.setText(MessageFormat.format("Date:{0}", String.valueOf(recyclerData.getTripBTime())));
            holder.txtTripBSlots.setText(MessageFormat.format("Slots:{0}", String.valueOf(recyclerData.getTripBSlots())));


            StringBuilder output = new StringBuilder();
            if(ninList !=null){
                for(int i = 0; i < ninList.size(); i++) {
                    if (i + 1 != ninList.size()) {
                        for (String string : ninList) {
                            string= ninList.get(i);
                            string = string.replace("\"", "");

                            output.append(string).append("\n");
                        }
                    }
                }

            }



            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.txtTripBNINs.setText(output.toString());

                }
            });
            if(tripBookingType !=null){
                if(tripBookingType.equalsIgnoreCase("Boat")){
                    Glide.with(holder.imgIcon).load(R.drawable.boat33)
                            .into(holder.imgIcon);
                }
                if(tripBookingType.equalsIgnoreCase("Train")){
                    Glide.with(holder.imgIcon).load(R.drawable.train)
                            .into(holder.imgIcon);
                }
                if(tripBookingType.equalsIgnoreCase("Taxi")){
                    Glide.with(holder.imgIcon).load(R.drawable.ic_taxi33)
                            .into(holder.imgIcon);
                }
                if(tripBookingType.equalsIgnoreCase("Jet")){
                    Glide.with(holder.imgIcon).load(R.drawable.jet_private)
                            .into(holder.imgIcon);
                }
            }
        }



    }
    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return (null != bookings ? bookings.size() : 0);
    }

    public  class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AppCompatTextView txtTripBID,txtTripBAmount,txtTripBSlots,txtTripBNINs,txtTripBOffice,txtTripBDate, txtTripBTo, txtTripBStatus, txtTripBTye, txtTripBFrom;
        private final BezelImageView imgIcon;
        private MaterialCardView cardView;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTripBID = itemView.findViewById(R.id.tb_Id);
            txtTripBStatus = itemView.findViewById(R.id.tb_Status);
            txtTripBFrom = itemView.findViewById(R.id.tb_from);
            txtTripBTo = itemView.findViewById(R.id.tb_To);
            txtTripBAmount = itemView.findViewById(R.id.tb_Amt);
            txtTripBTye = itemView.findViewById(R.id.tb_Type);
            txtTripBOffice = itemView.findViewById(R.id.tb_Office);
            imgIcon = itemView.findViewById(R.id.img_triB_Icon);
            txtTripBDate = itemView.findViewById(R.id.tb_date);
            cardView = itemView.findViewById(R.id.b_card_Nin);
            txtTripBSlots = itemView.findViewById(R.id.tb_slots);
            txtTripBNINs = itemView.findViewById(R.id.tb_slots_NIN);

            itemView.setOnClickListener(this);


        }
        void bind(TripBooking tripBooking) {
            txtTripBID.setText(tripBooking.getTripBID()+tripBooking.getTripBTripID());
            txtTripBStatus.setText(tripBooking.getTripBStatus());
            txtTripBTo.setText(tripBooking.getTripBDest());
        }
        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            listener.onListItemClick(clickedPosition);
        }
    }
    public interface OnTBClickListener {
        void onTriBClicked(TripBooking tripBooking);
        void onListItemClick(int index);
    }
}
