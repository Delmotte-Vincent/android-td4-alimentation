package com.main.exercice2.androidproject.Client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.main.exercice2.androidproject.R;

public class MainClient extends AppCompatActivity implements iButtonClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_client);
        FragmentMenu fragmentMenu = (FragmentMenu) getSupportFragmentManager().findFragmentById(R.id.menu);
        if(fragmentMenu ==null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.menu,new FragmentMenu());
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void onButtonProfileClicked(View button) {
        Intent intent = new Intent(getApplicationContext(),ProfileClientActivity.class);
        startActivity(intent);
    }

    @Override
    public void onButtonMapClicked(View button) {
        Intent intent = new Intent(getApplicationContext(),MapActivity.class);
        startActivity(intent);
    }

    @Override
    public void onButtonSignalerClicked(View button) {
        Intent intent = new Intent(getApplicationContext(),SignalementActivity.class);
        startActivity(intent);

    }
}
