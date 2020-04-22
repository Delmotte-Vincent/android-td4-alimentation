package com.main.exercice2.androidproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.main.exercice2.androidproject.Client.MainClient;
import com.main.exercice2.androidproject.Commercant.MainCommercant;
import com.squareup.picasso.Picasso;

public class LoginActivity extends AppCompatActivity implements LoginAs{
    boolean client ;
    Button connexion ;
    TextView info ;
    ImageView profile ;
    LoginButton loginFb ;
    CallbackManager callbackManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        info =findViewById(R.id.fbInfo);
        profile=findViewById(R.id.profile);
        loginFb = findViewById(R.id.loginFb);
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

        callbackManager = CallbackManager.Factory.create();

        loginFb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                info.setText("user id : "+loginResult.getAccessToken().getUserId());
                String imageUrl = "https://graph.facebook.com/"+loginResult.getAccessToken().getUserId()+"/picture?return_ssl_resources=1";
                Picasso.get().load(imageUrl).into(profile);


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
