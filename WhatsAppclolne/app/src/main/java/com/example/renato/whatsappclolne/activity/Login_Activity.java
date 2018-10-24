package com.example.renato.whatsappclolne.activity;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.renato.whatsappclolne.R;
import com.example.renato.whatsappclolne.config.ConfiguracaoFirebase;
import com.example.renato.whatsappclolne.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class Login_Activity extends AppCompatActivity {
    private TextInputEditText editEmail, editSenha;
    private FirebaseAuth autenticacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.editLoginEmail);
        editSenha = findViewById(R.id.editLoginSenha);

        autenticacao = ConfiguracaoFirebase.getfireBaseAutenticacao();

    }
    public void validarUsuario(View view){
        String textoEmail = editEmail.getText().toString();
        String textoSenha = editSenha.getText().toString();

        if (!textoEmail.isEmpty()){ //Verifica campo email

            if (!textoSenha.isEmpty()){ //Verifica campo senha
                Usuario usuario = new Usuario();
                usuario.setEmail(textoEmail);
                usuario.setSenha(textoSenha);

                logarUsuario(usuario);

            }else{
                Toast.makeText(Login_Activity.this, "Preencha o senha", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(Login_Activity.this, "Preencha o email", Toast.LENGTH_SHORT).show();
        }
    }


    public void logarUsuario(Usuario usuario){

        autenticacao = ConfiguracaoFirebase.getfireBaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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

                    Toast.makeText(Login_Activity.this,excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void cadastrarUsuario(View view){
        Intent intent = new Intent(this, Cadastro_Activity.class);
        startActivity(intent);
    }

    public void abrirTelaPrincipal(){
        Intent intent = new Intent(new Intent(Login_Activity.this, MainActivity.class));
        startActivity(intent);
    }

    public void onStart() {

        super.onStart();
        FirebaseUser usuarioAtual = autenticacao.getCurrentUser();
        if (usuarioAtual != null){
            abrirTelaPrincipal();
        }
    }

}
