package com.main.exercice2.androidproject.Client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.main.exercice2.androidproject.Post;
import com.main.exercice2.androidproject.R;

import java.util.ArrayList;

public class MainClient extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout ;
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
        trans= getSupportFragmentManager().beginTransaction();
        switch (itemId) {
            case R.id.action_profil:
                trans.replace(R.id.client_frame, new ClientProfilFragment());
                break;
            case R.id.action_alertes :
                trans.replace(R.id.client_frame, new ClientAlertFragment());
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
}

