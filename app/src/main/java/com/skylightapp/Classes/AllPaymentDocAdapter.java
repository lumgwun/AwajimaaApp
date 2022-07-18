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

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.circularreveal.CircularRevealRelativeLayout;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.util.ArrayList;


import static java.lang.String.valueOf;

public class AllPaymentDocAdapter extends RecyclerView.Adapter<AllPaymentDocAdapter.ViewHolder> {
    private final ArrayList<PaymentDoc> mValues;
    Context context;
    String statusSwitch1;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    DBHelper dbHelper;

    public AllPaymentDocAdapter(Context context, ArrayList<PaymentDoc> documents) {
        this.context = context;
        this.mValues = documents;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.docs_list_row, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView customerID;
        public final TextView documentID;
        public final TextView savingsID;
        public final TextView tittle;
        public final TextView documentStatus;
        public final AppCompatImageView imageView;
        public final AppCompatImageView statusImage;

        public final LinearLayoutCompat l1;
        public final LinearLayoutCompat l2;
        public final LinearLayoutCompat l3;
        public final LinearLayoutCompat l4;
        public final LinearLayoutCompat l5;
        public final LinearLayoutCompat l6;
        public final CardView c1;
        private ScaleGestureDetector scaleGestureDetector;
        public final SwitchCompat switchStatus;
        private final float mScaleFactor = 1.0f;
        View view;

        CircularRevealRelativeLayout root1;
        public PaymentDoc mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            l1 = (LinearLayoutCompat) view.findViewById(R.id.doc_rl1);
            l2 = (LinearLayoutCompat) view.findViewById(R.id.doc_l1);
            l3 = (LinearLayoutCompat) view.findViewById(R.id.doc_l3_a);
            l4 = (LinearLayoutCompat) view.findViewById(R.id.doc_l2);
            l5 = (LinearLayoutCompat) view.findViewById(R.id.doc_l3);
            l6 = (LinearLayoutCompat) view.findViewById(R.id.doc_l4);
            c1 = (CardView) view.findViewById(R.id.card_view_doc1);

            customerID = (TextView) view.findViewById(R.id.customer_Idi1);
            documentID = (TextView) view.findViewById(R.id.doc_ID_11);
            documentStatus = (TextView) view.findViewById(R.id.document_status_text1);
            savingsID = (TextView) view.findViewById(R.id.document_report_text11);
            tittle = (TextView) view.findViewById(R.id.doc_title_text11);
            imageView = (AppCompatImageView) view.findViewById(R.id.document_view11);
            statusImage = (AppCompatImageView) view.findViewById(R.id.b_user_customer07);
            switchStatus = (SwitchCompat) view.findViewById(R.id.doc_switch1);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + savingsID.getText() + "'";
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Customer customer = new Customer();
        class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
            @Override
            public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                mScaleFactor *= scaleGestureDetector.getScaleFactor();
                mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
                holder.imageView.setScaleX(mScaleFactor);
                holder.imageView.setScaleY(mScaleFactor);
                return true;
            }
        }
        CustomerDailyReport customerDailyReport = new CustomerDailyReport();
        ScaleGestureDetector gestureDetector = new ScaleGestureDetector(context.getApplicationContext(), new ScaleListener());
        final PaymentDoc paymentDoc = mValues.get(position);
        holder.customerID.setText(valueOf(customer.getCusUID()));
        holder.tittle.setText(paymentDoc.getDocTittle());

        holder.savingsID.setText(valueOf(customerDailyReport.getRecordID()));


        //holder.paymentDocument.setImageURI(Uri.parse(paymentDocument.getDocumentLink()));

        if (holder.view != null) {
            Glide.with(holder.view)
                    .load(paymentDoc.getDocumentLink())
                    .fitCenter()
                    .into(holder.imageView);
        }
        int docID = paymentDoc.getDocumentId();

        if (holder.view != null) {
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (i == DialogInterface.BUTTON_POSITIVE) {
                                Toast.makeText(context, "You are deleting payment Document of  " + docID + "," + position, Toast.LENGTH_SHORT).show();
                                dbHelper.deletePaymentDoc(docID);

                            }

                        }

                    };
                }

                ;
            });
        }

        holder.switchStatus.setText("");
        if (holder.switchStatus.isChecked()) {
            holder.switchStatus.setText("Verified");
            holder.statusImage.setImageResource(R.drawable.verified_savings);
            holder.documentStatus.setText("Verified");
            dbHelper = new DBHelper(context.getApplicationContext());
            String status1 = "Verified";
            //long docID = paymentDocument.getDocumentId();
            dbHelper.updateDocumentStatus(docID, status1);
            notifyDataSetChanged();


        } else {
            holder.switchStatus.setText("Not Verified");
            holder.documentStatus.setText("Not Verified");
            holder.statusImage.setImageResource(R.drawable.unverified);
            dbHelper = new DBHelper(context.getApplicationContext());
            String status2 = "Not Verified";
            //long docID = paymentDocument.getDocumentId();
            dbHelper.updateDocumentStatus(docID, status2);
            notifyDataSetChanged();
        }
    }


   /* @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }*/



    @Override
    public int getItemCount() {
        return (null != mValues ? mValues.size() : 0);

    }


    @SuppressLint("NotifyDataSetChanged")
    public void setPaymentList(ArrayList<? extends PaymentDoc> documents) {
        mValues.clear();
        mValues.addAll(documents);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addLast(PaymentDoc paymentDoc) {
        mValues.add(paymentDoc);
        notifyDataSetChanged();
    }



}
