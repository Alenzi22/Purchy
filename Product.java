package com.example.abdullah.purchy;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

import static com.example.abdullah.purchy.ScannerAct.limit_tracker;

/**
 * Created by Abdullah on 29/08/17.
 */


public class Product extends AppCompatActivity implements Serializable {

    public String product_name;

    public String product_barcode;

    public String product_price;

    public String product_qty;

    public String total;

    public String cat_name;

    public int pos;
    public String invNmb;

    public String actualPrice;

    public Product( String barcode, String name,String qty , String tot, String price,int p){
        product_barcode = barcode;
        product_name = name;
        product_price = price;
        product_qty = qty;
        total = tot;
        pos=p;
    }
    public Product(String n, String p , String b, String ca){
        product_barcode=b;
        product_name=n;
        product_price=p;
        cat_name=ca;
    }
    /* editit at 27 nov
    public Product (String name,String qty){
        product_name=name;
        product_qty=qty;
    } */
    public Product (String c,String t){
        cat_name=c;
        total=t;
    }

    public Product (String bar, String name,String cat,String p,String qty,String Tot,int i,String ac,String invb){
        product_barcode=bar;
        product_name=name;
        cat_name=cat;
        product_price = p;
        product_qty=qty;
        total = Tot;
        pos=i;
        actualPrice = ac;
        invNmb = invb;

    }


    public String getProduct_name(){
        return product_name;
    }
    public String getProduct_price(){
        return product_price;
    }
    public String getProduct_barcode(){
        return product_barcode;
    }
    public String getProduct_catName(){
        return cat_name;
    }
    public String getProduct_qty(){
        return product_qty;
    }
    public String getProduct_total(){
        return total;
    }
    public String getProduct_actual(){
        return actualPrice;
    }
    //public int getProduct_pos(){
        //return pos;
   // }/

    public String toString(){
        return "[" + invNmb +" " + product_barcode + " " + product_qty + " " + product_name + " " + product_price + " " + cat_name + " " +
                " " + total +"]";

    }
    public  void alert(){

            AlertDialog alertDialog = new AlertDialog.Builder(Product.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Your limit is not enoguth to buy this product");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();


    }







}

