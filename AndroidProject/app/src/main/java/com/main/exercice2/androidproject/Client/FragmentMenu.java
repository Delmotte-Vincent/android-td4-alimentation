package com.main.exercice2.androidproject.Client;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.main.exercice2.androidproject.R;

public class FragmentMenu extends Fragment implements View.OnClickListener{
    private iButtonClickListener myCallback ;
    public FragmentMenu(){}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myCallback= (iButtonClickListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_menu_client,container,false);
        rootView.findViewById(R.id.profile).setOnClickListener(this);
        rootView.findViewById(R.id.map).setOnClickListener(this);
        rootView.findViewById(R.id.signaler).setOnClickListener(this);
        return rootView ;
    }

    @Override
    public void onClick(View button) {
        switch (button.getId()){
            case R.id.profile :
                myCallback.onButtonProfileClicked(button);
                break;
            case R.id.map :
                myCallback.onButtonMapClicked(button);
                break;
            case R.id.signaler :
                myCallback.onButtonSignalerClicked(button);
                break;
        }
    }



    }
