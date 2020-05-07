package com.main.exercice2.androidproject;

import android.graphics.drawable.Drawable;

import org.osmdroid.util.GeoPoint;

public class CommercantObjet {
    private String title;
    private String message;
    private String type;
    private Drawable drawable;
    private GeoPoint geoPoint;

    public CommercantObjet(String title, String message, String type, Drawable drawable,GeoPoint geopoint) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.drawable=drawable;
        this.geoPoint=geopoint;
    }
    public CommercantObjet(String title,String message,Drawable drawable){
        this.title = title;
        this.message = message;
        this.drawable=drawable;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Drawable getDrawable(){return this.drawable;}

    public GeoPoint getGeoPoint(){return this.geoPoint;}
}
