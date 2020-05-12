package com.main.exercice2.androidproject.Commercant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.main.exercice2.androidproject.CommercantList;
import com.main.exercice2.androidproject.LoginActivity;
import com.main.exercice2.androidproject.MainActivity;
import com.main.exercice2.androidproject.R;

import java.util.Calendar;

public class MainCommercant extends AppCompatActivity implements NomMagasinDialog.ExampleDialogListener {

    Button deconnexion, categorie_button, description_button, envoyer_signal_button, ouverture_button, fermeture_button, nom_button;

    TextView nom_magasin_text, ouverture_text, fermeture_text, description_text;
    CommercantObjet commercant;

    int hour,minute;
    String heure;
    Bundle bundle;
    int idCommercant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_commercant);
        deconnexion = findViewById(R.id.deconnexionCom);
        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SAVE, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(LoginActivity.ID,-1);
                editor.putBoolean(LoginActivity.MODE,false);
                editor.apply();
                finishAffinity();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        bundle = this.getIntent().getExtras();
        idCommercant = bundle.getInt("id");
        commercant = CommercantList.findClientId(idCommercant);



        categorie_button = findViewById(R.id.categorie_button);
        categorie_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CommercantCategorie.class);
                startActivity(intent);
            }
        });

        envoyer_signal_button = findViewById(R.id.envoyer_signal_button);
        envoyer_signal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CommercantSignalement.class);
                bundle.putInt("id",idCommercant);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        description_button = findViewById(R.id.description_button);
        description_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CommercantDescription.class);
                startActivity(intent);
            }
        });

        ouverture_text = findViewById(R.id.ouverture_text);
        ouverture_button = findViewById(R.id.ouverture_button);
        ouverture_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHoraire(true);
            }
        });
        fermeture_text = findViewById(R.id.fermeture_text);
        fermeture_button = findViewById(R.id.fermeture_button);
        fermeture_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHoraire(false);
            }
        });

        nom_magasin_text = (TextView)findViewById(R.id.nom_magasin_text);
        nom_magasin_text.setText(commercant.getTitle());
        nom_button = (Button)findViewById(R.id.nom_button);
        nom_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


        String description = getIntent().getStringExtra("description_key");
        description_text = (TextView)findViewById(R.id.description_text);
        description_text.setText(description==null?"Description :":"Description :\n"+description);
        // setInfo();
    }

    public void openDialog() {
        NomMagasinDialog nomMagasinDialog = new NomMagasinDialog();
        nomMagasinDialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyTexts(String nom) {
        commercant.setTitle(nom);
        nom_magasin_text.setText(commercant.getTitle());
    }

    @Override
    protected void onRestart() {

        super.onRestart();
        setInfo();
    }


    void setInfo(){
        description_text.setText("Description :"+commercant.getMessage());
        // nom_magasin_text.setText("Nom du magasin :"+commercant.getType());
    }

    void setNom(String nom){
        nom_magasin_text.setText(nom);
    }

    void setHoraire(final boolean ouverture){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        final TimePickerDialog timePickerDialog2 = new TimePickerDialog(MainCommercant.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                heure=hourOfDay + "h" + ((minute<10)?"0"+minute:minute);
                if (ouverture){
                    ouverture_text.setText("Ouverture : "+heure);
                }
                else{
                    fermeture_text.setText("Fermeture : "+heure);
                }
            }
        }, hour, minute, true);
        timePickerDialog2.show();
    }

}