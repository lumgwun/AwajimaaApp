package com.skylightapp.RealEstate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Awards.Award;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PropertyListAdapter extends RecyclerView.Adapter<PropertyListAdapter.MyViewHolder> implements Filterable {
    Context mContext;
    PropertyListener mListener;
    String prefix = "";
    private List<Properties> propertiesList;
    private ArrayList<Properties> propertiesArrayList;
    private Award award;
    Profile userProfile;
    long profileID, propertyUserID;

    public PropertyListAdapter(Context context, ArrayList<Properties> propertiesArrayList) {
        this.propertiesArrayList = propertiesArrayList;
        this.mContext = context;

    }

    public PropertyListAdapter(ArrayList<Properties> propertiesArrayList) {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prop_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public int getItemCount() {
        return (null != propertiesArrayList ? propertiesArrayList.size() : 0);
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setPaymentList(ArrayList<? extends Properties> properties) {
        propertiesArrayList.clear();
        propertiesArrayList.addAll(properties);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addLast(Properties properties) {
        propertiesArrayList.add(properties);
        notifyDataSetChanged();
    }
    @Override
    public Filter getFilter() {
        return propertyFilter;
    }
    private Filter propertyFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Properties> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(propertiesArrayList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Properties item : propertiesArrayList) {
                    if (item.getTittleOfProperty().toLowerCase().contains(filterPattern)) {
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
            propertiesArrayList.clear();
            propertiesArrayList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public interface PropertyListener{
        void onItemClick(Properties properties);
    }
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, final int position) {
        if (propertiesArrayList.size() <= position) {
            return;
        }
        final Properties properties = propertiesArrayList.get(position);
        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        viewHolder.tittle.setText(MessageFormat.format("Tittle:{0}", properties.getTittleOfProperty()));
        viewHolder.type.setText(MessageFormat.format("Type:{0}", properties.getPropertyType()));
        viewHolder.description.setText(MessageFormat.format("Description:{0}", properties.getDescription()));
        viewHolder.price.setText(MessageFormat.format("Price:{0}", properties.getPrice()));
        viewHolder.capacity.setText(MessageFormat.format("Capacity:{0}", properties.getPropertyCapacity()));
        viewHolder.priceDuration.setText(MessageFormat.format("Duration:{0}", properties.getPriceDuration()));
        viewHolder.propStatus.setText(MessageFormat.format("Status:{0}", properties.getStatus()));

        viewHolder.town.setText(MessageFormat.format("Town:{0}", properties.getTown()));
        viewHolder.state.setText(MessageFormat.format("State:{0}", properties.getPropertyState()));
        viewHolder.country.setText(MessageFormat.format("Country:{0}", properties.getPropertyCountry()));
        viewHolder.lga.setText(MessageFormat.format("LGA:{0}", properties.getLga()));



    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        AppCompatTextView tittle;
        AppCompatTextView type;
        AppCompatTextView description;
        AppCompatTextView price;
        AppCompatTextView priceDuration;
        AppCompatTextView capacity,lga,state,country,town;
        AppCompatTextView propStatus;
        AppCompatButton btnViewMore;
        AppCompatButton btnViewOnMap;
        AppCompatButton btnViewPictures;
        AppCompatButton btnBookSeeing;
        AppCompatImageView inmageHolder;
        View parent;
        Properties properties;


        MyViewHolder(View itemView) {
            super(itemView);
            tittle =  itemView.findViewById(R.id.propLabelTittle);
            type =  itemView.findViewById(R.id.propType3);
            price =  itemView.findViewById(R.id.propPrice);
            priceDuration =  itemView.findViewById(R.id.priceDurationProp);
            description =  itemView.findViewById(R.id.propDescriptionLabel);
            capacity =  itemView.findViewById(R.id.propCapacity);
            town =  itemView.findViewById(R.id.propTown);
            lga =  itemView.findViewById(R.id.propLGA);
            state =  itemView.findViewById(R.id.propState);
            country =  itemView.findViewById(R.id.propCountry);
            propStatus =  itemView.findViewById(R.id.propStatus4);

            btnViewMore =  itemView.findViewById(R.id.btnViewMore);
            btnViewOnMap =  itemView.findViewById(R.id.btnViewOnMap);
            btnBookSeeing =  itemView.findViewById(R.id.btnBookSeeing);
            btnViewPictures =  itemView.findViewById(R.id.btnViewPictures);


            //return itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(userProfile !=null){
                        profileID=userProfile.getPID();
                        propertyUserID = properties.getProfile().getPID();
                        if(properties !=null){
                            if(propertyUserID ==profileID){
                                goDialog(properties);
                            }

                        }
                    }

                }
            });
        }



        private void  goDialog(Properties properties){
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext.getApplicationContext());
            builder.setTitle("Choose Award Action");
            builder.setItems(new CharSequence[]
                            {"delete", "Update"},
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            switch (which) {
                                case 0:
                                    Toast.makeText(mContext.getApplicationContext(), "Delete option, selected ", Toast.LENGTH_SHORT).show();
                                    deleteFromDB(properties);
                                    break;
                                case 1:
                                    Toast.makeText(mContext.getApplicationContext(), "Delete Choice, made", Toast.LENGTH_SHORT).show();
                                    updateInDB(properties);
                                    break;
                            }
                        }
                    })
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {


                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            builder.create().show();

        }

        @Override
        public void onClick(View view) {

            if (mListener != null) {
                mListener.onItemClick(properties);
            }

        }

        public void setData(String propertyTittle) {

        }
    }
    private  void deleteFromDB(Properties properties){
        DBHelper dbHelper=new DBHelper(mContext.getApplicationContext());
        long profileID = 0, propertyID = 0;
        Profile userProfile= new Profile();
        //properties = new Properties();
        if(userProfile !=null){
            profileID=userProfile.getPID();
            propertyID =properties.getPropertyID();
            if(profileID==properties.getProfile().getPID()){
                dbHelper.deleteProperty(String.valueOf(propertyID));

            }
        }


    }
    private  void updateInDB(Properties properties){
        DBHelper dbHelper=new DBHelper(mContext.getApplicationContext());
        long profileID = 0, propertyProfileID,propertyID = 0;
        Profile userProfile= new Profile();
        propertyID=properties.getPropertyID();
        if(userProfile !=null){
            profileID=userProfile.getPID();
            propertyProfileID =properties.getProfile().getPID();
            if(propertyProfileID ==profileID){
                Bundle bundle = new Bundle();
                bundle.putLong("ProfileID", profileID);
                bundle.putParcelable("Properties",properties);
                bundle.putLong("PROPERTY_ID", propertyID);
                Toast.makeText(mContext.getApplicationContext(), "On the way to property updating", Toast.LENGTH_SHORT).show();
                Intent updateIntent = new Intent(mContext.getApplicationContext(), PropertyUpdateAct.class);
                updateIntent.putExtras(bundle);
                updateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }
        }


    }
}
