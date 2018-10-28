package com.example.renato.whatsappclolne.helper;

import android.util.Base64;

public class Base64Custom {

    public static String base64encode(String texto){
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("\\n|\\r", "");
    }

    public static String base64decode(String textoDecode){

        return new String(Base64.decode(textoDecode, Base64.DEFAULT));
    }
}
