package com.example.abdullah.purchy;

/**
 * Created by Abdullah on 25/08/17.
 */
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


 //  public static final String LOGIN_URL = "http://192.168.92.1/Login/Login.php";
  public static final String LOGIN_URL = "http://purchy.000webhostapp.com/Login.php";
    public static final String DELETE_ALL = "http://purchy.000webhostapp.com/deleteALL.php";


    public static final String KEY_USERNAME="username";
    public static final String KEY_PASSWORD="password";

    private EditText editTextUsername2;
    private EditText editTextPassword2;
    private Button buttonLogin2;
    private Button Reg_1;


    private String username;
    private String password;

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

    public boolean login_orNot(EditText u,EditText p){
        if(u.getText().equals(null)==false && p.getText().equals(null)==false){
            return true;


        } else{
            return false;
        }


    }


    private void userLogin() {

        username = editTextUsername2.getText().toString().trim();
        password = editTextPassword2.getText().toString().trim();
        if (login_orNot(editTextUsername2,editTextPassword2)) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.trim().equals("success")) {
                                // openProfile();
                                deleteALL(username);
                            } else {
                                Toast.makeText(LoginActivity.this, response, Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(KEY_USERNAME, username);
                    map.put(KEY_PASSWORD, password);
                    return map;
                }
            };


            RequestQueue requestQueue = Volley.newRequestQueue(this);
            stringRequest.setRetryPolicy((new DefaultRetryPolicy(
                    5000,
                    0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
            requestQueue.add(stringRequest);
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage(" Please fill the required information with your username and password  ");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    private void openProfile(){
        Intent intent = new Intent(LoginActivity.this, ActivityUserProfile.class);
        intent.putExtra(KEY_USERNAME, username);
        startActivity(intent);
    }

    public void deleteALL(String user){
        username = user;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DELETE_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            openProfile();
                        }else{
                          //  openProfile();

                                }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG ).show();
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
            Intent i =new Intent(LoginActivity.this,Register.class);
            startActivity(i);
        }
    }

}