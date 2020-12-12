package com49.comments49.fincalc;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.ArrayList;

public class SlojniyProcent extends MyAppCompatActivity {

    private static final int HELP_TEXT = R.string.help_slojniy_procent;
    private static final String BAR_TITLE = "Сложный процент";
    private static final String INPUT_ERROR = "Убедитесь в правильности заполнения полей";

    private EditText mVznosPervonachalniyEdit; // поле ввода первоначального вклада
    private EditText mStavkaProcentnayaEdit; // поле ввода процентной ставки
    private EditText mSrokEdit;  // поле ввода продолжительности вклада
    private EditText mKolichestvoNachisleniyEdit;   // поле ввода периодичности выплаты процентов за год
    private TextView mResultTextView; // сумма в конце вклада
    private ArrayList<DataPoint> mArrayListDataPoint = new ArrayList<>(); // в этот лист добавляются пары значений DataPoint(x, y)
    private LineGraphSeries<DataPoint> mGraphSeries;  //
    private GraphView mGraph;  // график

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slojniy_procent);
        init();
        setDesignGraf();
        mStringHelp = HELP_TEXT;
        calculate(null);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(BAR_TITLE);
        }
    }

    private void init() {
        mGraph = findViewById(R.id.graph);
        mVznosPervonachalniyEdit = findViewById(R.id.vznos_pervonachalniy_editText_id);
        mStavkaProcentnayaEdit = findViewById(R.id.stavka_procentnaya_editText_id);
        mSrokEdit = findViewById(R.id.srok_editText_id);
        mKolichestvoNachisleniyEdit = findViewById(R.id.kolichestvo_nachisleniy_editText_id);
        mResultTextView = findViewById(R.id.result_editText_id);
        mVznosPervonachalniyEdit.setText(separate(mVznosPervonachalniyEdit.getText().toString()));

        separateTextView(mVznosPervonachalniyEdit, mResultTextView);
        separateTextView(mStavkaProcentnayaEdit, mResultTextView);
        separateTextView(mSrokEdit, mResultTextView);
        separateTextView(mKolichestvoNachisleniyEdit, mResultTextView);

    }

    public void calculate(View view) {  // в данном методе вычисляем значение сложного процента

        try {
            double resultInvestnig;
            double pervonachalniyVznos = Double.parseDouble(mVznosPervonachalniyEdit.getText().toString().replace(" ", "")); // убираем пробелы-разделители, чтобы не выдало ошибку при преобразовании полученного значения в Double
            double procentnayaStavka = Double.parseDouble(mStavkaProcentnayaEdit.getText().toString().replace(" ", "")) / 100;
            double srok = Double.parseDouble(mSrokEdit.getText().toString().replace(" ", ""));
            double kolichestvoNachisleniy = Double.parseDouble(mKolichestvoNachisleniyEdit.getText().toString().replace(" ", ""));   // количество начислений в году (если процент начисляют ежемесячно, то = 12)

            resultInvestnig = (pervonachalniyVznos * (Math.pow((1 + (procentnayaStavka / kolichestvoNachisleniy)), (kolichestvoNachisleniy * srok)))); // стоимость вклада по окончанию срока (сложный процент)
            String result_v = String.format("%.2f", resultInvestnig);
            mResultTextView.setText(separate(result_v));
            mResultTextView.setTextColor(getColor(R.color.green_graf));

            for (int i = 0; i <= srok; i++) {  //  расчитываем стоимость вклада с шагом в один год, для построения графика
                resultInvestnig = (pervonachalniyVznos * (Math.pow((1 + (procentnayaStavka / kolichestvoNachisleniy)), (kolichestvoNachisleniy * i))));
                mArrayListDataPoint.add(new DataPoint(i, resultInvestnig));
            }

            printGraph(srok, pervonachalniyVznos, resultInvestnig);
            mArrayListDataPoint.clear();
        } catch (NumberFormatException e) {
            Toast.makeText(this, INPUT_ERROR, Toast.LENGTH_LONG).show();
        }
    }

    private void setDesignGraf() {
        mGraphSeries = new LineGraphSeries<>(mArrayListDataPoint.toArray(new DataPoint[0]));
        mGraphSeries.setColor(getResources().getColor(R.color.green_graf, null));  // цвет графика
        mGraph.getGridLabelRenderer().setGridColor(Color.GRAY); // цвет сетки графика
        mGraph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE); // цвет текста по оси Y
        mGraph.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE); // цвет текста по оси Y
        mGraph.setCursorMode(true);
    }

    private void printGraph(double maxX, double minY, double maxY) {
        setDesignGraf();
        mGraph.getViewport().setMinX(0); // минимальное значение графика по оси X
        mGraph.getViewport().setMaxX(maxX); // максимальное значение графика по оси X
        mGraph.getViewport().setMinY(minY); // минимальное значение графика по оси Y
        mGraph.getViewport().setMaxY(maxY); // максимальное значение графика по оси Y
        mGraph.getViewport().setXAxisBoundsManual(true);
        mGraph.getViewport().setYAxisBoundsManual(true);
        mGraph.removeAllSeries();
        mGraph.addSeries(mGraphSeries);
    }


}
