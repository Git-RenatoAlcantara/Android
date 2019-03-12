package com.example.renato.organize.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.renato.organize.R;
import com.example.renato.organize.config.ConfiguracaoFireBase;
import com.example.renato.organize.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginActivity extends AppCompatActivity {
    private Button btnEntrar;
    private EditText editEmail, editSenha;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnEntrar = findViewById(R.id.btnLogin);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);


        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoEmail = editEmail.getText().toString();
                String textoSenha = editSenha.getText().toString();

                if (!textoEmail.isEmpty()){
                    if (!textoSenha.isEmpty()){
                        usuario = new Usuario();

                        usuario.setEmail(textoEmail);
                        usuario.setSenha(textoSenha);
                        validarLogin();
                    }else{
                        Toast.makeText(LoginActivity.this,"Preenca o email", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this,"Preenca a senha", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void validarLogin(){
        autenticacao = ConfiguracaoFireBase.getfireBaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                   abrirTelaPrincipal();
                }else{
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

                    Toast.makeText(LoginActivity.this,excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void abrirTelaPrincipal(){
        startActivity(new Intent(this,  PrincipalActivity.class));
        finish();
    }
}
