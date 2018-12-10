package com.ifsp.cadastropessoa.activity;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ifsp.cadastropessoa.R;
import com.ifsp.cadastropessoa.modal.Pessoa;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoCpf, campoIdade, campoTelefone, campoEmail;
    private TextView setaVoltar;

    //Variavel de instacia sqlite
    private SQLiteDatabase bancoDados;

    //Variavel de instancia classe Pessoa
    private Pessoa pessoa;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        //Chamando metodo para iniciar componentes
        inicializarComponentes();


    }
    public void validarCampos(View view){

        String nome = campoNome.getText().toString();
        String cpf = campoCpf.getText().toString();
        String idade = campoIdade.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoTelefone.getText().toString();


        if (!nome.isEmpty()) {
            if (!cpf.isEmpty()){
                if (!idade.isEmpty()){
                    if (!telefone.isEmpty()){
                        if (!email.isEmpty()){
                            salvarDados();
                        }else{
                            Toast.makeText(this, "Preencha o campo email", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(this, "Preencha o campo telefone", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Preencha o campo idade", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Preencha o campo cpf", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Preencha o campo nome", Toast.LENGTH_SHORT).show();
        }

    }
    //Metodo responsavel pela  persistência
    public void salvarDados(){

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
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS pessoas (id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR, cpf INT(11), idade INT(2), telefone INT(11), email VARCHAR)");
            //bancoDados.execSQL("DROP TABLE pessoas");



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
        setaVoltar = findViewById(R.id.setaVoltar);

    }


    public void voltar(View view){
        finish();
    }

}
