package com.skylightapp.Transactions;

public class SuggestedLoad {
    public String getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(String request) {
        this.request = request;
    }

    /**
     * @return the suggested_auth
     */
    public String getSuggested_auth() {
        return suggested_auth;
    }

    /**
     * @param suggested_auth the suggested_auth to set
     */
    public void setSuggested_auth(String suggested_auth) {
        this.suggested_auth = suggested_auth;
    }
    private String suggested_auth;
    private String request;
}
