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
import java.util.Date;

public class ShopListAdaptor extends ArrayAdapter<List> {
    private Context mContext;
    int mResource;
    public ShopListAdaptor(Context context, int resource, ArrayList<List> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;


    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int id = getItem(position).getId();
        String name = getItem(position).getName();
        String date = getItem(position).getCreatedDate();


        List list = new List(id, name, date);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView txtId = convertView.findViewById(R.id.ID);
        TextView txtName = convertView.findViewById(R.id.list);
        TextView txtDate = convertView.findViewById(R.id.CreatedDate);


        txtId.setText(Integer.toString(id));
        txtName.setText(name);
        txtDate.setText("" + date);



        return convertView;
    }
}
