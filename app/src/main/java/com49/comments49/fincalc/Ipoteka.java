package com49.comments49.fincalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

    double stoimostJilya;
    double pervonachalniyVznos;
    double procentnayaStavka;
    double srok;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.que_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, Info.class);
        startActivity(intent);
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipoteka);

        init();

    }


    public void init() {
        stoimostJilyaEditText = findViewById(R.id.stoimost_jilya_editText_id);
        pervonachalniyVznosEditText = findViewById(R.id.vznos_pervonachalniy_editText_id);
        procentnayaStavkaEditText = findViewById(R.id.stavka_procentnaya_editText_id);
        srokEditText = findViewById(R.id.srok_editText_id);
        resultEditText = findViewById(R.id.result_editText_id);
    }


    public void getData(View view) { //получаем значение полей и вычисляем результат

        stoimostJilya = Double.parseDouble(stoimostJilyaEditText.getText().toString());
        pervonachalniyVznos = Double.parseDouble(pervonachalniyVznosEditText.getText().toString());
        procentnayaStavka = Double.parseDouble(procentnayaStavkaEditText.getText().toString())/100/12;
        srok = Double.parseDouble(srokEditText.getText().toString())*12;
        calculate();

    }



    public void calculate() {
        Double x = (stoimostJilya - pervonachalniyVznos) * (   (procentnayaStavka * (Math.pow((1+procentnayaStavka), srok))  / (Math.pow((1+procentnayaStavka), srok)-1)));
        resultEditText.setText("Ежемесячный платеж: " + String.format("%.2f", x));
    }


}
