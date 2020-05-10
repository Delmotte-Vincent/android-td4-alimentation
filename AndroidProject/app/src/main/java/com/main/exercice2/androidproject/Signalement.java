package com.main.exercice2.androidproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Signalement {

    public static void AlertDialogSMS(final Context context, final EditText editTextNumber, final EditText editTextMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Voulez-vous envoyé un SMS à tous les abonnés ?")
                .setCancelable(false).setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendSMS(context, editTextNumber, editTextMessage);
            }
        })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // on ne fait rien, la boîte de dialogue quitte
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.setTitle("Envoyer SMS ?");
        dialog.show();
    }

    public static void AlertDialogCalendar(final Context context, final String titre, final String description) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

        AlertDialog dialog = builder.create();
        dialog.setTitle("Calendrier");
        dialog.show();
    }

    public static void sendSMS(Context context, EditText editTextNumber, EditText editTextMessage){
        /*
        TelephonyManager tMgr = (TelephonyManager)mAppContext.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
         */

        ArrayList<Abonnement> ab = abonnementList.getCommercant(5);

        for (int i=0;i<ab.size();i++){
            String number = String.valueOf(5554+i);
            String title = editTextNumber.getText().toString();
            String descr = editTextMessage.getText().toString();
            String message = title+" :\n"+descr;

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number,null,message,null,null);
        }

        Toast.makeText(context ,"SMS envoyé a "+ab.size()+" personnes",Toast.LENGTH_LONG).show();
    }
}
