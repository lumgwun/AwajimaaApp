package com.skylightapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.skylightapp.Admins.AdminBankDeposit;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.TransactionGranting;
import com.skylightapp.Classes.User;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class PaymentRequestAdap extends RecyclerView.Adapter<PaymentRequestAdap.ViewHolder> implements ListAdapter,View.OnClickListener, Filterable {

    private Context context;
    private int resource;
    String statusSwitch1;
    private final ArrayList<TransactionGranting> grantings;
    DBHelper applicationDb;
    String status;
    Profile userProfile;
    Customer customer;
    User user;
    SharedPreferences userPreferences;
    private Gson gson;
    private OnItemsClickListener listener;
    private TransactionGranting transactionGranting;
    private List<TransactionGranting> transactionGrantingList;
    private List<TransactionGranting> transactionGrantings;


    public PaymentRequestAdap(Context context, int resource, ArrayList<TransactionGranting> grantings) {
        super();
        this.context = context;
        this.resource = resource;
        this.grantings = grantings;
    }

    public PaymentRequestAdap(Context context, ArrayList<TransactionGranting> transactions2) {
        this.context = context;
        this.grantings = transactions2;
    }
    public void setWhenClickListener(OnItemsClickListener listener){
        this.listener = listener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payout_list_row, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public int getItemCount() {
        return (null != grantings ? grantings.size() : 0);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
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

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onItemClick(transactionGranting);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    transactionGrantings = transactionGrantingList;
                } else {
                    List<TransactionGranting> filteredList = new ArrayList<>();
                    for (TransactionGranting adminBankDeposit : transactionGrantingList) {
                        if (adminBankDeposit.getTe_Bank_AcctName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(adminBankDeposit);
                        }
                    }
                    transactionGrantings = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = transactionGrantings;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                transactionGrantings = (ArrayList<TransactionGranting>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtCusName;
        public final TextView txtTraxAuthorizer;
        public final TextView txtPayableAmount;
        public final TextView transactionID;
        public final AppCompatImageView imgTransactionIcon;
        public final TextView refNo;
        public final CardView cardView;
        public final TextView request_Date;
        public final TextView bankAcctNo;
        public final TextView receivingBank;
        public final TextView bankAcctName;
        public final TextView purpose;
        public final TextView status;
        public TransactionGranting mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            cardView = view.findViewById(R.id.card_view_payout);
            txtCusName = view.findViewById(R.id.txt_payout_Cus);
            imgTransactionIcon = view.findViewById(R.id.payoutICon);
            refNo = view.findViewById(R.id.payout_ref_No);
            request_Date = view.findViewById(R.id.payout_date);
            txtPayableAmount = view.findViewById(R.id.payout_Amount);
            receivingBank = view.findViewById(R.id.payout_Bank);
            transactionID = view.findViewById(R.id.txt_pt_id);
            bankAcctName = view.findViewById(R.id.sendingAccount);
            bankAcctNo = view.findViewById(R.id.payoutAcctNo);
            txtTraxAuthorizer = view.findViewById(R.id.payout_authorizer);
            purpose = view.findViewById(R.id.payoutPurpose);
            status = view.findViewById(R.id.payout_Status);


        }


    }
    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = grantings.get(position);
        final TransactionGranting transactionGranting = grantings.get(position);
        holder.refNo.setText(MessageFormat.format("", transactionGranting.getTeId()));
        holder.txtCusName.setText(MessageFormat.format("Customer: {0}", transactionGranting.getTe_Customer_Name()));
        holder.txtPayableAmount.setText(MessageFormat.format("NGN{0}", String.format("%.2f", transactionGranting.getTe_Amount())));
        holder.receivingBank.setText(MessageFormat.format("", transactionGranting.getTe_Bank()));
        holder.request_Date.setText(MessageFormat.format(" {0}", transactionGranting.getTe_Date()));
        holder.bankAcctName.setText(MessageFormat.format("", transactionGranting.getTe_Bank_AcctName()));
        holder.txtTraxAuthorizer.setText(MessageFormat.format("Authorizer: {0}", transactionGranting.getTe_Authorizer()));
        //holder.pictureOfCelebrant.setImageURI(profile.getProfilePicture());
        holder.bankAcctNo.setText(MessageFormat.format("", transactionGranting.getTe_Bank_AcctNo()));
        holder.purpose.setText(MessageFormat.format("{0}", transactionGranting.getTe_purpose()));
        holder.status.setText(MessageFormat.format("Status: {0}", transactionGranting.getTe_status()));
        holder.transactionID.setText(MessageFormat.format("", transactionGranting.getTransactionID()));


        if (transactionGranting.getTe_Authorizer().isEmpty()) {
            holder.imgTransactionIcon.setImageResource(R.drawable.uncompleted);
            holder.txtPayableAmount.setTextColor(Color.RED);
            holder.status.setText("Pending");
        } else if (transactionGranting.getTe_Authorizer() !=null) {
            holder.imgTransactionIcon.setImageResource(R.drawable.verified3);
            holder.txtPayableAmount.setTextColor(Color.GREEN);
            holder.status.setText("Approved");
        }


    }
    public interface OnItemsClickListener{
        void onItemClick(TransactionGranting transaction);
    }

}
