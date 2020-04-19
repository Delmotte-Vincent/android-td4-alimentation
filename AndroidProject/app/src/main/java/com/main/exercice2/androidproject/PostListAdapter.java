package com.main.exercice2.androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PostListAdapter extends ArrayAdapter<Post> {

    private static final String TAG = "PostListAdapter";
    private Context mContext;
    int mRessource;

    public PostListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Post> objects) {
        super(context, resource, objects);
        mContext = context;
        mRessource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String title = getItem(position).getTitle();
        String message = getItem(position).getMessage();

        Post post = new Post(title, message);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mRessource, parent, false);

        TextView tvTitle = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvMessage = (TextView) convertView.findViewById(R.id.textView2);

        tvTitle.setText(title);
        tvMessage.setText(message);

        return convertView;
    }
}
