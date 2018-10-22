package com.example.renato.gerarfrases;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void gerarFrases(View view){
        String[] frases = {"Frase1","Frase2","Frase3","Frase4","Frase5"};
        int numeroRand = new Random().nextInt(frases.length);
        TextView texto = findViewById(R.id.txtFrase);
        texto.setText(frases[numeroRand]);
    }
}
