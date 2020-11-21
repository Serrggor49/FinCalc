package com49.comments49.fincalc;

public class Methods {


    static String separate(String str){ // подаем на вход строку и добавляем в нее пробелы  для улучшения читабельности (было - 3000000, стало 3 000 000)

        StringBuilder s = new StringBuilder(str.replace(" ", ""));
        for(int i = s.length(); i > 0; i = i-3){
            s.insert(i, " ");
        }
        return  s.toString().trim();
    }

}
