package com.main.exercice2.androidproject;

import com.main.exercice2.androidproject.Client.Client;

import java.util.ArrayList;

public class ClientList {

    static ArrayList<Client> clients = new ArrayList<>();

    public static Client findClients(String mailIn , String passIn){
        for (int i = 0 ; i<clients.size() ;i++){
            if(clients.get(i).getEmail().equals(mailIn) && clients.get(i).getPassword().equals(passIn)){
                return clients.get(i) ;
            }
        }
        return null ;
    }

    public static Client findClientId (int id){
        for (int i = 0 ; i<clients.size() ;i++){
            if(clients.get(i).getId()==id ){
                return clients.get(i) ;
            }
        }
        return null ;
    }

    public static void add(Client client){
        clients.add(client);

    }


    public static void post(int id , Client client){
        clients.set(id,client);
    }

    public static boolean exist(String mail){
        for (int i = 0 ; i<clients.size() ;i++){
            if(clients.get(i).getEmail().equals(mail)){
                return true ;
            }
        }
        return false ;
    }

    public static int nouveau(String firstName, String lastName,String email ,String password, int phoneNumber){
        Client client = new Client(firstName,lastName,email,password,phoneNumber,maxId()+1);
        add(client);
        return client.getId();
    }
    public static int maxId(){
        int max = clients.get(0).getId() ;
        for (int i = 1 ; i<clients.size() ;i++){
            if(clients.get(i).getId()>(max)){
                max = clients.get(i).getId() ;
            }
        }
        return max ;
    }



}
