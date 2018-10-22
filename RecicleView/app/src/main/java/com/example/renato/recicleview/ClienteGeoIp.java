package com.example.renato.recicleview;

import com.example.renato.recicleview.classes.Bin;

import org.json.JSONException;
import org.json.JSONObject;

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

    public static Bin retornarLocalizacaoPorIp(String bin) throws JSONException {
        String content = request("https://lookup.binlist.net/" + bin);
//        System.out.println(content);
        JSONObject obj = new JSONObject(content);
        String paisResult = obj.getString("country");
        JSONObject convert = new JSONObject(paisResult);

        String bandeira = obj.getString("scheme");
        String tipo  = obj.getString("type");
        String estado = convert.getString("name");
        String sigla = convert.getString("alpha2");
        String moeda = convert.getString("currency");

        Bin binResult = new Bin(tipo, bandeira, sigla,estado, moeda);


        return binResult;
    }
}
