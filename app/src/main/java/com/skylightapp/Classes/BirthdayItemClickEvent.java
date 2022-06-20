package com.skylightapp.Classes;

public class BirthdayItemClickEvent {
    private final Birthday birthday;

    public BirthdayItemClickEvent(Birthday birthday) {
        this.birthday = birthday;
    }


    public Birthday getBirthday() {
        return birthday;
    }
}
