package com.main.exercice2.androidproject;

import android.graphics.drawable.Drawable;

import com.main.exercice2.androidproject.Commercant.CommercantObjet;

public class Post {

    private String title;
    private String message;
    private String type;
    private Drawable drawable;
    private Boolean defaultPicture;
    private CommercantObjet commercant;

    public Post(String title, String message, String type, Drawable drawable, Boolean defaultPicture, CommercantObjet commercant) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.drawable=drawable;
        this.defaultPicture = defaultPicture;
        this.commercant=commercant;
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

    public void setDefaultPicture(Boolean b) {
        this.defaultPicture = b;
    }

    public Boolean getDefaultPicture() {
        return this.defaultPicture;
    }

    public CommercantObjet getCommercant (){return this.commercant;}


}
