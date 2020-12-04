package com49.comments49.fincalc;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Ipoteka extends MyAppCompatActivity {

    static final String INPUT_ERROR = "Убедитесь в правильности заполнения полей";
    static final String INPUT_ERROR_VZNOS = "Первоначальный взнос не может превышать стоимость жилья";
    private EditText stoimostJilyaEdit; // поле ввода первоначального вклада
    private EditText pervonachalniyVznosEdit; // поле ввода процентной ставки
    private EditText procentnayaStavkaEdit;  // поле ввода продолжительности вклада
    private EditText srokEdit;   // поле ввода периодичности выплаты процентов за год
    private TextView resultEdit;
    private TextView resultVsegoViplat;
    private TextView resultPereplata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Ипотечный калькулятор</font>"));
        setContentView(R.layout.activity_ipoteka);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // включает отображение стрелочки назад в тулбаре
        init();
        separateTextView(stoimostJilyaEdit);
        separateTextView(pervonachalniyVznosEdit);
        stringHelp = R.string.help_ipoteka;
        calculate(null);
    }

    public void init() {
        stoimostJilyaEdit = findViewById(R.id.stoimost_jilya_editText_id);
        pervonachalniyVznosEdit = findViewById(R.id.vznos_pervonachalniy_editText_id);
        procentnayaStavkaEdit = findViewById(R.id.stavka_procentnaya_editText_id);
        srokEdit = findViewById(R.id.srok_editText_id);
        resultEdit = findViewById(R.id.result_editText_id);
        resultVsegoViplat = findViewById(R.id.result_vsego_viplat_editText_id);
        resultPereplata = findViewById(R.id.result_pereplat_editText_id);
        stoimostJilyaEdit.setText(Methods.separate(stoimostJilyaEdit.getText().toString()));
        pervonachalniyVznosEdit.setText(Methods.separate(pervonachalniyVznosEdit.getText().toString()));
    }

    public void calculate(View view) {

        try {
            double stoimostJilya = Double.parseDouble(stoimostJilyaEdit.getText().toString().replace(" ", "")); // убираем пробелы-разделители, дабы не выдало ошибку при преобразовании полученного значения в Double
            double pervonachalniyVznos = Double.parseDouble(pervonachalniyVznosEdit.getText().toString().replace(" ", ""));
            double procentnayaStavka = Double.parseDouble(procentnayaStavkaEdit.getText().toString()) / 100 / 12;
            double srok = Double.parseDouble(srokEdit.getText().toString()) * 12;

            if (stoimostJilya < pervonachalniyVznos) {
                Toast.makeText(this, INPUT_ERROR_VZNOS, Toast.LENGTH_LONG).show();

            } else {
                Double x = (stoimostJilya - pervonachalniyVznos) * ((procentnayaStavka * (Math.pow((1 + procentnayaStavka), srok)) / (Math.pow((1 + procentnayaStavka), srok) - 1)));  // ежемесячный платеж
                resultEdit.setText(" " + Methods.separate(String.format("%.2f", x)));
                resultVsegoViplat.setText(" " + Methods.separate(String.format("%.2f", x * srok)));
                resultPereplata.setText(" " + Methods.separate(String.format("%.2f", x * srok - (stoimostJilya - pervonachalniyVznos))));
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, INPUT_ERROR, Toast.LENGTH_LONG).show();
        }
    }

}
