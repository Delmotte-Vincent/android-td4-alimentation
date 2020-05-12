package com.main.exercice2.androidproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.EditText;
import android.widget.Toast;

import com.main.exercice2.androidproject.Client.Client;

import java.util.ArrayList;

public class AlertDialogCustom {

    // Fonction qui permet d'ouvrir une fenêtre demandant à l'utilisateur s'il veut envoyer des SMS
    public static void AlertDialogSMS(final Context context, final EditText editTextNumber, final EditText editTextMessage, final int idCommercant, final int phoneNumber) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setMessage("Voulez-vous envoyé un SMS à tous les abonnés ?")
                .setCancelable(false).setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendSMS(context, editTextNumber, editTextMessage, idCommercant, phoneNumber);
            }
        })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // on ne fait rien, la boîte de dialogue quitte
                    }
                });

        android.app.AlertDialog dialog = builder.create();
        dialog.setTitle("Envoyer SMS ?");
        dialog.show();
    }

    // Fonction qui permet d'ouvrir une fenêtre demandant à l'utilisateur s'il veut ajouter un événement à son agenda
    public static void AlertDialogCalendar(final Context context, final String titre, final String description) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setMessage("Voulez-vous ajouter un événement à votre agenda ?")
                .setCancelable(false).setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(context, EventCalendar.class);
                intent.putExtra("titre",titre);
                intent.putExtra("description",description);
                context.startActivity(intent);
            }
        })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // on ne fait rien, la boîte de dialogue quitte
                    }
                });

        android.app.AlertDialog dialog = builder.create();
        dialog.setTitle("Calendrier");
        dialog.show();
    }

    public static void sendSMS(Context context, EditText editTextNumber, EditText editTextMessage, int idCommercant, int phoneNumber){
        ArrayList<Abonnement> ab = abonnementList.getCommercant(idCommercant);
        System.out.println(ab);
        System.out.println(ab.size());
        System.out.println(idCommercant);
        System.out.println(phoneNumber);
        SmsManager smsManager = SmsManager.getDefault();
        String title = editTextNumber.getText().toString();
        String descr = editTextMessage.getText().toString();
        String message = (title.equals("")?"Sans titre":title)+" :\n"+(descr.equals("")?"Sans description":descr);

        // on parcourt tout les clients du commerçant
        for (int i=0;i<ab.size();i++){
            // Cas du client
            Client client = ClientList.findClientId(ab.get(i).getIdClient());
            // on vérifie qu'on ne s'envoie pas de message à soi-même
            if (phoneNumber!=client.getPhoneNumber()){
                smsManager.sendTextMessage(String.valueOf(phoneNumber),null,message,null,null);
            }
        }
        // Cas du commerçant
        // on vérifie qu'on ne s'envoie pas de message à soi-même
        if (phoneNumber!=CommercantList.findClientId(idCommercant).getPhoneNumber()){
            smsManager.sendTextMessage(String.valueOf(phoneNumber),null,message,null,null);
        }

        Toast.makeText(context ,"SMS envoyé a "+ab.size()+" personnes",Toast.LENGTH_LONG).show();
    }
}
