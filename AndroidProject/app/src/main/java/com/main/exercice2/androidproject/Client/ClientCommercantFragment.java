package com.main.exercice2.androidproject.Client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.main.exercice2.androidproject.Abonnement;
import com.main.exercice2.androidproject.ClientList;
import com.main.exercice2.androidproject.CommercantList;
import com.main.exercice2.androidproject.CommercantObjet;
import com.main.exercice2.androidproject.Interfaces.Constantes;
import com.main.exercice2.androidproject.R;
import com.main.exercice2.androidproject.abonnementList;

public class ClientCommercantFragment extends Fragment implements Constantes, View.OnClickListener {
    int idcom;
    CommercantObjet com;
    Client client;
    ClientCommercantFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_commercant_client,container,false);
        client = ClientList.findClientId(getActivity().getIntent().getExtras().getInt("id"));
        rootView.findViewById(R.id.frag_com_but).setOnClickListener(this);
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

    @Override
    public void onClick(View view) {

        if(abonnementList.getAbonnementClientCommercant(client.getId(),com.getId()).size()==0){
            abonnementList.addAbonnement(client.getId(),com.getId());
            Toast.makeText(getContext(), "Vous vous êtes abonné à "+com.getTitle(), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getContext(), "Vous êtes déjà abonné à "+com.getTitle(), Toast.LENGTH_SHORT).show();
        }
    }
}
