package com.example.abdullah.purchy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Abdullah on 29/08/17.
 */

public class Seassion extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;

    private Button Cancel;
    private Button Scan;
    private Button Clear;
    private Button Pay;
    private static final String REGISTER_URL = "http://192.168.92.1/Login/Cancelinvoice.php";
    public static final String KEY_Invoice = "invoice";
    public static final String KEY_username = "username";
    public String invoice;
    public String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seassion);


        textView = (TextView) findViewById(R.id.textViewUsername);

        Intent intent = getIntent();

        textView.setText("the invoice # is :  " + intent.getStringExtra("invoice") + " your name is " + intent.getStringExtra("username"));


        ListView listView = (ListView) findViewById(R.id.itemsList);
        Cancel = (Button) findViewById(R.id.Cancel);
        Clear = (Button) findViewById(R.id.Clear);

        Clear.setOnClickListener(this);

        Cancel.setOnClickListener(this);

    }
    public void cancel(){
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        invoice = intent.getStringExtra("invoice");


        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Intent i = new Intent(Seassion.this,ActivityUserProfile.class);
                            i.putExtra(KEY_username, username);

                            startActivity(i);
                        }else{
                            Toast.makeText(Seassion.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Seassion.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_username,username);
                map.put(KEY_Invoice,invoice);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



    @Override
    public void onClick(View v) {
        if(v==Cancel){
            cancel();
        }






    }


}
