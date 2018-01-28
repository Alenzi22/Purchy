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

import static com.example.abdullah.purchy.Analysis_Controller.avg;
import static com.example.abdullah.purchy.Analysis_Controller.sumer;


public class Analayzer1 extends ArrayAdapter<Analayzer_Array>  {


    private LayoutInflater mInflater;
    private ArrayList<Analayzer_Array> users;
    private int mViewResourceId;

    public Analayzer1(Context context, int textViewResourceId, ArrayList<Analayzer_Array> users) {
        super(context, textViewResourceId, users);
        this.users = users;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);
        final int actualPos=position;

        Analayzer_Array user = users.get(position);




        if (user != null) {
            TextView Name = (TextView) convertView.findViewById(R.id.invo_nb);
            TextView Price = (TextView) convertView.findViewById(R.id.inv_date);
            TextView Qty = (TextView) convertView.findViewById(R.id.total_ana);
            TextView Sum = (TextView) convertView.findViewById(R.id.total_analayzer);
            TextView Avg = (TextView) convertView.findViewById(R.id.avg_analayzer);


            if (Name != null) {
                Name.setText(user.inv_nb);
            }

            if (Price != null) {
                Price.setText((user.inv_date));
            }
            if (Qty != null) {
                Qty.setText((user.inv_total) + " SR");
            }
            if(Sum !=null){
                Sum.setText("Total: " + String.valueOf(sumer) + " SR");

            }
            if(Avg!=null){
                Avg.setText("Avarage: " + String.valueOf(avg));
            }
            //   if (Total != null) {

            //  }


        }



        return convertView;
    }
}
