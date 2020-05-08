package com.main.exercice2.androidproject.Commercant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
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


        ActivityCompat.requestPermissions(CommercantSignalement.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, PackageManager.PERMISSION_GRANTED);

        editTextNumber = findViewById(R.id.titre_signal);
        editTextMessage = findViewById(R.id.desc_signal);

        but_signal = findViewById(R.id.but_signal);

        but_signal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // sendSMS(v);
                // addEventCalendarAuto();
                addEvent();
            }
        });
    }

    public void sendSMS(View view){

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

    }

    public void addEventCalendarAuto(){
        String[] projection = new String[] { "_id", "name" };
        Uri calendars = Uri.parse("content://calendar/calendars");

        Cursor managedCursor = managedQuery(calendars, projection, "selected=1", null, null);

        /*String calName;
        String calId="";

        if (managedCursor.moveToFirst()) {

            int nameColumn = managedCursor.getColumnIndex("name");
            int idColumn = managedCursor.getColumnIndex("_id");
            do {
                calName = managedCursor.getString(nameColumn);
                calId = managedCursor.getString(idColumn);
            } while (managedCursor.moveToNext());
        }*/

        ContentValues event = new ContentValues();
        event.put("calendar_id", 1);

        event.put("title", "Event Title");
        event.put("description", "Event Desc");
        // event.put("eventLocation", "Event Location");


        Calendar cal = Calendar.getInstance();
        long startTime = cal.getTimeInMillis();
        long endTime = startTime+60*60*100;
        event.put("dtstart", startTime);
        event.put("dtend", endTime);

        /*
        event.put("allDay", 1);   // 0 for false, 1 for true
        event.put("eventStatus", 1);
        event.put("visibility", 0);
        event.put("transparency", 0);
        event.put("hasAlarm", 1); // 0 for false, 1 for true
         */

        Uri eventsUri = Uri.parse("content://com.android.calendar/events");
        Uri url = getContentResolver().insert(eventsUri, event);
    }

    public void addEvent(){
        Cursor cursor=getContentResolver().query(Uri.parse("content://com.android.calendar/calendars"),
                new String[]{"_id", "name"}, null, null, null);
        cursor.moveToFirst();
        // Get calendars name
        String[] calendarNames = new String[cursor.getCount()];
        // Get calendars id
        int[] calendarId = new int[cursor.getCount()];
        for (int i = 0; i < calendarNames.length; i++)
        {
            calendarId[i] = cursor.getInt(0);
            calendarNames[i] = cursor.getString(1);
            cursor.moveToNext();
        }

        ContentValues contentEvent = new ContentValues();
        // Particular Calendar in which we need to add Event
        contentEvent.put("calendar_id", calendarId[0]);
        // Title/Caption of the Event
        contentEvent.put("title", "Wedding");
        // Description of the Event
        contentEvent.put("description", "Wedding Party");
        // Venue/Location of the Event
        contentEvent.put("eventLocation", "New York");

        Calendar cal = Calendar.getInstance();
        long startTime = cal.getTimeInMillis();
        long endTime = startTime+60*60*1000;
        // Start Date of the Event with Time
        contentEvent.put("dtstart", startTime);
        // End Date of the Event with Time
        contentEvent.put("dtend", endTime);
        // All Day Event
        // contentEvent.put("allDay", 1);
        // Set alarm for this Event
        contentEvent.put("hasAlarm",1);
        contentEvent.put("eventTimezone", TimeZone.getDefault().getID());
        Uri eventsUri = Uri.parse("content://com.android.calendar/events");
        // event is added successfully
        getContentResolver().insert(eventsUri, contentEvent);
        cursor.close();
    }
}
