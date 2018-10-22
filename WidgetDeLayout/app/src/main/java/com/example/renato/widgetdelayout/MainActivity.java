package com.example.renato.widgetdelayout;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText campoProduto;
    private TextView resultado;
    private CheckBox checkBranco, checkVerde,checkVermelho;
    private RadioGroup rgEstoque;
    List<String> checks = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        campoProduto = findViewById(R.id.edtProdutos);
        resultado = findViewById(R.id.txtResultado);
        checkBranco = findViewById(R.id.cbBranco);
        checkVerde = findViewById(R.id.cbVerde);
        checkVermelho = findViewById(R.id.cbVermelho);
        rgEstoque = findViewById(R.id.rgEstoque);

        verifiedRadio();

    }

    public void verifiedCheck(){
        checks.clear();
        if (checkBranco.isChecked()){
            checks.add(checkBranco.getText().toString());
        }
        if (checkVermelho.isChecked()){
            checks.add(checkVermelho.getText().toString());
        }
        if (checkVerde.isChecked()){
            checks.add(checkVerde.getText().toString());
        }
        resultado.setText(checks.toString());
    }

    public void verifiedRadio(){
        rgEstoque.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rbSim){
                    resultado.setText("Sim");
                }else{
                    resultado.setText("Não");
                }
            }
        });
    }
    public void btnEnviar(View view){

         /* verifiedCheck(); */

        /* String tituloProduto = campoProduto.getText().toString();
        resultado.setText(tituloProduto);*/

    }
}
