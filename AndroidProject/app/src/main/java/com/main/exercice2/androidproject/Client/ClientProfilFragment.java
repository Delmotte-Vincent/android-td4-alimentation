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

import com.main.exercice2.androidproject.R;

public class ClientProfilFragment extends Fragment {

    ListView listeAbonnement;
    private String[] commerce = new String[]{
            "Epicerie", "Poissonnerie", "Boucherie", "Boulangerie"
    };

    public ClientProfilFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_profil,container,false);

        ListView listView = (ListView) rootView.findViewById(R.id.listeAbonnement);

        listView.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.abonnement_list_adapter, commerce));

        return rootView;
    }
}
