package com.main.exercice2.androidproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.main.exercice2.androidproject.Abonnement;
import com.main.exercice2.androidproject.AlertDialogCustom;
import com.main.exercice2.androidproject.Client.Client;
import com.main.exercice2.androidproject.ClientList;
import com.main.exercice2.androidproject.Commercant.CommercantDescription;
import com.main.exercice2.androidproject.Commercant.CommercantObjet;
import com.main.exercice2.androidproject.Commercant.MainCommercant;
import com.main.exercice2.androidproject.EventCalendar;
import com.main.exercice2.androidproject.Interfaces.AlertType;
import com.main.exercice2.androidproject.Post;
import com.main.exercice2.androidproject.R;

import java.util.ArrayList;

public class AlerteListAdapter extends ArrayAdapter<Post>implements Filterable {

    filtre filtre;
    Client client;
    ArrayList<Post> alertes;
    ArrayList<Post> alertesfiltre;
    public AlerteListAdapter(@NonNull Context context, @NonNull ArrayList<Post> objects) {
        super(context, R.layout.rowlayout, objects);
        alertes=objects;
        alertesfiltre=objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final String title = getItem(position).getTitle();
        final String message = getItem(position).getMessage();
        String type = getItem(position).getType();
        Drawable drawable = getItem(position).getDrawable();
        Boolean defaultPicture = getItem(position).getDefaultPicture();

        System.out.println(type);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

        TextView tvTitle = (TextView) rowView.findViewById(R.id.textViewTitle);
        TextView tvMessage = (TextView) rowView.findViewById(R.id.textViewMessage);
        TextView tvType = (TextView) rowView.findViewById(R.id.textViewType);
        ImageView ivPhoto = (ImageView) rowView.findViewById(R.id.photo);
        Button calendar_button = (Button) rowView.findViewById(R.id.calendar_button);
        calendar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialogCustom.AlertDialogCalendar(getContext(),title,message);
            }
        });

        tvTitle.setText(title);
        tvMessage.setText(message);
        tvType.setText(type);

        if(defaultPicture)
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
        else{
            ivPhoto.setImageDrawable(drawable);
        }
        //ivPhoto.setImageDrawable(drawable);
        return rowView;
    }
    @NonNull
    @Override
    public Filter getFilter() {
        if(filtre==null){
            filtre = new filtre();
        }
        return filtre;
    }

    private class filtre extends Filter {

        protected FilterResults performFiltering(Client client) {
            FilterResults filterResults = new FilterResults();
            ArrayList abonnement = client.getAbonnements();
            alertesfiltre= new ArrayList<>();
            for(Post p : alertes){
                CommercantObjet c =p.getCommercant();
                System.out.println("lsit : "+(abonnement)+(abonnement.contains(c)));
                if(abonnement.contains(c)){
                    System.out.println(c);
                    alertesfiltre.add(p);
                }
            }
            filterResults.count=alertesfiltre.size();
            filterResults.values=alertesfiltre;
            return filterResults;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            return performFiltering(ClientList.findClientId(Integer.parseInt(charSequence.toString())));
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            alertes= (ArrayList<Post>) filterResults.values;
            notifyDataSetChanged();
        }
    }

}
