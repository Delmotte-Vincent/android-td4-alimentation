
package com.main.exercice2.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.Bundle;

import android.app.TimePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class EventCalendar extends AppCompatActivity implements
        View.OnClickListener {

    Button btnStartTime, btnEndTime, ajout_calendrier_button;
    EditText start_time_text, end_time_text;
    EditText titre_text, description_text, location_text;
    CheckBox all_day_option, alarm_option;
    private int startHour, startMinute, endHour, endMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_calendar);

        btnStartTime=(Button)findViewById(R.id.btn_start_time);
        btnEndTime=(Button)findViewById(R.id.btn_end_time);
        ajout_calendrier_button=(Button)findViewById(R.id.ajout_calendrier_button);
        start_time_text=(EditText)findViewById(R.id.start_time_text);
        end_time_text=(EditText)findViewById(R.id.end_time_text);

        titre_text=(EditText)findViewById(R.id.titre_text);
        description_text=(EditText)findViewById(R.id.description_text);
        location_text=(EditText)findViewById(R.id.location_text);
        all_day_option=(CheckBox) findViewById(R.id.all_day_option);
        alarm_option=(CheckBox)findViewById(R.id.alarm_option);


        btnStartTime.setOnClickListener(this);
        btnEndTime.setOnClickListener(this);
        ajout_calendrier_button.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v == btnStartTime) {
            setTime(start_time_text, startHour, startMinute);
        }

        if (v == btnEndTime) {
            setTime(end_time_text, endHour, endMinute);
        }

        if (v == ajout_calendrier_button) {
            addEvent();
        }
    }

    public void setTime(final EditText time, int hour, int minute){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }

    public void addEvent(){
        if (verify()) {
            Cursor cursor = getContentResolver().query(Uri.parse("content://com.android.calendar/calendars"),
                    new String[]{"_id", "name"}, null, null, null);
            cursor.moveToFirst();
            // Get calendars name
            String[] calendarNames = new String[cursor.getCount()];
            if (cursor.getCount() == 0) {
                Toast.makeText(this, "Impossible d'ajouter au calendrier", Toast.LENGTH_SHORT).show();
                return;
            }
            // Get calendars id
            int[] calendarId = new int[cursor.getCount()];
            for (int i = 0; i < calendarNames.length; i++) {
                calendarId[i] = cursor.getInt(0);
                calendarNames[i] = cursor.getString(1);
                cursor.moveToNext();
            }

            ContentValues contentEvent = new ContentValues();
            // Particular Calendar in which we need to add Event
            contentEvent.put("calendar_id", calendarId[0]);
            // Title/Caption of the Event
            contentEvent.put("title", titre_text.getText().toString());
            // Description of the Event
            contentEvent.put("description", description_text.getText().toString());
            // Venue/Location of the Event
            contentEvent.put("eventLocation", location_text.getText().toString());

            Calendar cal = Calendar.getInstance();

            /*
            // Start Day of the Event
            contentEvent.put("startDay",cal.get(Calendar.DAY_OF_WEEK);
            // End Day of the Event
            contentEvent.put("endDay",cal.get(Calendar.DAY_OF_WEEK));
             */

            // All Day Event
            if (all_day_option.isChecked()) {
                contentEvent.put("dtstart", cal.getTimeInMillis());
                contentEvent.put("dtend", cal.getTimeInMillis());
                contentEvent.put("allDay", 1);
            }
            else{
                long[] tabTime = calculTime(cal);
                // Start Date of the Event with Time
                contentEvent.put("dtstart", tabTime[0]);
                // End Date of the Event with Time
                contentEvent.put("dtend", tabTime[1]);
            }
            // Set alarm for this Event
            if (alarm_option.isChecked()) {
                contentEvent.put("hasAlarm", 1);
            }
            contentEvent.put("eventTimezone", TimeZone.getDefault().getID());
            Uri eventsUri = Uri.parse("content://com.android.calendar/events");
            // event is added successfully
            getContentResolver().insert(eventsUri, contentEvent);
            cursor.close();
            Toast.makeText(this,"Vous avez créé un événement dans votre calendrier",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean verify() {
        if (all_day_option.isChecked()){
            return true;
        }
        if (start_time_text.getText().toString().isEmpty()){
            start_time_text.setError("Sélectionner une heure ou cocher \"Acitiver toute la journée\"");
            return false;
        }
        if (end_time_text.getText().toString().isEmpty()){
            end_time_text.setError("Sélectionner une heure ou cocher \"Acitiver toute la journée\"");
            return false;
        }
        else{
            return true;
        }
    }

    private long[] calculTime(Calendar cal) {
        String [] start = start_time_text.getText().toString().split(":");
        startHour = Integer.parseInt(start[0]);
        startMinute = Integer.parseInt(start[1]);

        String [] end = end_time_text.getText().toString().split(":");
        endHour = Integer.parseInt(end[0]);
        endMinute = Integer.parseInt(end[1]);

        startHour = startHour - cal.getTime().getHours();
        startMinute = startMinute - cal.getTime().getMinutes();
        long startTime = cal.getTimeInMillis() + startHour*60*60*1000 + startMinute*60*1000;

        endHour = endHour - cal.getTime().getHours();
        endMinute = endMinute - cal.getTime().getMinutes();
        long endTime = cal.getTimeInMillis() + endHour*60*60*1000 + endMinute*60*1000;
        long[] tab = new long[2];
        tab[0]=startTime;
        tab[1]=endTime;
        return tab;
    }
}
