package com.main.exercice2.androidproject.Client;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.main.exercice2.androidproject.ClientList;
import com.main.exercice2.androidproject.Commercant.CommercantObjet;
import com.main.exercice2.androidproject.Interfaces.AlertType;
import com.main.exercice2.androidproject.Adapter.AlerteListAdapter;
import com.main.exercice2.androidproject.Post;
import com.main.exercice2.androidproject.PostList;
import com.main.exercice2.androidproject.R;

import java.util.ArrayList;

public class ClientAlertFragment extends Fragment {
    private ArrayList<Post> postList;



    ClientAlertFragment(){
        postList= PostList.getAlertes();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_alert_client,container,false);
        Client client = ClientList.findClientId(getActivity().getIntent().getExtras().getInt("id"));

        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        AlerteListAdapter adapter = new AlerteListAdapter(getContext(), postList);
        listView.setAdapter(adapter);

        adapter.getFilter().filter(String.valueOf(client.getId()));
        return rootView;
    }


    public void newAlert(String titre, String desc, String type, Drawable drawable, Boolean defaultPicture, CommercantObjet commercantObjet) {

        postList.add(new Post(titre,desc, type, drawable, defaultPicture,commercantObjet));

    }
}
