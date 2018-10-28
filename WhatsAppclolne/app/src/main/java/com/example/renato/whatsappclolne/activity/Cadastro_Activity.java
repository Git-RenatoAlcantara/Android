package com.example.renato.whatsappclolne.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.renato.whatsappclolne.R;
import com.example.renato.whatsappclolne.config.ConfiguracaoFirebase;
import com.example.renato.whatsappclolne.helper.Base64Custom;
import com.example.renato.whatsappclolne.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

/*
    Classe responsavel por pegar os dados da view
    Codificar o email e cadastrar
 */
public class Cadastro_Activity extends AppCompatActivity {
    private TextInputEditText editNome, editEmail, editSenha;
    private FirebaseAuth auticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_);

        editNome = findViewById(R.id.editUsuario);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);



    }
    public void cadastrarUsuario(final Usuario usuario){

        //Pegando instacia autenticacao
        auticacao = ConfiguracaoFirebase.getfireBaseAutenticacao();

        //Metodo criando email e senha
        auticacao.createUserWithEmailAndPassword(
            usuario.getEmail(),usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Verificando se o cadastro foi realizado sem erro
                if (task.isSuccessful()){

                    Toast.makeText(Cadastro_Activity.this, "Usuario cadastrado", Toast.LENGTH_SHORT).show();
                    finish();

                    //Salvando dados no firebase
                    try{
                        String identificadorUsuario = Base64Custom.base64encode(usuario.getEmail());
                        usuario.setIdusuario(identificadorUsuario);
                        usuario.salvar();

                    }catch (Exception e){
                        e.printStackTrace();
                    }


                }else{
                    String excecao = "";
                    try{

                        throw task.getException();
                    }catch ( FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte!";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Por favor, digite um email válido";

                    }catch (FirebaseAuthUserCollisionException e){
                        excecao = "Esta conta já foi cadastrada";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: "+ e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(Cadastro_Activity.this, excecao, Toast.LENGTH_SHORT).show();
                }

            }
        });
        System.out.println("Usuario criado");

    }

    //Metodo validar cadastro
    public void validarCadastroUsuario(View view){

        String textoNome = editNome.getText().toString();
        String textoEmail = editEmail.getText().toString();
        String textoSenha = editSenha.getText().toString();

        if (!textoNome.isEmpty()){ //Verifica campo nome

            if (!textoEmail.isEmpty()){ //Verifica campo email

                if (!textoSenha.isEmpty()){ //Verifica campo senha

                   //Criando um objeto usuario passando por parametro para o metodo cadastrarUsuario
                    Usuario usuario = new Usuario();
                    System.out.println("Objeto usuario criado");
                    usuario.setNome(textoNome);
                    usuario.setEmail(textoEmail);
                    usuario.setSenha(textoSenha);

                    //Passando objeto usuario
                    cadastrarUsuario(usuario);

                }else{
                    Toast.makeText(Cadastro_Activity.this, "Preencha o senha", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(Cadastro_Activity.this, "Preencha o email", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(Cadastro_Activity.this, "Preencha o nome", Toast.LENGTH_SHORT).show();
        }
    }

}
