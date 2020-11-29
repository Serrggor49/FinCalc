package com49.comments49.fincalc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

import static com49.comments49.fincalc.Methods.separate;

public class Slojniy_procent extends AppCompatActivity {

    Double A; // результат инвестирования под сложный процент
    double A_2; // результат инвестирования под простой процент
    double prft; // сумма в результате инвестирования под сложный процент минус первоначальный взнос
    double P; // первоначальный взнос
    double r; // годовая процентная ставка
    double t; // срок, лет
    double n; // количество начислений в году (если каждый месяц начисляют процент, то = 12)

    EditText vznosPervonachalniy; // поле ввода первоначального вклада
    EditText stavkaProcentnaya; // поле ввода процентной ставки
    EditText srok;  // поле ввода продолжительности вклада
    EditText kolichestvo_nachisleniy;   // поле ввода периодичности выплаты процентов за год
    TextView profit; // заработанная сумма (сумма в конце вклада минус первоначальный вклад)
    TextView result; // сумма в конце вклада


    ArrayList<DataPoint> arrayListDataPoint = new ArrayList<>(); // в этот лист добавляются пары значений DataPoint(x, y)
    LineGraphSeries<DataPoint> graf_1;  //
    LineGraphSeries<DataPoint> graf_2;
    GraphView graph;  // график
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
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Сложный процент</font>"));
        setContentView(R.layout.activity_slojniy_procent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // включает отображение стрелочки назад в тулбаре

        init();
        setDesignGraf();

    }


    public void init(){
        graph = findViewById(R.id.graph);
        vznosPervonachalniy = findViewById(R.id.vznos_pervonachalniy_editText_id);
        stavkaProcentnaya = findViewById(R.id.stavka_procentnaya_editText_id);
        srok = findViewById(R.id.srok_editText_id);
        kolichestvo_nachisleniy = findViewById(R.id.kolichestvo_nachisleniy_editText_id);
        result = findViewById(R.id.result_editText_id);
        profit = findViewById(R.id.profit_editText_id);
    }


    public void setDesignGraf(){

        graf_1 = new LineGraphSeries<>(arrayListDataPoint.toArray(new DataPoint[0]));
        graf_1.setColor(getResources().getColor(R.color.green_graf, null));  // цвет графика
        graph.getGridLabelRenderer().setGridColor(Color.GRAY); // цвет сетки графика
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE); // цвет текста по оси Y
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE); // цвет текста по оси Y
        graph.setCursorMode(true);
    }

    public void getData(View view) { //получаем значение полей и вычисляем результат

        String vznos = vznosPervonachalniy.getText().toString().replace(" ", ""); // убираем пробелы-разделители, дабы не выдало ошибку при преобразовании полученного значения в Double
        P = Double.parseDouble(vznos);
        r = Double.parseDouble(stavkaProcentnaya.getText().toString()) / 100;
        t = Double.parseDouble(srok.getText().toString());
        n = Double.parseDouble(kolichestvo_nachisleniy.getText().toString());

        calculate();

        StringBuilder s = new StringBuilder(vznosPervonachalniy.getText().toString().replace(" ", "")); // добавляем пробелы в значение первоначального взноса для улучшения читабельности
        for(int i = s.length(); i > 0; i = i-3){
            s.insert(i, " ");
        }

        //vznosPervonachalniy.setText(s.toString());
        vznosPervonachalniy.setText(Methods.separate(vznosPervonachalniy.getText().toString()));

    }


    void calculate(){  // в данном методе вычисляем значение сложного процента

        A = (P * (Math.pow((1 + (r / n)), (n * t)))); // стоимость вклада по окончанию срока (сложный процент)
        String result_v = String.format("%.2f",  A);
        result.setText(" " + Methods.separate(result_v));
        String result_prft =  String.format("%.2f",  (A-P));
        // profit.setText("Прибыль               " + separate(result_prft));


        for (int i = 0; i <= t; i++) {  // тут расчитываем стоимость вклада с шагом в один год, для построения графика
            A = (P * (Math.pow((1 + (r / n)), (n * i))));
            arrayListDataPoint.add(new DataPoint(i, A));
        }

        A_2 = P*(1+t*r);
        setGraph();
        arrayListDataPoint.clear();

//        LinearLayout linearLayout = findViewById(R.id.linear_g);  // задать ширину
//        ViewGroup.LayoutParams param = graph.getLayoutParams();
//        param.width = linearLayout.getWidth()+500;
//        graph.setLayoutParams(param);

    }



    void setGraph() {

//        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{  // пример заполнения
//                new DataPoint(0, 1),
//                new DataPoint(1, 5),
//                new DataPoint(2, 3),
//                new DataPoint(4, 2),
//                new DataPoint(5, 2),
//                new DataPoint(6, 2),
//                new DataPoint(7, 2),
//                new DataPoint(8, 3),
//                new DataPoint(9, 2),
//                new DataPoint(10, 6)
//        });


        setDesignGraf();

        graph.getViewport().setMinX(0); // минимальное значение графика по оси X
        graph.getViewport().setMaxX(t); // максимальное значение графика по оси X

        graph.getViewport().setMinY(P); // минимальное значение графика по оси Y
        graph.getViewport().setMaxY(A); // максимальное значение графика по оси Y

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);

        graph.removeAllSeries();
        graph.addSeries(graf_1);


//        graf_2 = new LineGraphSeries<>(new DataPoint[] {   // для построения графика простый процентов-
//                new DataPoint(0, P),
//                new DataPoint(t, A_2),
//
//        });
//        graf_2.setColor(Color.RED);
//        graph.addSeries(graf_2);


    }






}


//        Toast.makeText(this, "" + graf_1.getHighestValueX(), Toast.LENGTH_LONG).show();
