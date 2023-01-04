package com.skylightapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.skylightapp.Classes.AppConstants;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.R;
import com.skylightapp.Transactions.PayStackCard;

import java.util.ArrayList;

public class PaymentListAd extends BaseAdapter {
    private LayoutInflater inflater;
    private ViewHolder holder;
    private ArrayList<PayStackCard> listPayStackCard;
    private int selectedPosition;
    private Context context;
    private PrefManager pHelper;
    private int positionKey = 21;

    public PaymentListAd(Context context, ArrayList<PayStackCard> listPayStackCard,
                              int defaultCard) {
        this.listPayStackCard = listPayStackCard;
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        selectedPosition = defaultCard;

    }

    @Override
    public int getCount() {
        return listPayStackCard.size();
    }

    @Override
    public Object getItem(int position) {
        return listPayStackCard.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.card_list_item,
                    parent, false);
            holder = new ViewHolder();
            holder.ivCard = (AppCompatImageView) convertView.findViewById(R.id.ivCard);
            holder.tvNo = (AppCompatTextView) convertView.findViewById(R.id.tvNo);
            holder.rdCardSelection = (RadioButton) convertView
                    .findViewById(R.id.rdCardSelection);
            convertView.setTag(holder);
            holder.rdCardSelection.setTag(position);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final PayStackCard payStackCard = listPayStackCard.get(position);
        final int cardId = payStackCard.getCardID();

        holder.tvNo.setText("*****" + payStackCard.getLastFour());
        String type = payStackCard.getCardType();

        if (type.equalsIgnoreCase(AppConstants.VISA)) {
            holder.ivCard.setImageResource(R.drawable.visa_logo_new);
        } else if (type.equalsIgnoreCase(AppConstants.MASTERCARD)) {
            holder.ivCard
                    .setImageResource(R.drawable.master_card_logo_svg);

        } else if (type.equalsIgnoreCase(AppConstants.VERVE)) {
            holder.ivCard.setImageResource(R.drawable.verve);
        }

        if (selectedPosition == cardId)
            holder.rdCardSelection.setChecked(true);
        else
            holder.rdCardSelection.setChecked(false);

        if (payStackCard.isDefault()) {
            holder.rdCardSelection.setChecked(true);
            PrefManager pref = new PrefManager(context);
            pref.putDefaultCard(cardId);
            pref.putDefaultCardNo(payStackCard.getLastFour());
            pref.putDefaultCardType(payStackCard.getCardType());
        } else
            holder.rdCardSelection.setChecked(false);

        holder.rdCardSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rd = (RadioButton) v;
                if (rd.isChecked()) {
                    //AppLog.Log("PaymentAdapater", "checked Id " + cardId);
                    selectedPosition = cardId;
                    PrefManager pref = new PrefManager(context);
                    pref.putDefaultCard(cardId);
                    pref.putDefaultCardNo(payStackCard.getLastFour());
                    pref.putDefaultCardType(payStackCard.getCardType());
                    pref.putUserId(pHelper.getUserId());
                    pref.putSessionToken(pHelper.getSessionToken());
                    pref.putDefaultCard(cardId);
                    notifyDataSetChanged();
                } else {
                    //AppLog.Log("PaymentAdapater", "unchecked Id " + cardId);
                }
                Intent i = new Intent("card_change_receiver");
                context.sendBroadcast(i);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        public AppCompatImageView ivCard;
        public AppCompatTextView tvNo;
        public RadioButton rdCardSelection;
    }




}
