package com.skylightapp.Classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class TwilloController {
    @Autowired
    private TwilloMessageSenderService twilloService;

    //https://api.twilio.com/2010-04-01/

    @PostMapping("/sendSMS")
    public String sendSMSByTwillo(@RequestBody TwilloMessage messageRequest) {
        String sendMessageResponse = twilloService.sendMessage(messageRequest);
        return sendMessageResponse;
    }
}
