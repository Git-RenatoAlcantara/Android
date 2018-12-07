package com.curso.ce.sistemacadastralpessoafisica.activity.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.curso.ce.sistemacadastralpessoafisica.R;
import com.curso.ce.sistemacadastralpessoafisica.activity.activity.CadastroActivity;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    }

    public void abrirTelaCadastro(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }

}
