package com.skylightapp.MarketClasses;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.card.MaterialCardView;
import com.skylightapp.GroupAcctDetailsAct;
import com.skylightapp.R;

import java.text.MessageFormat;

import de.hdodenhof.circleimageview.CircleImageView;

public class GrpAcctSwipeAdapter extends ArrayAdapter<GrpSNewUserReq> {
    public static final String EXTRA_GRP_ACCT_E = "970";
    public static final String EXTRA_SWIPE_VIEW_SOURCE = "401";
    private ImageLoader imageLoader;
    private FragmentActivity fragmentActivity;
    Context context;

    public GrpAcctSwipeAdapter(Context context, FragmentActivity fragmentActivity) {
        super(context, 0);
        this.fragmentActivity = fragmentActivity;
        this.imageLoader = AppE.getImageLoader();
    }

    public GrpAcctSwipeAdapter(Context context) {
        super(context, 0);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View contentView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (contentView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            contentView = inflater.inflate(R.layout.item_acct_swipe, parent, false);
            holder = new ViewHolder(contentView);
            contentView.setTag(holder);
        } else {
            holder = (ViewHolder) contentView.getTag();
        }

        GrpSNewUserReq grpSNewUserReq = getItem(position);
        if(grpSNewUserReq !=null){

            holder.amount.setText(MessageFormat.format("Amount{0}{1}", grpSNewUserReq.getGsAReqCurrency(), grpSNewUserReq.getGsAReqAmount()));
            holder.tittle.setText(MessageFormat.format("Tittle{0}", grpSNewUserReq.getGsAReqName()));
            holder.duration.setText(MessageFormat.format("Duration{0}", grpSNewUserReq.getDuration()));
            holder.durationRem.setText(MessageFormat.format("Duration Rem{0}", grpSNewUserReq.getGsAReqDurationRem()));
            holder.amountSoFar.setText(MessageFormat.format("Amount so Far{0}", grpSNewUserReq.getGsAReqAmtSoFar()));
            holder.endDate.setText(MessageFormat.format("End Date{0}", grpSNewUserReq.getGsAReqEndDate()));

        }



        holder.cardView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                View cardView = fragmentActivity.findViewById(R.id.user_swipe_card_image);
                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation(fragmentActivity,
                                Pair.create(cardView, "user_swipe_image_transition"));
                fragmentActivity.startActivity(getBundledIntent(grpSNewUserReq), options.toBundle());
            }
        });


        return contentView;
    }

    public GrpSNewUserReq getGSAcctDetailId(int acctId) {
        for (int i = 0; i < getCount(); i++) {
            if (getItem(i).getGsAReqAcctID() == acctId) {
                return getItem(i);
            }
        }
        return null;
    }

    private Intent getBundledIntent(GrpSNewUserReq grpAcctRequest) {
        Intent intent = new Intent(getContext(), GroupAcctDetailsAct.class);
        intent.putExtra(EXTRA_GRP_ACCT_E, grpAcctRequest);
        intent.putExtra(EXTRA_SWIPE_VIEW_SOURCE, true);
        return intent;
    }

    private static class ViewHolder {
        public AppCompatTextView tittle,amount,amountSoFar,duration,endDate,durationRem;
        public AppCompatImageView image;
        private MaterialCardView cardView;

        ViewHolder(View view) {
            this.duration = view.findViewById(R.id.duration_ga);
            this.cardView = view.findViewById(R.id.card_l1);
            this.image =  view.findViewById(R.id.img_acct_g);
            this.amount = view.findViewById(R.id.gAmt);
            this.tittle = view.findViewById(R.id.gtittle);
            this.endDate = view.findViewById(R.id.end_Date_ga);
            this.amountSoFar = view.findViewById(R.id.amt_so_far);
            this.durationRem = view.findViewById(R.id.rem_duration);
        }
    }
}
