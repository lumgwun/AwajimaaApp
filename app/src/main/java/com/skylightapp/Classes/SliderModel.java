package com.skylightapp.Classes;

public class SliderModel {
    private int sliderModelID;
    private String banner;
    private String backgroundColor;
    private SliderModel sliderModel;

    public SliderModel(int sliderModelID,String banner, String backgroundColor) {
        this.sliderModelID = sliderModelID;
        this.banner = banner;
        this.backgroundColor = backgroundColor;
    }
    public SliderModel(String banner, String backgroundColor) {
        this.banner = banner;
        this.backgroundColor = backgroundColor;
    }

    public SliderModel() {
        super();
    }

    public SliderModel(SliderModel sliderModel) {
        this.sliderModel = sliderModel;

    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
