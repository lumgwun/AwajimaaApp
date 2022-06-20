package com.skylightapp.Classes;

public class GeneralItem {
    private String string;
    private int no;

    public GeneralItem(String string, int no) {
        this.string = string;
        this.no = no;
    }

    public String getItem() {
        return string;
    }

    public void setItem(String string) {
        this.string = string;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
}
