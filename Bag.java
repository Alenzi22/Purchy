package com.example.abdullah.purchy;

/**
 * Created by Abdullah on 23/08/17.
 */

public class Bag {
    public double price;
    public String name;
    public int qty;
    public String adr;

    public Bag(){
        price=0.0;
        name="";
        qty=0;
    }

    public Bag (double p , String n , int q ){
        price=p;
        name=n;
        qty=q;
    }
    public Bag(String n , String ad){
        adr = ad;
        name = n;

    }

    public void setPrice(double p){
        price=p;
    }
    public void setName(String n){
        name=n;
    }
    public void setQty(int q){
        qty=q;
    }

    public double getPrice(){
        return price;
    }
    public String getName(){
        return name;
    }
    public int getQty(){
        return qty;
    }
}
