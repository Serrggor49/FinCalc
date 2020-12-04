package com49.comments49.fincalc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.graphics.Color;
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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class Slojniy_procent extends MyAppCompatActivity {

    static final String INPUT_ERROR = "Убедитесь в правильности заполнения полей";
    private EditText vznosPervonachalniyEdit; // поле ввода первоначального вклада
    private EditText stavkaProcentnayaEdit; // поле ввода процентной ставки
    private EditText srokEdit;  // поле ввода продолжительности вклада
    private EditText kolichestvo_nachisleniyEdit;   // поле ввода периодичности выплаты процентов за год
    private TextView resultTextView; // сумма в конце вклада
    private ArrayList<DataPoint> arrayListDataPoint = new ArrayList<>(); // в этот лист добавляются пары значений DataPoint(x, y)
    private LineGraphSeries<DataPoint> graf_1;  //
    private LineGraphSeries<DataPoint> graf_2;
    private GraphView graph;  // график


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Сложный процент</font>"));
        setContentView(R.layout.activity_slojniy_procent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // включает отображение стрелочки назад в тулбаре

        init();
        setDesignGraf();
        separateTextView(vznosPervonachalniyEdit);


        stringHelp = R.string.help_slojniy_procent;

    }


    private void init() {
        graph = findViewById(R.id.graph);
        vznosPervonachalniyEdit = findViewById(R.id.vznos_pervonachalniy_editText_id);
        stavkaProcentnayaEdit = findViewById(R.id.stavka_procentnaya_editText_id);
        srokEdit = findViewById(R.id.srok_editText_id);
        kolichestvo_nachisleniyEdit = findViewById(R.id.kolichestvo_nachisleniy_editText_id);
        resultTextView = findViewById(R.id.result_editText_id);
        vznosPervonachalniyEdit.setText(separate(vznosPervonachalniyEdit.getText().toString()));
    }


    public void calculate(View view) {  // в данном методе вычисляем значение сложного процента

        try {
            String vznos = vznosPervonachalniyEdit.getText().toString().replace(" ", ""); // убираем пробелы-разделители, дабы не выдало ошибку при преобразовании полученного значения в Double
            double resultInvestnig;
            double pervonachalniyVznos = Double.parseDouble(vznos);
            double procentnayaStavka = Double.parseDouble(stavkaProcentnayaEdit.getText().toString()) / 100;
            double srok = Double.parseDouble(srokEdit.getText().toString());
            double kolichestvoNachisleniy = Double.parseDouble(kolichestvo_nachisleniyEdit.getText().toString());   // количество начислений в году (если процент начисляют ежемесячно, то = 12)

            resultInvestnig = (pervonachalniyVznos * (Math.pow((1 + (procentnayaStavka / kolichestvoNachisleniy)), (kolichestvoNachisleniy * srok)))); // стоимость вклада по окончанию срока (сложный процент)
            String result_v = String.format("%.2f", resultInvestnig);
            resultTextView.setText(separate(result_v));

            for (int i = 0; i <= srok; i++) {  // тут расчитываем стоимость вклада с шагом в один год, для построения графика
                resultInvestnig = (pervonachalniyVznos * (Math.pow((1 + (procentnayaStavka / kolichestvoNachisleniy)), (kolichestvoNachisleniy * i))));
                arrayListDataPoint.add(new DataPoint(i, resultInvestnig));
            }

            printGraph(0, srok, pervonachalniyVznos, resultInvestnig);
            arrayListDataPoint.clear();
        } catch (NumberFormatException e) {
            Toast.makeText(this, INPUT_ERROR, Toast.LENGTH_LONG).show();
        }

    }


    public void setDesignGraf() {
        graf_1 = new LineGraphSeries<>(arrayListDataPoint.toArray(new DataPoint[0]));
        graf_1.setColor(getResources().getColor(R.color.green_graf, null));  // цвет графика
        graph.getGridLabelRenderer().setGridColor(Color.GRAY); // цвет сетки графика
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE); // цвет текста по оси Y
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE); // цвет текста по оси Y
        graph.setCursorMode(true);
    }

    void printGraph(double minX, double maxX, double minY, double maxY) {
        setDesignGraf();
        graph.getViewport().setMinX(minX); // минимальное значение графика по оси X
        graph.getViewport().setMaxX(maxX); // максимальное значение графика по оси X
        graph.getViewport().setMinY(minY); // минимальное значение графика по оси Y
        graph.getViewport().setMaxY(maxY); // максимальное значение графика по оси Y
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.removeAllSeries();
        graph.addSeries(graf_1);
    }

}
