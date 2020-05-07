package com.main.exercice2.androidproject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class CommercantListAdapter extends ArrayAdapter<CommercantObjet > implements Filterable {
    ArrayList<CommercantObjet> data;
    ArrayList<CommercantObjet> datafiltre;
    filtre filtre;

    public CommercantListAdapter(@NonNull Context context, @NonNull ArrayList<CommercantObjet> objects) {
        super(context, R.layout.researsh_commercant, objects);
        data=objects;
        datafiltre=objects;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CommercantObjet getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String title = getItem(position).getTitle();
        String message = getItem(position).getMessage();
        Drawable drawable =getItem(position).getDrawable();

        //CommercantObjet commercantObjet = new CommercantObjet(title,message,drawable);
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

    @NonNull
    @Override
    public Filter getFilter() {
        if(filtre==null){
            filtre = new filtre();
        }
        return filtre;
    }

    private class filtre extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();
            if(charSequence!=null && charSequence.length()>0){
                ArrayList<CommercantObjet> listfiltre=new ArrayList<>();
                for(CommercantObjet c : datafiltre){
                    if(c.getTitle().toUpperCase().contains(charSequence.toString().toUpperCase())||
                            c.getMessage().toUpperCase().contains(charSequence.toString().toUpperCase())){
                        listfiltre.add(c);
                    }
                }
                filterResults.count=listfiltre.size();
                filterResults.values=listfiltre;
            }
            else {
                filterResults.count=datafiltre.size();
                filterResults.values=datafiltre;
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data= (ArrayList<CommercantObjet>) filterResults.values;
                notifyDataSetChanged();
        }
    }
}
