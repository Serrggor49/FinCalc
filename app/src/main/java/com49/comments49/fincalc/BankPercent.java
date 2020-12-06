package com49.comments49.fincalc;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BankPercent extends MyAppCompatActivity {

    final int HELP_TEXT = R.string.help_bank_percent;
    final String INPUT_ERROR = "Убедитесь в правильности заполнения полей";
    private EditText mVznosPervonachalniy; // поле ввода первоначального вклада
    private EditText mStavkaProcentnaya; // поле ввода процентной ставки
    private TextView mResult; // сумма в конце вклада

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Калькулятор вкладов</font>"));
        setContentView(R.layout.activity_bank_percent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // включает отображение стрелочки назад в тулбаре
        init();
        separateTextView(mVznosPervonachalniy);
        mStringHelp = HELP_TEXT;
        calculate(null);

    }

    private void init() {
        mVznosPervonachalniy = findViewById(R.id.vznos_pervonachalniy_editText_id);
        mStavkaProcentnaya = findViewById(R.id.stavka_procentnaya_editText_id);
        mResult = findViewById(R.id.result_editText_id);
        mVznosPervonachalniy.setText(separate(mVznosPervonachalniy.getText().toString()));
    }

    public void calculate(View view) {  // в данном методе вычисляем значение сложного процента

        try {

            double mResultInvestnig; // результат инвестирования под простой процент
            double pervonachalniyVznos; // первоначальный взнос
            double procentnayaStavka; // годовая процентная ставка    static final String INPUT_ERROR = "Убедитесь в правильности заполнения полей";
            pervonachalniyVznos = Double.parseDouble(mVznosPervonachalniy.getText().toString().replace(" ", ""));  // убираем пробелы-разделители, дабы не выдало ошибку при преобразовании полученного значения в Double
            procentnayaStavka = Double.parseDouble(mStavkaProcentnaya.getText().toString()) / 100;
            mResultInvestnig = pervonachalniyVznos * procentnayaStavka;
            String mResult_v = String.format("%.2f", mResultInvestnig);
            mResult.setText("" + separate(mResult_v));

            StringBuilder s = new StringBuilder(mVznosPervonachalniy.getText().toString().replace(" ", "")); // добавляем пробелы в значение первоначального взноса для улучшения читабельности
            for (int i = s.length(); i > 0; i = i - 3) {
                s.insert(i, " ");
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, INPUT_ERROR, Toast.LENGTH_LONG).show();
        }

    }

}
