package com.main.exercice2.androidproject;

import android.graphics.drawable.Drawable;

public class Post {

    private String title;
    private String message;
    private String type;
    private Drawable drawable;
    private Boolean defaultPicture;

    public Post(String title, String message, String type, Drawable drawable, Boolean defaultPicture) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.drawable=drawable;
        this.defaultPicture = defaultPicture;
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


}
