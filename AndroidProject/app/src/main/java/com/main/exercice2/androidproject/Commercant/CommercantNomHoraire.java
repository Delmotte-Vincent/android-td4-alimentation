package com.main.exercice2.androidproject.Commercant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.main.exercice2.androidproject.R;

public class CommercantNomHoraire extends AppCompatActivity {

    Button nom_magasin_button;
    Button horaire_button;
    EditText nom_magasin_text;
    EditText horaire_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercant_nom_horaire);

        nom_magasin_button = (Button) findViewById(R.id.nom_magasin_button);
        horaire_button = (Button) findViewById(R.id.horaire_button);
        nom_magasin_text = (EditText) findViewById(R.id.nom_magasin_text);
        horaire_text = (EditText) findViewById(R.id.horaire_text);


        nom_magasin_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CommercantNomHoraire.this,MainCommercant.class);
                String nom = nom_magasin_text.getText().toString();
                intent.putExtra("nom_magasin_key",nom);
                startActivity(intent);
            }
        });


        horaire_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommercantNomHoraire.this,MainCommercant.class);
                String nom = horaire_text.getText().toString();
                intent.putExtra("horaire_key",nom);
                startActivity(intent);
            }
        });
    }
}
