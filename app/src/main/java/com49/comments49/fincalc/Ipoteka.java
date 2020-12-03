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

public class Ipoteka extends AppCompatActivity {

    Double A; // результат инвестирования под сложный процент

    EditText stoimostJilyaEditText; // поле ввода первоначального вклада
    EditText pervonachalniyVznosEditText; // поле ввода процентной ставки
    EditText procentnayaStavkaEditText;  // поле ввода продолжительности вклада
    EditText srokEditText;   // поле ввода периодичности выплаты процентов за год
    TextView resultEditText;
    TextView resultVsegoViplat;
    TextView resultPereplata;

    boolean canSeparateStoimostJilya = false;  // типа флага, который говорит о том, что в данный момент нельзя осуществлять сепарацию числа
    boolean canSeparatePervonachalniyVznos = false;  // типа флага, который говорит о том, что в данный момент нельзя осуществлять сепарацию числа


    double stoimostJilya;
    double pervonachalniyVznos;
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

        switch (item.getItemId()) {
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
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Ипотечный калькулятор</font>"));
        setContentView(R.layout.activity_ipoteka);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // включает отображение стрелочки назад в тулбаре
        init();
        separateTextView();
        separatePervonachalniyVznos();
        separateStoimostJilya();
    }


    public void separateTextView() {
        stoimostJilyaEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                stoimostJilyaEditText.setText(Methods.separate(stoimostJilyaEditText.getText().toString()));
                pervonachalniyVznosEditText.setText(Methods.separate(pervonachalniyVznosEditText.getText().toString()));
            }
        });
    }

    public void init() {
        stoimostJilyaEditText = findViewById(R.id.stoimost_jilya_editText_id);
        pervonachalniyVznosEditText = findViewById(R.id.vznos_pervonachalniy_editText_id);
        procentnayaStavkaEditText = findViewById(R.id.stavka_procentnaya_editText_id);
        srokEditText = findViewById(R.id.srok_editText_id);
        resultEditText = findViewById(R.id.result_editText_id);
        resultVsegoViplat = findViewById(R.id.result_vsego_viplat_editText_id);
        resultPereplata = findViewById(R.id.result_pereplat_editText_id);

        stoimostJilyaEditText.setText(Methods.separate(stoimostJilyaEditText.getText().toString()));
        pervonachalniyVznosEditText.setText(Methods.separate(pervonachalniyVznosEditText.getText().toString()));

    }


    public void separatePervonachalniyVznos() {

        pervonachalniyVznosEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int before, int count) {

                if (!canSeparateStoimostJilya)  //
                {
                    int a = pervonachalniyVznosEditText.getSelectionEnd();
                    String textAfter = pervonachalniyVznosEditText.getText().toString();
                    canSeparateStoimostJilya = true;

                    pervonachalniyVznosEditText.setText(Methods.separate(pervonachalniyVznosEditText.getText().toString()));
                    String textBefore = pervonachalniyVznosEditText.getText().toString();

                    pervonachalniyVznosEditText.setSelection(a - (textAfter.length() - textBefore.length()));
                } else {
                    canSeparateStoimostJilya = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }


    public void separateStoimostJilya() {

        stoimostJilyaEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int before, int count) {

                if (!canSeparatePervonachalniyVznos)  //
                {
                    int a = stoimostJilyaEditText.getSelectionEnd();
                    String textAfter = stoimostJilyaEditText.getText().toString();
                    canSeparatePervonachalniyVznos = true;

                    stoimostJilyaEditText.setText(Methods.separate(stoimostJilyaEditText.getText().toString()));
                    String textBefore = stoimostJilyaEditText.getText().toString();

                    stoimostJilyaEditText.setSelection(a - (textAfter.length() - textBefore.length()));
                } else {
                    canSeparatePervonachalniyVznos = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    public void getData(View view) { //получаем значение полей и вычисляем результат

        try {
            String stoimost = stoimostJilyaEditText.getText().toString().replace(" ", ""); // убираем пробелы-разделители, дабы не выдало ошибку при преобразовании полученного значения в Double
            String vznos = pervonachalniyVznosEditText.getText().toString().replace(" ", ""); // убираем пробелы-разделители, дабы не выдало ошибку при преобразовании полученного значения в Double

            stoimostJilya = Double.parseDouble(stoimost);
            pervonachalniyVznos = Double.parseDouble(vznos);
            procentnayaStavka = Double.parseDouble(procentnayaStavkaEditText.getText().toString()) / 100 / 12;
            srok = Double.parseDouble(srokEditText.getText().toString()) * 12;


            if (stoimostJilya < pervonachalniyVznos) {
                Toast.makeText(this, "Первоначальный взнос не может превышать стоимость жилья", Toast.LENGTH_LONG).show();

            } else {
                calculate();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Убедитесь в правильности заполнения полей", Toast.LENGTH_LONG).show();
        }

        //stoimostJilyaEditText.setText(Methods.separate(stoimostJilyaEditText.getText().toString()));
        //pervonachalniyVznosEditText.setText(Methods.separate(pervonachalniyVznosEditText.getText().toString()));

    }


    public void calculate() {
        Double x = (stoimostJilya - pervonachalniyVznos) * ((procentnayaStavka * (Math.pow((1 + procentnayaStavka), srok)) / (Math.pow((1 + procentnayaStavka), srok) - 1)));  // ежемесячный платеж
        resultEditText.setText(" " + Methods.separate(String.format("%.2f", x)));
        resultVsegoViplat.setText(" " + Methods.separate(String.format("%.2f", x * srok)));
        resultPereplata.setText(" " + Methods.separate(String.format("%.2f", x * srok - (stoimostJilya - pervonachalniyVznos))));
    }


}
