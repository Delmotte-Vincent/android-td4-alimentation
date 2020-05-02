package com.main.exercice2.androidproject.Commercant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


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
            "Poisson",
            "Légumes"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercant_categorie);

        listview = (ListView) findViewById(R.id.listView1);
        Addbutton = (Button) findViewById(R.id.button1);
        DeleteButton = (Button) findViewById(R.id.button2);
        GetValue = (EditText) findViewById(R.id.editText1);

        final List< String > ListElementsArrayList = new ArrayList< String >
                (Arrays.asList(ListElements));


        final ArrayAdapter< String > adapter = new ArrayAdapter < String >
                (CommercantCategorie.this, android.R.layout.simple_list_item_1,
                        ListElementsArrayList);

        listview.setAdapter(adapter);

        Addbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (GetValue.getText().toString().isEmpty()){
                    GetValue.setError("Il faut entrer un nom de catégorie");
                    return;
                }
                for (int i=0;i<ListElementsArrayList.size();i++){
                    if (ListElementsArrayList.get(i).equals(GetValue.getText().toString())){
                        GetValue.setError("Une catégorie de ce nom existe déjà");
                        return;
                    }
                }
                ListElementsArrayList.add(GetValue.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

        DeleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (int i=0;i<ListElementsArrayList.size();i++){
                    if (ListElementsArrayList.get(i).equals(GetValue.getText().toString())){
                        ListElementsArrayList.remove(GetValue.getText().toString());
                        adapter.notifyDataSetChanged();
                        return;
                    }
                }
                GetValue.setError("Il n'existe pas de nom de cette catégorie");
            }
        });
    }
}
