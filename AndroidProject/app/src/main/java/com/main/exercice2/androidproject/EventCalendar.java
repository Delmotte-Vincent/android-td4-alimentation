package com.main.exercice2.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.Bundle;

import android.app.TimePickerDialog;
import android.provider.CalendarContract.*;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class EventCalendar extends AppCompatActivity implements
        View.OnClickListener {

    Button btnDate, btnStartTime, btnEndTime, ajout_calendrier_button;
    TextView date_text, start_time_text, end_time_text;
    EditText titre_text, description_text, location_text;
    CheckBox all_day_option, alarm_option;
    private int startHour, startMinute, endHour, endMinute;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_calendar);

        btnDate = (Button) findViewById(R.id.btn_date);
        btnStartTime = (Button) findViewById(R.id.btn_start_time);
        btnEndTime = (Button) findViewById(R.id.btn_end_time);
        ajout_calendrier_button = (Button) findViewById(R.id.ajout_calendrier_button);
        date_text = (TextView) findViewById(R.id.date_text);
        start_time_text = (TextView) findViewById(R.id.start_time_text);
        end_time_text = (TextView) findViewById(R.id.end_time_text);

        Intent intent = getIntent();

        titre_text = (EditText) findViewById(R.id.titre_text);
        description_text = (EditText) findViewById(R.id.description_text);

        titre_text.setText(intent.getStringExtra("titre"));
        description_text.setText(intent.getStringExtra("description"));

        location_text = (EditText) findViewById(R.id.location_text);
        all_day_option = (CheckBox) findViewById(R.id.all_day_option);
        alarm_option = (CheckBox) findViewById(R.id.alarm_option);

        btnDate.setOnClickListener(this);
        btnStartTime.setOnClickListener(this);
        btnEndTime.setOnClickListener(this);
        ajout_calendrier_button.setOnClickListener(this);

        ActivityCompat.requestPermissions(EventCalendar.this, new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, PackageManager.PERMISSION_GRANTED);

    }

    @Override
    public void onClick(View v) {
        if (v == btnDate) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            date_text.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

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

    public void setTime(final TextView time, int hour, int minute) {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay + ":" + ((minute < 10) ? "0" + minute : minute));
                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }

    public void addEvent() {
        if (verify()) {
            Cursor cursor = getContentResolver().query(Uri.parse("content://com.android.calendar/calendars"),
                    new String[]{"_id", "name"}, null, null, null);
            cursor.moveToFirst();
            // Get calendars name
            String[] calendarNames = new String[cursor.getCount()];
            if (cursor.getCount() == 0) {
                Toast.makeText(this, "Impossible d'ajouter à l'agenda", Toast.LENGTH_SHORT).show();
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

            long[] tabTime = calculTime(cal);
            // All Day Event
            if (all_day_option.isChecked()) {
                contentEvent.put("dtstart", tabTime[0]);
                contentEvent.put("dtend", tabTime[1]);
                contentEvent.put("allDay", 1);
            } else {
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

            // on doit vérifier la permission pour mettre une alarme
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            if (alarm_option.isChecked()) {
                // Ajout d'un rappel/alarme
                Uri url = getContentResolver().insert(Events.CONTENT_URI, contentEvent);
                long eventId = Long.parseLong(url.getLastPathSegment());
                ContentValues reminder = new ContentValues();
                reminder.put(Reminders.EVENT_ID, eventId);
                reminder.put(Reminders.MINUTES, 10);
                reminder.put(Reminders.METHOD, Reminders.METHOD_ALERT);
                // event is added successfully
                getContentResolver().insert(Reminders.CONTENT_URI, reminder);
            }
            else{
                // event is added successfully
                getContentResolver().insert(eventsUri, contentEvent);
            }

            cursor.close();
            Toast.makeText(this,"Vous avez créé un événement dans votre agenda",Toast.LENGTH_LONG).show();
        }
    }

    /*
     * Fonction qui vérifie que les champs nécessaires soient renseignés
     */
    private boolean verify() {
        if (all_day_option.isChecked()){
            return true;
        }
        if (start_time_text.getText().toString().isEmpty()){
            Toast.makeText(this,"Il faut sélectionner une heure ou cocher \"Acitiver toute la journée\"",Toast.LENGTH_LONG).show();
            return false;
        }
        if (end_time_text.getText().toString().isEmpty()){
            Toast.makeText(this,"Il faut sélectionner une heure ou cocher \"Acitiver toute la journée\"",Toast.LENGTH_LONG).show();
            return false;
        }
        String [] start = start_time_text.getText().toString().split(":");
        startHour = Integer.parseInt(start[0]);
        startMinute = Integer.parseInt(start[1]);

        String [] end = end_time_text.getText().toString().split(":");
        endHour = Integer.parseInt(end[0]);
        endMinute = Integer.parseInt(end[1]);
        if (startHour>endHour || (startHour==endHour&&startMinute>endMinute)){
            Toast.makeText(this,"Impossible : la fin de l'événement est avant le début",Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            return true;
        }
    }

    // Fonction qui calcule la date, l'heure de départ et l'heure de fin de l'événement
    private long[] calculTime(Calendar cal) {
        String [] start;
        if (start_time_text.getText().toString().isEmpty()){
            // si l'heure n'est pas renseigné ça veut dire qu'on a activé l'événement toute la journée
            // par défaut on met les valeurs 24 car sinon on a un jour de retard
            start = "24:24".split(":");
        }
        else{
            start = start_time_text.getText().toString().split(":");
        }
        startHour = Integer.parseInt(start[0]);
        startMinute = Integer.parseInt(start[1]);

        String [] end;
        if (end_time_text.getText().toString().isEmpty()){
            // si l'heure n'est pas renseigné ça veut dire qu'on a activé l'événement toute la journée
            // par défaut on met les valeurs 24 car sinon on a un jour de retard
            end = "24:24".split(":");
        }
        else{
            end = end_time_text.getText().toString().split(":");
        }
        endHour = Integer.parseInt(end[0]);
        endMinute = Integer.parseInt(end[1]);

        Date date1= null;
        long newTime;
        String sDate1 = date_text.getText().toString();
        // calcul à partir de la date si elle est renseignée ou non
        if (sDate1.isEmpty()){
            // la date n'est pas renseignée l'événement aura lieu le jour même
            newTime=cal.getTimeInMillis();
            startHour = startHour - cal.getTime().getHours();
            startMinute = startMinute - cal.getTime().getMinutes();
            endHour = endHour - cal.getTime().getHours();
            endMinute = endMinute - cal.getTime().getMinutes();
        }
        else {
            try {
                date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            newTime=date1.getTime();
        }

        long startTime = newTime + startHour*60*60*1000 + startMinute*60*1000;
        long endTime = newTime + endHour*60*60*1000 + endMinute*60*1000;

        long[] tab = new long[2];
        tab[0]=startTime;
        tab[1]=endTime;
        return tab;
    }
}
