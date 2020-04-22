package com.main.exercice2.androidproject.Client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentTransaction;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.main.exercice2.androidproject.IButtonCLickedListener;
import com.main.exercice2.androidproject.Notification;
import com.main.exercice2.androidproject.Post;
import com.main.exercice2.androidproject.R;

import java.util.ArrayList;

public class MainClient extends AppCompatActivity implements IButtonCLickedListener {
    private static final String CHANNEL_ID ="channel1";
    private int notificationId = 0;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout ;
    ClientAlertFragment clientAlertFragment;
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
                trans.replace(R.id.client_frame, new ClientSignalFragment());
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
        //sendNotificationOnChannel("titre", "desc", CHANNEL_ID, NotificationCompat.PRIORITY_DEFAULT);
        Toast.makeText(this,"Nouveau Signalement : "+titre+" à été créé",Toast.LENGTH_LONG).show();
        clientAlertFragment.newAlert(titre,desc);
    }

    private void sendNotificationOnChannel(String titre, String desc, String channelId, int priority) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setSmallIcon(R.drawable.baseline_account_circle_black_18dp)
                .setContentTitle(titre)
                .setContentText("id=" + notificationId + " - " + desc)
                .setPriority(priority);
        Notification.getNotificationManager().notify(++notificationId, notification.build());
    }
}

