package com.example.abdullah.purchy;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //  public static final String LOGIN_URL = "http://192.168.92.1/Login/Login.php";
    public static final String LOGIN_URL = "http://purchy.000webhostapp.com/Login.php";
    public static final String DELETE_ALL = "http://purchy.000webhostapp.com/deleteALL.php";

    public static final String TYPE_URL = "http://purchy.000webhostapp.com/get_login_type.php";

    public static final String KEY_USERNAME="username";
    public static final String KEY_PASSWORD="password";

    private EditText editTextUsername2;
    private EditText editTextPassword2;
    private Button buttonLogin2;
    private Button Reg_1;
    private ProgressDialog loading;



    public static  String username;
    private String password;
    public static final ArrayList<Product> invo2 = new ArrayList<Product>();
    public static String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        editTextUsername2 = (EditText) findViewById(R.id.editTextUsername2);
        editTextPassword2 = (EditText) findViewById(R.id.editTextPassword2);


        //buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin2 = (Button) findViewById(R.id.buttonLogin2);
        Reg_1 = (Button) findViewById(R.id.to_register_activity_button);



        buttonLogin2.setOnClickListener(this);
        Reg_1.setOnClickListener(this);


    }










    private void userLogin() {

        username = editTextUsername2.getText().toString().trim();
        password = editTextPassword2.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            //openProfile();
                            deleteALL(username);
                        }else{
                            Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_USERNAME,username);
                map.put(KEY_PASSWORD,password);
                return map;
            }
        };


        /*
          RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy( (new DefaultRetryPolicy(
                5000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
         */
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy( (new DefaultRetryPolicy(
                5000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
        requestQueue.add(stringRequest);
    }

    private void openProfile(String user){


      //  type="user";
        if(username.equals("admin")){
            Intent intent = new Intent(MainActivity.this, Vendor_Validator.class);
            intent.putExtra(KEY_USERNAME, username);
            startActivity(intent);

        } else {
            Intent intent = new Intent(MainActivity.this, ActivityUserProfile.class);
            intent.putExtra(KEY_USERNAME, username);
            startActivity(intent);
        }

    }

    public void deleteALL(String user){
        username = user;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DELETE_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            openProfile(username);
                        }else{
                             openProfile(username);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_USERNAME,username);
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
    @Override
    public void onClick(View v) {
        if(v==buttonLogin2) {
            userLogin();
        }

        if(v==Reg_1){
            Intent i =new Intent(MainActivity.this,Register.class);
            startActivity(i);
        }
    }}