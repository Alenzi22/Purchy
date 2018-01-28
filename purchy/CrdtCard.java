package com.example.abdullah.purchy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static com.example.abdullah.purchy.MainActivity.invo2;
import static com.example.abdullah.purchy.ScannerAct.totale;
import static com.example.abdullah.purchy.ScannerAct.usr;

/**
 * Created by Abdullah on 09/11/17.
 */

public class CrdtCard extends AppCompatActivity implements View.OnClickListener {

    public static final String CRIDET_URL = "http://purchy.000webhostapp.com/Crdt_Card.php";
    public static final String Store_Invo_Cr_MySQL_URL = "http://purchy.000webhostapp.com/Ar.php";
    public static final String C_ISTHERE = "http://purchy.000webhostapp.com/c_isThere.php";
    public static final String VALIDATE_URL = "http://purchy.000webhostapp.com/validate.php";


    private EditText Crid_Number;
    private Button Crid_Next;
    public static String crd_nb;
    public static String json_c;



    @Override
protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ctdt_card);

        Crid_Number = (EditText) findViewById(R.id.crd_number);
        Crid_Next = (Button) findViewById(R.id.button_crdt_card);


        Crid_Next.setOnClickListener(this);
        }

    private void Crdt_Card_Payment() {



        StringRequest stringRequest = new StringRequest(Request.Method.POST, CRIDET_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                           // openProfile();
                            goJson_Crd();

                        }else{
                            Toast.makeText(CrdtCard.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CrdtCard.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("username",usr);
                map.put("cNm",crd_nb);
                map.put("total",String.valueOf(totale));
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy( (new DefaultRetryPolicy(
                5000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
        requestQueue.add(stringRequest);
    }

    public void goJson_Crd(){
        char comma = '"';
        String y = "[";
        for(int i=0;i<invo2.size();i++){
            if(i != invo2.size()-1){
                y+= 	"\n"+ "\t{\n" +"\t"+ comma  + "order_id" + comma +":" +" "  + comma
                        + invo2.get(i).invNmb + comma + "," +"\n" +"\t"+  comma +"barcodeNumber"+ comma +
                        ":" +" "  + comma + invo2.get(i).product_barcode + comma + "," + "\n" +"\t"+ comma  +
                        "item_qty" + comma +":"+ comma + invo2.get(i).product_qty + comma  + "," + "\n" + "\t" + comma
                        +"total"+ comma +
                        ":" +" "  + comma + invo2.get(i).total + comma + "," + "\n"+ "\t"  + comma + "name"+ comma +
                        ":" +" "  + comma + invo2.get(i).product_name + comma + "," + "\n" + "\t" + comma +"price"+ comma +
                        ":" +" "  + comma + invo2.get(i).product_price + comma + "," + "\n"+ "\t" + comma+ "catName"+ comma +
                        ":" +" "  + comma + invo2.get(i).cat_name + comma + "\n"  + "\t\t\n}," ;
            } else{
                y+= 	"\n"+ "\t{\n" +"\t"+ comma  + "order_id" + comma +":" +" "  + comma
                        + invo2.get(i).invNmb + comma + "," +"\n" +"\t"+  comma +"barcodeNumber"+ comma +
                        ":" +" "  + comma + invo2.get(i).product_barcode + comma + "," + "\n" +"\t"+ comma  +
                        "item_qty" + comma +":"+ comma + invo2.get(i).product_qty + comma  + "," + "\n" + "\t" + comma
                        +"total"+ comma +
                        ":" +" "  + comma + invo2.get(i).total + comma + "," + "\n"+ "\t"  + comma + "name"+ comma +
                        ":" +" "  + comma + invo2.get(i).product_name + comma + "," + "\n" + "\t" + comma +"price"+ comma +
                        ":" +" "  + comma + invo2.get(i).product_price + comma + "," + "\n"+ "\t" + comma+ "catName"+ comma +
                        ":" +" "  + comma + invo2.get(i).cat_name + comma + "\n"  + "\t\t\n}" ;

            }

        }
        y+="\n]";
        json_c =y;
        now_validate();
        //store_invo2_in_my_sql();
    }
    public void now_validate(){



        StringRequest stringRequest = new StringRequest(Request.Method.POST, VALIDATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(CrdtCard.this,response,Toast.LENGTH_LONG).show();
                        store_invo2_in_my_sql();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CrdtCard.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",usr);
                params.put("order_id", invo2.get(0).invNmb);

                return params;
            }

        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy((new DefaultRetryPolicy(
                20000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
        requestQueue.add(stringRequest);

    }
    public void store_invo2_in_my_sql(){



        StringRequest stringRequest = new StringRequest(Request.Method.POST, Store_Invo_Cr_MySQL_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(Cash.this,response,Toast.LENGTH_LONG).show();
                        fin();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CrdtCard.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("invo2", json_c);

                return params;
            }

        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy((new DefaultRetryPolicy(
                20000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
        requestQueue.add(stringRequest);

    }

    public void fin(){
        Intent i = new Intent(CrdtCard.this,Finally_Result.class);
        startActivity(i);
    }

    public void c_isThere(){

       /*   if(Crid_Number.getText().toString().equals("")){

        AlertDialog alertDialog = new AlertDialog.Builder(CrdtCard.this).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Please Enter Your Cridet Card Number. ");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

        } else */

        crd_nb = Crid_Number.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, C_ISTHERE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.trim().equals("0")){
                            Crdt_Card_Payment();

                            // display(lengthO);
                            //   goToInvices(lengtho);
                        }else{
                            AlertDialog alertDialog = new AlertDialog.Builder(CrdtCard.this).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("ERROR: WRONG CRIDET CARD NUMBER");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CrdtCard.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();

                map.put("cNm",crd_nb);
                // map.put(KEY_Length,lengtho);
                return map;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy((new DefaultRetryPolicy(
                10000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
        requestQueue.add(stringRequest);


    }


    public void Notempty(){

        if(Crid_Number.getText().toString().equals("")){

            AlertDialog alertDialog = new AlertDialog.Builder(CrdtCard.this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage("Please Enter Your Cridet Card Number. ");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        } else if(Crid_Number.getText().toString().equals("")==false){
            c_isThere();
        }
    }



@Override
public void onClick(View v) {

    if(v==Crid_Next){
        Notempty();
       // c_isThere();

    }

        }


}
