package com.skylightapp.Classes;

public class WebData {
    public final String title;
    public final String imageFilename;
    public final String description;
    public final String link;

    private WebData(String title, String imageFilename, String description, String link) {
        this.title = title;
        this.imageFilename = imageFilename;
        this.description = description;
        this.link = link;
    }
}

