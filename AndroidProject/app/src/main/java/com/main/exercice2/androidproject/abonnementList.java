package com.main.exercice2.androidproject;

import java.util.ArrayList;


public class abonnementList

{
    static ArrayList<Abonnement> abonnements = new ArrayList<>();

    public static ArrayList<Abonnement> getAbonnements()  {
        return abonnements;
    }

    public static ArrayList<Abonnement> getAbonnementClient(int idClient){
        ArrayList<Abonnement> ab =new ArrayList<>();
        for(int i=0 ; i<abonnements.size();i++){
            if(abonnements.get(i).getIdClient() ==idClient){
                ab.add(abonnements.get(i));
            }
        }
        return ab ;
    }


    public static ArrayList<Abonnement> getCommercant(int idCommercant){
        ArrayList<Abonnement> ab =new ArrayList<>();
        for(int i=0 ; i<abonnements.size();i++){
            if(abonnements.get(i).getIdCommercant() ==idCommercant){
                ab.add(abonnements.get(i));
            }
        }
        return ab ;
    }

    public static ArrayList<Abonnement> getAbonnementCommercant(int idCommercant){
        ArrayList<Abonnement> allClient = new ArrayList<>();
        ArrayList<Abonnement> ab = getCommercant(idCommercant);
        for(int i=0 ; i<ab.size();i++){
            allClient.add(ab.get(i));
        }
        return allClient ;
    }

    public static ArrayList<Abonnement> getAbonnementClientCommercant(int idClient , int idCommercant){
        ArrayList<Abonnement> ab =new ArrayList<>();
        for(int i=0 ; i<abonnements.size();i++){
            if(abonnements.get(i).getIdClient() ==idClient && abonnements.get(i).getIdCommercant() == idCommercant ){
                ab.add(abonnements.get(i));
            }
        }
        return ab ;
    }


    public static void  addAbonnement(int idClient , int idCommercant){
        abonnements.add(new Abonnement(idClient,idCommercant));
    }
}
