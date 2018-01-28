package com.example.abdullah.purchy;

/**
 * Created by Abdullah on 06/10/17.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.widget.Toast;

import static com.example.abdullah.purchy.ActivityUserProfile.lim;
import static com.example.abdullah.purchy.MainActivity.invo2;
import static com.example.abdullah.purchy.ScannerAct.Limit;
import static com.example.abdullah.purchy.ScannerAct.Totaler;
//import static com.example.abdullah.purchy.ScannerAct.invo2;
import static com.example.abdullah.purchy.ScannerAct.limit_tracker;
import static com.example.abdullah.purchy.ScannerAct.limitt;
import static com.example.abdullah.purchy.ScannerAct.qt;
import static com.example.abdullah.purchy.ScannerAct.steps;
import static com.example.abdullah.purchy.ScannerAct.totale;
import static com.example.abdullah.purchy.ScannerAct.tote;



public class Adapter_Shopping extends ArrayAdapter<Product>  {


    private LayoutInflater mInflater;
    private ArrayList<Product> users;
    private int mViewResourceId;

    public Adapter_Shopping(Context context, int textViewResourceId, ArrayList<Product> users) {
        super(context, textViewResourceId, users);
        this.users = users;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public void del(int poss){
        if(users.size()==1){

            limit_tracker = limitt;
            tote="0.0";
            users.clear();
            Totaler.setText("Total:" + tote  );
            Limit.setText("Limit:" + limit_tracker  );
            notifyDataSetChanged();

        } else {
            double update_total = 0.0;
            double update_limit = 0.0;

            for (int i = 0; i < users.size(); i++) {
                update_total += Double.parseDouble(users.get(poss).product_price);
            }
            update_limit = limitt - update_total;

            tote = String.valueOf(update_total);
            limit_tracker = update_limit;
            Totaler.setText("Total:" + tote);
            Limit.setText("Limit:" + limit_tracker);


            notifyDataSetChanged();
        }

    }
    public void incre(int poss){
        //1
        double update_total =0.0;
        //2
        double update_limit=0.0;
        //3
        String target_element_actual_price = users.get(poss).actualPrice;
        //4
        double target_element_new_price=0.0;
        //5
        int target_element_new_qty=0;




        //5
        target_element_new_qty++;
        target_element_new_price = target_element_new_qty* Double.parseDouble(target_element_actual_price);

        users.get(poss).product_price = String.valueOf(target_element_new_price);
        users.get(poss).product_qty = String.valueOf(target_element_new_qty);


        for(int i=0;i<users.size();i++){
            update_total += Double.parseDouble(users.get(poss).product_price);
        }
        update_limit = limitt - update_total;

        tote = String.valueOf(update_total);
        limit_tracker = update_limit;
        Totaler.setText("Total:" + tote  );
        Limit.setText("Limit:" + limit_tracker  );


        notifyDataSetChanged();






    }

    public void decre(int poss){

       //1
        double update_total = 0.0;
        //2
        double update_limit = 0.0;
        if(users.get(poss).product_qty=="1"){

            users.remove(poss);


            for (int i = 0; i < users.size(); i++) {
                update_total += Double.parseDouble(users.get(poss).product_price);
            }
            update_limit = limitt - update_total;

            tote = String.valueOf(update_total);
            limit_tracker = update_limit;
            Totaler.setText("Total:" + tote);
            Limit.setText("Limit:" + limit_tracker);


        } else {

            //3
            String target_element_actual_price = users.get(poss).actualPrice;
            //4
            double target_element_new_price=0.0;
            //5
            int target_element_new_qty=0;

             target_element_new_qty = Integer.parseInt(users.get(poss).product_qty);

            target_element_new_qty--;

            target_element_new_price = Double.parseDouble(target_element_actual_price) * target_element_new_qty;
            users.get(poss).product_price = String.valueOf(target_element_new_price);
            users.get(poss).product_qty = String.valueOf(target_element_new_qty);

            for(int i=0;i<users.size();i++){
                update_total += Double.parseDouble(users.get(poss).product_price);


            }





        }

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);
        final int actualPos=position;

        Product user = users.get(position);
        final ImageButton delete = (ImageButton) convertView.findViewById(R.id.Delete_sh);
        ImageButton Add = (ImageButton) convertView.findViewById(R.id.Add_sh);
        ImageButton Dec = (ImageButton) convertView.findViewById(R.id.Dec_sh);




        if (user != null) {
            TextView Name = (TextView) convertView.findViewById(R.id.Name_sh);
            TextView Price = (TextView) convertView.findViewById(R.id.Price_sh);
            TextView Qty = (TextView) convertView.findViewById(R.id.QTY_sh);
         //  TextView Total = (TextView) convertView.findViewById(R.id.tote_sh);

            if (Name != null) {
                Name.setText(user.product_name);
            }

            if (Price != null) {
                Price.setText((user.product_price));
            }
            if (Qty != null) {
                Qty.setText((user.product_qty));
            }
        //   if (Total != null) {

          //  }


        }



        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                if(users.size()==1){
                    limit_tracker=0.0;

                    limit_tracker = limitt;
                    totale =0.0;
                    tote=String.valueOf(totale);
                    users.remove(actualPos);

                    Totaler.setText("Total:" + tote  );
                    Limit.setText("Limit:" + limit_tracker  );
                    Limit.setTextColor(Color.rgb(0,0,0));

                    notifyDataSetChanged();

                } else {
                    users.remove(actualPos);
                    double update_total = 0.0;
                    double update_limit = 0.0;

                    for (int i = 0; i < users.size(); i++) {
                        update_total += Double.parseDouble(users.get(i).product_price);
                    }
                    update_limit = limitt - update_total;

                    tote = String.valueOf(update_total);
                    limit_tracker = update_limit;
                    Totaler.setText("Total:" + tote);
                    Limit.setText("Limit:" + limit_tracker);
                    Limit.setTextColor(Color.rgb(0,0,0));



                    notifyDataSetChanged();
                }
              //  notifyDataSetChanged();
            }
        });
        Add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                double update_total =0.0;
                //2
                double update_limit=0.0;
                //3
                String target_element_actual_price = users.get(actualPos).actualPrice;
                //4
                double target_element_new_price=0.0;
                //5
                int target_element_new_qty=Integer.parseInt(users.get(actualPos).product_qty);




                //5
                target_element_new_qty++;
              //  target_element_new_price = target_element_new_qty* Double.parseDouble(target_element_actual_price);
                if(Double.parseDouble(target_element_actual_price)<=limit_tracker){

                    target_element_new_price = target_element_new_qty* Double.parseDouble(target_element_actual_price);


                users.get(actualPos).product_price = String.valueOf(target_element_new_price);
                users.get(actualPos).product_qty = String.valueOf(target_element_new_qty);


                for(int i=0;i<users.size();i++){
                    update_total += Double.parseDouble(users.get(i).product_price);
                }
                update_limit = limitt - update_total;

                tote = String.valueOf(update_total);
                limit_tracker = update_limit;
                Totaler.setText("Total:" + tote  );
                Limit.setText("Limit:" + limit_tracker  );


                notifyDataSetChanged();



            }else{
                  Limit.setTextColor(Color.rgb(200,0,0));




                }
            }
        });
        Dec.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //1
                double update_total = 0.0;
                //2
                double update_limit = 0.0;
                int now =Integer.parseInt(users.get(actualPos).product_qty)-1;
                if(now <= 0){

                    users.remove(actualPos);


                    for (int i = 0; i < users.size(); i++) {
                        update_total += Double.parseDouble(users.get(i).product_price);
                    }

                    update_limit = limitt - update_total;
                    if(users.size()==0){
                        totale=0.0;
                        tote=String.valueOf(totale);
                    } else {
                        tote = String.valueOf(update_total);

                    }
                    limit_tracker = update_limit;
                    Totaler.setText("Total:" + tote);
                    Limit.setText("Limit:" + limit_tracker);
                    Limit.setTextColor(Color.rgb(0,0,0));



                } else {

                    //3
                    String target_element_actual_price = users.get(actualPos).actualPrice;
                    //4
                    double target_element_new_price=0.0;
                    //5
                    int target_element_new_qty=0;

                    target_element_new_qty = Integer.parseInt(users.get(actualPos).product_qty);

                    target_element_new_qty--;

                    target_element_new_price = Double.parseDouble(target_element_actual_price) * target_element_new_qty;
                    users.get(actualPos).product_price = String.valueOf(target_element_new_price);
                    users.get(actualPos).product_qty = String.valueOf(target_element_new_qty);

                    for(int i=0;i<users.size();i++){
                        update_total += Double.parseDouble(users.get(i).product_price);


                    }
                    update_limit = limitt - update_total;
                    tote=String.valueOf(update_total);
                    limit_tracker = update_limit;

                    Totaler.setText("Total:" + tote);
                    Limit.setText("Limit:" + limit_tracker);
                    Limit.setTextColor(Color.rgb(0,0,0));






                }

                notifyDataSetChanged();
            }
        });


        return convertView;
    }
}
