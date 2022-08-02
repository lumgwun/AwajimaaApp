package com.skylightapp.Classes;

import com.skylightapp.BuildConfig;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;


import static com.skylightapp.BuildConfig.TWILLO_NO;
import static com.skylightapp.BuildConfig.T_ACCT_SID;
import static com.skylightapp.BuildConfig.T_AUTH_TOKEN;
import static spark.Spark.get;
import static spark.Spark.post;

public class SMSBackend {
    public static void main(String[] args) {
        get("/", (req, res) -> "Skylight");

        String TWILLO_ACCOUNT_SID= T_ACCT_SID;
        String TWILLO_AUTH_TOKEN= T_AUTH_TOKEN;
        String TWILLO_PHONE_NO= TWILLO_NO;
        String from = TWILLO_NO;
        String password= BuildConfig.SKYLIGHT_EMAIL_PASSWORD;

        TwilioRestClient client = null;
        if (TWILLO_ACCOUNT_SID != null) {
            if (TWILLO_AUTH_TOKEN != null) {
                client = new TwilioRestClient.Builder(TWILLO_ACCOUNT_SID, TWILLO_AUTH_TOKEN).build();
            }
        }

        TwilioRestClient finalClient = client;
        post("/sms", (req, res) -> {
            String body = req.queryParams("Body");
            String to = req.queryParams("To");

            Message message = new MessageCreator(
                    new PhoneNumber(to),
                    new PhoneNumber(from),
                    body).create(finalClient);

            return message.getSid();
        });
    }
}
