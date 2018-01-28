package com.example.abdullah.purchy;

import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

/**
 * Created by Abdullah on 21/11/17.
 */

public class Analayzer_Array extends AppCompatActivity implements Serializable {

    public String inv_total;
    public String inv_date;
    public String inv_nb;
    public int pos;


public Analayzer_Array(String i1,String i2,String i3,int i4){
    inv_nb=i1;
    inv_date = i2;
    inv_total=i3;
    pos=i4;

}



}

