package com.skylightapp.Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.circularreveal.CircularRevealRelativeLayout;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.CodeDAO;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.LoanDAO;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.Database.PaymDocDAO;
import com.skylightapp.Database.PaymentCodeDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Database.SODAO;
import com.skylightapp.Database.TCashDAO;
import com.skylightapp.Database.TReportDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.R;

import java.util.ArrayList;



import static java.lang.String.valueOf;

public class PaymentCodeAdapter extends RecyclerView.Adapter<PaymentCodeAdapter.ViewHolder> {
    private  ArrayList<PaymentCode> mValues;
    Context context;
    String statusSwitch1;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;

    DBHelper dbHelper;
    private ViewHolder holder;
    private int position,code_list_row;
    private SODAO sodao;
    private TranXDAO tranXDAO;
    private MessageDAO messageDAO;
    private LoanDAO loanDAO;
    private AcctDAO acctDAO;
    private CodeDAO codeDAO;
    private PaymDocDAO paymDocDAO;

    CusDAO cusDAO;
    PaymentCodeDAO paymentCodeDAO;
    private ProfDAO profileDao;

    private TCashDAO cashDAO;
    private TReportDAO tReportDAO;

    public PaymentCodeAdapter(FragmentActivity activity, ArrayList<PaymentCode> paymentCodes) {

    }
    public PaymentCodeAdapter(Context context, ArrayList<PaymentCode> paymentCodes) {
        this.context = context;
        this.mValues = paymentCodes;
    }

    public PaymentCodeAdapter(Context context, int code_list_row, ArrayList<PaymentCode> paymentCodes) {
        this.context = context;
        this.mValues = paymentCodes;
        this.code_list_row = code_list_row;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())

                .inflate(R.layout.code_list_row, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView profileID;
        public final TextView phoneNumber;
        public final TextView code;
        public final TextView codeDate;
        public final TextView manager;
        public final TextView codeStatus;
        public final LinearLayoutCompat l1;
        public final LinearLayoutCompat l2;
        public final LinearLayoutCompat l3;
        public final LinearLayoutCompat l4;
        public final SwitchCompat switchStatus;
        private final float mScaleFactor = 1.0f;
        View view;

        CircularRevealRelativeLayout root1;

        public PaymentDoc mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            l1 = (LinearLayoutCompat) view.findViewById(R.id.code_l1);
            l2 = (LinearLayoutCompat) view.findViewById(R.id.code_l2);
            l3 = (LinearLayoutCompat) view.findViewById(R.id.code_l3);
            l4 = (LinearLayoutCompat) view.findViewById(R.id.code_l4);

            profileID = view.findViewById(R.id.profile_code_ID);
            phoneNumber = view.findViewById(R.id.code_phone);
            code = (TextView) view.findViewById(R.id.code_1);
            codeDate = (TextView) view.findViewById(R.id.generated_code_date);
            manager = (TextView) view.findViewById(R.id.code_manager);
            codeStatus = (TextView) view.findViewById(R.id.status_code);
            switchStatus = (SwitchCompat) view.findViewById(R.id.code_switch1);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + code.getText() + "'";
        }
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        this.holder = holder;
        this.position = position;
        Profile profile = new Profile();
        mValues=new ArrayList<>();
        final PaymentCode paymentCode = mValues.get(position);
        holder.profileID.setText(valueOf(profile.getPID()));
        holder.phoneNumber.setText(profile.getProfilePhoneNumber());
        holder.code.setText(valueOf(paymentCode.getCode()));
        holder.codeDate.setText(paymentCode.getCodeDate());
        holder.manager.setText(paymentCode.getCodeManager());
        holder.codeStatus.setText(paymentCode.getCodeStatus());

        paymentCodeDAO= new PaymentCodeDAO(context.getApplicationContext());

        codeDAO= new CodeDAO(context.getApplicationContext());

        holder.switchStatus.setText("");
        if (holder.switchStatus.isChecked()){
            holder.switchStatus.setText("Verified");
            holder.codeStatus.setText("Verified");
            dbHelper = new DBHelper(context.getApplicationContext());
            String status1 ="Verified";
            int codeId =paymentCode.getCodeID();
            codeDAO.updateSavingsCodeStatus(codeId,status1);
            notifyDataSetChanged();

        }else{
            holder.switchStatus.setText("Not Verified");
            holder.codeStatus.setText("Not Verified");
            dbHelper = new DBHelper(context.getApplicationContext());
            String status2 = "Not Verified";
            int codeId = paymentCode.getCodeID();
            codeDAO.updateSavingsCodeStatus(codeId, status2);
            notifyDataSetChanged();
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == DialogInterface.BUTTON_POSITIVE) {
                            if(paymentCode !=null){
                                int codeId = paymentCode.getCodeID();
                                Toast.makeText(context, "You are deleting Savings Code " + codeId + "," + position, Toast.LENGTH_SHORT).show();
                                codeDAO.deleteSavingsCode(codeId);
                                notifyDataSetChanged();

                            }


                        }

                    }

                };

            };
        });
    }


    @Override
    public int getItemCount() {
        return (null != mValues ? mValues.size() : 0);
    }


    public void setPaymentList(ArrayList<? extends PaymentCode> codes) {
        mValues.clear();
        mValues.addAll(codes);
        notifyDataSetChanged();
    }

    public void addLast(PaymentCode paymentCode) {
        mValues.add(paymentCode);
        notifyDataSetChanged();
    }


}
