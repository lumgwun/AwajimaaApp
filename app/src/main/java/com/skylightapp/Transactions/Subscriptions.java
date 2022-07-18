package com.skylightapp.Transactions;

import java.io.IOException;

public class Subscriptions {
    public String dolistsubscriptions() {
        AccountServices accountservices = new AccountServices();

        String response = accountservices.dolistsubscriptions();
        return response;
    }

    public String dofetchsubscriptions(Suscriptionfetch suscriptionfetch) {
        AccountServices accountservices = new AccountServices();

        String response = null;
        try {
            response = accountservices.dofetchsubscription(suscriptionfetch);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String docancelsubscriptions(Suscriptionfetch suscriptionfetch) throws IOException {
        AccountServices accountservices = new AccountServices();

        String response = accountservices.docancelsubscription(suscriptionfetch);
        return response;
    }

    public String doactivatesubscriptions(Suscriptionfetch suscriptionfetch) {
        AccountServices accountservices = new AccountServices();

        String response = accountservices.doactivatesubscription(suscriptionfetch);
        return response;
    }
}
