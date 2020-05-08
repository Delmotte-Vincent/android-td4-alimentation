package com.main.exercice2.androidproject.Commercant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.main.exercice2.androidproject.ClientList;
import com.main.exercice2.androidproject.CommercantList;
import com.main.exercice2.androidproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainCommercant extends AppCompatActivity {

    ListView listeCategorie;
    private String[] categorie = new String[]{
            "Epicerie", "Poissonnerie", "Boucherie", "Boulangerie"
    };

    Button categorie_button ;
    Button description_button ;
    Button nom_horaire_button;
    Button envoyer_signal_button;

    TextView nom_magasin_text;
    TextView horaire_text;
    TextView description_text;
    Commercant commercant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_commercant);

        Bundle b = this.getIntent().getExtras();
        int id =b.getInt("id");
        commercant = CommercantList.findClientId(id);


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

        nom_horaire_button = findViewById(R.id.nom_horaire_button);
        nom_horaire_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CommercantNomHoraire.class);
                startActivity(intent);
            }
        });

        String nom = getIntent().getStringExtra("nom_magasin_key");
        nom_magasin_text = (TextView)findViewById(R.id.nom_magasin_text);
        //nom_magasin_text.setText(nom==null?"Nom du magasin":commercant.getNom());

        String horaire = getIntent().getStringExtra("horaire_key");
        horaire_text = (TextView)findViewById(R.id.horaire_text);
        //horaire_text.setText(horaire==null?"Horaires : ":commercant.getDescription());

        String description = getIntent().getStringExtra("description_key");
        description_text = (TextView)findViewById(R.id.description_text);
        //description_text.setText(description==null?"Description":commercant.getDescription());
        setInfo();
    }
    @Override
    protected void onRestart() {

        super.onRestart();
        setInfo();
    }


    void setInfo(){
        description_text.setText("Description :"+commercant.getDescription());
        horaire_text.setText("Horaires : "+commercant.getHoraires());
        nom_magasin_text.setText("Nom du magasin :"+commercant.getNom());
    }

}