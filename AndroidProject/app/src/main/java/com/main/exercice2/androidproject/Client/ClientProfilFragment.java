package com.main.exercice2.androidproject.Client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.main.exercice2.androidproject.Abonnement;
import com.main.exercice2.androidproject.ClientList;
import com.main.exercice2.androidproject.R;
import com.main.exercice2.androidproject.abonnementList;

import java.util.ArrayList;

public class ClientProfilFragment extends Fragment {

    ListView listeAbonnement;
    private ArrayList commerce = new ArrayList();
    Client client;

    public ClientProfilFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        client= ClientList.findClientId(getActivity().getIntent().getExtras().getInt("id"));
        View rootView = inflater.inflate(R.layout.frag_profil,container,false);
        commerce.clear();
        for(Abonnement a :abonnementList.getAbonnementClient(client.getId()) ){
            commerce.add(a.toString());
        }
        ListView listView = (ListView) rootView.findViewById(R.id.listeAbonnement);

        listView.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.abonnement_list_adapter, commerce));

        return rootView;
    }
}
