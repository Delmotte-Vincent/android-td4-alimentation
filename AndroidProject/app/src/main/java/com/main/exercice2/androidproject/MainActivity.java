package com.main.exercice2.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.main.exercice2.androidproject.Interfaces.LoginAs;

public class MainActivity extends AppCompatActivity implements LoginAs {
    Button client ;
    Button commercant ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = findViewById(R.id.client);
        commercant = findViewById(R.id.commercant);
        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                intent.putExtra(logClient,true);
                startActivity(intent);
            }
        });

        commercant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                intent.putExtra(logClient,false);
                startActivity(intent);
            }
        });

    }


}
