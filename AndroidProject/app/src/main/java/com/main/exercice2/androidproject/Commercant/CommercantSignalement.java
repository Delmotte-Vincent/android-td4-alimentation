package com.main.exercice2.androidproject.Commercant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.main.exercice2.androidproject.R;

public class CommercantSignalement extends AppCompatActivity {

    private EditText editTextNumber;
    private EditText editTextMessage;
    private Button but_signal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercant_signalement);

        ActivityCompat.requestPermissions(CommercantSignalement.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        editTextNumber = findViewById(R.id.titre_signal);
        editTextMessage = findViewById(R.id.desc_signal);

        but_signal = findViewById(R.id.but_signal);

        but_signal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS(v);
            }
        });
    }

    public void sendSMS(View view){

        String number = editTextNumber.getText().toString();
        String message = editTextMessage.getText().toString();

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number,null,message,null,null);
    }
}
