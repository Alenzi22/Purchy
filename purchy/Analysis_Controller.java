package com.example.abdullah.purchy;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.abdullah.purchy.ActivityUserProfile.analayzer_length;


/**
 * Created by Abdullah on 21/11/17.
 */

public class Analysis_Controller extends AppCompatActivity implements View.OnClickListener {

    private Button General;
    private Button Specific;
    private ProgressDialog loading;
    public static double sumer;
    public static double avg;

    public static ArrayList<Analayzer_Array> analy;
    public static ArrayList<Analayzer_Array> analy_deep;

    public static String[] array_to = new String[analayzer_length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analysis_type);

        analy = new ArrayList<Analayzer_Array>();
        analy_deep=new ArrayList<Analayzer_Array>();


        General = (Button) findViewById(R.id.General_Analysis);
       Specific = (Button) findViewById(R.id.Specific);

        General.setOnClickListener(this);
        Specific.setOnClickListener(this);
        analy_deep.clear();
        analy.clear();
        avg=0.0;
        sumer=0.0;

    }
    public void alertt(){

        AlertDialog alertDialog = new AlertDialog.Builder(Analysis_Controller.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("ll " );
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }

    private void getData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("username");
        //  id="3";

        if (id.equals("")) {
            Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
            return;
        }
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        //  String url = config.DATA_URL3+id;
        String url = config.DATA_URL6+id;


        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Analysis_Controller.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){

        try {
            // for(int k=0;k<3;k++) {
            for(int k=0;k<analayzer_length;k++) {

                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(config.JSON_ARRAY);
                JSONObject collegeData = result.getJSONObject(k);

                Analayzer_Array inv = new Analayzer_Array(collegeData.getString("order_id"),collegeData.getString("reg_date"),collegeData.getString("total"),k);

                //  invo[k] = inv;
                analy.add(inv);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        findSum_avg();


    }

    public void findSum_avg(){
        for(int i=0;i<analy.size();i++){
            sumer +=Double.parseDouble(analy.get(i).inv_total);

        }
        avg=sumer/analayzer_length;
        GoToGeneral();

    }



    public void GoToGeneral(){
        Intent i = new Intent(Analysis_Controller.this,Analayzer_General.class);
        startActivity(i);
    }


    public void getInvoices(){
        Intent intent = getIntent();
        String id = intent.getStringExtra("username");
        //  id="3";

        if (id.equals("")) {
            Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
            return;
        }
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        //  String url = config.DATA_URL3+id;
        String url = config.DATA_URL6+id;


        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON2(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Analysis_Controller.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
    private void showJSON2(String response){

        try {
            // for(int k=0;k<3;k++) {
            for(int k=0;k<analayzer_length;k++) {

                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(config.JSON_ARRAY);
                JSONObject collegeData = result.getJSONObject(k);

                Analayzer_Array inv = new Analayzer_Array(collegeData.getString("order_id"),collegeData.getString("reg_date"),collegeData.getString("total"),k);

                //  invo[k] = inv;
                analy_deep.add(inv);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

      //  findSum_avg();
        putItInArray();


    }

    public void putItInArray(){
        String inv_d;

        for(int i=0;i<analy_deep.size();i++){
          //  inv_d=analy_deep.get(i).inv_date;
           // String[] one = inv_d.split(" ");


            array_to[i] = analy_deep.get(i).inv_date;

        }

       goToDeep();



    }

    public void goToDeep(){

        Intent i= new Intent(Analysis_Controller.this,Analsys_Deep.class);
        startActivity(i);
    }






    @Override
    public void onClick(View v) {
        if(v==General){
           /* Intent i = new Intent(Analysis_Controller.this,Analayzer_General.class);
            startActivity(i); */
           if(analy.size()>0) {
               analy.clear();
               sumer=0;
               avg=0;
           }
            sumer=0;
            avg=0;
           getData();
           // alertt();
        }
        if(v==Specific){
            if(analy_deep.size()>0){
                analy_deep.clear();

            }
            getInvoices();
        }

    }

}
