package com49.comments49.fincalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Credit extends AppCompatActivity {

    EditText summaKreditaEditText; // поле ввода суммы
    EditText procentnayaStavkaEditText;  // поле ввода продолжительности вклада
    EditText srokEditText;   // поле ввода периодичности выплаты процентов за год
    TextView resultEditText;
    TextView resultVsegoViplat;
    TextView resultPereplata;

    double summaKredita;
    double procentnayaStavka;
    double srok;

    int stringHelp = R.string.help_ipoteka;

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
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Кредитный калькулятор</font>"));
        setContentView(R.layout.activity_credit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // включает отображение стрелочки назад в тулбаре
        init();
        separateTextView();
    }

    public void separateTextView(){
        summaKreditaEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                summaKreditaEditText.setText(Methods.separate(summaKreditaEditText.getText().toString()));
            }
        });
    }

    public void init() {
        summaKreditaEditText = findViewById(R.id.stoimost_jilya_editText_id);
        procentnayaStavkaEditText = findViewById(R.id.stavka_procentnaya_editText_id);
        srokEditText = findViewById(R.id.srok_editText_id);
        resultEditText = findViewById(R.id.result_editText_id);
        resultVsegoViplat = findViewById(R.id.result_vsego_viplat_editText_id);
        resultPereplata = findViewById(R.id.result_pereplat_editText_id);
        summaKreditaEditText.setText(Methods.separate(summaKreditaEditText.getText().toString()));
    }
    

    public void getData(View view) { //получаем значение полей и вычисляем результат

        String stoimost = summaKreditaEditText.getText().toString().replace(" ", ""); // убираем пробелы-разделители, дабы не выдало ошибку при преобразовании полученного значения в Double

        summaKredita = Double.parseDouble(stoimost);
        procentnayaStavka = Double.parseDouble(procentnayaStavkaEditText.getText().toString()) / 100 / 12;
        srok = Double.parseDouble(srokEditText.getText().toString()) * 12;
        calculate();

        summaKreditaEditText.setText(Methods.separate(summaKreditaEditText.getText().toString()));

    }


    public void calculate() {
        Double x = (summaKredita) * ((procentnayaStavka * (Math.pow((1 + procentnayaStavka), srok)) / (Math.pow((1 + procentnayaStavka), srok) - 1)));  // ежемесячный платеж
        resultEditText.setText(" " + Methods.separate(String.format("%.2f", x)));
        resultVsegoViplat.setText(" " + Methods.separate(String.format("%.2f", x * srok)));
        resultPereplata.setText(" " + Methods.separate(String.format("%.2f", x * srok - (summaKredita))));
    }







}