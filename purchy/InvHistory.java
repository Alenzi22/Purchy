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

/**
 * Created by Abdullah on 02/09/17.
 */

public class InvHistory extends AppCompatActivity implements View.OnClickListener   {

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
    public static ArrayList<InvoiceArray> invo2;
    public static String doubler;
    public static ArrayAdapter<String> arr;
    private TextView textView;
    private TextView textView2;
    public String size;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);
        textView = (TextView) findViewById(R.id.display_name);
        textView2 = (TextView) findViewById(R.id.display_number);



        Intent intent = getIntent();
        String id = intent.getStringExtra("username");
        username = id;

        textView.setText("welcome " + id);
        Intent i = getIntent();

        int newer = Integer.parseInt(i.getStringExtra("lengtho"));
        size = String.valueOf(newer);


        textView2.setText("You Have " + size + " Invoices");

        invo2 = new ArrayList<InvoiceArray>();
        getData();


       /* AdapterInvo adapter = new AdapterInvo(this,
                R.layout.list_item, invo2);


        ListView listView = (ListView) findViewById(R.id.sampleListView);
        listView.setAdapter(adapter);*/



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
        String url = config.DATA_URL2+id;


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
                        Toast.makeText(InvHistory.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response){
        String order_id="";
        String reg_date="";
        String vc = "";
        Intent i = getIntent();
        int newer = Integer.parseInt(i.getStringExtra("lengtho"));
        int maker = Integer.parseInt(i.getStringExtra("lengtho"));
        int solver =0;
        invo = new InvoiceArray[newer];
        try {
            // for(int k=0;k<3;k++) {
            for(int k=0;k<invo.length;k++) {

                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(config.JSON_ARRAY);
                JSONObject collegeData = result.getJSONObject(k);

                InvoiceArray inv = new InvoiceArray(collegeData.getString("order_id"),collegeData.getString("reg_date"),k);

                //  invo[k] = inv;
                invo2.add(inv);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        array();

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

        AdapterInvo adapter = new AdapterInvo(this,
                R.layout.list_item, invo2);


        ListView listView = (ListView) findViewById(R.id.sampleListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String posi = String.valueOf(position);

                String orderr = invo2.get(Integer.parseInt(posi)).order_id;

                Intent i = new Intent(InvHistory.this, invInformation.class);

                // i.putExtra("position",posi);
                i.putExtra("orderNum",orderr);

                //  i.putExtra("username",username);


                startActivity(i);

            }
        });


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
