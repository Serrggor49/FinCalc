package com49.comments49.fincalc;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;



// https://github.com/jjoe64/GraphView-Demos/blob/master/app/src/main/java/com/jjoe64/graphview_demos/examples/StylingColors.java описание либы по построению графиков

public class MainActivity extends AppCompatActivity {

    Double A; // результат инвестирования под сложный процент
    double A_2; // результат инвестирования под простой процент
    double prft; // сумма в результате инвестирования под сложный процент минус первоначальный взнос
    double P; // первоначальный взнос
    double r; // годовая процентная ставка
    double t; // срок, лет
    double n; // количество начислений в году (если каждый месяц начисляют процент, то = 12)

    EditText vznosPervonachalniy;
    EditText stavkaProcentnaya;
    EditText srok;
    EditText kolichestvo_nachisleniy;
    TextView profit;
    TextView result;
    GraphView graph;

    LineGraphSeries<DataPoint> graf_1;
    LineGraphSeries<DataPoint> graf_2;
    ArrayList<DataPoint> arrayListDataPoint = new ArrayList<>(); // в этот лист добавляются пары значений DataPoint(x, y)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        Button button = findViewById(R.id.button_id);


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
        vznosPervonachalniy.setText(separate(vznosPervonachalniy.getText().toString()));

    }


    void calculate(){  // в данном методе вычисляем значение сложного процента

        A = (P * (Math.pow((1 + (r / n)), (n * t)))); // стоимость вклада по окончанию срока (сложный процент)

        //result.setText("Итоговая сумма " + String.format("%.2f", (A.toString())));
        String result_v = String.format("%.2f",  A);
        result.setText("Итоговая сумма " + separate(result_v));
        String result_prft =  String.format("%.2f",  (A-P));
        profit.setText("Заработано " + separate(result_prft));


        for (int i = 0; i <= t; i++) {  // тут расчитываем стоимость вклада с шагом в один год, для построения графика
            A = (P * (Math.pow((1 + (r / n)), (n * i))));
            arrayListDataPoint.add(new DataPoint(i, A));
        }

        graf_1 = new LineGraphSeries<>(arrayListDataPoint.toArray(new DataPoint[0]));

        A_2 = P*(1+t*r);
        setGraph();
        arrayListDataPoint.clear();

    }

    static String separate(String str){ // подаем на вход строку и добавляем в нее пробелы  для улучшения читабельности (было - 3000000, стало 3 000 000)

        StringBuilder s = new StringBuilder(str.replace(" ", ""));
        for(int i = s.length(); i > 0; i = i-3){
            s.insert(i, " ");
        }
        return  s.toString().trim();
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



        graf_1.setColor(getResources().getColor(R.color.green_graf, null));  // цвет графика

        graph.getGridLabelRenderer().setGridColor(Color.GRAY); // цвет сетки графика
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE); // цвет текста по оси Y
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE); // цвет текста по оси Y


        graph.setCursorMode(true);
        graph.getViewport().setMinX(0); // минимальное значение графика по оси X
        graph.getViewport().setMaxX(t); // максимальное значение графика по оси X

        graph.getViewport().setMinY(P); // минимальное значение графика по оси Y
        graph.getViewport().setMaxY(A); // максимальное значение графика по оси Y

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);

        graph.removeAllSeries();
        graph.addSeries(graf_1);


//        graf_2 = new LineGraphSeries<>(new DataPoint[] {
//                new DataPoint(0, P),
//                new DataPoint(t, A_2),
//
//        });
//        graf_2.setColor(Color.RED);
//        graph.addSeries(graf_2);


        //Toast.makeText(this, "результат " + A_2, Toast.LENGTH_LONG).show();

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

    static void setDesignGraf(){

    }


}


//        Toast.makeText(this, "" + graf_1.getHighestValueX(), Toast.LENGTH_LONG).show();
