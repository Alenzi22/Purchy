package com.example.abdullah.purchy;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.Intent;

public class Register extends AppCompatActivity implements View.OnClickListener {
    // private static final String REGISTER_URL = "http://192.168.92.1/Login/Reg.php";
    private static final String REGISTER_URL = "http://purchy.000webhostapp.com/Reg.php";


    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";

    private EditText name;
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    //private EditText confirm_p;




    public static  String username_reg ="[a-zA-Z0-9_-]{3,}";


    private Button buttonRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = (EditText) findViewById(R.id.username_register_edittext);
        editTextPassword = (EditText) findViewById(R.id.password_register_edittext);
        editTextEmail= (EditText) findViewById(R.id.email_register_edittext);
        //name=(EditText) findViewById(R.id.name_register_edittext);
       // confirm_p=(EditText) findViewById(R.id.confirm_password_register_edittext);

        buttonRegister = (Button) findViewById(R.id.register_button);

        buttonRegister.setOnClickListener(this);

    }

    private void registerUser(String sdate) {
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String date = sdate;
        if (valid(editTextEmail) == true && valid2(editTextUsername) && password.length()>3 ) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.trim().equals("Successfully Registered")) {
                                AlertDialog alertDialog = new AlertDialog.Builder(Register.this).create();
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage("The Account is created Successfully.  ");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                Intent i = new Intent(Register.this, LoginActivity.class);
                                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                                                startActivity(i);
                                                finish();
                                            }
                                        });
                                alertDialog.show();


                            } else {
                                AlertDialog alertDialog = new AlertDialog.Builder(Register.this).create();
                                alertDialog.setTitle("Alert");
                                alertDialog.setMessage("The username Already exists.  ");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();

                              //  Toast.makeText(Register.this, response, Toast.LENGTH_LONG).show();
                            }

                           // Toast.makeText(Register.this, response, Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Register.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_USERNAME, username);
                    params.put(KEY_PASSWORD, password);
                    params.put(KEY_EMAIL, email);
                    params.put("date", date);

                    return params;
                }

            };

        /*
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);*/

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            stringRequest.setRetryPolicy((new DefaultRetryPolicy(
                    20000,
                    0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
            requestQueue.add(stringRequest);
        } else{
            if(password.length()<=3){
                AlertDialog alertDialog = new AlertDialog.Builder(Register.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("The password length should be greater than 3  ");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            } if(valid(editTextEmail) == false || valid2(editTextUsername)==false ) {
                AlertDialog alertDialog = new AlertDialog.Builder(Register.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Not valid username OR email. ");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }
    }
    public void dateTime(){


        Calendar c = Calendar.getInstance();
        int x = c.get(Calendar.MONTH) + 1;
        String Month = String.valueOf(x);


        String sDate = c.get(Calendar.YEAR) + "-"
                + Month
                + "-" + c.get(Calendar.DAY_OF_MONTH)
                + " " + c.get(Calendar.HOUR_OF_DAY)
                + ":" + c.get(Calendar.MINUTE)
                + ":" + c.get(Calendar.SECOND);


        registerUser(sDate);
    }

    public static boolean valid(EditText argEditText) {

        try {
            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher matcher = pattern.matcher(argEditText.getText());
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean valid2(EditText argEditText) {

        try {
            Pattern pattern = Pattern.compile(username_reg);
            Matcher matcher = pattern.matcher(argEditText.getText());
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public void onClick(View v) {
        if(v == buttonRegister){
           // registerUser();
            dateTime();

        }

    }
}
