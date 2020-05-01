package com.main.exercice2.androidproject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class CommercantListAdapter extends ArrayAdapter<CommercantObjet > {

    public CommercantListAdapter(@NonNull Context context, @NonNull ArrayList<CommercantObjet> objects) {
        super(context, R.layout.researsh_commercant, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String title = getItem(position).getTitle();
        String message = getItem(position).getMessage();
        Drawable drawable =getItem(position).getDrawable();

        CommercantObjet commercantObjet = new CommercantObjet(title,message,drawable);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View resView = inflater.inflate(R.layout.researsh_commercant, parent, false);

        TextView titre = resView.findViewById(R.id.titre_commercant);
        TextView desc= resView.findViewById(R.id.desc_commercant);
        ImageView imageView = resView.findViewById(R.id.image_com);

        if(!title.equals("")){
            titre.setText(title);
        }
        if (!message.equals("")){
            desc.setText(message);
        }
        if(drawable!=null){
            imageView.setImageDrawable(drawable);
        }

        return resView;
    }
}
