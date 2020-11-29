package com49.comments49.fincalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;



// https://github.com/jjoe64/GraphView-Demos/blob/master/app/src/main/java/com/jjoe64/graphview_demos/examples/StylingColors.java описание либы по построению графиков

public class MainActivity extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.que_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        Intent intent = new Intent(this, Info.class);
        startActivity(intent);
//        switch (item.getItemId()) {
//            case R.id.que:
//                addSomething();
//                return true;
//
//        }
        return false;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


     public void startSlojniyProcent(View v){
        Intent intent = new Intent(MainActivity.this, Slojniy_procent.class);
        startActivity(intent);
    }

    public void startIpoteka(View v){
        Intent intent = new Intent(MainActivity.this, Ipoteka.class);
        startActivity(intent);
    }

    public void startCredit(View v){
        Intent intent = new Intent(MainActivity.this, Credit.class);
        startActivity(intent);
    }


    public void bankPercent(View v){
        Intent intent = new Intent(MainActivity.this, Bank_percent.class);
        startActivity(intent);
    }






}


//        Toast.makeText(this, "" + graf_1.getHighestValueX(), Toast.LENGTH_LONG).show();
