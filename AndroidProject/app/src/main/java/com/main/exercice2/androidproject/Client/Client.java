package com.main.exercice2.androidproject.Client;

import java.util.ArrayList;

public class Client {

    private ArrayList abonnements;
    private String firstName;
    private String lastName;
    private String email ;
    private String password ;
    private int id ;

    public Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.abonnements = new ArrayList();
    }

    public Client(String firstName, String lastName,String email ,String password,int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.abonnements = new ArrayList();
        this.email = email ;
        this.password = password ;
        this.id=id ;
    }

    /**
     * Permet d'ajouter un commerçant à la liste d'abonnement
     * @param idCommercant
     */
    public void addAbonnement (int idCommercant) {
        this.abonnements.add(idCommercant);
    }

    public String getEmail() {
        return email;
    }

    public ArrayList getAbonnements() {
        return abonnements;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }
}
