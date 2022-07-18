package com.skylightapp.Classes;

import java.util.ArrayList;

public class BirthdaysLoadedEvent {
    private final ArrayList<Birthday> birthdays;

    public BirthdaysLoadedEvent(ArrayList<Birthday> birthday) {
        this.birthdays = birthday;
    }

    public ArrayList<Birthday> getBirthdays() {
        return birthdays;
    }
}
