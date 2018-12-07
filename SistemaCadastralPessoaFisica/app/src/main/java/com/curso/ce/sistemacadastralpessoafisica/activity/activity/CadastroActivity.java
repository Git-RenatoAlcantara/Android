package com.curso.ce.sistemacadastralpessoafisica.activity.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.curso.ce.sistemacadastralpessoafisica.R;

import java.util.ArrayList;
import java.util.List;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoCpf, campoIdade, campoTelefone, campoEmail;


    //Variavel de instacia sqlite
    private SQLiteDatabase bancoDados;

    //Variavel de instancia classe Pessoa
    private Pessoa pessoa;

    //Lista de objetos Pessoa
    private List<Pessoa> listaPessoa = new ArrayList<>();

    //Variavel contadora
    private int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        //Chamando metodo para iniciar componentes
        inicializarComponentes();


    }

    //Metodo responsavel pela  persistência
    public void salvarDados(View view){

        //Instanciando objto pessoa
        pessoa = new Pessoa();

        //Setando valores aos atributos da classe
        pessoa.setNome(campoNome.getText().toString());
        pessoa.setCpf(campoCpf.getText().toString());
        pessoa.setIdade(campoIdade.getText().toString());
        pessoa.setTelefone(campoTelefone.getText().toString());
        pessoa.setEmail(campoEmail.getText().toString());


        //Tratamento de erros
        try{

            //Criar banco de dados
            bancoDados = openOrCreateDatabase("cadastro", MODE_PRIVATE, null);

            //Criar tabela
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS pessoas (nome VARCHAR, cpf INT(11), idade INT(2), telefone INT(11), email VARCHAR)");




            //Inserir dados
            bancoDados.execSQL("INSERT INTO pessoas (nome, cpf, idade, telefone, email) VALUES ('"+pessoa.getNome()+"', '"+pessoa.getCpf()+"', '"+pessoa.getIdade()+"', '"+pessoa.getTelefone()+"', '"+pessoa.getEmail()+"')");

            //Chamando metodo para limbar campo
            limparCampos();



            //Recuperar erro
        }catch (Exception e){

            //print erro
            e.printStackTrace();
        }

    }

    //Recupera dados do banco
    public void recuperarDados(View view){

        //Chamando metodo para limpar os campos
        limparCampos();

        //Zerando listapessoa
        listaPessoa.clear();


       try{

          //Abrir banco de dados
           bancoDados = openOrCreateDatabase("cadastro", MODE_PRIVATE, null);

           //Selecionando e recuperando dados da tabela
           Cursor cursor = bancoDados.rawQuery("select * from pessoas", null);

           //Iniciando o cursor indice 0
           cursor.moveToFirst();

           //percorre coluna enquanto diferente de vazio
           while (cursor != null) {

               //Instanciando objeto pessoa
               pessoa = new Pessoa();

               //Setando valores aos atributos da classe pesso com o retorno do banco
               pessoa.setNome(cursor.getString(0));
               pessoa.setCpf( cursor.getString(1));
               pessoa.setIdade(cursor.getString(2));
               pessoa.setTelefone(cursor.getString(3));
               pessoa.setEmail(cursor.getString(4));

               //Adicionando pessoa a lista
               listaPessoa.add(pessoa);


               //Move o cursor enquanto diferente de null
               cursor.moveToNext();

           }



       }catch (Exception e){

           e.printStackTrace();

       }

        //Chamando metodo que preenche os campos da activity com os valores
        listarDados();

    }

    //Metodo para preencher os campos com os dados do banco
    private void listarDados(){

        //zera o contador ao chegar no fim da lista
        if (contador >= listaPessoa.size()){

            contador = 0;

        }

        //Setando os valores
        campoNome.setText(listaPessoa.get(contador).getNome());
        campoCpf.setText(listaPessoa.get(contador).getCpf());
        campoIdade.setText(listaPessoa.get(contador).getIdade());
        campoTelefone.setText(listaPessoa.get(contador).getTelefone());
        campoEmail.setText(listaPessoa.get(contador).getEmail());

        //Incrementa variavel contador
        contador++;


    }

    //Metodo botao voltar para a primeira activity
    public void botaoVoltar(View view){

        //Encerra activity
        finish();

    }

    //Metodo para limpar campo
    private void limparCampos(){
       campoNome.setText("");
       campoCpf.setText("");
       campoIdade.setText("");
       campoTelefone.setText("");
       campoEmail.setText("");
    }

    //Carrar valores da activity
    private void inicializarComponentes(){

        campoNome = findViewById(R.id.editTextNome);
        campoCpf = findViewById(R.id.editTextCpf);
        campoIdade = findViewById(R.id.editTextIdade);
        campoTelefone = findViewById(R.id.editTextTelefone);
        campoEmail = findViewById(R.id.editTextEmail);

    }


}
