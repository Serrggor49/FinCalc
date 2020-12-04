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
import android.widget.Toast;

public class Credit extends MyAppCompatActivity {

    static final String INPUT_ERROR = "Убедитесь в правильности заполнения полей";
    EditText summaKreditaEdit; // поле ввода суммы
    EditText procentnayaStavkaEdit;  // поле ввода продолжительности вклада
    EditText srokEdit;   // поле ввода периодичности выплаты процентов за год
    TextView resultEdit;
    TextView resultVsegoViplat;
    TextView resultPereplata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Кредитный калькулятор</font>"));
        setContentView(R.layout.activity_credit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // включает отображение стрелочки назад в тулбаре
        init();
        separateTextView(summaKreditaEdit);
        stringHelp = R.string.help_ipoteka;
    }

    public void init() {
        summaKreditaEdit = findViewById(R.id.stoimost_jilya_editText_id);
        procentnayaStavkaEdit = findViewById(R.id.stavka_procentnaya_editText_id);
        srokEdit = findViewById(R.id.srok_editText_id);
        resultEdit = findViewById(R.id.result_editText_id);
        resultVsegoViplat = findViewById(R.id.result_vsego_viplat_editText_id);
        resultPereplata = findViewById(R.id.result_pereplat_editText_id);
        summaKreditaEdit.setText(Methods.separate(summaKreditaEdit.getText().toString()));
    }

    public void calculate(View view) {

        try {
            double summaKredita = Double.parseDouble(summaKreditaEdit.getText().toString().replace(" ", "")); // убираем пробелы-разделители, дабы не выдало ошибку при преобразовании полученного значения в Double
            double procentnayaStavka = Double.parseDouble(procentnayaStavkaEdit.getText().toString()) / 100 / 12;
            double srok = Double.parseDouble(srokEdit.getText().toString()) * 12;

            Double x = (summaKredita) * ((procentnayaStavka * (Math.pow((1 + procentnayaStavka), srok)) / (Math.pow((1 + procentnayaStavka), srok) - 1)));  // ежемесячный платеж
            resultEdit.setText(" " + Methods.separate(String.format("%.2f", x)));
            resultVsegoViplat.setText(" " + Methods.separate(String.format("%.2f", x * srok)));
            resultPereplata.setText(" " + Methods.separate(String.format("%.2f", x * srok - (summaKredita))));

        } catch (NumberFormatException e) {
            Toast.makeText(this, INPUT_ERROR, Toast.LENGTH_LONG).show();
        }
    }
}
