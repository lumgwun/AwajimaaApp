package com.skylightapp.Classes;

public class GridItem {
    private String course_name;
    private int imgid;

    public GridItem(String course_name, int imgid) {
        this.course_name = course_name;
        this.imgid = imgid;
    }

    public String getGridTitle() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }
}
