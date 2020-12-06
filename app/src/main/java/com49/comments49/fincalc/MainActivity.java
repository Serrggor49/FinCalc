package com49.comments49.fincalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButton();
    }

    private void initButton() {
        ImageButton buttonSlojniyProcent = findViewById(R.id.slojniy_procent_id);
        buttonSlojniyProcent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SlojniyProcent.class);
                startActivity(intent);
            }
        });

        ImageButton buttonIpotechniyCalc = findViewById(R.id.ipotechniy_calc_id);
        buttonIpotechniyCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Ipoteka.class);
                startActivity(intent);
            }
        });

        ImageButton kreditniyCalc = findViewById(R.id.kreditniy_calc_id);
        kreditniyCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Credit.class);
                startActivity(intent);
            }
        });

        ImageButton vkladovCalc = findViewById(R.id.vkladov_calc_id);
        vkladovCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BankPercent.class);
                startActivity(intent);
            }
        });
    }

}
