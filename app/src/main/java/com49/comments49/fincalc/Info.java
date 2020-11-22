package com49.comments49.fincalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Info extends AppCompatActivity {

    TextView textView;
    static int text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        init();
    }

     void init(){
        textView = findViewById(R.id.result_editText_id);
        textView.setText(text);
    }


    public static void setText(int text) {
        Info.text = text;
    }
}
