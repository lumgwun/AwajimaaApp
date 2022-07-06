package com.skylightapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.PaymentDoc;
import com.skylightapp.R;

import java.util.ArrayList;

import static java.lang.String.valueOf;


public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder> {
    private final ArrayList<PaymentDoc> mValues;
    Context context;
    String statusSwitch1;
    private float mScaleFactor = 1.0f;
    private ScaleGestureDetector scaleGestureDetector;


    public DocumentAdapter(Context context, ArrayList<PaymentDoc> documents) {
        this.context = context;
        this.mValues = documents;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())

                .inflate(R.layout.document_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.mItem = mValues.get(position);
        class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
            @Override
            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                mScaleFactor *= scaleGestureDetector.getScaleFactor();
                mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
                holder.documentView.setScaleX(mScaleFactor);
                holder.documentView.setScaleY(mScaleFactor);
                return true;
            }
        }
        Customer customer = new Customer();
        CustomerDailyReport customerDailyReport = new  CustomerDailyReport();
        ScaleGestureDetector gestureDetector = new ScaleGestureDetector(context.getApplicationContext(), new ScaleListener());
        final PaymentDoc paymentDoc = mValues.get(position);
        holder.customerID.setText(valueOf(customer.getCusUID()));
        holder.tittle.setText(paymentDoc.getDocTittle());
        holder.savingsID.setText(valueOf(customerDailyReport.getRecordID()));
        holder.documentStatus.setText(paymentDoc.getDocStatus());
        //holder.paymentDocument.setImageURI(Uri.parse(paymentDocument.getDocumentLink()));
        if (holder.view != null) {
            Glide.with(holder.view)
                    .load(paymentDoc.getDocumentLink())
                    .fitCenter()
                    .into(holder.documentView);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != mValues ? mValues.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView customerID;
        public final TextView documentID;
        public final TextView savingsID;
        public final TextView tittle;
        public final TextView documentStatus;
        public final AppCompatImageView documentView;
        private ScaleGestureDetector scaleGestureDetector;
        private float mScaleFactor = 1.0f;
        //public final SwitchCompat switchCompat;
        public PaymentDoc mItem;
        public ViewHolder(View view) {
            super(view);
            customerID = (TextView) view.findViewById(R.id.customer_Idi);
            documentID = (TextView) view.findViewById(R.id.doc_ID_1);
            documentStatus = (TextView) view.findViewById(R.id.document_status_text);
            savingsID = (TextView) view.findViewById(R.id.document_report_text);
            tittle = (TextView) view.findViewById(R.id.doc_title_text);
            documentView = (AppCompatImageView) view.findViewById(R.id.document_view);
            //switchCompat = (SwitchCompat) view.findViewById(R.id.message_switch);
            this.view = null;
        }


        @Override
        public String toString() {
            return super.toString() + " '" + savingsID.getText() + "'";
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setUserList(ArrayList<? extends PaymentDoc> documents) {
        mValues.clear();
        mValues.addAll(documents);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addLast(PaymentDoc paymentDoc) {
        mValues.add(paymentDoc);
        notifyDataSetChanged();
    }
    /*@Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }*/

}
