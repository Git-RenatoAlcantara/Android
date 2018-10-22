package com.example.renato.organize.helper;

import android.util.Base64;

public class Base64Custom {

    public static String base64Encode(String texto){
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("\\n|\\r", "");
    }

    public static String base64Decode(String textoEncode){
        return new String(Base64.decode(textoEncode, Base64.DEFAULT));
    }
}
