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
import androidx.recyclerview.widget.RecyclerView;

import com.main.exercice2.androidproject.Post;
import com.main.exercice2.androidproject.PostListAdapter;
import com.main.exercice2.androidproject.R;

import java.util.ArrayList;

public class ClientAlertFragment extends Fragment {



    ClientAlertFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_alert_client,container,false);
        ListView listView = (ListView) rootView.findViewById(R.id.listView);

        ArrayList<Post> postList = new ArrayList<>();
        postList.add(new Post("post 1", "Ceci est le post 1, blablabla !"));
        postList.add(new Post("post 2", "Ceci est le post 2, blablabla oulala fezfze !"));
        postList.add(new Post("post 3", "Ceci est le post 3, je suis beaucoup plus long que les deux autres pour faire un test"));
        PostListAdapter adapter = new PostListAdapter(getContext(), R.layout.adapter_view_layout, postList);
        listView.setAdapter(adapter);



        return rootView;
    }
}
