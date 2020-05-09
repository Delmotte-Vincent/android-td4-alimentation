package com.main.exercice2.androidproject.Client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.main.exercice2.androidproject.CommercantList;
import com.main.exercice2.androidproject.CommercantObjet;
import com.main.exercice2.androidproject.Interfaces.Constantes;
import com.main.exercice2.androidproject.R;

public class ClientCommercantFragment extends Fragment implements Constantes {
    int idcom;
    CommercantObjet com;
    ClientCommercantFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_commercant_client,container,false);
        if(getArguments()!=null)idcom=getArguments().getInt(PASSAGE_COM);
        if(CommercantList.findClientId(idcom)!=null){
            com=CommercantList.findClientId(idcom);
            ((TextView)rootView.findViewById(R.id.frag_com_nom)).setText(com.getTitle());
            String desc=com.getMessage()+"\n Contact : "+com.getEmail();
            ((TextView)rootView.findViewById(R.id.frag_com_desc)).setText(desc);
            ((ImageView)rootView.findViewById(R.id.frag_com_im)).setImageDrawable(com.getDrawable());
        }
        return rootView;
    }
}
