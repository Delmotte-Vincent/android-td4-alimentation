package com.main.exercice2.androidproject;

import android.graphics.drawable.Drawable;

import com.main.exercice2.androidproject.Commercant.CommercantObjet;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class CommercantList {
    static ArrayList<CommercantObjet> commercants = new ArrayList<>();

    public static CommercantObjet findCommercant(String mailIn , String passIn){
        for (int i = 0 ; i<commercants.size() ;i++){
            if(commercants.get(i).getEmail().equals(mailIn) && commercants.get(i).getPass().equals(passIn)){
                return commercants.get(i) ;
            }
        }
        return null ;
    }

    public static CommercantObjet findClientId (int id){
        for (int i = 0 ; i<commercants.size() ;i++){
            if(commercants.get(i).getId()==id ){
                return commercants.get(i) ;
            }
        }
        return null ;
    }


    public static void add(CommercantObjet commercant){
        commercants.add(commercant);
    }

    public static void post(int id , CommercantObjet commercant){
        commercants.set(id,commercant);
    }

    public static boolean exist(String mail){
        for (int i = 0 ; i<commercants.size() ;i++){
            if(commercants.get(i).getEmail().equals(mail)){
                return true ;
            }
        }
        return false ;
    }

    public static int nouveau(String name, String horaires, String email , GeoPoint geoPoint , Drawable drawable, String pass, String description,int phoneNumber){
        CommercantObjet commercant = new CommercantObjet(name,horaires,description,drawable,geoPoint,email, pass,phoneNumber,maxId()+1);
        add(commercant);
        return commercant.getId();
    }

    public static int maxId(){
        int max = commercants.get(0).getId() ;
        for (int i = 1 ; i<commercants.size() ;i++){
            if(commercants.get(i).getId()>(max)){
                max = commercants.get(i).getId() ;
            }
        }
        return max ;
    }

    public static ArrayList<CommercantObjet> getCommercants() {
        return commercants;
    }
}
