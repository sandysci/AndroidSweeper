package com.androidsweeper;

/**
 * Created by USER on 10/4/2017.
 */
public class ItemObject {


    private String path;
    private int imageID;
    private boolean isChecked = false;
    private boolean isVisible = false;

    public boolean isVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }



    public ItemObject(){

    }
    public ItemObject(String path,int imageID,boolean isChecked , boolean isVisible){
        this.path = path;
        this.imageID = imageID;
        this.isChecked = isChecked;
        this.isVisible = isVisible;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }



}