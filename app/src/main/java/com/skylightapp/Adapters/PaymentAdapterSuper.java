package com.skylightapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.Classes.Payment;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class PaymentAdapterSuper extends RecyclerView.Adapter<PaymentAdapterSuper.RecyclerViewHolder> implements View.OnClickListener, Filterable {

    private ArrayList<Payment> paymentArrayList;
    private List<Payment> paymentList;
    private List<Payment> payments;
    private Context mcontext;
    int resources;
    private FragmentActivity activity;
    private OnItemsClickListener listener;
    private Payment payment;

    public PaymentAdapterSuper(ArrayList<Payment> paymentArrayList, Context mcontext) {
        this.paymentArrayList = paymentArrayList;
        this.mcontext = mcontext;
    }

    public PaymentAdapterSuper(Context context, int resources, ArrayList<Payment> paymentArrayList) {
        this.paymentArrayList = paymentArrayList;
        this.mcontext = context;
        this.resources = resources;

    }
    public void setWhenClickListener(OnItemsClickListener listener){
        this.listener = listener;
    }
    public PaymentAdapterSuper(Context context, ArrayList<Payment> paymentArrayList) {
        this.paymentArrayList = paymentArrayList;
        this.mcontext = context;

    }

    public PaymentAdapterSuper(FragmentActivity activity, ArrayList<Payment> paymentArrayList) {
        this.paymentArrayList = paymentArrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.super_payment_row, parent, false);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Payment recyclerData = paymentArrayList.get(position);
        holder.txtPaymentType.setText(MessageFormat.format("Payment Type:{0}", recyclerData.getPaymentType()));
        holder.txtPaymentID.setText(MessageFormat.format("Payment ID:{0}", recyclerData.getPaymentID()));
        holder.txtPaymentAmount.setText(MessageFormat.format("Payment Amount:{0}", String.format("%.2f", recyclerData.getPaymentAmtToWithdraw())));
        holder.txtPaymentDate.setText(MessageFormat.format("Payment Date: NGN{0}", recyclerData.getPaymentDate()));
        holder.txtCustomerID.setText(MessageFormat.format("Acct Type:{0}", String.valueOf(recyclerData.getPaymentCusID())));
        holder.txtPaymentOffice.setText(MessageFormat.format("Office:{0}", String.valueOf(recyclerData.getPaymentOffice())));
        holder.txtPaymentStatus.setText(MessageFormat.format("Status:{0}", String.valueOf(recyclerData.getPaymentStatus())));
        holder.txtApprover.setText(MessageFormat.format("Status:{0}", String.valueOf(recyclerData.getPaymentStatus())));
        holder.txtApprovalDate.setText(MessageFormat.format("Approval Date:{0}", String.valueOf(recyclerData.getPaymentDate())));
        holder.txtAccount.setText(MessageFormat.format("Account:{0}", String.valueOf(recyclerData.getPaymentAccount())));
        holder.txtAccountType.setText(MessageFormat.format("Account Type:{0}", String.valueOf(recyclerData.getPaymentAccountType())));


    }
    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return (null != paymentArrayList ? paymentArrayList.size() : 0);
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onItemClick(payment);
        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    paymentList = payments;
                } else {
                    List<Payment> filteredList = new ArrayList<>();
                    for (Payment payment : payments) {
                        if (payment.getPaymentOffice().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(payment);
                        }
                    }
                    paymentList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = paymentList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                paymentList = (ArrayList<Payment>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView txtPaymentType;
        private TextView txtPaymentID;
        private final BezelImageView userPicture;
        private TextView txtPaymentOffice,txtPaymentStatus,txtApprover,txtApprovalDate,txtAccount,txtAccountType;
        private TextView txtPaymentAmount;
        private TextView txtPaymentDate;
        private TextView txtCustomerID;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            txtPaymentType = itemView.findViewById(R.id.paymentType);
            txtPaymentAmount = itemView.findViewById(R.id.paymentAmt);
            txtCustomerID = itemView.findViewById(R.id.paymentCusID);
            txtPaymentOffice = itemView.findViewById(R.id.paymentOffice);
            txtPaymentID = itemView.findViewById(R.id.payment_id4);
            txtPaymentStatus = itemView.findViewById(R.id.paymentStatus);
            txtPaymentDate = itemView.findViewById(R.id.paymentDate);
            txtApprover = itemView.findViewById(R.id.paymentApprover);
            txtApprovalDate = itemView.findViewById(R.id.paymentDateOfA);
            txtAccount = itemView.findViewById(R.id.paymentAcct);
            txtAccountType = itemView.findViewById(R.id.paymentAccountType);
            userPicture = itemView.findViewById(R.id.img_payment);

        }
    }
    public interface OnItemsClickListener{
        void onItemClick(Payment payment);
    }
}
