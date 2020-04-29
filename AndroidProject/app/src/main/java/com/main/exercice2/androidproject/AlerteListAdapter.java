package com.main.exercice2.androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class AlerteListAdapter extends ArrayAdapter<Post > {

    private Integer[] tab_images_pour_la_liste = {
            R.drawable.epicerie,
            R.drawable.poissonnerie,
            R.drawable.boucherie,
            R.drawable.boulangerie,
    };

    public AlerteListAdapter(@NonNull Context context, @NonNull ArrayList<Post> objects) {
        super(context, R.layout.rowlayout, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String title = getItem(position).getTitle();
        String message = getItem(position).getMessage();
        String type = getItem(position).getType();

        Post post = new Post(title, message, type);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

        TextView tvTitle = (TextView) rowView.findViewById(R.id.textView1);
        TextView tvMessage = (TextView) rowView.findViewById(R.id.textView2);
        ImageView ivPhoto = (ImageView) rowView.findViewById(R.id.photo);

        tvTitle.setText(title);
        tvMessage.setText(message);

        if(convertView == null )
            switch (type) {
                case AlertType.BOUCHERIE:
                    ivPhoto.setImageResource(R.drawable.boucherie);
                    break;
                case AlertType.BOULANGERIE:
                    ivPhoto.setImageResource(R.drawable.boulangerie);
                    break;
                case AlertType.POISONNERIE:
                    ivPhoto.setImageResource(R.drawable.poissonnerie);
                    break;
                case AlertType.EPICERIE:
                    ivPhoto.setImageResource(R.drawable.epicerie);
                    break;
                default:
                    ivPhoto.setImageResource(R.drawable.news);
            }
        else
            rowView = (View)convertView;
        return rowView;
    }

}
