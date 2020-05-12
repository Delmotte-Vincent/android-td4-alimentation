package com.main.exercice2.androidproject.Interfaces;

import android.view.View;

import com.main.exercice2.androidproject.Commercant.CommercantObjet;

public interface IButtonCLickedListener {
    void onButtonSignalClicked(View but , boolean checked, CommercantObjet commercant);

    void onButtonPictureSignalClicked(View view);

    void onCheckClicked(View view , boolean status);

    void deconnexion(View view) ;
}
