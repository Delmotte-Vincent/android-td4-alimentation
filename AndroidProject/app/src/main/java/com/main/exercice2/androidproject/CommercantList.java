package com.main.exercice2.androidproject;

import com.main.exercice2.androidproject.Commercant.Commercant;

import java.util.ArrayList;

public class CommercantList {
    static ArrayList<Commercant> commercants = new ArrayList<>();

    public static Commercant findCommercant(String mailIn , String passIn){
        for (int i = 0 ; i<commercants.size() ;i++){
            if(commercants.get(i).getEmail().equals(mailIn) && commercants.get(i).getPass().equals(passIn)){
                return commercants.get(i) ;
            }
        }
        return null ;
    }

    public static Commercant findClientId (int id){
        for (int i = 0 ; i<commercants.size() ;i++){
            if(commercants.get(i).getId()==id ){
                return commercants.get(i) ;
            }
        }
        return null ;
    }


    public static void add(Commercant commercant){
        commercants.add(commercant);
    }

    public static void post(int id , Commercant commercant){
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

    public static int nouveau(String name, String horaires,String email ,String pass,String description){
        Commercant commercant = new Commercant(name,horaires,email,pass,description,maxId()+1);
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
}
