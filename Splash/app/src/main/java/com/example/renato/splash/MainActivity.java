package com.example.renato.splash;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intent = new Intent(this, Splash.class);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setProgress(1);
        Thread timer = new Thread(){
            public void run(){

                    for (int i = 1; i <= 100; i++  ){
                        final int progresso = i;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                    progressBar.setProgress(progresso);
                            }
                        });

                        try{
                            sleep(500);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                startActivity(intent);
                finish();
            }
        };
        timer.start();
    }
}
