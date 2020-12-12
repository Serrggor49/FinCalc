package com49.comments49.fincalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

public class Info extends AppCompatActivity {


    private static final String BAR_TITLE = "Справка";
    private static int text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        init();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(BAR_TITLE);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
        super.onBackPressed();
        }
        return false;
    }

    private void init() {
        TextView mTextView = findViewById(R.id.result_editText_id);
        mTextView.setText(text);
    }

    public static void setText(int text) {
        Info.text = text;
    }




}
