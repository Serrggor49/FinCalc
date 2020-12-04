package com49.comments49.fincalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

// https://github.com/jjoe64/GraphView-Demos/blob/master/app/src/main/java/com/jjoe64/graphview_demos/examples/StylingColors.java описание либы по построению графиков

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

     public void openSlojniyProcent(View v){
        Intent intent = new Intent(MainActivity.this, SlojniyProcent.class);
        startActivity(intent);
    }

    public void openIpoteka(View v){
        Intent intent = new Intent(MainActivity.this, Ipoteka.class);
        startActivity(intent);
    }

    public void openCredit(View v){
        Intent intent = new Intent(MainActivity.this, Credit.class);
        startActivity(intent);
    }

    public void openPercent(View v){
        Intent intent = new Intent(MainActivity.this, Bank_percent.class);
        startActivity(intent);
    }

}


//        Toast.makeText(this, "" + graf_1.getHighestValueX(), Toast.LENGTH_LONG).show();
