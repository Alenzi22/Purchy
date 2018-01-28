package com.example.abdullah.purchy;

import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;

/**
 * Created by Abdullah on 28/11/17.
 */

public class Deep_Array extends AppCompatActivity implements Serializable {

    public String category;
    public String total;
    public int pos;


    public Deep_Array(String i1,String i2,int i4){
        category=i1;
        total = i2;
        pos=i4;

    }



}

