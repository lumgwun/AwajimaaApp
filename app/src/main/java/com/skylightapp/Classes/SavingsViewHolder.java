package com.skylightapp.Classes;

import android.content.Context;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.skylightapp.Adapters.SavingsAdapter;
import com.skylightapp.Interfaces.OnTellerReportChangeListener;


public class SavingsViewHolder extends RecyclerView.ViewHolder {

    private  AppCompatImageView avatarImageView;
    private ExpandableTextView commentTextView;
    private AppCompatTextView dateTextView;
    private final ProfileManager profileManager;
    private SavingsAdapter.Callback callback;
    private Context context;

    public SavingsViewHolder(View itemView, final SavingsAdapter.Callback callback) {
        super(itemView);

        this.callback = callback;
        this.context = itemView.getContext();
        profileManager = ProfileManager.getInstance(itemView.getContext().getApplicationContext());

        //avatarImageView = (AppCompatImageView) itemView.findViewById(R.id.avatarImageView);
        //commentTextView = (ExpandableTextView) itemView.findViewById(R.id.commentText);

        if (callback != null) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        callback.onLongItemClick(v, position);
                        return true;
                    }

                    return false;
                }
            });
        }
    }



    private OnTellerReportChangeListener<CustomerDailyReport> createOnSavingsChangeListener(final ExpandableTextView expandableTextView,
                                                                                            final AppCompatImageView avatarImageView,
                                                                                            final CustomerDailyReport comment,
                                                                                            final AppCompatTextView dateTextView) {
        return new OnTellerReportChangeListener<CustomerDailyReport>() {
            //@Override
            public void onObjectChanged(CustomerDailyReport obj) {
                long savingsID = obj.getRecordNo();
                //fillSavings(savingsID, comment, expandableTextView, dateTextView);
                if (obj.getSavingsDoc() != null) {
                    Utils.loadImage(Glide.with(context), obj.getSavingsDoc(), avatarImageView);
                }
            }

            @Override
            public void onSavingsChanged(CustomerDailyReport obj) {

            }

            @Override
            public void onError(String errorText) {
                //fillSavings("", comment, commentTextView, dateTextView);
            }
        };
    }


}
