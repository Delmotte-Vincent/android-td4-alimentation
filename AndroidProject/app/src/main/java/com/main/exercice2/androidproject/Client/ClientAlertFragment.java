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

        String[] values = new String[] { "Device", "Géo localisation", "Accéléromètre",
                "Navigateur internet"};


        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        if(postList.size()==0) {
            postList.add(new Post("Rupture de stock", "Il n'y a plus de pain dans la boulangerie, nous en referons dès que possible !", AlertType.BOULANGERIE));
            postList.add(new Post("Arrivage de viande", "De nouvelles variétés de boeuf sont arrivées aujourd'hui, vous pouvez venir en boucherie pour en profiter.", AlertType.BOUCHERIE));
            postList.add(new Post("post 3", "Ceci est le post 3, je suis beaucoup plus long que les deux autres pour faire un test", AlertType.DEFAULT));
            postList.add(new Post("post 4", "Ceci est le post 3, je suis beaucoup plus long que les deux autres pour faire un test", AlertType.DEFAULT));
        }

        AlerteListAdapter adapter = new AlerteListAdapter(getContext(), postList);

        listView.setAdapter(adapter);



        return rootView;
    }

    public void newAlert(String titre, String desc) {
        postList.add(new Post(titre,desc,AlertType.DEFAULT));
    }
}
