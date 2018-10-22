package com.example.renato.organize.helper;

import java.text.SimpleDateFormat;

public class DateCustom {

    public static String dataAtual(){
      long date =  System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyy");
       String data = simpleDateFormat.format(date);
       return data;
    }
}
