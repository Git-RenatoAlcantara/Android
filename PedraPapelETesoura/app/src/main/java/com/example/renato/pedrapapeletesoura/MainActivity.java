package com.example.renato.pedrapapeletesoura;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void selecionarPedra(View view){
        this.opcaoSelecionada("pedra");
    }

    public void selecionarTesoura(View view){
        this.opcaoSelecionada("tesoura");
    }

    public void selecionarPapel(View view){
        this.opcaoSelecionada("papel");
    }

    public void opcaoSelecionada(String escolhaUsuario){

        ImageView imagem = findViewById(R.id.imageResultado);
        TextView resultado = findViewById(R.id.txtResultado);

        String[]  opcoes = {"pedra","tesoura","papal"};
        int numero = new Random().nextInt(3);
        String escolhaApp = opcoes[numero];


        switch (escolhaApp){
            case "pedra":
                imagem.setImageResource(R.drawable.pedra);
                break;
            case "tesoura":
                imagem.setImageResource(R.drawable.tesoura);
                break;
            case "papel":
                imagem.setImageResource(R.drawable.papel);
                break;
        }


        if ((escolhaApp == "pedra" && escolhaUsuario == "tesoura") ||
                (escolhaApp == "papel" && escolhaUsuario == "pedra") ||
                (escolhaApp == "tesoura"  && escolhaUsuario == "papel")){
                resultado.setText("O app ganhou!");
        }else if((escolhaUsuario == "pedra" && escolhaApp == "tesoura") ||
                (escolhaUsuario == "papel" && escolhaApp == "pedra") ||
                (escolhaUsuario == "tesoura"  && escolhaApp == "papel")){
                resultado.setText("Você ganhou!");
        }else{
            resultado.setText("Empatou!");
        }
    }
}
