package com.main.exercice2.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.main.exercice2.androidproject.Client.MainClient;
import com.main.exercice2.androidproject.Commercant.MainCommercant;

public class LoginActivity extends AppCompatActivity implements LoginAs{
    boolean client ;
    Button connexion ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        connexion = findViewById(R.id.connexion);
        Intent intent = this.getIntent();
        client= intent.getBooleanExtra(logClient,true);
        TextView qui = findViewById(R.id.qui);
        if(client) {
            qui.setText("connexion en tant que client");
            connexion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MainClient.class);
                    //intent.putExtra(logClient,true);
                    startActivity(intent);
                }
            });
        }
        else {
            qui.setText("connexion en tant que commercant");
            connexion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MainCommercant.class);
                    //intent.putExtra(logClient,true);
                    startActivity(intent);
                }
            });
        }

    }
}
