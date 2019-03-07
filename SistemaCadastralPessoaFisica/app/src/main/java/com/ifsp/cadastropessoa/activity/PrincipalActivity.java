package com.ifsp.cadastropessoa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.ifsp.cadastropessoa.R;

public class PrincipalActivity extends AppCompatActivity {
    private TextView anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);


    }

    public void abrirTelaCadastro(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }

    public void exibirCadastrados(View view){

        startActivity(new Intent(this, ListaPessoasActivity.class));
    }

}
