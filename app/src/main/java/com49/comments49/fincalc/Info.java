package com49.comments49.fincalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class Info extends AppCompatActivity {

    private TextView mTextView;
    private static int text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // включает отображение стрелочки назад в тулбаре
        init();
    }

    void init() {
        mTextView = findViewById(R.id.result_editText_id);
        mTextView.setText(text);
    }

    public static void setText(int text) {
        Info.text = text;
    }
}
