package com.skylightapp.Classes;

import java.util.Date;

public class FirebaseImportantDay {
    public String name;
    public int dateDay;
    public int dateMonth;
    public int dateYear;
    public boolean remind;
    public boolean showYear;
    public String uID;

    // Required empty constructor
    public FirebaseImportantDay(String name, Date dateOfBirthday, int yearOfBirth, boolean remind, boolean includeYear, int birthdayID) {

    }

    public FirebaseImportantDay(String name, Date dateOfBirthday, int year, boolean notifyUserOfBirthday, boolean includeYear, String uID) {

        this.name = name;
        this.remind = notifyUserOfBirthday;
        this.showYear = includeYear;
        dateDay = dateOfBirthday.getDate();
        dateMonth = dateOfBirthday.getMonth();
        dateYear = year;
        this.uID = uID;
    }

    public static FirebaseImportantDay convertToFirebaseBirthday(Birthday birthday) {
        return new FirebaseImportantDay(birthday.getbFirstName(), birthday.getbDate(), birthday.getbYearOfBirth(),
                birthday.getbRemind(), birthday.shouldIncludeYear(), birthday.getBirthdayID());
    }
}
