package com49.comments49.fincalc;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Credit extends MyAppCompatActivity {

    private static final int HELP_TEXT = R.string.help_credit;
    private static final String BAR_TITLE = "Кредитный калькулятор";
    private static final String INPUT_ERROR = "Убедитесь в правильности заполнения полей";

    private EditText mSummaKreditaEdit; // поле ввода суммы
    private EditText mProcentnayaStavkaEdit;  // поле ввода продолжительности вклада
    private EditText mSrokEdit;   // поле ввода периодичности выплаты процентов за год
    private TextView mResultEdit;
    private TextView mResultVsegoViplat;
    private TextView mResultPereplata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        init();
        mStringHelp = HELP_TEXT;
        calculate(null);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(BAR_TITLE);
        }
    }

    private void init() {
        mSummaKreditaEdit = findViewById(R.id.stoimost_jilya_editText_id);
        mProcentnayaStavkaEdit = findViewById(R.id.stavka_procentnaya_editText_id);
        mSrokEdit = findViewById(R.id.srok_editText_id);
        mResultEdit = findViewById(R.id.result_editText_id);
        mResultVsegoViplat = findViewById(R.id.result_vsego_viplat_editText_id);
        mResultPereplata = findViewById(R.id.result_pereplat_editText_id);
        mSummaKreditaEdit.setText(separate(mSummaKreditaEdit.getText().toString()));

        separateTextView(mSummaKreditaEdit, mResultEdit, mResultVsegoViplat, mResultPereplata );
        separateTextView(mProcentnayaStavkaEdit, mResultEdit, mResultVsegoViplat, mResultPereplata );
        separateTextView(mSrokEdit, mResultEdit, mResultVsegoViplat, mResultPereplata );

    }

    public void calculate(View view) {

        try {
            double summaKredita = Double.parseDouble(mSummaKreditaEdit.getText().toString().replace(" ", "")); // убираем пробелы-разделители, дабы не выдало ошибку при преобразовании полученного значения в Double
            double procentnayaStavka = Double.parseDouble(mProcentnayaStavkaEdit.getText().toString()) / 100 / 12;
            double srok = Double.parseDouble(mSrokEdit.getText().toString()) * 12;

            Double x = (summaKredita) * ((procentnayaStavka * (Math.pow((1 + procentnayaStavka), srok)) / (Math.pow((1 + procentnayaStavka), srok) - 1)));  // ежемесячный платеж
            mResultEdit.setText(" " + separate(String.format("%.2f", x)));
            mResultEdit.setTextColor(getColor(R.color.green_graf));
            mResultVsegoViplat.setText(" " + separate(String.format("%.2f", x * srok)));
            mResultVsegoViplat.setTextColor(getColor(R.color.green_graf));
            mResultPereplata.setText(" " + separate(String.format("%.2f", x * srok - (summaKredita))));
            mResultPereplata.setTextColor(getColor(R.color.green_graf));

        } catch (NumberFormatException e) {
            Toast.makeText(this, INPUT_ERROR, Toast.LENGTH_LONG).show();
        }
    }
}
