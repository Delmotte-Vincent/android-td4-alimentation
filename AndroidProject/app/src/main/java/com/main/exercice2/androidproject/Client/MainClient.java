package com.main.exercice2.androidproject.Client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.main.exercice2.androidproject.R;

public class MainClient extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
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
        switch (itemId) {
            case R.id.action_profil:
                trans= getSupportFragmentManager().beginTransaction();
                trans.replace(R.id.client_frame, new ClientProfilFragment());
                trans.addToBackStack(null);
                trans.commit();
                break;
            case R.id.action_alertes :
                trans= getSupportFragmentManager().beginTransaction();
                trans.replace(R.id.client_frame, new ClientAlertFragment());
                trans.addToBackStack(null);
                trans.commit();
                break;
            case R.id.action_map:
                trans= getSupportFragmentManager().beginTransaction();
                trans.replace(R.id.client_frame, new ClientMapFragment());
                trans.addToBackStack(null);
                trans.commit();
                break;
            case R.id.action_signal :
                trans= getSupportFragmentManager().beginTransaction();
                trans.replace(R.id.client_frame, new ClientSignalFragment());
                trans.addToBackStack(null);
                trans.commit();
                break;
        }
        return true;
    }
}

