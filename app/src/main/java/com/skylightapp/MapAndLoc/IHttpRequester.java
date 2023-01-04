package com.skylightapp.MapAndLoc;

public interface IHttpRequester {
    public String get(String url) throws Exception;
    public String post(String url,String json) throws Exception;
}
