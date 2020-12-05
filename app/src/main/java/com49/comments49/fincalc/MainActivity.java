package com49.comments49.fincalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openSlojniyProcent(View v) {
        Intent intent = new Intent(MainActivity.this, SlojniyProcent.class);
        startActivity(intent);
    }

    public void openIpoteka(View v) {
        Intent intent = new Intent(MainActivity.this, Ipoteka.class);
        startActivity(intent);
    }

    public void openCredit(View v) {
        Intent intent = new Intent(MainActivity.this, Credit.class);
        startActivity(intent);
    }

    public void openPercent(View v) {
        Intent intent = new Intent(MainActivity.this, Bank_percent.class);
        startActivity(intent);
    }

}
