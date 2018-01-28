package com.example.abdullah.purchy;

/**
 * Created by Abdullah on 06/10/17.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mitch on 2016-05-06.
 */
public class ThreeColumn_ListAdapter extends ArrayAdapter<Product> {

    private LayoutInflater mInflater;
    private ArrayList<Product> users;
    private int mViewResourceId;

    public ThreeColumn_ListAdapter(Context context, int textViewResourceId, ArrayList<Product> users) {
        super(context, textViewResourceId, users);
        this.users = users;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

        Product user = users.get(position);


        if (user != null) {
           // TextView above1 = (TextView) convertView.findViewById(R.id.above_FirstName);
            //TextView above2 = (TextView) convertView.findViewById(R.id.above_lastName);
            //TextView above3 = (TextView) convertView.findViewById(R.id.above_N1);
            //TextView above4 = (TextView) convertView.findViewById(R.id.above_N2);
            TextView firstName = (TextView) convertView.findViewById(R.id.textFirstName);
            TextView lastName = (TextView) convertView.findViewById(R.id.textLastName);
            TextView favFood = (TextView) convertView.findViewById(R.id.textFavFood);
           TextView N1 = (TextView) convertView.findViewById(R.id.N1);
          //  TextView N2 = (TextView) convertView.findViewById(R.id.N2);
          /*  above1.setText("Item");
            above2.setText("Qunty");
            above3.setText("Price");
            above4.setText("Total");*/


            if (firstName != null) {
                firstName.setText(user.product_name);
            }
            if (lastName != null) {
                lastName.setText((user.product_qty));
            }
            if (favFood != null) {
                favFood.setText((user.product_price));
            }
            if (N1 != null) {
                N1.setText((user.total));
            }
          /* if (N2 != null) {
                N1.setText((user.total));
            } */
        }

        return convertView;
    }
}
