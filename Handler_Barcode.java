package com.example.abdullah.purchy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.EOFException;
import java.util.ArrayList;

/**
 * Created by Abdullah on 12/10/17.
 */

public class Handler_Barcode extends AppCompatActivity implements View.OnClickListener {



    public static String barcode;
    public static ArrayList<Product> arr = new ArrayList<Product>();
    private ProgressDialog loading;
    private TextView formatTxt, contentTxt;
    public static final String DATA_URL4 = "http://purchy.000webhostapp.com/retrive-FreeHosting.php?orderNum=\n";
    public static ArrayList<Product> invo2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);

        Intent intent = getIntent();
        String id = intent.getStringExtra("orderNum");


        invo2 = new ArrayList<Product>();

        getData("hello");
    }
    private void getData(String leng) {
        Intent intent = getIntent();
        String k = intent.getStringExtra("barcode");

       final String  id=k;

            try {
                if (id.equals("")) {
                    Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
                    return;
                }
                loading = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);

                //  String url = config.DATA_URL3+id;
                String url = config.DATA_URL4+id;


                StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        showJSON(response, id);
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Handler_Barcode.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        });

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                stringRequest.setRetryPolicy((new DefaultRetryPolicy(
                        20000,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
                requestQueue.add(stringRequest);
            }catch(Exception e){
                e.printStackTrace();
            }


    }


    private void showJSON(String response,String leng){

        int size = Integer.parseInt(leng);

        try {
            for(int k=0;k<size;k++) {

                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(config.JSON_ARRAY);
                JSONObject collegeData = result.getJSONObject(k);

                Product inv = new Product(collegeData.getString("barcodeNumber"),collegeData.getString("name"),collegeData.getString("price"),
                        collegeData.getString("catName"),"Hi",k);

                invo2.add(inv);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        array();



    }

    public void array(){


        ThreeColumn_ListAdapter adapter =  new ThreeColumn_ListAdapter(this,R.layout.list_adapter_view, invo2);
        ListView   listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);


    }
    @Override
    public void onClick(View v) {

    }





}
