package com.skylightapp.Classes;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.skylightapp.BuildConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.Message.Status;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;


public class TwilloMessageSenderService {
    private static final Logger logger = LoggerFactory.getLogger(TwilloMessageSenderService.class);

    @Value("${accountSID}")
    private String accountSID="AC5e05dc0a793a29dc1da2eabdebd6c28d";

    @Value("${accountAuthToken}")
    private String accountAuthToken="39410e8b813c131da386f3d7bb7f94f7";

    @Value("${twilloSenderNumber}")
    private String twilloSenderNumber= BuildConfig.TWILLO_NO;

    public String sendMessage(TwilloMessage messageRequest) {
        try {
            Twilio.init(accountSID, accountAuthToken);

            String smsText = messageRequest.getSmsText();
            String mobileNumber = messageRequest.getMobileNumber();

            PhoneNumber recieverPhoneNumber = new PhoneNumber(mobileNumber);
            PhoneNumber senderTwilloPhoneNumber = new PhoneNumber(twilloSenderNumber);

            MessageCreator creator = com.twilio.rest.api.v2010.account.Message.creator(recieverPhoneNumber, senderTwilloPhoneNumber, smsText);
            Message create = creator.create();

            String billingAmount = create.getPrice();
            Status status = create.getStatus();

            logger.info("Message Send Succesfully to the number " + mobileNumber);
            return "Message Send Succesfully";
        } catch (Exception e) {
            logger.error("Exception in sendMessage Method " + e);
            return "Message Send Fail";
        }

    }
}
