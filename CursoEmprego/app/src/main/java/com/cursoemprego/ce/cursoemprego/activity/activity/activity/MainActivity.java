package com.cursoemprego.ce.cursoemprego.activity.activity.activity;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Pair;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.cursoemprego.ce.cursoemprego.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView cardViewEmpregos, cardViewInformatica, cardViewGeral,
            cardViewSobrenos, cardViewFaleConosco;
    private Spinner spinner;
    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();




    }
    public void modal(){
        //Lista de itens
        String[] estados = getResources().getStringArray(R.array.estados);


        //adapter utilizando um layout customizado (TextView)
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.modal, estados);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha seu estado:");
        //define o diálogo como uma lista, passa o adapter.
        builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(MainActivity.this, "posição selecionada=" + arg1, Toast.LENGTH_SHORT).show();
                alerta.dismiss();
            }
        });

        alerta = builder.create();
        alerta.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.cardViewEmpregos:
                modal();

                /*Intent intentEmpregos = new Intent(getApplicationContext(), EmpregosActivity.class);
                activityTrasition(intentEmpregos);*/
                break;

            case R.id.cardViewInfo:
                Intent intentInfo = new Intent(getApplicationContext(), InformaticaActivity.class);
                activityTrasition(intentInfo);
                break;

        }


    }

    private void activityTrasition(Intent intent){

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
      }

    }



    private void inicializarComponentes(){

        spinner =  findViewById(R.id.spinner);

        //Listener cardeView
        cardViewEmpregos = findViewById(R.id.cardViewEmpregos);
        cardViewEmpregos.setOnClickListener(this);

        cardViewInformatica = findViewById(R.id.cardViewInfo);
        cardViewInformatica.setOnClickListener(this);

        cardViewGeral = findViewById(R.id.cardViewGeral);
        cardViewGeral.setOnClickListener(this);

        cardViewSobrenos = findViewById(R.id.cardViewSobre);
        cardViewSobrenos.setOnClickListener(this);

        cardViewFaleConosco = findViewById(R.id.cardViewFalarConosco);
        cardViewFaleConosco.setOnClickListener(this);

    }

}
