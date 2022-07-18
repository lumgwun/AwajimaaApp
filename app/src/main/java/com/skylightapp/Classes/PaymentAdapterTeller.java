package com.skylightapp.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.materialdrawer.view.BezelImageView;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;

public class PaymentAdapterTeller extends RecyclerView.Adapter<PaymentAdapterTeller.RecyclerViewHolder> {

    private ArrayList<Payment> paymentArrayList;
    private Context mcontext;
    int resources;
    FragmentActivity activity;
    private OnItemsClickListener listener;

    public PaymentAdapterTeller(ArrayList<Payment> paymentArrayList, Context mcontext) {
        this.paymentArrayList = paymentArrayList;
        this.mcontext = mcontext;
    }

    public PaymentAdapterTeller(Context context, int resources, ArrayList<Payment> paymentArrayList) {
        this.paymentArrayList = paymentArrayList;
        this.mcontext = context;
        this.resources = resources;

    }
    public void setWhenClickListener(OnItemsClickListener listener){
        this.listener = listener;
    }
    public PaymentAdapterTeller(Context context, ArrayList<Payment> paymentArrayList) {
        this.paymentArrayList = paymentArrayList;
        this.mcontext = context;

    }

    public PaymentAdapterTeller(FragmentActivity activity, ArrayList<Payment> paymentArrayList) {
        this.paymentArrayList = paymentArrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_list_row, parent, false);
        return new PaymentAdapterTeller.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Payment recyclerData = paymentArrayList.get(position);
        holder.txtPaymentType.setText(MessageFormat.format("Payment Type:{0}", recyclerData.getPaymentType()));
        holder.txtPaymentID.setText(MessageFormat.format("Payment ID:{0}", recyclerData.getPaymentID()));
        holder.txtPaymentAmount.setText(MessageFormat.format("Payment Amount:{0}", String.format("%.2f", recyclerData.getPaymentAmtToWithdraw())));
        holder.txtPaymentDate.setText(MessageFormat.format("Payment Date: NGN{0}", recyclerData.getPaymentDate()));
        holder.txtCustomerID.setText(MessageFormat.format("Acct Type:{0}", String.valueOf(recyclerData.getPaymentCusID())));
        holder.txtPaymentOffice.setText(MessageFormat.format("Acct ID:{0}", String.valueOf(recyclerData.getPaymentOffice())));
        holder.txtPaymentStatus.setText(MessageFormat.format("Acct ID:{0}", String.valueOf(recyclerData.getPaymentStatus())));


    }
    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return (null != paymentArrayList ? paymentArrayList.size() : 0);
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView txtPaymentType;
        private TextView txtPaymentID;
        private final BezelImageView userPicture;
        private TextView txtPaymentOffice,txtPaymentStatus;
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
            userPicture = itemView.findViewById(R.id.img_payment);

        }
    }
    public interface OnItemsClickListener{
        void onItemClick(Payment payment);
    }
}
