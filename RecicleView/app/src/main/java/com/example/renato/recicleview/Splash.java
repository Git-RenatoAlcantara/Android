package com.example.renato.recicleview;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    private ProgressBar  progressBar;
    private TextView txtStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Declarando a intencao de que essa activity quer chamar a MainActive
        final Intent intent = new Intent(this, MainActivity.class);


        progressBar = findViewById(R.id.progress);
        txtStatus = findViewById(R.id.txtStatus);

       //Testando conexao com a internet
        if (testarConexao()) {
            Thread timer = new Thread() {
                public void run() {
                    //Caso tenha conexao comeca o progressBar com 20%
                    final int conectado = 20;

                    //Seta a variavel conectado com o progress bar.
                    progressBar.setProgress(conectado);

                    //Atualiza a parte visual do progressBar
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(conectado);
                        }
                    });

                    //Dorme por meio segundo
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //Continua a contagem do progressBar ate 50
                    for (int i = conectado; i < 50; i++) {
                        final int progresso = i;


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(progresso);
                            }
                        });

                        try {
                            sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    
                    startActivity(intent);
                    finish();

                }
            };
            timer.start();

        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // set title
            builder.setTitle("Conexao Erro");

            // set dialog message
            builder
                    .setMessage("Você tem que está conectado para usar o serviço")
            .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close
                            // current activity
                            Splash.this.finish();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = builder.create();

            // show it
            alertDialog.show();

        }
    }

    //Metodo que faz o teste da conexao e retorana boolean
    public boolean testarConexao(){

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(this.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}

