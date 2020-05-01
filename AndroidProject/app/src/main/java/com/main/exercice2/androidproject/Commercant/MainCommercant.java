package com.main.exercice2.androidproject.Commercant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_commercant);

        categorie_button = findViewById(R.id.categorie_button);
        categorie_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CommercantCategorie.class);
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

    }
}