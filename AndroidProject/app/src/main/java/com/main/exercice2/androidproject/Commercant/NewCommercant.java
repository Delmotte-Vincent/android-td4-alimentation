package com.main.exercice2.androidproject.Commercant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.main.exercice2.androidproject.CommercantList;
import com.main.exercice2.androidproject.R;

public class NewCommercant extends AppCompatActivity {

    EditText name ;
    EditText horaires;
    EditText description ;
    EditText mail;
    EditText pass ;
    EditText passConf ;
    EditText phoneNumber ;
    Button send ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_commercant);

        name = findViewById(R.id.nouveauCommercantNom);
        horaires= findViewById(R.id.nouveauHoraires);
        description = findViewById(R.id.nouveauCommercantDescription);
        mail=findViewById(R.id.nouveauCommercantMail);
        pass=findViewById(R.id.nouveauCommercantPass);
        passConf=findViewById(R.id.nouveauCommercantPassConf);
        phoneNumber=findViewById(R.id.nouveauCommercantPhoneNumber);
        send =findViewById(R.id.sendCommercant);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
    }

    private void send() {
        boolean conf = false ;
        String nameS = name.getText().toString();
        String horairesS= horaires.getText().toString();
        String descriptiosS=description.getText().toString() ;
        String mailS=mail.getText().toString();
        String passS =pass.getText().toString();
        String passConfS =passConf.getText().toString();
        String phoneS=phoneNumber.getText().toString();
        int phoneNumber=(phoneS.equals(""))?0:Integer.parseInt(phoneS);

        if(CommercantList.exist(mailS)) {
            Toast.makeText(this, "cet email est déjà utilisé ", Toast.LENGTH_SHORT).show();
            conf = true ;
        }
        if(!passConfS.equals(passS)) {
            Toast.makeText(this,"Confirmation de mot de passe échoué",Toast.LENGTH_SHORT).show();
            conf = true ;
        }
        if(conf)
            return;
        int id = CommercantList.nouveau(nameS,horairesS,descriptiosS,null,null,mailS,passS,phoneNumber);
        Intent intent = new Intent(getApplicationContext(), MainCommercant.class);
        Toast.makeText(this,"compte crée ",Toast.LENGTH_SHORT).show();
        finishAffinity();
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
