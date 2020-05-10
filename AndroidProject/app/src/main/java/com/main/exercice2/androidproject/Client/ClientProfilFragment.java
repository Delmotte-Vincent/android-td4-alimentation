package com.main.exercice2.androidproject.Client;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.main.exercice2.androidproject.Abonnement;
import com.main.exercice2.androidproject.ClientList;
import com.main.exercice2.androidproject.Commercant.CommercantObjet;
import com.main.exercice2.androidproject.CommercantList;
import com.main.exercice2.androidproject.Interfaces.IButtonCLickedListener;
import com.main.exercice2.androidproject.Interfaces.ICallBack;
import com.main.exercice2.androidproject.R;
import com.main.exercice2.androidproject.abonnementList;

import java.util.ArrayList;

public class ClientProfilFragment extends Fragment implements AdapterView.OnItemClickListener  {

    ListView listeAbonnement;
    private ArrayList commerce = new ArrayList();
    Client client;
    Button deconnexion ;
    private IButtonCLickedListener mCallBack;
    private ICallBack callBack;


    public ClientProfilFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        client= ClientList.findClientId(getActivity().getIntent().getExtras().getInt("id"));
        View rootView = inflater.inflate(R.layout.frag_profil,container,false);
        deconnexion = rootView.findViewById(R.id.deconnexion);
        commerce.clear();
        for(Abonnement a :abonnementList.getAbonnementClient(client.getId()) ){
            commerce.add(a);
        }
        ListView listView = (ListView) rootView.findViewById(R.id.listeAbonnement);
        listView.setOnItemClickListener(this);

        listView.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.abonnement_list_adapter, commerce));
        deconnexion = rootView.findViewById(R.id.deconnexion);
        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.deconnexion(v);

            }
        });


        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCallBack = (IButtonCLickedListener) getActivity();
        callBack = (ICallBack) getActivity();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Abonnement abonnement =(Abonnement) adapterView.getItemAtPosition(i);
        CommercantObjet commercantObjet = CommercantList.findClientId(abonnement.getIdCommercant());
        callBack.sendCommercantObjet(commercantObjet);
    }
}
