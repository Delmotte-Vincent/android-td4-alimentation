package com.main.exercice2.androidproject.Client;

import java.util.ArrayList;

public class Client {

    private ArrayList abonnements;
    private String firstName;
    private String lastName;

    public Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.abonnements = new ArrayList();
    }


    /**
     * Permet d'ajouter un commerçant à la liste d'abonnement
     * @param idCommercant
     */
    public void addAbonnement (int idCommercant) {
        this.abonnements.add(idCommercant);
    }
}
