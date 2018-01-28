package com.example.abdullah.purchy;

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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abdullah on 02/09/17.
 */

public class invInformation extends AppCompatActivity implements View.OnClickListener   {

    //private static final String INVOICELENGTH_URL = "http://192.168.92.1/Login/getAllInvoices/getInvoiceInformation/display/getNbRows.php";
    private static final String Saver_URL = "http://192.168.92.1/Login/getAllInvoices/getInvoiceInformation/display/Save_Array_in_Table.php";
    private static final String INVOICELENGTH_URL = "http://purchy.000webhostapp.com/getNbRows.php";


    private Button Show;
    private Button Go;
    public static final String KEY_Length = "lengtho";
    public static final String KEY_username = "username";
    private ProgressDialog loading;

    public static  String username;
    private String lengthO;
    // private TextView textView;
    private TextView TextViewResult;
    public static InvoiceArray[] invo ;
    public static ArrayList<Product> invo2;
    public static ArrayList<InvoiceArray> iner;

    public static String doubler;
    public static ArrayAdapter<String> arr;
    private TextView textView;
    private TextView textView2;
    public String size;
    public static String lengther;
    public static String id;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);

        Intent intent = getIntent();
        id = intent.getStringExtra("orderNum");


        invo2 = new ArrayList<Product>();
        TextView above1 = (TextView) findViewById(R.id.above_FirstName);
        TextView above2 = (TextView) findViewById(R.id.above_lastName);
        TextView above3 = (TextView) findViewById(R.id.above_N1);
        TextView above4 = (TextView) findViewById(R.id.above_N2);
        above1.setText("Item");
        above2.setText("Total");
        above3.setText("Price");
        above4.setText("Qunty");
        nbRows(id);
        // getData();


    }
    public void nbRows(String id){


        username = id;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, INVOICELENGTH_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.trim().equals("0")){
                            String val;
                            lengther  = response.trim();
                            getData(lengther);
                            // display(lengthO);
                            //   goToInvices(lengtho);
                        }else{
                            Toast.makeText(invInformation.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(invInformation.this,error.toString(),Toast.LENGTH_LONG ).show();
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



    private void getData(final String leng) {
        // Intent intent = getIntent();
        // String id = intent.getStringExtra("username");
       // final String  id=leng;

        if (id.equals("")) {
            Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
            return;
        }
        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);

        //  String url = config.DATA_URL3+id;
        String url = config.DATA_URL3+id;


        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response,leng);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(invInformation.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy( (new DefaultRetryPolicy(
                5000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
        requestQueue.add(stringRequest);


    }


    private void showJSON(String response,String leng){
        String order_id="";
        String reg_date="";
        String vc = "";
        int size = Integer.parseInt(leng);

        //invo = new InvoiceArray[3];
        try {
            for(int k=0;k<size;k++) {
                // for(int k=0;k<invo.length;k++) {

                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(config.JSON_ARRAY);
                JSONObject collegeData = result.getJSONObject(k);

                Product inv = new Product(collegeData.getString("barcodeNumber"),collegeData.getString("name"),collegeData.getString("item_qty"),
                        collegeData.getString("total"),collegeData.getString("price"),k);

                //  invo[k] = inv;
                invo2.add(inv);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        array();
        // save_in_DB(invo2);


    /*    String size = Integer.toString(invo2.size());
        Intent intent = new Intent(InvHistory.this, Displaer.class);
        intent.putExtra("list", invo2);
        intent.putExtra("name",username);
        startActivity(intent);*/

        //   TextViewResult.setText("Name:\t"+invo[0].order_id+"\nDate:\t" +invo[0].reg_date + " " + invo[1].reg_date + " " +invo[1].order_id

        //        + " " + invo[2].reg_date + " " + invo[2].order_id + " Size " + size);
        // TextViewResult.setText();

    }
    public void array(){

        //  AdapterInvo adapter = new AdapterInvo(this,
        //     R.layout.list_item, invo2);
        ThreeColumn_ListAdapter adapter =  new ThreeColumn_ListAdapter(this,R.layout.list_adapter_view, invo2);
        ListView   listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);


    }

    private void save_in_DB(final ArrayList<Product> inve){
        final String barcode = " ";
        final String name =  " ";
        final String qty = " ";
        final String total = " ";
        final String price = " ";



        StringRequest stringRequest = new StringRequest(Request.Method.POST, Saver_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(invInformation.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(invInformation.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                for(int i=0;i<invo2.size();i++) {
                    params.put("barcode", inve.get(i).product_barcode );
                    params.put("name", inve.get(i).product_name);
                    params.put("qty", inve.get(i).product_qty);
                    params.put("total", inve.get(i).total);
                    params.put("price", inve.get(i).product_price);
                }

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    @Override
    public void onClick(View v) {
     /*   if(v==Show){
            getData();
        }
        if(v==Go){
            getData();

        }
*/


    }






}
