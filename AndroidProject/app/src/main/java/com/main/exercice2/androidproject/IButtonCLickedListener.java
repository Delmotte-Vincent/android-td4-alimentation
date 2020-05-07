package com.main.exercice2.androidproject;

import android.view.View;

public interface IButtonCLickedListener {
    void onButtonSignalClicked(View but , boolean checked);

    void onButtonPictureSignalClicked(View view);

    void onCheckClicked(View view , boolean status);
}
