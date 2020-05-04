package com.main.exercice2.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.main.exercice2.androidproject.Client.Client;
import com.main.exercice2.androidproject.Client.MainClient;
import com.main.exercice2.androidproject.Client.NewClient;
import com.main.exercice2.androidproject.Commercant.MainCommercant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Predicate;

public class LoginActivity extends AppCompatActivity implements  LoginAs{
    boolean client ;
    Button connexion ;
    TextView info ;
    ImageView profile ;
    Client test = new Client("mohamed","fertala","test","test",0);
    EditText mail ;
    EditText pass ;
    String  mailIn ;
    String  passIn ;
    Button nouveau ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        info =findViewById(R.id.InfoApp);
        profile=findViewById(R.id.profile);
        connexion = findViewById(R.id.connexion);
        nouveau = findViewById(R.id.nouveau);
        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.pass);
        Intent intent = this.getIntent();
        client= intent.getBooleanExtra(logClient,true);
        Clients.add(test);
        TextView qui = findViewById(R.id.qui);
        if(client)
            qui.setText("connexion en tant que client");
         else
            qui.setText("connexion en tant que commercant");

        connexion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (client)
                        connexionClient();
                    else {
                        Intent intent = new Intent(getApplicationContext(), MainCommercant.class);
                        startActivity(intent);
                    }

                }
        });

        nouveau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewClient.class);
                finishAffinity();
                intent.putExtra("client",client);
                startActivity(intent);

            }
        });
    }




    private void  connexionClient(){
        Intent intent = new Intent(getApplicationContext(), MainClient.class);
        mailIn = mail.getText().toString();
        passIn = pass.getText().toString() ;
        Client find = Clients.findClients(mailIn,passIn) ;


        //intent.putExtra(logClient,true);

        if(find!=null){
            Toast.makeText(this,"connexion réussie",Toast.LENGTH_SHORT).show();
            finishAffinity();
            Bundle bundle = new Bundle();
            bundle.putInt("id",find.getId());
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else
            Toast.makeText(this,"connexion échouée",Toast.LENGTH_SHORT).show();
    }





}
