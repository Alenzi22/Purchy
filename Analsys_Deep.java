package com.example.abdullah.purchy;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.abdullah.purchy.ActivityUserProfile.date_time;
import static com.example.abdullah.purchy.ActivityUserProfile.invNumberr;
import static com.example.abdullah.purchy.Analysis_Controller.analy_deep;
import static com.example.abdullah.purchy.Analysis_Controller.array_to;
import static com.example.abdullah.purchy.MainActivity.invo2;
import static com.example.abdullah.purchy.ScannerAct.totale;
import static com.example.abdullah.purchy.ScannerAct.usr;

/**
 * Created by Abdullah on 26/11/17.
 */

public class Analsys_Deep extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {
    public static String Selected;
    private Button Next;
    public static String confirm="FALSE";
    private static final String STR_URL = "http://purchy.000webhostapp.com/Invoice.php";
    public static String  Year_Month_Day=null;
    public static String target_invo=null;
    private ProgressDialog loading;
    public static ArrayList<Deep_Array> arr_deep;
    private static final String DEEP_NB = "http://purchy.000webhostapp.com/deep_nbRows.php";
    public static String length_deep;
    public static String Display_invo;
    public static String Display_date;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deep_chose);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_analsys);
        Next = (Button) findViewById(R.id.Show_Deep);
        Next.setOnClickListener(this);


        spinner.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<String>();
        arr_deep = new ArrayList<Deep_Array>();







        for(int i=0;i<analy_deep.size();i++){
            categories.add(analy_deep.get(i).inv_date);
        }




        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Selected = parent.getItemAtPosition(position).toString();

    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }



    public void getCategoryAndTotal(){

        for(int i=0;i<analy_deep.size();i++){
            if(analy_deep.get(i).inv_date.equals(Selected)){
                target_invo=analy_deep.get(i).inv_nb;
                break;
            }

        }
        Display_invo=target_invo;
        Display_date=Selected;
      //  target_invo="1023976";

        find();



    }
    public void find(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DEEP_NB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.trim().equals("0")){
                            String val;
                            length_deep  = response.trim();
                            collect_cate();
                            // display(lengthO);
                            //   goToInvices(lengtho);
                        }else{
                            Toast.makeText(Analsys_Deep.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Analsys_Deep.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();

                map.put("username",target_invo);
                // map.put(KEY_Length,lengtho);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);







    }
    public void collect_cate(){

           // target_invo="1023976";

        try {
            if (target_invo.equals("")) {
                Toast.makeText(this, "Please enter an id", Toast.LENGTH_LONG).show();
                return;
            }
            loading = ProgressDialog.show(this, "Please wait...", "Analysing ...", false, false);

            //  String url = config.DATA_URL3+id;
            String url = config.DATA_URL7+target_invo;


            StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loading.dismiss();
                    showJSON(response, target_invo);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Analsys_Deep.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
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
        int size = Integer.parseInt(length_deep);

        try {
            for(int k=0;k<size;k++) {

                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(config.JSON_ARRAY);
                JSONObject collegeData = result.getJSONObject(k);

                Deep_Array inv = new Deep_Array(collegeData.getString("catName"),collegeData.getString("total"),k);
                arr_deep.add(inv);






            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //  checker(invo2.size()-1);
       classify();



    }

    public void classify(){
        double totll1;
        double totll2;
        double tot_final;

        for(int i=0;i<arr_deep.size();i++){
            for(int j=i+1;j<arr_deep.size();j++){
                if(arr_deep.get(i).category.equals(arr_deep.get(j).category)){
                    totll1 = Double.parseDouble(arr_deep.get(i).total);
                    totll2 = Double.parseDouble(arr_deep.get(j).total);
                    tot_final = totll1+totll2;

                    arr_deep.get(i).total = String.valueOf(tot_final) ;
                    arr_deep.remove(j);
                    j--;
                }
            }
        }

        if(arr_deep.size()==0){
            AlertDialog alertDialog = new AlertDialog.Builder(Analsys_Deep.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("ERROR!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        }
else if(arr_deep.size()!=0){
           Iam_Done_Analysing();}





    }

    public void  Iam_Done_Analysing(){

      /*  Deep_Adapter adapter = new Deep_Adapter(this, R.layout.deep_chose, arr_deep);
        ListView listView = (ListView) findViewById(R.id.sampleListView);
        listView.setAdapter(adapter);*/

      Intent i = new Intent(Analsys_Deep.this,Deep_Result.class);
        startActivity(i);



    }

    @Override
    public void onClick(View v) {
        if(v==Next){
            arr_deep.clear();

            getCategoryAndTotal();


        }

    }
}


