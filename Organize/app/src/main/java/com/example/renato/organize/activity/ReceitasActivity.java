package com.example.renato.organize.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.renato.organize.R;
import com.example.renato.organize.config.ConfiguracaoFireBase;
import com.example.renato.organize.helper.Base64Custom;
import com.example.renato.organize.helper.DateCustom;
import com.example.renato.organize.model.Movimentacao;
import com.example.renato.organize.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ReceitasActivity extends AppCompatActivity {
    private EditText editValor;
    private TextInputEditText editCategoria, editDescricao, editData;
    private Movimentacao movimentacao;
    private Double receitaTotal = 0.00;
    private  Double despesaAtualizada = 0.00;

    private FirebaseAuth autenticacao = ConfiguracaoFireBase.getfireBaseAutenticacao();
    private DatabaseReference firebase = ConfiguracaoFireBase.getFirebaseDatabase();
    private Double despesaTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);
        editData = findViewById(R.id.editData);
        editCategoria = findViewById(R.id.editCategoria);
        editValor = findViewById(R.id.editValor);
        editDescricao = findViewById(R.id.editDescricao);

        editData.setText(DateCustom.dataAtual());
        pegarReceitaTotal();
        recuperarRecitaTotal();

    }

    //Fucao para validar campos
    public void enviarDados(View view){

        if (! editValor.getText().toString().isEmpty()){
            if (!editData.getText().toString().isEmpty()){
                if (!editDescricao.getText().toString().isEmpty()){
                    if (!editCategoria.getText().toString().isEmpty()){
                       //Aqui eu chamo o metodo salvar despesas para salva no banco de dados
                        salvarReceitas();

                    }else{
                        Toast.makeText(ReceitasActivity.this, "Preencha o campo categoria", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ReceitasActivity.this, "Preencha o Descrição", Toast.LENGTH_SHORT).show();

                }
            }else{
                Toast.makeText(ReceitasActivity.this, "Preencha o campo data", Toast.LENGTH_SHORT).show();

            }
        }else{
            Toast.makeText(ReceitasActivity.this, "Preencha o campo valor", Toast.LENGTH_SHORT).show();

        }

    }

    //Metodo para salvar no banco firebase
    public void salvarReceitas(){

        //Chamando a classe model movimentacao
        movimentacao = new Movimentacao();
        //Convertendo o valor editValor em double salvando em uma variavel
        Double valorRecuperado = Double.parseDouble(editValor.getText().toString());

        //Setando valores ao objeto movimentacao
        movimentacao.setValor(valorRecuperado);
        movimentacao.setTipo("r");
        movimentacao.setData(editData.getText().toString());
        movimentacao.setCategoria(editCategoria.getText().toString());
        movimentacao.setDescricao(editDescricao.getText().toString());

        //A variavel despesaAtualizada recebe a variavel despesaTotal vinda do banco
        //E soma com a variavel valorRecuperado vindo do ImputTexto do Objeto ReceitasActivity
       despesaAtualizada = receitaTotal + valorRecuperado;

        //Metodo para atualizar depesa no banco
        atualizarReceita(despesaAtualizada);

        //Chamando o metodo salva da classe movimentacao responsavel por salva no banco
        movimentacao.salvar();

        finish();

    }

    //Metodo que recupera o valorTotal do banco
    public void recuperarRecitaTotal(){


        //Acessando o atributo autencacao do tipo FirebaseAuth e acessando o metodo
        //getCurrentUser para acessa o usuario autenticado e retorna o email
        String email = autenticacao.getCurrentUser().getEmail();

        //Criando uma istancia de DatabaseReference e acessando o metodo contido na classe
        //ConfiguracaoFireBase
        String idUsuario = Base64Custom.base64Encode(email);
        DatabaseReference usuarioRef = firebase.child("usuarios").child(idUsuario);


       usuarioRef.child(idUsuario);
        //Retorna a mudanca da despesa no banco
        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);

                //Passando o retorno da despesa para
                receitaTotal = usuario.getReceitaTotal();
                System.out.println("Receta total: "+ receitaTotal);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void atualizarReceita(Double receita){

        DatabaseReference usuarioRef = ConfiguracaoFireBase.getFirebaseDatabase().child("usuarios");
        String email = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.base64Encode(email);

        usuarioRef.child(idUsuario).child("receitaTotal").setValue(receita);
    }
    public void pegarReceitaTotal(){
        String email = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.base64Encode(email);

        DatabaseReference usuarioRef = firebase.child("usuarios").child(idUsuario);
        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                despesaTotal = usuario.getReceitaTotal();
                System.out.println(despesaTotal);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}