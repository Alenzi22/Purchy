package com.example.abdullah.purchy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import static com.example.abdullah.purchy.Analysis_Controller.analy;
import static com.example.abdullah.purchy.Analysis_Controller.avg;
import static com.example.abdullah.purchy.Analysis_Controller.sumer;

/**
 * Created by Abdullah on 21/11/17.
 */


public class Analayzer_General extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private TextView textView2;
    private TextView t1;
    private TextView t2;
    private TextView t3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analayzer_1);
        textView = (TextView) findViewById(R.id.total_analayzer);
        textView2 = (TextView) findViewById(R.id.avg_analayzer);
        t1 = (TextView) findViewById(R.id.above_invoiceNb);
        t2 = (TextView) findViewById(R.id.above_date);
        t3 = (TextView) findViewById(R.id.above_total);
        t1.setText("Inoivce#");
        t2.setText("Date");
        t3.setText("Total");


        array();
    }

    public void array(){


            //  AdapterInvo adapter = new AdapterInvo(this,
            //     R.layout.list_item, invo2);
            Analayzer1 adapter =  new Analayzer1(this,R.layout.analayzer_1_adapter, analy);
            ListView listView = (ListView) findViewById(R.id.listView_Shopping_analysis);
            listView.setAdapter(adapter);

            textView.setText("Total of all: " +String.valueOf(sumer));
        textView2.setText("Avarage: " +String.valueOf(avg));



    }


    @Override
    public void onClick(View v) {

    }


}
