package com.main.exercice2.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.main.exercice2.androidproject.Client.Client;
import com.main.exercice2.androidproject.Client.MainClient;
import com.main.exercice2.androidproject.Client.NewClient;
import com.main.exercice2.androidproject.Commercant.CommercantObjet;
import com.main.exercice2.androidproject.Commercant.MainCommercant;
import com.main.exercice2.androidproject.Commercant.NewCommercant;
import com.main.exercice2.androidproject.Interfaces.LoginAs;

public class LoginActivity extends AppCompatActivity implements LoginAs {
    boolean client ;
    Button connexion ;
    TextView info ;
    ImageView profile ;

    public static final String SAVE = "SAVELOG";
    public static final String ID = "IDLOG";
    public static final String MODE = "MODE" ;


    EditText mail ;
    EditText pass ;
    String  mailIn ;
    String  passIn ;
    Button nouveau ;
    ImageView see ;
    boolean covered ;
    ImageView back ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        back = findViewById(R.id.back);
        info =findViewById(R.id.InfoApp);
        profile=findViewById(R.id.profile);
        connexion = findViewById(R.id.connexion);
        nouveau = findViewById(R.id.nouveau);
        mail = findViewById(R.id.mail);
        pass = findViewById(R.id.pass);
        see = findViewById(R.id.see) ;
        Intent intent = this.getIntent();
        client= intent.getBooleanExtra(logClient,true);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView qui = findViewById(R.id.qui);
        covered = true ;
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
                        connexionCommercant();
                    }

                }
        });

        nouveau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(client){
                     intent = new Intent(getApplicationContext(), NewClient.class);

                }
                else {
                    intent = new Intent(getApplicationContext(), NewCommercant.class);

                }
                intent.putExtra("client",client);
                startActivity(intent);


            }
        });
        if(see!=null) {
            see.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (covered)
                        pass.setTransformationMethod(null);
                    else
                        pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    pass.setSelection(pass.getText().length());
                    covered = !covered;


                }
            });
        }
    }




    private void  connexionClient(){
        Intent intent = new Intent(getApplicationContext(), MainClient.class);
        mailIn = mail.getText().toString();
        passIn = pass.getText().toString() ;
        Client find = ClientList.findClients(mailIn,passIn) ;
        if(find!=null){
            Toast.makeText(this,"connexion réussie",Toast.LENGTH_SHORT).show();
            finishAffinity();
            Bundle bundle = new Bundle();
            bundle.putInt("id",find.getId());
            intent.putExtras(bundle);
            startActivity(intent);
            save(find.getId());
        }
        else
            Toast.makeText(this,"connexion échouée",Toast.LENGTH_SHORT).show();
    }


    private void  connexionCommercant(){
        Intent intent = new Intent(getApplicationContext(), MainCommercant.class);
        mailIn = mail.getText().toString();
        passIn = pass.getText().toString() ;
        CommercantObjet find = CommercantList.findCommercant(mailIn,passIn) ;
        if(find!=null){
            Toast.makeText(this,"connexion réussie",Toast.LENGTH_SHORT).show();
            finishAffinity();
            Bundle bundle = new Bundle();
            bundle.putInt("id",find.getId());
            intent.putExtras(bundle);
            startActivity(intent);
            save(find.getId());
        }
        else
            Toast.makeText(this,"connexion échouée",Toast.LENGTH_SHORT).show();
    }

    public void save(int id ){
        SharedPreferences sharedPreferences = getSharedPreferences(SAVE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ID,id);
        editor.putBoolean(MODE,client);
        editor.apply();
    }






}
