package com.skylightapp.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.skylightapp.Admins.AdminCusActionView;
import com.skylightapp.Admins.AdminPackageActivity;
import com.skylightapp.Admins.CusPackHistoryAct;
import com.skylightapp.Admins.ImportDateTab;
import com.skylightapp.Admins.SODueDateListAct;
import com.skylightapp.Admins.ProfilePackHistoryAct;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.SkyLightPackModel;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Interfaces.SkylightPackageListener;
import com.skylightapp.PayNowActivity;
import com.skylightapp.R;
import com.skylightapp.SkyLightPackageActivity;
import com.skylightapp.SuperAdmin.SuperAdminCountAct;
import com.skylightapp.Tellers.CustomerDetailAct;
import com.skylightapp.Tellers.MyProfilePackList;

import java.text.MessageFormat;
import java.util.ArrayList;

import static java.lang.String.valueOf;


public class SkyLightPackageAdapter extends RecyclerView.Adapter<SkyLightPackageAdapter.ViewHolder> implements ListAdapter {
    Context context;
    private ArrayList<SkyLightPackage> packageList;
    private ArrayList<SkyLightPackModel> skyLightPackage_2s;
    private OnItemsClickListener listener;
    SkylightPackageListener mListener;
    private Customer customer;
    private GestureDetector gestureDetector;


    public SkyLightPackageAdapter(Context applicationContext) {
        this.context = applicationContext;
    }

    public SkyLightPackageAdapter(ArrayList<SkyLightPackage> recyclerDataArrayList, SkyLightPackageActivity skyLightPackageActivity) {
        this.packageList = recyclerDataArrayList;

    }

    public SkyLightPackageAdapter(Context context, ArrayList<SkyLightPackModel> skyLightPackage_2s) {
        this.skyLightPackage_2s = skyLightPackage_2s;
        this.context=context;

    }

    public SkyLightPackageAdapter(SODueDateListAct SODueDateListAct, ArrayList<SkyLightPackage> skyLightPackageArrayList) {
        this.context= SODueDateListAct;
        this.packageList=skyLightPackageArrayList;

    }

    public SkyLightPackageAdapter(AdminCusActionView adminCusActionView, ArrayList<SkyLightPackage> skyLightPackages) {
        this.context=adminCusActionView;
        this.packageList=skyLightPackages;

    }

    public SkyLightPackageAdapter(AdminPackageActivity adminPackageActivity, ArrayList<SkyLightPackage> skyLightPackages) {
        this.context=adminPackageActivity;
        this.packageList=skyLightPackages;

    }

    public SkyLightPackageAdapter(CusPackHistoryAct cusPackHistoryAct, ArrayList<SkyLightPackage> skyLightPackages) {
        this.context=cusPackHistoryAct;
        this.packageList=skyLightPackages;

    }

    public SkyLightPackageAdapter(ProfilePackHistoryAct profilePackHistoryAct, ArrayList<SkyLightPackage> skyLightPackages) {
        this.context=profilePackHistoryAct;
        this.packageList=skyLightPackages;

    }

    public SkyLightPackageAdapter(MyProfilePackList myProfilePackList, ArrayList<SkyLightPackage> skyLightPackages) {
        this.context=myProfilePackList;
        this.packageList=skyLightPackages;

    }

    public SkyLightPackageAdapter(CustomerDetailAct customerDetailAct, ArrayList<SkyLightPackage> skyLightPackageArrayList) {
        this.context=customerDetailAct;
        this.packageList=skyLightPackageArrayList;

    }

    public SkyLightPackageAdapter(ImportDateTab importDateTab, ArrayList<SkyLightPackage> skyLightPackages) {
        this.context=importDateTab;
        this.packageList=skyLightPackages;

    }

    public SkyLightPackageAdapter(SuperAdminCountAct superAdminCountAct, ArrayList<SkyLightPackage> skyLightPackages) {
        this.context=superAdminCountAct;
        this.packageList=skyLightPackages;

    }


    public void setWhenClickListener(OnItemsClickListener listener){
        this.listener = listener;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.package_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final SkyLightPackModel skyLightPackModel = skyLightPackage_2s.get(position);
        String tittle=skyLightPackModel.getpMItemName();
        String type=skyLightPackModel.getpMType();
        int duration=skyLightPackModel.getpMDuration();
        double amount=skyLightPackModel.getpMPrice();
        double grandTotal=duration*amount;
        int id=skyLightPackModel.getpModeID();
        holder.itemName.setText(MessageFormat.format("Item Name.:{0}", skyLightPackModel.getpMItemName()));
        holder.description.setText(MessageFormat.format("Desc.:{0}", skyLightPackModel.getpMdesc()));
        holder.price.setText(MessageFormat.format("Price.:N{0}", skyLightPackModel.getpMPrice()));
        holder.duration1.setText(MessageFormat.format("Duration.:{0}", skyLightPackModel.getpMDuration()));
        Glide.with(holder.itemImage).load(skyLightPackModel.getpMItemImage()).fitCenter().into(holder.itemImage);

        //holder.itemImage.setImageResource(R.drawable.savings);
        final SkyLightPackage skyLightPackage = new SkyLightPackage(id,0,tittle,amount,grandTotal,type,duration);


        holder.singleItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(skyLightPackage);
                }
            }
        });
        holder.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle paymentBundle = new Bundle();
                if(skyLightPackage !=null){
                    customer=skyLightPackage.getPackageCustomer();
                }
                if(listener != null){
                    listener.onItemClick(skyLightPackage);
                }

            }
        });

    }
    @Override
    public int getItemCount() {
        return (null != packageList ? packageList.size() : 0);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CardView singleItemCardView;
        public TextView singleItemTextView;
        public View mView;
        public Object mItem;
        public TextView itemName;
        public TextView description;
        public TextView price;
        public TextView total;
        public TextView duration1;
        public AppCompatEditText noOfDays;
        public ImageView itemImage;
        public AppCompatButton btnSubmit;
        SkyLightPackage skyLightPackage;
        DBHelper dbHelper;


        // Link up the Main-List items layout components with their respective id
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            singleItemCardView = itemView.findViewById(R.id.singleItemCardView);
            total = itemView.findViewById(R.id.pTotal);
            itemName = itemView.findViewById(R.id.pname);
            price = itemView.findViewById(R.id._daily_price);
            noOfDays = itemView.findViewById(R.id.daysNo);
            duration1 = itemView.findViewById(R.id.duration3);
            btnSubmit = itemView.findViewById(R.id.BSubmitBtn);
            description = itemView.findViewById(R.id.packageDescription);
            itemImage = itemView.findViewById(R.id.ItemImg);
            dbHelper = new DBHelper(itemView.getContext());

        }
        public void setData(int itemImage, String itemName, double price, String description, String duration){
            this.itemName.setText(itemName);
            this.price.setText((int) price);
            this.description.setText(description);
            this.duration1.setText( duration);
            this.itemImage.setImageResource(R.drawable.skylight_logo);
        }

        @Override
        public void onClick(View view) {

            if (mListener != null) {
                mListener.onItemClick(skyLightPackage);
            }

        }
    }

    public interface OnItemsClickListener{
        void onItemClick(SkyLightPackage lightPackage);
    }
}
