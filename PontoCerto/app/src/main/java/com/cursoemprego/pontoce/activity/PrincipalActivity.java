package com.cursoemprego.pontoce.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cursoemprego.pontoce.R;
import com.cursoemprego.pontoce.config.ConfiguracaoFireBase;
import com.cursoemprego.pontoce.model.Pessoa;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;


public class PrincipalActivity extends AppCompatActivity {
    public static TextView tvresult;
    private EditText editEmail, editSenha;
    private Button botaoLogin;
    private FirebaseAuth autenticacao;
    private Pessoa pessoa;
    android.app.AlertDialog dialog;
    SweetAlertDialog sweetAlertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

/*
        editEmail = findViewById(R.id.editTextIdUsuario);
        editSenha = findViewById(R.id.editTextSenha);

        dialog = new SpotsDialog.Builder().setContext(this).build();
        botaoLogin = findViewById(R.id.btnEntrarLogin);
        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validarLogin();


            }
        });*/
        //Toolbar custom

       /* Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("WhatsApp");
        setSupportActionBar(toolbar);*/


    }



    private void validarLogin(){

        String textoEmail = editEmail.getText().toString();
        String textoSenha = editSenha.getText().toString();

        if (!textoEmail.isEmpty()){
            if (!textoSenha.isEmpty()){
                dialog.show();
                pessoa = new Pessoa();
                pessoa.setEmail(textoEmail);
                pessoa.setSenha(textoSenha);

                logar();

            }else{
                Toast.makeText(PrincipalActivity.this,"Preenca a senha", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(PrincipalActivity.this,"Preenca o email", Toast.LENGTH_SHORT).show();
        }
    }

    public void logar(){


        autenticacao = ConfiguracaoFireBase.getfireBaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                pessoa.getEmail(), pessoa.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    dialog.dismiss();
                    abrirTelaPrincipal();

                }else{
                    dialog.dismiss();
                    String excecao = "";
                    try{

                        throw task.getException();
                    }catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Usuario nao esta cadastrado";
                    }catch (FirebaseAuthInvalidUserException e){
                        excecao = "E-mail e senha nao correspondem a um usuario cadastrado";
                    }catch (Exception e){
                        excecao = "Erro ao logar tente novamente "+ e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(PrincipalActivity.this,excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void abrirTelaPrincipal(){
        startActivity(new Intent(this,  ScannerActivity.class));
        finish();
    }


}
