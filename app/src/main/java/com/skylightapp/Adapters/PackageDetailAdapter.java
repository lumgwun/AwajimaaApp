package com.skylightapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.Classes.Customer;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.Classes.Utils;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;

public class PackageDetailAdapter extends RecyclerView.Adapter<PackageDetailAdapter.RecyclerViewHolder> {


    private ArrayList<MarketBizPackage> marketBizPackages;
    private Context mcontext;
    int resources;
    private OnItemsClickListener listener;

    public PackageDetailAdapter(ArrayList<MarketBizPackage> recyclerDataArrayList, Context mcontext) {
        this.marketBizPackages = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    public PackageDetailAdapter(Context context, int resources, ArrayList<MarketBizPackage> marketBizPackages) {
        this.marketBizPackages = marketBizPackages;
        this.mcontext = context;
        this.resources = resources;

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.package_list_row, parent, false);
        return new RecyclerViewHolder(view);
    }
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements OnItemsClickListener{

        public TextView status,amountRemaining,daysRemaining,packageAmount,customerName,customerID;
        public final BezelImageView ImageIcon;
        private TextView endDate,startDate,profileManager,savedAmount ,grandTotalAmount,packageType,duration,packageID;
        public AppCompatButton btnMore;
        LinearLayoutCompat p_01,p_41;

        public RecyclerViewHolder(@NonNull View convertView) {
            super(convertView);
            ImageIcon = convertView.findViewById(R.id.thumbnail_p);
            //packageID = convertView.findViewById(R.id.package_ID1);
            savedAmount = convertView.findViewById(R.id.package_saved);
            packageType = convertView.findViewById(R.id.package_type3);
            customerName = convertView.findViewById(R.id.package_customer);
            //customerID = convertView.findViewById(R.id.package_user_id);
            profileManager = convertView.findViewById(R.id.package_manager);
            duration = convertView.findViewById(R.id.package_duration1);
            grandTotalAmount = convertView.findViewById(R.id.package_total);
            startDate = convertView.findViewById(R.id.package_start_date1);
            endDate = convertView.findViewById(R.id.endDate3);
            //endDate.setVisibility(View.VISIBLE);
            packageAmount = convertView.findViewById(R.id.package_amount1);
            daysRemaining = convertView.findViewById(R.id.package_days_remaining1);
            //btnMore = convertView.findViewById(R.id.package_more);
            amountRemaining = convertView.findViewById(R.id.package_amount_rem1);
            status = convertView.findViewById(R.id.package_status_3);


        }

        @Override
        public void onItemClick(MarketBizPackage marketBizPackage) {

        }
    }

    @SuppressLint({"ResourceAsColor", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        MarketBizPackage marketBizPackage = marketBizPackages.get(position);
        Customer customer = new Customer();
        holder.packageType.setText(MessageFormat.format("Type:{0}", marketBizPackage.getPackageType() +"-"+ marketBizPackage.getPackageType()));
        holder.amountRemaining.setText(MessageFormat.format("Rem Amount: NGN{0}", String.format("%.2f", marketBizPackage.getPackageAmtRem())));
        holder.grandTotalAmount.setText(MessageFormat.format("Exp Amount: NGN{0}", String.format("%.2f", marketBizPackage.getPackageTotalAmount())));
        holder.packageAmount.setText(MessageFormat.format("Package Amount: NGN{0}", String.format("%.2f", marketBizPackage.getPackageDailyAmount())));
        //holder.packageID.setText(MessageFormat.format("ID:{0}", String.valueOf(marketBizPackage.getPackageId())));
        holder.status.setText(MessageFormat.format("Status:{0}", marketBizPackage.getPackageStatus()));
        holder.customerName.setText(MessageFormat.format("Customer''s Name:{0},{1}", customer.getCusSurname(), customer.getCusFirstName()));
        holder.customerID.setText(MessageFormat.format("Customer''s ID:{0}", String.valueOf(customer.getCusUID())));
        holder.startDate.setText(MessageFormat.format("Start Date:{0}", marketBizPackage.getPackageDateStarted()));
        holder.savedAmount.setText(MessageFormat.format("Saved Amount: NGN{0}", String.format("%.2f", marketBizPackage.getPackageAmount_collected())));
        holder.endDate.setText(MessageFormat.format("End date:{0}", marketBizPackage.getPackageDateEnded()));
        holder.daysRemaining.setText(MessageFormat.format("Days Rem:{0}", String.valueOf(marketBizPackage.getPackageDaysRem())));
        holder.duration.setText(MessageFormat.format("Duration:{0}", String.valueOf(marketBizPackage.getPackageDuration())));
        holder.profileManager.setText(MessageFormat.format("Manager:{0}", String.valueOf(marketBizPackage.getPackageTellerName())));


        if (marketBizPackage.getPackageType().equalsIgnoreCase("Savings")) {
            holder.ImageIcon.setImageResource(R.drawable.shape_rect_1);
            holder.packageType.setText(MessageFormat.format("Savings: {0}", marketBizPackage.getPackageSavings()));
            holder.packageAmount.setTextColor(Color.RED);
        } else if (marketBizPackage.getPackageType().equalsIgnoreCase("Item Purchase")) {
            holder.ImageIcon.setImageResource(R.drawable.shape_rect_4);
            holder.packageType.setText(MessageFormat.format("Item Purchase: {0}", marketBizPackage.getPackageName()));
            holder.packageAmount.setTextColor(Color.BLUE);
        } else if (marketBizPackage.getPackageType().equalsIgnoreCase("Investment")) {
            holder.ImageIcon.setImageResource(R.drawable.shape_rect_7);
            holder.packageType.setVisibility(View.GONE);
            holder.packageAmount.setTextColor(android.R.color.holo_green_dark);
        }else if (marketBizPackage.getPackageType().equalsIgnoreCase("Promo")) {
            holder.ImageIcon.setImageResource(R.drawable.shape_layer_3);
            holder.packageType.setVisibility(View.GONE);
            holder.packageAmount.setTextColor(android.R.color.holo_red_dark);
        }


    }
    @Override
    public int getItemCount() {
        return (null != marketBizPackages ? marketBizPackages.size() : 0);
    }
    private String removeNewLinesDividers(String text) {
        int decoratedTextLength = Math.min(text.length(), Utils.SkyLightPackage33.MAX_TEXT_LENGTH_IN_LIST);
        return text.substring(0, decoratedTextLength).replaceAll("\n", " ").trim();
    }

    public interface OnItemsClickListener{
        void onItemClick(MarketBizPackage marketBizPackage);
    }

}
