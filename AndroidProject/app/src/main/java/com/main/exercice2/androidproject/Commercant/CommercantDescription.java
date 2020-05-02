package com.main.exercice2.androidproject.Commercant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

                setContentView(R.layout.activity_commercant_description);
                TextView tv1 = (TextView)findViewById(R.id.description_view);

                tv1.setText("dfgdfnghn");
            }
        });
    }
}
