package com49.comments49.fincalc;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MyAppCompatActivity extends AppCompatActivity {

    int mStringHelp;  // текст с описанием раздела
    boolean mCanSeparateNumbers = false;  // типа флага, который говорит о том, что в данный момент нельзя осуществлять сепарацию числа

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
                Info.setText(mStringHelp);
                Intent intent = new Intent(this, Info.class);
                startActivity(intent);
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }


    public void separateTextView(final EditText editText) {  // разделяем на лету вводимые числа

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int before, int count) {

                if (!mCanSeparateNumbers)  //
                {
                    int a = editText.getSelectionEnd();
                    String textAfter = editText.getText().toString();
                    mCanSeparateNumbers = true;
                    editText.setText(separate(editText.getText().toString()));
                    String textBefore = editText.getText().toString();
                    editText.setSelection(a - (textAfter.length() - textBefore.length()));
                } else {
                    mCanSeparateNumbers = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }


    public static String separate(String str) { // подаем на вход строку и добавляем в нее пробелы  для улучшения читабельности (было - 3000000, стало 3 000 000)
        StringBuilder s = new StringBuilder(str.replace(" ", ""));
        if (s.length() > 3) {
            if (s.charAt(s.length() - 3) != ',') { // если полученное число имеет разделитель, то мы начинает отделять по 3 числа не с третьего, а с шестого символа, чтобы не было пробела перед запятой
                for (int i = s.length(); i > 0; i = i - 3) {
                    s.insert(i, " ");
                }
            } else {
                for (int i = s.length() - 6; i > 0; i = i - 3) {
                    s.insert(i, " ");
                }
            }
        }
        return s.toString().trim();
    }


}
