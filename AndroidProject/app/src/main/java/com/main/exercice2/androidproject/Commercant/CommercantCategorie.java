package com.main.exercice2.androidproject.Commercant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.main.exercice2.androidproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CommercantCategorie extends AppCompatActivity {

    ListView listview;
    Button Addbutton;
    Button DeleteButton;
    EditText GetValue;
    String[] ListElements = new String[] {

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercant_categorie);

        listview = (ListView) findViewById(R.id.categorie_listView);
        Addbutton = (Button) findViewById(R.id.ajouter_categorie_button);
        DeleteButton = (Button) findViewById(R.id.supprimer_categorie_button);
        GetValue = (EditText) findViewById(R.id.edit_nom_categorie_text);

        final List< String > ListElementsArrayList = new ArrayList< String >
                (Arrays.asList(ListElements));


        final ArrayAdapter< String > adapter = new ArrayAdapter < String >
                (CommercantCategorie.this, android.R.layout.simple_list_item_1,
                        ListElementsArrayList);

        listview.setAdapter(adapter);

        Addbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // on vérifie que l'utilisateur entre un nom
                if (GetValue.getText().toString().isEmpty()){
                    GetValue.setError("Il faut entrer un nom de catégorie");
                    return;
                }
                // on vérifie qu'il n'existe pas déjà un nom de catégorie identique à celui que l'on veut ajouter
                for (int i=0;i<ListElementsArrayList.size();i++){
                    if (ListElementsArrayList.get(i).equals(GetValue.getText().toString())){
                        GetValue.setError("Une catégorie de ce nom existe déjà");
                        return;
                    }
                }
                // on peut ajouter la catégorie
                ListElementsArrayList.add(GetValue.getText().toString());
                adapter.notifyDataSetChanged();
                Toast.makeText(CommercantCategorie.this, "Catégorie \""+GetValue.getText().toString()+"\" ajoutée", Toast.LENGTH_SHORT).show();
            }
        });

        DeleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // on parcourt la liste pour trouver la catégorie à supprimer
                for (int i=0;i<ListElementsArrayList.size();i++){
                    if (ListElementsArrayList.get(i).equals(GetValue.getText().toString())){
                        ListElementsArrayList.remove(GetValue.getText().toString());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(CommercantCategorie.this, "Catégorie \""+GetValue.getText().toString()+"\" supprimée", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // on n'a pas trouvé la catégorie demandée
                GetValue.setError("Il n'existe pas de nom de cette catégorie");
            }
        });
    }
}
