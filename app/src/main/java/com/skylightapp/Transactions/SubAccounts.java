package com.skylightapp.Transactions;

public class SubAccounts {
    public String dosubaccountslist(SubAccountPayload subaccountpayload){

        AccountServices accountservices = new AccountServices();
        String response = accountservices.dolistsubaccounts(subaccountpayload);
        return response;
    }

    public String dosubaccountsget(SubAccountPayload subaccountpayload){

        AccountServices accountservices = new AccountServices();
        String response = accountservices.dogetsubaccounts(subaccountpayload);
        return response;
    }

    public String dosubaccountscreate(SubAccountPayload subaccountpayload){

        AccountServices accountservices = new AccountServices();
        String response = accountservices.docreatesubaccounts(subaccountpayload);
        return response;
    }

    public String dosubaccountsdelete(SubAccountPayload subaccountpayload){

        AccountServices accountservices = new AccountServices();
        String response = accountservices.dodeletesubaccounts(subaccountpayload);
        return response;
    }
}
