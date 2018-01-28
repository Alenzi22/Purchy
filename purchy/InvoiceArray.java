package com.example.abdullah.purchy;

import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

/**
 * Created by Abdullah on 02/09/17.
 */

public class InvoiceArray extends AppCompatActivity implements Serializable {

    public String order_id;
    public String reg_date;
    public int position;

    public InvoiceArray(String x,String y,int p){
        order_id = x;
        reg_date=y;
        position = p;
    }

    public String getOrder_id(){
        return order_id;
    }
    public String getReg_date(){
return reg_date;
    }
    public int getPosition(){
        return position;
    }
}
