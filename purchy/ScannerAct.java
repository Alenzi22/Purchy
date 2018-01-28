package com.example.abdullah.purchy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.fasterxml.jackson.core.type.TypeReference;

/*
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
*/

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.abdullah.purchy.ActivityUserProfile.invNumberr;
import static com.example.abdullah.purchy.ActivityUserProfile.lim;
import static com.example.abdullah.purchy.MainActivity.invo2;
//import static com.example.abdullah.purchy.MainActivity.lim;


/**
 * Created by Abdullah on 06/09/17.
 */

public class ScannerAct extends AppCompatActivity implements View.OnClickListener {

   // public static ArrayList<Product> invo2;

  /*  private Button scanBtn;
    private Button Purches; */


    private ImageButton scanBtn;
    private ImageButton Purches;
    private TextView t1;
    private TextView t2;
    private TextView t3;


    private Button clear_all;


    private TextView formatTxt, contentTxt;
    public static String barcode;
    public static ArrayList<Product> arr = new ArrayList<Product>();
    private ProgressDialog loading;
    public static TextView Totaler;
    public static TextView Limit;
    public static double totale;
    public static String steps;
    public static String qt;
    public static String tote;
    public static String test_Json;
    public static String m_Text = "";
    public static Double limitt;
    public static Double limit_tracker;
   public static String arrayToJson;
    public static String information_forPayment;
    private static final String Exixt_URL = "http://purchy.000webhostapp.com/exist.php";
    public static String usr;










    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.scanner);

      /*  scanBtn = (Button)findViewById(R.id.scan_button_shopping);
        Purches = (Button)findViewById(R.id.Purches);*/

        scanBtn = (ImageButton) findViewById(R.id.scan_button_shopping);
        Purches = (ImageButton)findViewById(R.id.Purches);

        t1 = (TextView) findViewById(R.id.above_shop_0);
        t2 = (TextView)findViewById(R.id.above_shop_1);
        t3 = (TextView) findViewById(R.id.above_shop_2);

        t1.setText("Item");
        t2.setText("Price");
        t3.setText("Qty");



        clear_all = (Button)findViewById(R.id.clear_all);



        Totaler = (TextView) findViewById(R.id.TexViewTotaler);
        Limit = (TextView) findViewById(R.id.Limit);
        Intent intent = getIntent();
        usr = intent.getStringExtra("username");

       // Intent intent = getIntent();
       // String id = intent.getStringExtra("limit");
        limit_tracker = lim ;
        limitt =lim ;
        if(invo2.size()>=1){
            invo2.clear();
        }

        scanBtn.setOnClickListener(this);
        Purches.setOnClickListener(this);
        clear_all.setOnClickListener(this);


    }
    private void toMySQL() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonConfig = gson.toJson(invo2);
    System.out.println(jsonConfig);
    }



    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {


         //  String scanContent = scanningResult.getContents().toString();
            barcode = scanningResult.getContents();

            if(barcode !=null)
Exist();
              //  checker(barcode);

           // finish();

         //   Intent i = new Intent(this,ScannerAct.class);
          //  startActivity(i);


        }
        else{
           /* Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();*/
           finish();
        }
    }

    public void test(){
       Intent r = new Intent(ScannerAct.this,Handler_Barcode.class);
        r.putExtra("barcode",barcode);
         startActivity(r);


    }
    private void getData() {
     //   Intent intent = getIntent();
       // String k = intent.getStringExtra("barcode");
       // barcode="9786140101500";

       // final String  id=barcode;

        try {
            if (barcode.equals("")) {
                Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
                return;
            }
            loading = ProgressDialog.show(this, "Please wait...", "Fetching Product..", false, false);

            //  String url = config.DATA_URL3+id;
            String url = config.DATA_URL4+barcode;


            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loading.dismiss();
                    showJSON(response, barcode);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ScannerAct.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
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

       // int size = Integer.parseInt(leng);

        try {
            for(int k=0;k<1;k++) {

                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(config.JSON_ARRAY);
                JSONObject collegeData = result.getJSONObject(k);

                Product inv = new Product(collegeData.getString("barcodeNumber"),collegeData.getString("name"),collegeData.getString("catName"),
                        collegeData.getString("price"),"1","1",k,collegeData.getString("price"),"1219004");


               if(Double.parseDouble(inv.product_price) <= limit_tracker){

                       invo2.add(inv);
                      double new_limit = limit_tracker - Double.parseDouble(inv.product_price);
                        limit_tracker = new_limit;
                  // array();

               } else{
                        AlertDialog alertDialog = new AlertDialog.Builder(ScannerAct.this).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage("Your limit is not enoguth to buy this product");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                    }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
      //  checker(invo2.size()-1);
       array();



    }
    public void checker(String b){

        if(invo2.size() ==0){
            getData();
        } else if(invo2.size()!=0){
        boolean flag=true;

        String barcode_to_be_check = b;

        for(int i=0;i<invo2.size();i++){
            if(invo2.get(i).product_barcode.equals(barcode)){
                flag=false;

               // return;
               /* AlertDialog alertDialog = new AlertDialog.Builder(ScannerAct.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("You are already added this product to list!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return; */

            }
        }

        if(flag==true){
        getData();}
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(ScannerAct.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("You are already added this product to list!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        }}

    }

    public void Exist(){


      //  username = id;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Exixt_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.trim().equals("0")){
                            checker(barcode);

                            // display(lengthO);
                            //   goToInvices(lengtho);
                        }else{
                            AlertDialog alertDialog = new AlertDialog.Builder(ScannerAct.this).create();
                            alertDialog.setTitle("Alert");
                            alertDialog.setMessage("ERROR: there is an error!");
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
                        Toast.makeText(ScannerAct.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();

                map.put("barcode",barcode);
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






    public void array() {

        t1.setText("Item");
        t2.setText("Price");
        t3.setText("Qty");

double update_total=0.0;
        if(invo2.size() !=0) {
           // totale += Double.parseDouble(invo2.get(invo2.size()-1).actualPrice);
           // String tote = String.valueOf(totale);
            for (int i = 0; i < invo2.size(); i++) {
                update_total += Double.parseDouble(invo2.get(i).product_price);
            }
            tote = String.valueOf(update_total);

            // to();
            Totaler.setText("Total:" + tote  );
            Limit.setText("Your Limit is:" + limit_tracker  );

            // Totaler.setText("Total:" + test_Json  );


            Adapter_Shopping adapter = new Adapter_Shopping(this, R.layout.adapter_shop, invo2);
            ListView listView = (ListView) findViewById(R.id.listView_Shopping);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                   // array();

                }
            });
        }
        if(invo2.size() !=0){
       // showTotal();
        }

    }
    public void  showTotal(){

        for(int i=0;i<invo2.size();i++) {
            steps = invo2.get(i).product_price;
            totale += Double.parseDouble(steps);
        }
        String tote = String.valueOf(totale);
        Totaler.setText("Total:" + tote  );




    }

/*
    public void store_invo2_in_my_sql(){



        StringRequest stringRequest = new StringRequest(Request.Method.POST, Store_Invo_MySQL_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ScannerAct.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ScannerAct.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("invo2", arrayToJson);

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
*/
    public void toJsonTo(){
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
        arrayToJson=y;
       // store_invo2_in_my_sql();

    }


    public void informationCalculator(){
        double tott=0.0;
        Intent intent = getIntent();
         usr = intent.getStringExtra("username");

      /*  information_forPayment = "invoice Number: " + invNumberr;
        information_forPayment += " \n " + " Username: " + usr;
        for(int z=0;z<invo2.size();z++){
            tott+= Double.parseDouble(invo2.get(z).total);

        }
        information_forPayment += "\n" +"Total:" + totale; */

        for(int i=0;i<invo2.size();i++){
            invo2.get(i).invNmb=invNumberr;
        }
        for(int j=0;j<invo2.size();j++){
            invo2.get(j).total = invo2.get(j).product_price;
                    //String.valueOf(Double.parseDouble(invo2.get(j).actualPrice) * Double.parseDouble(
                    //invo2.get(j).product_qty));
          //  invo2.get(j).product_price=invo2.get(j).actualPrice;
            invo2.get(j).product_price=invo2.get(j).actualPrice;

        }

        information_forPayment = "invoice Number :" + "'" +invNumberr + "'";
        information_forPayment += " \n" + "Username :" + "'" + usr + "'";
        totale=0.0;
        for(int z=0;z<invo2.size();z++){
            totale+= Double.parseDouble(invo2.get(z).total);

        }
        information_forPayment += "\n" +"Total :" + "'" + totale+ "'";


        Intent i = new Intent(ScannerAct.this,Payment_Method.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(i);
        finish();

    }

    public void purch(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                informationCalculator();            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                // Stuff to do
            }
        });

        builder.setMessage("Are you sure you want Finish Your shopping? ");
        builder.setTitle("Warning..");

        android.app.AlertDialog d = builder.create();
        d.show();
    }
    @Override
    public void onClick(View v) {

        if(v==scanBtn){
          //  setLimit();
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.setOrientationLocked(true);
                scanIntegrator.setBeepEnabled(true);
                scanIntegrator.setCaptureActivity(CaptureActivityPortrait.class);
                scanIntegrator.initiateScan();


        }
       if(v==Purches){
           if(invo2.size()!=0) {
               purch();
              // informationCalculator();
           } else{
               AlertDialog alertDialog = new AlertDialog.Builder(ScannerAct.this).create();
               alertDialog.setTitle("Alert");
               alertDialog.setMessage("The List is empty!");
               alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                       new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                           }
                       });
               alertDialog.show();

           }
        /*   Intent i = new Intent(ScannerAct.this,Payment_Method.class);
           startActivity(i); */
          //  toJsonTo();
        }
        if(v==clear_all){
            Intent k = new Intent(ScannerAct.this,ActivityUserProfile.class);
            k.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            k.putExtra("username",usr);

            startActivity(k);
            finish();

        }

    }
}


