package com.main.exercice2.androidproject.Commercant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.main.exercice2.androidproject.Abonnement;
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

        abonnementList.addAbonnement(1,5);
        abonnementList.addAbonnement(2,5);
        abonnementList.addAbonnement(3,5);
        abonnementList.addAbonnement(1,6);


        ActivityCompat.requestPermissions(CommercantSignalement.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE}, PackageManager.PERMISSION_GRANTED);

        editTextNumber = findViewById(R.id.titre_signal);
        editTextMessage = findViewById(R.id.desc_signal);

        but_signal = findViewById(R.id.but_signal);

        but_signal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS(v);
                // addEventCalendar();
            }
        });
    }

    public void sendSMS(View view){

        ArrayList<Abonnement> ab = abonnementList.getCommercant(5);

        for (int i=0;i<ab.size();i++){
            String number = String.valueOf(5554+i);
            String title = editTextNumber.getText().toString();
            String descr = editTextMessage.getText().toString();
            String message = title+" :\n"+descr;

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number,null,message,null,null);
        }

    }

    public void addEventCalendar(){
        Calendar cal = Calendar.getInstance();

        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", true);
        intent.putExtra("rrule", "FREQ=YEARLY");
        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        intent.putExtra("title", "A Test Event from android app");
        startActivity(intent);
    }

    public void addEventCalendarAuto(){
        String[] projection = new String[] { "_id", "name" };
        Uri calendars = Uri.parse("content://calendar/calendars");

        Cursor managedCursor = managedQuery(calendars, projection, null, null, null);
    }

}
