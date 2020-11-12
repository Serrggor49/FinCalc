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

public class MainActivity extends AppCompatActivity {

    double A; // результат инвестирования под сложный процент
    double A_2; // результат инвестирования под простой процент
    double P; // первоначальный взнос
    double r; // годовая процентная ставка
    double t; // срок, лет
    double n; // количество начислений в году (если каждый месяц начисляют процент, то = 12)

    EditText vznosPervonachalniy;
    EditText stavkaProcentnaya;
    EditText srok;
    EditText kolichestvo_nachisleniy;
    TextView result;

    LineGraphSeries<DataPoint> graf_1;
    LineGraphSeries<DataPoint> graf_2;
    ArrayList<DataPoint> arrayListDataPoint = new ArrayList<>(); // в этот лист добавляются пары значений DataPoint(x, y)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button_id);
    }


    public void getData(View view) { //получаем значение полей и вычисляем результат

        vznosPervonachalniy = findViewById(R.id.vznos_pervonachalniy_editText_id);
        stavkaProcentnaya = findViewById(R.id.stavka_procentnaya_editText_id);
        srok = findViewById(R.id.srok_editText_id);
        kolichestvo_nachisleniy = findViewById(R.id.kolichestvo_nachisleniy_editText_id);
        result = findViewById(R.id.result_editText_id);


        P = Double.parseDouble(vznosPervonachalniy.getText().toString());
        r = Double.parseDouble(stavkaProcentnaya.getText().toString()) / 100;
        t = Double.parseDouble(srok.getText().toString());
        n = Double.parseDouble(kolichestvo_nachisleniy.getText().toString());

        calculate();

    }


    void calculate(){  // в данном методе вычисляем значение сложного процента

        A = (P * (Math.pow((1 + (r / n)), (n * t)))); // стоимость вклада по окончанию срока (сложный процент)
        result.setText(String.format("%.2f", A));


        for (int i = 0; i <= t; i++) {  // тут расчитываем стоимость вклада с шагом в один год, для построения графика
            A = (P * (Math.pow((1 + (r / n)), (n * i))));
            arrayListDataPoint.add(new DataPoint(i, A));
        }

        graf_1 = new LineGraphSeries<>(arrayListDataPoint.toArray(new DataPoint[0]));

        A_2 = P*(1+t*r);
        setGraph();
        arrayListDataPoint.clear();



    }


    void setGraph() {
        GraphView graph = findViewById(R.id.graph);

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

//
        //graph.setTitleColor(R.color.green);
        //graph.setTitle("график сложного процента");
        graph.setCursorMode(true);


        graph.getViewport().setMinX(0); // минимальное значение графика по оси X
        graph.getViewport().setMaxX(t); // максимальное значение графика по оси X

        graph.getViewport().setMinY(P); // минимальное значение графика по оси Y
        graph.getViewport().setMaxY(A); // максимальное значение графика по оси Y

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);

        graph.removeAllSeries();
        graph.addSeries(graf_1);





        graf_2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, P),
                new DataPoint(t, A_2),

        });

        graph.addSeries(graf_2);


        Toast.makeText(this, "результат " + A_2, Toast.LENGTH_LONG).show();

    }


}


//        Toast.makeText(this, "" + graf_1.getHighestValueX(), Toast.LENGTH_LONG).show();
