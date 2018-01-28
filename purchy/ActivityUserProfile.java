package com.example.abdullah.purchy;

/**
 * Created by Abdullah on 25/08/17.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog.*;
import android.app.AlertDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Random.*;
public class ActivityUserProfile extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private ImageButton regOut;
    private Button NewSeassion;
    private Button ShowAll;
    private Button Analsys;

    private TextView textViewResult;
    public static Double lim;



    private static final String REGISTER_URL = "http://purchy.000webhostapp.com/Invoice.php";
    private static final String INVOICELENGTH_URL = "http://purchy.000webhostapp.com/getNBofINvoices.php";


    public static final String KEY_Invoice = "invoice";
    public static final String KEY_username = "username";
    public static final String KEY_Date = "date";

    public static final String KEY_Length = "lengtho";
    public static final String getURL = "http://192.168.92.1/Login/getAllInvoices/InvFromMYSQL/getInv.php";



    private String username;
    private String invoice;
    public static String date;
    public static String lengtho;
    public static String invNumberr;
    public static String date_time;
    public static int analayzer_length;
    public static  String m_Text = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityuserprofile);


        textView = (TextView) findViewById(R.id.textViewUsername);

       regOut = (ImageButton) findViewById(R.id.LogOut);
        NewSeassion = (Button) findViewById(R.id.NewSession);
        ShowAll = (Button) findViewById(R.id.ShowAll);
        Analsys = (Button) findViewById(R.id.analysis);
       // textViewResult = (TextView) findViewById(R.id.textViewResult);

        regOut.setOnClickListener(this);
        NewSeassion.setOnClickListener(this);
        ShowAll.setOnClickListener(this);
        Analsys.setOnClickListener(this);

        //textViewResult.setOnClickListener(this);


        Intent intent = getIntent();
      //Intent intentFromHistory = getIntent();

        textView.setText("welcome " + intent.getStringExtra("username"));
        //textView.setText("welcome" + intentFromHistory.getStringExtra("position"));
    }

    public void setLimit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Your Limit");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_NUMBER);
        if(input!=null) {
            builder.setView(input);
        }

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(input.getText().toString().startsWith("0")==false && input.getText().toString().equals("")==false) {
                    m_Text = input.getText().toString();
                    lim = Double.parseDouble(input.getText().toString());
                    Intent intent = getIntent();
                    String usr = intent.getStringExtra("username");

                    Intent i = new Intent(ActivityUserProfile.this, ScannerAct.class);
                    //  i.putExtra("limit", lim);
                    i.putExtra("username",usr);
                    startActivity(i);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void generate(){


        Random r = new Random();
        int i = r.nextInt(900000-100000) + 1000000;
        //SaveInvoiceNumber(i);
        invNumberr = String.valueOf(i);
        dateTime(i);



    }
    public void dateTime(int in){

        int inv = in;

        Calendar c = Calendar.getInstance();
        int x = c.get(Calendar.MONTH) + 1;
        String Month = String.valueOf(x);


        String sDate = c.get(Calendar.YEAR) + "-"
                + Month
                + "-" + c.get(Calendar.DAY_OF_MONTH)
                + " " + c.get(Calendar.HOUR_OF_DAY)
                + ":" + c.get(Calendar.MINUTE)
                + ":" + c.get(Calendar.SECOND);
        date_time = sDate;

        setLimit();
       // startScanningActivity();

       // SaveInvoiceNumber(inv,sDate);
    }
    public void startScanningActivity(){
        Intent inte = new Intent(ActivityUserProfile.this,ScannerAct.class);
     //   inte.putExtra(KEY_Invoice, invoice);
      // inte.putExtra("lim", lim);
        startActivity(inte);
    }

    public void SaveInvoiceNumber(int in,String da){

        invoice = Integer.toString(in);
        Intent intent =getIntent();
        username = intent.getStringExtra("username");
        date = da;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            start();
                        }else{
                           Toast.makeText(ActivityUserProfile.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityUserProfile.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_username,username);
                map.put(KEY_Invoice,invoice);
                map.put(KEY_Date,date);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }

    public void start(){

        Intent intent = new Intent(ActivityUserProfile.this,Seassion.class);
      //  intent.putExtra(KEY_username, username);
        intent.putExtra(KEY_Invoice, invoice);
        intent.putExtra(KEY_username, username);


        startActivity(intent);
    }


    public void  getNbRows(){

        Intent intent =getIntent();
        username = intent.getStringExtra("username");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, INVOICELENGTH_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.trim().equals("0")){
                            String val;
                           lengtho  = response.trim();
                          // display(lengthO);
                           goToInvices(lengtho);
                        }else{
                           // Toast.makeText(ActivityUserProfile.this,response,Toast.LENGTH_LONG).show();
                            android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(ActivityUserProfile.this).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("There is no invoices yet. ");
                            alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEUTRAL, "OK",
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
                        Toast.makeText(ActivityUserProfile.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();

                map.put(KEY_username,username);
               // map.put(KEY_Length,lengtho);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }
    public void  getNbRows2(){

        Intent intent =getIntent();
        username = intent.getStringExtra("username");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, INVOICELENGTH_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.trim().equals("0")){
                            String val;
                            lengtho  = response.trim();
                            analayzer_length = Integer.parseInt(lengtho);
                            goGeneral();

                        }else{
                          //  Toast.makeText(ActivityUserProfile.this,response,Toast.LENGTH_LONG).show();
                            android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(ActivityUserProfile.this).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("There is no invoices yet to be analyzed. ");
                            alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEUTRAL, "OK",
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
                        Toast.makeText(ActivityUserProfile.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();

                map.put(KEY_username,username);
                // map.put(KEY_Length,lengtho);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }

    public void display(String le){

        textViewResult.setText("Name:\t"+le);

    }
    public void goGeneral(){
        Intent i=new Intent(ActivityUserProfile.this,Analysis_Controller.class);
        i.putExtra(KEY_username, username);

        startActivity(i);
    }

    public void goToInvices(String lo){

      //  lengthO  =lo;

        Intent intent = new Intent(ActivityUserProfile.this,InvHistory.class);
       intent.putExtra(KEY_Length, lengtho);
        intent.putExtra(KEY_username, username);

        startActivity(intent);



    }

    public void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent k = new Intent(ActivityUserProfile.this,MainActivity.class);
                k.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(k);
                finish();             }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                // Stuff to do
            }
        });

        builder.setMessage("Are you sure you want logout? ");
        builder.setTitle("Warning..");

        AlertDialog d = builder.create();
        d.show();
    }


    @Override
    public void onClick(View v) {
        if(v == regOut){

            logout();

         /*   Intent k = new Intent(ActivityUserProfile.this,MainActivity.class);
            k.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(k);
            finish(); */
        }
        if(v== NewSeassion){

            generate();
        }
        if(v== ShowAll){

                getNbRows();

        }
        if(v==Analsys){
            getNbRows2();
        }

    }


}