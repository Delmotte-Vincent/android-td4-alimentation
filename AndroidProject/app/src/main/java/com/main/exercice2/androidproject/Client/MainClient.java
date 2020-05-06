package com.main.exercice2.androidproject.Client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.main.exercice2.androidproject.AlertType;
import com.main.exercice2.androidproject.Constantes;
import com.main.exercice2.androidproject.IButtonCLickedListener;
import com.main.exercice2.androidproject.Notification;
import com.main.exercice2.androidproject.R;

import java.util.ArrayList;


public class MainClient extends AppCompatActivity implements IButtonCLickedListener, Constantes {
    private Bitmap picture;
    private static final String CHANNEL_ID ="channel1";
    private int notificationId = 0;
    BottomNavigationView bottomNavigationView;
    ClientAlertFragment clientAlertFragment;
    ClientSignalFragment clientSignalFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_client);
        bottomNavigationView=findViewById(R.id.activity_main_bottom_navigation);
        this.configureBottomView();

        ClientProfilFragment clientProfilFragment = (ClientProfilFragment)getSupportFragmentManager().findFragmentById(R.id.client_frame);
        if (clientProfilFragment==null){
            FragmentTransaction trans= getSupportFragmentManager().beginTransaction();
            trans.replace(R.id.client_frame, new ClientProfilFragment());
            trans.addToBackStack(null);
            trans.commit();
        }
        clientAlertFragment= new ClientAlertFragment();

    }

    private void configureBottomView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return MainClient.this.updateFragment(item.getItemId());
            }
        });;
    }

    private boolean updateFragment(int itemId) {
        FragmentTransaction trans;
        trans= getSupportFragmentManager().beginTransaction();
        switch (itemId) {
            case R.id.action_profil:
                trans.replace(R.id.client_frame, new ClientProfilFragment());
                break;
            case R.id.action_alertes :
                trans.replace(R.id.client_frame, clientAlertFragment);
                break;
            case R.id.action_map:
                trans.replace(R.id.client_frame, new ClientMapFragment());
                break;
            case R.id.action_signal :
                trans.replace(R.id.client_frame, clientSignalFragment=new ClientSignalFragment());
                break;
        }
        trans.addToBackStack(null);
        trans.commit();
        return true;
    }

    public  ArrayList<String> getSignal(){
        ArrayList<String> retour=new ArrayList<>();
        String title="Sans Titre";
        String description="sans description";
        EditText titre=findViewById(R.id.titre_signal);
        EditText desc=findViewById(R.id.desc_signal);
        if(!titre.getText().toString().equals("")){title=titre.getText().toString();}
        if(!desc.getText().toString().equals("")){description=desc.getText().toString(); }
        retour.add(title);
        retour.add(description);
        return retour;
    }

    @Override
    public void onButtonSignalClicked(View but) {
        ArrayList<String> data = getSignal();
        String titre = data.get(0);
        String desc = data.get(1);

        ImageView imageView = findViewById(R.id.image_signal);
        Drawable draw =imageView.getDrawable();
        sendNotificationOnChannel("titre", "desc", CHANNEL_ID, NotificationCompat.PRIORITY_DEFAULT);
        Toast.makeText(this,"Nouveau Signalement : "+titre+" à été créé",Toast.LENGTH_LONG).show();
        clientAlertFragment.newAlert(titre,desc,draw);
    }

    private void sendNotificationOnChannel(String titre, String desc, String channelId, int priority) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setSmallIcon(R.drawable.baseline_account_circle_black_18dp)
                .setContentTitle(titre)
                .setContentText("Vous avez une news !")
                .setPriority(priority)
                .setShowWhen(true);

        Notification.getNotificationManager().notify(++notificationId, notification.build());
    }

    @Override
    public void onButtonPictureSignalClicked(View view) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CAMERA:{
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Toast toast = Toast.makeText(getApplicationContext(),"autorisation caméra validée",Toast.LENGTH_LONG);
                    toast.show();
                    clientSignalFragment.takePicture();
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),"autorisation caméra refusée",Toast.LENGTH_LONG);
                    toast.show();
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== REQUEST_CAMERA){
            if (resultCode==RESULT_OK){
                picture = (Bitmap) data.getExtras().get("data");
                clientSignalFragment.setImage(picture);
            }
            else if (resultCode==RESULT_CANCELED){
                Toast toast = Toast.makeText(getApplicationContext(),"la photo n'a pas été prise",Toast.LENGTH_LONG);
                toast.show();
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(),"Erreur de caméra",Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}
