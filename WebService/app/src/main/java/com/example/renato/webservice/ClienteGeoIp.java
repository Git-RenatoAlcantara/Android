package com.example.renato.webservice;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ClienteGeoIp {
    private static String readStream(InputStream in){
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        StringBuilder total = new StringBuilder();
        String line;

       try {
           while ((line = r.readLine()) != null){
                total.append(line).append("\n");

           }
       } catch (IOException e) {
           e.printStackTrace();
       }

       return total.toString();

    }
    public static String request(String stringUrl){
        URL url = null;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(stringUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }
        return "";
    }

    public static String retornarLocalizacaoPorIp(){
        String content = request("https://lookup.binlist.net/4005003");
        return content;
    }
}
