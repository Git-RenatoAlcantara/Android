package com.example.renato.alcoolougasolina;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText alcool;
    private EditText gasolina;
    private TextView msgResultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alcool = findViewById(R.id.editAlcool);
        gasolina = findViewById(R.id.editGasolina);
        msgResultado = findViewById(R.id.textResultado);

    }

    public void calcularPreco(View view){
        String precoAlcool =  alcool.getText().toString();
        String precoGasolina = gasolina.getText().toString();
       if (validar(precoAlcool,precoGasolina)){
            calcularMelhorPreco(precoAlcool, precoGasolina);
       }

    }
    public void calcularMelhorPreco(String valor1, String valor2){
        Double valorA = Double.parseDouble(valor1);
        Double valorG = Double.parseDouble(valor2);
        Double resultado = valorA / valorG;
        if (resultado > 0.7){
            msgResultado.setText("Gasolina e melhor");
        }else {
            msgResultado.setText("Álcool é melhor");
        }
    }
    public boolean validar(String pAlcool, String pGasolina){
        Boolean camposValidos = true;
        if (pAlcool == null || pAlcool.equals("")){
            camposValidos = false;
        }else if (pGasolina == null || pGasolina.equals("")){
            camposValidos = false;
        }
        return camposValidos;
    }
}
