package com.example.abdullah.purchy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.abdullah.purchy.Analysis_Controller.avg;
import static com.example.abdullah.purchy.Analysis_Controller.sumer;

/**
 * Created by Abdullah on 28/11/17.
 */

public class Deep_Adapter extends ArrayAdapter<Deep_Array> {


    private LayoutInflater mInflater;
    private ArrayList<Deep_Array> users;
    private int mViewResourceId;

    public Deep_Adapter(Context context, int textViewResourceId, ArrayList<Deep_Array> users) {
        super(context, textViewResourceId, users);
        this.users = users;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);
        final int actualPos=position;

        Deep_Array user = users.get(position);




        if (user != null) {
            TextView Name = (TextView) convertView.findViewById(R.id.Category);
            TextView total = (TextView) convertView.findViewById(R.id.Total_Deep);




            if (Name != null) {
                Name.setText(user.category);
            }

            if (total != null) {
                total.setText((user.total) + " SR");
            }


        }



        return convertView;
    }
}
