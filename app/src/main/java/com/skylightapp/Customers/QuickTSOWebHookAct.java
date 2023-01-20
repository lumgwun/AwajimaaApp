package com.skylightapp.Customers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.skylightapp.Classes.Utils;
import com.skylightapp.R;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;

public class QuickTSOWebHookAct extends AppCompatActivity {
    private WebView mywebView;
    String jstring;
    String paymentRef;
    String paymentFName;
    String paymentLName;
    long paymentAmt;
    String paymentDate;
    String paymentEmail,savings_so;
    String paymentProvider,paymentPurpose;
    int paymentNo;
    QTDataAdapter adapter;
    private ArrayList<QTellerData> qTellerDataArrayList;
    private ArrayList<QTellerData> membershipDataArrayList;
    private ArrayList<QTellerData> bizSubDataArrayList;
    private ListView listView;
    private JSONObject jsonObject;
    private JsonArray array;
    private static final String SO_WEB_HOOK = "https://eod04os6ldlez5q.m.pipedream.net";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_qt_so_webhook);
        listView = findViewById(R.id.list_provider);
        getJSONstring(savings_so);
    }
    public void getJSONstring(String savings_so) {
        String url = SO_WEB_HOOK;
        Log.i("url", url);

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        qTellerDataArrayList= new ArrayList<>();
                        listView = findViewById(R.id.list_provider);

                        try{
                            jsonObject = new JSONObject(response);
                            //Toast.makeText(this, "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
                        }
                        catch(JSONException e) {
                        }
                        Log.i("Response", response.toString());

                        /*if(jsonObject !=null){
                            array = jsonObject.getAsJsonArray("results");


                        }
                        for (int i = 0; i < 3; i++){
                            if (jsonArray != null) {
                                try {
                                    JSONObject recipe = (JSONObject)jsonArray.get(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }*/


                        for (JsonElement result: array){
                            JsonObject resultObject = result.getAsJsonObject();
                            String lastName = resultObject.get("lastName").getAsString();
                            String firstName = resultObject.get("firstName").getAsString();
                            String emailAddress = resultObject.get("emailAddress").getAsString();
                            String provider = resultObject.get("provider").getAsString();
                            String purpose = resultObject.get("purpose").getAsString();
                            long amount = resultObject.get("amount").getAsLong();
                            String ref = resultObject.get("ref").getAsString();
                            String dateOfPayment = resultObject.get("dateOfPayment").getAsString();

                        }
                        for (int i = 0; i < qTellerDataArrayList.size(); i++){

                        }

                        /*Object ob = gson.fromJson(input, QTellerData.class);
                        QTellerData o = (QTellerData) ob;
                        JSONObject jobj = null;
                        try {
                            jobj = new JSONObject(jstring);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSONArray jsonArray = null;
                        try {
                            if (jobj != null) {
                                jsonArray = jobj.getJSONArray("recipes");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < 3; i++){
                            if (jsonArray != null) {
                                try {
                                    JSONObject recipe = (JSONObject)jsonArray.get(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            //titles.add((String)recipe.get("title"));
                                //recipeURLs.add((String)recipe.get("href"));
                            }*/
                        adapter = new QTDataAdapter();
                        listView.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ERROR", error.toString());
            }
        });


        queue.add(stringRequest);

    }
    private class QTDataAdapter extends BaseAdapter {
        private ArrayList<QTellerData> qTellerDataArrayList;

        @Override
        public int getCount() {
            return qTellerDataArrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final int index = i;

            if(view == null){

                LayoutInflater mInflater = (LayoutInflater) getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                view = mInflater.inflate(R.layout.qt_so_data, null);
                QTellerData qTellerData=qTellerDataArrayList.get(index);
                if(qTellerData !=null){
                    paymentRef =qTellerData.getPaymentRef();
                    paymentFName =qTellerData.getFirstName();
                    paymentLName =qTellerData.getLastName();
                    paymentAmt =qTellerData.getAmount();
                    paymentDate =qTellerData.getDate();
                    paymentEmail =qTellerData.getEmailAddress();
                    paymentProvider =qTellerData.getProvider();
                    paymentNo=getCount();
                    paymentPurpose =qTellerData.getPurpose();


                }
                AppCompatImageView icon =  view.findViewById(R.id.provider_icon);
                AppCompatTextView txtNo = view.findViewById(R.id.provider_No);
                AppCompatTextView txtProviderName = view.findViewById(R.id.provider_Names);
                AppCompatTextView txtFirstName = view.findViewById(R.id.f_Name_Provider);
                AppCompatTextView txtLastName = view.findViewById(R.id.lName_provider);
                AppCompatTextView txtEmailAddress = view.findViewById(R.id.email_provider);
                AppCompatTextView txtRef = view.findViewById(R.id.ref_provider);
                AppCompatTextView txtDate = view.findViewById(R.id.sDate_provider);
                AppCompatTextView txtAmount = view.findViewById(R.id.amt_provider);
                AppCompatTextView txtPurpose = view.findViewById(R.id.purpose_provider);

                txtNo.setText(MessageFormat.format("Payment Count :{0}", paymentNo));
                txtProviderName.setText(MessageFormat.format("Payment Provider :{0}", paymentProvider));
                txtFirstName.setText(MessageFormat.format("First Name :{0}", paymentFName));
                txtLastName.setText(MessageFormat.format("Last Name :{0}", paymentLName));
                txtEmailAddress.setText(MessageFormat.format("Email :{0}", paymentEmail));
                txtRef.setText(MessageFormat.format("Ref :{0}", paymentRef));
                txtDate.setText(MessageFormat.format("Date :{0}", paymentDate));
                txtAmount.setText(MessageFormat.format("Amount NGN:{0}", Utils.awajimaAmountFormat(paymentAmt)));
                txtPurpose.setText(MessageFormat.format("Purpose :{0}", paymentPurpose));


            }
            return view;
        }





    }

    public class QTellerData{
        private String firstName;
        private String lastName;
        private String emailAddress;
        private String date;
        private long amount;
        private String paymentRef;
        private String currency;
        private String purpose;
        private String provider;

        QTellerData(){
         super();
        }
        QTellerData(String paymentRef1, String lastName1, String firstName1,String emailAddress1,long amount1,String currency1,String purpose1,String provider1,String date1){

            paymentRef = paymentRef1;
            lastName = lastName1;
            firstName = firstName1;
            emailAddress = emailAddress1;
            amount = amount1;
            date = date1;
            currency = currency1;
            purpose = purpose1;
            provider = provider1;
        }


        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }

        public String getPaymentRef() {
            return paymentRef;
        }

        public void setPaymentRef(String paymentRef) {
            this.paymentRef = paymentRef;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getPurpose() {
            return purpose;
        }

        public void setPurpose(String purpose) {
            this.purpose = purpose;
        }

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }
    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;

    }

}