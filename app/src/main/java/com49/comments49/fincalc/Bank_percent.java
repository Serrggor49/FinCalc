package com49.comments49.fincalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.series.DataPoint;

public class Bank_percent extends AppCompatActivity {


    double A_2; // результат инвестирования под простой процент
    double P; // первоначальный взнос
    double r; // годовая процентная ставка
    double t; // срок, лет

    EditText vznosPervonachalniy; // поле ввода первоначального вклада
    EditText stavkaProcentnaya; // поле ввода процентной ставки
    //EditText srok;  // поле ввода продолжительности вклада
    //EditText kolichestvo_nachisleniy;   // поле ввода периодичности выплаты процентов за год
    //TextView profit; // заработанная сумма (сумма в конце вклада минус первоначальный вклад)
    TextView result; // сумма в конце вклада


    boolean canSeparate = false;  // типа флага, который говорит о том, что в данный момент нельзя осуществлять сепарацию числа

    int stringHelp = R.string.help_slojniy_procent;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.que_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.que:
                Info.setText(stringHelp);
                Intent intent = new Intent(this, Info.class);
                startActivity(intent);
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Банковский процент</font>"));
        setContentView(R.layout.activity_bank_percent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // включает отображение стрелочки назад в тулбаре
        init();
        separateTextView();
    }





    public void separateTextView(){

        vznosPervonachalniy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int before, int count) {

                if (!canSeparate)  //
                {
                    int a = vznosPervonachalniy.getSelectionEnd();
                    String textAfter = vznosPervonachalniy.getText().toString();
                    canSeparate = true;

                    vznosPervonachalniy.setText(Methods.separate(vznosPervonachalniy.getText().toString()));
                    String textBefore = vznosPervonachalniy.getText().toString();

                    vznosPervonachalniy.setSelection(a - (textAfter.length()-textBefore.length()));
                }
                else {
                    canSeparate = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    public void init(){
        vznosPervonachalniy = findViewById(R.id.vznos_pervonachalniy_editText_id);
        stavkaProcentnaya = findViewById(R.id.stavka_procentnaya_editText_id);
        result = findViewById(R.id.result_editText_id);
        vznosPervonachalniy.setText(Methods.separate(vznosPervonachalniy.getText().toString()));
    }


    void calculate(){  // в данном методе вычисляем значение сложного процента

        A_2 = P*r;
        String result_v = String.format("%.2f",  A_2);
        result.setText("" + Methods.separate(result_v));

    }



    public void getData(View view) { //получаем значение полей и вычисляем результат

        String vznos = vznosPervonachalniy.getText().toString().replace(" ", ""); // убираем пробелы-разделители, дабы не выдало ошибку при преобразовании полученного значения в Double
        P = Double.parseDouble(vznos);
        r = Double.parseDouble(stavkaProcentnaya.getText().toString()) / 100;

        calculate();

        StringBuilder s = new StringBuilder(vznosPervonachalniy.getText().toString().replace(" ", "")); // добавляем пробелы в значение первоначального взноса для улучшения читабельности
        for(int i = s.length(); i > 0; i = i-3){
            s.insert(i, " ");
        }

       // vznosPervonachalniy.setText(Methods.separate(vznosPervonachalniy.getText().toString()));

    }




}
