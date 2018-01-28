package com.example.abdullah.purchy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import static com.example.abdullah.purchy.ActivityUserProfile.invNumberr;
import static com.example.abdullah.purchy.Analsys_Deep.Display_date;
import static com.example.abdullah.purchy.Analsys_Deep.Display_invo;
import static com.example.abdullah.purchy.Analsys_Deep.arr_deep;
import static com.example.abdullah.purchy.Analysis_Controller.analy;
import static com.example.abdullah.purchy.Analysis_Controller.avg;
import static com.example.abdullah.purchy.Analysis_Controller.sumer;
import static com.example.abdullah.purchy.ScannerAct.usr;

/**
 * Created by Abdullah on 27/11/17.
 */

public class Deep_Result extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private TextView textView2;
    private TextView t1;
    private TextView t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_deep);
        t1 = (TextView) findViewById(R.id.Dis_Date);
        t2 = (TextView) findViewById(R.id.Dis_Inv);

        t1.setText("Invoice Number:"+Display_invo);
        t2.setText("Date: " + Display_date);



        array();
       // alert();
    }

    public void array(){


        //  AdapterInvo adapter = new AdapterInvo(this,
        //     R.layout.list_item, invo2);
        Deep_Adapter adapter =  new Deep_Adapter(this,R.layout.chose_date_for_invoice_analsyis, arr_deep);
        ListView listView = (ListView) findViewById(R.id.listView_deep);
        listView.setAdapter(adapter);




    }

    public void alert(){
            AlertDialog alertDialog = new AlertDialog.Builder(Deep_Result.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("check:" + arr_deep.get(0).category);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

    }


    @Override
    public void onClick(View v) {

    }


}
