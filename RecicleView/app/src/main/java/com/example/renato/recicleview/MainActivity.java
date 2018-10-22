package com.example.renato.recicleview;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toolbar;


import com.example.renato.recicleview.classes.Bin;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText txtBin;
    ListView listview;
    Bin binResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        listview = (ListView) findViewById(R.id.listView);
        txtBin = findViewById(R.id.txtBin);


    }
    public void carregarItem(View view){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String bin = txtBin.getText().toString();

        ClienteGeoIp clienteGeoIp = new ClienteGeoIp();
        try {
            binResult =  clienteGeoIp.retornarLocalizacaoPorIp(bin);

            String[] dados = new String[] {binResult.getBandeira(),binResult.getTipo(),binResult.getPais(), binResult.getEstado()};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview_edititem, dados);
            listview.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


}
