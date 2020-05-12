package com.main.exercice2.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.main.exercice2.androidproject.Client.Client;
import com.main.exercice2.androidproject.Client.MainClient;
import com.main.exercice2.androidproject.Commercant.CommercantObjet;
import com.main.exercice2.androidproject.Commercant.MainCommercant;
import com.main.exercice2.androidproject.Interfaces.AlertType;
import com.main.exercice2.androidproject.Interfaces.LoginAs;

import org.osmdroid.util.GeoPoint;

public class MainActivity extends AppCompatActivity implements LoginAs {
    Button client ;
    Button commercant ;
    private int idSaved;
    private boolean modeSaved;

    Client test = new Client("mohamed","fertala","test","test",0,0);

    /*
     * Mocks Safwane
     */
    Client coralie = new Client("Coralie","Dupont","coralie","dupont",5556,7);
    Client coralieBis = new Client("Coralie2","Dupont2","coralie2","dupont2",5558,8);
    CommercantObjet christophe = new CommercantObjet("Bio Légume","Vend légumes bio et frais","Légume",null,new GeoPoint(43.62950, 17.01517),"a","a",5554,6);

    /*
     * Mocks
     */
    CommercantObjet commercantOb = new CommercantObjet("Boucherie Halal","aprés midi","le matin",
            null,new GeoPoint(111,11),"test","test",0,0);
    CommercantObjet restoCom = new CommercantObjet("resto", "delice de maman", AlertType.DEFAULT,
            null, new GeoPoint(43.64950, 7.00517),"them","e",2,2);

    CommercantObjet homeCom = new CommercantObjet("home","rallo's home", AlertType.DEFAULT,
            null,new GeoPoint(43.65020,7.00517),"em","pas",1,1);

    CommercantObjet epiCom = new CommercantObjet("Epicerie Léa","épicerie chez Léa", AlertType.DEFAULT,
            null,new GeoPoint(43.65320,7.00617),"epicerie","lea",3,3);

    CommercantObjet bouCom = new CommercantObjet("Boulangerie","magnifique boulangerie", AlertType.DEFAULT,
            null,new GeoPoint(43.64820,7.00317),"boulanger","pain",4,4);

    CommercantObjet poiCom = new CommercantObjet("Poissonnier","chez Otto ", AlertType.DEFAULT,
            null,new GeoPoint(43.65820,7.00017),"poisson","poi",5,5);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = findViewById(R.id.client);
        commercant = findViewById(R.id.commercant);

        ClientList.add(test);
        ClientList.add(coralie);
        ClientList.add(coralieBis);

        CommercantList.add(commercantOb);
        CommercantList.add(restoCom);
        CommercantList.add(homeCom);
        CommercantList.add(christophe);
        CommercantList.add(epiCom);
        CommercantList.add(poiCom);
        CommercantList.add(bouCom);

        /*
         * Abonnement Safwane : Coralie et CoralieBis abonnée à Christophe
         */
        abonnementList.addAbonnement(7, 6);
        abonnementList.addAbonnement(8, 6);

        load();
        if(idSaved == -1){
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
        } else {
            Intent intent1;
            if(modeSaved){
                 intent1 = new Intent(getApplicationContext(),MainClient.class);
                Bundle b = new Bundle();
                b.putInt("id",idSaved);
                intent1.putExtras(b);
                startActivity(intent1);
                finish();
            }
            else {
               intent1 = new Intent(getApplicationContext(),MainCommercant.class);
                Bundle b = new Bundle();
                b.putInt("id",idSaved);
                intent1.putExtras(b);
                startActivity(intent1);
                finish();
            }


        }


    }

    public void load(){
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SAVE,MODE_PRIVATE);
        idSaved = sharedPreferences.getInt(LoginActivity.ID , -1);
        modeSaved = sharedPreferences.getBoolean(LoginActivity.MODE,false);

    }


}
