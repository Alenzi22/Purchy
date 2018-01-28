package com.example.abdullah.purchy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.abdullah.purchy.ActivityUserProfile.invNumberr;
import static com.example.abdullah.purchy.ActivityUserProfile.lim;
import static com.example.abdullah.purchy.ScannerAct.usr;

/**
 * Created by Abdullah on 11/11/17.
 */

public class Finally_Result extends AppCompatActivity implements View.OnClickListener {
    public static TextView Invoide_IDd;
    private Button finishing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finally_result);

        finishing = (Button)findViewById(R.id.finisher);


        Invoide_IDd = (TextView) findViewById(R.id.id_final);


        finishing.setOnClickListener(this);
       // Invoide_IDd.setOnClickListener(this);
        Invoide_IDd.setText("You Are finising your Shopping, \n Your invoice Number is:" + invNumberr);


    }

    @Override
    public void onClick(View v) {
        Intent k = new Intent(Finally_Result.this,ActivityUserProfile.class);
        k.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

    k.putExtra("username",usr);

        startActivity(k);
        finish();
    };






}
