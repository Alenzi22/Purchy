package com.example.abdullah.purchy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.abdullah.purchy.ActivityUserProfile.date_time;
import static com.example.abdullah.purchy.ActivityUserProfile.invNumberr;
import static com.example.abdullah.purchy.ScannerAct.totale;
import static com.example.abdullah.purchy.ScannerAct.usr;

/**
 * Created by Abdullah on 09/11/17.
 */

public class Payment_Method extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {

    public static String Selected;
    private Button Next;
    public static String confirm="FALSE";
    private static final String STR_URL = "http://purchy.000webhostapp.com/Invoice.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_method);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Next = (Button) findViewById(R.id.Next);
        Next.setOnClickListener(this);


        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
       // categories.add(" ");

        categories.add("Cash");
        categories.add("Cridet Card ");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        Selected = parent.getItemAtPosition(position).toString();
       /* if(item.equals("Cash")){
            Intent i = new Intent(Payment_Method.this,Cash.class);
            startActivity(i);
        } else{
            Intent t = new Intent(Payment_Method.this,CrdtCard.class);
            startActivity(t);
        }
*/
        // Showing selected spinner item
       // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    public void save(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, STR_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            to_the_next();
                        }else{
                            Toast.makeText(Payment_Method.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Payment_Method.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("username",usr);
                map.put("invoice",invNumberr);
                map.put("date",date_time);
                map.put("state",confirm);
                map.put("total",String.valueOf(totale));

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    public void save2(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, STR_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            to_the_next2();
                        }else{
                            Toast.makeText(Payment_Method.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Payment_Method.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("username",usr);
                map.put("invoice",invNumberr);
                map.put("date",date_time);
                map.put("state",confirm);
                map.put("total",String.valueOf(totale));

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    public void to_the_next(){
        Intent i = new Intent(Payment_Method.this,Cash.class);
        startActivity(i);
    }
    public void to_the_next2(){
        Intent i = new Intent(Payment_Method.this,CrdtCard.class);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        if(v==Next){
            if(Selected.equals("Cash")){
                save();
               /* Intent i = new Intent(Payment_Method.this,Cash.class);
                startActivity(i); */
            }else{
               save2();
              /*  Intent t = new Intent(Payment_Method.this,CrdtCard.class);
                startActivity(t);*/


            }
        }

    }
}

