package com.example.abdullah.purchy;

/**
 * Created by Abdullah on 03/09/17.
 */
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.TextureView;
import android.widget.TextView;


//import static com.example.abdullah.purchy.R.id.display_name;


public class AdapterInvo extends ArrayAdapter<InvoiceArray> {

    private Activity activity;
    private ArrayList<InvoiceArray> lPerson;
    private static LayoutInflater inflater = null;

    public AdapterInvo (Activity activity, int textViewResourceId,ArrayList<InvoiceArray> _lPerson) {
        super(activity, textViewResourceId, _lPerson);
        try {
            this.activity = activity;
            this.lPerson = _lPerson;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return lPerson.size();
    }

    public InvoiceArray getItem(InvoiceArray position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_name;
        public TextView display_number;
        public Button payment;

    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.list_item, null);
                holder = new ViewHolder();

                holder.display_name = (TextView) vi.findViewById(R.id.display_name);
                holder.display_number = (TextView) vi.findViewById(R.id.display_number);
                holder.display_name.setText("Order_ID:");
                holder.display_number.setText("Date:");



                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }



            holder.display_name.setText("Order_ID:");


            holder.display_name.setText(lPerson.get(position).order_id);
            holder.display_number.setText("Date:");

            holder.display_number.setText(lPerson.get(position).reg_date);


        } catch (Exception e) {


        }
        return vi;
    }


}
