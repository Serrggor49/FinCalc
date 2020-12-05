package com49.comments49.fincalc;

import android.os.Bundle;
import android.text.Html;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Ipoteka extends MyAppCompatActivity {

    final int HELP_TEXT = R.string.help_slojniy_procent;
    final String INPUT_ERROR = "Убедитесь в правильности заполнения полей";
    final String INPUT_ERROR_VZNOS = "Первоначальный взнос не может превышать стоимость жилья";
    private EditText mStoimostJilyaEdit; // поле ввода первоначального вклада
    private EditText mPervonachalniyVznosEdit; // поле ввода процентной ставки
    private EditText mProcentnayaStavkaEdit;  // поле ввода продолжительности вклада
    private EditText mSrokEdit;   // поле ввода периодичности выплаты процентов за год
    private TextView mResultEdit;
    private TextView mResultVsegoViplat;
    private TextView mResultPereplata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Ипотечный калькулятор</font>"));
        setContentView(R.layout.activity_ipoteka);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // включает отображение стрелочки назад в тулбаре
        init();
        separateTextView(mStoimostJilyaEdit);
        separateTextView(mPervonachalniyVznosEdit);
        mStringHelp = HELP_TEXT;
        calculate(null);
    }

    private void init() {
        mStoimostJilyaEdit = findViewById(R.id.stoimost_jilya_editText_id);
        mPervonachalniyVznosEdit = findViewById(R.id.vznos_pervonachalniy_editText_id);
        mProcentnayaStavkaEdit = findViewById(R.id.stavka_procentnaya_editText_id);
        mSrokEdit = findViewById(R.id.srok_editText_id);
        mResultEdit = findViewById(R.id.result_editText_id);
        mResultVsegoViplat = findViewById(R.id.result_vsego_viplat_editText_id);
        mResultPereplata = findViewById(R.id.result_pereplat_editText_id);
        mStoimostJilyaEdit.setText(separate(mStoimostJilyaEdit.getText().toString()));
        mPervonachalniyVznosEdit.setText(separate(mPervonachalniyVznosEdit.getText().toString()));
    }

    public void calculate(View view) {

        try {
            double stoimostJilya = Double.parseDouble(mStoimostJilyaEdit.getText().toString().replace(" ", "")); // убираем пробелы-разделители, дабы не выдало ошибку при преобразовании полученного значения в Double
            double pervonachalniyVznos = Double.parseDouble(mPervonachalniyVznosEdit.getText().toString().replace(" ", ""));
            double procentnayaStavka = Double.parseDouble(mProcentnayaStavkaEdit.getText().toString()) / 100 / 12;
            double srok = Double.parseDouble(mSrokEdit.getText().toString()) * 12;

            if (stoimostJilya < pervonachalniyVznos) {
                Toast.makeText(this, INPUT_ERROR_VZNOS, Toast.LENGTH_LONG).show();

            } else {
                Double x = (stoimostJilya - pervonachalniyVznos) * ((procentnayaStavka * (Math.pow((1 + procentnayaStavka), srok)) / (Math.pow((1 + procentnayaStavka), srok) - 1)));  // ежемесячный платеж
                mResultEdit.setText(" " + separate(String.format("%.2f", x)));
                mResultVsegoViplat.setText(" " + separate(String.format("%.2f", x * srok)));
                mResultPereplata.setText(" " +separate(String.format("%.2f", x * srok - (stoimostJilya - pervonachalniyVznos))));
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, INPUT_ERROR, Toast.LENGTH_LONG).show();
        }
    }

}
