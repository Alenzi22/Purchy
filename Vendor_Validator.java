package com.example.abdullah.purchy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Vendor_Validator extends AppCompatActivity implements View.OnClickListener {

    private Button scanBtn_vendor;
    private Button validate_vendor;
    public static TextView Vendor_Info;


    public static String vendor_payment_information;
    public static String inv_vendor;
    public static String username_vendor ;
    public static String total_vendor;

    private static final String VALIDATE_URL = "http://purchy.000webhostapp.com/validate.php";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_valid);
        scanBtn_vendor = (Button)findViewById(R.id.scan_vendor);
        validate_vendor = (Button)findViewById(R.id.validate);
        Vendor_Info =(TextView) findViewById(R.id.information_payment_vendor);

        scanBtn_vendor.setOnClickListener(this);
        validate_vendor.setOnClickListener(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {


            vendor_payment_information = scanningResult.getContents();

            if(vendor_payment_information !=null)
                Calc();



        }
        else{

            finish();
        }
    }

    public void Calc(){

        String[] one = vendor_payment_information.split("\n");



        Pattern pattern = Pattern.compile("'(.*?)'");
        Matcher matcher = pattern.matcher(one[1]);
        while (matcher.find()) {
            username_vendor = matcher.group(1);
        }
        matcher = pattern.matcher(one[2]);
        while (matcher.find()) {
            inv_vendor = matcher.group(1);
        }
        matcher = pattern.matcher(one[0]);
        while (matcher.find()) {
            total_vendor = matcher.group(1);
        }

        view();






    }

    public void view(){
Vendor_Info.setText("username " + username_vendor + "\n " + " invoice number " + total_vendor + "\n" +" total " + inv_vendor);    }

    public void now_validate(){



        StringRequest stringRequest = new StringRequest(Request.Method.POST, VALIDATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Vendor_Validator.this,response,Toast.LENGTH_LONG).show();

                        finish_vald();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Vendor_Validator.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",username_vendor);
                params.put("order_id",total_vendor);

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

    public void  finish_vald(){



    }


    @Override
    public void onClick(View v) {
        if(v==scanBtn_vendor){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.setOrientationLocked(true);
            scanIntegrator.setBeepEnabled(true);
            scanIntegrator.setCaptureActivity(CaptureActivityPortrait.class);
            scanIntegrator.initiateScan();
        }
        if(v==validate_vendor){
            now_validate();

        }

    }
}