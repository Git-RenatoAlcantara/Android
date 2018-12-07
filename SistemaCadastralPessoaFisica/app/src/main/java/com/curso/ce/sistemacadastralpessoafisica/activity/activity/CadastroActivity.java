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
    private SQLiteDatabase bancoDados;
    private Pessoa pessoa;
    private List<Pessoa> listaPessoa = new ArrayList<>();
    private int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inicializarComponentes();


    }

    public void salvarDados(View view){

        pessoa = new Pessoa();
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


            //bancoDados.execSQL("DROP TABLE pessoas");


            //Inserir dados
            bancoDados.execSQL("INSERT INTO pessoas (nome, cpf, idade, telefone, email) VALUES ('"+pessoa.getNome()+"', '"+pessoa.getCpf()+"', '"+pessoa.getIdade()+"', '"+pessoa.getTelefone()+"', '"+pessoa.getEmail()+"')");

            limparCampos();



            //Recuperar erro
        }catch (Exception e){

            //print erro
            e.printStackTrace();
        }

    }

    public void recuperarDados(View view){
        limparCampos();
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


               pessoa = new Pessoa();
               pessoa.setNome(cursor.getString(0));
               pessoa.setCpf( cursor.getString(1));
               pessoa.setIdade(cursor.getString(2));
               pessoa.setTelefone(cursor.getString(3));
               pessoa.setEmail(cursor.getString(4));

               listaPessoa.add(pessoa);


               //Move o cursor enquanto diferente de null
               cursor.moveToNext();

           }



       }catch (Exception e){

           e.printStackTrace();

       }

        listarDados();

    }

    private void listarDados(){

        if (contador >= listaPessoa.size()){
            contador = 0;
        }

        System.out.println(listaPessoa.size());
        campoNome.setText(listaPessoa.get(contador).getNome());
        campoCpf.setText(listaPessoa.get(contador).getCpf());
        campoIdade.setText(listaPessoa.get(contador).getIdade());
        campoTelefone.setText(listaPessoa.get(contador).getTelefone());
        campoEmail.setText(listaPessoa.get(contador).getEmail());
        contador++;
        

    }

    public void botaoVoltar(View view){

        finish();

    }


    private void limparCampos(){
       campoNome.setText("");
       campoCpf.setText("");
       campoIdade.setText("");
       campoTelefone.setText("");
       campoEmail.setText("");
    }

    private void inicializarComponentes(){

        campoNome = findViewById(R.id.editTextNome);
        campoCpf = findViewById(R.id.editTextCpf);
        campoIdade = findViewById(R.id.editTextIdade);
        campoTelefone = findViewById(R.id.editTextTelefone);
        campoEmail = findViewById(R.id.editTextEmail);

    }


}
