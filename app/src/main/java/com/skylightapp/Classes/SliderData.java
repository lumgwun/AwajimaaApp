package com.skylightapp.Classes;

import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class SliderData {
    private int imgUrl;
    private List<Integer> imgLink;
    private ArrayList<SlideModel> slideModelList;

    public SliderData(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public List<Integer> getImgLink() {
        return imgLink;
    }

    public void setImgLink(List<Integer> imgLink) {
        this.imgLink = imgLink;
    }

    public ArrayList<SlideModel> getSlideModelList() {
        return slideModelList;
    }

    public void setSlideModelList(ArrayList<SlideModel> slideModelList) {
        this.slideModelList = slideModelList;
    }
    public void setSlideModel(SlideModel slideModel) {
        slideModelList = new ArrayList<>();
        slideModelList.add(slideModel);
    }
}
