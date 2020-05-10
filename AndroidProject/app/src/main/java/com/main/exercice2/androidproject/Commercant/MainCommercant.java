package com.main.exercice2.androidproject.Commercant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.main.exercice2.androidproject.CommercantList;
import com.main.exercice2.androidproject.LoginActivity;
import com.main.exercice2.androidproject.MainActivity;
import com.main.exercice2.androidproject.R;

import java.util.Calendar;

public class MainCommercant extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {



    ListView listeCategorie;
    private String[] categorie = new String[]{
            "Epicerie", "Poissonnerie", "Boucherie", "Boulangerie"
    };
    Button deconnexion ;
    Button categorie_button ;
    Button description_button ;
    Button envoyer_signal_button;
    Button horaire_button;
    Button nom_button;

    TextView nom_magasin_text;
    TextView horaire_text;
    TextView description_text;
    CommercantObjet commercant;

    int hour,minute;
    String horaire,debut;
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

        horaire_text = findViewById(R.id.horaire_text);
        horaire_button = findViewById(R.id.horaire_button);
        horaire_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHoraire();
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
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
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

    void setHoraire(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainCommercant.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                debut = " - "+hourOfDay + "h" + minute;
            }
        }, hour, minute, true);
        timePickerDialog.show();

        TimePickerDialog timePickerDialog2 = new TimePickerDialog(MainCommercant.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                horaire="Horaires : "+hourOfDay + "h" + minute+debut;
                horaire_text.setText(horaire);
            }
        }, hour, minute, true);
        timePickerDialog2.show();
    }

    void setDescription(){

    }

}