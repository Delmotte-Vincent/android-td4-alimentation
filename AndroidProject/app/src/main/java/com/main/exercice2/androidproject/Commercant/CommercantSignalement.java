package com.main.exercice2.androidproject.Commercant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.main.exercice2.androidproject.Abonnement;
import com.main.exercice2.androidproject.EventCalendar;
import com.main.exercice2.androidproject.Signalement;
import com.main.exercice2.androidproject.abonnementList;
import com.main.exercice2.androidproject.R;

import java.util.ArrayList;

public class CommercantSignalement extends AppCompatActivity {

    private EditText editTextNumber;
    private EditText editTextMessage;
    private Button but_signal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercant_signalement);

        abonnementList.addAbonnement(1, 5);
        abonnementList.addAbonnement(2, 5);
        abonnementList.addAbonnement(3, 5);
        abonnementList.addAbonnement(1, 6);


        ActivityCompat.requestPermissions(CommercantSignalement.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, PackageManager.PERMISSION_GRANTED);

        editTextNumber = findViewById(R.id.titre_signal);
        editTextMessage = findViewById(R.id.desc_signal);

        but_signal = findViewById(R.id.but_signal);

        but_signal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Signalement.AlertDialogCalendar(CommercantSignalement.this);
                Signalement.AlertDialogSMS(CommercantSignalement.this, editTextNumber, editTextMessage);
            }
        });
    }
}
