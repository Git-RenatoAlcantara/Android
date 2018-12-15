package com.cursoemprego.pontoce.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cursoemprego.pontoce.R;
import com.cursoemprego.pontoce.config.ConfiguracaoFireBase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ScannerActivity extends AppCompatActivity {
    public static TextView tvresult;
    private Button btnScanner;
    SweetAlertDialog pDialog;
    FirebaseAuth autication  =  ConfiguracaoFireBase.getfireBaseAutenticacao();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        inicializarComponentes();


    }

    private void inicializarComponentes(){
        tvresult = findViewById(R.id.tvResult);
        btnScanner = findViewById(R.id.btnScanner);
        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
                startActivity(intent);
            }
        });
    }
}
