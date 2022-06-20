package com.skylightapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.R;
import com.skylightapp.SkyLightPackageActivity;

import java.util.ArrayList;
import java.util.List;



@SuppressWarnings("ALL")
public class SkyLightCustomerSubListAdapter extends RecyclerView.Adapter<SkyLightCustomerSubListAdapter.ViewHolder>{
    Context context;
    int[] packages;
    LayoutInflater inflter;
    public static ArrayList<String> selectedAction;
    private List<String> data;
    private List<SkyLightPackage> packageList;
    private OnItemsClickListener listener;

    public SkyLightCustomerSubListAdapter(Context applicationContext, int[] packages) {
        this.context = applicationContext;
        this.packages = packages;
        selectedAction = new ArrayList<>();
        for (int i = 0; i < packages.length; i++) {
            selectedAction.add("Checked");
        }
        inflter = (LayoutInflater.from(applicationContext));
    }

    public SkyLightCustomerSubListAdapter(ArrayList<SkyLightPackage> recyclerDataArrayList, SkyLightPackageActivity skyLightPackageActivity) {

        this.packageList = recyclerDataArrayList;

    }
    public void setWhenClickListener(OnItemsClickListener listener){
        this.listener = listener;
    }

    @Override
    public long getItemId(int i) {
        return (null != packageList ? packageList.size() : 0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_package_reports, parent,
                false);

        ImageView icon = (ImageView) rowItem.findViewById(R.id.icon);
        TextView title_package = (TextView) rowItem.findViewById(R.id.package_name);
        TextView customerName4 = (TextView) rowItem.findViewById(R.id.customerName4);
        TextView p_start_date = (TextView) rowItem.findViewById(R.id.p_start_date);
        TextView p_complted_date = (TextView) rowItem.findViewById(R.id.p_complted_date);
        TextView status = (TextView) rowItem.findViewById(R.id.status);
        RadioButton paid = (RadioButton) rowItem.findViewById(R.id.yes);
        RadioButton notPaid = (RadioButton) rowItem.findViewById(R.id.no);
        return new ViewHolder(rowItem);
    }

    @NonNull
    //@Override
    public ViewHolder onCreateViewHolder(final int i, ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_package_reports, parent,
                false);

        ImageView icon = (ImageView) rowItem.findViewById(R.id.icon);
        TextView title_package = (TextView) rowItem.findViewById(R.id.package_name);
        TextView customerName4 = (TextView) rowItem.findViewById(R.id.customerName4);
        TextView p_start_date = (TextView) rowItem.findViewById(R.id.p_start_date);
        TextView p_complted_date = (TextView) rowItem.findViewById(R.id.p_complted_date);
        TextView status = (TextView) rowItem.findViewById(R.id.status);
        RadioButton paid = (RadioButton) rowItem.findViewById(R.id.yes);
        RadioButton notPaid = (RadioButton) rowItem.findViewById(R.id.no);

        paid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedAction.set(i, "Paid");
            }
        });
        notPaid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedAction.set(i, "Not Paid");

            }
        });
        if(selectedAction.get(i).equals(paid)){
            paid.setChecked(true);
        }else if(selectedAction.get(i).equals(notPaid)) {
            notPaid.setChecked(true);

        }

        icon.setImageResource(packages[i]);
        status.setText(packages[i]);
        return new ViewHolder(rowItem);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SkyLightPackage modelItems = packageList.get(position);
        holder.setData(packageList.get(position).getPackageName());
        holder.singleItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onItemClick(modelItems);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return packageList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        public CardView singleItemCardView;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            singleItemCardView = view.findViewById(R.id.singleItemCardView);
            this.textView = view.findViewById(R.id.customerName4);
        }
        public void setData(String name) {
            this.textView.setText(name);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), "position : " + getLayoutPosition() + " text : " + this.textView.getText(), Toast.LENGTH_SHORT).show();
        }
    }
    public interface OnItemsClickListener{
        void onItemClick(SkyLightPackage lightPackage);
    }
}
