package com.skylightapp.Bookings;

import static com.skylightapp.Bookings.BookingConstant.AMERICAN_EXPRESS;
import static com.skylightapp.Bookings.BookingConstant.DINERS_CLUB;
import static com.skylightapp.Bookings.BookingConstant.DISCOVER;
import static com.skylightapp.Bookings.BookingConstant.MASTERCARD;
import static com.skylightapp.Bookings.BookingConstant.TAG;
import static com.skylightapp.Bookings.BookingConstant.URL;
import static com.skylightapp.Bookings.BookingConstant.VISA;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.skylightapp.Classes.AppLog;
import com.skylightapp.Classes.AsyncListener;
import com.skylightapp.Classes.Card;
import com.skylightapp.Classes.ParseContent;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.UtilsExtra;
import com.skylightapp.Classes.VolleyHttpRequest;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PaymentListAd extends BaseAdapter implements
        AsyncListener, Response.ErrorListener {
    private LayoutInflater inflater;
    private ViewHolder holder;
    private ArrayList<Card> listCard;
    private int selectedPosition;
    private Context context;
    private PrefManager pHelper;
    private int positionKey = 21;
    private RequestQueue requestQueue;

    public PaymentListAd(Context context, ArrayList<Card> listCard,
                              int defaultCard) {
        this.listCard = listCard;
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        selectedPosition = defaultCard;
        pHelper = new PrefManager(context);
        requestQueue = Volley.newRequestQueue(context);
    }

    @Override
    public int getCount() {
        return listCard.size();
    }

    @Override
    public Object getItem(int position) {
        return listCard.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_payment_item,
                    parent, false);
            holder = new ViewHolder();
            holder.ivCard =  convertView.findViewById(R.id.ivCard_Pay);
            holder.tvNo = convertView.findViewById(R.id.tvNo_Pay);
            holder.rdCardSelection = convertView
                    .findViewById(R.id.rdCardSelection);
            convertView.setTag(holder);
            holder.rdCardSelection.setTag(position);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Card card = listCard.get(position);
        final int cardId = card.getCardID();

        holder.tvNo.setText("*****" + card.getLastFour());
        String type = card.getCardType();

        if (type.equalsIgnoreCase(VISA)) {
            holder.ivCard.setImageResource(R.drawable.visa_logo_new);
        } else if (type.equalsIgnoreCase(MASTERCARD)) {
            holder.ivCard
                    .setImageResource(R.drawable.master_card_logo_svg);
        } else if (type.equalsIgnoreCase(AMERICAN_EXPRESS)) {
            //holder.ivCard.setImageResource(R.drawable.ub__creditcard_amex);
        } else if (type.equalsIgnoreCase(DISCOVER)) {
            //holder.ivCard.setImageResource(R.drawable.ub__creditcard_discover);
        } else if (type.equalsIgnoreCase(DINERS_CLUB)) {
            //holder.ivCard.setImageResource(R.drawable.ub__creditcard_discover);
        } else {
            //holder.ivCard.setImageResource(R.drawable.ub__nav_payment);
        }

        if (selectedPosition == cardId)
            holder.rdCardSelection.setChecked(true);
        else
            holder.rdCardSelection.setChecked(false);

        if (card.isDefault()) {
            holder.rdCardSelection.setChecked(true);
            PrefManager pref = new PrefManager(context);
            pref.putDefaultCard(cardId);
            pref.putDefaultCardNo(card.getLastFour());
            pref.putDefaultCardType(card.getCardType());
        } else
            holder.rdCardSelection.setChecked(false);

        holder.rdCardSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatRadioButton rd = (AppCompatRadioButton) v;
                if (rd.isChecked()) {
                    AppLog.Log("PaymentAdapater", "checked Id " + cardId);
                    selectedPosition = cardId;
                    PrefManager pref = new PrefManager(context);
                    pref.putDefaultCard(cardId);
                    pref.putDefaultCardNo(card.getLastFour());
                    pref.putDefaultCardType(card.getCardType());
                    notifyDataSetChanged();
                    setDefaultCard(cardId);
                } else {
                    AppLog.Log("PaymentAdapater", "unchecked Id " + cardId);
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
        public AppCompatRadioButton rdCardSelection;
    }

    private void setDefaultCard(int cardId) {
        UtilsExtra.showCustomProgressDialog(context,
                context.getString(R.string.c_default_card), true,
                null);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(URL, BookingConstant.ServiceType.DEFAULT_CARD);
        map.put(BookingConstant.Params.ID, String.valueOf(pHelper.getUserId()));
        map.put(BookingConstant.Params.TOKEN, String.valueOf(pHelper.getSessionToken()));
        map.put(BookingConstant.Params.DEFAULT_CARD_ID, String.valueOf(cardId));

        // new HttpRequester((Activity) context, map,
        // Const.ServiceCode.DEFAULT_CARD, this);
        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
                BookingConstant.ServiceCode.DEFAULT_CARD, this, this));
    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        UtilsExtra.removeCustomProgressDialog();
        listCard.clear();
        AppLog.Log("PaymentAdapter", "CardSelection reponse : " + response);
        new ParseContent((Activity) context).parseCards(response, listCard);
        notifyDataSetChanged();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // TODO Auto-generated method stub
        AppLog.Log(TAG, error.getMessage());
    }
}
