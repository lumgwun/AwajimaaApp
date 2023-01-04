package com.skylightapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.circularreveal.CircularRevealRelativeLayout;
import com.skylightapp.Admins.AdminPackageAct;
import com.skylightapp.Classes.PaymentCode;
import com.skylightapp.Classes.PaymentDoc;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.util.ArrayList;

import static java.lang.String.valueOf;


public class MySavingsCodeAdapter extends RecyclerView.Adapter<MySavingsCodeAdapter.ViewHolder> {
    private ArrayList<PaymentCode> mValues;
    Context context;
    private OnItemsClickListener listener;

    DBHelper dbHelper;

    public MySavingsCodeAdapter(FragmentActivity activity, ArrayList<PaymentCode> paymentCodes) {

    }
    public MySavingsCodeAdapter(Context context, ArrayList<PaymentCode> paymentCodes) {
        this.context = context;
        this.mValues = paymentCodes;
    }

    public MySavingsCodeAdapter(AdminPackageAct adminPackageAct) {

    }
    public void setWhenClickListener(OnItemsClickListener listener){
        this.listener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())

                .inflate(R.layout.customer_code_row, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView profileID;
        //public final TextView phoneNumber;
        public final TextView code;
        public final TextView codeDate;
        //public final TextView manager;
        public final TextView codeStatus;
        public final LinearLayoutCompat l1;
        public final LinearLayoutCompat l2;
        public final LinearLayoutCompat l3;
        public final LinearLayoutCompat l4;
        private final float mScaleFactor = 1.0f;
        View view;

        CircularRevealRelativeLayout root1;

        public PaymentDoc mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            l1 = (LinearLayoutCompat) view.findViewById(R.id.code_l11);
            l2 = (LinearLayoutCompat) view.findViewById(R.id.code_l21);
            l3 = (LinearLayoutCompat) view.findViewById(R.id.code_l31);
            l4 = (LinearLayoutCompat) view.findViewById(R.id.code_l41);

            profileID = view.findViewById(R.id.profile_code_ID1);
            //phoneNumber = view.findViewById(R.id.code_phone1);
            code = (TextView) view.findViewById(R.id.code_11);
            codeDate = (TextView) view.findViewById(R.id.generated_code_date1);
            //manager = (TextView) view.findViewById(R.id.code_manager1);
            codeStatus = (TextView) view.findViewById(R.id.status_code1);
            //switchStatus = (SwitchCompat) view.findViewById(R.id.code_switch1);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + code.getText() + "'";
        }
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Profile profile = new Profile();
        final PaymentCode paymentCode = mValues.get(position);
        holder.profileID.setText(valueOf(profile.getPID()));
        holder.code.setText(valueOf(paymentCode.getCode()));
        holder.codeDate.setText(paymentCode.getCodeDate());
        holder.codeStatus.setText(paymentCode.getCodeStatus());
    }


    @Override
    public int getItemCount() {
        return (null != mValues ? mValues.size() : 0);
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setPaymentList(ArrayList<? extends PaymentCode> codes) {
        mValues.clear();
        mValues.addAll(codes);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addLast(PaymentCode paymentCode) {
        mValues.add(paymentCode);
        notifyDataSetChanged();
    }
    public interface OnItemsClickListener{
        void onItemClick(PaymentCode paymentCode);
    }


}
