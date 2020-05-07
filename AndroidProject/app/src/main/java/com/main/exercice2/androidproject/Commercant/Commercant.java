package com.main.exercice2.androidproject.Commercant;

import java.util.ArrayList;

public class Commercant {
    String nom ;
    String horaires;
    String description ;
    String email ;
    String pass ;
    int id ;
    ArrayList<String>categorie ;
    public Commercant(String nom, String horaires, String email, String pass, String description, int id){
        this.nom =nom;
        this.horaires= horaires;
        this.description = description ;
        this.pass = pass;
        this.email =email ;
        this.id = id ;
        categorie = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public String getHoraires() {
        return horaires;
    }

    public String getNom() {
        return nom;
    }

    public String getPass() {
        return pass;
    }

}
