package com.skylightapp.Classes;

public class ChartData {
    private String data;
    private  double value;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ChartData() {
        super();

    }
    public ChartData( String data) {
        super();
        this.data = data;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
