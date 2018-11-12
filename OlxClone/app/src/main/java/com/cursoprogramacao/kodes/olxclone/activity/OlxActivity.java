package com.cursoprogramacao.kodes.olxclone.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.cursoprogramacao.kodes.olxclone.R;
import com.cursoprogramacao.kodes.olxclone.helper.ConfiguracaoFirebase;
import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class OlxActivity extends AppCompatActivity {


    private EditText campoEmail, campoSenha;
    private ActionProcessButton botaoAcessar;
    private Switch tipoAcesso;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olx);

        inicializaComponentes();
        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();


        botaoAcessar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();
                botaoAcessar.setProgress(1);
                validarCampo(email, senha);
            }
        });

    }



    private void validarCampo(String email, String senha) {
        if (    !email.isEmpty()    ){

            if (    !senha.isEmpty()    ){

                //Verifica o estado do switch
                if (    tipoAcesso.isChecked()  ){

                    autenticacao.createUserWithEmailAndPassword(
                            email, senha
                    ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                botaoAcessar.setProgress(100);
                                Toast.makeText(OlxActivity.this, "Usuario Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                            }else {

                                String erroExcecao = "";
                                try{

                                    throw task.getException();

                                }catch ( FirebaseAuthWeakPasswordException e){

                                    erroExcecao = "Digite uma senha mais forte!";

                                }catch (FirebaseAuthInvalidCredentialsException e){

                                    erroExcecao = "Por favor, digite um email válido";

                                }catch (FirebaseAuthUserCollisionException e){

                                    erroExcecao = "Esta conta já foi cadastrada";

                                }catch (Exception e){

                                    erroExcecao = "Erro ao cadastrar usuário: "+ e.getMessage();
                                    e.printStackTrace();

                                }

                                Toast.makeText(OlxActivity.this,
                                        erroExcecao,
                                        Toast.LENGTH_SHORT).show();
                                botaoAcessar.setProgress(-1);

                            }
                        }


                    });

                }else{

                    autenticacao.signInWithEmailAndPassword(
                            email,senha
                    ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(OlxActivity.this, "Usuario logado com sucesso!", Toast.LENGTH_SHORT).show();
                                botaoAcessar.setProgress(100);
                                startActivity(new Intent(getApplicationContext(), AnunciosActivity.class));
                            }else{

                                String erroExcecao = "";
                                try{

                                    throw task.getException();

                                }catch (FirebaseAuthInvalidCredentialsException e) {

                                    erroExcecao = "Usuario nao esta cadastrado";

                                }catch (FirebaseAuthInvalidUserException e){

                                    erroExcecao = "E-mail e senha nao correspondem a um usuario cadastrado";

                                }catch (Exception e){

                                    erroExcecao = "Erro ao logar tente novamente "+ e.getMessage();
                                    e.printStackTrace();

                                }

                                Toast.makeText(OlxActivity.this,
                                        erroExcecao,
                                        Toast.LENGTH_SHORT).show();
                                botaoAcessar.setProgress(-1);
                            }
                        }
                    });

                }

            }else{
                Toast.makeText(this, "Preencha a senha", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Preencha o email", Toast.LENGTH_SHORT).show();
        }
    }


    private void inicializaComponentes(){

        campoEmail = findViewById(R.id.editCadastroEmail);
        campoSenha = findViewById(R.id.editCadastroSenha);
        botaoAcessar = findViewById(R.id.btnAcesso);
        tipoAcesso = findViewById(R.id.switchAcesso);

    }
}
