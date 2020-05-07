package com.main.exercice2.androidproject.Commercant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.main.exercice2.androidproject.R;

public class CommercantDescription extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercant_description);

        button = (Button)findViewById(R.id.description_button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CommercantDescription.this,MainCommercant.class);
                EditText description_edit_text = (EditText)findViewById(R.id.description_edit_text);
                String description = description_edit_text.getText().toString();
                System.out.println("/n/nwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww"+description);
                intent.putExtra("description_key",description);
                startActivity(intent);
            }
        });
    }
}
