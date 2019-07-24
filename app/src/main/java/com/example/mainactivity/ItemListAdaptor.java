package com.example.mainactivity;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ItemListAdaptor extends ArrayAdapter<Item> {
    private static final String TAG = "ItemListAdaptor";
    private Context mContext;
    int mResource;



    public ItemListAdaptor(Context context, int resource, ArrayList<Item> objects) {
        super(context, resource,  objects);
        mContext = context;
        mResource = resource;


    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int id = getItem(position).getId();
        String name = getItem(position).getName();
        int quantity = getItem(position).getQuantity();
        double price = getItem(position).getPrice();
        boolean isBought = getItem(position).isSelected();
        Log.d("myTag", Boolean.toString(isBought));

        Item item = new Item(id, name, quantity, price, 1);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView txtId = convertView.findViewById(R.id.id);
        TextView txtName = convertView.findViewById(R.id.name);
        TextView txtQuantity = convertView.findViewById(R.id.quantity);
        TextView txtPrice = convertView.findViewById(R.id.price);

        txtId.setText(Integer.toString(position + 1));
        txtName.setText(name);

        txtQuantity.setText(Integer.toString(quantity));
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        String total = "$ " + String.format("%.2f", price);
        txtPrice.setText(total);

        if (isBought) {
            txtId.setPaintFlags(txtId.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            txtName.setPaintFlags(txtName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            txtQuantity.setPaintFlags(txtQuantity.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            txtPrice.setPaintFlags(txtPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }

        return convertView;
    }
}

