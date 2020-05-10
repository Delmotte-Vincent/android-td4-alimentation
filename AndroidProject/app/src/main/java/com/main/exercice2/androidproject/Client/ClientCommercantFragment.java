package com.main.exercice2.androidproject.Client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.main.exercice2.androidproject.ClientList;
import com.main.exercice2.androidproject.CommercantList;
import com.main.exercice2.androidproject.Commercant.CommercantObjet;
import com.main.exercice2.androidproject.Interfaces.Constantes;
import com.main.exercice2.androidproject.R;
import com.main.exercice2.androidproject.abonnementList;

public class ClientCommercantFragment extends Fragment implements Constantes, View.OnClickListener {
    int idcom;
    CommercantObjet com;
    Client client;
    View rootView;
    ClientCommercantFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_commercant_client,container,false);
        client = ClientList.findClientId(getActivity().getIntent().getExtras().getInt("id"));
        rootView.findViewById(R.id.frag_com_but).setOnClickListener(this);
        if(getArguments()!=null)idcom=getArguments().getInt(PASSAGE_COM);
        if(CommercantList.findClientId(idcom)!=null){
            com=CommercantList.findClientId(idcom);
            ((TextView)rootView.findViewById(R.id.frag_com_nom)).setText(com.getTitle());
            String desc=com.getMessage()+"\n Contact : "+com.getEmail();
            ((TextView)rootView.findViewById(R.id.frag_com_desc)).setText(desc);
            if(com.getDrawable()!=null) {
                ((ImageView) rootView.findViewById(R.id.frag_com_im)).setImageDrawable(com.getDrawable());
            }
        }
        if(!(abonnementList.getAbonnementClientCommercant(client.getId(),com.getId()).size()==0)){
            ((Button)rootView.findViewById(R.id.frag_com_but)).setText("Se désabonner");
        }
        else{
            ((Button)rootView.findViewById(R.id.frag_com_but)).setText("S'abonner");
        }
        return rootView;
    }

    @Override
    public void onClick(View view) {
        System.out.println(abonnementList.getAbonnementClient(client.getId()));
        if(abonnementList.getAbonnementClientCommercant(client.getId(),com.getId()).size()==0){
            abonnementList.addAbonnement(client.getId(),com.getId());
            Toast.makeText(getContext(), "Vous vous êtes abonné à "+com.getTitle(), Toast.LENGTH_SHORT).show();
            ((Button)rootView.findViewById(R.id.frag_com_but)).setText("Se désabonner");
        }
        else{
            System.out.println(abonnementList.getAbonnementClientCommercant(client.getId(),com.getId()));
            Toast.makeText(getContext(), "Vous vous êtes désabonné de "+com.getTitle(), Toast.LENGTH_SHORT).show();
            abonnementList.delAbonnement(client.getId(),com.getId());
            ((Button)rootView.findViewById(R.id.frag_com_but)).setText("S'abonner");
        }
    }
}
