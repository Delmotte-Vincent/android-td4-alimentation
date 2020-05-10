package com.main.exercice2.androidproject.Commercant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.main.exercice2.androidproject.AlertDialogCustom;
import com.main.exercice2.androidproject.abonnementList;
import com.main.exercice2.androidproject.R;


public class CommercantSignalement extends AppCompatActivity {

    private EditText titre_signal;
    private EditText desc_signal;
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

        titre_signal = findViewById(R.id.titre_signal);
        desc_signal = findViewById(R.id.desc_signal);

        but_signal = findViewById(R.id.but_signal);

        but_signal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogCustom.AlertDialogCalendar(CommercantSignalement.this, titre_signal.getText().toString(), desc_signal.getText().toString());
                AlertDialogCustom.AlertDialogSMS(CommercantSignalement.this, titre_signal, desc_signal);
            }
        });
    }
}
