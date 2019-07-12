package com.example.mainactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyPastExpenditureAdaptor extends ArrayAdapter<ExpenditureList> {
    private Context mContext;
    int mResource;
    public MyPastExpenditureAdaptor(Context context, int resource, ArrayList<ExpenditureList> objects) {
        super(context, resource, objects);

        mContext = context;
        mResource = resource;


    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int id = getItem(position).getId();
        String name = getItem(position).getName();
        String category = getItem(position).getCategory();
        String date = getItem(position).getCreatedDate();



        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView txtId = convertView.findViewById(R.id.id);
        TextView txtName = convertView.findViewById(R.id.name);
        TextView txtLocation = convertView.findViewById(R.id.location);
        TextView txtDate = convertView.findViewById(R.id.date);


        txtId.setText(Integer.toString(position + 1));
        txtName.setText(name);
        txtLocation.setText(category);
        txtDate.setText("" + date);



        return convertView;
    }
}
