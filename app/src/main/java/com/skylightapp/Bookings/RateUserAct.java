package com.skylightapp.Bookings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.skylightapp.LoginDirAct;
import com.skylightapp.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RateUserAct extends AppCompatActivity implements View.OnClickListener, RatingBar.OnRatingBarChangeListener{
    RatingBar ratingBar;
    AppCompatButton acceptButton;
    AppCompatTextView ratingText;
    boolean ratingChanged = false;
    String rideId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_rate_user);
        assignViews();
    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
    private void assignViews() {
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        ratingBar.setOnRatingBarChangeListener(this);

        ratingText = (AppCompatTextView) findViewById(R.id.rating_label);

        acceptButton = findViewById(R.id.accept_rating_button);
        acceptButton.setOnClickListener(this);

        rideId = getIntent().getExtras().getString("rideId");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rate_driver, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            default:
                break;
            case R.id.accept_rating_button:
                if(ratingChanged)
                {
                    sendDriverRating();
                }
                else
                {
                    ratingText.setError("Please rate your client");
                }
                break;
        }
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        ratingChanged = true;
    }

    private void sendDriverRating()
    {
        float ratingStars = ratingBar.getRating();
        int ratingInt = (int) ratingStars;

        StringBuilder sb = new StringBuilder();
        sb.append(getResources().getString(R.string.ip));
        sb.append("awajima/ride/rating/driver?rideid=");
        sb.append(rideId);
        sb.append("&rating=");
        sb.append(ratingInt);

        URLpetition petition = new URLpetition("send driver rating");
        petition.execute(sb.toString());
    }

    private class URLpetition extends AsyncTask<String, Void, String>
    {
        String action;
        public URLpetition(String action)
        {
            this.action = action;
        }
        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            Log.d("url = ", params[0]);
            HttpGet get = new HttpGet(params[0]);
            String retorno="";
            StringBuilder stringBuilder = new StringBuilder();
            try {
                HttpResponse response = client.execute(get);
                HttpEntity entity = response.getEntity();
                //InputStream stream = new InputStream(entity.getContent(),"UTF-8");
                InputStream stream = entity.getContent();
                BufferedReader r = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                String line;
                while ((line= r.readLine()) != null) {
                    stringBuilder.append(line);
                }
                return stringBuilder.toString();

            }
            catch(IOException e) {
                Log.d("Error: ", e.getMessage());
            }
            Log.d("Return text = ", retorno);
            return retorno;
        }

        @Override
        protected void onPostExecute(String result) {
            switch (action)
            {
                default:
                    break;
                case "send driver rating":
                    if (result.equals("Rating sent"))
                    {
                        finishRide();
                    }
                    break;
            }
        }

        @Override
        protected void onPreExecute() {}
    }

    private void finishRide()
    {
        Intent intent = new Intent(this, LoginDirAct.class);
        startActivity(intent);
        finish();
    }
}