package com.example.abdullah.purchy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.HashMap;
import java.util.Map;

import static android.R.attr.width;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;
import static com.example.abdullah.purchy.ActivityUserProfile.date_time;
import static com.example.abdullah.purchy.ActivityUserProfile.invNumberr;
import static com.example.abdullah.purchy.MainActivity.invo2;
import static com.example.abdullah.purchy.ScannerAct.information_forPayment;
import static com.example.abdullah.purchy.ScannerAct.usr;
import static java.awt.font.TextAttribute.WIDTH;

/**
 * Created by Abdullah on 09/11/17.
 */

public class Cash extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private ImageView imageView;
    public static String jsonson;
    public static String us_name;
    public static String inv_nmb;
    public static String d_ate;
    public static final String CHECK_URL = "http://purchy.000webhostapp.com/check_if_TRUE.php";
    private static final String Store_Invo_MySQL_URL = "http://purchy.000webhostapp.com/Ar.php";




    //private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr1);

        final Context context = this;
        //editText = (EditText) this.findViewById(R.id.editText);
        button = (Button) this.findViewById(R.id.button);
        button.setOnClickListener(this);

        imageView = (ImageView)
                this.findViewById(R.id.imageView);
      //  button.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
              //  String text2Qr = editText.getText().toString();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(information_forPayment, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);


                    imageView.setImageBitmap(bitmap);


                   /* Intent intent = new Intent(context, QrActivity.class);
                    intent.putExtra("pic",bitmap);
                    context.startActivity(intent);*/


                } catch (WriterException e) {
                    e.printStackTrace();
                }
       //     }
     //   });
    }
    public void check_payment_state(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, CHECK_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            goJson();
                        }else{
                            Toast.makeText(Cash.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Cash.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("username",usr);
                map.put("id",invNumberr);
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
    public void goJson(){
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
        jsonson=y;
        store_invo2_in_my_sql();
       // check_payment_state();
    }

    public void store_invo2_in_my_sql(){



        StringRequest stringRequest = new StringRequest(Request.Method.POST, Store_Invo_MySQL_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(Cash.this,response,Toast.LENGTH_LONG).show();
                        finallye();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Cash.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("invo2", jsonson);

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
    public void finallye(){
       Intent i = new Intent(Cash.this,Finally_Result.class);
        startActivity(i);
      /*
        AlertDialog alertDialog = new AlertDialog.Builder(Cash.this).create();
        alertDialog.setTitle("Mbroook");
        alertDialog.setMessage("You Already add this product to your list, if you want increase qty please use specified button");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
*/

    }



    @Override
    public void onClick(View v) {
        if (v==button){

            check_payment_state();
           // goJson();

        }
    }
}
