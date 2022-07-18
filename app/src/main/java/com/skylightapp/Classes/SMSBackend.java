package com.skylightapp.Classes;

import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import spark.Spark;


import static com.skylightapp.Classes.AppConstants.ACCOUNT_SID;
import static com.skylightapp.Classes.AppConstants.AUTH_TOKEN;
import static spark.Spark.get;
import static spark.Spark.post;

public class SMSBackend {
    public static void main(String[] args) {
        get("/", (req, res) -> "Skylight");

        TwilioRestClient client = null;
        if (ACCOUNT_SID != null) {
            if (AUTH_TOKEN != null) {
                client = new TwilioRestClient.Builder(ACCOUNT_SID, AUTH_TOKEN).build();
            }
        }

        TwilioRestClient finalClient = client;
        post("/sms", (req, res) -> {
            String body = req.queryParams("Body");
            String to = req.queryParams("To");
            String from = "+15703251701";

            Message message = new MessageCreator(
                    new PhoneNumber(to),
                    new PhoneNumber(from),
                    body).create(finalClient);

            return message.getSid();
        });
    }
}
