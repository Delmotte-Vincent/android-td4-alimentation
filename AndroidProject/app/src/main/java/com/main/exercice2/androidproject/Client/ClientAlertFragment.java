package com.main.exercice2.androidproject.Client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.main.exercice2.androidproject.AlertType;
import com.main.exercice2.androidproject.AlerteListAdapter;
import com.main.exercice2.androidproject.Post;
import com.main.exercice2.androidproject.R;

import java.util.ArrayList;

public class ClientAlertFragment extends Fragment {
    private ArrayList<Post> postList;



    ClientAlertFragment(){
        postList=new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_alert_client,container,false);




        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        AlerteListAdapter adapter = new AlerteListAdapter(getContext(), postList);
        listView.setAdapter(adapter);

        return rootView;
    }

    public void newAlert(String titre, String desc, String type) {
        postList.add(new Post(titre, desc, type));
    }
}
