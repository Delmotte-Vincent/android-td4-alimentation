package com.main.exercice2.androidproject;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Abonnement {
    private  int idClient ;
    private int idCommercant ;
    private ArrayList<String> categories ;

    Abonnement(int idClient , int idCommercant ){
        this.idClient=idClient;
        this.idCommercant= idCommercant;
        this.categories = new ArrayList<>();
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdCommercant() {
        return idCommercant;
    }

    @NonNull
    @Override
    public String toString() {
        return CommercantList.findClientId(idCommercant).toString();
    }
}
