package com.skylightapp.Transactions;

public class BillLoad {
    public Service_payload getService_payload() {
        return service_payload;
    }


    public void setService_payload(Service_payload service_payload) {
        this.service_payload = service_payload;
    }


    public String getSecret_key() {
        return secret_key;
    }


    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }


    public String getService() {
        return service;
    }


    public void setService(String service) {
        this.service = service;
    }


    public String getService_method() {
        return service_method;
    }

    public void setService_method(String service_method) {
        this.service_method = service_method;
    }

    public String getService_version() {
        return service_version;
    }


    public void setService_version(String service_version) {
        this.service_version = service_version;
    }


    public String getService_channel() {
        return service_channel;
    }

    public void setService_channel(String service_channel) {
        this.service_channel = service_channel;
    }

    private String secret_key;
    private String service;
    private String service_method;
    private String service_version;
    private String service_channel;
    private Service_payload service_payload;
}
