package com.main.exercice2.androidproject.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.main.exercice2.androidproject.ClientList;
import com.main.exercice2.androidproject.LoginActivity;
import com.main.exercice2.androidproject.R;

public class NewClient extends AppCompatActivity {
    boolean client ;
    Button send ;
    EditText nom ;
    EditText prenom ;
    EditText mail ;
    EditText pass ;
    EditText confirmation ;
    EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_client);
        client = getIntent().getBooleanExtra("client",true);
        send =findViewById(R.id.send);
        nom = findViewById(R.id.nouveauClientNom);
        prenom=findViewById(R.id.nouveauClientPrenom);
        mail=findViewById(R.id.nouveauClientMail);
        pass = findViewById(R.id.nouveauClientPass);
        phone = findViewById(R.id.nouveauClientPhone);
        confirmation = findViewById(R.id.nouveauClientPassConf);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(client){
                    send();
                }
            }
        });
    }
    private void send(){
        boolean stop = false ;
        String nomS = nom.getText().toString();
        String prenomS  = prenom.getText().toString();
        String mailS  = mail.getText().toString();
        String passS  = pass.getText().toString();
        String confirmationS  = confirmation.getText().toString();
        String phoneS = phone.getText().toString();
        int phoneNumber=(phoneS.equals(""))?0:Integer.parseInt(phoneS);
        if(ClientList.exist(mailS)) {
            Toast.makeText(this, "cet email est déjà utilisé ", Toast.LENGTH_SHORT).show();
            stop = true ;
        }
        if(!confirmationS.equals(passS)) {
            Toast.makeText(this,"Confirmation de mot de passe échoué",Toast.LENGTH_SHORT).show();
            stop = true ;
        }
        if(stop)
            return;
        int id = ClientList.nouveau(nomS,prenomS,mailS,passS,phoneNumber);
        Intent intent = new Intent(getApplicationContext(), MainClient.class);
        Toast.makeText(this,"compte crée ",Toast.LENGTH_SHORT).show();
        finishAffinity();
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void save(int id ){
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SAVE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LoginActivity.ID,id);
        editor.putBoolean(LoginActivity.MODE,true);
        editor.apply();
    }

}
