package com.main.exercice2.androidproject.Commercant;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import org.osmdroid.util.GeoPoint;

public class CommercantObjet {
    private String title;
    private String message;
    private String type;
    private Drawable drawable;
    private GeoPoint geoPoint;
    private String email ;
    private  String pass ;
    private int id ;

    public CommercantObjet(String title, String message, String type, Drawable drawable,GeoPoint geopoint,String email , String pass , int id) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.drawable=drawable;
        this.geoPoint=geopoint;
        this.email = email ;
        this.pass = pass ;
        this.id = id ;
    }
    public CommercantObjet(String title,String message,Drawable drawable){
        this.title = title;
        this.message = message;
        this.drawable=drawable;
    }
    public CommercantObjet(String title){
        this.title=title;
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

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public Drawable getDrawable(){return this.drawable;}

    public GeoPoint getGeoPoint(){return this.geoPoint;}

    @NonNull
    @Override
    public String toString() {
        return title;
    }
}
