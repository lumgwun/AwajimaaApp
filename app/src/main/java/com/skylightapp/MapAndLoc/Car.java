package com.skylightapp.MapAndLoc;

public class Car {
    int iconId;
    Integer title;
    Integer separatorColorId;
    Double costForKm;

    public Car(int iconId, int title, Integer separatorColorId, Double costForKm) {
        this.iconId = iconId;
        this.title = title;
        this.separatorColorId = separatorColorId;
        this.costForKm = costForKm;
    }

    public Car() {
        super();

    }


    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public Integer getTitle() {
        return title;
    }

    public void setTitle(Integer title) {
        this.title = title;
    }

    public Integer getSeparatorColorId() {
        return separatorColorId;
    }

    public void setSeparatorColorId(Integer separatorColorId) {
        this.separatorColorId = separatorColorId;
    }

    public Double getCostForKm() {
        return costForKm;
    }

    public void setCostForKm(Double costForKm) {
        this.costForKm = costForKm;
    }


}
